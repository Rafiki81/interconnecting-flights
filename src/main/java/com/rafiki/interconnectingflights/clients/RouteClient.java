package com.rafiki.interconnectingflights.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.rafiki.interconnectingflights.models.Route;

@FeignClient(name = "routeClient", url = "https://services-api.ryanair.com")
public interface RouteClient {

	@GetMapping(value = "/locate/3/routes")
	 public List<Route> getRoutes();
}
