package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.SesionEntity;

public interface SesionRepository extends JpaRepository<SesionEntity, Long> {
    
}
