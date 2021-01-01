package com.rafiki.interconnectingflights.clients;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.rafiki.interconnectingflights.models.Route;

@SpringBootTest
public class RouteClientTest {
	
	@Autowired
	RouteClient client;
	
	@Test
	public void findAllRoutes_shouldRunSuccesfully() throws Exception {
		//given route client
		//then
	   List<Route> routes = new ArrayList<>(client.getRoutes());
	   
	   //then
	   assertThat(routes).hasSizeGreaterThan(2);

	}

}
