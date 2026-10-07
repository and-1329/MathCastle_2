import java.util.Random;

public class Person {
    protected int x, y;
    private String image = "\uD83E\uDDD9\u200D";
    private static int live = 3;
    Random r = new Random();

    Person(int sizeBoard) {
        y = sizeBoard;
        int n = r.nextInt(sizeBoard);
        x = n == 0 ? 1 : n;
    }

    Person(int x, int y){
        this.x = x;
        this.y = y;
    }
    Person(){
        this(1, 1);
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getLive() {
        return live;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean moveCorrect(int x, int y) {
        if (!isValidCoord(this.x) || !isValidCoord(this.y)) {
            return false;
        }
        if (!isValidCoord(x) || !isValidCoord(y)) {
            return false;
        }

        return (this.x == x && Math.abs(this.y - y) == 1)
                || (this.y == y && Math.abs(this.x - x) == 1);
    }

    private boolean isValidCoord(int v) {
        return v >= 1 && v <= 5;
    }

    void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void downLive(){
        live--;
    }
}