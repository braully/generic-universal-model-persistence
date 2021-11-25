/* MIT License
* 
* Copyright (c) 2021 Braully Rocha
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */
package io.github.braully.domain;

import io.github.braully.constant.Attr;
import io.github.braully.persistence.IEntity;
import io.github.braully.persistence.IGlobalEntity;
import io.github.braully.persistence.ILightRemoveEntity;
import io.github.braully.util.UtilValidation;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Where;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER,
        name = "type_id", columnDefinition = "smallint default '0'"
)
//@Where(clause = "removed = false")
//@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity
        implements IEntity, Serializable,
        ILightRemoveEntity, IGlobalEntity {

    public AbstractEntity() {

    }

    @Id
    @GeneratedValue(generator = "snowflawke-id-generator")
    //TODO: Transfer for file properties or Convert JPA 
    @GenericGenerator(name = "snowflawke-id-generator",
            strategy = "io.github.braully.persistence.HibernateSnowflakeIdGenerator")
    protected Long id;

//    @Attr("hidden")
//    @Basic
//    @Column(unique = true, insertable = false, updatable = false,
//            //Postgres
//            columnDefinition = "bigint NOT NULL DEFAULT nextval('base.sequence_abstract_entity_id')"
//    )
//    protected Long shortId;
    @Attr("hidden")
    @Basic
    @Column(columnDefinition = "boolean DEFAULT false")
    protected Boolean removed = false;

    //https://stackoverflow.com/questions/5257709/how-to-autogenerate-created-or-modified-timestamp-field
    @Attr("hidden")
    //@Basic
    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    @LastModifiedDate
    protected Date lastModifiedDate;

    //https://blog.countableset.com/2014/03/08/auditing-spring-data-jpa-java-config/
    @Attr("hidden")
//    @CreatedBy
//    @LastModifiedBy
    /*
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    protected UserLogin lastModifiedUser;
     */
    //@Basic//For performance
    //https://github.com/spring-projects/spring-data-examples/blob/master/jpa/example/src/main/java/example/springdata/jpa/auditing/AuditableUser.java
    @Column(name = "fk_last_modified_user")
    protected Long lastModifiedUser;

    /*@PrePersist
    @PreUpdate
    protected void refreshLastModifiedDate() {
        this.lastModifiedDate = new Date();
    }*/
    public void toggleRemoved() {
        if (this.removed == null) {
            this.removed = false;
        }
        this.removed = !this.removed;
    }

    public Boolean getActive() {
        return getRemoved() == null || !getRemoved();
    }

    public void setActive(Boolean set) {
        setRemoved(!set);
    }

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
