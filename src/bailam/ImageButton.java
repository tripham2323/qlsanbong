package bailam;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class ImageButton extends JButton {
    private ImageIcon backgroundImage;

    public ImageButton(String text, String imagePath) {
        super(text);
        this.backgroundImage = new ImageIcon(imagePath);  // Đọc hình ảnh từ file
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ hình nền
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), (ImageObserver) this);  // Vẽ ảnh nền
        }
    }
}

