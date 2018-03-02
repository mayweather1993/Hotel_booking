package com.booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
public class BookingsDTO {
    private final Instant from;
    private final Instant to;
    private final List<String> rooms;
    private final Double totalPrice;
}
