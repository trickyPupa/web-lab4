package app.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
    @NotNull(message = "X cannot be null")
    @Size(min = -5, max = 5, message = "X must be between -5 and 5")
    private double x;

    @NotNull(message = "Y cannot be null")
    @Size(min = -5, max = 5, message = "Y must be between -5 and 5")
    private double y;

    @NotNull(message = "R cannot be null")
    @Size(min = -5, max = 5, message = "R must be between 0 and 5")
    private double r;
}
