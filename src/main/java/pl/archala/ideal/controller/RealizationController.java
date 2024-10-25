package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.realization.GetRealizationResponse;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.List;

@RestController
@RequestMapping("/api/realizations")
@RequiredArgsConstructor
@Validated
public class RealizationController {

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
                             .body(realizationsService.addComment(saveCommentRequest));
    }

    @DeleteMapping
    public GetRealizationResponse deleteById(@RequestParam Long id) {
        return realizationsService.deleteById(id);
    }
}
