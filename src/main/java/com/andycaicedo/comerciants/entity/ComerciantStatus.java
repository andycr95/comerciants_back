package com.andycaicedo.comerciants.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ComerciantStatus {
    REGISTRED("registrado"), ACTIVE("activo"), INACTIVE("inactivo"), SUSPENDED("suspendido"), CANCELED("cancelado");

    private final String comerciantStatus;

    @JsonValue
    public String getComercientStatus() {
        return comerciantStatus;
    }

    @JsonCreator
    public static ComerciantStatus fromValue(String value) {
        for (ComerciantStatus comerciantStatus : values()) {
            String currentStatus = comerciantStatus.getComercientStatus();
            if (currentStatus.equals(value)) {
                return comerciantStatus;
            }
        }
        throw new IllegalArgumentException("Invalid value, status not authorized: " + value);
    }
}
