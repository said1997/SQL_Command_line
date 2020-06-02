package OsTraitemetTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import OsTraitement.FindCommand;
import OsTraitement.OsTraitement;
import QueryTraitement.attributsOfFile;
import QueryTraitement.querySelect;
public class OsTraitementTest {
	
	@Test
	public void executeCommandLsTest() {
		List<String> list = new ArrayList<String>();
		
		list=OsTraitement.DiskFileExplore("src");
		assertEquals(2, list.size());
		assertEquals("/home/said/Documents/Ter2/SQL_Command_line/src/test", list.get(0));
		assertEquals("/home/said/Documents/Ter2/SQL_Command_line/src/main", list.get(1));
		
	}
	
	@Test
	public void executeCommandStatTest() {
		querySelect q = new querySelect("select CREATIONTIME, LASTACCESSTIME, LASTMODIFIEDTIME, PERMISSIONS from src");
		q.ExtractClausesSelect();
		OsTraitement testOsTraitemetn = new OsTraitement(q.getQueryResult()) {};
		String expected="basic:creationTime,lastAccessTime,lastModifiedTime";
		assertEquals(expected, testOsTraitemetn.convertSelectAttributes());
		
		
	}
	

}
