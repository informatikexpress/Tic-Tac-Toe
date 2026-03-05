import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TikTakToe extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private String currentPlayer;
    private String winner;

    public TikTakToe() {
        super("TikTakToe");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buttons = new JButton[3][3];
        currentPlayer = "X";
        winner = "";
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                JButton button = new JButton();
                button.setFont(new Font("Monospaced", Font.BOLD, 80));
                button.setFocusable(false);
                button.addActionListener(this);
                panel.add(button);
                buttons[y][x] = button;
            }
        }
        add(panel);
        setVisible(true);
    }

    private String symboleAt(int x, int y){
        return buttons[y][x].getText();
    }

    private void reset(){
        for(JButton[] row : buttons){
            for(JButton button: row){
                button.setText("");
                button.setEnabled(true);
            }
        }
        winner="";
    }

    private boolean checkWinner(String a, String b, String c){
        if(!a.isEmpty() && a.equals(b) && a.equals(c)){
            winner = a;
        }
        return a.isEmpty() || b.isEmpty() || c.isEmpty();
    }

    private void checkWinner(){
        boolean anyEmpty = false;
        for (int y = 0; y < 3; y++) {
        if(checkWinner(symboleAt(0,y),symboleAt(1,y),symboleAt(2,y))){
            anyEmpty = true;
        }
        }
        for (int x = 0; x < 3; x++) {
           if(checkWinner(symboleAt(x,0),symboleAt(x,1),symboleAt(x,2))){
               anyEmpty = true;
           }
        }
        if(checkWinner(symboleAt(0,0),symboleAt(1,1),symboleAt(2,2))|
        checkWinner(symboleAt(2,0), symboleAt(1,1),symboleAt(0,2))){
            anyEmpty= true;
        }
        if(!winner.isEmpty() || !anyEmpty) {
            String message = winner.isEmpty()? "tie!" : "Player " + winner + " won!";
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
         reset();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    button.setText(currentPlayer);
    button.setEnabled(false);
    checkWinner();
    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    public static void main(String[] args){

        new TikTakToe();
    }
}