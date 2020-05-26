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
	querySelect queryselect=new querySelect("select name from Bureau");
	queryselect.ExtractClausesSelect();
	Map<String, List<String>> result=queryselect.getQueryResult();
	List<String> list=result.get("SELECT");
	assertTrue(list.get(0).equals("NAME"));
	
	FindCommand find = new FindCommand(queryselect.getQueryResult());
	find.addSelectTraduction();
	System.out.println(find.getCommand());
	
	
	}
}
