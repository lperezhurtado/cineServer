package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.PeliculaEntity;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Long> {
    
    Page<PeliculaEntity> findByGeneroId(Long id_genero, Pageable pageable);

    Page<PeliculaEntity> findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContaining(String filterTitulo, String filterDirector, Pageable pageable);

    Page<PeliculaEntity> findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContainingAndGeneroId(String filterTitulo, String filterDirector, Long id_genero, Pageable pageable);

}
