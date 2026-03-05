package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.forohub.domain.topico.dto.DatosRegistroTopico;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private StatusTopico status;
    private String autor;
    private String curso;

    public Topico(DatosRegistroTopico datos) {
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = StatusTopico.PENDIENTE;
        this.autor = datos.autor();
        this.curso = datos.curso();
    }

    public void actualizarTopico(DatosActualizarTopico datos) {
        boolean actualizado = false;

        if(datos.titulo() != null) {
            this.titulo = datos.titulo();
            actualizado = true;
        }
        if(datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
            actualizado = true;
        }
        if(datos.autor() != null) {
            this.titulo = datos.titulo();
            actualizado = true;
        }
        if(datos.curso() != null) {
            this.curso = datos.curso();
            actualizado = true;
        }

        if(actualizado) {
            this.status = StatusTopico.ACTUALIZADO;
        }
    }
}
