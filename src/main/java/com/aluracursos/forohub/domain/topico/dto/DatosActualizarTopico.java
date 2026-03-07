package com.aluracursos.forohub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(
        String titulo,
        String mensaje,
        String curso
) {
}
