package org.openmrs.module.mirebalais.smoke.dataModel;

import java.math.BigInteger;

public class User {

    private final BigInteger personNameId;

    private final BigInteger userId;

    private final String username;

    private String role;

    private BigInteger personId;

    private String uuid;

    public User(BigInteger personId, String uuid, BigInteger personNameId, BigInteger userId, String username, String role) {
        this.personId = personId;
        this.uuid = uuid;
        this.personNameId = personNameId;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public BigInteger getId() {
        return personId;
    }

    public String getUuid() {
        return uuid;
    }

    public BigInteger getPerson_name_id() {
        return personNameId;
    }

    public BigInteger getUser_id() {
        return userId;
    }

    public String getSystem_id() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" + "personNameId=" + personNameId + ", userId=" + userId + ", username='" + username +
                     ", personId=" + personId + ", uuid='" + uuid + '\'' + '}';
    }

    public String getPassword() {
        return "Admin123";
    }

}
