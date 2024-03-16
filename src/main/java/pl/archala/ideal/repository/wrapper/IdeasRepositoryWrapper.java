package pl.archala.ideal.repository.wrapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.archala.ideal.entity.Idea;
import pl.archala.ideal.repository.IdeasRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeasRepositoryWrapper {

    private final IdeasRepository repository;

    public Idea findById(Long id) {
        Optional<Idea> ideaOptional = repository.findById(id);
        if (ideaOptional.isEmpty()) {
            throw new EntityNotFoundException("Idea with id %d does not exist".formatted(id));
        }
        return ideaOptional.get();
    }

    public Idea save(Idea idea) {
        return repository.save(idea);
    }

    public Page<Idea> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public void delete(Idea idea) {
        repository.delete(idea);
    }

    public long count() {
        return repository.count();
    }

}
