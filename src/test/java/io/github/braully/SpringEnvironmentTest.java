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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

/**
 *
 * @author braully
 */
@SpringBootTest
@ContextConfiguration(classes = {SpringConfigDBTest.class})
@EnableTransactionManagement
public class SpringEnvironmentTest {

    @Autowired
    DataSource datsource;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testEnvironment() {
        System.out.println("testEnvironment()");
        System.err.println("testEnvironment()");
    }

    @Test
    public void testDbConnection() {
        Assert.notNull(datsource, "datasource is null");
    }

    @Test
    public void testJpaEM() {
        Assert.notNull(entityManager, "entity manager is null");
    }
}
