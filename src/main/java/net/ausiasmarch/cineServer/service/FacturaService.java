package net.ausiasmarch.cineServer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.FacturaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.FacturaRepository;
import net.ausiasmarch.cineServer.repository.UsuarioRepository;

@Service
public class FacturaService {

    @Autowired
    AuthService authService;

    @Autowired
    FacturaRepository facturaRepo;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    UsuarioService usuarioService;

    public void validateID(Long id) {
        if (!facturaRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra película con id  " + id);
        }
    }

    public void validate(FacturaEntity facturaEntity) {
        ValidationHelper.validateIntRange(facturaEntity.getIva(), 0, 100, "IVA no válido");
        ValidationHelper.validateDoubleRange(facturaEntity.getTotal(), 0, 300, "Precio erróneo");//MIRAR EL MAX
        usuarioService.validateID(facturaEntity.getUsuario().getId());
    }
    //GET
    public FacturaEntity get(Long id) {
        validateID(id);
        return facturaRepo.findById(id).get();
    }
    //CREATE
    public Long create(FacturaEntity newFactura) {
        validate(newFactura);
        newFactura.setId(0L);
        return facturaRepo.save(newFactura).getId();
    }
    //UPDATE
    @Transactional
    public Long update(FacturaEntity updatedFactura) {
        validateID(updatedFactura.getId());
        validate(updatedFactura);

        FacturaEntity actualFactura = facturaRepo.findById(updatedFactura.getId()).get();
        actualFactura.setFecha(updatedFactura.getFecha());
        actualFactura.setIva(updatedFactura.getIva());
        actualFactura.setTotal(updatedFactura.getTotal());
        actualFactura.setUsuario(updatedFactura.getUsuario());
        return facturaRepo.save(actualFactura).getId();
    }
    //DELETE
    public Long delete(Long id) {
        validateID(id);
        usuarioRepo.deleteById(id);

        if(facturaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
    //COUNT
    public Long count() {
        return facturaRepo.count();
    }
    //GETPAGE
    public Page<FacturaEntity> getPage(Pageable pageable, Long id_usuario) {

        ValidationHelper.validateRPP(pageable.getPageSize());

        if (id_usuario == null) {
            return facturaRepo.findAll(pageable);
        } else {
            return facturaRepo.findByUsuarioId(id_usuario, pageable);
        }
    }
    
}
