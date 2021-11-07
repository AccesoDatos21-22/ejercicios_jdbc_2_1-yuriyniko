package org.iesinfantaelena.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.utils.Utilidades;

/**
 * @descrition
 * @author Carlos
 * @date 23/10/2021
 * @version 1.0
 * @license GPLv3
 */

public class Cafes   {

  	private static Connection con;
	// Consultas a realizar en BD
		private static final String SELECT_CAFES_QUERY = "select CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL from CAFES";
		private static final String SEARCH_CAFE_QUERY = "select * from CAFES WHERE CAF_NOMBRE= ?";
		private static final String INSERT_CAFE_QUERY = "insert into CAFES values (?,?,?,?,?)";
		private static final String DELETE_CAFE_QUERY = "delete from CAFES WHERE CAF_NOMBRE = ?";
		private static final String SEARCH_CAFES_PROVEEDOR = "select * from CAFES,PROVEEDORES WHERE CAFES.PROV_ID= ? AND CAFES.PROV_ID=PROVEEDORES.PROV_ID";

    private static final String CREATE_TABLE_PROVEEDORES ="create table if not exists proveedores (PROV_ID integer NOT NULL, PROV_NOMBRE varchar(40) NOT NULL, CALLE varchar(40) NOT NULL, CIUDAD varchar(20) NOT NULL, PAIS varchar(2) NOT NULL, CP varchar(5), PRIMARY KEY (PROV_ID));";

    private static final String CREATE_TABLE_CAFES ="create table if not exists CAFES (CAF_NOMBRE varchar(32) NOT NULL, PROV_ID int NOT NULL, PRECIO numeric(10,2) NOT NULL, VENTAS integer NOT NULL, TOTAL integer NOT NULL, PRIMARY KEY (CAF_NOMBRE), FOREIGN KEY (PROV_ID) REFERENCES PROVEEDORES(PROV_ID));";

    	/**
	 * Constructor: inicializa conexión
	 * 
	 * @throws AccesoDatosException
	 */
	
	public Cafes() {
		
			Statement stmt = null;

			try {
				con = new Utilidades().getConnection();
        stmt = con.createStatement(); 

        stmt.executeUpdate(CREATE_TABLE_PROVEEDORES);       

        stmt.executeUpdate(CREATE_TABLE_CAFES);  

        stmt.executeUpdate("insert into proveedores values(49, 'PROVerior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460');");
        stmt.executeUpdate("insert into proveedores values(101, 'Acme, Inc.', '99 mercado CALLE', 'Groundsville', 'CA', '95199');");
        stmt.executeUpdate("insert into proveedores values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966');");
			
			} catch (IOException e) {
				// Error al leer propiedades
				// En una aplicación real, escribo en el log y delego
				//System.err.println(e.getMessage());
			
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log y delego
				//Utilidades.printSQLException(sqle);
			

			} finally {
				try {
					// Liberamos todos los recursos pase lo que pase
					if (stmt != null) {
						stmt.close();
					}					
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log, no delego porque
					// es error al liberar recursos
					Utilidades.printSQLException(sqle);
				}
			}
	}

