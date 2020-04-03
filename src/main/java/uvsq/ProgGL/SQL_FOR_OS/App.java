package uvsq.ProgGL.SQL_FOR_OS;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlVisitor;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String query = "Select a,b,c from d where d.id in (select id from (select id from e))";


    	try {
    	    SqlParser parser = SqlParser.create(query);
    	    SqlNode sqlNode = parser.parseQuery();

    	    //sqlNode.accept(new SqlVisitor());

    	} catch (SqlParseException e) {
    	    e.printStackTrace();
    	}
    }
}
