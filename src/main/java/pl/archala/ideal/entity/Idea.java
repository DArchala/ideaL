package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String content;
//TODO
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany
    private List<Comment> comment;

    @Column
    private LocalDateTime created;

}