		/**
		 * Metodo que muestra por pantalla los datos de la tabla cafes
		 * 
		 * @param con
		 * @throws SQLException
		 */
		public void verTabla() throws AccesoDatosException {
	
			/* Sentencia sql */
			Statement stmt = null;
			/* Conjunto de Resultados a obtener de la sentencia sql */
			ResultSet rs = null;
			try {			
				// Creación de la sentencia
				stmt = con.createStatement();
				// Ejecución de la consulta y obtención de resultados en un
				// ResultSet
				rs = stmt.executeQuery(SELECT_CAFES_QUERY);

				// Recuperación de los datos del ResultSet
				while (rs.next()) {
					String coffeeName = rs.getString("CAF_NOMBRE");
					int supplierID = rs.getInt("PROV_ID");
					float PRECIO = rs.getFloat("PRECIO");
					int VENTAS = rs.getInt("VENTAS");
					int total = rs.getInt("TOTAL");
					System.out.println(coffeeName + ", " + supplierID + ", "
							+ PRECIO + ", " + VENTAS + ", " + total);
				}

			
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log y delego
				// System.err.println(sqle.getMessage());
				Utilidades.printSQLException(sqle);
				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");
			} finally {
				try {
					// Liberamos todos los recursos pase lo que pase
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}				
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log, no delego porque
					// es error al liberar recursos
					Utilidades.printSQLException(sqle);
				}
			}

		}

		/**
		 * Mótodo que busca un cafe por nombre y muestra sus datos
		 *
		 * @param nombre
		 */
		public void buscar(String nombre) throws AccesoDatosException {
		
			/* Sentencia sql */
			PreparedStatement stmt = null;
			/* Conjunto de Resultados a obtener de la sentencia sql */
			ResultSet rs = null;
			try {			
				// Creación de la sentencia
				stmt = con.prepareStatement(SEARCH_CAFE_QUERY);
				stmt.setString(1, nombre);
				// Ejecución de la consulta y obtención de resultados en un
				// ResultSet
				rs = stmt.executeQuery();

				// Recuperación de los datos del ResultSet
				if (rs.next()) {
					String coffeeName = rs.getString("CAF_NOMBRE");
					int supplierID = rs.getInt("PROV_ID");
					float PRECIO = rs.getFloat("PRECIO");
					int VENTAS = rs.getInt("VENTAS");
					int total = rs.getInt("TOTAL");
					System.out.println(coffeeName + ", " + supplierID + ", "
							+ PRECIO + ", " + VENTAS + ", " + total);
				}

		
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log y delego
				Utilidades.printSQLException(sqle);
				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");
			} finally {
				try {
					// Liberamos todos los recursos pase lo que pase
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}				
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log, no delego porque
					// es error al liberar recursos
					Utilidades.printSQLException(sqle);
				}
			}

		}

		/**
		 * Mótodo para insertar una fila
		 * 
		 * @param nombre
		 * @param provid
		 * @param precio
		 * @param ventas
		 * @param total
		 * @return
		 */
		public void insertar(String nombre, int provid, float precio, int ventas,
				int total) throws AccesoDatosException {
		
			/* Sentencia sql */
			PreparedStatement stmt = null;

			try {
			
				stmt = con.prepareStatement(INSERT_CAFE_QUERY);
				stmt.setString(1, nombre);
				stmt.setInt(2, provid);
				stmt.setFloat(3, precio);
				stmt.setInt(4, ventas);
				stmt.setInt(5, total);
				// Ejecución de la inserción
				stmt.executeUpdate();

		
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log y delego
				Utilidades.printSQLException(sqle);
				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");

			} finally {
				try {
					// Liberamos todos los recursos pase lo que pase
					if (stmt != null) {
						stmt.close();
					}
				
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log, no delego porque
					// es error al liberar recursos
					Utilidades.printSQLException(sqle);
				}
			}

		}

		/**
		 * Mótodo para borrar una fila dado un nombre de cafó
		 * 
		 * @param nombre
		 * @return
		 */
		public void borrar(String nombre) throws AccesoDatosException {
		
			/* Sentencia sql */
			PreparedStatement stmt = null;

			try {
				// Creación de la sentencia
				stmt = con.prepareStatement(DELETE_CAFE_QUERY);
				stmt.setString(1, nombre);
				// Ejecución del borrado
				stmt.executeUpdate();
        System.out.println("café "+nombre+ " ha sido borrado.");
		
			} catch (SQLException sqle) {
				// En una aplicación real, escribo en el log y delego
				Utilidades.printSQLException(sqle);
				throw new AccesoDatosException(
						"Ocurrió un error al acceder a los datos");

			} finally {
				try {
					// Liberamos todos los recursos pase lo que pase
					if (stmt != null) {
						stmt.close();
					}
				
				} catch (SQLException sqle) {
					// En una aplicación real, escribo en el log, no delego porque
					// es error al liberar recursos
					Utilidades.printSQLException(sqle);
				}

			}

		}

		/**
		 * Mótodo que busca un cafe por nombre y muestra sus datos
		 *
		 * @param nombre
		 */
		public void cafesPorProveedor(int provid) throws AccesoDatosException {
		

		}
}
