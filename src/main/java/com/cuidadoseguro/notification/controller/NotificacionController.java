
package com.cuidadoseguro.notification.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuidadoseguro.notification.dto.CrearNotificacionDTO;
import com.cuidadoseguro.notification.dto.NotificacionDTO;
import com.cuidadoseguro.notification.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(
            @Valid @RequestBody CrearNotificacionDTO datos) {

        NotificacionDTO notificacion = notificacionService.crear(datos);

        return ResponseEntity.status(HttpStatus.CREATED).body(notificacion);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarPorDestinatario(
            @RequestParam String destinatario) {

        return ResponseEntity.ok(
                notificacionService.listarPorDestinatario(destinatario)
        );
    }

    @PutMapping("/{id}/leida")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                notificacionService.marcarComoLeida(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {

        notificacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
