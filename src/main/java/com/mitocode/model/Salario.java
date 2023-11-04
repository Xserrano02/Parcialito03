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
@Table(name = "Salarios")
public class Salario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salarioId;

    @ManyToOne
    @JoinColumn(name = "EmpleadoID", nullable = false)
    private Empleado empleado;

    @Column(name = "SalarioBruto", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioBruto;

    @Column(name = "SalarioLiquido", precision = 10, scale = 2)
    private BigDecimal salarioLiquido;

    @Column(name = "FechaRegistro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
}