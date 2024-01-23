package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "idea_id")
    private Idea idea;

    @ManyToOne
    @JoinColumn(name = "realization_id")
    private Realization realization;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column
    private LocalDateTime created;
}
