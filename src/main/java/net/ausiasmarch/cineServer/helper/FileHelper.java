package net.ausiasmarch.cineServer.helper;

import java.io.*;
import java.nio.file.*;
import org.springframework.web.multipart.MultipartFile;

import net.ausiasmarch.cineServer.exceptions.ValidationException;

public class FileHelper {

    //Las 2 lineas de codigo se repiten en get y delete, por eso se hace un metodo aparte y se llama en cada uno
    public static Path path(String path, String fileName) {
        Path uploadPath = Paths.get(path);
        Path filePath = uploadPath.resolve(fileName);
        return filePath;
    }
    
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartfile) {
        Path uploadPath = Paths.get(uploadDir); //obtiene la ruta absoluta

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //crea la ruta a la imagen y la guarda en dicha ruta
        try (InputStream inputStream = multipartfile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new ValidationException("No se ha podido guardar la imagen: "+ fileName);
        }
    }

    public static byte[] getImage ( String fileName, String path) {
        //Path uploadPath = Paths.get(path);
        //Path filePath = uploadPath.resolve(fileName);
        byte[] image = new byte[0];
        try {
            image = Files.readAllBytes(path(path, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(image.length != 0) {
            return image;
        } else {
            throw new ValidationException("No se encuentra la imagen "+fileName);
        }
    }

    public static void deleteImage(String path, String imagen) {
        //Path uploadPath = Paths.get(path);
        //Path filePath = uploadPath.resolve(imagen);
        try {
            if (getImage(imagen, path).length != 0) {
                Files.delete(path(path, imagen)); //llama a path para obtener ruta de img a borrar
            }
            else{
                throw new ValidationException("No se ha podido borrar la imagen \""+imagen+"\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
