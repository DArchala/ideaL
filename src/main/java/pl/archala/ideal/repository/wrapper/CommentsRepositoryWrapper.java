package pl.archala.ideal.repository.wrapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.archala.ideal.entity.Comment;
import pl.archala.ideal.repository.CommentsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsRepositoryWrapper {

    private final CommentsRepository repository;

    public Comment findById(Long id) {
        Optional<Comment> optionalComment = repository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment with id %d does not exist".formatted(id));
        }
        return optionalComment.get();
    }

    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    public void delete(Comment comment) {
        repository.delete(comment);
    }
}
