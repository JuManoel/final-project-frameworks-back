package edu.ucaldas.back.models.user;

public enum TypeUser {
    OWNER("owner"),
    TENANT("tenant"),
    ADMIN("admin");

    private String typeUser;

    TypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String fromString(String typeUser) {
        for (TypeUser type : TypeUser.values()) {
            if (type.typeUser.equalsIgnoreCase(typeUser)) {
                return type.typeUser;
            }
        }
        return null;
    }
}
