package com.alura.latam.Challenge_Back_End_Alura.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findAllByTopicoId(Long topicoId, Pageable pageable);

    Page<Respuesta> findAllByUsuarioId(Long usuarioId, Pageable pageable);
    Respuesta getRefereceByTopicoId(Long id);
    Respuesta getRefereceByUsuarioId(Long id);

    Respuesta getReferenceByTopicoId(Long id);
}
