package org.jarlinfonseca.junitapp.example.models;

import org.jarlinfonseca.junitapp.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(){
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el método");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el método de prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");
    }

    @Tag("cuenta")
    @Nested
    @DisplayName("probando atributos de la cuenta corriente")
    class CuentaTestNombreSaldo{
        @Test
        @DisplayName("Probando el nombre de la cuenta corriente!")
        void testNombreCuenta() {
            String esperado = "Andres";
            String real = cuenta.getPersona();
            assertNotNull(real, ()->"La cuenta no puede ser nula");
            assertEquals(esperado, real, ()->"El nombre de la cuenta no es el que se esperaba: se esperaba "+
                    esperado+" sin embargo fue "+real);
            assertTrue(real.equals("Andres"), ()->"Nombre cuenta esperada debe ser igual a la real");
        }

        @Test
        @DisplayName("Probando el saldo, que no sea null, mayor que cero, valor esperado.")
        void testSaldoCuenta() {
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @Test
        @DisplayName("Testeando referencias que sean iguales con el método equals.")
        void testReferenciaCuenta() {
            cuenta = new Cuenta("John Dow", new BigDecimal("8900.9997"));
            Cuenta cuenta2 = new Cuenta("John Dow", new BigDecimal("8900.9997"));
            //  assertNotEquals(cuenta2, cuenta);
            assertEquals(cuenta2, cuenta);
        }
    }

    @Nested
    class CuentaOperacionesTest{
        @Test
        void testDebitoCuenta() {
            cuenta.debito(new BigDecimal(100));

            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testCreditoCuenta() {
            cuenta.credito(new BigDecimal(100));

            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue());
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testTransferirDineroCuenta() {
            Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
            Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

            Banco banco = new Banco();
            banco.setNombre("Banco del Estado");
            banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }

    }


    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);

    }


    @Test
    @DisplayName("Probando relaciones entre las cuentas y el banco con assertAll.")
    //@Disabled
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));


        assertAll(() ->
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                            ()->"El valor del saldo de la cuenta2 no es el esperado."),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                            ()->"El valor del saldo de la cuenta1 no es el esperado."),
                () -> assertEquals(2, banco.getCuentas().size(), ()->"El banco no tiene las cuentas esperadas."),
                () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                () -> assertEquals("Andres", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona()),
                () -> assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("John Doe"))));
    }
    @Nested
    class SistemaOperativoTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows(){
        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac(){
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows(){
        }
    }

    @Nested
    class JavaVersionTest {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void soloJdk8() {

        }

        @Test
        @EnabledOnJre(JRE.JAVA_11)
        void soloJdk11() {

        }

        @Test
        @DisabledOnJre(JRE.JAVA_11)
        void testNoJdk11() {

        }
    }

    @Nested
    class SistemPropertiesTest{
        @Test
        void imprimirSystemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v)-> System.out.println(k+":"+v));
        }
        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = ".*11.*")
        void testJavaVersion(){

        }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testSolo64() {
        }

        @Test
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testNO64() {
        }

        @Test
        @EnabledIfSystemProperty(named = "user.name", matches = "jarlin.fonsecab_prag")
        void testUsername() {
        }

        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void testDev() {
        }

    }

    @Nested
    class VariableAmbienteTest{
        @Test
        void imprimirVariablesAmbiente(){
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k,v)-> System.out.println(k+" = "+v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-11.0.17.*")
        void testJavaHome(){

        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
        void testProcesadores(){

        }

        @Test
        @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
        void testEnv() {
        }

        @Test
        @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "prod")
        void testEnvProdDisabled() {
        }

    }


    @Test
    @DisplayName("test Saldo Cuenta Dev")
    void testSaldoCuentaDev() {
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("test Saldo Cuenta Dev 2")
    void testSaldoCuentaDev2() {
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @DisplayName("Probando Debito Cuenta Repetir!")
    @RepeatedTest(value=5, name = "{displayName} - Repetición numero {currentRepetition} de {totalRepetitions}")
    void testDebitoCuentaRepetir(RepetitionInfo info) {
        if(info.getCurrentRepetition() == 3){
            System.out.println("estamos en la repeticion " + info.getCurrentRepetition());
        }
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }
    
}



