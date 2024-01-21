package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
//TODO
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToOne
    private Idea idea;

    @Column
    private LocalDateTime created;
}
