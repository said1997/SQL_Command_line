package uvsq.ProgGL.SQL_FOR_OS;


import org.apache.calcite.adapter.*;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String query = "select a,b,c from d where d.id in (select id from (select id from e))";
    	

    	try {
    	   SqlParser parser = SqlParser.create(query);
    	    SqlNode sqlNode = parser.parseQuery();
    	    System.out.println(sqlNode);
    	    //sqlNode.accept(new SqlVisitor());

    	} catch (SqlParseException e) {
    		
    	    e.printStackTrace();
    	}
    	
    	//FilesTableFunction table = new FilesTableFunction();
    }
}
