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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author braullyrocha
 */
@Getter
@Setter
@Entity(name = "TagItem")
@Table(name = "tag_item", schema = "base")
public class TagItem extends AbstractEntity implements Serializable {

    protected String name;
    @ManyToOne(fetch = FetchType.LAZY)
    protected TagItem parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    protected Set<TagItem> childs;

    public TagItem() {
    }

    public Set<TagItem> getFilhos() {
        return childs;
    }

    public TagItem getPai() {
        return parent;
    }
}
