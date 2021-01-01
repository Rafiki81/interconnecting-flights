package com.rafiki.interconnectingflights.services;

import com.rafiki.interconnectingflights.clients.RouteClient;
import com.rafiki.interconnectingflights.models.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteClient client;

    @Override
    public List<Route> getRoutes() {
        return client.getRoutes().stream()
        		.filter(route -> route.getConnectingAirport() == null && "RYANAIR".equals(route.getOperator())).collect(Collectors.toList());
    }


}
