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

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter @Accessors(chain = true)
@Entity
@Table(schema = "base")
public class Task extends AbstractEntity {

    @ManyToOne
    protected Partner partnerExecutor;

    @ManyToOne
    protected Partner partnerInterested;

    @Basic
    protected String name;
    @Column(columnDefinition = "TEXT")
    protected String description;
    @Enumerated
    protected StatusExecutionCycle statusExecution;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date date;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date finish;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Task parent;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Task next;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "base", name = "task_tag")
    protected Set<TagItem> tags;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    protected Set<Task> childrens;

    public Collection<Task> getChildrens() {
        return childrens;
    }

}
