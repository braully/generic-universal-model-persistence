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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braullyrocha
 */
@Entity
//@Table(name = "vw_log_consolidated", schema = "base")
@Getter
@Setter
@Table(schema = "base")
public class LogEntryErrorView implements Serializable {

    @Id
    protected int id;
    @Basic
    protected String app;
    @Basic
    protected String location;
    @Column
    protected int countOccurrence;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date lastOccurrence;
    @Column
    protected String greatVersion;
    @Column
    protected Long lastRevision;

    public LogEntryErrorView() {
    }

}
