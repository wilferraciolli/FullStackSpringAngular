package com.linkedin.learning.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Database config. This class is to set up JPA usage.
 */
@EnableJpaRepositories("com.linkedin.learning.repository")
@EnableTransactionManagement
public class DatabaseConfig {

}
