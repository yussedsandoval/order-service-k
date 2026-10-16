package com.logitrack.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank String name,
        @NotBlank @Size(min = 8, max = 254) String description,
        @NotNull BigDecimal price,
        @NotNull @PositiveOrZero Long stock) {
}
