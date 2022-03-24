package mx.com.bmv.service;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mx.com.bmv.service.impl.PasswordManagerService;
import mx.com.bmv.service.rest.dto.PasswordDTO;

public class PasswordManagerServiceUTest {
	
	private final static int LONGITUD_MIN = 8;
	private final static int LONGITUD_MAX = 12;
	private final static String PATRON_PASSWORD = "^(?=.*[0-9].*[0-9].*[0-9])(?=.*[0-9])(?=.*[a-zñ])(?=.*[A-ZÑ])(?=.*[*.$%&#\"@].*[*.$%&#\"@]).{8,12}$";
	
	PasswordManagerService service;
	
	@BeforeClass
	public void setUp() {
		service = new PasswordManagerService ();
	}

	@Test
	public void generarPassword () {
		PasswordDTO password = service.generarPassword(12);
		Assert.assertNotNull(password);
		Assert.assertNotNull(password.getPassword());
		Assert.assertTrue (password.getPassword().matches(PATRON_PASSWORD));
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
