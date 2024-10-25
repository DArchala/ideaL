package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.SaveCommentRequest;
import pl.archala.ideal.dto.comment.GetCommentResponse;
import pl.archala.ideal.dto.comment.UpdateCommentRequest;
import pl.archala.ideal.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentsService commentsService;

    @GetMapping("/details/{id}")
    public GetCommentResponse getById(@PathVariable Long id) {
        return commentsService.findById(id);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentResponse> addComment(@Valid @RequestBody SaveCommentRequest saveCommentRequest) {
        return ResponseEntity.status(201)
                             .body(commentsService.addComment(saveCommentRequest));
    }

    @PutMapping
    public GetCommentResponse putUpdate(@Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        return commentsService.putUpdate(updateCommentRequest);
    }

    @DeleteMapping
    public GetCommentResponse delete(@RequestParam Long id) {
        return commentsService.deleteById(id);
    }
}
