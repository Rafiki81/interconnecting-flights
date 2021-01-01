package com.rafiki.interconnectingflights.services;

import com.rafiki.interconnectingflights.clients.ScheduleClient;
import com.rafiki.interconnectingflights.models.DaySchedule;
import com.rafiki.interconnectingflights.models.Flight;
import com.rafiki.interconnectingflights.models.FlightSchedule;
import com.rafiki.interconnectingflights.models.Schedule;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleClient client;

	@Override
	public List<Flight> getScheduledFlightsDateTime(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		
		int year = departureDateTime.getYear();
		int month = departureDateTime.getMonthValue();
	
		return getScheduledFlights(departure, arrival, year, month, departureDateTime, arrivalDateTime);
		
	}
	
    private List<Flight> getScheduledFlights(String departure, String arrival, int year, int month, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        
    	Schedule schedule = client.getSchedules(departure, arrival, year, month);	
        return listOfFlights(schedule, departure, arrival, departureDateTime, arrivalDateTime);
               
    }
	
    private List<Flight> listOfFlights(Schedule schedule, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        return schedule.getDays().stream()
                .flatMap(day -> day.getFlights().stream()
                        .map(flight -> scheduletoFlight(schedule, day, flight, departure, arrival, departureDateTime)))
                .filter(flight -> flight.getDepartureDateTime().isAfter(departureDateTime) && flight.getArrivalDateTime().isBefore(arrivalDateTime))
                .collect(Collectors.toList());
    }
	
    private Flight scheduletoFlight(Schedule schedule, DaySchedule day, FlightSchedule flight, String departure, String arrival, LocalDateTime departureDateTime) {
        LocalDateTime flightDepartureDateTime = getDateTime(departureDateTime.getYear(), schedule.getMonth(), day.getDay(), flight.getDepartureTime());
        Duration duration = flightDuration(flight.getDepartureTime(), flight.getArrivalTime());
        LocalDateTime flightArrivalDateTime = flightDepartureDateTime.plusMinutes(duration.toMinutes());

        return Flight.builder()
                .number(flight.getNumber())
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureDateTime(flightDepartureDateTime)
                .arrivalDateTime(flightArrivalDateTime)
                .build();
    }

    private Duration flightDuration(LocalTime departure, LocalTime arrival) {
        Duration duration = Duration.between(departure, arrival);
        if (duration.isNegative()) {
            return Duration.ofDays(1).plusMinutes(duration.toMinutes());
        } else {
            return duration;
        }
    }
	
    private LocalDateTime getDateTime(Integer year, Integer month, Integer day, LocalTime time) {
        return LocalDateTime.of(LocalDate.of(year, month, day), time);
    }



}
