package com.inoastrum.pharmaroleservice.exceptions;

public class RoleNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Role that you're looking for doesn't exist";
    }
}
