import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountdownTimer extends JFrame {

    private JTextField hoursField;
    private JTextField minutesField;
    private JTextField secondsField;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearButton;
    private Timer timer;

    public CountdownTimer() {
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 420);
        setTitle("Count Down Timer");

        JPanel centerPanel = new JPanel(new GridBagLayout());
        Font font = new Font("Arial", Font.PLAIN, 100);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < 5; ++i) {
            JTextField textField = new JTextField();
            if (i % 2 != 0) {
                textField.setText(":");
                textField.setFont(font);
                textField.setEditable(false);
            } else {
                textField.setText("00");
                textField.setFont(font);
                if (i == 0) {
                    hoursField = textField;
                } else if (i == 2) {
                    minutesField = textField;
                } else {
                    secondsField = textField;
                }
            }
            centerPanel.add(textField, gbc);
            gbc.gridx++;
        }

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown();
            }
        });
        centerPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCountdown();
            }
        });
        centerPanel.add(stopButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        centerPanel.add(clearButton);

        add(centerPanel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCountdown();
            }
        });

        setVisible(true);
    }

    private void startCountdown() {
        try {
            int inputHours = Integer.parseInt(hoursField.getText());
            int inputMinutes = Integer.parseInt(minutesField.getText());
            int inputSeconds = Integer.parseInt(secondsField.getText());
            if (inputHours >= 0 && inputMinutes >= 0 && inputSeconds >= 0) {
                hoursField.setEnabled(false);
                minutesField.setEnabled(false);
                secondsField.setEnabled(false);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                clearButton.setEnabled(false);
                timer.start();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter non-negative integers.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter non-negative integers.");
        }
    }

    private void stopCountdown() {
        timer.stop();
        hoursField.setEnabled(true);
        minutesField.setEnabled(true);
        secondsField.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        clearButton.setEnabled(true);
    }

    private void clearFields() {
        timer.stop();
        hoursField.setText("00");
        minutesField.setText("00");
        secondsField.setText("00");
        hoursField.setEnabled(true);
        minutesField.setEnabled(true);
        secondsField.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        clearButton.setEnabled(true);
    }

    private void updateCountdown() {
        int currentHours = Integer.parseInt(hoursField.getText());
        int currentMinutes = Integer.parseInt(minutesField.getText());
        int currentSeconds = Integer.parseInt(secondsField.getText());

        if (currentSeconds > 0) {
            currentSeconds--;
        } else {
            if (currentMinutes > 0) {
                currentMinutes--;
                currentSeconds = 59;
            } else {
                if (currentHours > 0) {
                    currentHours--;
                    currentMinutes = 59;
                    currentSeconds = 59;
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(this, "Countdown complete!");
                    hoursField.setEnabled(true);
                    minutesField.setEnabled(true);
                    secondsField.setEnabled(true);
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    clearButton.setEnabled(true);
                }
            }
        }

        hoursField.setText(String.format("%02d", currentHours));
        minutesField.setText(String.format("%02d", currentMinutes));
        secondsField.setText(String.format("%02d", currentSeconds));
    }

    public static void main(String[] args) {
        new CountdownTimer();
    }
}

