import java.util.Scanner;

public class HugeMonster extends Monster {

    private String image = "\uD83E\uDD96"; // 🦖

    HugeMonster(int sizeBoard) {
        super(sizeBoard);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int getDamage() {
        return 2;
    }


    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу:");


        int maxDiv  = 5 + difficultGame * 5;
        int maxQuo = 5 + difficultGame * 5;

        int div  = r.nextInt(2, maxDiv);
        int quo = r.nextInt(2, maxQuo);
        int dividend = div * quo;

        int trueAnswer = dividend / div;

        System.out.println("Реши пример: " + dividend + " : " + div + " = ?");

        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();

        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил огромного монстра!");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }
    }
}