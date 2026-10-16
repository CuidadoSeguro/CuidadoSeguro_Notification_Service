
package com.cuidadoseguro.notification.service;

import com.cuidadoseguro.notification.dto.CorreoDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    private final JavaMailSender mailSender;

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreo(CorreoDTO correo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();

            mensaje.setTo(correo.getDestinatario());
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getMensaje());

            mailSender.send(mensaje);

        } catch (MailException ex) {
            throw new IllegalStateException(
                    "No fue posible enviar el correo electrónico.", ex
            );
        }
    }
}
