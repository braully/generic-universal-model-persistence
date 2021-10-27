package com.github.braully.domain;

import com.github.braully.persistence.IAuditableEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

@MappedSuperclass
public abstract class AbstractAuditableEntity extends AbstractEntity implements Serializable, IAuditableEntity {

    @Basic
    Long userIdModified;

    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date lastModified;

    public AbstractAuditableEntity() {

    }

    public Long getUserIdModified() {
        return this.userIdModified;
    }

    @Override
    public void setUserIdModified(Long userIdModified) {
        this.userIdModified = userIdModified;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
