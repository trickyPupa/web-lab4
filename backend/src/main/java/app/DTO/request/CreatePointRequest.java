package app.DTO.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CreatePointRequest(
        @NotNull(message = "X cannot be null")
        @DecimalMin(value = "-5.0")
        @DecimalMax("5.0")
        double x,

        @NotNull(message = "Y cannot be null")
        @DecimalMin(value = "-5.0")
        @DecimalMax("5.0")
        double y,

        @NotNull(message = "R cannot be null")
        @DecimalMin(value = "0.0")
        @DecimalMax("5.0")
        double r
) {}