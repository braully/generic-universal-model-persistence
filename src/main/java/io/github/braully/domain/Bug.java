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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author braullyrocha
 */
@Entity
@Getter
@Setter @Accessors(chain = true)
@Table(name = "bug", schema = "base")
public class Bug extends AbstractStatusEntity implements Serializable {

    @Column(name = "status_bug")
    @Enumerated
    protected StatusBug statusBug;
    @Column(name = "num_redmine")
    protected String numRedMine;
    @Column(name = "data_criacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateCreated;
    @Column(name = "data_finalizacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateEnd;
    @ManyToOne(fetch = FetchType.LAZY)
    protected App app;
    @OneToOne(fetch = FetchType.LAZY)
    protected LogEntryErrorView viewLog;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Bug parent;
    @Column(columnDefinition = "TEXT")
    protected String description;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Task task;
    @Transient
    protected List<LogEntryError> messages;

    public Bug() {
        this.statusBug = StatusBug.OPEN;
        this.dateCreated = new Date();
    }

    public Bug(LogEntryErrorView vis) {
        this();
        this.viewLog = vis;
    }

    public LogEntryErrorView getVisaoLog() {
        return viewLog;
    }

    public void setVisaoLog(LogEntryErrorView visaoLog) {
        this.viewLog = visaoLog;
    }

    public String getAssunto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problema em ");
        sb.append(viewLog.getLocation());
        return sb.toString();
    }

    public String getDescricaoFormatada() {
        StringBuilder sb = new StringBuilder();
        sb.append("Problema identificado no projeto ");
        sb.append(viewLog.getApp());
        sb.append(" na versão ");
        sb.append(viewLog.getGreatVersion());
        sb.append(" na revisão ");
        sb.append(viewLog.getLastRevision());
        sb.append(", foram encontradas ");
        sb.append(viewLog.getCountOccurrence());
        sb.append(" ocorrencia(s), na seguinte localização ");
        sb.append(viewLog.getLocation());
        sb.append(".\n");
        return sb.toString();
    }

    public String getNotas() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public boolean isAberto() {
        return this.statusBug == statusBug.OPEN;
    }

    public Date getDataCriacao() {
        return dateCreated;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dateCreated = dataCriacao;
    }

    public Date getDataFinalizacao() {
        return dateEnd;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dateEnd = dataFinalizacao;
    }

    public Bug getPai() {
        return parent;
    }

    public void setPai(Bug pai) {
        this.parent = pai;
    }

    public Task getTarefa() {
        return task;
    }

    public void setTarefa(Task tarefa) {
        this.task = tarefa;
    }

    public void setDescricao(String descricao) {
        this.description = descricao;
    }

    public String getDescricao() {
        return description;
    }

    public App getProjeto() {
        return app;
    }

    public void setProjeto(App projeto) {
        this.app = projeto;
    }

    public List<LogEntryError> getMensagens() {
        return messages;
    }

    public void setMensagens(List<LogEntryError> mensagens) {
        this.messages = mensagens;
    }

    public List<Task> getCadeiaTarefas() {
        List<Task> tarefas = new ArrayList<Task>();
        if (this.task != null) {
            tarefas.add(this.task);
//            tarefas.addAll(this.task.get());
        }
        return tarefas;
    }

    public void finalizar() {
        if (this.statusBug == StatusBug.OPEN || this.statusBug == StatusBug.REPORTED) {
            this.statusBug = StatusBug.CANCELED;
            this.dateEnd = new Date();
        } else {
            throw new IllegalStateException("Bug não pode ser finalizado.");
        }
    }

    public void resolver() {
        if (this.statusBug == StatusBug.OPEN || this.statusBug == StatusBug.REPORTED) {
            this.statusBug = StatusBug.SOLVED;
            this.dateEnd = new Date();
        } else {
            throw new IllegalStateException("Bug não pode ser resolvido.");
        }
    }

    public boolean isFinalizado() {
        return this.statusBug == StatusBug.CANCELED;
    }

    public boolean isReportado() {
        return statusBug == StatusBug.REPORTED;
    }

    public boolean isResolvido() {
        return statusBug == StatusBug.SOLVED;
    }
}
