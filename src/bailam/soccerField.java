package bailam;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class soccerField extends JFrame {
	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel westPanel;
	private JLabel userLabel;
	private JButton signOutButton;
	private JLabel menuLabel;
	private JButton[] menuButtons;

	public soccerField(String username) {
		setTitle("VKU Football!");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		northPanel = new JPanel(new BorderLayout());
		northPanel.setBackground(new Color(255, 250, 200));
		northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		userLabel = new JLabel("Welcome back, " + username + "!");
		userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		userLabel.setIcon(new ImageIcon("user_icon.png"));
		northPanel.add(userLabel, BorderLayout.WEST);

		signOutButton = new JButton("Sign Out");
		signOutButton.setBackground(Color.RED);
		signOutButton.setForeground(Color.WHITE);
		signOutButton.setFocusPainted(false);
		signOutButton.addActionListener(e -> {
		    int confirm = JOptionPane.showConfirmDialog(null, 
		        "Are you sure you want to sign out?", 
		        "Confirm logout", 
		        JOptionPane.YES_NO_OPTION);
		    
		    if (confirm == JOptionPane.YES_OPTION) {
		        dispose(); 
		        new login().showForm(); 
		    }
		});
		northPanel.add(signOutButton, BorderLayout.EAST);

		add(northPanel, BorderLayout.NORTH);

		westPanel = new JPanel();
		westPanel.setBackground(new Color(139, 69, 19));
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		menuLabel = new JLabel("Menu");
		menuLabel.setForeground(Color.WHITE);
		menuLabel.setFont(new Font("Arial", Font.BOLD, 18));
		menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		westPanel.add(menuLabel);

		String[] buttonTexts = { "Dashboard", "Fields", "Reservations", "Waitlist", "Report" };
		menuButtons = new JButton[buttonTexts.length];

		for (int i = 0; i < buttonTexts.length; i++) {
			menuButtons[i] = new JButton(buttonTexts[i]);
			menuButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			menuButtons[i].setMaximumSize(new Dimension(200, 40));
			menuButtons[i].setFocusPainted(false);
			menuButtons[i].setBackground(Color.ORANGE);
			menuButtons[i].setForeground(Color.BLACK);

			if (buttonTexts[i].equals("Dashboard")) {
				menuButtons[i].addActionListener(e -> showDashboard());
			} else if (buttonTexts[i].equals("Fields")) {
				menuButtons[i].addActionListener(e -> showFields());
			} else if (buttonTexts[i].equals("Reservations")) {
				menuButtons[i].addActionListener(e -> showReservations());
			} else if (buttonTexts[i].equals("Waitlist")) {
				menuButtons[i].addActionListener(e -> showWaitlists());
			} else if (buttonTexts[i].equals("Report")) {
				menuButtons[i].addActionListener(e -> showReport());
			}

			westPanel.add(Box.createVerticalStrut(10));
			westPanel.add(menuButtons[i]);
		}

		add(westPanel, BorderLayout.WEST);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);

		showDashboard();

		setVisible(true);

	}

	private void showDashboard() {
		centerPanel.removeAll();
		centerPanel.add(new BackgroundPanel("E:\\tailieujava\\dashboard2.png"), BorderLayout.CENTER);
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	private void showFields() {
	    centerPanel.removeAll();
	    BackgroundPanel backgroundPanel = new BackgroundPanel("E:\\tailieujava\\fields.png");
	    backgroundPanel.setLayout(null);

	    JPanel buttonsPanel = new JPanel();
	    buttonsPanel.setOpaque(false);

	    String[] buttonTexts = { "Field 1", "Field 2", "Field 3", "Field 4", "Field 5" };
	    String[] imagePaths = { "E:\\tailieujava\\field1.png", "E:\\tailieujava\\field2.png",
	            "E:\\tailieujava\\field3.png", "E:\\tailieujava\\field4.png", "E:\\tailieujava\\field5.png" };
	    
	    ImageButton[] buttons = new ImageButton[buttonTexts.length];
	    
	    // Tạo các nút và gán hành động cho mỗi nút
	    for (int i = 0; i < buttonTexts.length; i++) {
	        buttons[i] = new ImageButton(buttonTexts[i], imagePaths[i]);
	        buttons[i].setBackground(Color.ORANGE);
	        buttons[i].setForeground(Color.BLACK); 
	        buttons[i].setBounds(getButtonBounds(i)); 

	        final int fieldIndex = i; 
	        buttons[i].addActionListener(e -> showAddCustomerDialog2(fieldIndex));
	        
	        backgroundPanel.add(buttons[i]);
	    }

	    centerPanel.add(backgroundPanel, BorderLayout.CENTER);
	    centerPanel.revalidate();
	    centerPanel.repaint();
	}

	private Rectangle getButtonBounds(int index) {
	    switch (index) {
	        case 0:
	            return new Rectangle(60, 85, 150, 90);
	        case 1:
	            return new Rectangle(230, 85, 150, 90);
	        case 2:
	            return new Rectangle(470, 85, 150, 90);
	        case 3:
	            return new Rectangle(670, 85, 150, 90);
	        case 4:
	            return new Rectangle(270, 360, 320, 110);
	        default:
	            return new Rectangle(0, 0, 0, 0);
	    }
	}


	private void showReservations() {
	    centerPanel.removeAll();

	    String[] columnNames = { "Booking ID", "Contact number", "Customer Name", "Field ID", "Booking Date", "Start Time",
	            "End Time", "Status", "Total Amount" };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	    JTable bookingTable = new JTable(tableModel);

	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
	    bookingTable.setRowSorter(sorter);

	    TableColumn statusColumn = bookingTable.getColumnModel().getColumn(7);
	    JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "pending", "completed", "cancelled" });
	    statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));

	    bookingTable.setFillsViewportHeight(true);

	    try (Connection conn = db.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {
	        while (rs.next()) {
	            Vector<Object> row = new Vector<>();
	            row.add(rs.getInt("booking_id"));
	            row.add(rs.getInt("contact_number"));
	            row.add(rs.getString("customer_name"));
	            row.add(rs.getInt("field_id"));
	            row.add(rs.getString("booking_date"));
	            row.add(rs.getTime("start_time"));
	            row.add(rs.getTime("end_time"));
	            row.add(rs.getString("status"));
	            row.add(rs.getDouble("total_amount"));
	            tableModel.addRow(row);
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Error when querying data" + e.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }


	    bookingTable.getModel().addTableModelListener(e -> {
	        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 7) { 
	            int row = e.getFirstRow();
	            int bookingId = (int) tableModel.getValueAt(row, 0); 
	            String newStatus = (String) tableModel.getValueAt(row, 7); 

	            
	            try (Connection conn = db.getConnection();
	                 PreparedStatement pstmt = conn.prepareStatement(
	                         "UPDATE bookings SET status = ? WHERE booking_id = ?")) {
	                pstmt.setString(1, newStatus);
	                pstmt.setInt(2, bookingId);
	                int rowsUpdated = pstmt.executeUpdate();
	                if (rowsUpdated > 0) {
	                    JOptionPane.showMessageDialog(this, "Status has been updated successfully!", "Success",
	                            JOptionPane.INFORMATION_MESSAGE);
	                }
	            } catch (SQLException | ClassNotFoundException ex) {
	                JOptionPane.showMessageDialog(this, "Error updating status:" + ex.getMessage(), "Error",
	                        JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });

	   
	    JScrollPane scrollPane = new JScrollPane(bookingTable);
	    centerPanel.add(scrollPane, BorderLayout.CENTER);

	    
	    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton deleteButton = new JButton("Delete");
	    JButton reloadButton = new JButton("Reload");
	    reloadButton.addActionListener(e -> reloadReservations(tableModel)); 
	    deleteButton.addActionListener(e -> deleteBooking(bookingTable, tableModel));
	    actionPanel.add(deleteButton);

	    JTextField searchField = new JTextField(20);
	    JButton searchButton = new JButton("Search");
	    searchButton.addActionListener(e -> searchBooking(searchField.getText(), tableModel));
	    actionPanel.add(searchField);
	    actionPanel.add(searchButton);
	    actionPanel.add(reloadButton);

	    
	    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JRadioButton completedButton = new JRadioButton("Completed");
	    JRadioButton pendingButton = new JRadioButton("Pending", true); 
	    JRadioButton cancelledButton = new JRadioButton("Cancelled");

	  
	    ButtonGroup group = new ButtonGroup();
	    group.add(completedButton);
	    group.add(pendingButton);
	    group.add(cancelledButton);

	    filterPanel.add(completedButton);
	    filterPanel.add(pendingButton);
	    filterPanel.add(cancelledButton);

	    
	    completedButton.addActionListener(e -> filterByStatus("completed", tableModel));
	    pendingButton.addActionListener(e -> filterByStatus("pending", tableModel));
	    cancelledButton.addActionListener(e -> filterByStatus("cancelled", tableModel));

	    
	    actionPanel.add(filterPanel);
	    centerPanel.add(actionPanel, BorderLayout.SOUTH);

	    centerPanel.revalidate();
	    centerPanel.repaint();

	    
	    bookingTable.getTableHeader().addMouseListener(new MouseAdapter() {
	        private boolean ascending = true; 

	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int column = bookingTable.columnAtPoint(e.getPoint());
	            if (column == 4) { 
	                ascending = !ascending;
	                sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(4, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING)));
	            }
	        }
	    });
	}

	private void filterByStatus(String status, DefaultTableModel tableModel) {
	    
	    tableModel.setRowCount(0);

	    
	    try (Connection conn = db.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(
	                 "SELECT * FROM bookings WHERE status = ?")) {
	        pstmt.setString(1, status);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Vector<Object> row = new Vector<>();
	                row.add(rs.getInt("booking_id"));
	                row.add(rs.getInt("contact_number"));
	                row.add(rs.getString("customer_name"));
	                row.add(rs.getInt("field_id"));
	                row.add(rs.getString("booking_date"));
	                row.add(rs.getTime("start_time"));
	                row.add(rs.getTime("end_time"));
	                row.add(rs.getString("status"));
	                row.add(rs.getDouble("total_amount"));
	                tableModel.addRow(row);
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Error when filtering data:" + e.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	    } catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(this, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}


	
	private void reloadReservations(DefaultTableModel tableModel) {
		
		tableModel.setRowCount(0);

		
		try (Connection conn = db.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {

			
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getInt("booking_id")); 
				row.add(rs.getInt("contact_number")); 
				row.add(rs.getString("customer_name")); 
				row.add(rs.getInt("field_id")); 
				row.add(rs.getString("booking_date")); 
				row.add(rs.getTime("start_time")); 
				row.add(rs.getTime("end_time")); 
				row.add(rs.getString("status")); 
				row.add(rs.getDouble("total_amount")); 
				tableModel.addRow(row); 
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error when reloading data!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void searchBooking(String keyword, DefaultTableModel tableModel) {
		tableModel.setRowCount(0);

		try (Connection conn = db.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookings WHERE customer_name LIKE ?")) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getInt("booking_id"));
				row.add(rs.getInt("contact_number"));
				row.add(rs.getString("customer_name"));
				row.add(rs.getInt("field_id"));
				row.add(rs.getDate("booking_date"));
				row.add(rs.getTime("start_time"));
				row.add(rs.getTime("end_time"));
				row.add(rs.getString("status"));
				row.add(rs.getDouble("total_amount"));
				tableModel.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error while searching!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(); 
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void deleteBooking(JTable table, DefaultTableModel tableModel) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a row to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int bookingId = (int) tableModel.getValueAt(selectedRow, 0);

		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try (Connection conn = db.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bookings WHERE booking_id = ?")) {
				pstmt.setInt(1, bookingId);
				int rowsDeleted = pstmt.executeUpdate();
				if (rowsDeleted > 0) {
					tableModel.removeRow(selectedRow);
					JOptionPane.showMessageDialog(this, "Deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Error while deleting:" + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	private void showWaitlists() {
		centerPanel.removeAll();

		// Tạo bảng hiển thị danh sách chờ
		String[] columnNames = { "Booking ID", "Field ID", "Customer Name", "Booking Date", "Start Time", "End Time",
				"Status", "Total Amount" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		JTable waitlistTable = new JTable(tableModel);
		waitlistTable.setFillsViewportHeight(true);

		try (Connection conn = db.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM bookings WHERE status = 'pending'")) {
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getInt("booking_id")); 
				// row.add(rs.getInt("customer_id"));
				row.add(rs.getString("field_id"));
				row.add(rs.getString("customer_name")); 
				row.add(rs.getDate("booking_date")); 
				row.add(rs.getTime("start_time"));
				row.add(rs.getTime("end_time")); 
				row.add(rs.getString("status"));
				row.add(rs.getDouble("total_amount")); 
				tableModel.addRow(row);
			}
		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error loading waiting list:" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		
		JScrollPane scrollPane = new JScrollPane(waitlistTable);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		
		JButton addCustomerButton = new JButton("Add Customer");
		addCustomerButton.addActionListener(e -> showAddCustomerDialog(tableModel));
		JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		actionPanel.add(addCustomerButton);

		centerPanel.add(actionPanel, BorderLayout.SOUTH);

		centerPanel.revalidate();
		centerPanel.repaint();
	}

	
	private void showAddCustomerDialog(DefaultTableModel tableModel) {
	    JDialog addCustomerDialog = new JDialog(this, "Add Customers", true);
	    addCustomerDialog.setSize(400, 350);
	    addCustomerDialog.setLayout(new BorderLayout());

	    JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));

	   
	    JTextField customerNameField = new JTextField(); 
	    JComboBox<String> fieldComboBox = new JComboBox<>(new String[]{"Sân 1", "Sân 2", "Sân 3", "Sân 4", "Sân 5"});

	   
	    JDateChooser bookingDateChooser = new JDateChooser(); 
	    bookingDateChooser.setDateFormatString("yyyy-MM-dd");

	    JTextField startTimeField = new JTextField("HH:mm:ss"); 
	    JTextField endTimeField = new JTextField("HH:mm:ss"); 
	    JTextField contactNumberField = new JTextField(); 

	    
	    contentPanel.add(new JLabel("Tên Khách Hàng:"));
	    contentPanel.add(customerNameField);
	    contentPanel.add(new JLabel("Sân Bóng:"));
	    contentPanel.add(fieldComboBox);
	    contentPanel.add(new JLabel("Ngày Đặt:"));
	    contentPanel.add(bookingDateChooser);
	    contentPanel.add(new JLabel("Giờ Bắt Đầu:"));
	    contentPanel.add(startTimeField);
	    contentPanel.add(new JLabel("Giờ Kết Thúc:"));
	    contentPanel.add(endTimeField);
	    contentPanel.add(new JLabel("Số Điện Thoại:"));
	    contentPanel.add(contactNumberField);

	    
	    HashMap<String, Integer> fieldMap = new HashMap<>();
	    fieldMap.put("Sân 1", 1);
	    fieldMap.put("Sân 2", 2);
	    fieldMap.put("Sân 3", 3);
	    fieldMap.put("Sân 4", 4);
	    fieldMap.put("Sân 5", 5);

	    JButton addButton = new JButton("Add");
	    addButton.addActionListener(e -> {
	        try {
	            
	            String inputName = customerNameField.getText().trim();
	            String fullName = formatCustomerName(inputName);
	            customerNameField.setText(fullName); 

	            String selectedField = (String) fieldComboBox.getSelectedItem(); 
	            int fieldId = fieldMap.get(selectedField); 
	            java.util.Date utilDate = bookingDateChooser.getDate(); 
	            java.sql.Date bookingDate = new java.sql.Date(utilDate.getTime()); 
	            String startTime = startTimeField.getText().trim(); 
	            String endTime = endTimeField.getText().trim(); 
	            String contactNumber = contactNumberField.getText();

	            String phoneNumberPattern = "^(|0\\d{9})$";

	            if (!contactNumber.matches(phoneNumberPattern)) {
	                JOptionPane.showMessageDialog(addCustomerDialog, "Invalid phone number",
	                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            double totalAmount = calculateTotalAmount(fieldId, startTime, endTime);
	            
	            if (fullName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || utilDate == null) {
	                JOptionPane.showMessageDialog(addCustomerDialog, "Please fill in all information!",
	                        "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            
	            try {
	                LocalTime.parse(startTime);
	                LocalTime.parse(endTime);
	            } catch (DateTimeParseException ex) {
	                JOptionPane.showMessageDialog(addCustomerDialog, "Invalid time format! Use HH:mm:ss.",
	                        "Format error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            
	            try (Connection conn = db.getConnection();
	                 PreparedStatement checkStmt = conn.prepareStatement(
	                         "SELECT * FROM bookings WHERE field_id = ? AND booking_date = ? " +
	                                 "AND status = 'pending' AND " +
	                                 "((start_time <= ? AND end_time > ?) " +
	                                 "OR (start_time < ? AND end_time >= ?) " +
	                                 "OR (start_time >= ? AND end_time <= ?))")) {

	                checkStmt.setInt(1, fieldId);
	                checkStmt.setDate(2, bookingDate);
	                checkStmt.setString(3, startTime);
	                checkStmt.setString(4, startTime);
	                checkStmt.setString(5, endTime);
	                checkStmt.setString(6, endTime);
	                checkStmt.setString(7, startTime);
	                checkStmt.setString(8, endTime);

	                try (ResultSet rs = checkStmt.executeQuery()) {
	                    if (rs.next()) {
	                        JOptionPane.showMessageDialog(addCustomerDialog,
	                                "Another customer booked this course during this time. Please choose another time!",
	                                "Error", JOptionPane.ERROR_MESSAGE);
	                        return;
	                    }
	                }
	            }

	            
	            try (Connection conn = db.getConnection();
	                 PreparedStatement pstmt = conn.prepareStatement(
	                         "INSERT INTO bookings (customer_name, field_id, booking_date, start_time, end_time, status, total_amount, contact_number) "
	                                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
	                         Statement.RETURN_GENERATED_KEYS)) {
	                pstmt.setString(1, fullName); 
	                pstmt.setInt(2, fieldId); 
	                pstmt.setDate(3, bookingDate); 
	                pstmt.setString(4, startTime); 
	                pstmt.setString(5, endTime); 
	                pstmt.setString(6, "pending"); 
	                pstmt.setDouble(7, totalAmount); 
	                pstmt.setString(8, contactNumber); 

	                int rowsInserted = pstmt.executeUpdate();
	                if (rowsInserted > 0) {
	                    ResultSet rs = pstmt.getGeneratedKeys(); 
	                    int generatedId = -1;
	                    if (rs.next()) {
	                        generatedId = rs.getInt(1); 
	                    }
	                    rs.close();

	                    if (generatedId != -1) {
	                        JOptionPane.showMessageDialog(addCustomerDialog, "Successfully added customers!",
	                                "Thành công", JOptionPane.INFORMATION_MESSAGE);

	                       
	                        Vector<Object> newRow = new Vector<>();
	                        newRow.add(generatedId); 
	                        newRow.add(fieldId);
	                        newRow.add(fullName);
	                        newRow.add(bookingDate);
	                        newRow.add(startTime);
	                        newRow.add(endTime);
	                        newRow.add("pending");
	                        newRow.add(totalAmount); 
	                        newRow.add(contactNumber);

	                        tableModel.addRow(newRow);

	                        
	                        addCustomerDialog.dispose();
	                    } else {
	                        JOptionPane.showMessageDialog(addCustomerDialog, "Successfully added customers!", "Lỗi",
	                                JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            } catch (SQLException | ClassNotFoundException ex) {
	                JOptionPane.showMessageDialog(addCustomerDialog, "Error when adding customer:" + ex.getMessage(),
	                        "Lỗi", JOptionPane.ERROR_MESSAGE);
	                ex.printStackTrace();
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(addCustomerDialog, "An error occurred:" + ex.getMessage(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
	            ex.printStackTrace();
	        }
	    });

	    
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(e -> addCustomerDialog.dispose());

	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(addButton);
	    buttonPanel.add(cancelButton);

	    addCustomerDialog.add(contentPanel, BorderLayout.CENTER);
	    addCustomerDialog.add(buttonPanel, BorderLayout.SOUTH);

	    addCustomerDialog.setLocationRelativeTo(this);
	    addCustomerDialog.setVisible(true);
	}

	
	private String formatCustomerName(String inputName) {
	    if (inputName == null || inputName.isEmpty()) {
	        return "";
	    }

	    String[] words = inputName.trim().toLowerCase().split("\\s+"); 
	    StringBuilder formattedName = new StringBuilder();

	    for (String word : words) {
	        if (!word.isEmpty()) {
	            formattedName.append(Character.toUpperCase(word.charAt(0))) 
	                    .append(word.substring(1)) 
	                    .append(" ");
	        }
	    }

	    return formattedName.toString().trim(); 
	}

	private double calculateTotalAmount(int fieldId, String startTime, String endTime) {
		try {
			LocalTime start = LocalTime.parse(startTime);
			LocalTime end = LocalTime.parse(endTime);

			
			long durationInMinutes = java.time.Duration.between(start, end).toMinutes();
			if (durationInMinutes <= 0) {
				throw new IllegalArgumentException("The end time must be greater than the start time");
			}

			
			double hours = durationInMinutes / 60.0;

			
			double pricePerHour = 0.0;
			switch (fieldId) {
			case 1: 
			case 2:
				pricePerHour = 70000;
				break;
			case 3: 
			case 4: 
				pricePerHour = 100000;
				break;
			case 5: 
				pricePerHour = 200000;
				break;
			default:
				throw new IllegalArgumentException("There is no yard with this ID.");
			}

			
			double totalAmount = pricePerHour * hours;
			return totalAmount;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
					"Invalid time format. Please re-enter in HH:mm:ss format.");
		} catch (Exception e) {
			throw new IllegalArgumentException("Error when calculating total amount:" + e.getMessage());
		}
	}
	
	
	
	public void showReport() {
	    
	    JPanel reportPanel = new JPanel();
	    reportPanel.setLayout(new BorderLayout());

	    
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    
	    try (Connection conn = db.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(
	            "SELECT MONTH(booking_date) AS month, SUM(total_amount) AS revenue " +
	            "FROM bookings " +
	            "WHERE status = 'completed' " +
	            "GROUP BY MONTH(booking_date) " +
	            "ORDER BY month")) {
	        
	        
	        while (rs.next()) {
	            int month = rs.getInt("month");
	            double revenue = rs.getDouble("revenue");
	            dataset.addValue(revenue, "Revenue", getMonthName(month));
	        }
	        
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Error when querying data:" + e.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }  catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	    
	    JFreeChart barChart = ChartFactory.createBarChart(
	            "Monthly Revenue",  
	            "Month",                 
	            "Revenue (VND)",       
	            dataset,                 
	            org.jfree.chart.plot.PlotOrientation.VERTICAL,
	            true,                    
	            true,                   
	            false                   
	    );

	   
	    ChartPanel chartPanel = new ChartPanel(barChart);
	    reportPanel.add(chartPanel, BorderLayout.CENTER);

	    
	    centerPanel.removeAll();
	    centerPanel.add(reportPanel);
	    centerPanel.revalidate();
	    centerPanel.repaint();
	}

	
	private String getMonthName(int month) {
	    switch (month) {
	        case 1: return "January";
	        case 2: return "February";
	        case 3: return "March";
	        case 4: return "April";
	        case 5: return "May";
	        case 6: return "June";
	        case 7: return "July";
	        case 8: return "August";
	        case 9: return "September";
	        case 10: return "October";
	        case 11: return "November";
	        case 12: return "December";
	        default: return "";
	    }
	}
	private void showAddCustomerDialog2(int fieldIndex) {
		
	    JDialog availabilityDialog = new JDialog(this, "Check for Empty Yards", true);
	    availabilityDialog.setSize(400, 150);
	    availabilityDialog.setLayout(new BorderLayout());

	    JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));

	    
	    JComboBox<String> fieldComboBox = new JComboBox<>(new String[] { "Field 1", "Filed 2", "Field 3", "Field 4", "Field 5" });
	    contentPanel.add(new JLabel("Choose field:"));
	    contentPanel.add(fieldComboBox);
	    
	    fieldComboBox.setSelectedItem(fieldIndex);
	    
	    JDateChooser bookingDateChooser = new JDateChooser();
	    bookingDateChooser.setDateFormatString("yyyy-MM-dd");
	    contentPanel.add(new JLabel("Select Booking Date:"));
	    contentPanel.add(bookingDateChooser);

	    
	    JButton checkButton = new JButton("Check");
	    checkButton.addActionListener(e -> {
	        java.util.Date bookingDate = bookingDateChooser.getDate();
	        if (bookingDate == null) {
	            JOptionPane.showMessageDialog(availabilityDialog, "Please select a date!", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        String selectedField = (String) fieldComboBox.getSelectedItem();
	        boolean isFieldAvailable = isFieldAvailable(selectedField, bookingDate);

	       
	        if (isFieldAvailable) {
	            JOptionPane.showMessageDialog(availabilityDialog, "The yard is available on " + new SimpleDateFormat("yyyy-MM-dd").format(bookingDate), "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(availabilityDialog, "The field was fully booked on " + new SimpleDateFormat("yyyy-MM-dd").format(bookingDate), "Thông Báo", JOptionPane.WARNING_MESSAGE);
	        }
	    });

	    
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(e -> availabilityDialog.dispose());

	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(checkButton);
	    buttonPanel.add(cancelButton);

	   
	    availabilityDialog.add(contentPanel, BorderLayout.CENTER);
	    availabilityDialog.add(buttonPanel, BorderLayout.SOUTH);

	    
	    availabilityDialog.setLocationRelativeTo(this);
	    availabilityDialog.setVisible(true);
	}

	
	private boolean isFieldAvailable(String field_id, java.util.Date bookingDate) {
	   
	    String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(bookingDate);

	    
	    try (Connection conn = db.getConnection();
	            PreparedStatement stmt = conn.prepareStatement(
	                "SELECT SUM(TIMESTAMPDIFF(SECOND, start_time, end_time)) / 3600.0 AS total_hours FROM bookings " +
	                "WHERE field_id = ? AND booking_date = ?")) {

	           stmt.setString(1, field_id); 
	           stmt.setString(2, formattedDate); 

	           ResultSet rs = stmt.executeQuery();
	           if (rs.next()) {
	               double totalHoursBooked = rs.getDouble("total_hours");

	             
	               return totalHoursBooked < 24;
	           }
	       } catch (SQLException e) {
	           JOptionPane.showMessageDialog(null, "Error when querying data:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	       } catch (ClassNotFoundException e) {
		        JOptionPane.showMessageDialog(null, "MySQL Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
		        e.printStackTrace();
		    }
	    return false;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		soccerField sc = new soccerField(null);
		sc.setVisible(true);
	}
}
