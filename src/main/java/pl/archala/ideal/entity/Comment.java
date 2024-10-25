package pl.archala.ideal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private OffsetDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

}
