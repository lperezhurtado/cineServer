package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long>{
    
}
