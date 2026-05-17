package com.crown.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingAddressDto {
    @NotBlank(message = "Calle y número es obligatorio")
    private String calleNumero;

    @NotBlank(message = "Colonia es obligatoria")
    private String colonia;

    @NotBlank(message = "Código postal es obligatorio")
    private String codigoPostal;

    @NotBlank(message = "Ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "Estado es obligatorio")
    private String estado;
}
