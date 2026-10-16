
package com.cuidadoseguro.notification;

import com.cuidadoseguro.notification.dto.CorreoDTO;
import com.cuidadoseguro.notification.service.CorreoService;

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
}
