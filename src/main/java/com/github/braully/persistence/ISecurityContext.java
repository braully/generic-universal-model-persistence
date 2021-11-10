package com.github.braully.persistence;

public interface ISecurityContext {

    IUser getUser();

    boolean hasRole(String string);

    boolean isLoggedIn();

}
