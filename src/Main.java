import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String castle = "\uD83C\uDFF0";
        int sizeBoard = 5;

        Person person = new Person(sizeBoard);
        int step = 0;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = "  ";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 5;
        Random r = new Random();


        Monster[] arrMonster = new Monster[countMonster + 1];
        int count = 0;
        Monster test;
        while (count <= countMonster) {
            int dice = r.nextInt(100);
            if (dice < 50) {
                test = new Monster(sizeBoard);
            } else if (dice < 85) {
                test = new BigMonster(sizeBoard);
            } else {
                test = new HugeMonster(sizeBoard);
            }
            if (board[test.getY()][test.getX()].equals("  ")) {
                board[test.getY()][test.getX()] = test.getImage();
                arrMonster[count] = test;
                count++;
            }
        }

        int castleX = r.nextInt(sizeBoard);
        int castleY = 0;

        board[castleY][castleX] = castle;

        System.out.println("Привет! Странный вопрос, но играть будешь? (Напиши: ДА или НЕТ)");

        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().trim().toUpperCase();
        System.out.println("Ваш ответ:\t" + answer);

        switch (answer) {
            case "ДА", "YES", "Y", "Д", "y", "Да", "да", "д", "Yes", "yes" ->
                    play(sc, board, person, arrMonster, castle, step, sizeBoard);
            case "НЕТ", "Нет", "нет", "Н", "н", "No", "NO", "no", "n", "N" ->
                    System.out.println("\nА зачем ты вообще тогда зпаустил эту программу?");
            default ->
                    System.out.println("\nСложно было нормально ответить, да?");
        }
    }

    static void play(Scanner sc, String[][] board, Person person,
                     Monster[] arrMonster, String castle, int step, int sizeBoard) {

        System.out.println("Выбери сложность игры (от 1 до 5):");
        int difficultGame = sc.nextInt();
        if (difficultGame > 5) {
            difficultGame = 5;

        }
        if (difficultGame < 1) {
            difficultGame = 1;
        }
        System.out.println("Выбранная сложность:\t" + difficultGame);

        while (true) {
            int liveNow = Person.getLive();
            if (liveNow < 1) {
                break;
            }
            board[person.getY() - 1][person.getX() - 1] = person.getImage();
            outputBoard(board, person.getLive());

            System.out.println("Введите, куда будет ходить персонаж (ход возможен только по вертикали и горизонтали на одну клетку)" +
                    "\nКоординаты персонажа - (x: " + person.getX() + ", y: " + person.getY() + ")\nФормат ввода: отдельными строками X, Y");
            int x = sc.nextInt();
            int y = sc.nextInt();

            // проверка
            if (person.moveCorrect(x, y)) {
                String next = board[y - 1][x - 1];
                if (next.equals("  ")) {
                    board[person.getY() - 1][person.getX() - 1] = "  ";
                    person.move(x, y);
                    step++;
                    System.out.println("Ход корректный; Новые координаты: " + person.getX() + ", " + person.getY() +
                            "\nХод номер: " + step);
                } else if (next.equals(castle)) {
                    System.out.println("\n\nВы достигли замка!\nНо внутри замка живет дракон. Он съел вас.");
                    break;
                } else {
                    for (Monster monster : arrMonster) {
                        if (monster.conflictPerson(x, y)) {
                            if (monster.taskMonster(difficultGame)) {
                                board[person.getY() - 1][person.getX() - 1] = "  ";
                                person.move(x, y);
                            } else {
                                // HugeMonster бьёт на 2, остальные — на 1
                                for (int i = 0; i < monster.getDamage(); i++) {
                                    person.downLive();
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Некорректный ход");
            }
        }
        System.out.println("\n\nYOU LOSE. YOU DIED. RIP PLAYER.\n");
    }

    static void outputBoard(String[][] board, int live) {

        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] raw : board) {
            System.out.println(wall);
            for (String col : raw) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.println("Количество жизней:\t" + live + "\n");
    }
}