package org.test.remotee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RemoteeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemoteeApplication.class, args);
	}

}
