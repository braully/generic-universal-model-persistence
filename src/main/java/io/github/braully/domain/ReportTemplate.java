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
import lombok.experimental.Accessors;

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
@Setter @Accessors(chain = true)
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
