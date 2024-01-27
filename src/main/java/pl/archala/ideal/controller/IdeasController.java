package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<GetIdeaDTO> getById(@RequestParam Long id) {
        GetIdeaDTO ideaDTO = ideasService.findById(id);
        return ResponseEntity.ok(ideaDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<IdeaCategory>> getAvailableIdeaCategories() {
        return ResponseEntity.ok(List.of(IdeaCategory.values()));
    }

    @GetMapping("/random")
    public ResponseEntity<GetIdeaDTO> getRandomIdea(@RequestParam(required = false) IdeaCategory category) {
        GetIdeaDTO getIdeaDTO = ideasService.getRandom(category);
        return ResponseEntity.ok(getIdeaDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetIdeaDTO> save(@Valid @RequestBody AddIdeaDTO ideaDTO) {
        GetIdeaDTO savedIdeaDTO = ideasService.save(ideaDTO);
        return ResponseEntity.status(201).body(savedIdeaDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<List<GetIdeaDTO>> get(
            @RequestParam(defaultValue = "0") @Min(message = "Page number must be equal or greater than 0", value = 0) int pageNumber,
            @RequestParam(defaultValue = "20") @Min(value = 1, message = "Page length must be equal or greater than 1")
            @Max(value = 200, message = "Page length must be equal or less than 200") int pageLength,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") @NotBlank(message = "Sort field must not be blank") String sortField) {
        Sort sort = Sort.by(direction, sortField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageLength, sort);
        List<GetIdeaDTO> ideaDTOS = ideasService.getPage(pageRequest);
        return ResponseEntity.ok(ideaDTOS);
    }

    @DeleteMapping
    public ResponseEntity<GetIdeaDTO> delete(@RequestParam Long id) {
        GetIdeaDTO dto = ideasService.deleteById(id);
        return ResponseEntity.ok(dto);
    }

}
