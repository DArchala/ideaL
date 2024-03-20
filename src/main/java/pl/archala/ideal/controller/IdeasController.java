package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.comment.AddCommentDTO;
import pl.archala.ideal.dto.comment.GetCommentDTO;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.enums.IdeaCategory;
import pl.archala.ideal.service.interfaces.IdeasService;

import java.util.List;

@RestController
@RequestMapping("/api/idea")
@RequiredArgsConstructor
@Validated
public class IdeasController {

    private final IdeasService ideasService;

    @GetMapping("/details/{id}")
    public GetIdeaDTO getById(@PathVariable Long id) {
        return ideasService.findById(id);
    }

    @GetMapping("/categories")
    public List<IdeaCategory> getAvailableIdeaCategories() {
        return List.of(IdeaCategory.values());
    }

    @PostMapping
    public ResponseEntity<GetIdeaDTO> save(@Valid @RequestBody AddIdeaDTO ideaDTO) {
        return ResponseEntity.status(201).body(ideasService.save(ideaDTO));
    }

    @PostMapping("/add-comment")
    public ResponseEntity<GetCommentDTO> addComment(@Valid @RequestBody AddCommentDTO addCommentDTO) {
        return ResponseEntity.status(201).body(ideasService.addComment(addCommentDTO));
    }

    @GetMapping("/page")
    public List<GetIdeaDTO> get(
            @RequestParam(defaultValue = "0") @Min(message = "Page number must be equal or greater than 0", value = 0) int pageNumber,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "Page length must be equal or greater than 1")
            @Max(value = 200, message = "Page length must be equal or less than 200") int pageLength,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") @NotBlank(message = "Sort field must not be blank") String sortField) {
        return ideasService.getPage(PageRequest.of(pageNumber, pageLength, Sort.by(direction, sortField)));
    }

    @DeleteMapping
    public GetIdeaDTO deleteById(@RequestParam Long id) {
        return ideasService.deleteById(id);
    }

}
