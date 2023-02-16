package views;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTable;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtLogin;
	private JTextField txtNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		getContentPane().setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/Users2.png")));
		setResizable(false);
		setTitle("Usu\u00E1rios ");
		setBounds(100, 100, 728, 516);
		getContentPane().setLayout(null);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(310, 279, 86, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nome*:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(24, 32, 64, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblLogin = new JLabel("Login*:");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLogin.setBounds(24, 278, 64, 20);
		getContentPane().add(lblLogin);

		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.setBounds(81, 279, 191, 20);
		getContentPane().add(txtLogin);

		JLabel lblSenha = new JLabel("Senha*:");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(24, 325, 64, 20);
		getContentPane().add(lblSenha);

		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Arial", Font.PLAIN, 15));
		lblId.setBounds(283, 281, 64, 14);
		getContentPane().add(lblId);

		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(98, 30, 191, 20);
		getContentPane().add(txtNome);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarNome();
			}
		});
		btnBuscar.setToolTipText("Procurar");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(null);
		btnBuscar.setBackground(Color.BLACK);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Search.png")));
		btnBuscar.setBounds(299, 11, 48, 48);
		getContentPane().add(btnBuscar);

		btnAdd = new JButton("");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUsuario();
			}
		});
		btnAdd.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Add.png")));
		btnAdd.setToolTipText("Adicionar");
		btnAdd.setBorder(null);
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setBounds(81, 377, 64, 64);
		getContentPane().add(btnAdd);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verificar se o checkbox
				// para verificar se não está selecionado use NOT (!)
				if (chckSenha.isSelected()) {
					updateUsuarioSenha(); // No primeiro caso seguindo a logica caso o checkbox esteja marcado ele vai
											// qurer trocar a senha então o metodo tem que vir primeiro, caso não queria
											// ele só faz a taualização normal do úsuario.

				} else {
					updateUsuario();

				}
			}
		});
		btnUpdate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Update.png")));
		btnUpdate.setToolTipText("Atualizar Cadastro");
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(283, 377, 64, 64);
		getContentPane().add(btnUpdate);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Delete.png")));
		btnExcluir.setToolTipText("Atualizar Cadastro");
		btnExcluir.setBorder(null);
		btnExcluir.setBackground(Color.BLACK);
		btnExcluir.setBounds(474, 377, 64, 64);
		getContentPane().add(btnExcluir);

		// Uso da Biblioteca
		RestrictedTextField validar = new RestrictedTextField(txtNome);
		validar.setLimit(50);
		validar.setOnlyText(true);
		validar.setAcceptSpace(true);

		// Uso da tecla <Enter> junto a um botão (so da pra fazer com um botão)
		getRootPane().setDefaultButton(btnBuscar);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(81, 326, 231, 20);
		getContentPane().add(txtPassword);

		lblPerfil = new JLabel("Perfil*:");
		lblPerfil.setForeground(Color.WHITE);
		lblPerfil.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPerfil.setBounds(406, 278, 64, 20);
		getContentPane().add(lblPerfil);

		cboPerfil = new JComboBox<Object>();
		cboPerfil.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "admin", "usuário" }));
		cboPerfil.setBounds(454, 278, 90, 22);
		getContentPane().add(cboPerfil);

		chckSenha = new JCheckBox("Alterar Senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// fazer o check na cixar Jchechbx, usando botão direito
				txtPassword.setEditable(true);
				txtPassword.setText(null);
				txtPassword.requestFocus();
				txtPassword.setBackground(Color.white);
			}
		});
		chckSenha.setVisible(true);
		chckSenha.setBounds(324, 325, 113, 23);
		getContentPane().add(chckSenha);

		lblCpf = new JLabel("CPF*:");
		lblCpf.setForeground(Color.WHITE);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCpf.setBounds(24, 138, 64, 14);
		getContentPane().add(lblCpf);

		txtCPF = new JTextField();
		txtCPF.setColumns(10);
		txtCPF.setBounds(70, 136, 191, 20);
		getContentPane().add(txtCPF);

		lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(24, 99, 64, 14);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(81, 97, 191, 20);
		getContentPane().add(txtEmail);

		lblContato = new JLabel("Contato*:");
		lblContato.setForeground(Color.WHITE);
		lblContato.setFont(new Font("Arial", Font.PLAIN, 15));
		lblContato.setBounds(289, 99, 64, 14);
		getContentPane().add(lblContato);

		txtContato = new JTextField();
		txtContato.setColumns(10);
		txtContato.setBounds(357, 97, 113, 20);
		getContentPane().add(txtContato);

		lblEndereo = new JLabel("Endereço*:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEndereo.setBounds(24, 178, 78, 14);
		getContentPane().add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(104, 176, 191, 20);
		getContentPane().add(txtEndereco);

		lblBairro = new JLabel("Bairro*:");
		lblBairro.setForeground(Color.WHITE);
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 15));
		lblBairro.setBounds(305, 178, 78, 14);
		getContentPane().add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(359, 176, 191, 20);
		getContentPane().add(txtBairro);

		lblCep = new JLabel("CEP:");
		lblCep.setForeground(Color.WHITE);
		lblCep.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCep.setBounds(275, 138, 78, 14);
		getContentPane().add(lblCep);

		txtCEP = new JTextField();
		txtCEP.setColumns(10);
		txtCEP.setBounds(319, 136, 191, 20);
		getContentPane().add(txtCEP);

		JButton btnCEP = new JButton("");
		btnCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCEP.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Search.png")));
		btnCEP.setToolTipText("Procurar");
		btnCEP.setBorder(null);
		btnCEP.setBackground(Color.BLACK);
		btnCEP.setBounds(526, 120, 48, 48);
		getContentPane().add(btnCEP);

		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUF.setBounds(611, 135, 48, 22);
		getContentPane().add(cboUF);

		JLabel lblCidade = new JLabel("Cidade*:");
		lblCidade.setForeground(Color.WHITE);
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCidade.setBounds(24, 216, 78, 14);
		getContentPane().add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(81, 214, 191, 20);
		getContentPane().add(txtCidade);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setForeground(Color.WHITE);
		lblComplemento.setFont(new Font("Arial", Font.PLAIN, 15));
		lblComplemento.setBounds(296, 216, 125, 14);
		getContentPane().add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(402, 214, 191, 20);
		getContentPane().add(txtComplemento);

		JLabel lblNumero = new JLabel("Numero*:");
		lblNumero.setForeground(Color.WHITE);
		lblNumero.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumero.setBounds(560, 178, 78, 14);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(627, 176, 57, 20);
		getContentPane().add(txtNumero);

		JLabel lblUf = new JLabel("UF*:");
		lblUf.setForeground(Color.WHITE);
		lblUf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblUf.setBounds(573, 138, 35, 14);
		getContentPane().add(lblUf);

		RestrictedTextField validar2 = new RestrictedTextField(txtLogin);
		validar2.setLimit(15);

		RestrictedTextField validar3 = new RestrictedTextField(txtContato);
		validar3.setLimit(30);
		validar3.setOnlyNums(true);

		RestrictedTextField validar4 = new RestrictedTextField(txtCPF);
		validar4.setLimit(16);
		validar4.setOnlyNums(true);

		RestrictedTextField validar5 = new RestrictedTextField(txtCEP);
		validar5.setLimit(10);
		validar5.setOnlyNums(true);

		RestrictedTextField validar6 = new RestrictedTextField(txtEndereco);
		validar6.setLimit(250);

		RestrictedTextField validar7 = new RestrictedTextField(txtEmail);
		validar7.setLimit(250);

		RestrictedTextField validar8 = new RestrictedTextField(txtNumero);
		validar8.setOnlyNums(true);
		validar8.setLimit(6);

		RestrictedTextField validar9 = new RestrictedTextField(txtComplemento);
		validar9.setLimit(30);
		validar9.setAcceptSpace(true);

		RestrictedTextField validar10 = new RestrictedTextField(txtBairro);
		
		JLabel lblNewLabel_1 = new JLabel("Campos com  *  são obrigatórios");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(24, 57, 212, 14);
		getContentPane().add(lblNewLabel_1);
		validar10.setLimit(50);
		validar10.setAcceptSpace(true);
		validar10.setOnlyText(true);

	} // fim do constutor

	DAO dao = new DAO();

	private JButton btnUpdate;
	private JButton btnExcluir;
	private JButton btnAdd;
	private JButton btnBuscar;
	private JPasswordField txtPassword;
	private JComboBox<Object> cboPerfil;
	private JCheckBox chckSenha;
	private JLabel lblCpf;
	private JTextField txtCPF;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblContato;
	private JTextField txtContato;
	private JLabel lblEndereo;
	private JTextField txtEndereco;
	private JLabel lblBairro;
	private JTextField txtBairro;
	private JLabel lblCep;
	private JTextField txtCEP;
	private JTextField txtCidade;
	private JTextField txtComplemento;
	private JTextField txtNumero;
	private JComboBox<?> cboUF;
	private JLabel lblPerfil;

	private void status() {

		try {
			Connection con = dao.conectar();
			if (con == null) {

			} else {

			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}// FIM DO STATUS

	private void pesquisarNome() {

		// VALIDAÇÃO
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Nome");
			txtNome.requestFocus();
		} else {

			String read = "select * from usuarios where nome = ?";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(read);

				// pst.setString(2, txtNome.getText());
				pst.setString(1, txtNome.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtId.setText(rs.getString(1));
					txtCPF.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(4));
					txtNumero.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(6));
					txtBairro.setText(rs.getString(7));
					txtCidade.setText(rs.getString(8));
					txtCEP.setText(rs.getString(9));
					cboUF.setSelectedItem(rs.getString(10));
					txtContato.setText(rs.getString(11));
					txtEmail.setText(rs.getString(12));
					txtLogin.setText(rs.getString(13));
					txtPassword.setText(rs.getString(14));
					cboPerfil.setSelectedItem(rs.getString(15));

					// exibir a caixa Checkbox
					chckSenha.setVisible(true);

					// desativar table
					txtPassword.setEditable(false);

					btnUpdate.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnAdd.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Nome inexistente");

					txtLogin.setText(null);
					txtPassword.requestFocus();

					btnAdd.setEnabled(true);
					btnBuscar.setEnabled(true);

				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);

			}
		}
	}

	// FIM DA BUSCA DO USUARIO

	void addUsuario() {

		// validação de CAMPOS OBRIGATÓRIOS
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Login");
			txtLogin.requestFocus();
		}

		else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Endereco");
			txtEndereco.requestFocus();
		}

		else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Número");
			txtNumero.requestFocus();

		}

		else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Bairro");
			txtBairro.requestFocus();

		}

		else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade");
			txtCidade.requestFocus();
		}

		else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha A  UF");
			cboUF.requestFocus();
		}

		else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();
		}

		else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Contato");
			txtContato.requestFocus();

		}

		else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O login");
			txtLogin.requestFocus();

		}

		else if (txtPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha");
			txtPassword.requestFocus();

		}

		else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil");
			cboPerfil.requestFocus();

		}

		else {

			// System.out.println("Teste Confirmar");
			String create = "insert into usuarios (nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,login,senha,perfil)\r\n"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,md5(?),?) ";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(create);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCPF.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCEP.getText());
				pst.setString(9, cboUF.getSelectedItem().toString());
				pst.setString(10, txtContato.getText());
				pst.setString(11, txtEmail.getText());
				pst.setString(12, txtLogin.getText());
				String capturaSenha = new String(txtPassword.getPassword());
				pst.setString(13, capturaSenha);
				pst.setString(14, cboPerfil.getSelectedItem().toString());

				int confirma1 = pst.executeUpdate(); // pra adicionar ou mudar é sempre o UPDATE
				// System.out.println("confrima");
				if (confirma1 == 1) {
					JOptionPane.showMessageDialog(null, "Usuário Adicionado");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ATENÇÃO  USUÁRIO NÃO ADICIONADO");

				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse login ou CPF já existem!!");
				txtLogin.setText(null);
				txtCPF.setText(null);
				txtLogin.requestFocus();
			}

			catch (Exception e3) {
				System.out.println(e3);

			}
		}
	}// FIM DO ADICIONAR

	private void updateUsuario() {
		// System.out.println("teste ");

		// validação de CAMPOS OBRIGATÓRIOS
		// validação de CAMPOS OBRIGATÓRIOS
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Login");
			txtLogin.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O CPF");
			txtCPF.requestFocus();
		}

		else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Endereço");
			txtEndereco.requestFocus();

		}

		else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Número");
			txtNumero.requestFocus();

		}

		else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Bairro");
			txtBairro.requestFocus();

		}

		else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade");
			txtCidade.requestFocus();
		}

		else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha A  UF");
			cboUF.requestFocus();

		} else if (txtContato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Contato");
			txtContato.requestFocus();

		}

		else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O login");
			txtLogin.requestFocus();

		}

		else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil");
			cboPerfil.requestFocus();

		}

		else {

			// LOGICA PRINCIPAL
			String update = "update usuarios set cpf = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, cep = ?, uf = ?, contato = ?, email = ?, login = ?, senha = ?, perfil = ? where nome = ? ";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(update);

				pst.setString(1, txtCPF.getText());
				pst.setString(2, txtEndereco.getText());
				pst.setString(3, txtNumero.getText());
				pst.setString(4, txtComplemento.getText());
				pst.setString(5, txtBairro.getText());
				pst.setString(6, txtCidade.getText());
				pst.setString(7, txtCEP.getText());
				pst.setString(8, cboUF.getSelectedItem().toString());
				pst.setString(9, txtContato.getText());
				pst.setString(10, txtEmail.getText());
				pst.setString(11, txtLogin.getText());
				String capturaSenha = new String(txtPassword.getPassword());
				pst.setString(12, capturaSenha);
				pst.setString(13, cboPerfil.getSelectedItem().toString());
				pst.setString(14, txtNome.getText());

				int confirma = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Usuário Atualizado");
					limpar();
				}

				con.close();
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse login ou CPF já existem!!");
			}

			catch (Exception e1) {
				System.out.println(e1);
			}
		}

	}// FIM DO UPDATE

	private void updateUsuarioSenha() {
		// System.out.println("teste ");

		// validação de CAMPOS OBRIGATÓRIOS
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Login");
			txtLogin.requestFocus();
		}

		else if (txtPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha");
			txtPassword.requestFocus();
		} else {

			// LOGICA PRINCIPAL
			String update = "update usuarios set nome = ?, login = ?, senha = md5(?), perfil = ? where idfor= ?";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());

				// capturarsenha
				String capturarSenha = new String(txtPassword.getPassword());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtId.getText());

				int confirma = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Usuário Atualizado");
					limpar();
				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}
		}
	}// FIM DO UPDATESENHA

	// METODO PARA EXCLUIR CONTATO
	private void excluirUsuario() {
		// System.out.println("Teste");

		int confirma = JOptionPane.showConfirmDialog(null, "Deseja Excluir Esse Usuário?", "Exluir Contato!!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from usuarios where idfor = ?";

			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());

				int confirmaExcluir = pst.executeUpdate();

				if (confirmaExcluir == 1) {
					JOptionPane.showMessageDialog(null, "Usuário Excluido");
					limpar();

				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}

		}

	}// FIM DO EXCLUIR

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = txtCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}

			}

			txtEndereco.setText(tipoLogradouro + " " + logradouro);

		} catch (

		Exception e) {
			System.out.println(e);
		}

	}

	// Limpar Campos e resetar os botões
	private void limpar() {
		txtLogin.setText(null);
		txtPassword.setText(null);
		txtNome.setText(null);
		txtContato.setText(null);
		txtCPF.setText(null);
		txtEmail.setText(null);
		txtComplemento.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtCidade.setText(null);
		txtBairro.setText(null);
		txtCEP.setText(null);
		txtId.setText(null);
		txtNome.requestFocus();
		cboPerfil.setSelectedItem("");
		cboUF.setSelectedItem("");
		btnAdd.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnExcluir.setEnabled(false);
		txtPassword.setBackground(null);
		chckSenha.setSelected(false); // desmarcar a caixa check
		chckSenha.setVisible(false);
		txtPassword.setEditable(true);

	}
}