package pe.tcloud.shikotsu.finances.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private UUID invoiceId;
    private BigInteger total;
    private JsonNode lines;
}
