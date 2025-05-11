package edu.ucaldas.back.models.user;

/**
 * Enum representing different types of users in the system.
 * Each type of user is associated with a specific string value.
 */
public enum TypeUser {

    /**
     * Represents an owner user type.
     */
    OWNER("owner"),

    /**
     * Represents a tenant user type.
     */
    CLIENT("client"),

    /**
     * Represents an admin user type.
     */
    ADMIN("admin");

    private String typeUser;

    /**
     * Constructor for the TypeUser enum.
     *
     * @param typeUser The string representation of the user type.
     */
    TypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    /**
     * Converts a string to its corresponding TypeUser enum value.
     *
     * @param typeUser The string representation of the user type.
     * @return The corresponding TypeUser enum value, or null if no match is found.
     */
    public String fromString(String typeUser) {
        for (TypeUser type : TypeUser.values()) {
            if (type.typeUser.equalsIgnoreCase(typeUser)) {
                return type.typeUser;
            }
        }
        return null;
    }
}
