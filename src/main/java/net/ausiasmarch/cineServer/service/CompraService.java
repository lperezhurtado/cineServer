package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.CompraEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepo;

    @Autowired
    FacturaService facturaService;

    public void validateID(Long id) {
        if (!compraRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra compra con id " +id);
        }
    }

    public Long count() {
        return compraRepo.count();
    }

    public Page<CompraEntity> getPage(Pageable pageable, Long id_factura) {
        if (id_factura == null) {
            return compraRepo.findAll(pageable);
        } else {
            return compraRepo.findByFacturaId(id_factura, pageable);
        }
    }

    public Long create(CompraEntity neWCompra) {
        neWCompra.setId(0L);
        return compraRepo.save(neWCompra).getId();
    }

    public CompraEntity get(Long id) {
        validateID(id);
        return compraRepo.getReferenceById(id); 
    }

    public Long update(CompraEntity updatedCompra) {
        CompraEntity actualCompra = compraRepo.getReferenceById(updatedCompra.getId());
        actualCompra.setDescuentoUsuario(updatedCompra.getDescuentoUsuario());
        actualCompra.setPrecio(updatedCompra.getPrecio());
        actualCompra.setFecha(updatedCompra.getFecha());
        actualCompra.setEntrada(updatedCompra.getEntrada());
        actualCompra.setFactura(updatedCompra.getFactura());

        return compraRepo.save(actualCompra).getId();
    }

    public Long delete(Long id) {
        validateID(id);
        compraRepo.deleteById(id);

        if(compraRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }

    
    
}
