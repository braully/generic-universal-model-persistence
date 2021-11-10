/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.github.braully.persistence;

import io.github.braully.util.UtilCollection;
import static io.github.braully.util.UtilCollection.add;
import io.github.braully.util.UtilValidation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Braully Rocha
 */
public class Query<T> {

    static int WINDOW_SIZE = 7;

    public static <T> Query<T> of(Class<T> aClass) {
        return new Query<T>(aClass);
    }

    Map parameters;
    Map infoExtra;

    String queryString;
    String queryCountString;
    Class<T> type;

//    Query query;
    List<T> result;
    Set<String> fetchs;
    Set<String> orders;
    Map<String, Object> wheres;
    Set<String> whereRaws;

    int page;
    int count;
    int size;

    Query() {
        result = new ArrayList();
        parameters = new HashMap<>();
    }

    Query(Class<T> type) {
        this();
        this.type = type;
    }

    Query(int size, int page) {
        this();
        this.size = size;
        this.page = page;
    }

    public Query where(String raw) {
        whereRaws = add(whereRaws, raw);
        return this;
    }

    public Query where(Object... param) {
        wheres = add(wheres, param);
        return this;
    }

    public Query fetch(String... fetch) {
        fetchs = add(fetchs, fetch);
        return this;
    }

    public Query orderBy(String... sort) {
        orders = add(orders, sort);
        return this;
    }

    public Query putParam(String paramName, Object v) {
        parameters = add(parameters, paramName, v);
        return this;
    }

    public List getResult() {
        return result;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        if (size == 0) {
            return 0;
        }
        float tpages = (float) count / (float) size;
        return Math.round(tpages);
    }

    public List<Integer> getWindowPages() {
        return UtilCollection.list(getWindowIni(), getWindowEnd());
    }

    public int getWindowIni() {
        if (getTotalPages() > WINDOW_SIZE) {
            int desloc = (this.page % (WINDOW_SIZE - 2));
            int pos = desloc * WINDOW_SIZE;
            return Math.min(this.page, pos);
        }
        return 0;
    }

    public int getWindowEnd() {
        int totalPages = this.getTotalPages();
        if (getTotalPages() > WINDOW_SIZE) {
            return Math.min(getWindowIni() + WINDOW_SIZE, totalPages);
        }
        return totalPages;
    }

    void newResults(Collection nwreslt) {
        result.clear();
        result.addAll(nwreslt);
    }

    public void setPage(int toPage) {
        this.page = toPage;
    }

    public void next() {
        if (!hasNext()) {
            throw new IllegalStateException("Page Query Result not found");
        }
        this.page++;
    }

    public void previous() {
        if (!hasPrevious()) {
            throw new IllegalStateException("Page Query Result not found");
        }
        this.page--;
    }

    public boolean hasNext() {
        return this.page < this.getTotalPages();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public boolean hasWindowPageQueryResult() {
        //Se tem mais paginas que o tamanho da janela
        if (this.getTotalPages() > WINDOW_SIZE) {
            return true;
        }
        return false;
    }

    public T get() {
        if (UtilValidation.hasData(result)) {
            return result.get(0);
        }
        return null;
    }

    public List list() {
        return result;
    }

    public List listPaged() {
        return null;
    }
}
