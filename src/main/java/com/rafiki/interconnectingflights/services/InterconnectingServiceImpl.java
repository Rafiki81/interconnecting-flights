package com.rafiki.interconnectingflights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafiki.interconnectingflights.models.Flight;
import com.rafiki.interconnectingflights.models.Interconnection;
import com.rafiki.interconnectingflights.models.Leg;
import com.rafiki.interconnectingflights.models.Route;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class InterconnectingServiceImpl implements InterconnectingService {

    @Autowired
    private RouteService routeService;
    @Autowired
    private ScheduleService scheduleService;

    @Override
    public List<Interconnection> getInterconnections(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {

    	List<Route> routes = routeService.getRoutes();
        List<Interconnection> directFlights = routes.stream()
                .filter(route -> route.getAirportFrom().equals(departure) && route.getAirportTo().equals(arrival))
                .flatMap(route -> getDirectInterconnection(route, departureDateTime, arrivalDateTime).stream()).collect(Collectors.toList());
        List<Interconnection> oneStopFlights = routes.stream()
                .filter(route -> route.getAirportFrom().equals(departure) && !route.getAirportTo().equals(arrival))
                .flatMap(route -> getOneStopInterconnection(route, arrival, departureDateTime, arrivalDateTime).stream()).collect(Collectors.toList());
        
        return Stream.concat(directFlights.stream(), oneStopFlights.stream())
        .collect(Collectors.toList());
    }
    
	private List<Interconnection> getOneStopInterconnection(Route route, String arrivalAirport, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		
		List<Flight> firstLegFlights = scheduleService.getScheduledFlightsDateTime(route.getAirportFrom(), route.getAirportTo(), departureDateTime, arrivalDateTime);
		List<Flight> secondLegFlights = scheduleService.getScheduledFlightsDateTime(route.getAirportTo(), arrivalAirport, departureDateTime, arrivalDateTime);
		
        return firstLegFlights.stream().flatMap(firstLegFlight -> secondLegFlights.stream()
                .filter(secondLegFlight -> firstLegFlight.getArrivalDateTime().plusHours(2).isBefore(secondLegFlight.getDepartureDateTime()))
                .map(secondLegFlight -> flightToInterconnection(firstLegFlight, secondLegFlight))).collect(Collectors.toList());
    }
	
    
	private List<Interconnection> getDirectInterconnection(Route route, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		
		List<Flight> flights = scheduleService.getScheduledFlightsDateTime(route.getAirportFrom(), route.getAirportTo(), departureDateTime, arrivalDateTime);
		
        return flights.stream().map(this::flightToInterconnection).collect(Collectors.toList());
               
    }
    
    private Interconnection flightToInterconnection(Flight... flights) {
        return Interconnection.builder()
                .stops(flights.length - 1)
                .legs(Arrays.stream(flights)
                .map(this::flightToLeg)
                .collect(Collectors.toList()))
                .build();
    }
    
    private Leg flightToLeg(Flight flight) {
        return Leg.builder()
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalDateTime(flight.getArrivalDateTime())
                .build();
    }

}
