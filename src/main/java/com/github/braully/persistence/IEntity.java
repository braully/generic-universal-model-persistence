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
package com.github.braully.persistence;

import java.io.Serializable;

/**
 *
 * @author braully
 */
public interface IEntity extends Serializable {//, Comparable<IEntity> {

    public Long getId();

    public void setId(Long id);

    default public boolean isPersisted() {
        return getId() != null && getId() > 0;
    }

    default public boolean isPersistido() {
        return isPersisted();
    }

    default public String getToString() {
        return toString();
    }

    default public String getToStringDetailed() {
        return toStringDetailed();
    }

    default public String toStringDetailed() {
        return toString();
    }

//    default public int compareTo(IEntity o) {
//        try {
//            return Long.compare(getId(), o.getId());
//        } catch (Exception e) {
//            return 0;
//        }
//    }
}
