package com.bdxh.librarybase.utils;

public class ResponseException extends Exception {

    public int code;
    public String displayMsg;

    public ResponseException() {

    }

    public ResponseException(int code, String displayMsg) {
        this.code = code;
        this.displayMsg = displayMsg;
    }

    public ResponseException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ResponseException(String message, int code, String displayMsg) {
        super(message);
        this.code = code;
        this.displayMsg = displayMsg;
    }


    public int getCode() {
        return code;
    }

    public String getDisplayMsg() {
        return displayMsg;
    }

}
