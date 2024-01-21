package pl.archala.ideal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.ideal.entity.Realization;

@SuppressWarnings("unused")
@Repository
public interface RealizationsRepository extends JpaRepository<Realization, Long> {
}