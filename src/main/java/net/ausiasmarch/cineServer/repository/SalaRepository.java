package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.SalaEntity;

public interface SalaRepository extends JpaRepository<SalaEntity, Long> {

    Page<SalaEntity> findByTiposalaId(Long id_tipoSala, Pageable pageable);
    
}
