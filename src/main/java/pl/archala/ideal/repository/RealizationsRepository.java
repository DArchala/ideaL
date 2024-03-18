package pl.archala.ideal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.entity.Realization;

@Repository
public interface RealizationsRepository extends JpaRepository<Realization, Long> {

    @Modifying
    @Query("update Realization r set r.idea = null where r.idea.id = :ideaId")
    void detachIdeaFromRealizations(Long ideaId);

}
