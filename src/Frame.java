import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame{
	
	private JPanel contentPane;
	private JTextField textField_Key;
	private JTextArea textView_Input, textView_Output;
	private JLabel lable_FileSelected;
	
	private DES_Encryption des_Encryption;
	private DES_Decryption des_Decryption;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("DES Encryption App");
		setBounds(100, 100, 800, 700);
		setResizable(false);
		
		contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        /* Key */
        JLabel lblKey = new JLabel("Key:");
        lblKey.setBounds(10, 15, 46, 14);
        contentPane.add(lblKey);

        textField_Key = new JTextField();
        textField_Key.setBounds(65, 10, 150, 25);
        contentPane.add(textField_Key);
        textField_Key.setColumns(10);
        
        /* Choose File */
        JButton btnSelectFile = new JButton("Select File");
        btnSelectFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    lable_FileSelected.setText("File Selected: " + selectedFile.getAbsolutePath());
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");;
                        }
                        br.close();
                        textView_Input.setText(sb.toString().trim());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btnSelectFile.setBounds(10, 50, 100, 25);
        contentPane.add(btnSelectFile);

        lable_FileSelected = new JLabel("File Selected: ");
        lable_FileSelected.setBounds(125, 55, 450, 14);
        contentPane.add(lable_FileSelected);
        
        /* Data in file */
        JLabel lblTextView1 = new JLabel("Input_File'sData:");
        lblTextView1.setBounds(10, 85, 100, 14);
        contentPane.add(lblTextView1);
        
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(10, 105, 725, 200);
        contentPane.add(scrollPane1);

        textView_Input = new JTextArea();
        scrollPane1.setViewportView(textView_Input);
        
        /* Nút mã hóa*/
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textView_Input.getText();
                //System.out.print(text);
                String key = textField_Key.getText();
                
                // Kiểm tra độ dài của khóa
                if (key.length() > 8) {
                    JOptionPane.showMessageDialog(null, "Độ dài của khóa không được vượt quá 8 kí tự");
                    return; // Dừng xử lý nếu khóa không hợp lệ
                }
                
                long startTime = System.currentTimeMillis();
                // Your encryption logic here
                des_Encryption = new DES_Encryption(text, key);
                String ciphertext = des_Encryption.Encrypt();
                // Set encrypted text to textView2
                textView_Output.setText(ciphertext.trim());
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                JOptionPane.showMessageDialog(null, "Thời gian mã hóa: "+duration +" milliseconds");
            }
        });
        btnEncrypt.setBounds(100, 320, 100, 25);
        contentPane.add(btnEncrypt);
        
        /* Nút giải mã*/        
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String text = textView_Input.getText();
                String key = textField_Key.getText();
                
                // Kiểm tra độ dài của khóa
                if (key.length() > 8) {
                    JOptionPane.showMessageDialog(null, "Độ dài của khóa không được vượt quá 8 kí tự");
                    return; // Dừng xử lý nếu khóa không hợp lệ
                }
                
                long startTime = System.currentTimeMillis();
                // Your encryption logic here
                des_Decryption = new DES_Decryption(text, key);
                String decryptedtext = des_Decryption.Decrypt();
                // Set encrypted text to textView2
                textView_Output.setText(decryptedtext.trim());
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                JOptionPane.showMessageDialog(null, "Thời gian giải mã: "+duration +" milliseconds");
            }
        });
        btnDecrypt.setBounds(550, 320, 100, 25);
        contentPane.add(btnDecrypt);
        
      //TextView Output
        JLabel lblTextView2 = new JLabel("Output:");
        lblTextView2.setBounds(10, 350, 100, 14);
        contentPane.add(lblTextView2);
        
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10, 370, 725, 200);
        contentPane.add(scrollPane2);

        textView_Output = new JTextArea();
        scrollPane2.setViewportView(textView_Output);
        
        
        /* Nút Clear và Save */
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	textField_Key.setText("");
            	lable_FileSelected.setText("File Selected: ");
                textView_Input.setText("");
                textView_Output.setText("");
            }
        });
        btnClear.setBounds(200, 575, 100, 25);
        contentPane.add(btnClear);

        JButton btnSaveFile = new JButton("Save");
        btnSaveFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String filePath = lable_FileSelected.getText().replace("File Selected: ", "");
		        if (!filePath.isEmpty()) {
		            File selectedFile = new File(filePath);
		            try {
		                FileWriter writer = new FileWriter(selectedFile);
		                //System.out.print(textView_Output.getText());
		                writer.write(textView_Output.getText().trim());
		                writer.close();
		                JOptionPane.showMessageDialog(null, "Lưu file thành công!");
		            } catch (IOException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Lỗi lưu file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Không tìm thấy file!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
        btnSaveFile.setBounds(450, 575, 100, 25);
        contentPane.add(btnSaveFile);
        
        

	}

}
