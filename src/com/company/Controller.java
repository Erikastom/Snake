package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Controller {
    private static final int FIELD_WIDTH = 20;
    private static final int FIELD_HEIGHT = 15;
    private static final int SQUARE_SIZE = 20;
    private static final int SCREEN_WIDTH = FIELD_WIDTH * SQUARE_SIZE;
    private static final int SCREEN_HEIGHT = FIELD_HEIGHT * SQUARE_SIZE;
    private View view;
    private Graphics graphics;
    private Direction direction = Direction.RIGHT;
    private List<Point> list = new ArrayList<>();
    private Point fruitPoint;
    private int count = 500;

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        view.create(SCREEN_WIDTH, SCREEN_HEIGHT);
        list.add(new Point(FIELD_WIDTH / 2 - 1, FIELD_HEIGHT / 2));
        list.add(new Point(FIELD_WIDTH / 2, FIELD_HEIGHT / 2));
        list.add(new Point(FIELD_WIDTH / 2 + 1, FIELD_HEIGHT / 2));
        moveFruit();
        while (true) {
            renderImage();
            sleep();
            Point nextPoint = new Point(list.get(list.size() - 1));
            switch (direction) {
                case RIGHT -> nextPoint.x++;
                case LEFT -> nextPoint.x--;
                case UP -> nextPoint.y--;
                case DOWN -> nextPoint.y++;

            }
            if (list.contains(nextPoint)) {
                System.out.println("Game Over");
                return;
            }
            nextPoint.x = (nextPoint.x + FIELD_WIDTH) % FIELD_WIDTH;
            nextPoint.y = (nextPoint.y + FIELD_HEIGHT) % FIELD_HEIGHT;
            list.add(nextPoint);
            if (nextPoint.equals(fruitPoint)) {
                moveFruit();
                count *= 0.9;
            } else {
                list.remove(0);
            }
        }
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
        if (keyCode == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
        if (keyCode == KeyEvent.VK_UP && direction != Direction.DOWN) {
            direction = Direction.UP;
        }
        if (keyCode == KeyEvent.VK_DOWN && direction != Direction.UP) {
            direction = Direction.DOWN;
        }
    }

    private void renderImage() {
        BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        drawSnake();
        drawSquare(fruitPoint, Color.RED);
        String score = "Score: " + list.size();
        graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
        graphics.setColor(Color.WHITE);
        graphics.drawString(score, 5, 20);
        view.setImageLabel(image);
    }

    private void drawSquare(Point point, Color color) {
        graphics.setColor(color);
        graphics.fillRect(point.x * SQUARE_SIZE, point.y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake() {
        for (Point point : list) {
            drawSquare(point, Color.GREEN);
        }
    }

    private void moveFruit() {
        do {
            fruitPoint = new Point(random(FIELD_WIDTH), random(FIELD_HEIGHT));
        } while (list.contains(fruitPoint));
    }

    private int random(int max) {
        return (int) (Math.random() * max);
    }

    public void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(count); //half a second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
