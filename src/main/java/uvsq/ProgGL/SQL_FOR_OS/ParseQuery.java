package uvsq.ProgGL.SQL_FOR_OS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class ParseQuery {
	private static List<String> extrairesClausesFrom(SqlNode node) {
		final List<String> tables = new ArrayList<String>();

		// Si on a un orderBy dans la requete.
		if (node.getKind().equals(SqlKind.ORDER_BY)) {
			// Retrouver le vrai Select.
			//node = ((SqlSelect) ((SqlOrderBy) node).query).getFrom();
		} else {
			
			//node = ((SqlSelect) node).getFrom();
			
		}

		if (node == null) {
			return tables;
		}

		// si ya qu'une seule close.
		if (node.getKind().equals(SqlKind.AS)) {
			tables.add(((SqlBasicCall) node).operand(1).toString());
			return tables;
		}

		// si on a plus d'une seule clause.
		if (node.getKind().equals(SqlKind.JOIN)) {
			final SqlJoin from = (SqlJoin) node;

			// si on a le alias i.e:AS je prends l'alias.
			if (from.getLeft().getKind().equals(SqlKind.AS)) {
				tables.add(((SqlBasicCall) from.getLeft()).operand(1).toString());
			} else {
				// Case when more than 2 data sets are in the query.
				SqlJoin left = (SqlJoin) from.getLeft();

				// Traverse until on get un AS.
				while (!left.getLeft().getKind().equals(SqlKind.AS)) {
					tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
					left = (SqlJoin) left.getLeft();
				}

				tables.add(((SqlBasicCall) left.getLeft()).operand(1).toString());
				tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
			}

			tables.add(((SqlBasicCall) from.getRight()).operand(1).toString());
			return tables;
		}

		return tables;
	}

	private static Map<String, String> extrairesClausesWhere(SqlNode node) {
		final Map<String, String> Table = new HashMap<String,String>();

		// si on a un order by dans la query
		if (node.getKind().equals(SqlKind.ORDER_BY)) {
			// Retrouver exact select.
			//node = ((SqlOrderBy) node).query;
		}

		if (node == null) {
			return Table;
		}

		final SqlBasicCall where = (SqlBasicCall) ((SqlSelect) node).getWhere();

		if (where != null) {
			// Si on a une seule clause where
			if (where.operand(0).getKind().equals(SqlKind.IDENTIFIER)
					&& where.operand(1).getKind().equals(SqlKind.LITERAL)) {
				Table.put(where.operand(0).toString(), where.operand(1).toString());
				return Table;
			}

			final SqlBasicCall sqlBasicCallRight = where.operand(1);
			SqlBasicCall sqlBasicCallLeft = where.operand(0);

			// je prends l'identifiant a gauche iteeration à gauche
			while (!sqlBasicCallLeft.operand(0).getKind().equals(SqlKind.IDENTIFIER)
					&& !sqlBasicCallLeft.operand(1).getKind().equals(SqlKind.LITERAL)) {
				Table.put(((SqlBasicCall) sqlBasicCallLeft.operand(1)).operand(0).toString(),
						((SqlBasicCall) sqlBasicCallLeft.operand(1)).operand(1).toString());
				sqlBasicCallLeft = sqlBasicCallLeft.operand(0); // Move to next where condition.
			}

			Table.put(sqlBasicCallLeft.operand(0).toString(), sqlBasicCallLeft.operand(1).toString());
			Table.put(sqlBasicCallRight.operand(0).toString(), sqlBasicCallRight.operand(1).toString());
			return Table;
		}

		return Table;
	}

	public static void main(String[] args) throws SqlParseException {
		final String query = "SELECT e.first_name AS FirstName, s.salary AS Salary from employee AS e join salary AS s on e.emp_id=s.emp_id where e.organization = 'Tesla' and s.zizi = 'Tesla' and e.zizi = 'Tesla'";
		final SqlParser parser = SqlParser.create(query);
		final SqlNode sqlNode = parser.parseQuery();
		final SqlSelect sqlSelect = (SqlSelect) sqlNode;
		
		//SqlNodeList ListSelect= parser.parseStmtList();
		
		SqlNodeList ListNode = sqlSelect.getSelectList();
		System.out.println(ListNode.get(0));
		
		final SqlJoin from = (SqlJoin) sqlSelect.getFrom();

		// extraires les tables utilise dans from
		 final List<String> tables = extrairesClausesFrom(from);
		//System.out.println(tables.get(1));

		// extraires les alias ou les vrai tables de la clause where
		final Map<String, String> whereClauses = extrairesClausesWhere(sqlSelect);
		//System.out.println(whereClauses);
	}
}
