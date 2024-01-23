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
//        browserOps(sc);
//        calculator(sc);
//        decimalToBinary(sc.nextInt());
        bracketMatch(sc.nextLine());
    }

    private  static void bracketMatch(String input) {
        ArrayDeque<Integer> currentStartIndex = new ArrayDeque<Integer>();
        ArrayDeque<String> subComponents = new ArrayDeque<String>();

        for (int i = 0; i < input.length(); i++) {
            char item = input.charAt(i);
            switch (item) {
                case '(' -> currentStartIndex.push(i);
                case ')' -> {
                    subComponents.add(input.substring(currentStartIndex.pop(), i+1));
                }
            }
        }

        while (!subComponents.isEmpty()) {
            System.out.println(subComponents.pop());
        }

    }
    private static void decimalToBinary(int input) {
        if (input == 0) {
            System.out.println(0);
            return;
        }

        ArrayDeque<Integer> num = new ArrayDeque<Integer>();
        while (input != 0) {
            num.push(input % 2);
            input /= 2;
        }

        StringBuilder result = new StringBuilder(num.size());
        while (!num.isEmpty()) {
            result.append(num.pop());
        }

        System.out.println(result);
    }

    private static void calculator(Scanner sc) {
        String[] tokens = sc.nextLine().split(" ");
        ArrayDeque<String> operations = new ArrayDeque<String>();
        ArrayDeque<Integer> nums = new ArrayDeque<Integer>();

        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0))) {
                nums.add(Integer.parseInt(token));
            } else {
                operations.add(token);
            }
        }

        if (nums.isEmpty()) {
            System.out.println(0);
            return;
        }

        int result = nums.pop();

        while (!nums.isEmpty()) {
            if (operations.isEmpty()) {
                break;
            }

            String op = operations.pop();

            switch (op) {
                case "+": {
                    result += nums.pop();
                    break;
                }
                case "-": {
                    result -= nums.pop();
                    break;
                }
            }
        }

        System.out.println(result);
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