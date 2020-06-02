package OsTraitemetTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import OsTraitement.FindCommand;
import QueryTraitement.querySelect;


public class FindCommandTest {
	
	@Test
	public void addSelectT() {
	querySelect queryselect=new querySelect("select path from Bureau");
	queryselect.ExtractClausesSelect();
	Map<String, List<String>> result=queryselect.getQueryResult();
	List<String> list=result.get("SELECT");
	assertTrue(list.get(0).equals("PATH"));
	FindCommand find = new FindCommand(queryselect.getQueryResult());
	find.addSelectTraduction();
	
	
	}
	
	@Test
	public void addTraductionFromTest() {
		querySelect queryselect=new querySelect("select creationtime,size from \"bin/pom.xml\",\"/home/said/Bureau/\",src ");
		queryselect.ExtractClausesFrom();
		queryselect.ExtractClausesSelect();
		
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		cmd.AddFromTraduction();
		assertEquals("[/home/said/Documents/Ter2/SQL_Command_line/src/test, /home/said/Documents/Ter2/SQL_Command_line/src/main]",cmd.getFolderAndContainers("src").toString());
	
	
	}
}
