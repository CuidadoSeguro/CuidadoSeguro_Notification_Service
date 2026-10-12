
package com.cuidadoseguro.notification.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cuidadoseguro.notification.dto.CrearNotificacionDTO;
import com.cuidadoseguro.notification.dto.NotificacionDTO;
import com.cuidadoseguro.notification.entity.Notificacion;

@Service
public class NotificacionService {

    private final List<Notificacion> notificaciones = new ArrayList<>();

    public NotificacionDTO crear(CrearNotificacionDTO datos) {
        Notificacion notificacion = new Notificacion(
                datos.getDestinatario(),
                datos.getTipo(),
                datos.getTitulo(),
                datos.getMensaje()
        );

        notificaciones.add(notificacion);

        return convertirADTO(notificacion);
    }

    public List<NotificacionDTO> listarPorDestinatario(String destinatario) {
        return notificaciones.stream()
                .filter(n -> n.getDestinatario().equals(destinatario))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public NotificacionDTO marcarComoLeida(UUID id) {
        Notificacion notificacion = buscarPorId(id);

        if (notificacion == null) {
            throw new IllegalArgumentException(
                    "No existe una notificación con el ID: " + id
            );
        }

        if ("NO_LEIDA".equals(notificacion.getEstado())) {
            notificacion.setEstado("LEIDA");
            notificacion.setFechaLectura(LocalDateTime.now());
        }

        return convertirADTO(notificacion);
    }

    public void eliminar(UUID id) {
        Notificacion notificacion = buscarPorId(id);

        if (notificacion == null) {
            throw new IllegalArgumentException(
                    "No existe una notificación con el ID: " + id
            );
        }

        notificaciones.remove(notificacion);
    }

    private Notificacion buscarPorId(UUID id) {
        return notificaciones.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private NotificacionDTO convertirADTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();

        dto.setId(n.getId());
        dto.setDestinatario(n.getDestinatario());
        dto.setTipo(n.getTipo());
        dto.setTitulo(n.getTitulo());
        dto.setMensaje(n.getMensaje());
        dto.setEstado(n.getEstado());
        dto.setFechaCreacion(n.getFechaCreacion());
        dto.setFechaLectura(n.getFechaLectura());

        return dto;
    }
}
