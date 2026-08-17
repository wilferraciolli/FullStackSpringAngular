package com.wiltech.pdfparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PdfParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfParserApplication.class, args);
	}

}
