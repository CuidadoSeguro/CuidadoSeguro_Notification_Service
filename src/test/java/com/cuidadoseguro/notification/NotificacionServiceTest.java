
package com.cuidadoseguro.notification;

import com.cuidadoseguro.notification.dto.CrearNotificacionDTO;
import com.cuidadoseguro.notification.dto.NotificacionDTO;
import com.cuidadoseguro.notification.service.NotificacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionServiceTest {

    private NotificacionService service;

    @BeforeEach
    void setUp() {
        service = new NotificacionService();
    }

    @Test
    void debeCrearNotificacionNoLeida() {
        CrearNotificacionDTO datos = new CrearNotificacionDTO();
        datos.setDestinatario("usuario-prueba");
        datos.setTipo("PACIENTE_REGISTRADO");
        datos.setTitulo("Nuevo paciente");
        datos.setMensaje("Se registró un paciente.");

        NotificacionDTO resultado = service.crear(datos);

        assertNotNull(resultado.getId());
        assertEquals("usuario-prueba", resultado.getDestinatario());
        assertEquals("NO_LEIDA", resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
    }

    @Test
    void debeListarNotificacionesPorDestinatario() {
        CrearNotificacionDTO datos = new CrearNotificacionDTO();
        datos.setDestinatario("usuario-prueba");
        datos.setTipo("PACIENTE_ACTUALIZADO");
        datos.setTitulo("Paciente actualizado");
        datos.setMensaje("Se actualizaron sus datos.");

        service.crear(datos);

        List<NotificacionDTO> resultado =
                service.listarPorDestinatario("usuario-prueba");

        assertEquals(1, resultado.size());
    }

    @Test
    void debeMarcarNotificacionComoLeida() {
        CrearNotificacionDTO datos = new CrearNotificacionDTO();
        datos.setDestinatario("usuario-prueba");
        datos.setTipo("PACIENTE_REGISTRADO");
        datos.setTitulo("Nuevo paciente");
        datos.setMensaje("Se registró un paciente.");

        NotificacionDTO creada = service.crear(datos);

        NotificacionDTO resultado =
                service.marcarComoLeida(creada.getId());

        assertEquals("LEIDA", resultado.getEstado());
        assertNotNull(resultado.getFechaLectura());
    }

    @Test
    void debeRechazarIdInexistenteAlMarcarComoLeida() {
        UUID idInexistente = UUID.randomUUID();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.marcarComoLeida(idInexistente)
        );
    }
}
