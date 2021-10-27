/*
 Copyright 2109 Braully Rocha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 
 */
package com.github.braully.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braully
 */
@Entity
@Getter
@Setter
@Table(schema = "base")
public class EntityOperationDetail extends AbstractEntity {

    @Basic
    protected String classe;
    @Basic
    protected String operation;
    @Basic
    protected Integer count;
    @ManyToOne(targetEntity = UserLogin.class)
    protected UserLogin userLogin;

    public EntityOperationDetail() {
    }

    public EntityOperationDetail(String classe, String operation, UserLogin userLogin, Integer count) {
        this.classe = classe;
        this.operation = operation;
        this.userLogin = userLogin;
        this.count = count;
    }

}
