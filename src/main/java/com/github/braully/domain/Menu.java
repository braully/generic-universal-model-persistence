//
// This file was generated by the JPA Modeler
//
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

package com.github.braully.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "menu", schema = "security")
public class Menu extends AbstractLightRemoveEntity implements Serializable {

    @Basic
    protected Integer sortIndex;

    @Basic
    protected String name;

    @Basic
    protected String link;

    @Basic
    protected String icon;

    @OneToMany(targetEntity = Menu.class, mappedBy = "parent")
    protected List<Menu> childs;

    @ManyToOne
    protected Menu parent;

    @Basic
    protected String value;

    public Menu() {

    }

    public Menu(String menu) {
        this.value = menu;
        this.name = menu;
    }

    public List<Menu> getChildsActive() {
        return (List<Menu>) this.cache().getMap()
                .computeIfAbsent("childsActive",
                        k -> this.childs.stream()
                                .filter(v -> v.getActive()).collect(Collectors.toList()));
    }

    public List<Menu> getChilds() {
        return this.childs;
    }

//    @JsonIgnore
    public Menu getPai() {
        return parent;
    }

    public void setPai(Menu pai) {
        this.parent = pai;
    }

    public Menu addChild(Menu menu) {
        if (this.childs == null) {
            this.childs = new ArrayList<>();
        }
        this.childs.add(menu);
        return this;
    }

    public Menu link(String link) {
        this.link = link;
        return this;
    }
}
