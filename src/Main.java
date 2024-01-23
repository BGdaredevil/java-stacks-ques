import java.util.ArrayDeque;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("input");

        System.out.println(reverseStrStack(sc));
    }

    private static String reverseStrStack(Scanner scanner) {
        int length = Integer.parseInt(scanner.nextLine());
        ArrayDeque<Integer> deck = new ArrayDeque<>(length);
        StringBuilder result = new StringBuilder();

        while (length > 0) {
            System.out.println("nextNum");
            int num = Integer.parseInt(scanner.nextLine());
            deck.push(num);
            length--;
        }

        while (!deck.isEmpty()) {
            result.append(deck.pop()).append(" ");
        }

        result.append("\n");

        return  result.toString().trim();

    }
}