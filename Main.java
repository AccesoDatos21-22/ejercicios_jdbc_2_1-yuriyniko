import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;

public class Main {

    public static void main(String[] args) {

        try {
            Cafes cafes = new Cafes();
            cafes.insertar("Cafetito", 150, 1.0f, 100, 1000);
            cafes.insertar("tacilla", 49, 2.0f, 101, 1001);
            cafes.verTabla();
            System.out.println();
            cafes.buscar("tacilla");
            System.out.println();
            cafes.cafesPorProveedor(49);
            System.out.println();
            cafes.borrar("tacilla");
            cafes.verTabla();

            System.out.println("Cerramos cafes");
           // cafes.cerrar();

            Libros lib = new Libros();

            Libro l1 = new Libro(12345, "Sistemas Operativos", "Tanembaun", "Informatica", 156, 3);
            Libro l2 = new Libro(12453, "Minix", "Stallings", "Informatica", 345, 4);
            Libro l3 = new Libro(1325, "Linux", "Richard Stallman", "FSF", 168, 10);
            Libro l4 = new Libro(1725, "Java", "Juan Garcia", "Programacion", 245, 9);

            lib.anadirLibro(l1);
            lib.anadirLibro(l2);
            lib.anadirLibro(l3);
            lib.anadirLibro(l4);

            System.out.println("Lista libros");

            lib.verCatalogo();

            System.out.println("Campos lobro");

            String[] campos = lib.getCamposLibro();

            for (String camp : campos) {
                System.out.println(camp);
            }


            System.out.println("Libros por isbn");
            lib.obtenerLibro(1325);
            System.out.println("Actualizamos paginas");
            lib.actualizarCopias(l2);
            System.out.println("Borramos");
            lib.borrar(l1);

            System.out.println("Listamos");

            lib.verCatalogo();

            System.out.println("Cerramos libros");

            lib.cerrar();


        } catch (AccesoDatosException e) {
            e.printStackTrace();
        }
    }

}