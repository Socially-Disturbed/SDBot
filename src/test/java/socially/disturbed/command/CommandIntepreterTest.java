//package socially.disturbed.commands;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CommandIntepreterTest {
//
//    private final CommandIntepreter commandIntepreter = new CommandIntepreter(new SDFunctionsImpl());
//
//    @Test
//    public void testGetPlayerByName() {
//
//        String playerDytt = "Dy7t";
//        String answer = commandIntepreter.invokeMethod("!getPlayersByName " + playerDytt);
//
//        assertEquals(playerDytt, answer);
//    }
//
//    @Test
//    public void testGetPlayerById() {
//        String id = "12345";
//        String answer = commandIntepreter.invokeMethod("!getPlayersById " + id);
//
//        assertEquals("Dy7t", answer);
//    }
//
//    @Test
//    public void testNoPlayerFound() {
//        String result = commandIntepreter.invokeMethod("!getPlayersByName Vebisen");
//        assertEquals("No player found", result);
//    }
//}
