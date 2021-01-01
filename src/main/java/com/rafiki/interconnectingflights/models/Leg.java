package com.rafiki.interconnectingflights.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class Leg {
    String departureAirport;
    String arrivalAirport;
    LocalDateTime departureDateTime;
    LocalDateTime arrivalDateTime;
}
