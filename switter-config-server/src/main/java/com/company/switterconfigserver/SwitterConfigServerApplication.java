package com.company.switterconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SwitterConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwitterConfigServerApplication.class, args);
	}

}
