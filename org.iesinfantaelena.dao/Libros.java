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

public class Libros {

	// inicializamos
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pstmt;

	// Consultas a realizar en BD

	private static final String INSERT_LIBROS_QUERY = "insert into libros values (?,?,?,?,?,?)";

	private static final String CREATE_TABLE_LIBROS = "create table if not exists libros (isbn integer not null,titulo varchar(50) not null,autor varchar(50) not null,editorial varchar(25) not null,paginas integer not null,copias integer not null,constraint isbn_pk primary key (isbn));";


	/**
	 * Constructor: inicializa conexión
	 * 
	 * @throws AccesoDatosException
	 */
	
	public Libros() throws AccesoDatosException {
		con=null;
		stmt=null;
		rs=null;
		pstmt=null;
		try {
			// Obtenemos la conexión
			con = new Utilidades().getConnection();
			stmt.executeQuery(CREATE_TABLE_LIBROS);

		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());
			throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
		} finally {
			liberar();
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
	 * @throws SQLException
	 */
	
	public List<Libro> verCatalogo() throws AccesoDatosException {
	
		return null;

	}

    /**
     * Actualiza el numero de copias para un libro
	 *
     * @throws AccesoDatosException
     */
	
	public void actualizarCopias(Libro libro) throws AccesoDatosException {
		
	}

    /**
     * Añade un nuevo libro a la BD
	 *
     * @throws AccesoDatosException
     */

	public void anadirLibro(Libro libro) throws AccesoDatosException {
		try {
			pstmt = con.prepareStatement(INSERT_LIBROS_QUERY);

			pstmt.setInt(1, libro.getISBN());
			pstmt.setString(2, libro.getTitulo());
			pstmt.setString(3, libro.getAutor());
			pstmt.setString(4, libro.getEditorial());
			pstmt.setInt(5, libro.getPaginas());
			pstmt.setInt(6, libro.getCopias());

			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
		} finally {
			liberar();
		}
	}

	/**
	 * Borra un libro por ISBN
	 *
	 * @throws AccesoDatosException
	 */

	public void borrar(Libro libro) throws AccesoDatosException {
		
		
	}
	
	/**
	 * Devulve los nombres de los campos de BD
	 *
	 * @throws AccesoDatosException
	 */

	public String[] getCamposLibro() throws AccesoDatosException {
       
    return null;
	}

	public void obtenerLibro(int ISBN) throws AccesoDatosException {
		
	}

}

