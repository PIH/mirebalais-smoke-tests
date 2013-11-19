package org.openmrs.module.mirebalais.smoke.dataModel;

import java.math.BigInteger;

public class User {

    private final BigInteger personNameId;

    private final BigInteger userId;

    private final String username;

    private String role;

    private BigInteger personId;

    private String uuid;

    private BigInteger providerId;

    private String providerUuid;

    private Integer provider_role_id;

    public User(BigInteger personId, String uuid, BigInteger personNameId, BigInteger userId, String username, String role,
                BigInteger providerId, String providerUuid, Integer provider_role_id) {
        this.personId = personId;
        this.uuid = uuid;
        this.personNameId = personNameId;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.providerId = providerId;
        this.providerUuid = providerUuid;
        this.provider_role_id = provider_role_id;
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

    public String getProvider_uuid() {
        return providerUuid;
    }

    public BigInteger getPerson_name_id() {
        return personNameId;
    }

    public BigInteger getUser_id() {
        return userId;
    }

    public BigInteger getProvider_id() {
        return providerId;
    }

    public String getProvider_identifier() {
        return "testId" + personId;
    }

    public String getSystem_id() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public Integer getProvider_role_id() {
        return provider_role_id;
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
