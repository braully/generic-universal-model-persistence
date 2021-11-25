package io.github.braully.persistence;

import io.github.braully.util.logutil;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Properties;
import java.util.Random;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

/**
 * Based in: https://developer.twitter.com/en/docs/basics/twitter-ids.html
 * https://github.com/magicae/hibernate-snowball-id-generator/
 * https://github.com/sony/sonyflake
 *
 */
public class HibernateSnowflakeIdGenerator implements IdentifierGenerator, Configurable {

    /* 2019-01-01 00:00:00.0000 */
    protected final long START_TIME = 1546308000000L;

    // |--- unused[1] ---|--- timestamp[41] ---|--- worker[10] ---|--- sequence[12] ---|
    private final long workerBitsIdPart = 10L;
    private final long maxWorkerIdSize = -1L ^ (-1L << workerBitsIdPart);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerBitsIdPart;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    protected long localWorkerId = 0;

    protected long localSequence = 0L;
    private long lastTimestamp = -1L;

    {
        byte[] address = null;
        //Trying get MAC Address
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
            address = ni.getHardwareAddress();
        } catch (Exception e) {

        }
        if (address == null) {
            //Else trying local IP
            try {
                address = InetAddress.getLocalHost().getAddress();
            } catch (Exception e) {
            }
        }
        // Random else
        if (address == null) {
            address = new byte[(int) workerBitsIdPart];
            new Random().nextBytes(address);
        }
        if (address != null) {
            for (byte x : address) {
                localWorkerId = ((localWorkerId << 8) - Byte.MIN_VALUE + x) & maxWorkerIdSize;
            }
        }
        logutil.debug(String.format("Worker starting. Timestamp left shift %d, worker id bits %d, sequence bits %d, worker id %d.",
                timestampLeftShift, workerBitsIdPart, sequenceBits, localWorkerId));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor ssci, Object o) throws HibernateException {
        return nextId();
    }

    protected synchronized long nextId() {
        long timestamp = timeGen();

        //TODO: Waiting is better than fail
        if (timestamp < lastTimestamp) {
            logutil.error(String.format("Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            localSequence = (localSequence + 1) & sequenceMask;
            if (localSequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            localSequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIME) << timestampLeftShift) | (localWorkerId << workerIdShift) | localSequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public void configure(Type type, Properties properties,
            ServiceRegistry serviceRegistry) throws MappingException {
    }
}
