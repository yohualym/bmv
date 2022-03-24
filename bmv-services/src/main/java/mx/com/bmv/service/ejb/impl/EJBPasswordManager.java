package mx.com.bmv.service.ejb.impl;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.com.bmv.service.IPasswordManagerService;
import mx.com.bmv.service.ejb.IEJBPasswordManager;
import mx.com.bmv.service.rest.dto.PasswordDTO;

/**
 * Servicio EJB encargado de administrar los passwords en la aplicación.
 * <br/>
 * Este servicio delega la responsabilidad al servicio de spring apropiado.
 * @author Yohualy Montejano
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EJBPasswordManager implements IEJBPasswordManager {
    
    @Autowired
    IPasswordManagerService passwordManagerService;
    
    /**{@inheritDoc}*/
    public Boolean esFuerte(PasswordDTO password) {
        return passwordManagerService.esFuerte(password);
    }
    
    /**{@inheritDoc}*/
    public PasswordDTO generarPassword(Integer longitud) {
        return passwordManagerService.generarPassword(longitud);
    }
}
