package org.iesinfantaelena.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;
import org.iesinfantaelena.utils.Utilidades;


/**
 * @descrition
 * @author Carlos
 * @date 23/10/2021
 * @version 1.0
 * @license GPLv3
 */

public class Libros {

	// Consultas a realizar en BD


	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	/**
	 * Constructor: inicializa conexión
	 * 
	 * @throws AccesoDatosException
	 */
	
	public Libros() throws AccesoDatosException {
		try {
			// Obtenemos la conexión
			this.con = new Utilidades().getConnection();
			this.stmt = null;
			this.rs = null;
			this.pstmt = null;
		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		}
	}

	
	/**
	 * Método para cerrar la conexión
	 * 
	 * @throws AccesoDatosException
	 */
	public void cerrar() {
					
			if (con != null) {
				Utilidades.closeConnection(con);
			}
		
	}

	
	/**
	 * Método para liberar recursos
	 * 
	 * @throws AccesoDatosException
	 */
	private void liberar() {
		try {
			// Liberamos todos los recursos pase lo que pase
			//Al cerrar un stmt se cierran los resultset asociados. Podíamos omitir el primer if. Lo dejamos por claridad.
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}			
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log, no delego porque
			// es error al liberar recursos
			Utilidades.printSQLException(sqle);
		}
	}

	/**
	 * Metodo que muestra por pantalla los datos de la tabla cafes
	 * 
	 * @param con
	 * @throws SQLException
	 */
	
	public List<Libro> verCatalogo() throws AccesoDatosException {
	
		return null;

	}

    /**
     * Actualiza el numero de copias para un libro
     * @param isbn
     * @param copias
     * @throws AccesoDatosException
     */
	
	public void actualizarCopias(Libro libro) throws AccesoDatosException {
		
	}

	
    /**
     * Añade un nuevo libro a la BD
     * @param isbn
     * @param titulo
     * @param autor
     * @param editorial
     * @param paginas
     * @param copias
     * @throws AccesoDatosException
     */
	public void anadirLibro(Libro libro) throws AccesoDatosException {
		
	
	}

	/**
	 * Borra un libro por ISBN
	 * @param isbn
	 * @throws AccesoDatosException
	 */

	public void borrar(Libro libro) throws AccesoDatosException {
		
		
	}
	
	/**
	 * Devulve los nombres de los campos de BD
	 * @return
	 * @throws AccesoDatosException
	 */

	public String[] getCamposLibro() throws AccesoDatosException {
       
    return null;
	}


	public void obtenerLibro(int ISBN) throws AccesoDatosException {
		
	}




}

