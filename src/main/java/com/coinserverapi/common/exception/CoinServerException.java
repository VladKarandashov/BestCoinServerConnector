package com.coinserverapi.common.exception;

import lombok.Getter;

@Getter
public class CoinServerException extends RuntimeException {

    private final int statusCode;

    public CoinServerException(ExceptionBasis basis) {
        super(basis.name());
        this.statusCode = basis.getStatusCode();
    }

    public CoinServerException(ExceptionBasis basis, Exception e) {
        super(basis.name(), e);
        this.statusCode = basis.getStatusCode();
    }
}