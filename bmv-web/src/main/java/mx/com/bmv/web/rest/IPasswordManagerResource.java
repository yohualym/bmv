package mx.com.bmv.web.rest;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import mx.com.bmv.service.rest.dto.PasswordDTO;

/**
 * Interface que debe cumplir el servicio RESTful encargado de administrar los passwords en la aplicación.
 * @author Yohualy Montejano
 */
@Local
@Path("/password")
public interface IPasswordManagerResource {
    
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Boolean esFuerte (@Valid PasswordDTO password);

    /**
     * Permite crear un password seguro.
     * @param longitud Longitud del password (Número entero 8 y 12).
     * @return Password seguro.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    PasswordDTO generarPassword(@NotNull @Min(8) @Max(12) @QueryParam("longitud") Integer longitud);
}
