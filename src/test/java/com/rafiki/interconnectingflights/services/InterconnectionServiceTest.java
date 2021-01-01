package com.rafiki.interconnectingflights.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rafiki.interconnectingflights.models.Flight;
import com.rafiki.interconnectingflights.models.Interconnection;
import com.rafiki.interconnectingflights.models.Leg;
import com.rafiki.interconnectingflights.models.Route;

@SpringBootTest
class InterconnectionServiceTest {
	
	@MockBean
	InterconnectingService interconnectingService;
	
	@MockBean
	RouteService routeService;
	
	@MockBean
	private ScheduleService scheduleService;
	
	@Test
	void getInterconnections() {
		
		//given
		
		Route routeMock = Route.builder()
				.airportFrom("DUB")
				.airportTo("WRO")
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
		routes.add(routeMock2);
		
		String departure = "DUB";
	    String arrival = "WRO";
	    String number = "1234";
	    LocalDateTime departureDateTime = LocalDateTime.parse("2021-03-01T07:00");
	    LocalDateTime arrivalDateTime = LocalDateTime.parse("2021-03-03T07:00");
	    
	    LocalDateTime departureExpectedDateTime = LocalDateTime.parse("2021-03-01T06:00");
	    LocalDateTime arrivalExpectedDateTime = LocalDateTime.parse("2021-03-03T08:00");
	    
	    List<Flight> flights = new ArrayList<>();
	    Flight flight = Flight.builder()
	    		.arrivalAirport(arrival)
	    		.departureAirport(departure)
	    		.arrivalDateTime(arrivalDateTime)
	    		.departureDateTime(departureDateTime)
	    		.number(number)
	    		.build();
	    flights.add(flight);
	    
	    List<Interconnection> interconnections = new ArrayList<>();
	    Leg leg = Leg.builder()
	    		.departureAirport(departure)
	    		.arrivalAirport(arrival)
	    		.departureDateTime(departureDateTime)
	    		.arrivalDateTime(arrivalDateTime).build();
	    List<Leg> legs = new ArrayList<>();
	    legs.add(leg);
	    
	    Interconnection interconnection = Interconnection.builder()
	    		.stops(0)
	    		.legs(legs)
	    		.build();
	    
	    interconnections.add(interconnection);
		
	    //when
	    doReturn(interconnections)
	    .when(interconnectingService).getInterconnections(departure, arrival, departureExpectedDateTime, arrivalExpectedDateTime);
		
		//then
		assertThat(interconnections).isNotEmpty();
		assertThat(interconnections.get(0).getLegs()).isNotEmpty().hasSizeGreaterThan(0);
		assertThat(interconnections.get(0).getLegs().get(0).getArrivalAirport()).isEqualTo(arrival);
		assertThat(interconnections.get(0).getLegs().get(0).getDepartureAirport()).isEqualTo(departure);
		assertThat(interconnections.get(0).getLegs().get(0).getArrivalDateTime().isBefore(arrivalExpectedDateTime)).isTrue();
		assertThat(interconnections.get(0).getLegs().get(0).getDepartureDateTime().isAfter(departureExpectedDateTime)).isTrue();
	}
	

	
	

}
