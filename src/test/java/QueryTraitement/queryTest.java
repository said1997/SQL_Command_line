package QueryTraitement;


import org.apache.calcite.sql.SqlKind;
import static org.junit.Assert.*;

import org.junit.Test;

public class queryTest {
	
	
	@Test
	public void QueryParseTest() {
	final String quer = "SELECT e.first_name AS FirstName, s.salary AS Salary from employee AS e "
			+ "join salary AS s on e.emp_id=s.emp_id where e.organization = 'Tesla' and s.zizi = 'Tesla' and e.zizi = 'Tesla'";
	 querySelect q= new querySelect(quer);
	assertEquals(quer,q.getQuery());
	assertEquals(SqlKind.SELECT,q.parseQuery().getKind());
	}
	
	
}
