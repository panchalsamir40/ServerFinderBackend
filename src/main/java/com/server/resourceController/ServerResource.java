package com.server.resourceController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.enumeration.Status;
import com.server.model.Response;
import com.server.model.Server;
import com.server.service.impl.ServerserviceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
	private final ServerserviceImpl serverservice;

	@GetMapping("/list")
	public ResponseEntity<Response> getServer() {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("servers", serverservice.list(30)))
						.message("Servers retrived")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {

		Server server = serverservice.ping(ipAddress);

		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("servers", server))
						.message(server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping Failed")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {

		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("servers", serverservice.create(server)))
						.message("Server Created")
						.status(HttpStatus.CREATED)
						.statusCode(HttpStatus.CREATED.value())
						.build());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable Long id) {

		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("servers", serverservice.get(id)))
						.message("Server Retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable Long id) {

		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("deleted", serverservice.delete(id)))
						.message(String.format("Server id %d has been deleted", id))
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] deleteServer(@PathVariable String fileName) throws IOException {
		return Files.readAllBytes(Paths.get("image/" + fileName));
	}
}
