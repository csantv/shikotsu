package pe.tcloud.shikotsu.finances.dto;

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
public class NewServiceDTO {
    private BigInteger price;
    private BigInteger maxDiscount;
    private String title;
    private String description;
    private UUID teethStatusId;
}
