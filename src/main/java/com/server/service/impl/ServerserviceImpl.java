package com.server.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.server.enumeration.Status;
import com.server.model.Server;
import com.server.repo.ServerRepo;
import com.server.service.ServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerserviceImpl implements ServerService {

	private final ServerRepo serverRepo;

	@Override
	public Server create(Server server) {
		log.info("Saving new Servier: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching of servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by ID: {}", id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating new Servier: {}", server.getName());
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("deleting server ip: {}", id);
		serverRepo.deleteById(id);
		return true;

	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server ip: {}", ipAddress); // Basic Logging
		Server server = serverRepo.findByIpAddress(ipAddress); // finding a server from the database
		// checking if server is available or not in the computer
		InetAddress address = InetAddress.getByName(ipAddress);
		// if available the set the status within time
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepo.save(server); // save the server info to the database
		return server;
	}

	private String setServerImageUrl() {
		String[] imageNames = { "1.png", "2.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/server/image/" + imageNames[new Random().nextInt(2)]).toUriString();
	}

}
