import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class reads from the userProfiles.txt file and displays a leaderboard that is
 * sorted based on the level in the game that player reached.
 *
 * @author Lucas Lin
 * @version Dec 1, 2019
 */
public class Leaderboard extends JFrame implements ActionListener {
    private JButton playAgainButton;
    private JButton exitButton;

    Leaderboard() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(480, 640);
        this.setResizable(false);
        this.setTitle("Leaderboard");
        this.setLocationRelativeTo(null);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        JLabel leaderLabel = new JLabel("Leaderboard");
        leaderLabel.setFont(new Font("Leader font", Font.ITALIC, 21));
        contentPane.add(leaderLabel);
        leaderLabel.setBounds(175, 10, 200, 50);

        JTextArea leadersArea = new JTextArea();
        leadersArea.setEditable(false);
        contentPane.add(leadersArea);
        leadersArea.setBounds(40, 70, 400, 400);
        leadersArea.setFont(new Font("Leader font", Font.PLAIN, 20));
        leadersArea.setText("------Name-------------Scores--------\n");

        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Button font", Font.PLAIN, 25));
        contentPane.add(playAgainButton);
        playAgainButton.addActionListener(this);
        playAgainButton.setBounds(70, 510, 200, 50);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Button font", Font.PLAIN, 25));
        contentPane.add(exitButton);
        exitButton.addActionListener(this);
        exitButton.setBounds(300, 510, 100, 50);

        ProfileReader reader = new ProfileReader();
        reader.putDataIntoMap();
        TreeMap<Integer, String> map = reader.getProfilesMap();
        ArrayList<Integer> keys = new ArrayList<>(map.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            leadersArea.append("       " + map.get(keys.get(i)) + "                                  " +
                    keys.get(i) + "             \n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == playAgainButton) {
            this.setVisible(false);
            LoginScreen login = new LoginScreen();
            login.setVisible(true);
        } else if (event.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
