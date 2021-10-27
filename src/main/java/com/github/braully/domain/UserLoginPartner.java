package com.github.braully.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Table(schema = "base",
        uniqueConstraints = @UniqueConstraint(name = "uk_partner_user_login",
                columnNames = {"fk_partner", "fk_user_login"})
)
@Entity
@Getter
@Setter
public class UserLoginPartner extends AbstractEntity implements Serializable {

    @ManyToOne(targetEntity = Partner.class)
    protected Partner partner;

    @ManyToOne(targetEntity = Partner.class)
    protected UserLogin userLogin;
}
