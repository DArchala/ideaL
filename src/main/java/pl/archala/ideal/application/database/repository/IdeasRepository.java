package pl.archala.ideal.application.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.domain.model.Idea;

@Repository
public interface IdeasRepository extends JpaRepository<Idea, Long> {

}
