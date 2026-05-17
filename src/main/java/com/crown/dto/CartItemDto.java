package com.crown.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.List;

@Data
public class CartItemDto {

    @NotBlank(message = "El tamaño de caja es obligatorio")
    private String boxSize;

    @NotNull
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;

    @NotEmpty(message = "Debe incluir al menos un chocolate")
    @Valid
    private List<ChocolateDto> chocolates;
}
