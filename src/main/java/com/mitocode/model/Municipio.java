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
@Table(name = "Municipios")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer municipioId;

    @ManyToOne
    @JoinColumn(name = "DepartamentoID", nullable = false)
    private Departamentos departamento;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;
}