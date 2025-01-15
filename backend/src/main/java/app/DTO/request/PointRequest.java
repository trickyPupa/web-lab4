package app.DTO.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointRequest {
    @NotNull(message = "X cannot be null")
    @DecimalMin(value = "-5.0")
    @DecimalMax("5.0")
    private double x;

    @NotNull(message = "Y cannot be null")
    @DecimalMin(value = "-5.0")
    @DecimalMax("5.0")
    private double y;

    @NotNull(message = "R cannot be null")
    @DecimalMin(value = "0.0")
    @DecimalMax("5.0")
    private double r;
}
