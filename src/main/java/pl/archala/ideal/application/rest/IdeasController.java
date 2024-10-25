package pl.archala.ideal.application.rest;

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
import pl.archala.ideal.application.rest.dto.in.SaveCommentRequest;
import pl.archala.ideal.application.rest.dto.out.GetCommentResponse;
import pl.archala.ideal.application.rest.dto.in.SaveIdeaRequest;
import pl.archala.ideal.application.rest.dto.out.GetIdeaResponse;
import pl.archala.ideal.application.rest.dto.in.SaveRealizationRequest;
import pl.archala.ideal.application.rest.dto.out.GetRealizationResponse;
import pl.archala.ideal.domain.enums.IdeaCategory;
import pl.archala.ideal.domain.service.interfaces.IdeasService;

import java.util.List;

@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
@Validated
public class IdeasController {

    private final IdeasService ideasService;

    @GetMapping("/details/{id}")
    public GetIdeaResponse getById(@PathVariable Long id) {
        return ideasService.findById(id);
    }

    @GetMapping("/categories")
    public List<IdeaCategory> getAvailableIdeaCategories() {
        return List.of(IdeaCategory.values());
    }

    @PostMapping
    public ResponseEntity<GetIdeaResponse> save(@Valid @RequestBody SaveIdeaRequest ideaDTO) {
        return ResponseEntity.status(201)
                             .body(ideasService.save(ideaDTO));
    }

    @PostMapping("/comment")
    public ResponseEntity<GetCommentResponse> addComment(@Valid @RequestBody SaveCommentRequest saveCommentRequest) {
        return ResponseEntity.status(201)
                             .body(ideasService.addComment(saveCommentRequest));
    }

    @PostMapping("/realization")
    public ResponseEntity<GetRealizationResponse> addRealization(@Valid @RequestBody SaveRealizationRequest saveRealizationRequest) {
        return ResponseEntity.status(201)
                             .body(ideasService.addRealization(saveRealizationRequest));
    }

    @GetMapping("/page")
    public List<GetIdeaResponse> get(
            @RequestParam(defaultValue = "0") @Min(message = "Page number must be equal or greater than 0", value = 0) int pageNumber,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "Page length must be equal or greater than 1")
            @Max(value = 200, message = "Page length must be equal or less than 200") int pageLength,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") @NotBlank(message = "Sort field must not be blank") String sortField) {
        return ideasService.getPage(PageRequest.of(pageNumber, pageLength, Sort.by(direction, sortField)));
    }

    @DeleteMapping
    public GetIdeaResponse deleteById(@RequestParam Long id) {
        return ideasService.deleteById(id);
    }

}
