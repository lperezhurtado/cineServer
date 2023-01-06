package net.ausiasmarch.cineServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServer.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    UsuarioEntity findByLoginAndPassword(String login, String password);

    boolean existsByLogin(String login);
    
    UsuarioEntity findByLogin(String login);

    Page<UsuarioEntity> findByTipousuarioId(Long id_tipousuario, Pageable oPageable);
                        
    Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(String strFilterName, String strFilterSurname, String strFilterLast_name, Pageable oPageable);

    Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingAndTipousuarioId(String strFilterName, String strFilterSurname, String strFilterLast_name, Long id_usertype, Pageable oPageable);

    Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(String strFilterName, String strFilterSurname, String strFilterLast_name, Long id_team, Pageable oPageable);

}
