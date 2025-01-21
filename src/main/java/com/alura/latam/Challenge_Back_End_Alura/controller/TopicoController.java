package com.alura.latam.Challenge_Back_End_Alura.controller;

import com.alura.latam.Challenge_Back_End_Alura.domain.curso.Curso;
import com.alura.latam.Challenge_Back_End_Alura.domain.curso.CursoRepository;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.DetalleRespuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.Respuesta;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.RespuestaRepository;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.*;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.validaciones.ValidarTopicoActualizado;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.validaciones.ValidarTopicoCreado;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.Usuario;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidarTopicoCreado> crearValidadores;

    @Autowired
    List<ValidarTopicoActualizado> actualizarValidadores;

    @PostMapping
    @Transactional
    public ResponseEntity<DetallesTopico> crearTopico(@RequestBody @Valid CrearTopico crearTopico, UriComponentsBuilder uriBuilder){
        crearValidadores.forEach(v -> v.validate(crearTopico));

        Usuario usuario = usuarioRepository.findById(crearTopico.usuarioId()).get();
        Curso curso = cursoRepository.findById(crearTopico.cursoId()).get();
        Topico topico = new Topico(crearTopico, usuario, curso);

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetallesTopico(topico));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DetallesTopico>> leerTodosTopicos(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.DESC) Pageable pageable){
        var pagina = topicoRepository.findAll(pageable).map(DetallesTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    public ResponseEntity<Page<DetallesTopico>> leerTopicosNoEliminados(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.DESC) Pageable pageable){
        var pagina = topicoRepository.findAllByEstadoIsNot(Estado.Deleted, pageable).map(DetallesTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesTopico> leerUnTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DetallesTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @GetMapping("/{id}/solucion")
    public ResponseEntity<DetalleRespuesta> leerSolucionTopico(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceByTopicoId(id);

        var datosRespuesta = new DetalleRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getSolucion(),
                respuesta.getBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetallesTopico> actualizarTopico(@RequestBody @Valid ActualizarTopico actualizarTopico, @PathVariable Long id){
        actualizarValidadores.forEach(v -> v.validate(actualizarTopico));

        Topico topico = topicoRepository.getReferenceById(id);

        if(actualizarTopico.cursoId() != null){
            Curso curso = cursoRepository.getReferenceById(actualizarTopico.cursoId());
            topico.actualizarTopicoConCurso(actualizarTopico, curso);
        }else{
            topico.actualizarTopico(actualizarTopico);
        }

        var datosTopico = new DetallesTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getName(),
                topico.getCurso().getCategoria()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }

}
