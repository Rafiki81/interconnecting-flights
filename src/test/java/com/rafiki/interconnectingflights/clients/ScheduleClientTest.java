package com.rafiki.interconnectingflights.clients;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rafiki.interconnectingflights.models.Schedule;

@SpringBootTest
public class ScheduleClientTest {
	
	@Autowired
	ScheduleClient client;
	
	@Test
	public void findSchedules_shouldRunSuccesfully() throws Exception {
		//given
		String departure = "DUB";
		String arrival = "WRO";
		int year = 2021;
		int month = 4;
		//when
		Schedule schedule = client.getSchedules(departure, arrival, year, month);
		//then
		assertThat(schedule.getMonth()).isEqualTo(4);
		assertThat(schedule.getDays()).hasSizeGreaterThan(0);
		assertThat(schedule.getDays().get(4).getFlights()).hasSizeGreaterThan(0);
		
	}

}
