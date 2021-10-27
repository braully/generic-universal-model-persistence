package com.github.braully.domain;

import com.github.braully.constant.Attr;
import com.github.braully.persistence.IEntity;
import com.github.braully.persistence.IEntityStatus;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractStatusEntity extends AbstractEntity
        implements IEntity, IEntityStatus, Serializable {

    @Attr("hidden")
    @Column(name = "status",
            columnDefinition = "integer default '0'")
    @Enumerated(EnumType.ORDINAL)
    protected Integer status;

    @Override
    public boolean isPersisted() {
        return this.id != null && this.id > 0;
    }
}
