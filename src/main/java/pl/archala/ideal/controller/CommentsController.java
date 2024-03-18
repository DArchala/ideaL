package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.*;
import pl.archala.ideal.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/details/{id}")
    public GetCommentDTO getById(@PathVariable Long id) {
        return commentsService.findById(id);
    }

    @PostMapping("/idea")
    public ResponseEntity<GetCommentDTO> saveIdeaComment(@Valid @RequestBody AddIdeaCommentDTO addIdeaCommentDTO) {
        return ResponseEntity.status(201).body(commentsService.save(addIdeaCommentDTO));
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentDTO> saveCommentComment(@Valid @RequestBody AddCommentCommentDTO addIdeaCommentDTO) {
        return ResponseEntity.status(201).body(commentsService.save(addIdeaCommentDTO));
    }

    @PostMapping("/realization")
    public ResponseEntity<GetCommentDTO> saveRealizationComment(@Valid @RequestBody AddRealizationCommentDTO addRealizationCommentDTO) {
        return ResponseEntity.status(201).body(commentsService.save(addRealizationCommentDTO));
    }

    @PatchMapping
    public GetCommentDTO updateCommentContent(@Valid @RequestBody PatchCommentDTO patchCommentDTO) {
        return commentsService.updateContent(patchCommentDTO);
    }

    @DeleteMapping
    public GetCommentDTO delete(@RequestParam Long id) {
        return commentsService.deleteById(id);
    }
}
