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
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author braullyrocha
 */
@Entity(name = "TagItem")
//@Access(AccessType.FIELD)
@Table(name = "tag_item", schema = "legacy")
public class TagItem extends AbstractEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    protected TagItem pai;
    @OneToMany(mappedBy = "pai", fetch = FetchType.EAGER)
    protected Set<TagItem> filhos;

    public TagItem() {
    }

    public Set<TagItem> getFilhos() {
        return filhos;
    }

    public void setFilhos(Set<TagItem> filhos) {
        this.filhos = filhos;
    }

    public TagItem getPai() {
        return pai;
    }

    public void setPai(TagItem pai) {
        this.pai = pai;
    }
}
