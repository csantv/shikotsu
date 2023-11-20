package pe.tcloud.shikotsu.finances.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.finances.dto.NewServiceDTO;
import pe.tcloud.shikotsu.finances.model.Service;
import pe.tcloud.shikotsu.finances.repository.ServiceRepository;
import pe.tcloud.shikotsu.medical.repository.TeethStatusRepository;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceWriteController {
    private final ServiceRepository serviceRepository;
    private final TeethStatusRepository teethStatusRepository;

    public ServiceWriteController(ServiceRepository serviceRepository,
                                  TeethStatusRepository teethStatusRepository) {
        this.serviceRepository = serviceRepository;
        this.teethStatusRepository = teethStatusRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<Service> createNew(@AuthenticationPrincipal CustomUser currentUser, @RequestBody NewServiceDTO dto) {
        var service = new Service();
        if (dto.getTeethStatusId() != null) {
            var teethStatus = teethStatusRepository.findById(dto.getTeethStatusId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND));
            service.setTeethStatus(teethStatus);
        }
        service.setCompany(currentUser.getCompany());
        service.setPrice(dto.getPrice());
        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setMaxDiscount(dto.getMaxDiscount());
        service = serviceRepository.save(service);
        return ResponseEntity.ok(service);
    }
}
