package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.cineServer.entity.SesionEntity;

public interface SesionRepository extends JpaRepository<SesionEntity, Long> {

    Page<SesionEntity> findBySalaId(Long id_sala, Pageable pageable);

    Page<SesionEntity> findByPeliculaId(Long id_pelicula, Pageable pageable);

    Page<SesionEntity> findByTarifaId(Long id_tarifa, Pageable pageable);

    Page<SesionEntity> findByPeliculaIdAndTarifaId(Long id_pelicula, Long id_tarifa, Pageable pageable);

    Page<SesionEntity> findBySalaIdAndPeliculaId(Long id_sala, Long id_pelicula, Pageable pageable);

    Page<SesionEntity> findBySalaIdAndTarifaId(Long id_sala, Long id_tarifa, Pageable pageable);

    Page<SesionEntity> findBySalaIdAndPeliculaIdAndTarifaId(Long id_sala, Long id_pelicula, Long id_tarifa, Pageable pageable);

    @Query(value = "SELECT * FROM sesion WHERE id_pelicula = ?1 AND fecha_hora LIKE  %?2%", nativeQuery = true)
    Page<SesionEntity> findByPeliculaIdAndFechahoraContaining(Long id_pelicula, String filter, Pageable pageable);
    
}
