package com.rafiki.interconnectingflights.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rafiki.interconnectingflights.clients.RouteClient;
import com.rafiki.interconnectingflights.models.Route;

@SpringBootTest
class RouteServiceTest {
	
	@MockBean
	RouteService routeService;
	
	@MockBean
	RouteClient routeClient;
	
	@Test
	void getRoutesTest() {
		
		//given
		Route routeMock = Route.builder()
				.airportFrom("DUB")
				.airportTo("WRO")
				.carrierCode("FR")
				.connectingAirport(null)
				.group("Canary")
				.operator("RYANAIR")
				.build();
		Route routeMock1 = Route.builder()
				.airportFrom("DUB")
				.airportTo("AAA")
				.carrierCode("FR")
				.connectingAirport(null)
				.group("Canary")
				.operator("RYANAIR")
				.build();
		Route routeMock2 = Route.builder()
				.airportFrom("DUB")
				.airportTo("AAA")
				.carrierCode("FR")
				.connectingAirport(null)
				.group("Canary")
				.operator("RYANAIR")
				.build();
		List <Route> routes = new ArrayList<>();
		routes.add(routeMock);
		routes.add(routeMock1);
		routes.add(routeMock2);

		//when
		doReturn(routes)
		.when(routeClient).getRoutes();
		doReturn(routes)
		.when(routeService).getRoutes();
		
		//then
		assertThat(routeService.getRoutes()).isNotEmpty().hasSizeGreaterThan(0);
	}

}
