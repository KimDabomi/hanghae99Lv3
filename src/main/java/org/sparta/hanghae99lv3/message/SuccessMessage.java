package org.sparta.hanghae99lv3.message;

public enum SuccessMessage {
    JOIN_SUCCESS_MESSAGE("회원가입이 완료되었습니다."),
    LOGIN_SUCCESS_MESSAGE("Your login has been completed."),
    DELETE_SUCCESS_MESSAGE("삭제가 완료되었습니다.");

    private final String successMessage;

    SuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return "[SUCCESS] " + successMessage;
    }

}
