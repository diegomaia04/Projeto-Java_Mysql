package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	private JButton btnOS;
	JButton btnRelatorios;
	JButton btnUsuarios;
	JLabel lblPerfil;
	JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/Menu.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				lblData.setText(formatador.format(data));
			}
		});
		setTitle("Menu");
		setBounds(100, 100, 471, 340);
		contentPane = new JPanel();
		contentPane.setToolTipText("Ordem de Serviço");
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnOS = new JButton("");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Os os = new Os();
				os.setVisible(true);
			}
		});
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setToolTipText("Ordem de Serviço");
		btnOS.setBackground(Color.BLACK);
		btnOS.setBorderPainted(false);
		btnOS.setIcon(new ImageIcon(Main.class.getResource("/img/os icon.png")));
		btnOS.setBounds(244, 34, 64, 64);
		contentPane.add(btnOS);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setBorderPainted(false);
		btnClientes.setBackground(Color.BLACK);
		btnClientes.setToolTipText("Clientes");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Main.class.getResource("/img/Clients.png")));
		btnClientes.setBounds(51, 34, 89, 62);
		contentPane.add(btnClientes);

		btnUsuarios = new JButton("");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setEnabled(false);
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setBackground(Color.BLACK);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setIcon(new ImageIcon(Main.class.getResource("/img/avatar.png")));
		btnUsuarios.setBounds(51, 137, 89, 62);
		contentPane.add(btnUsuarios);

		btnRelatorios = new JButton("");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setEnabled(false);
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setBorderPainted(false);
		btnRelatorios.setBackground(Color.BLACK);
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setIcon(new ImageIcon(Main.class.getResource("/img/Report.png")));
		btnRelatorios.setBounds(238, 137, 89, 72);
		contentPane.add(btnRelatorios);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnNewButton_4.setToolTipText("Sobre");
		btnNewButton_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_4.setBackground(Color.BLACK);
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setIcon(new ImageIcon(Main.class.getResource("/img/About.png")));
		btnNewButton_4.setBounds(386, 2, 64, 64);
		contentPane.add(btnNewButton_4);

		panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setForeground(SystemColor.menu);
		panel.setBounds(0, 232, 455, 69);
		contentPane.add(panel);
		panel.setLayout(null);

		lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setBounds(10, 22, 281, 31);
		panel.add(lblData);

		lblPerfil = new JLabel("");
		lblPerfil.setBounds(350, 24, 94, 26);
		panel.add(lblPerfil);
	}// fim do Construtor

}// FIM
