import java.util.ArrayList;
public class Main {
    static BrickBreaker b = null;
    public static void main(String[] args) {
        BrickBreaker a = new BrickBreaker();
        b = a;
    }
    public static ArrayList<Brick> getBricks() {
        return b.getBricks();
    }
}