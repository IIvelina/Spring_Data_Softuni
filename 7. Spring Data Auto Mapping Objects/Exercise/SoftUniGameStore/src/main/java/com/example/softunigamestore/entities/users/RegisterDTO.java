package com.example.softunigamestore.entities.users;


import com.example.softunigamestore.exections.ValidationException;

public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    /**
     * Validate the data for registering a user
     * Email must be...
     * Password must be...
     * commandParts[0] is skipped because it contains the command name
     * which is not needed in the user object
     * @param commandParts all data read from the console
     */
    public RegisterDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];
        this.confirmPassword = commandParts[3];
        this.fullName = commandParts[4];

        this.validate();
    }

    private void validate() {

        int indexOfAt = email.indexOf("@");
        int indexOfDot = email.lastIndexOf(".");
            if (indexOfAt < 0 || indexOfDot < 0 || indexOfAt > indexOfDot){
                throw new ValidationException("Email must contain @ and .");
            }


        if (password.length() < 6) {
            throw new ValidationException("Password length must be at least 6 characters.");
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        if (!hasUpperCase || !hasLowerCase || !hasDigit) {
            throw new ValidationException("Password must contain at least one uppercase letter, one lowercase letter, and one digit.");
        }


        if (!password.equals(confirmPassword)){
            throw new ValidationException("The passwords do not match.");
        }


        boolean isNameFill = false;
        if ("".equals(fullName)){
            throw new ValidationException("You must fill your name");
        }


    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }
}
