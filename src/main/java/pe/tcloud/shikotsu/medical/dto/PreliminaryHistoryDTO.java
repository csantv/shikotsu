package pe.tcloud.shikotsu.medical.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreliminaryHistoryDTO {
    private UUID preliminaryHistoryId;
    private UUID dentalChartId;
    private UUID patientId;
    private JsonNode dentalChartData;
}
