package com.rafiki.interconnectingflights.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rafiki.interconnectingflights.models.Schedule;

@FeignClient(name = "scheduleClient", url = "https://timtbl-api.ryanair.com")
public interface ScheduleClient {
	
	 @GetMapping(value = "/3/schedules/{departure}/{arrival}/years/{year}/months/{month}")
	 public Schedule getSchedules(
	            @PathVariable("departure") String departure,
	            @PathVariable("arrival") String arrival,
	            @PathVariable("year") int year,
	            @PathVariable("month") int month
	 );

}
