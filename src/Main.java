import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("input");

//        System.out.println(reverseStrStack(sc));
//        System.out.println(stackOps(Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray(), sc));
        browserOps(sc);
    }

    private static void browserOps(Scanner sc) {
        ArrayDeque<String> history = new ArrayDeque<String>();
        String command = sc.nextLine();
        String currentUrl = "";

        while (!command.equals("Home")) {
            if (command.equals("back")) {
                if (!history.isEmpty()) {
                    history.pop();
                }
            } else {
                history.push(command);
            }

            if (history.isEmpty()) {
                System.out.println("no previous URLs");
            } else {
                currentUrl = history.peek();
                System.out.println(currentUrl);
            }

            command = sc.nextLine();
        }

    }

    private static String stackOps(int[] args, Scanner sc) {
        int stackLength = args[0];
        int removeCount = args[1];
        int itemToFind = args[2];
        ArrayDeque<Integer> stack = new ArrayDeque<Integer>(stackLength);
        System.out.println("get nums");
        int[] inputNums = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        for (int num : inputNums) {
            stack.push(num);
        }

        while (removeCount > 0) {
            stack.pop();
            removeCount--;
        }

        if (stack.isEmpty()) {
            return "0";
        }

        if (stack.contains(itemToFind)) {
            return "true";
        }
        int lowest = Integer.MAX_VALUE;
        for (int item : stack) {
            if (item < lowest) {
                lowest = item;
            }
        }

        return lowest + "";
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

        return result.toString().trim();

    }
}