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
public class InactividadDTO {

    @EqualsAndHashCode.Include
    private Integer inactividadId;

    @NotNull
    private EmpleadoDTO empleado;

    @NotNull
    private String motivo;

    @NotNull
    private String fechaInactividad;
}