package com.crown.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChocolateDto {
    @NotBlank(message = "El sabor base es obligatorio")
    private String baseFlavor;

    @NotBlank(message = "El relleno es obligatorio")
    private String filling;
}
