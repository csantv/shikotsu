package pe.tcloud.shikotsu.finances.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.finances.dto.InvoiceDTO;
import pe.tcloud.shikotsu.finances.model.Invoice;
import pe.tcloud.shikotsu.finances.repository.InvoiceRepository;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceWriteController {
    private final InvoiceRepository invoiceRepository;

    public InvoiceWriteController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping("/update")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody InvoiceDTO dto) {
        var invoice = invoiceRepository.findById(dto.getInvoiceId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        invoice.setLines(dto.getLines());
        invoice.setTotal(dto.getTotal());
        invoice = invoiceRepository.save(invoice);
        return ResponseEntity.ok(invoice);
    }
}
