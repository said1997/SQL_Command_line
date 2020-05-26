package QueryTraitement;

import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.calcite.sql.parser.SqlParseException;
import org.junit.Test;

import junit.framework.TestCase;

public class querySelectTest{
	
	@Test
	public void TestSelect() throws SqlParseException
	{
		querySelect queryselect=new querySelect("select name from Bureau");
		queryselect.ExtractClausesSelect();
		Map<String, java.util.List<String>> result=queryselect.getQueryResult();
		java.util.List list=result.get("SELECT");
		assertTrue(list.get(0).equals("NAME"));
		
	}
	@Test
	public void TestFrom()
	{
		querySelect queryselect=new querySelect("select name from Bureau,documents,c");
		queryselect.ExtractClausesFrom();
		Map<String, java.util.List<String>> result=queryselect.getQueryResult();
		java.util.List list=result.get("FROM");
		assertTrue(list.size()==3);
		
	}
	
}
