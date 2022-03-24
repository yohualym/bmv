package mx.com.bmv.service;

import javax.ws.rs.QueryParam;

import mx.com.bmv.service.rest.dto.PasswordDTO;

/**
 * Interface que debe cumplir el servicio encargado de administrar los passwords en la aplicación.
 * @author Yohualy Montejano
 */
public interface IPasswordManagerService {
    /**
     * Permite validar un password de acuerdo con las siguientes reglas:
     * <p>
     * <ul>
     * <li>Longitud mínima de 8 y máxima de 12 caracteres.</li>
     * <li>Debe contener al menos 2 caracteres especiales ( $%&#”@).</li>
     * <li>Debe contener al menos 3 números.</li>
     * <li>Debe tener al menos una mayúscula.</li>
     * <li>Debe contemplar el carácter ñ.</li>
     * </ul>
     * </p>
     * @param password Password a ser validado.
     * @return Verdadero si el password cumple con las reglas o Falso en caso contrario.
     */
    Boolean esFuerte (PasswordDTO password);

    /**
     * Permite crear un password seguro.
     * @param longitud Longitud del password.
     * @return Password seguro.
     */
    PasswordDTO generarPassword(@QueryParam("longitud") Integer longitud);
}
