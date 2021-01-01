package com.rafiki.interconnectingflights.controllers;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rafiki.interconnectingflights.models.Interconnection;
import com.rafiki.interconnectingflights.models.Leg;
import com.rafiki.interconnectingflights.services.InterconnectingService;



@WebMvcTest(InterconnectingFlightController.class)
class InterconnectingFlightsControllerRestAssuredTest {
	

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private InterconnectingService interconnectingService;
	

	@Test
	void verifyGetInterconnectionsOk() throws Exception {
		
		//Given
		String departure = "DUB";
	    String arrival = "WRO";
	    LocalDateTime departureDateTime = LocalDateTime.parse("2021-03-01T07:00");
	    LocalDateTime arrivalDateTime = LocalDateTime.parse("2021-03-03T07:00");
	    
	    LocalDateTime departureExpectedDateTime = LocalDateTime.parse("2021-03-01T06:00");
	    LocalDateTime arrivalExpectedDateTime = LocalDateTime.parse("2021-03-03T08:00");
	    
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
	    
	    //When
	    doReturn(interconnections)
	    .when(interconnectingService).getInterconnections(departure, arrival, departureExpectedDateTime, arrivalExpectedDateTime);
	  
	    //Then
		mockMvc.perform(MockMvcRequestBuilders.get("/flights/interconnections?departure=DUB&arrival=WRO&departureDateTime=2021-03-01T06:00&arrivalDateTime=2021-03-03T08:00"))
		        .andExpect(status().isOk()).andReturn();
	}
	

	
	
}
