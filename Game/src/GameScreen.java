import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Handles the actual game and outputs the max level the user reaches, it also
 * uses ProfileWriter to write user data to userProfiles.txt.
 * 
 * @author Lucas Lin
 * @version Dec 1, 2019
 */
public class GameScreen extends JFrame implements ActionListener {
    private JTextField guessField;
    private JLabel levelNum, hilo, triesLeft;
    private Random random;
    private int guessCount, trueNum, guessLimit, level, guess;
    private String username;

    GameScreen(String user) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(480, 640);
        this.setResizable(false);
        this.setTitle("HiLo Game");
        this.setLocationRelativeTo(null);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        JPanel HiLoPanel = new JPanel();
        HiLoPanel.setBorder(BorderFactory.createBevelBorder(1));
        HiLoPanel.setLayout(null);
        contentPane.add(HiLoPanel);
        HiLoPanel.setBounds(65, 40, 350, 300);

        levelNum = new JLabel("LEVEL 1");
        levelNum.setFont(new Font("Level font", Font.PLAIN, 18));
        HiLoPanel.add(levelNum);
        levelNum.setBounds(20, 10, 150, 50);

        hilo = new JLabel("");
        hilo.setFont(new Font("HILO font", Font.ITALIC, 250));
        HiLoPanel.add(hilo);
        hilo.setBounds(40, 20, 300, 300);

        JPanel guessPan = new JPanel();
        guessPan.setBorder(BorderFactory.createBevelBorder(0));
        guessPan.setLayout(null);
        contentPane.add(guessPan);
        guessPan.setBounds(65, 380, 350, 100);

        guessField = new JTextField();
        guessField.setFont(new Font("Guess font", Font.BOLD, 21));
        guessPan.add(guessField);
        guessField.setBounds(50, 30, 150, 40);

        // TODO, add enter keyListener to the game
        guessField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        guess = Integer.parseInt(guessField.getText());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }
        });

        JButton guessButton = new JButton("GUESS!");
        guessButton.setFont(new Font("Button font", Font.PLAIN, 15));
        guessButton.addActionListener(this);
        guessPan.add(guessButton);
        guessButton.setBounds(210, 30, 100, 40);

        triesLeft = new JLabel("");
        triesLeft.setFont(new Font("Tries font", Font.PLAIN, 20));
        contentPane.add(triesLeft);
        triesLeft.setBounds(130, 480, 350, 100);

        random = new Random();
        guessLimit = 10;
        level = 1;
        trueNum = random.nextInt(100);

        username = user;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        guessCount++;
        levelNum.setText("LEVEL " + level);
        guessLimit = 11 - level;
        guess = 0;
        triesLeft.setBounds(130, 480, 350, 100);

        try {
            guess = Integer.parseInt(guessField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter your numbers as digits!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        boolean nextLevel = false;
        if (guess == trueNum) {
            level++;
            levelNum.setText("LEVEL " + level);
            trueNum = random.nextInt(100);
            hilo.setText("Lv");
            guessCount = 0;
            nextLevel = true;
        } else if (guess < trueNum) {
            hilo.setText("Lo");
        } else {
            hilo.setText("Hi");
        }

        if (nextLevel) {
            triesLeft.setBounds(80, 480, 350, 100);
            triesLeft.setText("Congrats! You reached LEVEL " + level);
            JOptionPane.showMessageDialog(null, "Congrats, proceed to the next level!");
        } else {
            triesLeft.setText("You have " + (guessLimit - guessCount) + " tries left!");
        }

        guessField.setText(null);
        guessField.requestFocus();

        if (guessCount >= guessLimit) {
            this.setVisible(false);
            ProfileWriter writer = new ProfileWriter();
            writer.writeToMap(username, level);
            JOptionPane.showMessageDialog(null, "Thanks for playing! You reached level " +
                    level, "Very Nice!", JOptionPane.PLAIN_MESSAGE);
            Leaderboard leaderboard = new Leaderboard();
            leaderboard.setVisible(true);
        }
    }
}