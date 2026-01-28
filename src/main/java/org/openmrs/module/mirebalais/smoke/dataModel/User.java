package org.openmrs.module.mirebalais.smoke.dataModel;

import org.openmrs.module.mirebalais.smoke.helper.Security;

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

    private Integer providerRoleId;

    private String personNameUuid;

    private String userUuid;

    private String defaultLocale;

    public User(BigInteger personId, String uuid, BigInteger personNameId, BigInteger userId, String username, String role,
                BigInteger providerId, String providerUuid, Integer providerRoleId, String personNameUuid, String userUuid,
                String defaultLocale) {
        this.personId = personId;
        this.uuid = uuid;
        this.personNameId = personNameId;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.providerId = providerId;
        this.providerUuid = providerUuid;
        this.providerRoleId = providerRoleId;
        this.personNameUuid = personNameUuid;
        this.userUuid = userUuid;
        this.defaultLocale = defaultLocale;
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
        return providerRoleId;
    }

    public String getPerson_name_uuid() {
        return personNameUuid;
    }

    public String getUser_uuid() {
        return userUuid;
    }

    public String getDefaultLocale() { return defaultLocale; }

    public String getHashedPassword() {
        return Security.encodeString(getPassword() + getSalt());
    }

    public String getSalt() {
        return "be96aa653dc0b06fe18eb4cfc6b8c8878cac9973521b24bb95ff93e0945151897e39af881f7ba58317ba14c74d843ab166738756f56b4edf39977dc89798f379";
    }

    public String getSecretQuestion() {
        return "Re-enter password";
    }

    public String getHashedSecretAnswer() {
        return Security.encodeString(getPassword().toLowerCase() + getSalt());
    }

    /**
     * Convenience method to convert a byte array to a string
     *
     * @param block Byte array to convert to HexString
     * @return Hexadecimal string encoding the byte array
     */
    private static String hexString(byte[] block) {
        StringBuilder buf = new StringBuilder();
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        int high;
        int low;
        for (byte aBlock : block) {
            high = ((aBlock & 0xf0) >> 4);
            low = (aBlock & 0x0f);
            buf.append(hexChars[high]);
            buf.append(hexChars[low]);
        }

        return buf.toString();
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
