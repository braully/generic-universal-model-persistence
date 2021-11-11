package io.github.braully.persistence;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

/**
 *
 * @author braully
 *
 *
 * References:
 * https://docs.jboss.org/hibernate/orm/5.0/userguide/html_single/Hibernate_User_Guide.html#ImplicitNamingStrategy
 * https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
 *
 */
public class HibernateFKNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
//        Identifier determineJoinColumnName = super.determineJoinColumnName(source);
//        return determineJoinColumnName;
        final String name;
        if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
                || source.getAttributePath() == null) {
            name = "fk_" + transformEntityName(source.getEntityNaming()) //                    + '_'
                    //                    + source.getReferencedColumnName().getText()
                    ;
        } else {
            name = "fk_" + transformAttributePath(source.getAttributePath()) //                    + '_'
                    //                    + source.getReferencedColumnName().getText()
                    ;
        }
        return toIdentifier(name, source.getBuildingContext());
    }
}
