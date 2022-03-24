package mx.com.bmv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import mx.com.bmv.service.IPasswordManagerService;
import mx.com.bmv.service.rest.dto.PasswordDTO;

/**
 * Servicio de Spring encargado de administrar los passwords en la aplicación.
 * @author Yohualy Montejano
 */
@Service
public class PasswordManagerService implements IPasswordManagerService {
    
    private static final String PATRON_PASSWORD = "^(?=.*[0-9].*[0-9].*[0-9])(?=.*[0-9])(?=.*[a-zñ])(?=.*[A-ZÑ])(?=.*[*.$%&#\"@].*[*.$%&#\"@]).{8,12}$";
    private static final  String CONJUNTO_ALFANUMERICO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
    private static final String CONJUNTO_MAYUSCULAS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private static final String CONJUNTO_NUMERICO = "0123456789";
    private static final String CONJUNTO_ESPECIAL = "$%&#\"@";
    private static final int LONGITUD_MIN = 8;
    private static final int LONGITUD_MAX = 12;
    private static final int CANT_NUMEROS = 3;
    private static final int CANT_ESPECIALES = 2;
    private static final int CANT_MAYUSCULAS = 1;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerService.class);
    
    /**{@inheritDoc}*/
    public Boolean esFuerte(PasswordDTO password) {
        LOGGER.debug("Validando el password: " + password );
        Assert.notNull(password, "El objeto password no puede ser null.");
        Assert.notNull(password.getPassword(), "El atributo password no puede ser null.");
        return password.getPassword().matches(PATRON_PASSWORD);
    }

    /**{@inheritDoc}*/
    public PasswordDTO generarPassword(Integer longitud) {
        LOGGER.debug("Generando password con longitud: " + longitud );
        Assert.notNull(longitud, "La longitud no puede ser null.");
        Assert.isTrue (longitud >= LONGITUD_MIN && longitud <= LONGITUD_MAX, 
                "El valor de la longitud solo puede estar entre " +LONGITUD_MIN +" y " + LONGITUD_MAX + " .");
        return new PasswordDTO (generaPassword (longitud));
    }
    
    /* Genera password aleatorio.*/
    private String generaPassword(int longitud) {
        String numeros         = RandomStringUtils.random(CANT_NUMEROS, CONJUNTO_NUMERICO);
        String especiales     = RandomStringUtils.random(CANT_ESPECIALES, CONJUNTO_ESPECIAL);
        String mayusculas    = RandomStringUtils.random(CANT_MAYUSCULAS, CONJUNTO_MAYUSCULAS);
        String regulares     = RandomStringUtils.random(longitud - (CANT_NUMEROS + CANT_ESPECIALES + CANT_MAYUSCULAS), CONJUNTO_ALFANUMERICO);
        
        return mezclaCadenas (numeros, especiales, mayusculas, regulares);
    }
    
    /* Mezcla el contenido de las cadenas de manera aleatoria.*/
    private String mezclaCadenas (String ... cadenas ){
        Random random = new Random();
        List<Character> caracteres = new ArrayList<Character>();
        StringBuilder resultado = new StringBuilder();
        int indice;
        
        for (String cadena : cadenas) {
            for(int i = 0; i < cadena.length(); i ++) {
                caracteres.add(cadena.charAt(i) );
            }
        }
      
        while(! caracteres.isEmpty()){
            indice = random.nextInt(caracteres.size());
            resultado.append(caracteres.remove(indice));
        }
        return resultado.toString();
    }
    
}
