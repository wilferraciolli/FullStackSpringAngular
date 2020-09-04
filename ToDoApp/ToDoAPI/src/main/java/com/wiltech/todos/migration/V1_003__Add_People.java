package com.wiltech.todos.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_003__Add_People extends BaseJavaMigration {

    public void migrate(final Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into iam.person(id, first_name, last_name, gender, phone_Number, birth_date, marital_status, number_of_dependants)\n"
                         + "    values(1001, 'Maria', 'Georgiou', 'FEMALE', '+44 7540595289', '1991-08-14' , 'MARRIED', 1)");
    }
}
