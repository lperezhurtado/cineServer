package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.TipoSalaEntity;

public interface TipoSalaRepository extends JpaRepository<TipoSalaEntity, Long> {
    
    public Page<TipoSalaEntity> findByNombreIgnoreCaseContaining(String filter, Pageable pageable);
}
