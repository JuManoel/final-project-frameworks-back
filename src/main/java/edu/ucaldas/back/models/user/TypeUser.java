package edu.ucaldas.back.models.user;


/**
 * The {@code TypeUser} enum represents the different types of users in the system.
 * Each enum constant is associated with a string value that identifies the user type.
 * <p>
 * Available user types:
 * <ul>
 *   <li>{@link #OWNER} - Represents an owner user.</li>
 *   <li>{@link #CLIENT} - Represents a client user.</li>
 *   <li>{@link #ADMIN} - Represents an admin user.</li>
 * </ul>
 * </p>
 */
public enum TypeUser {

    OWNER("owner"),

    CLIENT("client"),

    ADMIN("admin");

    private String typeUser;

    TypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    /**
     * Returns the string representation of a TypeUser enum constant that matches the given string,
     * ignoring case considerations.
     *
     * @param typeUser the string to match against the typeUser field of the enum constants
     * @return the matching typeUser string if found; otherwise, {@code null}
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
