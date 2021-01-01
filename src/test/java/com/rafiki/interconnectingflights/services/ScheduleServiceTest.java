package com.rafiki.interconnectingflights.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rafiki.interconnectingflights.clients.ScheduleClient;
import com.rafiki.interconnectingflights.models.DaySchedule;
import com.rafiki.interconnectingflights.models.Flight;
import com.rafiki.interconnectingflights.models.FlightSchedule;
import com.rafiki.interconnectingflights.models.Schedule;

@SpringBootTest
class ScheduleServiceTest {
	
	@MockBean
	private ScheduleService scheduleService;
	
	@MockBean
	private ScheduleClient scheduleClient;
	
	@Test
	void getScheduledFlightsTest() {
		
		//given
		String departure = "DUB";
	    String arrival = "WRO";
	    String number = "1234";
	    LocalDateTime departureDateTime = LocalDateTime.parse("2021-03-01T07:00");
	    LocalDateTime arrivalDateTime = LocalDateTime.parse("2021-03-03T07:00");
	    
		FlightSchedule flightSchedule = FlightSchedule.builder()
				.arrivalTime(arrivalDateTime.toLocalTime())
				.departureTime(departureDateTime.toLocalTime())
				.carrierCode("FR").build();
		List<FlightSchedule> flightSchedules = new ArrayList<>();
		flightSchedules.add(flightSchedule);
		
		DaySchedule day = new DaySchedule(3, flightSchedules);
		List<DaySchedule> days = new ArrayList<>();
		days.add(day);
		
		Schedule schedule = new Schedule(2021,days);
		
		
	    List<Flight> flights = new ArrayList<>();
	    Flight flight = Flight.builder()
	    		.arrivalAirport(arrival)
	    		.departureAirport(departure)
	    		.arrivalDateTime(arrivalDateTime)
	    		.departureDateTime(departureDateTime)
	    		.number(number)
	    		.build();
	    flights.add(flight);
	   
		//when
	    doReturn(schedule).when(scheduleClient)
	    .getSchedules(departure, arrival, departureDateTime.getYear(), departureDateTime.getMonthValue());
	    doReturn(flights)
	    .when(scheduleService).getScheduledFlightsDateTime(departure, arrival, departureDateTime, arrivalDateTime);
	    
		//then
	    assertThat(schedule.getDays()).isNotEmpty().hasSizeGreaterThan(0);
	    assertThat(schedule.getDays().get(0).getFlights()).hasSize(1);
	    assertThat(flights).isNotEmpty().hasSize(1);
		assertThat(flights.get(0).getNumber()).isEqualTo("1234");
	}

}
