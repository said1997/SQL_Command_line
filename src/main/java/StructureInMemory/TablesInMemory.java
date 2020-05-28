package StructureInMemory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;


public abstract class TablesInMemory {

	/**
	 * Nom de la Structure en mémoire 
	 */
	private static String StructInMemory;
	/**
	 * Connexion à la structure
	 */
	private static Connection connection;

	/**
	 * Creation de la structure.
	 * @throws SQLException en cas d'erreur de creation
	 */
	public static void Create()  {

		try {
			setNomStruct("StructTable");
			connection = DriverManager.getConnection("jdbc:derby:memory:"+StructInMemory+";create=true");

		} catch (SQLException e) {
			System.err.println("Impossible de Créer la Table représentant un dossier et ses attributs en mémoire \n"
					+ "du à un arrêt brutal de l'application (sans commande exit)\n");
			System.err.println("Veuillez relancer l'application");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Se connecter a la Structure.
	 * @return connection a la Structure
	 */

	public static Connection Connect() {
		try {
			return DriverManager.getConnection("jdbc:derby:memory:"+StructInMemory+";create=false");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Fermer la connexion
	 * @throws SQLException 
	 */
	public static void CloseConnexion() throws SQLException {
		connection.close();
	}
	/**
	 * Retourner le nom qui identifie la structure en mémoire
	 * @return nom de la structure
	 * @throws SQLException
	 */
	public static String getNomStruct() throws SQLException {
		return StructInMemory;
	}

	/**
	 * Donner un nom à la structure à créer en mémoire.
	 * @param name nom
	 */
	public static void setNomStruct(String name) {
		StructInMemory = name + "";
	}

	/**
	 * Création de la table Folder en mémoire pour chaque dossier de la clause From.
	 * @throws Exception en cas  d'erreur de creation
	 */
	public static void CreateTableForAttrFrom(Connection conn,String NameTable, List<String> attribut) throws Exception {

		TablesInMemory.CreateTable(conn, NameTable, attribut);

		//connection.close();
	}

	/* 
	 * supprime les tables.
	 * @param connect connection a la bdd
	 *
    private static void SupprimerTables(Connection conn,String nameTable) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.execute("drop table " +nameTable.replace("/", ""));
        } catch (SQLException e) {
        }

    }*/

	/**
	 * Creation de  la table Figure.
	 * @param connection connexion a la BDD
	 * @throws SQLException en cas d'erreur de creation
	 */
	private static void CreateTable(Connection conn, String NameTable, List<String> attribut)throws SQLException {
		Map<String, String > map = new HashMap<String, String>();
		map.put(attributsOfFile.NOM.get(), attributsOfFile.NOM.get()+" varchar(80),");
		map.put(attributsOfFile.TYPE.get(), attributsOfFile.TYPE.get()+" varchar(80),");
		map.put(attributsOfFile.SIZE.get(), attributsOfFile.SIZE.get()+" int,");
		map.put(attributsOfFile.ACCESRIGHTS.get(), attributsOfFile.ACCESRIGHTS.get()+" varchar(80),");
		map.put(attributsOfFile.DATELACCES.get(), attributsOfFile.DATELACCES.get()+" varchar(80),");
		map.put(attributsOfFile.DATELMODIFICATION.get(), attributsOfFile.DATELMODIFICATION.get()+" varchar(80),");

		String query = "CREATE TABLE "+ NameTable.replace("/", "") +" ( ";

		for(String Attr : attribut) {
			query = query + map.get(Attr);
		}

		if(query.endsWith(",")) 
			query = query.substring(0, query.length() - 1);

		query = query + " )";
		System.out.println(query);
		Statement statement = conn.createStatement();
		statement.execute(query);
	}

	/**
	 * Ajout d'un ligne à une TableEnMémoire
	 * @throws SQLException 
	 */

	public static void AddLineToTable(Connection conn,String Table, String line , List<String> attribut, String quryToExecute) {

		String attr="";
		String ValuePrep="";
		Map<String, String > map = new HashMap<String, String>();
		map.put(attributsOfFile.NOM.get(), attributsOfFile.NOM.get()+",");
		map.put(attributsOfFile.TYPE.get(), attributsOfFile.TYPE.get()+",");
		map.put(attributsOfFile.SIZE.get(), attributsOfFile.SIZE.get()+",");
		map.put(attributsOfFile.ACCESRIGHTS.get(), attributsOfFile.ACCESRIGHTS.get()+",");
		map.put(attributsOfFile.DATELACCES.get(), attributsOfFile.DATELACCES.get()+",");
		map.put(attributsOfFile.DATELMODIFICATION.get(), attributsOfFile.DATELMODIFICATION.get()+",");

		for(String A : attribut) {
			ValuePrep = ValuePrep + "? ,";
			attr = attr + map.get(A);
		}


		if(line.endsWith(",")) 
			ValuePrep = ValuePrep.substring(0, ValuePrep.length() - 1);
		if(line.endsWith(",")) 
			line = line.substring(0, line.length() - 2);
		if(attr.endsWith(",")) 
			attr = attr.substring(0, attr.length() - 1);

		String[] value = line.split(",");
		//System.out.println("INSERT INTO "+Table.replace("/", "")+" ("+ attr +") VALUES("+ValuePrep+")");

		PreparedStatement prepare;
		try {
			prepare = conn.prepareStatement("INSERT INTO "+Table.replace("/", "")+" ("+ attr +") VALUES("+ValuePrep+")");
			for(int i =0 ; i< value.length;i++) {
				int n = i+1;
				//System.out.println("insersion : value : " + n  +" " + value[i] + " ");
				if(!value[i].endsWith(".")) {
					prepare.setString(n, value[i]);
				}
				else {
					value[i] = value[i].replace(" ", "");
					value[i] = value[i].substring(0, value[i].length() - 1);
					int tointeger=0;
					try {
						tointeger = Integer.parseInt(value[i]);
					}
					catch (NumberFormatException e)
					{	/*"\nChaine de caractères non transformée en int "
							+ "dans la fonction AddLineToTable car un nombre "
							+ "dans une chaine doit de terminer par un point (.)\n"
							+ "Merci de mettre un point pour chaque nombre dans "
							+ "la classe OsTraitement méthode (convertSelectAttributes).*/
						tointeger = 0;
					}
					prepare.setInt(n, tointeger);
				}
			}
			//System.out.println("\n nouvelle ligne");
			prepare.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}