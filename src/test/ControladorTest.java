package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControladorTest {

    private final String PATH = "alumnos.txt";

    @BeforeEach
    void setUp() {
        // Limpiamos el fichero antes de cada test para que sea una prueba "limpia"
        File file = new File(PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test: Agregar y Buscar Alumno en Memoria")
    void testAgregarYBuscar() {
        Controlador ctrl = new Controlador();
        Alumno a = new Alumno("Juan", "11111111A", "1º DAM");
        ctrl.agregar(a);
        
        Alumno encontrado = ctrl.buscar("11111111A");
        assertNotNull(encontrado, "El alumno debería encontrarse tras agregarlo.");
        assertEquals("Juan", encontrado.getNombre());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Persistencia en Fichero (Escritura y Lectura)")
    void testPersistenciaFichero() {
        // 1. Creamos controlador y guardamos
        Controlador ctrl1 = new Controlador();
        ctrl1.agregar(new Alumno("Maria", "22222222B", "2º DAM"));
        
        // 2. Creamos una NUEVA instancia. 
        // El constructor del Controlador debería leer el fichero "alumnos.txt"
        Controlador ctrl2 = new Controlador();
        Alumno recuperado = ctrl2.buscar("22222222B");
        
        assertNotNull(recuperado, "El alumno debe persistir en el archivo 'alumnos.txt' y ser recuperado por una nueva instancia.");
        assertEquals("Maria", recuperado.getNombre());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Borrar Alumno")
    void testBorrar() {
        Controlador ctrl = new Controlador();
        ctrl.agregar(new Alumno("Pepe", "33333333C", "1º DAW"));
        
        assertTrue(ctrl.borrar("33333333C"), "El método borrar debe devolver true si el alumno existía.");
        assertNull(ctrl.buscar("33333333C"), "El alumno ya no debería existir en la lista.");
    }
}
