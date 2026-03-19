package com.chad.sales.exception;

import java.util.List;

public class ErrorResponse {

    private int status;
    private List<String> erros;

    public ErrorResponse(int status, List<String> erros) {
        this.status = status;
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErros() {
        return erros;
    }
}