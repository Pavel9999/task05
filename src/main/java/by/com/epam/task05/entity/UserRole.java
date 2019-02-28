package by.com.epam.task05.entity;

public enum UserRole {
    GUEST,
    CLIENT,
    MANAGER,
    ADMIN;

    public static UserRole fromInteger(int value) {
        switch(value) {
            case 1:
                return UserRole.GUEST;
            case 2:
                return UserRole.CLIENT;
            case 3:
                return UserRole.MANAGER;
            case 4:
                return UserRole.ADMIN;
            default: return UserRole.GUEST;
        }
    }
}
