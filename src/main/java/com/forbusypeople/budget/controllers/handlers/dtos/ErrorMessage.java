package com.forbusypeople.budget.controllers.handlers.dtos;

public class ErrorMessage {

    private String errorCode;
    private String errorDescription;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public static final class ErrorMessageBuilder {
        private String errorCode;
        private String errorDescription;

        private ErrorMessageBuilder() {
        }

        public static ErrorMessageBuilder anErrorMessage() {
            return new ErrorMessageBuilder();
        }

        public ErrorMessageBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorMessageBuilder withErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
            return this;
        }

        public ErrorMessage build() {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setErrorCode(errorCode);
            errorMessage.setErrorDescription(errorDescription);
            return errorMessage;
        }
    }
}
