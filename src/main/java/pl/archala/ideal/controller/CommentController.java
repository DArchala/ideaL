package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.comment.PutCommentDTO;
import pl.archala.ideal.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentsService commentsService;

    @GetMapping("/details/{id}")
    public GetCommentDTO getById(@PathVariable Long id) {
        return commentsService.findById(id);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentDTO> addComment(@Valid @RequestBody AddCommentDTO addCommentDTO) {
        return ResponseEntity.status(201)
                             .body(commentsService.addComment(addCommentDTO));
    }

    @PutMapping
    public GetCommentDTO putUpdate(@Valid @RequestBody PutCommentDTO putCommentDTO) {
        return commentsService.putUpdate(putCommentDTO);
    }

    @DeleteMapping
    public GetCommentDTO delete(@RequestParam Long id) {
        return commentsService.deleteById(id);
    }
}
