package pl.archala.ideal.application.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.domain.service.interfaces.RealizationsService;

import java.util.List;

@RestController
@RequestMapping("/api/realizations")
@RequiredArgsConstructor
public class RealizationsController {

    private final RealizationsService realizationsService;

    @GetMapping("/details/{id}")
    public GetRealizationResponse findById(@PathVariable Long id) {
        return realizationsService.findById(id);
    }

    @GetMapping("/by-idea")
    public List<GetRealizationResponse> findByIdeaId(@RequestParam Long ideaId) {
        return realizationsService.findAllByIdeaId(ideaId);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentResponse> addComment(@Valid @RequestBody SaveCommentRequest saveCommentRequest) {
        return ResponseEntity.status(201)
                             .body(realizationsService.save(saveCommentRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteById(@RequestParam Long id) {
        realizationsService.deleteById(id);
    }
}
