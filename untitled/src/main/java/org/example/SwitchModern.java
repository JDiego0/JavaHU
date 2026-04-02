/*Java 17/21 (switch expression):
- Uses '->'
- Does not require a break
- Safer, cleaner, and avoids errors

Comparison:
- Java 8 uses a traditional switch statement with 'break', which carries a risk of "fall-through".
- Java 17/21 uses '->', which doesn't require a break, making it safer and more readable.
-Java 17/21 improves error messages by showing more detail
about the cause of the exception, unlike Java 8 which
was more generic.*/

package org.example;

public class SwitchModern {

    public static String getSalaryCategory(double salary){

        return switch ((int) salary / 1000) {
            case 0, 1 -> "Low salary";
            case 2, 3 -> "Medium salary";
            case 4, 5 -> "High salary";
            default -> "Very high salary";
        };
    }
}