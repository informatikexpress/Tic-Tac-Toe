import javax.swing.*; //Diese Zeilen importieren die nötigen Bibliotheken.
import java.awt.*; //javax.swing und java.awt werden für die grafische Oberfläche (Fenster, Buttons, Layouts) benötigt.
import java.awt.event.ActionEvent; //Die event Klassen kümmern sich darum, dass das Programm reagiert, wenn man etwas anklickt.
import java.awt.event.ActionListener;

public class TikTakToe extends JFrame implements ActionListener { //Hauptklasse. Mit extends JFrame ist die Klasse jetzt ein Fenster. Implements ActionListener verpflichtet sich, auf Klicks (Events) zu hören und darauf zu reagieren.
    private JButton[][] buttons; //Ein zweidimensionales Array, das die 9 Buttons(3x3) speichert.
    private String currentPlayer; //Speichert, wer grade dran ist (entweder "X" oder "O").
    private String winner; //Speichert, den Gewinner. Wenn noch niemand gewonnen hat, ist dieser String leer.

    public TikTakToe() {
        super("TikTakToe"); // Gibt dem Fenster den Titel "TikTakToe"
        setSize(500,500); // Setzt die Fenstergröße auf 500x500 Pixel
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Beendet das Programm, wenn das Fenster geschlossen wird
        setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschrim
        buttons = new JButton[3][3]; //Erstellt das 3x3 Raster für die Buttons im Speicher
        currentPlayer = "X"; // Spieler X darf anfangen
        winner = ""; //Zu Beginn gibt es noch keinen Gewinner
        JPanel panel = new JPanel(new GridLayout(3, 3)); // Erstellt ein Panel (eine Art Container), das seine Elemente in einem 3x3 Gitter anordnet
        //Jetzt kommt eine verschachtelte Schleife, um die 9 Buttons zu erstellen
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                JButton button = new JButton(); //Erstellt einen neuen Button
                button.setFont(new Font("Monospaced", Font.BOLD, 80)); // Macht die Schrift auf dem Button riesig (Größe 80) und fett
                button.setFocusable(false); //Verhindert einen unschönen Rahmen um den Text nach dem Klicken
                button.addActionListener(this); // Sagt dem Button: "Wenn du geklickt wirst, melde dich bei dieser Klasse (this)"
                panel.add(button); //Fügt dem Button sichtbar dem Panel hinzu
                buttons[y][x] = button; // Speichert den Button in unserem Array, um später darauf zugreifen zu können
            }
        }
        add(panel); // Fügt das gesamte 3x3-Panel dem Hauptfenster hinzu
        setVisible(true); // Macht das Fenster auf dem Bildschirm sichtbar
    }
 // Hilfsmethode
    private String symboleAt(int x, int y){ //Diese kleine Methode ist sehr praktisch.
        return buttons[y][x].getText(); // Sie schaut im Array nach, welcher Text("X","O" oder leer"") auf dem Button an der Position X/Y steht, und gibt diesen zurück.
    }

    private void reset(){ //Diese Methode setzt das Spielfeld zurück. Sie geht durch jeden einzelnen Button, macht ihn leer und wieder anklickbar.
        for(JButton[] row : buttons){ // Gehe jede Reihe im Array Buttons durch
            for(JButton button: row){ // Gehe jeden button in dieser Reihe durch
                button.setText(""); // Löscht den Text auf dem Button ("X" oder "O")
                button.setEnabled(true); // Macht den Button wieder klickbar
            }
        }
        winner=""; // Setzt den Gewinner zurück
    }

    private boolean checkWinner(String a, String b, String c){ // Hier gibt es zwei Methoden mit demselben Namen (checkWinner), aber unterschiedlichen Parametern. Das nennt man "Overloading".
        if(!a.isEmpty() && a.equals(b) && a.equals(c)){ //Wenn Feld 'a' nicht leer ist und 'a' gleich 'b' ist und 'a' gleich 'c' ist...
            winner = a; //...dann haben wir drei gleiche Symbole in einer Reihe! Speichere den Gewinner.
        } // Gibt 'true' zurück, wenn mindestens eines der drei Felder leer ist.
        // Gibt 'false' zurück, wenn alle drei Felder belegt sind.
        return a.isEmpty() || b.isEmpty() || c.isEmpty(); // Wenn alle drei Felder belegt sind braucht man auch kein Gewinner ermitteln und kann direkt zur nächsten Zeile
    }

    private void checkWinner(){
        boolean anyEmpty = false; // Wir merken uns hier, ob es noch freie Felder auf dem Brett gibt.
        for (int y = 0; y < 3; y++) { //checkt alle drei horizontal Reihen.
        if(checkWinner(symboleAt(0,y),symboleAt(1,y),symboleAt(2,y))){ // 1.symboleAT() holt den Inhalt eines Feldes. 2.checkWinner(...) prüft mit .isEmpty(), ob das Feld leer ist. 3. Wenn ja -> return true. 4. Dann wird anyEmpty = true
            anyEmpty = true; //Wenn in dieser Reihe noch ein leeres Feld war, merken wir uns das.
             }
        }
        for (int x = 0; x < 3; x++) { //checkt alle drei vertikalen Spalten
           if(checkWinner(symboleAt(x,0),symboleAt(x,1),symboleAt(x,2))){
               anyEmpty = true;
           }
        }
        if(checkWinner(symboleAt(0,0),symboleAt(1,1),symboleAt(2,2))| //checkt die beiden Diagonalen
        checkWinner(symboleAt(2,0), symboleAt(1,1),symboleAt(0,2))){
            anyEmpty= true;
        } // Wenn ein Gewinner feststeht oder kein Feld mehr frei ist (Unentschieden)
        if(!winner.isEmpty() || !anyEmpty) { //Winner ist nicht leer -> es gibt ein Gewinner oder es gibt kein freies Feld mehr
            String message = winner.isEmpty()? "tie!" : "Player " + winner + " won!"; //Erstellt die Nachricht für das Popup
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE); //Zeigt ein kleines Info-Fenster (Popup) mit der Nachricht
         reset(); //Startet das Spiel neu
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource(); // Findet heraus, welcher der 9 Buttons genau geklickt wurde
    button.setText(currentPlayer); // Schreibt "X" oder "O" auf diesen Button
    button.setEnabled(false); // Deaktiviert den Button, damit man ihn nicht nochmal klicken kann
    checkWinner(); //Prüft, ob dieser Zug zum Sieg oder Unentschieden geführt hat
    currentPlayer = currentPlayer.equals("X") ? "O" : "X"; // Wechselt den Spieler: Wenn currentPlayer "X" ist, wird er "O", ansonsten wird er "X"
    }

    public static void main(String[] args){

        new TikTakToe(); //Erstellt ein neues Spiel-Objekt. Dadurch wird der Konstruktor aufgerufen und das Fenster erscheint.
    }
}