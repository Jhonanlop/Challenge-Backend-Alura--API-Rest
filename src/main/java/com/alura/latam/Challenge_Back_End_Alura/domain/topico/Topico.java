package com.alura.latam.Challenge_Back_End_Alura.domain.topico;

import com.alura.latam.Challenge_Back_End_Alura.domain.curso.Curso;
import com.alura.latam.Challenge_Back_End_Alura.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity(name = "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;


    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name="ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(CrearTopico crearTopico, Usuario usuario, Curso curso) {
        this.titulo = crearTopico.titulo();
        this.mensaje = crearTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.estado = Estado.Open;
        this.usuario = usuario;
        this.curso = curso;
    }



    public void actualizarTopicoConCurso(ActualizarTopico actualizarTopico, Curso curso) {
        if (actualizarTopico.titulo() != null){
            this.titulo = actualizarTopico.titulo();
        }
        if (actualizarTopico.mensaje() != null){
            this.mensaje = actualizarTopico.mensaje();
        }
        if (actualizarTopico.estado() != null){
            this.estado = actualizarTopico.estado();
        }
        if (actualizarTopico.cursoId() != null){
            this.curso = curso;
        }
        this.ultimaActualizacion = LocalDateTime.now();

    }

    public void actualizarTopico(ActualizarTopico actualizarTopico){
        if (actualizarTopico.titulo() != null){
            this.titulo = actualizarTopico.titulo();
        }
        if (actualizarTopico.mensaje() != null){
            this.mensaje = actualizarTopico.mensaje();
        }
        if(actualizarTopico.estado() != null){
            this.estado = actualizarTopico.estado();
        }
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void eliminarTopico(){
        this.estado = Estado.Deleted;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
