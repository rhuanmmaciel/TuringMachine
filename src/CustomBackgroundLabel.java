import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class CustomBackgroundLabel extends JLabel {
    private final int highlightPosition;

    public CustomBackgroundLabel(String text, int highlightPosition) {
        super(text);
        this.highlightPosition = highlightPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        String text = getText();
        if (text != null && highlightPosition >= 0 && highlightPosition < text.length()) {

            Graphics2D g2d = (Graphics2D) g.create();
            FontMetrics fm = g2d.getFontMetrics();

            int x = 0;
            int y = fm.getAscent();

            for (int i = 0; i < text.length(); i++) {

                char c = text.charAt(i);
                String character = String.valueOf(c);

                if (i == highlightPosition) {

                    g2d.setColor(Color.YELLOW);
                    int width = fm.stringWidth(character);
                    Rectangle2D.Float background = new Rectangle2D.Float(x, 0, width, getHeight());
                    g2d.fill(background);
                    g2d.setColor(Color.BLACK);

                }

                g2d.drawString(character, x, y);
                x += fm.stringWidth(character);

            }

            g2d.dispose();

        }
    }
}
