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
		/*List<String> listStat = new ArrayList<String>();
		listStat=OsTraitement.executeCommand(OsTraitement.constructStatCommand());
		String expected="file name : target, Type : répertoire, Size : 4096, Access Rights : drwxr-xr-x, "
				+ "Time Of last Access : 2020-05-26 16:26:22.888745772 +0200, "
				+ "Time of last modif 2020-05-26 16:26:22.848744971 +0200 ";
		System.out.println(listStat);
		assertEquals(1, listStat.size());
		assertEquals(expected, listStat.get(0));*/
		
	}
	
	/*@Test
	public void getinitStatCommand() {
		querySelect queryselect=new querySelect("select name,type,size,ACCESSRIGHTS from \"/home/said/Bureau/\" ");
		List<String> listStat = new ArrayList<String>();
		queryselect.ExtractClausesSelect();
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		String [] tab = cmd.addSelectTraduction();
		//listStat=OsTraitement.executeCommand(cmd.addSelectTraduction());
		cmd.AddFromTraduction();
		for(String s : result.get("FROM")) {
			for(String c : cmd.getFolderAndContainers(s)) {
				System.out.println("--->Pour : "+c);
				for(String d : OsTraitement.executeCommand(cmd.constructStatCommandFrom(tab,c))) {
					System.out.println(d);
				}
				
			}
		}

	}*/
}
