package com.sparkequation.spring.trial.api.report;

import java.util.HashMap;
import java.util.Map;

public class ResponseReport {

    private Map<String, String> error;
    private Map<String, String> success;

    public void addError(String key, String value) {
        if (error == null) {
            error = new HashMap<>();
        }
        this.error.put(key, value);
    }

    public void addSuccess(String key, String value) {
        if (success == null) {
            success = new HashMap<>();
        }
        this.success.put(key, value);
    }


    public Map<String, String> getError() {
        return error;
    }

    public Map<String, String> getSuccess() {
        return success;
    }
}
