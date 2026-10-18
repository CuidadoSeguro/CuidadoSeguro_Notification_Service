
package com.cuidadoseguro.notification.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.cuidadoseguro.notification.dto.CorreoConAdjuntoDTO;
import com.cuidadoseguro.notification.dto.CorreoDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;

@Service
public class CorreoService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correo;

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreo(CorreoDTO correo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();

            mensaje.setFrom(this.correo);
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

    public void enviarCorreoConAdjunto(CorreoConAdjuntoDTO correo) {
        try {
            byte[] contenido = Base64.getDecoder().decode(correo.getContenidoBase64());
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mensaje, true, StandardCharsets.UTF_8.name());

            if (this.correo != null && !this.correo.isBlank()) {
                helper.setFrom(this.correo);
            }
            helper.setTo(correo.getCorreoDestino());
            helper.setSubject("Documento Cuidado Seguro");
            helper.setText("Hola " + correo.getNombre()
                    + ", adjuntamos el documento solicitado.", false);
            helper.addAttachment(
                    correo.getNombreArchivo(),
                    new ByteArrayResource(contenido),
                    "application/pdf");

            mailSender.send(mensaje);
        } catch (MailException | MessagingException | IllegalArgumentException ex) {
            throw new IllegalStateException(
                    "No fue posible enviar el correo electrónico con el PDF adjunto.", ex);
        }
    }
}
