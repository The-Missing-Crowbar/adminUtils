package com.themissingcrowbar.adminUtils;

import java.sql.SQLException;

public class NotAWarningException extends Exception {
    public NotAWarningException (String message) {
        super(message);
    }
}
