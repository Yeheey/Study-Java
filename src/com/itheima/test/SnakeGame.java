package com.itheima.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * 贪吃蛇游戏
 * 基于 Java Swing 实现，方向键控制移动，空格键开始/重开
 */
public class SnakeGame extends JFrame {

    // 游戏配置常量
    private static final int CELL = 20;          // 每格像素大小
    private static final int COLS = 25;          // 列数
    private static final int ROWS = 25;          // 行数
    private static final int WIDTH = CELL * COLS; // 画布宽度
    private static final int HEIGHT = CELL * ROWS;// 画布高度
    private static final int SPEED = 120;        // 刷新间隔(ms)

    // 游戏状态
    private final LinkedList<Point> snake = new LinkedList<>(); // 蛇身坐标
    private Point food;          // 食物位置
    private int dirX = 1, dirY = 0; // 移动方向
    private int score = 0;       // 分数
    private boolean over = false;    // 是否结束
    private boolean running = false; // 是否运行中
    private Timer timer;         // 游戏定时器

    // UI 组件
    private final JLabel scoreLabel = new JLabel("  分数: 0");
    private final JLabel tipLabel = new JLabel("  按空格键开始");
    private final JPanel board = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            render(g);
        }
    };

    public SnakeGame() {
        setTitle("贪吃蛇");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // 顶部信息栏
        JPanel infoBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoBar.add(scoreLabel);
        infoBar.add(tipLabel);

        // 游戏画布
        board.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        board.setBackground(Color.WHITE);
        board.setFocusable(true);

        add(infoBar, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        // 键盘监听
        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                onKey(e.getKeyCode());
            }
        });

        reset();
    }

    /** 重置游戏到初始状态 */
    private void reset() {
        snake.clear();
        snake.add(new Point(COLS / 2, ROWS / 2));
        dirX = 1; dirY = 0;
        score = 0;
        over = false;
        running = false;
        scoreLabel.setText("  分数: 0");
        tipLabel.setText("  按空格键开始");
        spawnFood();
        if (timer != null) timer.stop();
        board.repaint();
    }

    /** 启动游戏循环 */
    private void start() {
        running = true;
        tipLabel.setText("  游戏中...");
        timer = new Timer(SPEED, e -> {
            if (!over) {
                tick();
                board.repaint();
            }
        });
        timer.start();
    }

    /** 每一帧的逻辑更新 */
    private void tick() {
        Point head = snake.getFirst();
        Point next = new Point(head.x + dirX, head.y + dirY);

        // 撞墙检测
        if (next.x < 0 || next.x >= COLS || next.y < 0 || next.y >= ROWS) {
            finish(); return;
        }
        // 撞自身检测
        for (Point p : snake) {
            if (p.equals(next)) { finish(); return; }
        }

        snake.addFirst(next);

        // 吃到食物：加分、重新生成食物、蛇身增长
        if (next.equals(food)) {
            score += 10;
            scoreLabel.setText("  分数: " + score);
            spawnFood();
        } else {
            snake.removeLast(); // 未吃到食物，移除尾部保持长度
        }
    }

    /** 游戏结束处理 */
    private void finish() {
        over = true;
        running = false;
        if (timer != null) timer.stop();
        tipLabel.setText("  游戏结束！按空格键重新开始");
    }

    /** 随机生成食物，避开蛇身 */
    private void spawnFood() {
        Random r = new Random();
        do {
            food = new Point(r.nextInt(COLS), r.nextInt(ROWS));
        } while (snake.contains(food));
    }

    /** 键盘事件处理 */
    private void onKey(int key) {
        if (key == KeyEvent.VK_SPACE) {
            if (over || !running) { reset(); start(); }
            return;
        }
        if (!running || over) return;

        // 方向控制，禁止直接反向
        switch (key) {
            case KeyEvent.VK_UP:    if (dirY !=  1) { dirX =  0; dirY = -1; } break;
            case KeyEvent.VK_DOWN:  if (dirY != -1) { dirX =  0; dirY =  1; } break;
            case KeyEvent.VK_LEFT:  if (dirX !=  1) { dirX = -1; dirY =  0; } break;
            case KeyEvent.VK_RIGHT: if (dirX != -1) { dirX =  1; dirY =  0; } break;
        }
    }

    /** 绘制游戏画面 */
    private void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 网格线
        g2.setColor(new Color(230, 230, 230));
        for (int i = 0; i <= COLS; i++)
            g2.drawLine(i * CELL, 0, i * CELL, HEIGHT);
        for (int i = 0; i <= ROWS; i++)
            g2.drawLine(0, i * CELL, WIDTH, i * CELL);

        // 食物（红色圆）
        g2.setColor(new Color(220, 60, 60));
        g2.fillOval(food.x * CELL + 2, food.y * CELL + 2, CELL - 4, CELL - 4);

        // 蛇身（圆角矩形，头部深绿，尾部浅绿渐变）
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            float t = 1.0f - (float) i / snake.size() * 0.5f;
            g2.setColor(i == 0 ? new Color(40, 160, 70) : new Color(40, (int)(160 * t), 70));
            g2.fillRoundRect(p.x * CELL + 1, p.y * CELL + 1, CELL - 2, CELL - 2, 5, 5);
        }

        // 游戏结束遮罩
        if (over) {
            g2.setColor(new Color(0, 0, 0, 140));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("微软雅黑", Font.BOLD, 36));
            FontMetrics fm = g2.getFontMetrics();
            String s = "游戏结束";
            g2.drawString(s, (WIDTH - fm.stringWidth(s)) / 2, HEIGHT / 2 - 10);
            g2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            fm = g2.getFontMetrics();
            s = "最终得分: " + score;
            g2.drawString(s, (WIDTH - fm.stringWidth(s)) / 2, HEIGHT / 2 + 30);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}
