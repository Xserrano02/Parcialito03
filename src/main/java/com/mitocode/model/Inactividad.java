package com.mitocode.model;
import lombok.Getter;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Inactividades")
public class Inactividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inactividadId;

    @ManyToOne
    @JoinColumn(name = "EmpleadoID", nullable = false)
    private Empleado empleado;

    @Column(name = "Motivo", nullable = false, length = 255)
    private String motivo;

    @Column(name = "FechaInactividad", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactividad;
}