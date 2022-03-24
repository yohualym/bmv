package mx.com.bmv.service.ejb;

import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mx.com.bmv.service.IPasswordManagerService;
import mx.com.bmv.service.ejb.impl.EJBPasswordManager;
import mx.com.bmv.service.rest.dto.PasswordDTO;

public class EJBPasswordManagerUTest {
	
	EJBPasswordManager ejb;
	IPasswordManagerService  passwordManagerService;
	
	@BeforeClass
	public void setUp() {
		ejb = new EJBPasswordManager ();
		passwordManagerService = Mockito.mock(IPasswordManagerService.class);
		ReflectionTestUtils.setField(ejb, "passwordManagerService", passwordManagerService);
	}
	
	@Test
	public void generarPassword () {
		ejb.generarPassword(12);
		Mockito.verify(passwordManagerService, Mockito.times(1)).generarPassword (Mockito.eq(12));
	}
	
	@Test
	public void esFuerte () {
		PasswordDTO passwordDTO = new PasswordDTO ("$%23Aqwe");
		ejb.esFuerte(passwordDTO) ;
		Mockito.verify(passwordManagerService, Mockito.times(1)).esFuerte (Mockito.eq(passwordDTO));
	}
}
