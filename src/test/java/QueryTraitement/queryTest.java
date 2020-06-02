package QueryTraitement;


import static org.junit.Assert.*;

import org.junit.Test;

public class queryTest {
	
	
	@Test
	public void QueryParseTest() {
	String quer = "select * from src";
	 querySelect q= new querySelect(quer);
	assertEquals(quer,q.getQuery());
	}
	
	
}
