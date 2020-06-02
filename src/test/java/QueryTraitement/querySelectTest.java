package QueryTraitement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.parser.SqlParseException;
import org.junit.Assert;
import org.junit.Test;


public class querySelectTest{
	
	@Test
	public void TestSelect() throws SqlParseException
	{
		querySelect queryselect=new querySelect("select path from Bureau");
		queryselect.ExtractClausesSelect();
		Map<String, List<String>> result=queryselect.getQueryResult();
		List<String> list=result.get("SELECT");
		assertTrue(list.get(0).equals(attributsOfFile.PATH.get()));
		
	}
	@Test
	public void TestFromWithMajusclusPath()
	{
		querySelect queryselect=new querySelect(" select * from \"~/Bureau\",\"documents\",\"c\",\"e\" ");
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		List<String> list=result.get("FROM");
		assertTrue(list.size()==4);
		assertEquals("e",list.get(0));
		assertEquals("c",list.get(1));
		assertEquals("documents",list.get(2));
		assertEquals("~/Bureau",list.get(3));	
	}
	
	@Test
	public void TestFromWithMinsculesPath()
	{
		querySelect queryselect=new querySelect(" select * from documents,c,e ");
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		List<String> list=result.get("FROM");
		assertTrue(list.size()==3);
		assertEquals("e",list.get(0));
		assertEquals("c",list.get(1));
		assertEquals("documents",list.get(2));
		
	}
	@Test
	public void ParseQueryTest() throws SqlParseException {
	 //System.out.println(CalciteParser.getOnlySelectNode("select path,size from src"));
	 //System.out.println(CalciteParser.hasJoinOperation("select path from src order by src"));
	//CalciteParser.ensureNoAliasInExpr("select path,name from \"/home/said/Bureau\" where size > 5000 order by size");
	// assertEquals(CalciteParser.hasJoinOperation("select path from src order by src")
			 //,CalciteParser.hasJoinOperation("select path from src"));
	}
	
	@Test
	public void ExtractClausesWhereTEST() throws SqlParseException {
		String requete = "Select * FROM src WHERE size > 5000 ";
		Map<String,List<String>> MapTest = new HashMap<>();
		List<String>ListTest = new ArrayList<String>();
		ListTest.add("SIZE");
		ListTest.add(">");
		ListTest.add("5000");
		MapTest.put("WHERE", ListTest);
		querySelect qs = new querySelect(requete);
		qs.ExtractClausesWhere();
		Assert.assertEquals(qs.getQueryResult(), MapTest);
	}
	@Test
	public void ExtractANDClausesWhereTEST() throws SqlParseException {
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
	public void ExtractORClausesWhereTEST() throws SqlParseException {
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