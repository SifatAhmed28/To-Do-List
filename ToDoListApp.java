import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.Border;

class LoginPanel extends JPanel {
    JTextField usernameField;
    JPasswordField passwordField;

    LoginPanel() {
        setLayout(new GridLayout(3, 2));
        setBackground(new Color(240, 128, 128)); // Light Coral

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setBackground(new Color(255, 228, 196)); // Bisque
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(255, 228, 196)); // Bisque
        add(passwordField);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}

class LoginFrame extends JFrame {
    LoginPanel loginPanel;

    LoginFrame() {
         setTitle("Manage Your Day with To-Do List"); 
      
        setSize(390, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginPanel = new LoginPanel();
        add(loginPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Submit");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        loginButton.setBackground(new Color(240, 128, 128)); // Light Coral
        add(loginButton, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        // Replace this with your actual authentication logic
        if ("Admin".equals(username) && "1234".equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();  // Close the login window after successful login
            AppFrame appFrame = new AppFrame();  // Open the main application window
            appFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class Task extends JPanel {
    JLabel index;
    JTextField taskName;
    JButton done;
    JLabel timeLabel;

    Color taskColor = new Color(173, 216, 230); // Light Blue
    Color completedColor = new Color(152, 251, 152); // Pale Green
    Color doneColor = new Color(255, 160, 122); // Light Coral

    private boolean checked;
    private Calendar alarmTime;

    Task() {
        this.setPreferredSize(new Dimension(400, 40));
        this.setBackground(taskColor);

        this.setLayout(new BorderLayout());

        checked = false;
        alarmTime = null;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write something..");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(taskColor);
        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);
        done.setFocusPainted(false);
        this.add(done, BorderLayout.EAST);

        timeLabel = new JLabel("No Time Set");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(timeLabel, BorderLayout.SOUTH);
    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(completedColor);
        taskName.setBackground(completedColor);
        checked = true;
        revalidate();
    }

    public void setAlarmTime(Calendar time) {
        this.alarmTime = time;
        updateTimeLabel();
    }

    private void updateTimeLabel() {
        if (alarmTime != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm");
            String formattedTime = dateFormat.format(alarmTime.getTime());
            timeLabel.setText("Alarm: " + formattedTime);
        } else {
            timeLabel.setText("No Time Set");
        }
    }
}

class List extends JPanel {
    Color listColor = new Color(255, 228, 196); // Bisque

    List() {
        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5);

        this.setLayout(layout);
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(listColor);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }
    }

    public void removeCompletedTasks() {
        for (Component c : getComponents()) {
            if (c instanceof Task) {
                Task task = (Task) c;
                if (task.getState()) {
                    remove(c);
                    updateNumbers();
                }
            }
        }
    }
}

class Footer extends JPanel {
    JButton addTask;
    JButton clear;
    JButton setTime;

    Color buttonColor = new Color(240, 128, 128); // Light Coral
    Color footerColor = new Color(255, 228, 196); // Bisque
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(footerColor);

        addTask = new JButton("Add Task");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        addTask.setVerticalAlignment(JButton.BOTTOM);
        addTask.setBackground(buttonColor);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20));

        clear = new JButton("Clear finished tasks");
        clear.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        clear.setBorder(emptyBorder);
        clear.setBackground(buttonColor);
        this.add(clear);

        this.add(Box.createHorizontalStrut(20));

        setTime = new JButton("Set Time");
        setTime.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        setTime.setBorder(emptyBorder);
        setTime.setBackground(buttonColor);
        this.add(setTime);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }

    public JButton getSetTime() {
        return setTime;
    }
}

class TitleBar extends JPanel {
    Color titleColor = new Color(255, 228, 196); // Bisque

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(titleColor);
        JLabel titleText = new JLabel("Welcome to To-Do List"); // Updated title
        titleText.setPreferredSize(new Dimension(300, 60));
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class AppFrame extends JFrame {
    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;
    private JButton clear;
    private JButton setTime;

    AppFrame() {
        this.setTitle("Welcome to To-Do List"); // Set the title here
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(list, BorderLayout.CENTER);

        newTask = footer.getNewTask();
        clear = footer.getClear();
        setTime = footer.getSetTime();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Task task = new Task();
                list.add(task);
                list.updateNumbers();

                task.getDone().addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        task.changeState();
                        list.updateNumbers();
                        revalidate();
                    }
                });

                setTime.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        setTaskTime(task);
                    }
                });
            }
        });

        clear.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                list.removeCompletedTasks();
                repaint();
            }
        });
    }

    private void setTaskTime(Task task) {
        String timeString = JOptionPane.showInputDialog(this, "Enter task time (HH:mm):");
        if (timeString != null && !timeString.isEmpty()) {
            try {
                String[] timeParts = timeString.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);

                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, hours);
                time.set(Calendar.MINUTE, minutes);

                task.setAlarmTime(time);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Invalid time format. Please use HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

public class ToDoListApp {

    public static void main(String args[]) {
        // Open the login window
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}

