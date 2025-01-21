package com.alura.latam.Challenge_Back_End_Alura.controller;


import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.*;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones.ValidarRespuestaActualizada;
import com.alura.latam.Challenge_Back_End_Alura.domain.respuesta.validaciones.ValidarRespuestaCreada;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.Estado;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.Topico;
import com.alura.latam.Challenge_Back_End_Alura.domain.topico.TopicoRepository;
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
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
class RespuestaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidarRespuestaCreada> crearValidadores;

    @Autowired
    List<ValidarRespuestaActualizada> actualizarValidadores;

    @PostMapping
    @Transactional

    public ResponseEntity<DetalleRespuesta> crearRespuesta(@RequestBody @Valid CrearRespuesta crearRespuesta, UriComponentsBuilder uriBuilder){

        crearValidadores.forEach(v -> v.validate(crearRespuesta));

        Usuario usuario = usuarioRepository.getReferenceById(crearRespuesta.usuarioId());
        Topico topico = topicoRepository.findById(crearRespuesta.topicoId()).get();

        var respuesta = new Respuesta(crearRespuesta, usuario, topico);
        respuestaRepository.save(respuesta);

        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleRespuesta(respuesta));

    }


    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<Page<DetalleRespuesta>> leerRespuestaDeTopico(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long topicoId){
        var pagina = respuestaRepository.findAllByTopicoId(topicoId, pageable).map(DetalleRespuesta::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/usuario/{nombreUsuario}")
    public ResponseEntity<Page<DetalleRespuesta>> leerRespuestasDeUsuarios(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long usuarioId){
        var pagina = respuestaRepository.findAllByUsuarioId(usuarioId, pageable).map(DetalleRespuesta::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleRespuesta> leerUnaRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

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
    public ResponseEntity<DetalleRespuesta> actualizarRespuesta(@RequestBody @Valid ActualizarRespuesta actualizarRespuesta, @PathVariable Long id){
        actualizarValidadores.forEach(v -> v.validate(actualizarRespuesta, id));
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(actualizarRespuesta);

        if(actualizarRespuesta.solucion()){
            var temaResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            temaResuelto.setEstado(Estado.Closed);
        }

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


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> borrarRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }

}
