package mx.com.bmv.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configuración de la ruta para el acceso a los servicios Rest.
 * <br/>
 * El acceso a los mismos será: {host}/bmv-web/api/
 * @author Yohualy Montejano.
 *
 */
@ApplicationPath("/api")
public class RestConfig extends Application{

}
