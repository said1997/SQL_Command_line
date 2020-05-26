package uvsq.ProgGL.SQL_FOR_OS;

import java.util.List;

import javax.print.attribute.standard.PrinterInfo;

import org.apache.calcite.adapter.os.FilesTableFunction;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.fun.SqlInOperator;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.util.Util;

import QueryTraitement.querySelect;


public class App {
	
	
 

	public static void main(String[] args) {
		/*String query = "select a,b,c from d where d.id in (select id from (select id from e))";

		try {
			SqlParser parser = SqlParser.create(query);
			SqlNode sqlNode = parser.parseStmt();
			//sqlNode.accept(new SqlBasicVisitor<Void>());
			System.out.println(sqlNode.getKind());
			sqlNode.getKind();
			//CalciteCatalogReader catalogReader = new CalciteCatalogReader();
		} catch (SqlParseException e) {

			e.printStackTrace();
		}*/
		querySelect qs = new querySelect("Select a from A where a = b");
		qs.ExtractClausesWhere();
		System.out.println(qs.getQueryResult());

	}
}
