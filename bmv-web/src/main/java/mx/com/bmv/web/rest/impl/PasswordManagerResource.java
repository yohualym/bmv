package mx.com.bmv.web.rest.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.com.bmv.service.ejb.IEJBPasswordManager;
import mx.com.bmv.service.rest.dto.PasswordDTO;
import mx.com.bmv.web.rest.IPasswordManagerResource;

/**
 * Servicio REST encargado de administrar los passwords en la aplicación.
 * <br/>
 * Este servicio delega la responsabilidad al EJB apropiado.
 * @author Yohualy Montejano
 */
@Stateless
public class PasswordManagerResource implements IPasswordManagerResource {
    
    @EJB
    IEJBPasswordManager passwordManagerEJBService;

    /**{@inheritDoc}*/
    public Boolean esFuerte(PasswordDTO password) {
        return passwordManagerEJBService.esFuerte(password);
    }

    /**{@inheritDoc}*/
    public PasswordDTO generarPassword(Integer longitud) {
        return passwordManagerEJBService.generarPassword(longitud);
    }

}
