package mx.com.bmv.service;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mx.com.bmv.service.impl.PasswordManagerService;
import mx.com.bmv.service.rest.dto.PasswordDTO;

public class PasswordManagerServiceUTest {
	
	private final static int LONGITUD_MIN = 8;
	private final static int LONGITUD_MAX = 12;
	
	private String PATRON_PASSWORD 			= "password.patron";
	private String CONJUNTO_ALFANUMERICO 	= "password.conjunto.alfanumerico";
	private String CONJUNTO_MAYUSCULAS 		= "password.conjunto.mayusculas";
	private String CONJUNTO_NUMERICO 		= "password.conjunto.numerico";
	private String CONJUNTO_ESPECIAL 		= "password.conjunto.especial";
	
	private String KEY_LONGITUD_MIN 		= "password.longitud.min";
	private String KEY_LONGITUD_MAX 		= "password.longitud.max";
	
	private String CANTIDAD_NUMEROS 		= "password.cantidad.numeros";
	private String CANTIDAD_ESPECIALES 		= "password.cantidad.especiales";
	private String CANTIDAD_MAYUSCULAS 		= "password.cantidad.mayusculas";
	
	private String patronPassword;
	
	Properties properties;
	PasswordManagerService service;
	
	@BeforeClass
	public void setUp() throws Exception {
		properties = new Properties();
		try (InputStream is = PasswordManagerService.class.getResourceAsStream("/password.properties")) {
		  properties.load(is);
		}
		
		patronPassword = (String) properties.get(PATRON_PASSWORD);
		
		service = new PasswordManagerService ();
		ReflectionTestUtils.setField(service, "patron", patronPassword);
		ReflectionTestUtils.setField(service, "conjuntoAlfanumerico", 	(String) properties.get(CONJUNTO_ALFANUMERICO));
		ReflectionTestUtils.setField(service, "conjuntoMayusculas", 	(String) properties.get(CONJUNTO_MAYUSCULAS));
		ReflectionTestUtils.setField(service, "conjuntoNumerico", 		(String) properties.get(CONJUNTO_NUMERICO));
		ReflectionTestUtils.setField(service, "conjuntoEspecial", 		(String) properties.get(CONJUNTO_ESPECIAL));
		
		ReflectionTestUtils.setField(service, "longitudMinima", 		Integer.valueOf( (String) properties.get(KEY_LONGITUD_MIN)));
		ReflectionTestUtils.setField(service, "longitudMaxima", 		Integer.valueOf( (String) properties.get(KEY_LONGITUD_MAX)));
		
		ReflectionTestUtils.setField(service, "cantidadNumeros", 		Integer.valueOf( (String) properties.get(CANTIDAD_NUMEROS)));
		ReflectionTestUtils.setField(service, "cantidadEspeciales", 	Integer.valueOf( (String) properties.get(CANTIDAD_ESPECIALES)));
		ReflectionTestUtils.setField(service, "cantidadMayusculas", 	Integer.valueOf( (String) properties.get(CANTIDAD_MAYUSCULAS)));
		
	}

	@Test
	public void generarPassword () {
		PasswordDTO password = service.generarPassword(12);
		Assert.assertNotNull(password);
		Assert.assertNotNull(password.getPassword());
		Assert.assertTrue (password.getPassword().matches(patronPassword));
	}
	
	@Test (expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".*La longitud no puede ser null.*")
	public void generarPasswordFailNull () {
		service.generarPassword(null);
	}
	
	@Test (expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".* " + LONGITUD_MIN  + " y " + LONGITUD_MAX + " .*")
	public void generarPasswordFailTamanio7 () {
		service.generarPassword(7);
	}
	
	@Test (expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".* " + LONGITUD_MIN  + " y " + LONGITUD_MAX + " .*")
	public void generarPasswordFailTamanio13 () {
		service.generarPassword(13);
	}
	
	@Test
	public void esFuerte () {
		Assert.assertTrue (
				service.esFuerte( new PasswordDTO ("1$%23Aqweñ") ) );
	}
	
	/*Falla si es null*/
	@Test (expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".*El objeto password no puede ser null.*")
	public void esFuerteFailNull () {
		service.esFuerte( null );
	}
	
	/*Falla si el password es null*/
	@Test (expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".*El atributo password no puede ser null.*")
	public void esFuerteFailNullAtributo () {
		service.esFuerte( new PasswordDTO (null) );
	}
	
	/*Falla si no son 3 números*/
	@Test
	public void esFuerteFailNumero () {
		Assert.assertFalse (
				service.esFuerte( new PasswordDTO("$%23Aqwe") ) );
	}
	
	/*Falla si no hay una letra mayúscula*/
	@Test
	public void esFuerteFailMayuscula () {
		Assert.assertFalse (
				service.esFuerte( new PasswordDTO ("1$%23aqwe") ) );
	}
	
	/*Falla si no hay 2 caracteres especiales*/
	@Test
	public void esFuerteFailCaracterEspecial () {
		Assert.assertFalse (
				service.esFuerte( new PasswordDTO ("1$23Aqweñ") ) );
	}
	
	/*Falla por ser mayor a 12 caracteres*/
	@Test
	public void esFuerteFailExcedeLongitud () {
		Assert.assertFalse (
				service.esFuerte( new PasswordDTO ("1$%23Aqweñaaaaaaaa") ) );
	}
	
	/*Falla por ser menor a 8 caracteres*/
	@Test
	public void esFuerteFailFaltaLongitud () {
		Assert.assertFalse (
				service.esFuerte( new PasswordDTO ("1$%23Aq") ) );
	}

}
