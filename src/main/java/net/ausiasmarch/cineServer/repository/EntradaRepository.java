package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.EntradaEntity;

public interface EntradaRepository extends JpaRepository<EntradaEntity, Long> {
    
}
