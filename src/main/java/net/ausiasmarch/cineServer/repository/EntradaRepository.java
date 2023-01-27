package net.ausiasmarch.cineServer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.EntradaEntity;

public interface EntradaRepository extends JpaRepository<EntradaEntity, Long> {

    Page<EntradaEntity> findBySesionId(Long id_sesion, Pageable pageable);

    List<EntradaEntity> findBySesionId(Long id_sesion);
    
}
