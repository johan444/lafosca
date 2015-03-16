package com.example.joan.lafosca;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Joan on 03/03/2015.
 */
public class RequestPackage {

    private String uri;
    private String method;
    private Map<String, String> params = new HashMap<>();
    private String id;
    private String successCode;

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public void setID(String id) { this.id = id; }
    public String getID() { return this.id; }
    public void setSuccessCode(String code) { this.successCode = code; }
    public String getSuccessCode() { return this.successCode; }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public Map<String, String> getParams() {
        return params;
    }
    public String getParams(String key) {
        return params.get(key);
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParam(String key, String value) {
        params.put(key, value);
    }
}
