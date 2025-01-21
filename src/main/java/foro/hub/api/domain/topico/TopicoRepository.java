package foro.hub.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByMensajeAndIdNot(String mensaje, Long id);

    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND t.activo = true")
    Page<Topico> findByCurso(
            @Param("curso") String curso,
            Pageable paginacion);
}
