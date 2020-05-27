package QueryTraitement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class querySelectTest{
	
	@Test
	public void ExtractClausesWhereTEST() {
		String requete = "Select * FROM Bureau WHERE nom.txt = test.xml ";
		Map<String,List<String>> MapTest = new HashMap<>();
		List<String>ListTest = new ArrayList<String>();
		ListTest.add("NOM.TXT");
		ListTest.add("=");
		ListTest.add("TEST.XML");
		MapTest.put("WHERE", ListTest);
		querySelect qs = new querySelect(requete);
		qs.ExtractClausesWhere();
		Assert.assertEquals(qs.getQueryResult(), MapTest);
	}
	@Test
	public void ExtractANDClausesWhereTEST() {
		String requete = "Select * FROM Bureau WHERE nom.txt = test.xml and b < c and z=2";
		Map<String,List<String>> MapTest = new HashMap<>();
		List<String>ListTest1 = new ArrayList<String>();
		List<String>ListTest2 = new ArrayList<String>();
		List<String>ListTest3 = new ArrayList<String>();
		List<String>ListTest4 = new ArrayList<String>();
		ListTest1.add("Z");
		ListTest1.add("=");
		ListTest1.add("2");
		MapTest.put("AND_right", ListTest1);

		ListTest2.add("NOM.TXT");
		ListTest2.add("=");
		ListTest2.add("TEST.XML");
		MapTest.put("AND_left", ListTest2);

		ListTest3.add("B");
		ListTest3.add("<");
		ListTest3.add("C");
		MapTest.put("AND0", ListTest3);
		
		ListTest4.add("AND");
		MapTest.put("WHERE", ListTest4);

		querySelect qs = new querySelect(requete);
		qs.ExtractClausesWhere();
		Assert.assertEquals(qs.getQueryResult(), MapTest);
	}
	@Test
	public void ExtractORClausesWhereTEST() {
		String requete = "Select * FROM Bureau WHERE nom.txt = test.xml or b < c or z=2";
		Map<String,List<String>> MapTest = new HashMap<>();
		List<String>ListTest1 = new ArrayList<String>();
		List<String>ListTest2 = new ArrayList<String>();
		List<String>ListTest3 = new ArrayList<String>();
		List<String>ListTest4 = new ArrayList<String>();
		ListTest1.add("Z");
		ListTest1.add("=");
		ListTest1.add("2");
		MapTest.put("OR_right", ListTest1);

		ListTest2.add("NOM.TXT");
		ListTest2.add("=");
		ListTest2.add("TEST.XML");
		MapTest.put("OR_left", ListTest2);

		ListTest3.add("B");
		ListTest3.add("<");
		ListTest3.add("C");
		MapTest.put("OR0", ListTest3);
		
		ListTest4.add("OR");
		MapTest.put("WHERE", ListTest4);

		querySelect qs = new querySelect(requete);
		qs.ExtractClausesWhere();
		Assert.assertEquals(qs.getQueryResult(), MapTest);
	}
}
