package com.cuidadoseguro.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class CorreoDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    @Email (message = "El destinatario debe ser un correo válido")
    @Size(max = 254, message = "El correo no puede superar 254 caracteres")
    private String destinatario;

    @NotBlank (message = "El asunto es obligatorio")
    @Size(max = 150, message = "El asunto no puede superar 150 caracteres")
    private String asunto;

    @NotBlank (message = "El mensaje es obligatorio")
    @Size(max = 5000, message = "El mensaje no puede superar 5000 caracteres")
    private String mensaje;

    public CorreoDTO() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}