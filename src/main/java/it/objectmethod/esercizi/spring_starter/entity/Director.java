package it.objectmethod.esercizi.spring_starter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "city", nullable = false)
    private String city;

    @ToString.Exclude
    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Film> films = new ArrayList<>();
}
