package bailam;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class register {

	private JFrame frame;
	private JTextField userField, fullnameField, phoneField;
	private JPasswordField passField, confirmPassField;
	private JButton registerButton;

	public register() {
		frame = new JFrame("Register");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		BackgroundPanel panel = new BackgroundPanel("E:\\tailieujava\\register.png");
		panel.setLayout(null);

		JLabel loginLabel = new JLabel("Register");
		loginLabel.setFont(new Font("Arial", Font.BOLD, 30));
		loginLabel.setBounds(650, 75, 150, 40);
		loginLabel.setForeground(Color.black);
		panel.add(loginLabel);

		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(450, 150, 150, 30);
		userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		userLabel.setForeground(Color.black);
		panel.add(userLabel);

		userField = new JTextField();
		userField.setBounds(450, 180, 200, 30);
		userField.setBackground(Color.white);
		userField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		userField.setMargin(new Insets(10, 20, 10, 10));
		panel.add(userField);

		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(450, 240, 150, 30);
		passLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		passLabel.setForeground(Color.black);

		panel.add(passLabel);

		passField = new JPasswordField();
		passField.setBounds(450, 275, 200, 30);
		passField.setBackground(Color.white);
		passField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		panel.add(passField);

		JLabel confirmPassLabel = new JLabel("Confirm Password");
		confirmPassLabel.setBounds(450, 335, 200, 30);
		confirmPassLabel.setForeground(Color.black);
		confirmPassLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(confirmPassLabel);

		confirmPassField = new JPasswordField();
		confirmPassField.setBounds(450, 375, 200, 30);
		confirmPassField.setBackground(Color.white);
		confirmPassField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		panel.add(confirmPassField);

		JLabel fullnameLabel = new JLabel("Full Name");
		fullnameLabel.setBounds(750, 150, 100, 30);
		fullnameLabel.setForeground(Color.black);
		fullnameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(fullnameLabel);

		fullnameField = new JTextField();
		fullnameField.setBounds(750, 180, 200, 30);
		fullnameField.setBackground(Color.white);
		fullnameField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		fullnameField.setMargin(new Insets(10, 20, 10, 10));
		panel.add(fullnameField);

		JLabel phoneLabel = new JLabel("Phone Number");
		phoneLabel.setBounds(750, 240, 150, 30);
		phoneLabel.setForeground(Color.black);
		phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(phoneLabel);

		phoneField = new JTextField();
		phoneField.setBounds(750, 275, 200, 30);
		phoneField.setBackground(Color.white);
		phoneField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

		phoneField.setMargin(new Insets(10, 20, 10, 10));
		panel.add(phoneField);

		registerButton = new JButton("Sign Up");
		registerButton.setBounds(625, 480, 200, 40);
		registerButton.setForeground(Color.black);
		registerButton.setBorder(new LineBorder(Color.black, 2));
		registerButton.setFocusPainted(false);
		registerButton.setBackground(new Color(173, 216, 230));
		panel.add(registerButton);

		registerButton.addActionListener(e -> registerUser());

		JLabel haveAccountLabel = new JLabel("I have an account - ");
		haveAccountLabel.setBounds(450, 415, 150, 30);
		//haveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		haveAccountLabel.setForeground(Color.black);
		panel.add(haveAccountLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(550, 415, 80, 30);
		//loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
		loginButton.setForeground(Color.red);
		loginButton.setBorder(BorderFactory.createEmptyBorder());
		loginButton.setFocusPainted(false);
		loginButton.setOpaque(false);
		loginButton.setContentAreaFilled(false);
		loginButton.setBackground(new Color(173, 216, 230));
		panel.add(loginButton);

		loginButton.addActionListener(e -> {
			frame.dispose();
			new login().showForm();
		});

		frame.setContentPane(panel);

		frame.setVisible(true);
	}

	private void registerUser() {
		String username = userField.getText();
		String password = new String(passField.getPassword());
		String confirmPassword = new String(confirmPassField.getPassword());
		String fullname = fullnameField.getText();
		String phone = phoneField.getText();

		if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullname.isEmpty()
				|| phone.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ thông tin!");
			return;
		}

		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(frame, "Phải nhập lại đúng Password");
			return;
		}
		
		if(!isValidFullName(fullname)) {
			JOptionPane.showMessageDialog(frame, "Tên không hợp lệ");
			return;
		}
		
		if(!isValidPhoneNumber(phone)) {
			JOptionPane.showMessageDialog(frame, "Số điện thoại không hợp lệ");
			return;
		}
		//JOptionPane.showMessageDialog(frame, "Đăng ký thành công!");
		
		
		try (Connection conn = db.getConnection()) {
			
			
	        String check = "SELECT COUNT(*) FROM account WHERE username = ?";
	        try (PreparedStatement checkStmt = conn.prepareStatement(check)) {
	            checkStmt.setString(1, username);
	            try (ResultSet rs = checkStmt.executeQuery()) {
	                if (rs.next() && rs.getInt(1) > 0) {
	                    JOptionPane.showMessageDialog(frame, "Username đã tồn tại!");
	                    return; 
	                }
	            }
	        }

	        
	        String query = "INSERT INTO account (username, password, full_name, phone_number, role) VALUES (?, ?, ?, ?, 'user')";
	        try (PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, username);
	            stmt.setString(2, password);
	            stmt.setString(3, fullname);
	            stmt.setString(4, phone);

	            int rows = stmt.executeUpdate(); 
	            if (rows > 0) {
	                JOptionPane.showMessageDialog(frame, "Đăng ký thành công!");
	                frame.dispose(); 
	                new login().showForm(); 
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(frame, "Lỗi đăng ký: " + e.getMessage());
	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(frame, "Lỗi: Không tìm thấy Driver MySQL");
	    }
	}
	private boolean isValidPhoneNumber(String phone) {
		return phone.matches("^0[0-9]{9}$");
	}
	private boolean isValidFullName(String fullname) {
	    return fullname.matches("^[a-zA-Z0-9 ]+$");
	}
	public static void main(String[] args) {
		new register();
	}
}
