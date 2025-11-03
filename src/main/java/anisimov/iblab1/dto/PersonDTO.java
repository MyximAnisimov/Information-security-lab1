package anisimov.iblab1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class PersonDTO {

    Long id;

    @NotBlank String name;

    @NotBlank String surname;

    Integer age;
}