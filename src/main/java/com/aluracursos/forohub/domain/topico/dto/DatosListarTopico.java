package com.aluracursos.forohub.domain.topico.dto;

import com.aluracursos.forohub.domain.topico.StatusTopico;
import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.domain.user.Usuario;

import java.time.LocalDateTime;

public record DatosListarTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        StatusTopico status,
        Usuario autor,
        String curso
) {
    public DatosListarTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
