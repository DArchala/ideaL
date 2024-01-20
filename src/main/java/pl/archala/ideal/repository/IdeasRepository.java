package pl.archala.ideal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.entity.Idea;

@Repository
public interface IdeasRepository extends JpaRepository<Idea, Long> {

}
