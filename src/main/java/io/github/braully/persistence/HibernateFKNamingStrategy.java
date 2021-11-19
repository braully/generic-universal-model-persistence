package io.github.braully.persistence;

import io.github.braully.util.UtilString;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.hibernate.HibernateException;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
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
    public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
        String tableName = join(splitAndReplace(transformEntityName(source.getEntityNaming())));
        return toIdentifier(tableName, source.getBuildingContext());
    }

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
//        Identifier determineJoinColumnName = super.determineJoinColumnName(source);
//        return determineJoinColumnName;
        final String name;
        if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
                || source.getAttributePath() == null) {
            name = "fk_" + join(splitAndReplace(transformEntityName(source.getEntityNaming()))) //                    + '_'
                    //                    + source.getReferencedColumnName().getText()
                    ;
        } else {
            name = "fk_" + join(splitAndReplace(transformAttributePath(source.getAttributePath()))) //                    + '_'
                    //                    + source.getReferencedColumnName().getText()
                    ;
        }
        return toIdentifier(name, source.getBuildingContext());
    }

//https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/naming.html
    private LinkedList<String> splitAndReplace(String name) {
        LinkedList<String> result = new LinkedList<>();
        for (String part : UtilString.splitByCharacterTypeCamelCase(name)) {
            if (part == null || part.trim().isEmpty()) {
                // skip null and space
                continue;
            }
            result.add(part.toLowerCase(Locale.ROOT));
        }
        return result;
    }

    private String join(List<String> parts) {
        boolean firstPass = true;
        String separator = "";
        StringBuilder joined = new StringBuilder();
        for (String part : parts) {
            joined.append(separator).append(part);
            if (firstPass) {
                firstPass = false;
                separator = "_";
            }
        }
        return joined.toString();
    }
}
