package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.TarifaEntity;

public interface TarifaRepository extends JpaRepository<TarifaEntity, Long> {

    Page<TarifaEntity> findByNombreIgnoreCaseContaining(String filter, Pageable pageable);
    
}
