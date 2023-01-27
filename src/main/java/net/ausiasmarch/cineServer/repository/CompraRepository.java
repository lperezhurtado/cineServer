package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.CompraEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {

    Page<CompraEntity> findByFacturaId(Long id_factura, Pageable pageable);
    
}
