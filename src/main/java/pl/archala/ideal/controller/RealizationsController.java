package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.List;

@RestController
@RequestMapping("/api/realizations")
@RequiredArgsConstructor
@Validated
public class RealizationsController {

    private final RealizationsService realizationsService;

    @GetMapping("/details/{id}")
    public GetRealizationDTO findById(@PathVariable Long id) {
        return realizationsService.findById(id);
    }

    @GetMapping("/by-idea")
    public List<GetRealizationDTO> findByIdeaId(@RequestParam Long ideaId) {
        return realizationsService.findAllByIdeaId(ideaId);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentDTO> addComment(@Valid @RequestBody AddCommentDTO addCommentDTO) {
        return ResponseEntity.status(201)
                             .body(realizationsService.addComment(addCommentDTO));
    }

    @DeleteMapping
    public GetRealizationDTO deleteById(@RequestParam Long id) {
        return realizationsService.deleteById(id);
    }
}
