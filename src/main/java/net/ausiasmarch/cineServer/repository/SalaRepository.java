package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.SalaEntity;

public interface SalaRepository extends JpaRepository<SalaEntity, Long> {
    
}
