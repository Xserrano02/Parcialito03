package com.mitocode.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "camisa_id", referencedColumnName = "id")
    private Camisa camisa;

    @ManyToOne
    @JoinColumn(name = "posicion_id", referencedColumnName = "id")
    private Posicion posicion;
}

