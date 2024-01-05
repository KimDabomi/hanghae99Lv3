package org.sparta.hanghae99lv3.entity;

public enum StaffAuthEnum {
    STAFF(Authority.STAFF),
    ADMIN(Authority.ADMIN);

    private final String authority;

    StaffAuthEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String STAFF = "AUTH_STAFF";
        public static final String ADMIN = "AUTH_ADMIN";
    }
}
