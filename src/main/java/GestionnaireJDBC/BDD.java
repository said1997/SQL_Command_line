package GestionnaireJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BDD {
	
	
	/**
	 * Nom de la Bdd(dessin)
	 */
    private static String Bdd;
    /**
     * Connexion à la bdd
     */
    private static Connection connection;
    
    /**
     * Creation de la BDD.
     * @throws SQLException en cas d'erreur de creation
     */
    public static void Bdd()  {
        
        try {
            connection = DriverManager.getConnection("jdbc:derby:memory:"+Bdd+";create=true");
            connection.close();
        } catch (SQLException e) {
        	System.err.println("Impossible de Créer la JDBC ni d'accéder suite à une connexion encore "
        			+ "ouverte dans le fichier derby.log \n"
        			+ "du à un arrêt brutal de l'application (sans commande exit)\n");
        	System.err.println("Veuillez relancer l'application et créer une nouvelle BDD");
        	System.exit(0);
        }
    }
    
    /**
     * Se connecter a la BDD.
     * @return connection a la BDD
     */
    
    public static Connection Connect() {
        try {
            return DriverManager.getConnection("jdbc:derby:memory:"+Bdd+";create=false");
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
     * Retourner le nom de la BDD qui identifie un dessin
     * @return nom de la BDD
     * @throws SQLException
     */
    public static String getNomBdd() throws SQLException {
    	return Bdd;
    }
    
    /**
     * modifier le nom de la base de donnée.
     * @param name nouveau nom
     */
    public static void setNomBdd(String name) {
        Bdd = name + "";
    }
    
    /**
     * Creation des tables dans la BDD.
     * @throws Exception en cas  d'erreur de creation
     */
    public static void resetBddTables() throws Exception {
        Connection connection = BDD.Connect();
        BDD.SupprimerTables(connection);
        BDD.CreateTableFigure(connection);
        BDD.CreateTableTriangle(connection);
        BDD.CreateTableCarre(connection);
        BDD.CreateTableRectangle(connection);
        CreateTableCercle(connection);
        CreateTableGroupeFigures(connection);
        CreateTableRelation(connection);
        connection.close();
    }
    
    /**
     * supprime les tables.
     * @param connect connection a la bdd
     */
    private static void SupprimerTables(final Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.execute("drop table RelationComposition");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table GroupeFigures");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table Cercle");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table Rectangle");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table Carre");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table Triangle");
        } catch (SQLException e) {
        }
        try {
            statement.execute("drop table Figure");
        } catch (SQLException e) {
        }
    }
    
    /**
     * Creation de  la table Figure.
     * @param connection connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableFigure(Connection connection, String NameTable, List<String> attr)throws SQLException {
    	Map<String, String > map = new HashMap<String, String>();
    	map.put(attributsOfFile.NOM.get(), "? varchar(20) primary key, ");
        String T = "CREATE table "+ NameTable +" ("
                + "? varchar(20) primary key,"
                + "HG_x int, HG_y int,"
                + "longueur int,"
                + "foreign key (figure) references Figure (figure))";
        Statement statement = connection.createStatement();
        statement.execute(T);
    }
    
    /**
     * Creation de la table Carre.
     * @param connection connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableCarre(Connection connection)
            throws SQLException {
        String T = "CREATE table Carre ("
                + "figure varchar(20) primary key,"
                + "HG_x int, HG_y int,"
                + "longueur int,"
                + "foreign key (figure) references Figure (figure))";
        Statement stat = connection.createStatement();
        stat.execute(T);
    }
    
    /**
     * Creation de la table Cercle.
     * @param connection a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableCercle(Connection connect)
            throws SQLException {
        String table = "create table Cercle ("
                + "figure varchar(20) primary key,"
                + "centre_x int,"
                + "centre_y int,"
                + "rayon int,"
                + "foreign key (figure) references Figure (figure)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    /**
     * Creation la table GroupeFigures.
     * @param connection connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableGroupeFigures(final Connection connect)
            throws SQLException {
        String table = "create table GroupeFigures ("
                + "figure varchar(20) primary key,"
                + "foreign key (figure) references Figure (figure) )";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    /**
     * Creation de la table Triangle.
 	   @param connection connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableTriangle(final Connection connection)
            throws SQLException {
        String table = "create table Triangle ("
                + "figure varchar(20) primary key,"
                + "point1_x int, point1_y int,"
                + "point2_x int, point2_y int,"
                + "point3_x int, point3_y int,"
                + "foreign key (figure) references Figure (figure) )";
        Statement stat = connection.createStatement();
        stat.execute(table);
    }
    
    /**
     * Creation de  la table de composition groupeFigures.
     * @param connection connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableRelation(final Connection connection)
            throws SQLException {
        String table = "create table RelationComposition ("
                + "IdGroupe varchar(20),"
                + "IdComposant varchar(20),"
                + "primary key (IdGroupe, IdComposant),"
                + "foreign key (IdGroupe) references GroupeFigures (figure),"
                + "foreign key (IdComposant) references Figure (figure) )";
        Statement stat = connection.createStatement();
        stat.execute(table);
    }
    
    /**
     * Creation de la table Rectangle.
     * @param connect connexion a la BDD
     * @throws SQLException en cas d'erreur de creation
     */
    private static void CreateTableRectangle(Connection connection)
            throws SQLException {
        String table = "create table Rectangle ("
                + "figure varchar(20) primary key,"
                + "HG_x int, HG_y int,"
                + "longueur int,"
                + "largeur int,"
                + "foreign key (figure) references Figure (figure) )";
        Statement stat = connection.createStatement();
        stat.execute(table);
    }
    
	
}