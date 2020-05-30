package QueryTraitement;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOrderBy;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.sql.util.SqlVisitor;
import com.google.common.base.Preconditions;



public class CalciteParser {
    public static SqlNode parse(String sql) throws SqlParseException {
        SqlParser.ConfigBuilder parserBuilder = SqlParser.configBuilder();
        SqlParser sqlParser = SqlParser.create(sql, parserBuilder.build());
        return sqlParser.parseQuery();
    }

    public static SqlNode getOnlySelectNode(String sql) {
        SqlNodeList selectList = null;
        try {
            selectList = ((SqlSelect) CalciteParser.parse(sql)).getSelectList();
        } catch (SqlParseException e) {
            throw new RuntimeException(
                    "Failed to parse expression \'" + sql + "\', please make sure the expression is valid", e);
        }

        Preconditions.checkArgument(selectList.size() < 6,
                "Expression is invalid because size of select list exceeds six");

        return selectList;
    }

    public static SqlNode getExpNode(String expr) {
        return getOnlySelectNode("select " + expr + " from t");
    }

    public static String getLastNthName(SqlIdentifier id, int n) {
        //n = 1 is getting column
        //n = 2 is getting table's alias, if has.
        //n = 3 is getting database name, if has.
        return id.names.get(id.names.size() - n).replace("\"", "").toUpperCase(Locale.ROOT);
    }

    public static void ensureNoAliasInExpr(String expr) {
        SqlNode sqlNode = getExpNode(expr);

        SqlVisitor sqlVisitor = new SqlBasicVisitor() {
            @Override
            public Object visit(SqlIdentifier id) {
                if (id.names.size() > 1) {
                    throw new IllegalArgumentException(
                            "Column Identifier in the computed column expression should only contain COLUMN");
                }
                return null;
            }
        };

        sqlNode.accept(sqlVisitor);
    }
    
    /**
     * Check if the SELECT query has join operation
     */
    public static SqlSelect hasJoinOperation(String selectQuery) {
     
    	if (selectQuery == null || selectQuery.length() == 0) {
    		return null;
    		}
   
      SqlParser sqlParser = SqlParser.create(selectQuery);
      try {
    	 

        SqlNode all = sqlParser.parseQuery();
        System.out.println(" which is not supported here");
        SqlSelect query;
       
        if (all instanceof SqlSelect) {
          query = (SqlSelect) all;
          return query;
          
        } else if (all instanceof SqlOrderBy) {
          query = (SqlSelect) ((SqlOrderBy) all).query;
          return query;
        } else {
        	System.out.println("The select query is type of " + all.getClass() + " which is not supported here");
        }
      } catch (SqlParseException e) {
    	  System.out.println("Type de requette non détecté : ");
        return null;
      }
	return null;
    }


    public static void descSortByPosition(List<SqlIdentifier> sqlIdentifiers) {
        Collections.sort(sqlIdentifiers, new Comparator<SqlIdentifier>() {
            @Override
            public int compare(SqlIdentifier o1, SqlIdentifier o2) {
                int linegap = o2.getParserPosition().getLineNum() - o1.getParserPosition().getLineNum();
                if (linegap != 0)
                    return linegap;

                return o2.getParserPosition().getColumnNum() - o1.getParserPosition().getColumnNum();
            }
        });
    }

      
   
    }
