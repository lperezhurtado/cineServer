package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.CompraEntity;
import net.ausiasmarch.cineServer.entity.EntradaEntity;
import net.ausiasmarch.cineServer.entity.SesionEntity;
import net.ausiasmarch.cineServer.entity.TarifaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.exceptions.ValidationException;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.CompraRepository;
import net.ausiasmarch.cineServer.repository.EntradaRepository;
import net.ausiasmarch.cineServer.repository.TarifaRepository;
import net.ausiasmarch.cineServer.repository.SesionRepository;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepo;

    @Autowired
    FacturaService facturaService;

    @Autowired
    EntradaRepository entradaRepo;

    @Autowired
    EntradaService entradaService;

    @Autowired
    SesionService sesionService;

    @Autowired
    SesionRepository sesionRepo;

    @Autowired
    TarifaService tarifaService;

    @Autowired
    TarifaRepository tarifaRepo;

    public void validateID(Long id) {
        if (!compraRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra compra con id " +id);
        }
    }

    public void validateCompra(CompraEntity compra) {
        EntradaEntity entrada = entradaRepo.getReferenceById(compra.getEntrada().getId());
        entradaService.validateID(entrada.getId());
        SesionEntity sesion = sesionRepo.getReferenceById(entrada.getSesion().getId());
        sesionService.validateID(sesion.getId());
        TarifaEntity tarifa = tarifaRepo.getReferenceById(sesion.getTarifa().getId());
        tarifaService.validateID(tarifa.getId());

        ValidationHelper.validatePrecio(compra.getPrecio(), tarifa.getPrecio(), "El precio no coincide con la tarifa de la sesión");
        ValidationHelper.validateIntRange(compra.getDescuentoUsuario(), 0, 100, "Descuento no válido");
        
        entradaService.validateID(entrada.getId());
        if (entrada.isLibre()) {
            entrada.setLibre(false);

            double precioOriginal = compra.getPrecio();  //calcula el precio tras aplicar el descuento
            int descuento = compra.getDescuentoUsuario(); 
            double precioDesc = precioOriginal - ((descuento/100) *100);
            compra.setPrecio(precioDesc);   
        } else {
            throw new ValidationException("Esta butaca no está disponible");
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

    public Long create(CompraEntity newCompra) {
        validateCompra(newCompra);
        //newCompra.setId(0L);
        return compraRepo.save(newCompra).getId();
    }

    public CompraEntity get(Long id) {
        validateID(id);
        return compraRepo.getReferenceById(id); 
    }

    public Long update(CompraEntity updatedCompra) {
        validateID(updatedCompra.getId());
        validateCompra(updatedCompra);
        CompraEntity actualCompra = compraRepo.getReferenceById(updatedCompra.getId());
        EntradaEntity actualEntrada = entradaRepo.getReferenceById(actualCompra.getEntrada().getId());//Se cambia el estado de la entrada comprada original a libre
        actualEntrada.setLibre(true);

        actualCompra.setDescuentoUsuario(updatedCompra.getDescuentoUsuario());
        actualCompra.setPrecio(updatedCompra.getPrecio());
        actualCompra.setFecha(updatedCompra.getFecha());
        actualCompra.setEntrada(updatedCompra.getEntrada());
        actualCompra.setFactura(updatedCompra.getFactura());

        return compraRepo.save(actualCompra).getId();
    }

    public Long updateFactura(CompraEntity updatedCompra) {
        validateID(updatedCompra.getId());
        CompraEntity actualCompra = compraRepo.getReferenceById(updatedCompra.getId());
        //EntradaEntity actualEntrada = entradaRepo.getReferenceById(actualCompra.getEntrada().getId());//Se cambia el estado de la entrada comprada original a libre
        //actualEntrada.setLibre(true);

        //actualCompra.setDescuentoUsuario(updatedCompra.getDescuentoUsuario());
        //actualCompra.setPrecio(updatedCompra.getPrecio());
        //actualCompra.setFecha(updatedCompra.getFecha());
        //actualCompra.setEntrada(updatedCompra.getEntrada());
        actualCompra.setFactura(updatedCompra.getFactura());

        return compraRepo.save(actualCompra).getId();
    }

    public Long delete(Long id) {
        validateID(id);
        CompraEntity compra = compraRepo.getReferenceById(id);
        EntradaEntity entrada = entradaRepo.getReferenceById(compra.getEntrada().getId());
        entrada.setLibre(true);
        compraRepo.deleteById(id);

        if(compraRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
}