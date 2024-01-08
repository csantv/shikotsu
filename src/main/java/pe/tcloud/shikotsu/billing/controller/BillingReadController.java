package pe.tcloud.shikotsu.billing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.billing.service.SunatService;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingReadController {
    private final SunatService sunatService;

    public BillingReadController(SunatService sunatService) {
        this.sunatService = sunatService;
    }

    @PostMapping("/generatePdf")
    public ResponseEntity<String> generateBillPdf() {
        sunatService.testRequest();
        return ResponseEntity.ok("");
    }
}
