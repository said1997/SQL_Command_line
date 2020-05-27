package OsTraitemetTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import OsTraitement.FindCommand;
import OsTraitement.OsTraitement;
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
	
	@Test
	public void addTraductionFromTest() {
		querySelect queryselect=new querySelect("select name,type,size,ACCESSRIGHTS from \"bin/pom.xml\",\"/home/said/Bureau/\",src ");
		//List<String> listStat = new ArrayList<String>();
		queryselect.ExtractClausesFrom();
		queryselect.ExtractClausesSelect();
		
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		cmd.AddFromTraduction();
		//verifier le résultat si il est pas à nul
		System.out.println(cmd.getFolderAndContainers("bin/pom.xml"));
		System.out.println(cmd.getFolderAndContainers("/home/said/Bureau/"));
		System.out.println(cmd.getFolderAndContainers("src"));
		
		
		
		
	}
}
