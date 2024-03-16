package pl.archala.ideal.repository.wrapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.archala.ideal.entity.Realization;
import pl.archala.ideal.repository.RealizationsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealizationsRepositoryWrapper {

    private final RealizationsRepository repository;

    public Realization findById(Long id) {
        Optional<Realization> optionalComment = repository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Realization with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }
}
