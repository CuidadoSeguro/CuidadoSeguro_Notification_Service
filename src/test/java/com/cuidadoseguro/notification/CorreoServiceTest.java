
package com.cuidadoseguro.notification;

import java.util.Base64;
import java.util.Properties;

import com.cuidadoseguro.notification.dto.CorreoConAdjuntoDTO;
import com.cuidadoseguro.notification.dto.CorreoDTO;
import com.cuidadoseguro.notification.service.CorreoService;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CorreoServiceTest {

    private JavaMailSender mailSender;
    private CorreoService correoService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        correoService = new CorreoService(mailSender);
    }

    @Test
    void debePrepararYEnviarCorreo() {
        CorreoDTO correo = new CorreoDTO();
        correo.setDestinatario("prueba@example.com");
        correo.setAsunto("Prueba Cuidado Seguro");
        correo.setMensaje("Este es un correo de prueba.");

        correoService.enviarCorreo(correo);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void debePropagarErrorCuandoFallaElEnvio() {
        CorreoDTO correo = new CorreoDTO();
        correo.setDestinatario("prueba@example.com");
        correo.setAsunto("Prueba");
        correo.setMensaje("Mensaje de prueba.");

        doThrow(new org.springframework.mail.MailSendException(
                "Error simulado"))
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        IllegalStateException error = assertThrows(
                IllegalStateException.class,
                () -> correoService.enviarCorreo(correo)
        );

        assertEquals(
                "No fue posible enviar el correo electrónico.",
                error.getMessage()
        );
    }

    @Test
    void debeEnviarCorreoConPdfAdjunto() throws Exception {
        MimeMessage mensaje = new MimeMessage(
                Session.getInstance(new Properties()));
        when(mailSender.createMimeMessage()).thenReturn(mensaje);

        CorreoConAdjuntoDTO correo = new CorreoConAdjuntoDTO();
        correo.setRequestId("solicitud-123");
        correo.setCorreoDestino("paciente@example.com");
        correo.setNombre("Paciente de prueba");
        correo.setNombreArchivo("ficha-clinica.pdf");
        correo.setContenidoBase64(Base64.getEncoder()
                .encodeToString("pdf de prueba".getBytes()));

        correoService.enviarCorreoConAdjunto(correo);

        verify(mailSender).send(mensaje);
    }

    @Test
    void debeRechazarContenidoPdfInvalido() {
        CorreoConAdjuntoDTO correo = new CorreoConAdjuntoDTO();
        correo.setContenidoBase64("no-es-base64");

        IllegalStateException error = assertThrows(
                IllegalStateException.class,
                () -> correoService.enviarCorreoConAdjunto(correo)
        );

        assertEquals(
                "No fue posible enviar el correo electrónico con el PDF adjunto.",
                error.getMessage()
        );
    }
}
