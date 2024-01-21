package pl.archala.ideal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping
    public ResponseEntity<GetCommentDTO> getById(@RequestParam Long id) {
        GetCommentDTO getCommentDTO = commentsService.findById(id);
        return ResponseEntity.ok(getCommentDTO);
    }

    @PostMapping
    public ResponseEntity<GetCommentDTO> save(@RequestBody AddCommentDTO addCommentDTO) {
        GetCommentDTO getCommentDTO = commentsService.save(addCommentDTO);
        return ResponseEntity.status(201).body(getCommentDTO);
    }
}
