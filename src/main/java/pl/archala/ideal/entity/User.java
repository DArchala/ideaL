package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
//TODO
//    @OneToMany
//    private List<Idea> ideas;
//    @OneToMany
//    private List<Comment> comments;
}
