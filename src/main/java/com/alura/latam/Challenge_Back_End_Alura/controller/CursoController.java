package com.alura.latam.Challenge_Back_End_Alura.controller;

import com.alura.latam.Challenge_Back_End_Alura.domain.curso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalleCurso> crearTopico(@RequestBody @Valid CrearCurso crearCurso,
                                                    UriComponentsBuilder uriBuilder){
        Curso curso = new Curso(crearCurso);
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{i}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalleCurso(curso));

    }


    @GetMapping("/all")
    public ResponseEntity<Page<DetalleCurso>> listarCursos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetalleCurso::new);
        return ResponseEntity.ok(pagina);
    }


    @GetMapping
    public ResponseEntity<Page<DetalleCurso>> listarCursosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByActivoTrue(pageable).map(DetalleCurso::new);
        return ResponseEntity.ok(pagina);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DetalleCurso> ListarUnCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        var datosDelCurso = new DetalleCurso(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return ResponseEntity.ok(datosDelCurso);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetalleCurso> actualizarCurso(@RequestBody @Valid ActualizarCurso actualizarCurso, @PathVariable Long id){

        Curso curso = repository.getReferenceById(id);

        curso.actualizarCurso(actualizarCurso);

        var datosDelCurso = new DetalleCurso(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return ResponseEntity.ok(datosDelCurso);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }

}
