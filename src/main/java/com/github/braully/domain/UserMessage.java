package com.github.braully.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "base")
@Getter
@Setter
public class UserMessage extends AbstractLightRemoveEntity implements Serializable {

    @ManyToOne(targetEntity = UserLogin.class)
    protected UserLogin userFrom;

    @Basic
    protected String title;

    @Basic
    protected String message;

    @Basic
    protected Date date;

    @Basic
    protected Date dateView;

    @ManyToOne(targetEntity = UserLogin.class)
    protected UserLogin userTo;

    public UserMessage() {

    }

}
