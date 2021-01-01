package com.rafiki.interconnectingflights.models;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class Route {
    String airportFrom;
    String airportTo;
    String connectingAirport;
    Boolean newRoute;
    Boolean seasonalRoute;
    String operator;
    String group;
    List<String> similarArrivalAirportCodes;
    List<String> tags;
    String carrierCode;
}
