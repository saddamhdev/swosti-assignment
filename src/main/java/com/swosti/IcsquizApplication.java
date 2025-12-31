package com.swosti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.swosti")
public class IcsquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcsquizApplication.class, args);
	}

}
