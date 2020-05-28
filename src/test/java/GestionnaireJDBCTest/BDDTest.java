package GestionnaireJDBCTest;

import org.junit.Test;

import GestionnaireJDBC.BDD;

public class BDDTest {

	@Test
	public void test() throws Exception{
		BDD.setNomBdd("BDD");
		BDD.Bdd();
		BDD.resetBddTables();
		
	}
}
