package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * Основной класс игры.
 */
public class Game {
    private final Engine engine = new Engine();
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    /**
     * Выводит заставку и информацию об игроках.
     */
    public void showInfo() {
        drawIntro();
        engine.printSquadsRaces();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Enter - следующий ход, x - закончить игру: ");
    }

    /**
     * Выводит экранную заставку.
     */
    private void drawIntro() {
        int width = 100;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Dialog", Font.PLAIN, 16));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("Heroes", 10, 20);

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? " " : "S");
            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(sb);
        }
        System.out.println("\n");
    }

    /**
     * Игровой цикл.
     */
    private void loop() {
        if (engine.isAllUnitsDead()) {
            System.out.println("Игра окончена");
        }
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equals("x")) {
            engine.makeTurn();
            System.out.println("\nEnter - следующий ход, x - закончить игру: ");
        }
        System.out.println("Игра окончена");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.showInfo();
        game.loop();
    }
}
