package org.example;
/*Java 17/21 improves error messages by showing exactly
the variable that caused the exception, whereas Java 8
provided more generic messages, making debugging more difficult.*/

public class Main {
    public static void main(String[] args) {
    SwitchLegacy menu = new SwitchLegacy();
    menu.initLegacyMenu();
    }
}