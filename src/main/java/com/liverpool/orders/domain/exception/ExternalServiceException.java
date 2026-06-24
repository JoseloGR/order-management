package com.liverpool.orders.domain.exception;

public class ExternalServiceException extends RuntimeException {

    private final String serviceName;

    public ExternalServiceException(final String serviceName, final Throwable cause) {
        super("Error al comunicarse con el servicio externo: " + serviceName, cause);
        this.serviceName = serviceName;
    }

    public ExternalServiceException(final String serviceName, final String message) {
        super("Error en servicio '" + serviceName + "': " + message);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
