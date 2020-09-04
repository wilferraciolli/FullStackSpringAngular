package com.wiltech.todos.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;

public class V1_002__TestInsertJava extends BaseJavaMigration {
    public void migrate(final Context context) throws Exception {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");

        try (final PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("insert into iam.Provider(id, name, description) values(1009, 'Admiral', 'Admiral Description');")) {

            statement.execute();
        }
    }
}
