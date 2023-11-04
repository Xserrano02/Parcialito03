package com.mitocode.model;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empleadoId;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Genero", length = 1)
    private String genero;

    @Column(name = "Direccion", nullable = false, length = 255)
    private String direccion;

    @Column(name = "Telefono", length = 50)
    private String telefono;

    @Column(name = "Activo", nullable = false)
    private Boolean activo;
}