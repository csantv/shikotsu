package pe.tcloud.shikotsu.user.dto;

import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.Person;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.UUID;

public record LoggedInUserDTO (
        UserAccount user,
        Person person,
        Company company,
        UUID roleId
)
{ }
