package com.github.braully.domain;

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

@Getter
@Setter
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
