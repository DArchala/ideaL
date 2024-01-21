package pl.archala.ideal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.entity.Comment;

@SuppressWarnings("unused")
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
