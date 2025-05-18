package org.six;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    void testMainShouldReturnValidOutput() {
        // Given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // When:
        Main.main(new String[]{});
        System.setOut(originalOut);

        // Then:
        String expected = "Hello world!" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
}
