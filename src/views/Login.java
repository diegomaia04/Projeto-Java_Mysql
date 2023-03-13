package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/Login.png")));
		setTitle("Login ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//a linha baixo centraliza o Jframe/ Jdialog
		setLocationRelativeTo(null);

		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(167, 215, 89, 23);
		contentPane.add(btnAcessar);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(378, 11, 48, 48);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(48, 42, 46, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(104, 39, 202, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSenha.setBounds(48, 119, 46, 14);
		contentPane.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(104, 116, 202, 20);
		contentPane.add(txtSenha);

		// botão Enter
		getRootPane().setDefaultButton(btnAcessar);

		RestrictedTextField validar = new RestrictedTextField(txtLogin);
		validar.setLimit(15);

	} // Fim do Construtor

	DAO dao = new DAO();

	private void status() {

		try {
			Connection con = dao.conectar();
			if (con == null) {

				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));

			} else {

				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dbon.png")));
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}// FIM DO STATUS

	private void logar() {
		// validação da senha (captura segura)
		String capturaSenha = new String(txtSenha.getPassword());
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o seu Login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a sua Senha");
			txtSenha.requestFocus();
		} else {
			// logica principal (pesquisar login e senha correspondente)
			String read = "select * from usuarios where login= ? and senha= md5(?)";

			try {
				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(read);

				// setar os argumentos(?,? login e senha)
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);

				// Executar a querry e executar o login se existir login e senha correspondente
				// no banco
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					Main Main = new Main();

					// a linha abaixo captura o perfil do usuario
					String perfil = rs.getString(15);

					// coportamento de Login baseado na função do usuario
					if (perfil.equals("admin")) {

						Main.setVisible(true);
						// alterar a label da tela principal (inserir nome do usuário no rodapé
						// rs.getString é pra puxar as informações do bando de dados
						// apoio a logica
						// System.out.println(rs.getString(2));
						Main.lblPerfil.setText(rs.getString(13));

						// habilitar os botões que so adm pode mexer
						Main.btnRelatorios.setEnabled(true);
						Main.btnUsuarios.setEnabled(true);

						// alterar a cor do rodapé
						Main.panel.setBackground(Color.RED);

						// fechar o Jframe
						this.dispose();

					} else {

						Main.setVisible(true);
						Main.lblPerfil.setText(rs.getString(15));

						// fechar o Jframe
						this.dispose();

					}

				} else {
					JOptionPane.showMessageDialog(null, "Login ou Senha Incorreto");
					limpar();
				}

				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	} // FIM DO METODO LOGAR

	void limpar() {
		txtSenha.setText(null);
	}

}// FIM
