
package com.cuidadoseguro.notification.controller;

import com.cuidadoseguro.notification.dto.CorreoDTO;
import com.cuidadoseguro.notification.service.CorreoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/correos")
public class CorreoController {

    private final CorreoService correoService;

    public CorreoController(CorreoService correoService) {
        this.correoService = correoService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, String>> enviarCorreo(
            @Valid @RequestBody CorreoDTO correo) {

        correoService.enviarCorreo(correo);

        return ResponseEntity.status(HttpStatus.OK).body(
                Map.of(
                        "estado", "ENVIADO",
                        "mensaje", "Correo enviado correctamente."
                )
        );
    }
}
