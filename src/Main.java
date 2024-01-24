import factorio.Clock;
import factorio.Robot;

import java.util.*;
import java.util.stream.Collectors;

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
//        bracketMatch(sc.nextLine());
//        printerQue(sc);
//        balanceParentheses(sc.nextLine());
//        hotPotato(sc);
//        maxElement(sc);
//        queOperations(sc);
//        taskScheduler(sc);
//        documentEditor(sc);
//        System.out.println(recursiveFibonachi(sc.nextInt()));

        factorio(sc);
    }

    private static void factorio(Scanner sc) {
        String[] input = sc.nextLine().split("\\|");
        ArrayDeque<Robot> robotList = new ArrayDeque<>(input.length);
        ArrayDeque<String> jobList = new ArrayDeque<>();
        HashMap<String, Robot> robotJobs = new HashMap<>(input.length);

        System.out.println("getStart");
        int[] time = Arrays.stream(sc.nextLine().split(":")).mapToInt(Integer::parseInt).toArray();
        Clock clock = new Clock(time[0], time[1], time[2]);

        for (String item : input) {
            String[] params = item.split("-");
            robotList.add(new Robot(params[0], Integer.parseInt(params[1])));
        }

        String comand = sc.nextLine();

        while (!comand.equals("End")) {
            jobList.add(comand);
            comand = sc.nextLine();
        }

        while (!jobList.isEmpty()) {
            clock.tick();
            String currentTime = clock.getTime();

            Robot isDone = robotJobs.get(currentTime);
            if (isDone != null) {
                robotList.add(isDone);
                robotJobs.remove(currentTime);
            }

            String job = jobList.peek();
            Robot freeRobot = robotList.peek();

            if (job != null && freeRobot != null) {
                String timeDone = clock.getFutureTime(freeRobot.processTime);
                jobList.pop();
                robotList.pop();
                robotJobs.put(timeDone, freeRobot);
                System.out.println(freeRobot.name + " - " + job + " [" + currentTime + "]");
            }

        }

    }

    private static HashMap<Integer, Integer> fibonachiMemo = new HashMap<Integer, Integer>();

    private static int recursiveFibonachi(int depth) {
        int result = 0;

        if (depth == 0) {
            return result;
        }

        if (depth == 1) {
            result = 1;
            return result;
        }

        var memo = fibonachiMemo.get(depth);

        if (memo != null) {
            result = memo;
            return result;
        }

        result = recursiveFibonachi(depth - 1) + recursiveFibonachi(depth - 2);

        fibonachiMemo.put(depth, result);

        return result;
    }

    private static void documentEditor(Scanner sc) {
        String[] commands = sc.nextLine().split("\\(\"?|\"?\\)");
        ArrayDeque<String> history = new ArrayDeque<String>();
        ArrayDeque<String> redo = new ArrayDeque<String>();

        while (!commands[0].equals("End")) {
            switch (commands[0]) {
                case "Insert": {
                    redo.clear();
                    history.add(commands[1]);
                    break;
                }
                case "Undo": {
                    if (!history.isEmpty()) {
                        redo.add(history.pollLast());
                    }
                    break;
                }
                case "Redo": {
                    if (!redo.isEmpty()) {
                        history.add(redo.pollLast());
                    }
                    break;
                }
            }

            System.out.println(String.join("", history));

            commands = sc.nextLine().split("\\(\"?|\"?\\)");
        }

    }

    private static void taskScheduler(Scanner sc) {
        ArrayDeque<Task> priorityTask = new ArrayDeque<Task>();
        String input = sc.nextLine();

        while (!input.isEmpty()) {
            String[] comandProps = input.split(" ");

            if (comandProps[0].equals("Add")) {
                Task item = new Task(comandProps[1], Integer.parseInt(comandProps[2]));
                priorityTask.add(item);
                input = sc.nextLine();
                continue;
            }

            if (comandProps[0].equals("getNextTask")) {
                Optional<Task> item = priorityTask.stream().max(Comparator.comparingInt(a -> a.priority));
                item.ifPresent(priorityTask::remove);
                item.ifPresentOrElse(task -> System.out.printf("%s - %d\n", task.name, task.priority), () -> System.out.println("empty"));
                input = sc.nextLine();
                continue;
            }

            System.out.println("invalid input");
            input = sc.nextLine();
        }

    }

    private static void queOperations(Scanner sc) {
        int[] commands = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int countAdd = commands[0];
        int countRem = commands[1];
        int toFInd = commands[2];
        ArrayDeque<Integer> items = new ArrayDeque<Integer>(countAdd);
        System.out.println("get input");
        int[] input = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        for (int item : input) {
            if (countAdd <= 0) {
                break;
            }
            items.add(item);
            countAdd--;
        }

        for (int i = 0; i < countRem; i++) {
            items.poll();
        }

        Optional<Integer> found = items.stream().filter(e -> e == toFInd).findFirst();

        found.ifPresentOrElse(integer -> System.out.println(true), () -> items.stream().min(Integer::compareTo).ifPresent(System.out::println));
    }

    private static void maxElement(Scanner sc) {
        int comandCount = Integer.parseInt(sc.nextLine());
        String command = sc.nextLine();
        ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

        while (comandCount > 0) {
            String[] comandParams = command.split(" ");

            switch (comandParams[0]) {
                case "1" -> stack.push(Integer.parseInt(comandParams[1]));
                case "2" -> stack.poll();
                case "3" -> {
                    Optional<Integer> max = stack.stream().max(Comparator.comparingInt(a -> a));
                    max.ifPresent(System.out::println);
                }
            }

            comandCount--;
            if (comandCount > 0) {
                System.out.println("get");
                command = sc.nextLine();
            }
        }

    }

    private static void hotPotato(Scanner sc) {
        ArrayList<String> kids = new ArrayList<String>(Arrays.stream(sc.nextLine().split("\\s+")).toList());
        int count = sc.nextInt();
        int tempCount = (count - 1) % kids.size();

        while (kids.size() > 1) {
            while (tempCount > 0) {
                kids.addLast(kids.removeFirst());
                tempCount--;
            }

            System.out.println("Removed " + kids.removeFirst());
            tempCount = (count - 1) % kids.size();
        }

        System.out.println("Last is " + kids.getFirst());
    }

    private static void balanceParentheses(String input) {
        ArrayDeque<Character> currentParentheses = new ArrayDeque<Character>();

        for (char item : input.toCharArray()) {
            switch (item) {
                case '(':
                case '{':
                case '[': {
                    currentParentheses.push(item);
                    break;
                }
                case ')':
                case '}':
                case ']': {
                    if (currentParentheses.isEmpty()) {
                        System.out.println(false);
                        return;
                    }

                    char prev = currentParentheses.peek();

                    switch (item) {
                        case ')': {
                            if (prev != '(') {
                                System.out.println(false);
                                return;
                            }
                            break;
                        }
                        case '}': {
                            if (prev != '{') {
                                System.out.println(false);
                                return;
                            }
                            break;
                        }
                        case ']': {
                            if (prev != '[') {
                                System.out.println(false);
                                return;
                            }
                            break;
                        }
                    }

                    currentParentheses.pop();
                }
            }
        }
        System.out.println(true);
    }

    private static void printerQue(Scanner sc) {
        String comand = sc.nextLine();
        ArrayDeque<String> printTail = new ArrayDeque<String>();
        ArrayList<String> output = new ArrayList<String>();

        while (!comand.equals("print")) {
            if (comand.equals("cancel")) {
                String item = printTail.pollFirst();
                if (item == null) {
                    output.add("Standby");
                } else {
                    output.add("Canceled " + item);
                }
            } else {
                printTail.offer(comand);
            }

            System.out.println("input");
            comand = sc.nextLine();
        }

        while (!printTail.isEmpty()) {
            output.add(printTail.pop());
        }

        for (String row : output) {
            System.out.println(row);
        }

    }

    private static void bracketMatch(String input) {
        ArrayDeque<Integer> currentStartIndex = new ArrayDeque<Integer>();
        ArrayDeque<String> subComponents = new ArrayDeque<String>();

        for (int i = 0; i < input.length(); i++) {
            char item = input.charAt(i);
            switch (item) {
                case '(' -> currentStartIndex.push(i);
                case ')' -> {
                    subComponents.add(input.substring(currentStartIndex.pop(), i + 1));
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