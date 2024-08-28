import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Aimlab {
    public JFrame frame = new JFrame("AimlabLite");
    public int count = 0;
    public JLabel countLabel = new JLabel("得分: 0");
    public JLabel timerLabel = new JLabel("倒计时: 30");
    public Timer gameTimer; // 用于计时的 Timer
    public int remainingTime = 30; // 初始倒计时时间

    public void launch() {
        // 基础JFrame设置
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);

        // 倒计时标签
        timerLabel.setBounds(400, 10, 100, 30);

        // 起始启动按钮
        JButton startButton = new JButton("Start");
        startButton.setBounds(470, 400, 100, 50);
        startButton.addActionListener(e -> {
            // 按下按钮开始游戏
            frame.remove(startButton);
            frame.repaint();
            int choice = JOptionPane.showConfirmDialog(frame, "按下确定，游戏开始", "确认", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                gameStart();
            }
        });

        // 计数器大小设置
        countLabel.setBounds(470, 10, 100, 30);

        frame.add(timerLabel);
        frame.add(startButton);
        frame.add(countLabel);

        frame.setVisible(true);
    }

    public void gameStart() {
        count = 0;
        remainingTime = 30; // 重置时间
        timerLabel.setText("倒计时: " + remainingTime);

        // 初始化并启动倒计时
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText("倒计时: " + remainingTime);
                if (remainingTime <= 0) {
                    gameTimer.stop();
                    endGame();
                }
            }
        });
        gameTimer.start();

        // 生成目标按钮的计时器
        Timer targetTimer = new Timer(300, new ActionListener() {
            int counter = 0;
            int times = 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter < times) {
                    JButton tmpButton = getButton();
                    frame.add(tmpButton);
                    frame.repaint();

                    Timer buttonTimer = new Timer(500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frame.remove(tmpButton);
                            frame.repaint();
                        }
                    });
                    buttonTimer.setRepeats(false);
                    buttonTimer.start();

                    counter++;
                } else if (counter == times) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        targetTimer.setInitialDelay(0);
        targetTimer.start();
    }

    public JButton getButton() {
        JButton aimButton = new JButton("🎯🎯🎯🎯");
        aimButton.setBounds(
                new Random().nextInt(20, 800),
                new Random().nextInt(20, 800),
                100, 50);
        aimButton.addActionListener(e -> {
            count++;
            countLabel.setText("得分: " + count);
            frame.remove(aimButton);
            frame.repaint();
        });
        return aimButton;
    }

    public void endGame() {
        int choice = JOptionPane.showConfirmDialog(frame, "你的得分是 " + count + " 是否继续？", "游戏结束", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            gameStart();
        } else {
            JOptionPane.showMessageDialog(frame, "感谢游玩🙏");
            System.exit(0);
        }
    }
}
