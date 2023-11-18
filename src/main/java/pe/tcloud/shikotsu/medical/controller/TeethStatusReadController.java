package pe.tcloud.shikotsu.medical.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.medical.model.TeethStatus;
import pe.tcloud.shikotsu.medical.repository.TeethStatusRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teeth-status")
public class TeethStatusReadController {
    private final TeethStatusRepository teethStatusRepository;

    public TeethStatusReadController(TeethStatusRepository teethStatusRepository) {
        this.teethStatusRepository = teethStatusRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeethStatus>> getAll() {
        var result = teethStatusRepository.findAll();
        return ResponseEntity.ok(result);
    }
}
