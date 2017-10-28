package com.linkedin.learning.linkedinlearningfullstackappangularspringboot;

import com.linkedin.learning.rest.ReservationResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Linked in learning full stack app angular spring boot application.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = ReservationResource.class)
public class LinkedInLearningFullStackAppAngularSpringBootApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(LinkedInLearningFullStackAppAngularSpringBootApplication.class, args);
	}
}
