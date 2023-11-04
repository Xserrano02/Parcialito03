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
public class EmpleadoDTO {

    @EqualsAndHashCode.Include
    private Integer empleadoId;

    @NotNull
    private String apellidos;

    @NotNull
    private String nombres;

    private String genero;

    @NotNull
    private String direccion;

    private String telefono;

    @NotNull
    private Boolean activo;
}