import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends JFrame implements ActionListener {

    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    JTextField inputField = new JTextField();
    TimerThread timerThread;

    Timer() {
        setSize(300, 300);
        setLayout(null);

        playButton.setBounds(50, 100, 100, 50);
        add(playButton);
        stopButton.setBounds(160, 100, 100, 50);
        add(stopButton);
        inputField.setBounds(100, 200, 100, 50);
        add(inputField);
        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            if (timerThread == null || !timerThread.isRunning()) {
                timerThread = new TimerThread(playButton, inputField.getText());
                timerThread.start();
            }
        } else if (e.getSource() == stopButton) {
            if (timerThread != null && timerThread.isRunning()) {
                timerThread.stopTimer();
            }
        }
    }
}

class TimerThread extends Thread {
    private JButton playButton;
    private String inputText;
    private int secondsElapsed = 0;
    private volatile boolean running = true;

    public TimerThread(JButton playButton, String inputText) {
        this.playButton = playButton;
        this.inputText = inputText;
    }

    public void run() {
        if (!inputText.matches("[0-9]+")) {
            playButton.setText("Error");
            return;
        }

        while (running) {
            playButton.setText(secondsElapsed / 60 + " : " + secondsElapsed % 60);
            secondsElapsed++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
