package com.rafiki.interconnectingflights.controllers;


import com.rafiki.interconnectingflights.controllers.errors.InterconnectionNotFoundException;
import com.rafiki.interconnectingflights.controllers.errors.QueryParamsNullException;
import com.rafiki.interconnectingflights.models.Interconnection;
import com.rafiki.interconnectingflights.services.InterconnectingService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableFeignClients
public class InterconnectingFlightController {

    private final InterconnectingService service;

    @ApiOperation(value = "information about possible direct and interconnected flights (maximum 1 stop) based on the data consumed from external APIs", response = Interconnection.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK", response = Interconnection.class),
    		@ApiResponse(code = 404, message = "Not Found"), 
    		@ApiResponse(code = 400, message = "Bad Request")
    })
    @GetMapping("/flights/interconnections")
    public ResponseEntity<?> getInterconnections(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime departureDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime arrivalDateTime
    ){
    	
    	if(departure.equals("") || arrival.equals("") || departureDateTime == null || arrivalDateTime == null) {
    		throw new QueryParamsNullException();
    	}
    	if(departureDateTime.isAfter(arrivalDateTime)){
            throw new InterconnectionNotFoundException();
        }

        List<Interconnection> interconnection = service.getInterconnections(departure, arrival,departureDateTime,arrivalDateTime);

        if(interconnection.isEmpty()) {
        	throw new InterconnectionNotFoundException();
        }else {
           return ResponseEntity.ok(interconnection);
        }
    }


}
