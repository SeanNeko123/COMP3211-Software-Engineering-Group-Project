package Test;

import Square.*;
import GameBoard.GameBoardController; // Adjust this import based on your package structure
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestGameBoard {

    private GameBoardController gameBoardController;

    @BeforeEach
    public void setUp() {
        gameBoardController = new GameBoardController(); // Initialize your controller before each test
    }

    @Test
    public void testLoadCustomGameBd() {
        // Create a temporary XML file for testing
        File tempXmlFile = null;
        try {
            tempXmlFile = createTempXmlFile("validTestGameBoard.xml");
            // Call the method with the path to the temporary XML file
            int result = gameBoardController.loadCustomGameBd(tempXmlFile.getAbsolutePath());

            // Assert that the method executed successfully (you can check return value or state)
            assertEquals(0, result); // Assuming 0 indicates success

            // Additional assertions to verify that squares were loaded correctly
            assertEquals(5, gameBoardController.getGameBoard().getSquareList().size()); // Check if 5 squares were loaded

            // Check specific properties of the squares loaded
            assertEquals("Central", ((Property) gameBoardController.getGameBoard().getSquareList().get(1)).getName());
            assertEquals(800, ((Property) gameBoardController.getGameBoard().getSquareList().get(1)).getPrice());
            assertEquals(90, ((Property) gameBoardController.getGameBoard().getSquareList().get(1)).getRent());

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } finally {
            // Clean up the temporary file
            if (tempXmlFile != null && tempXmlFile.exists()) {
                tempXmlFile.delete();
            }
        }
    }

    @Test
    public void testLoadCustomGameBdWithMissingType() {
        File tempXmlFile = null;
        try {
            tempXmlFile = createTempXmlFile("invalidTestGameBoardMissingType.xml");

            // Call the method and expect it to return -1 due to missing type attribute
            int result = gameBoardController.loadCustomGameBd(tempXmlFile.getAbsolutePath());
            assertEquals(-1, result); // Expecting -1 for error case

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } finally {
            if (tempXmlFile != null && tempXmlFile.exists()) {
                tempXmlFile.delete();
            }
        }
    }

    @Test
    public void testLoadCustomGameBdWithDuplicateStartSquare() {
        File tempXmlFile = null;
        try {
            tempXmlFile = createTempXmlFile("invalidTestGameBoardDuplicateStart.xml");

            // Call the method and expect it to return -1 due to duplicate start square
            int result = gameBoardController.loadCustomGameBd(tempXmlFile.getAbsolutePath());
            assertEquals(-1, result); // Expecting -1 for error case

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        } finally {
            if (tempXmlFile != null && tempXmlFile.exists()) {
                tempXmlFile.delete();
            }
        }
    }

    private File createTempXmlFile(String fileName) throws IOException {
        File tempXmlFile = File.createTempFile(fileName, ".xml");
        try (FileWriter writer = new FileWriter(tempXmlFile)) {
            if (fileName.equals("validTestGameBoard.xml")) {
                writer.write(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                "<root>\n" +
                                "    <MonopolyGame>\n" +
                                "        <PlayerList></PlayerList>\n" +
                                "        <GameBoard>\n" +
                                "            <squares position=\"1\" type=\"Go\"></squares>\n" +
                                "            <squares position=\"2\" type=\"Property\">\n" +
                                "                <name>Central</name>\n" +
                                "                <price>800</price>\n" +
                                "                <rent>90</rent>\n" +
                                "                <owner></owner>\n" +
                                "            </squares>\n" +
                                "            <squares position=\"3\" type=\"Property\">\n" +
                                "                <name>Wan Chai</name>\n" +
                                "                <price>700</price>\n" +
                                "                <rent>65</rent>\n" +
                                "                <owner></owner>\n" +
                                "            </squares>\n" +
                                "            <squares position=\"4\" type=\"IncomeTax\"></squares>\n" +
                                "            <squares position=\"5\" type=\"Property\">\n" +
                                "                <name>Stanley</name>\n" +
                                "                <price>600</price>\n" +
                                "                <rent>60</rent>\n" +
                                "                <owner></owner>\n" +
                                "            </squares>\n" +
                                "</GameBoard>\n" +
                                "</MonopolyGame>\n" +
                                "</root>"
                );

            } else if (fileName.equals("invalidTestGameBoardMissingType.xml")) {
                writer.write(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                "<root>\n" +
                                "    <MonopolyGame>\n" +
                                "        <PlayerList></PlayerList>\n" +
                                "        <GameBoard>\n" +
                                "            <squares position=\"1\"></squares>\n" +  // Missing type attribute
                                "</GameBoard>\n" +
                                "</MonopolyGame>\n" +
                                "</root>"
                );

            } else if (fileName.equals("invalidTestGameBoardDuplicateStart.xml")) {
                writer.write(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                                "<root>\n" +
                                "    <MonopolyGame>\n" +
                                "        <PlayerList></PlayerList>\n" +
                                "        <GameBoard>\n" +
                                "            <squares position=\"1\" type=\"Go\"></squares>\n" +
                                "            <squares position=\"2\" type=\"Go\"></squares>\n" +  // Duplicate start square
                                "</GameBoard>\n" +
                                "</MonopolyGame>\n" +
                                "</root>"
                );

            }
        }

        return tempXmlFile;
    }
}