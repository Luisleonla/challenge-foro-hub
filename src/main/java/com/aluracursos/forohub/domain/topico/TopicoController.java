package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.forohub.domain.topico.dto.DatosDetalleTopico;
import com.aluracursos.forohub.domain.topico.dto.DatosListarTopico;
import com.aluracursos.forohub.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.forohub.domain.user.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {
//        Obtenemos al usuario actual para poder enviarlo como parámetro.
        var usuarioLogueado = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        var topico = new Topico(datos, usuarioLogueado);
        repository.save(topico);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarTopico>> listar(@PageableDefault(size=6, sort={"fechaDeCreacion"})Pageable paginacion) {
        var page = repository.findAll(paginacion).map(DatosListarTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListarTopico> detalleTopico(@PathVariable Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return ResponseEntity.ok(new DatosListarTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos,
            @AuthenticationPrincipal Usuario usuarioLogueado) {

        var topico = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException());

        if(!topico.getAutor().equals(usuarioLogueado)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        topico.actualizarTopico(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogueado) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        if(!topico.getAutor().equals(usuarioLogueado)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
