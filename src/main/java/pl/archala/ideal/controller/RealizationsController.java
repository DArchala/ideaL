package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.service.interfaces.RealizationsService;

import java.util.List;

@RestController
@RequestMapping("/api/realization")
@RequiredArgsConstructor
@Validated
public class RealizationsController {

    private final RealizationsService realizationsService;

    @GetMapping("/details/{id}")
    public GetRealizationDTO findById(@PathVariable Long id) {
        return realizationsService.findById(id);
    }

    @GetMapping("/by-idea")
    public List<GetRealizationDTO> findByIdeaId(@RequestParam Long ideaId) {
        return realizationsService.findAllByIdeaId(ideaId);
    }

    @PostMapping
    public ResponseEntity<GetRealizationDTO> save(@Valid @RequestBody AddRealizationDTO addRealizationDTO) {
        return ResponseEntity.status(201).body(realizationsService.save(addRealizationDTO));
    }

}
