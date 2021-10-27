package com.github.braully.domain;

import com.github.braully.persistence.IEntity;
import com.github.braully.util.UtilValidation;
import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER,
        name = "type_id", columnDefinition = "smallint default '0'"
)
public abstract class AbstractSimpleEntity implements IEntity, Serializable {

    public AbstractSimpleEntity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isPersisted() {
        return this.id != null && this.id > 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        IEntity other = (IEntity) obj;
        if (id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(preToString());
        sb.append(" (#");
        sb.append(id);
        String posToString = "";

        try {
            posToString = posToString();
        } catch (Exception e) {

        }

        try {
            if (UtilValidation.isStringValid(posToString)) {
                sb.append(" ").append(posToString);
            }
        } catch (Exception e) {
        }

        sb.append(')');
        return sb.toString();
    }

    protected String preToString() {
        return this.getClass().getSimpleName();
    }

    protected String posToString() {
        return null;
    }

    @Transient
    CacheTemporayAtrribute cache = null;

    public CacheTemporayAtrribute cache() {
        if (cache == null) {
            cache = new CacheTemporayAtrribute();
        }
        return cache;
    }

    public CacheTemporayAtrribute getCache() {
        return cache();
    }
}
