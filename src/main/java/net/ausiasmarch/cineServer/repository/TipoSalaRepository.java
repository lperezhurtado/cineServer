package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.TipoSalaEntity;

public interface TipoSalaRepository extends JpaRepository<TipoSalaEntity, Long> {
    
}
