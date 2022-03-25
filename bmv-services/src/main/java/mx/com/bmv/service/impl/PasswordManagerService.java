package mx.com.bmv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value ("${password.patron}")
    private String patron;
    
    @Value ("${password.conjunto.alfanumerico}")
    private String conjuntoAlfanumerico;
    
    @Value ("${password.conjunto.mayusculas}")
    private String conjuntoMayusculas;
    
    @Value ("${password.conjunto.numerico}")
    private String conjuntoNumerico;
    
    @Value ("${password.conjunto.especial}")
    private String conjuntoEspecial;
    
    @Value ("${password.longitud.min}")
    private Integer longitudMinima;
    
    @Value ("${password.longitud.max}")
    private Integer longitudMaxima;
    
    @Value ("${password.cantidad.numeros}")
    private Integer cantidadNumeros;
    
    @Value ("${password.cantidad.especiales}")
    private Integer cantidadEspeciales;
    
    @Value ("${password.cantidad.mayusculas}")
    private Integer cantidadMayusculas;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordManagerService.class);
    
    /**{@inheritDoc}*/
    public Boolean esFuerte(PasswordDTO password) {
        LOGGER.debug("Validando el password: {}", password );
        Assert.notNull(password, "El objeto password no puede ser null.");
        Assert.notNull(password.getPassword(), "El atributo password no puede ser null.");
        return password.getPassword().matches(patron);
    }

    /**{@inheritDoc}*/
    public PasswordDTO generarPassword(Integer longitud) {
        LOGGER.debug("Generando password con longitud: {}", longitud );
        Assert.notNull(longitud, "La longitud no puede ser null.");
        Assert.isTrue (longitud >= longitudMinima && longitud <= longitudMaxima, 
                "El valor de la longitud solo puede estar entre " + longitudMinima +" y " + longitudMaxima + " .");
        return new PasswordDTO (generaPassword (longitud));
    }
    
    /* Genera password aleatorio.*/
    private String generaPassword(int longitud) {
        String numeros       = RandomStringUtils.random(cantidadNumeros, conjuntoNumerico);
        String especiales    = RandomStringUtils.random(cantidadEspeciales, conjuntoEspecial);
        String mayusculas    = RandomStringUtils.random(cantidadMayusculas, conjuntoMayusculas);
        String regulares     = RandomStringUtils.random(longitud - (cantidadNumeros + cantidadEspeciales + cantidadMayusculas), conjuntoAlfanumerico);
        
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
