package pl.archala.ideal.application.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.in.UpdateCommentRequest;
import pl.archala.ideal.domain.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/details/{id}")
    public GetCommentResponse findById(@PathVariable Long id) {
        return commentsService.findById(id);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentResponse> save(@Valid @RequestBody SaveCommentRequest saveCommentRequest) {
        return ResponseEntity.status(201)
                             .body(commentsService.addComment(saveCommentRequest));
    }

    @PutMapping
    public GetCommentResponse update(@Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        return commentsService.putUpdate(updateCommentRequest);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteById(@RequestParam Long id) {
        commentsService.deleteById(id);
    }
}
