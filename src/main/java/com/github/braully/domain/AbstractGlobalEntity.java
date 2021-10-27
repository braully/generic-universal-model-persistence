package com.github.braully.domain;

import com.github.braully.constant.Attr;
import com.github.braully.persistence.IEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractGlobalEntity
        implements IEntity, Serializable {

    public AbstractGlobalEntity() {

    }

    @Id
    //Global generator entity
    @GeneratedValue(generator = "snowflawke-id-generator")
    @GenericGenerator(name = "snowflawke-id-generator",
            strategy = "com.github.braully.persistence.HibernateSnowflakeIdGenerator")
    protected Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    @Basic
    @Attr("hidden")
    protected String uniqueCode;

    @Override
    public boolean isPersisted() {
        return this.id != null && this.id > 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + "#" + id + ')';
    }
}
