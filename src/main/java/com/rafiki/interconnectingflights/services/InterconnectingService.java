package com.rafiki.interconnectingflights.services;

import com.rafiki.interconnectingflights.models.Interconnection;

import java.time.LocalDateTime;
import java.util.List;

public interface InterconnectingService {
	List<Interconnection> getInterconnections(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime);
}
