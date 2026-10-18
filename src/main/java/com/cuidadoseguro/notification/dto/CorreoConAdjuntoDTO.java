package com.cuidadoseguro.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CorreoConAdjuntoDTO {

    @NotBlank(message = "La solicitud es obligatoria")
    @Size(max = 100, message = "La solicitud no puede superar 100 caracteres")
    private String requestId;

    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "El destinatario debe ser un correo válido")
    @Size(max = 254, message = "El correo no puede superar 254 caracteres")
    private String correoDestino;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no puede superar 255 caracteres")
    private String nombreArchivo;

    @NotBlank(message = "El contenido del archivo es obligatorio")
    @Size(max = 14_000_000, message = "El archivo adjunto supera el tamaño permitido")
    private String contenidoBase64;

    public CorreoConAdjuntoDTO() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCorreoDestino() {
        return correoDestino;
    }

    public void setCorreoDestino(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContenidoBase64() {
        return contenidoBase64;
    }

    public void setContenidoBase64(String contenidoBase64) {
        this.contenidoBase64 = contenidoBase64;
    }
}
