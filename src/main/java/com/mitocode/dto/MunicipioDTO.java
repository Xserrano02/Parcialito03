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
public class MunicipioDTO {

    @EqualsAndHashCode.Include
    private Integer municipioId;

    @NotNull
    private DepartamentoDTO departamento;

    @NotNull
    private String nombre;
}