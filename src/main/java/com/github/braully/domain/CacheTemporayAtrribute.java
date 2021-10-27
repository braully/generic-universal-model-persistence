/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.braully.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author strike
 */
public class CacheTemporayAtrribute {

    Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public CacheTemporayAtrribute merge(CacheTemporayAtrribute cache) {
        if (cache != null) {
            merge(cache.map);
        }
        return this;
    }

    public CacheTemporayAtrribute merge(Map<String, Object> map) {
        if (map != null) {
            for (Entry<String, Object> e : map.entrySet()) {
                if (e.getValue() != null) {
                    this.map.put(e.getKey(), e.getValue());
                }
            }
        }
        return this;
    }

    public CacheTemporayAtrribute setValue(String attr, Object value) {
        this.map.put(attr, value);
        return this;
    }

}
