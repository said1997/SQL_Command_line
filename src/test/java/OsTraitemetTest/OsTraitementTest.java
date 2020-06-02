package OsTraitemetTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import OsTraitement.OsTraitement;
import QueryTraitement.querySelect;
public class OsTraitementTest {
	
	@Test
	public void executeCommandLsTest() {
		List<String> list = new ArrayList<String>();
		
		list=OsTraitement.DiskFileExplore("src");
		assertEquals(2, list.size());
		
		
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
