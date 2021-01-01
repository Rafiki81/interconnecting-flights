package com.rafiki.interconnectingflights.services;


import com.rafiki.interconnectingflights.models.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
	List<Flight> getScheduledFlightsDateTime(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime);
}
