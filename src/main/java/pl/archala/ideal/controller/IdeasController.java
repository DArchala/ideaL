package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.idea.AddIdeaDTO;
import pl.archala.ideal.dto.idea.GetIdeaDTO;
import pl.archala.ideal.service.IdeasService;

@RestController
@RequestMapping("/api/idea")
@RequiredArgsConstructor
@Validated
public class IdeasController {

    private final IdeasService ideasService;

    @GetMapping
    public ResponseEntity<GetIdeaDTO> getById(@RequestParam Long id) {
        GetIdeaDTO ideaDTO = ideasService.getById(id);
        return ResponseEntity.ok().body(ideaDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetIdeaDTO> save(@Valid @RequestBody AddIdeaDTO ideaDTO) {
        GetIdeaDTO savedIdeaDTO = ideasService.save(ideaDTO);
        return ResponseEntity.status(201).body(savedIdeaDTO);
    }

}
