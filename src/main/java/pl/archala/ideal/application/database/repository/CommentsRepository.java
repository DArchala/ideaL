package pl.archala.ideal.application.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.domain.model.Comment;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
