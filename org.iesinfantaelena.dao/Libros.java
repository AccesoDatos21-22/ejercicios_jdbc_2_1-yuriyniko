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
    private ResultSetMetaData rsmd;

    // Consultas a realizar en BD

    private static final String INSERT_LIBROS_QUERY = "insert into libros values (?,?,?,?,?,?)";
    private static final String SELECT_LIBROS_QUERY = "select * from libros";
    private static final String DELETE_LIBROS_QUERY = "delete from libros WHERE isbn = ?";
    private static final String UPDATE_LIBROS_QUERY = "update libros set copias=? WHERE isbn = ?";
    private static final String SEARCH_LIBROS_QUERY = "SELECT * from libros WHERE isbn = ?";
    private static final String SELECT_CAMPOS_QUERY = "SELECT * FROM libros LIMIT 1";

    private static final String CREATE_TABLE_LIBROS = "create table if not exists libros (isbn integer not null,titulo varchar(50) not null,autor varchar(50) not null,editorial varchar(25) not null,paginas integer not null,copias integer not null,constraint isbn_pk primary key (isbn));";


    /**
     * Constructor: inicializa conexión
     *
     * @throws AccesoDatosException
     */

    public Libros() throws AccesoDatosException {
        rs = null;
        pstmt = null;
        try {
            // Obtenemos la conexión
            con = new Utilidades().getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate(CREATE_TABLE_LIBROS);

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
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
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
     * @throws SQLException
     */

    public List<Libro> verCatalogo() throws AccesoDatosException {
        List<Libro> libros = new ArrayList<>();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_LIBROS_QUERY);

            while (rs.next()) {
                Libro temp = new Libro();
                temp.setISBN(rs.getInt("isbn"));
                temp.setTitulo(rs.getString("titulo"));
                temp.setAutor(rs.getString("autor"));
                temp.setEditorial(rs.getString("editorial"));
                temp.setPaginas(rs.getInt("paginas"));
                temp.setCopias(rs.getInt("copias"));

                libros.add(temp);
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }

        for (Libro lib : libros) {
            System.out.println(lib.toString());
        }


        return libros;
    }

    /**
     * Actualiza el numero de copias para un libro
     *
     * @throws AccesoDatosException
     */

    public void actualizarCopias(Libro libro) throws AccesoDatosException {
        try {
            int numpag = 100;

            pstmt = con.prepareStatement(UPDATE_LIBROS_QUERY);
            pstmt.setInt(2, libro.getISBN());
            pstmt.setInt(1, numpag);

            pstmt.executeUpdate();
            System.out.println("Libro" + libro.getISBN() + " ha .sido modificado");

        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
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
        try {
            pstmt = con.prepareStatement(DELETE_LIBROS_QUERY);
            pstmt.setInt(1, libro.getISBN());

            pstmt.executeUpdate();
            System.out.println("Libro" + libro.getISBN() + " ha sido borrado.");
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

    /**
     * Devulve los nombres de los campos de BD
     *
     * @throws AccesoDatosException
     */

    public String[] getCamposLibro() throws AccesoDatosException {
        rsmd = null;
        String[] campos = null;

        try {
            pstmt = con.prepareStatement(SELECT_CAMPOS_QUERY);

            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();

            int columns = rsmd.getColumnCount();

            campos = new String[columns];

            for (int i = 0; i < columns; i++) {
                campos[i] = rsmd.getColumnLabel(i + 1);
            }
            return campos;

        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log y delego
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

    /**
     * Devulve los libros por isbn
     *
     * @throws AccesoDatosException
     */

    public void obtenerLibro(int ISBN) throws AccesoDatosException {
        try {
            pstmt = con.prepareStatement(SEARCH_LIBROS_QUERY);
            pstmt.setInt(1, ISBN);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                int isbn = rs.getInt("isbn");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                int paginas = rs.getInt("paginas");
                int copias = rs.getInt("copias");

                System.out.println(isbn + ", " + titulo + ", "
                        + autor + ", " + editorial + ", " + paginas + ", " + copias);
            }
        } catch (SQLException sqle) {
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException("Ocurrió un error al acceder a los datos");
        } finally {
            liberar();
        }
    }

}

