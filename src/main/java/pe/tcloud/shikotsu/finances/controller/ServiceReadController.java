package pe.tcloud.shikotsu.finances.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.finances.model.Service;
import pe.tcloud.shikotsu.finances.repository.ServiceRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceReadController {
    private final ServiceRepository serviceRepository;

    public ServiceReadController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Service>> getAll() {
        var result = serviceRepository.findAll();
        return ResponseEntity.ok(result);
    }
}
