package com.mitocode.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class RegistroSalarioRequestDTO {

    @NotNull
    private Integer empleadoId;

    @NotNull
    private Double salarioBruto;

    // Getters y setters
}