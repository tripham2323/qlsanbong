package bailam;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

//jcalendar, jfreechart
public class login extends JFrame {

	private JFrame frame;

	public login() {

		frame = new JFrame("Login Form");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		BackgroundPanel panel = new BackgroundPanel("E:\\tailieujava\\login.png");
		panel.setLayout(null);

		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
		loginLabel.setBounds(140, 60, 100, 40);
		loginLabel.setForeground(Color.black);
		panel.add(loginLabel);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(100, 175, 100, 30);
		userLabel.setForeground(Color.black);
		panel.add(userLabel);

		JTextField userText = new JTextField();
		userText.setBounds(100, 215, 200, 30);
		userText.setBackground(Color.white);
		userText.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		userText.setMargin(new Insets(10, 20, 10, 10));
		// userText.setHorizontalAlignment(JTextField.LEFT);
		// userText.setForeground(Color.WHITE);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(100, 255, 100, 30);
		passwordLabel.setForeground(Color.black);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField();
		passwordText.setBounds(100, 295, 200, 30);
		passwordText.setBackground(Color.white);
		passwordText.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		panel.add(passwordText);

		JButton signInButton = new JButton("Sign In");
		signInButton.setBounds(100, 335, 200, 40);
		signInButton.setForeground(Color.black);
		signInButton.setBorder(new LineBorder(Color.black, 2));
		signInButton.setFocusPainted(false);
		signInButton.setBackground(new Color(173, 216, 230));
		// signInButton.setOpaque(false);
		// signInButton.setContentAreaFilled(false);
		panel.add(signInButton);

		JLabel dontHaveAccountLabel = new JLabel("Don't have an account?");
		dontHaveAccountLabel.setBounds(100, 385, 200, 30);
		dontHaveAccountLabel.setForeground(Color.black);
		panel.add(dontHaveAccountLabel);

		JButton registerButton = new JButton("Register");
		registerButton.setBounds(230, 385, 100, 30);
		registerButton.setForeground(Color.red);
		registerButton.setBorder(BorderFactory.createEmptyBorder());
		registerButton.setFocusPainted(false);
		registerButton.setOpaque(false);
		registerButton.setContentAreaFilled(false);
		registerButton.setBackground(new Color(173, 216, 230));
		panel.add(registerButton);

		registerButton.addActionListener(e -> {
			frame.dispose();
			new register();
		});

		signInButton.addActionListener(e -> {
			String username = userText.getText();
			String password = new String(passwordText.getPassword());

			if (username.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Vui lòng nhập đầy đủ thông tin!");
				return;
			}

			try (Connection conn = db.getConnection()) {

				String query = "SELECT password FROM account WHERE username = ?";
				try (PreparedStatement stmt = conn.prepareStatement(query)) {
					stmt.setString(1, username);
					try (ResultSet rs = stmt.executeQuery()) {
						if (rs.next()) {

							String storedPassword = rs.getString("password");
							if (storedPassword.equals(password)) {
								JOptionPane.showMessageDialog(frame, "Đăng nhập thành công!");
								frame.dispose();
								new soccerField(username);
							} else {
								JOptionPane.showMessageDialog(frame, "Sai mật khẩu!");
							}
						} else {
							JOptionPane.showMessageDialog(frame, "Tài khoản không tồn tại!");
						}
					}
				}
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(frame, "Lỗi khi kết nối database: " + e1.getMessage());
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(frame, "Lỗi: Không tìm thấy Driver MySQL");
			}
		});

		frame.setContentPane(panel);
	}

	public void showForm() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {

	}
}
