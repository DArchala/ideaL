package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.AddCommentCommentDTO;
import pl.archala.ideal.dto.comment.AddIdeaCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.service.interfaces.CommentsService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping
    public ResponseEntity<GetCommentDTO> getById(@RequestParam Long id) {
        GetCommentDTO getCommentDTO = commentsService.findById(id);
        return ResponseEntity.ok(getCommentDTO);
    }

    @PostMapping("/idea")
    public ResponseEntity<GetCommentDTO> saveIdeaComment(@Valid @RequestBody AddIdeaCommentDTO addIdeaCommentDTO) {
        GetCommentDTO getCommentDTO = commentsService.save(addIdeaCommentDTO);
        return ResponseEntity.status(201).body(getCommentDTO);
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentDTO> saveCommentComment(@Valid @RequestBody AddCommentCommentDTO addIdeaCommentDTO) {
        GetCommentDTO getCommentDTO = commentsService.save(addIdeaCommentDTO);
        return ResponseEntity.status(201).body(getCommentDTO);
    }

    @DeleteMapping
    public ResponseEntity<GetCommentDTO> delete(@RequestParam Long id) {
        GetCommentDTO dto = commentsService.deleteById(id);
        return ResponseEntity.ok(dto);
    }
}
