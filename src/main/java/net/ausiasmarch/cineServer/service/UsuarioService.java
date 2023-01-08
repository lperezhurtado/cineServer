package net.ausiasmarch.cineServer.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.exceptions.ValidationException;

import net.ausiasmarch.cineServer.entity.UsuarioEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.helper.RandomHelper;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.TipoUsuarioRepository;
import net.ausiasmarch.cineServer.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    AuthService authService;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Autowired
    TipoUsuarioService tipoUsuarioService;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepo;

    private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final String WILDCART_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"; //wildcart
    private final String[] NAMES = {"Jose", "Mark", "Elen", "Toni", "Hector", "Jose", "Laura", "Vika", "Sergio",
        "Javi", "Marcos", "Pere", "Daniel", "Jose", "Javi", "Sergio", "Aaron", "Rafa", "Lionel", "Borja"};

    private final String[] SURNAMES = {"Penya", "Tatay", "Coronado", "Cabanes", "Mikayelyan", "Gil", "Martinez",
        "Bargues", "Raga", "Santos", "Sierra", "Arias", "Santos", "Kuvshinnikova", "Cosin", "Frejo", "Marti",
        "Valcarcel", "Sesa", "Lence", "Villanueva", "Peyro", "Navarro", "Navarro", "Primo", "Gil", "Mocholi",
        "Ortega", "Dung", "Vi", "Sanchis", "Merida", "Aznar", "Aparici", "Tarazón", "Alcocer", "Salom", "Santamaría"};

    public void validateID(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new ResourceNotFound("No existe usuario con id  " + id);
        }
    }

    public void validate(UsuarioEntity usuarioEntity, String op) {
        ValidationHelper.validateDNI(usuarioEntity.getDni(), " DNI no válido");
        ValidationHelper.validateStringLength(usuarioEntity.getNombre(), 2, 30, "campo nombre de Usuario (el campo debe tener longitud de 2 a 30 caracteres)");
        ValidationHelper.validateStringLength(usuarioEntity.getApellido1(), 2, 30, "campo primer apellido de Usuario (el campo debe tener longitud de 2 a 30 caracteres)");
        ValidationHelper.validateStringLength(usuarioEntity.getApellido2(), 2, 30, "campo segundo apellido de Usuario (el campo debe tener longitud de 2 a 30 caracteres)");
        ValidationHelper.validateEmail(usuarioEntity.getEmail(), " Email de Usuario no válido");
        ValidationHelper.validateLogin(usuarioEntity.getLogin(), " Login de Usuario no válido");
        if ( op.equals("new") ) {
            if( usuarioRepo.existsByLogin(usuarioEntity.getLogin()) ) {
                throw new ValidationException("Login ya existente");
            }
        }
        else if( op.equals("update") ){
            UsuarioEntity actualUsuarioEntity = usuarioRepo.findById(usuarioEntity.getId()).get();
            if(usuarioRepo.existsByLogin(usuarioEntity.getLogin()) && !actualUsuarioEntity.getLogin().equals(usuarioEntity.getLogin())) {
                throw new ValidationException("Login ya existente");
            }
        }
        
        ValidationHelper.validateRange(usuarioEntity.getDescuento(), 0, 100, "campo Descuento de la entidad Usuario (debe ser un entero entre 0 y 100)");
        tipoUsuarioService.validate(usuarioEntity.getTipousuario().getId());
    }

    //GET Method
    public UsuarioEntity get(Long id) {
            authService.OnlyAdminsOrOwnUserData(id);
            try {
                return usuarioRepo.findById(id).get(); 
            } catch (Exception e) {
                throw new ResourceNotFound("El id "+id+" no existe");
            }
    }

    //CREATE Method
    public Long create (UsuarioEntity newUsuario) {

        authService.onlyAdmins();
        validate(newUsuario, "new");

        newUsuario.setId(0L);
        return usuarioRepo.save(newUsuario).getId();
    }

    //UPDATE Method
    @Transactional
    public Long update(UsuarioEntity updatedUsuario) {

        validateID(updatedUsuario.getId());
        authService.OnlyAdminsOrOwnUserData(updatedUsuario.getId());
        validate(updatedUsuario, "update");

        //UsuarioEntity actualUsuario = usuarioRepo.getReferenceById(updatedUsuario.getId());
        //updatedUsuario.setPassword(actualUsuario.getPassword());

        if(authService.isAdmin()) {
            return update4Admins(updatedUsuario).getId();
        }
        else{
            return update4Users(updatedUsuario).getId();
        }
    }

    @Transactional
    private UsuarioEntity update4Admins(UsuarioEntity updatedUsuario) {
        UsuarioEntity usuarioEntity = usuarioRepo.findById(updatedUsuario.getId()).get();
        //keeping login password token & validado 
        usuarioEntity.setDni(updatedUsuario.getDni());
        usuarioEntity.setNombre(updatedUsuario.getNombre());
        usuarioEntity.setApellido1(updatedUsuario.getApellido1());
        usuarioEntity.setApellido2(updatedUsuario.getApellido2());
        usuarioEntity.setEmail(updatedUsuario.getEmail());
        usuarioEntity.setLogin(updatedUsuario.getLogin());
        usuarioEntity.setDescuento(updatedUsuario.getDescuento());
        usuarioEntity.setTipousuario(tipoUsuarioService.get(updatedUsuario.getTipousuario().getId()));
        return usuarioRepo.save(usuarioEntity);
    }

    @Transactional
    private UsuarioEntity update4Users(UsuarioEntity updatedUsuario) {
        UsuarioEntity usuarioEntity = usuarioRepo.findById(updatedUsuario.getId()).get();
        //keeping login password token & descuento tipousuario
        usuarioEntity.setDni(updatedUsuario.getDni());
        usuarioEntity.setNombre(updatedUsuario.getNombre());
        usuarioEntity.setApellido1(updatedUsuario.getApellido1());
        usuarioEntity.setApellido2(updatedUsuario.getApellido2());
        usuarioEntity.setEmail(updatedUsuario.getEmail());
        usuarioEntity.setLogin(updatedUsuario.getLogin());
        usuarioEntity.setTipousuario(tipoUsuarioService.get(2L));
        return usuarioRepo.save(usuarioEntity);
    }

    //DELETE Method
    public Long delete(Long id) {

        authService.onlyAdmins();
        validateID(id);
        usuarioRepo.deleteById(id);

        if(usuarioRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
    //COUNT Method
    public Long count() {
        authService.onlyAdmins();
        return usuarioRepo.count();
    }
    //GETPAGE Method
    public Page<UsuarioEntity> getPage(Pageable pageable, String strFilter, Long id_tipousuario) {
    
        authService.onlyAdmins();
        ValidationHelper.validateRPP(pageable.getPageSize());

        if (strFilter == null || strFilter.length()==0) {
            if (id_tipousuario == null) {
                    return usuarioRepo.findAll(pageable);
            } else {
                    return usuarioRepo.findByTipousuarioId(id_tipousuario, pageable);
            }
        } else {
            if (id_tipousuario == null) {
                    return usuarioRepo.findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(strFilter,strFilter,strFilter, pageable);
            } else {
                    return usuarioRepo.findByNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContainingAndTipousuarioId(strFilter,strFilter,strFilter, id_tipousuario, pageable);
            }
        }
    } 

    //METODOS PARA GENERAR USUARIOS

    public Long generateSome(Integer amount) {
        //authService.onlyAdmins();
        List<UsuarioEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity oUsuarioEntity = generateRandomUser();
            usuarioRepo.save(oUsuarioEntity);
            userList.add(oUsuarioEntity);
        }
        return usuarioRepo.count();
    }

    private UsuarioEntity generateRandomUser() {
        UsuarioEntity oUserEntity = new UsuarioEntity();
        oUserEntity.setDni(generateDNI());
        oUserEntity.setNombre(generateName());
        oUserEntity.setApellido1(generateSurname());
        oUserEntity.setApellido2(generateSurname());
        oUserEntity.setLogin(oUserEntity.getNombre() + "_" + oUserEntity.getApellido1());
        oUserEntity.setPassword(WILDCART_DEFAULT_PASSWORD); // wildcart
        oUserEntity.setEmail(generateEmail(oUserEntity.getNombre(), oUserEntity.getApellido1()));
        oUserEntity.setDescuento(RandomHelper.getRandomInt(0, 51));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oUserEntity.setTipousuario(tipoUsuarioRepo.getReferenceById(2L));
        } else {
            oUserEntity.setTipousuario(tipoUsuarioRepo.getReferenceById(1L));
        }
        return oUserEntity;
    }

    private String generateDNI() {
        String dni = "";
        int dniNumber = RandomHelper.getRandomInt(11111111, 99999999 + 1);
        dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
        return dni;
    }

    private String generateName() {
        return NAMES[RandomHelper.getRandomInt(0, NAMES.length - 1)].toLowerCase();
    }

    private String generateSurname() {
        return SURNAMES[RandomHelper.getRandomInt(0, SURNAMES.length - 1)].toLowerCase();
    }

    private String generateEmail(String name, String surname) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(surname);
        return getFromList(list) + "_" + getFromList(list) + "@daw.tk";
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }
}