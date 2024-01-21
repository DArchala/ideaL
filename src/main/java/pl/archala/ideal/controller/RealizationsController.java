package pl.archala.ideal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.archala.ideal.dto.realization.GetRealizationDTO;
import pl.archala.ideal.service.interfaces.RealizationsService;

@RestController
@RequestMapping("/api/realization")
@RequiredArgsConstructor
public class RealizationsController {

    private final RealizationsService realizationsService;

    @GetMapping
    public ResponseEntity<GetRealizationDTO> getById(@RequestParam Long id) {
        GetRealizationDTO dto = realizationsService.findById(id);
        return ResponseEntity.ok(dto);
    }

}
