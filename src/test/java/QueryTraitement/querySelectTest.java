package QueryTraitement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.parser.SqlParseException;
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
	
}