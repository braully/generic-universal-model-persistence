package io.github.braully.persistence;

import static io.github.braully.util.UtilValidation.hasData;
import static io.github.braully.util.UtilValidation.isValid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DAOJPA {

    @Autowired
    // @Inject
    EntityManager entityManager;

    public <T> T get(Class<T> classe, Object id) {
        return entityManager.find(classe, id);
    }

    public <T> Optional<T> getOpt(Class<T> classe, Object id) {
        try {
            return Optional.of(entityManager.find(classe, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> T get(Query<T> query) {
        execute(query);
        return (T) query.get();
    }

    public <T> T get(Class<T> classe, String searchString,
            Map<String, Object> extraSearchParams) {
        return null;
    }

    public <T> List<T> search(Class<T> classe) {
        return null;
    }

    public <T> List<T> search(Class<T> classe, String searchString, Map<String, Object> extraSearchParams) {
        return null;
    }

    // Build Query
    private <T> void execute(Query query) {
        constructQuery(query);
        executeQuery(query);
        //StringBuilder where = templateWhereQuery(query);
    }

    public Query query(Class classe) {
        return new Query();
    }

    String entityName(Class type) {
        return type.getSimpleName();
    }

    public Query convertQuery(javax.persistence.Query query, int size, int page, Map extraPaged) {
        Query pagedQueryResult = new Query(size, page);
        if (query != null) {
            Set<Parameter<?>> setparams = query.getParameters();
            if (setparams != null) {
                setparams.forEach(p -> pagedQueryResult.parameters.put(p, query.getParameterValue(p)));
            }
            String queryString = query.unwrap(org.hibernate.query.Query.class).getQueryString();
            pagedQueryResult.queryString = queryString;
            String queryCountString = countQueryString(queryString, extraPaged);
            pagedQueryResult.queryCountString = queryCountString;
        }
        return pagedQueryResult;
    }

    public String countQueryString(String queryString, Map extraPaged) throws IllegalStateException {
        String extraCount = null;
        if (extraPaged != null) {
            extraCount = (String) extraPaged.get("countExtra");
            if (extraCount != null && !extraCount.isBlank()) {
                extraCount = ", " + extraCount;
            }
        }
        if (extraCount == null) {
            extraCount = "";
        }
        String queryCountString = queryString.replaceFirst("SELECT ", "SELECT COUNT(");
        queryCountString = queryCountString.replaceFirst(" FROM ", ") as count " + extraCount + " FROM ");
        queryCountString = queryCountString.replaceFirst("LEFT JOIN FETCH .*? WHERE", "WHERE");
        //
        if (queryCountString.contains("LEFT JOIN FETCH")) {
            throw new IllegalStateException("A consulta possui mais de um carregamento");
        }
        //
        queryCountString = queryCountString.replaceFirst("ORDER BY .*?\n", "");
        return queryCountString;
    }

    @Transactional
    public void executeQuery(Query query) {
        javax.persistence.Query nquery = this.entityManager.createQuery(query.queryString);
//        queryResult.parameters.forEach((k, v) -> nquery.setParameter(k, v));
        setParameters(query, nquery);
        //If is paginated query
        if (query.size > 0) {
            nquery.setFirstResult(query.page * query.size);
            nquery.setMaxResults(query.size);
        }
        query.newResults(nquery.getResultList());
    }

    private void count(Query queryResult) {
        javax.persistence.Query query = this.entityManager.createQuery(queryResult.queryCountString);
        setParameters(queryResult, query);
        List resultList = query.getResultList();
        queryResult.count = 0;
        //
        if (resultList != null && !resultList.isEmpty()) {
            Object rcount = resultList.get(0);
            if (rcount instanceof Number) {
                queryResult.count = ((Number) rcount).intValue();
            } else if (rcount instanceof Object[]) {
                Object[] arr = (Object[]) rcount;
                queryResult.count = ((Number) arr[0]).intValue();
                //
                if (arr.length > 1) {
                    for (int i = 1; i < arr.length; i++) {
                        queryResult.infoExtra.put(i, arr[i]);
                    }
                }
            }
        }
    }

    private void setParameters(Query queryResult, javax.persistence.Query query) {
        if (queryResult != null && hasData(queryResult.parameters)) {

            Set<Map.Entry<Parameter, Object>> entrySet = queryResult.parameters.entrySet();
//        queryResult.parameters.forEach((k, v) -> query.setParameter(k, v));

            for (Map.Entry<Parameter, Object> e : entrySet) {
                Parameter key = e.getKey();
                String name = key.getName();
                Integer position = key.getPosition();
                if (name != null) {
                    query.setParameter(name, e.getValue());
                } else if (position != null) {
                    query.setParameter(position, e.getValue());
                } else {
                    query.setParameter(key, e.getValue());
                }
            }
        }
    }

    private void constructQuery(Query query) {
        StringBuilder selectFromClause = new StringBuilder("SELECT DISTINCT e FROM ");
        String abrevRaiz = "e";
        String raiz = entityName(query.type);

        selectFromClause.append(raiz).append(" ").append(abrevRaiz);
        Map<String, String> mapaPropriedades = new HashMap<String, String>();
        mapaPropriedades.put(raiz, abrevRaiz);
        if (hasData(query.fetchs)) {
            for (Object prop : query.fetchs) {
                String tmp = append(abrevRaiz, prop.toString(), mapaPropriedades);
                selectFromClause.append(tmp);
            }
        }

        StringBuilder whereClause = new StringBuilder();
        if (hasData(query.wheres)) {
            Set<Map.Entry> entrySet = query.wheres.entrySet();
            for (Map.Entry e : entrySet) {
                Object v = e.getValue();
                String k = e.getKey().toString();

                if (!isValid(v)) {
                    continue;
                }
                //Param property
                if (whereClause.length() > 0)//if (!firstWhere) {
                {
                    whereClause.append(" AND");
                }
                boolean string = v instanceof String;
                if (string) {
                    whereClause.append(" lower(");
                }

                String function = null;
                int fIdx = k.indexOf("(");
                String attr = k;
                if (fIdx > 0) {
                    function = k.substring(0, fIdx);
                    attr = k.substring(fIdx + 1, k.length() - 1);//Trim string name
                }

                //WHERE atribute
                whereClause.append(" e.");
                whereClause.append(attr);

                if (string) {
                    whereClause.append(")  like ");
                } else if (function != null) {
                    switch (function) {
                        case "ceil":
                            whereClause.append(" <= ");
                            break;
                        case "floor":
                            whereClause.append(" >= ");
                            break;
                        default:
                            whereClause.append(" = ");
                    }
                } else {
                    whereClause.append(" = ");
                }

                //PARAM NAME
                whereClause.append(":");
                String paramName = k.replaceAll("[^a-zA-Z0-9]", "_");
                whereClause.append(paramName);

                query.putParam(paramName, v);
            }
            selectFromClause.append(whereClause);
        }
        if (hasData(query.whereRaws)) {
            for (Object whr : query.whereRaws) {
                if (whereClause.length() > 0)//if (!firstWhere) {
                {
                    whereClause.append(" AND");
                }
                whereClause.append(whr);
            }
        }
        query.queryString = selectFromClause.toString();
    }

    public StringBuilder templateQueryFetch(String raiz, String... subselect) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT e FROM ");
        String abrevRaiz = "e";
        sb.append(raiz).append(" ").append(abrevRaiz);
        Map<String, String> mapaPropriedades = new HashMap<String, String>();
        mapaPropriedades.put(raiz, abrevRaiz);
        if (subselect != null && subselect.length > 0) {
            for (String prop : subselect) {
                String tmp = append(abrevRaiz, prop, mapaPropriedades);
                sb.append(tmp);
            }
        }
        return sb;
    }

    private String append(String parent, String prop, Map<String, String> mapProps) {
        StringBuilder sb = new StringBuilder();
        if (prop.contains(".")) {
            String filho = prop.substring(0, prop.indexOf("."));
            String propFilha = prop.substring(prop.indexOf(".") + 1);
            String paiTmp = mapProps.get(parent + "_" + filho);
            sb.append(append(paiTmp, propFilha, mapProps));
        } else {
            sb.append(" LEFT JOIN FETCH ");
            sb.append(parent);
            sb.append(".");
            sb.append(prop);
            sb.append(" ");
            sb.append(abbreviation(parent, prop, mapProps));
        }
        return sb.toString();
    }

    private String abbreviation(String parent, String prop, Map<String, String> mapProps) {
        String chave = parent + "_" + prop;
        String ret = mapProps.get(chave);
        if (ret == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(prop.charAt(0));
            int ind = 0;
            while (mapProps.containsValue(sb.toString())) {
                sb.append(ind++);
            }
            ret = sb.toString();
            mapProps.put(chave, ret);
        }
        return ret;
    }

    @Transactional
    public void save(IEntity e) {
        if (e.isPersisted()) {
            this.update(e);
        } else {
            this.insert(e);
        }
    }

    public void insert(Object entity) {
        entityManager.persist(entity);
    }

    public void update(Object entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(Object entity) {
        if (entity instanceof ILightRemoveEntity) {
            deleteSoft((ILightRemoveEntity) entity);
        } else if (entity instanceof IEntity) {
            IEntity tmp = (IEntity) entity;
            javax.persistence.Query query = entityManager.createQuery("DELETE FROM " + entity.getClass().getSimpleName() + " WHERE id = :id");
            query.setParameter("id", tmp.getId());
            int executeUpdate = query.executeUpdate();
        } else {
            entityManager.merge(entity);
            entityManager.remove(entity);
        }
    }

    @Transactional
    public void deleteSoft(ILightRemoveEntity entity) {
        javax.persistence.Query query = entityManager.createQuery("UPDATE " + entity.getClass().getSimpleName() + " SET removed = True WHERE id = :id");
        query.setParameter("id", entity.getId());
        int executeUpdate = query.executeUpdate();
    }
}
