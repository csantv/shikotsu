package pe.tcloud.shikotsu.user.dto;

public record PersonDTO (
        String name,
        String lastName,
        String email,
        String birthDate,
        int phoneNumber,
        int identificationNumber
) {
}
