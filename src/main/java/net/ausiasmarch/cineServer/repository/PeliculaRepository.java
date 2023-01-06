package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.PeliculaEntity;

public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Long> {
    
}
