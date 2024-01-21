package pl.archala.ideal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.archala.ideal.dto.realization.AddRealizationDTO;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.service.interfaces.RealizationsService;

@RestController
@RequestMapping("/api/realization")
@RequiredArgsConstructor
@Validated
public class RealizationsController {

    private final RealizationsService realizationsService;

    @GetMapping
    public ResponseEntity<GetRealizationDTO> getById(@RequestParam Long id) {
        GetRealizationDTO dto = realizationsService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<GetRealizationDTO> save(@Valid @RequestBody AddRealizationDTO addRealizationDTO) {
        GetRealizationDTO dto = realizationsService.save(addRealizationDTO);
        return ResponseEntity.status(201).body(dto);
    }

}
