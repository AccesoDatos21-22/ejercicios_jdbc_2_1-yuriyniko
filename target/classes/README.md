### Ejercicios JDBC 2.1 Yuriy Butko

###***¿Dónde llamas a cerrar y liberar? ¿Por qué?***
    Cerrar:

    Llamaría a este método principalmente en el Main cuando
    quisiera cerrar la conexión con una base de datos o en
    algún otro método en la que estaría seguro que sería la
    ultimo que realice.

    Liberar:

    Lo llamaría al final de cada método para así borrar todo    
    rastro de datos viejos y que no se los lleve a otros métodos.
###***Piensa en las ventajas e inconvenientes de esta nueva versión de Cafes.java***
    
    Una ventaja seria la reutilización de los métodos limpiar y cerrar
    porque se pueden poner en todos los métodos
 
    