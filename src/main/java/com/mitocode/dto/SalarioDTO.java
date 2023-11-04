package com.mitocode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SalarioDTO {

    @EqualsAndHashCode.Include
    private Integer salarioId;

    @NotNull
    private EmpleadoDTO empleado;

    @NotNull
    private Double salarioBruto;

    @NotNull
    private Double salarioLiquido;

    @NotNull
    private String fechaRegistro;
}