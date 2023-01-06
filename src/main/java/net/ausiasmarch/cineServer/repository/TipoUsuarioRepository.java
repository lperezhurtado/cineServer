package net.ausiasmarch.cineServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import net.ausiasmarch.cineServer.entity.TipoUsuarioEntity;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long>{
    
    public Page<TipoUsuarioEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

}
