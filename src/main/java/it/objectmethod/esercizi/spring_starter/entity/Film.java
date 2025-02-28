package it.objectmethod.esercizi.spring_starter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "film")
@DynamicUpdate
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release_date;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "id_director")
    private Director director;

    //    @ToString.Exclude
    @ManyToMany(mappedBy = "films")
    private List<Actor> actor = new ArrayList<>();
}
