package com.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.server.enumeration.Status;
import com.server.model.Server;
import com.server.repo.ServerRepo;

@SpringBootApplication
public class ServerStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerStatusApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepo serverRepo) {
		return args -> {
			serverRepo.save(new Server(null, "192.168.1.160", "Ubantu", "16 GB", "Personal PC",
					"http://localhost:8080/server/image/1.png", Status.SERVER_UP));
			serverRepo.save(new Server(null, "192.168.1.161", "Fedora", "10 GB", "Dell",
					"http://localhost:8080/server/image/2.png", Status.SERVER_DOWN));
			serverRepo.save(new Server(null, "192.168.1.162", "Windows", "24 GB", "Router",
					"http://localhost:8080/server/image/1.png", Status.SERVER_UP));
			serverRepo.save(new Server(null, "192.168.1.163", "MS 2008", "48 GB", "Mac",
					"http://localhost:8080/server/image/2.png", Status.SERVER_UP));
		};
	}
}
