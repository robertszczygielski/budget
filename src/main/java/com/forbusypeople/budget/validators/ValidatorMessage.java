package com.forbusypeople.budget.validators;

class ValidatorMessage {

    private StringBuilder message = new StringBuilder();
    private StringBuilder code = new StringBuilder();

    public void setMessage(String message) {
        if (this.message.length() > 1) {
            this.message.append("; ");
        }
        this.message.append(message);
    }

    public void setCode(String code) {
        if (this.code.length() > 1) {
            this.code.append("; ");
        }
        this.code.append(code);
    }

    public String getMessage() {
        return message.toString();
    }

    public String getCode() {
        return code.toString();
    }
}
