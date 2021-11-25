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
package io.github.braully;

import io.github.braully.domain.EntityDummy;
import io.github.braully.persistence.DAOJPA;
import io.github.braully.util.UtilCollection;
import io.github.braully.util.UtilString;
import io.github.braully.util.UtilValidation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

/**
 *
 * @author braully
 */
@SpringBootTest
@ComponentScan({"io.github.braully"})
@ContextConfiguration(classes = {SpringConfigTest.class})
@ActiveProfiles("test")
public class SpringEnvironmentTest {

    @Autowired
    DataSource datsource;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    DAOJPA dao;

    @Test
    public void testEnvironment() {
        System.out.println("testEnvironment()");
    }

    @Test
    public void testDbConnection() {
        Assert.notNull(datsource, "datasource is null");
    }

    @Test
    public void testJpaEM() {
        Assert.notNull(entityManagerFactory, "entity manager is null");
    }

    @Test
    public void insertionTest() {
        EntityDummy dummy = new EntityDummy();
        dummy.setName(UtilString.random())
                .setDate(new Date())
                .setFraction(Math.random());
        System.out.println("Saving: " + dummy);
        dao.save(dummy);
        Assert.notNull(dummy.getId(), "Fail on save dummy, id is null");
    }

    @Test
    public void deleteTest() {
        List<EntityDummy> searched = dao.search(EntityDummy.class);
        if (UtilValidation.hasData(searched)) {
            EntityDummy dummy = searched.get(0);
            System.out.println("Deleting: " + dummy);
            dao.delete(dummy);
        }

    }
}
