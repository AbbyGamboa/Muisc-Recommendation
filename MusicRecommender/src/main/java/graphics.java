import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abbygamboa
 */
public class graphics{
    public static JFrame frame = new JFrame("Artist Recommendation System");
    public static JPanel contentPane = new JPanel();
    public static String answer;
    public static int value;
    public static boolean ifCorrect;
    public static int numCount = 1;
    public static void dra(){
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        
        //welcome screen 
        JLabel label1 = new JLabel("<html> Welcome to the Music recommendation "
                + "system!</html>");
        label1.setForeground(Color.GREEN);
        label1.setFont(new Font("Monospaced", Font.BOLD, 28));
        label1.setSize(300, 300);
        label1.setLocation(95, 0);
        //added detail for fun
        JLabel label2 = new JLabel("<html> Welcome to the Music recommendation "
                + "system!</html>");
        label2.setForeground(Color.BLACK);
        label2.setFont(new Font("Monospaced", Font.BOLD, 28));
        label2.setSize(300, 300);
        label2.setLocation(98, 0);
        /*if hit rules button, menu will pop up explaining the objective 
        of the system*/
        JButton menu = new JButton("RULES");
        menu.setSize(100,30);
        menu.setLocation(200,300);
        menu.addActionListener((ActionEvent evt) -> {
            menu();
        });
        
        //one the user is ready, answer is set to yes to begin prompting questions
        JButton button = new JButton("READY!");
        button.setSize(100, 30);
        button.setLocation(95, 300);
        button.addActionListener((ActionEvent evt) -> {
            contentPane.removeAll();
            answer = "yes";
        });
        
        contentPane.add(menu);
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(button);

        frame.setContentPane(contentPane);
        frame.setSize(410, 410);
        frame.setVisible(true);

    }
    
    public static void menu(){
        JPanel menuPane = new JPanel();
        menuPane.setBackground(Color.WHITE);
        JLabel label = new JLabel("<HTML> You will be prompted with a number <br> " 
                                    + "of questions regarding artists based  <br> "
                                    + "in the last.fm database. For each <br>"
                                    + "artist, adjust the slider to your <br>"
                                    + "preference.Once you have reached your <br>"
                                    + "appreciation level hit the CORRECT <br> "
                                    + "button and you will be prompted with <br>"
                                    + "more questions! If you have never <br>"
                                    + "listened to the artist set the scale <br>"
                                    + "to 0.<HTML>");
        
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setSize(300, 300);
        label.setLocation(10, 0);
        
        //goes back to welcome page
        JButton button = new JButton("<- Back");
        button.setSize(100, 30);
        button.setLocation(0, 0);
        button.addActionListener((ActionEvent evt) -> {
            dra();
        });
        
        menuPane.add(label);
        menuPane.add(button);
        frame.setContentPane(menuPane);
        
        frame.setVisible(true);
    }
    
    public void printDisplay(String words){
        answer = "";
        /*ifCorrect notifies if the correct button was clicked, 
        meaning the user is satisfied with their response*/
        ifCorrect = false;
        
        //prompts user with an artist
        JLabel label = new JLabel(words);
        label.setForeground(Color.black);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        label.setSize(400, 100);
        label.setLocation(5, 0);
        
        //Establishes a slider 1-10 with the slider initialized at 5
        JSlider slide = new JSlider(JSlider.HORIZONTAL, 0,10,5);
        slide.setBackground(Color.white);
        slide.setSize(250, 100);
        slide.setMinorTickSpacing(1);
        slide.setMajorTickSpacing(5);
        slide.setPaintTrack(true);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.setLocation(40,40);
        /*slide.addChangeListener((ChangeEvent ce) -> {
        });*/
        
        JButton correct = new JButton("CORRECT");
        correct.setSize(200, 30);
        correct.setLocation(95, 150);
        correct.addActionListener((ActionEvent evt) -> {
            contentPane.removeAll();
            // gets the value off of the slider
            value = slide.getValue();
            // sets correct button to notify musicRec is was clicked
            ifCorrect = true;
            //for extra detail, also to ensure a set amount of questions are prompted
            numCount++;
        });
        
        
        contentPane.removeAll();
        contentPane.add(slide);
        contentPane.add(label);
        contentPane.add(correct);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
    
    public void printNext(String line){
        //sets answer back to empty string
        answer = "";
        //prompts user if they would like to try the system again. 
        JLabel label = new JLabel(line);
        label.setForeground(Color.black);
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setSize(300, 25);
        label.setLocation(0, 0);
        
        //if yes, system begins again, if no, system ends prompting exit screen
        JButton buttonY = new JButton("YES");
        buttonY.setSize(100, 30);
        buttonY.setLocation(50, 95);
        buttonY.addActionListener((ActionEvent evt) -> {
            answer = "yes";
        });
        
        JButton buttonN = new JButton("NO");
        buttonN.setSize(100, 30);
        buttonN.setLocation(195, 95);
        buttonN.addActionListener((ActionEvent evt) -> {
            answer = "no";
        });

        contentPane.removeAll();
        contentPane.add(buttonY);
        contentPane.add(buttonN);
        contentPane.add(label);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
   
    public void OnExit(String finish){
        JLabel label1 = new JLabel(finish);
        label1.setForeground(Color.GREEN);
        label1.setFont(new Font("Monospaced", Font.BOLD, 28));
        label1.setSize(400, 300);
        label1.setLocation(0, 0);
        
        JLabel label2 = new JLabel(finish);
        label2.setForeground(Color.black);
        label2.setFont(new Font("Monospaced", Font.BOLD, 28));
        label2.setSize(400, 300);
        label2.setLocation(3, 0);
        
        contentPane.removeAll();
        contentPane.add(label1);
        contentPane.add(label2);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
    
    public void printArtist(String line1, String line2){
        //answer always set back to empty string
        answer = "";
        //gives user their recommended artist
        JLabel label1 = new JLabel(line1);
        label1.setForeground(Color.black);
        label1.setFont(new Font("Monospaced", Font.BOLD, 14));
        label1.setSize(300, 25);
        label1.setLocation(5, 0);
        
        //prompts them if they want to view their page
        JLabel label2 = new JLabel(line2);
        label2.setForeground(Color.black);
        label2.setFont(new Font("Monospaced", Font.BOLD, 14));
        label2.setSize(300, 25);
        label2.setLocation(0,25);
        
        /*two buttons display a given answer either they do want to visit the 
        page or they do not*/
        JButton buttonY = new JButton("YES");
        buttonY.setSize(100, 30);
        buttonY.setLocation(95, 95);
        buttonY.addActionListener((ActionEvent evt) -> {
            answer = "yes";
        });
        
        JButton buttonN = new JButton("NO");
        buttonN.setSize(100, 30);
        buttonN.setLocation(195, 95);
        buttonN.addActionListener((ActionEvent evt) -> {
            answer = "no";
        });
        
        contentPane.removeAll();
        contentPane.add(buttonY);
        contentPane.add(buttonN);
        contentPane.add(label1);
        contentPane.add(label2);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
    
    // used in the musicRec class to call on values found in graphics
    public int getCount(){
        return numCount;
    }
    public boolean getIfCorrect(){
        return ifCorrect;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public Integer getValue(){
        return value;
    }
    
    public JFrame getFrame(){
        return frame;
    }
}
