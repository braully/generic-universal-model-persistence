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

import io.github.braully.persistence.IGlobalEntity;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter @Accessors(chain = true)
@Table(schema = "security")
@NamedQueries({
    @NamedQuery(name = "Usuario.userByNome", query = "select u from UserLogin u where u.userName = :nome"),
    @NamedQuery(name = "Usuario.idUsuarioByNome", query = "select u.id from UserLogin u where u.userName = :nome")}
)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserLogin extends AbstractGlobalEntity implements IGlobalEntity {

    @Column(unique = true)
    @Basic
    protected String userName;
    @Basic
    protected String password;
    @Basic
    protected String email;
    @Basic
    protected String passwordType;
    @Basic
    protected Boolean active;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastLogin;

    @Column(columnDefinition = "smallint default '0'")
    @Enumerated(EnumType.ORDINAL)
    protected SysRole sysRole;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_login_role", schema = "security")
    protected Set<Role> roles;

    @ManyToMany(targetEntity = OrganizationRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_login_organization_role", schema = "security")
    protected Set<OrganizationRole> organizationRole;

    @ManyToMany(targetEntity = Menu.class)
    @JoinTable(schema = "security")
    protected Set<Menu> menus;

    public UserLogin() {

    }

    public UserLogin(String userName) {
        this.userName = userName;
    }

    public UserLogin(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    //Spring security methods
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles;
//    }
//    @Override
    public String getUsername() {
        return this.userName;
    }

//    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

//    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

//    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

//    @Override
    public boolean isEnabled() {
        return this.active == null || this.active;
    }
}
