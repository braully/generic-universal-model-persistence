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

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author user
 */
@Entity
@Table(schema = "base")
@DiscriminatorValue("0")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type_id",
        columnDefinition = "smallint default '0'", length = 1)
@Getter
@Setter
public class ReportTemplate extends AbstractStatusEntity {

    //TODO: Migrate to BinaryFile
    protected String description;
    protected String name;
    protected String localPath;
    protected byte[] report;

    @Column
    @Temporal(TemporalType.DATE)
    protected Date created;
    @Transient
    protected boolean statico;
    @Transient
    protected String classeStatica;

    public ReportTemplate() {
    }

    public ReportTemplate(String descricao, String nomeArquivo, byte[] relatorio,
            Date dataCadastro) {
        this.description = descricao;
        this.name = nomeArquivo;
        this.report = relatorio;
        this.status = status;
        this.created = dataCadastro;
    }

    public String getExtensaoArquivo() {
        String ret = null;
        if (this.name != null) {
            ret = this.name.substring(this.name.lastIndexOf('.'), this.name.length());
        }
        return ret;
    }

    public String getNomeSaida() {
        String ret = name;
        if (this.name != null) {
            ret = this.name.replaceAll(".jasper", ".pdf");
        }
        return ret;
    }
}
