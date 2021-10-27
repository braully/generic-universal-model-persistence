package com.github.braully.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braully
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_organization_role",
        columnNames = {"fk_role", "fk_organization"}), schema = "base")
public class OrganizationRole
        extends AbstractEntity implements Serializable {

    @ManyToOne
    Role role;

    @ManyToOne
    Organization organization;

    public OrganizationRole() {
    }

    public OrganizationRole(Organization o, Role rol) {
        this.role = rol;
        this.organization = o;
    }

    @Override
    protected String preToString() {
        return organization.toString();
    }

    @Override
    protected String posToString() {
        return this.role.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.role);
        hash = 29 * hash + Objects.hashCode(this.organization);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final OrganizationRole other = (OrganizationRole) obj;
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.organization, other.organization)) {
            return false;
        }
        return true;
    }

}
