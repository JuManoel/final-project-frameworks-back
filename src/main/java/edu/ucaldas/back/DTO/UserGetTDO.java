package edu.ucaldas.back.DTO;

import edu.ucaldas.back.models.user.TypeUser;

public record UserGetTDO(
    String name,
    String email,
    float stars,
    TypeUser typeUser
) {

}
