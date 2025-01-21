package com.alura.latam.Challenge_Back_End_Alura.controller;

import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.*;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.validaciones.ValidarActualizarUsuario;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.validaciones.ValidarCrearUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCrearUsuario> crearValidador;

    @Autowired
    List<ValidarActualizarUsuario> actualizarValidador;

    @PostMapping
    @Transactional
    public ResponseEntity<DetallesUsuario> crearUsuario(@RequestBody @Valid CrearUsuario crearUsuario, UriComponentsBuilder uriBuilder){
        crearValidador.forEach(v -> v.validate(crearUsuario));

        String hashedPassword = passwordEncoder.encode(crearUsuario.password());
        Usuario usuario = new Usuario(crearUsuario, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetallesUsuario(usuario));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DetallesUsuario>> leerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetallesUsuario::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    public ResponseEntity<Page<DetallesUsuario>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByEnabledTrue(pageable).map(DetallesUsuario::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<DetallesUsuario> leerUnUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        var datosUsuario = new DetallesUsuario(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DetallesUsuario>leerUnUsuario(@PathVariable Long id){
        Usuario usuario = repository.getReferenceById(id);
        var datosUsuario = new DetallesUsuario(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<DetallesUsuario> actualizarUsuario(@RequestBody @Valid ActualizarUsuario actualizarUsuario, @PathVariable String username){
        actualizarValidador.forEach(v -> v.validate(actualizarUsuario));

        Usuario usuario = (Usuario) repository.findByUsername(username);

        if (actualizarUsuario.password() != null){
            String hashedPassword = passwordEncoder.encode(actualizarUsuario.password());
            usuario.actualizarUsuarioConPassword(actualizarUsuario, hashedPassword);

        }else {
            usuario.actualizarUsuario(actualizarUsuario);
        }

        var datosUsuario = new DetallesUsuario(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }


}
