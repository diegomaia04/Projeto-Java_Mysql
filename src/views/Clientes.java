package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itextpdf.text.Document;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;

public class Clientes extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JTextField txtEmail;
	private JTextField txtFone;
	private JLabel lblEndereo;
	private JTextField txtEndereco;
	private JLabel lblEndereo_1;
	private JTextField txtNumero;
	private JLabel lblEndereo_2;
	private JLabel lblEndereo_3;
	private JLabel lblEndereo_4;
	private JTextField txtCep;
	private JTextField txtCidade;
	private JLabel lblEndereo_5;
	private JLabel lblEndereo_6;
	private JTextField txtComplemento;
	private JComboBox<?> cboUf;
	private JTextField txtBairro;
	private JTextField txtObs;
	private JButton btnBuscarcep;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnExcluir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes frame = new Clientes();
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
	public Clientes() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Clientes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Clients.png")));
		setBounds(100, 100, 673, 502);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(20, 11, 26, 14);
		contentPane.add(lblNewLabel);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(40, 8, 39, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		JLabel lblNome = new JLabel("Nome*:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNome.setBounds(140, 11, 50, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisaAvançada();
			}

			@Override

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				}
			}

		});
		txtNome.setColumns(10);
		txtNome.setBounds(189, 8, 163, 20);
		contentPane.add(txtNome);

		JLabel lblCpf = new JLabel("CPF*: ");
		lblCpf.setForeground(Color.WHITE);
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCpf.setBounds(396, 11, 39, 14);
		contentPane.add(lblCpf);

		txtCpf = new JTextField();
		txtCpf.setColumns(10);
		txtCpf.setBounds(434, 8, 130, 20);
		contentPane.add(txtCpf);

		JLabel lblDfone = new JLabel("Fone*:");
		lblDfone.setForeground(Color.WHITE);
		lblDfone.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDfone.setBounds(253, 171, 39, 14);
		contentPane.add(lblDfone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(20, 171, 39, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(69, 168, 163, 20);
		contentPane.add(txtEmail);

		txtFone = new JTextField();
		txtFone.setColumns(10);
		txtFone.setBounds(302, 168, 103, 20);
		contentPane.add(txtFone);

		lblEndereo = new JLabel("Endereço*:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo.setBounds(20, 281, 62, 14);
		contentPane.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(92, 278, 163, 20);
		contentPane.add(txtEndereco);

		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarnome();
			}
		});
		btnBuscar.setBorder(null);
		btnBuscar.setIcon(new ImageIcon(Clientes.class.getResource("/img/Search.png")));
		btnBuscar.setForeground(Color.BLACK);
		btnBuscar.setBackground(Color.BLACK);
		btnBuscar.setBounds(89, -5, 33, 49);
		contentPane.add(btnBuscar);

		lblEndereo_1 = new JLabel("Número*: ");
		lblEndereo_1.setForeground(Color.WHITE);
		lblEndereo_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_1.setBounds(265, 281, 62, 14);
		contentPane.add(lblEndereo_1);

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBounds(332, 278, 73, 20);
		contentPane.add(txtNumero);

		lblEndereo_2 = new JLabel("Bairro*: ");
		lblEndereo_2.setForeground(Color.WHITE);
		lblEndereo_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_2.setBounds(434, 281, 62, 14);
		contentPane.add(lblEndereo_2);

		lblEndereo_3 = new JLabel("Cidade*: ");
		lblEndereo_3.setForeground(Color.WHITE);
		lblEndereo_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_3.setBounds(290, 340, 62, 14);
		contentPane.add(lblEndereo_3);

		lblEndereo_4 = new JLabel("CEP:");
		lblEndereo_4.setForeground(Color.WHITE);
		lblEndereo_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_4.setBounds(20, 236, 33, 14);
		contentPane.add(lblEndereo_4);

		txtCep = new JTextField();
		txtCep.setColumns(10);
		txtCep.setBounds(69, 233, 103, 20);
		contentPane.add(txtCep);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(349, 337, 103, 20);
		contentPane.add(txtCidade);

		lblEndereo_5 = new JLabel("UF*:");
		lblEndereo_5.setForeground(Color.WHITE);
		lblEndereo_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_5.setBounds(493, 340, 26, 14);
		contentPane.add(lblEndereo_5);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(521, 336, 56, 22);
		contentPane.add(cboUf);

		lblEndereo_6 = new JLabel("Complemento: ");
		lblEndereo_6.setForeground(Color.WHITE);
		lblEndereo_6.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_6.setBounds(10, 340, 92, 14);
		contentPane.add(lblEndereo_6);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(112, 337, 163, 20);
		contentPane.add(txtComplemento);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(493, 278, 103, 20);
		contentPane.add(txtBairro);

		JLabel lblEndereo_6_1 = new JLabel("Obs:");
		lblEndereo_6_1.setForeground(Color.WHITE);
		lblEndereo_6_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEndereo_6_1.setBounds(20, 397, 33, 14);
		contentPane.add(lblEndereo_6_1);

		txtObs = new JTextField();
		txtObs.setBounds(61, 394, 220, 20);
		contentPane.add(txtObs);
		txtObs.setColumns(10);

		btnBuscarcep = new JButton("");
		btnBuscarcep.setIcon(new ImageIcon(Clientes.class.getResource("/img/Search.png")));
		btnBuscarcep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtCep.getText().equals("")) {
					JOptionPane.showInternalMessageDialog(null, " Infome o CEP");
					txtCep.requestFocus();

				} else {

					buscarCep();

				}
			}
		});
		btnBuscarcep.setForeground(Color.BLACK);
		btnBuscarcep.setBorder(null);
		btnBuscarcep.setBackground(Color.BLACK);
		btnBuscarcep.setBounds(182, 213, 39, 54);
		contentPane.add(btnBuscarcep);

		btnAdd = new JButton("");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCliente();
			}
		});
		btnAdd.setIcon(new ImageIcon(Clientes.class.getResource("/img/Add.png")));
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setToolTipText("Adicionar");
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBorder(null);
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setBounds(341, 388, 64, 64);
		contentPane.add(btnAdd);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateClientes();
			}
		});
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(Clientes.class.getResource("/img/Update.png")));
		btnUpdate.setToolTipText("Atualizar");
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(455, 388, 64, 64);
		contentPane.add(btnUpdate);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/Delete.png")));
		btnExcluir.setToolTipText("Atualizar");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.setBorder(null);
		btnExcluir.setBackground(Color.BLACK);
		btnExcluir.setBounds(561, 388, 64, 64);
		contentPane.add(btnExcluir);

		// ID
		RestrictedTextField validar2 = new RestrictedTextField(txtId);
		validar2.setLimit(5);
		validar2.setOnlyNums(true);

		// Nome Do Contato
		RestrictedTextField validar = new RestrictedTextField(txtNome);
		validar.setLimit(50);
		validar.setOnlyText(true);
		validar.setAcceptSpace(true);

		// CPF
		RestrictedTextField validar3 = new RestrictedTextField(txtCpf);
		validar3.setLimit(16);
		validar3.setOnlyNums(true);

		// Email
		RestrictedTextField validar11 = new RestrictedTextField(txtEmail);
		validar11.setLimit(50);

		// Fone
		RestrictedTextField validar8 = new RestrictedTextField(txtFone, "0123456789()-");
		validar8.setLimit(15);
		validar8.setOnlyNums(true);

		// CEP
		RestrictedTextField validar12 = new RestrictedTextField(txtCep);
		validar12.setOnlyNums(true);
		validar12.setLimit(10);

		// Endereço
		RestrictedTextField validar13 = new RestrictedTextField(txtEndereco);
		validar13.setLimit(50);

		// Numero
		RestrictedTextField validar14 = new RestrictedTextField(txtNumero, "0123456789()-");
		validar14.setLimit(6);
		validar14.setOnlyNums(true);

		// Complemento
		RestrictedTextField validar15 = new RestrictedTextField(txtComplemento);
		validar15.setLimit(250);

		// Bairro
		RestrictedTextField validar16 = new RestrictedTextField(txtBairro);
		validar16.setLimit(50);
		validar16.setOnlyText(true);
		validar16.setAcceptSpace(true);

		// Cidade
		RestrictedTextField validar17 = new RestrictedTextField(txtCidade);
		validar17.setLimit(50);

		getRootPane().setDefaultButton(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 55, 569, 93);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});

		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "ID", "Nome", "CPF " }));
		scrollPane.setViewportView(table);

		lblNewLabel_1 = new JLabel("Campos com  *  são obrigatórios");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(140, 30, 212, 14);
		contentPane.add(lblNewLabel_1);
		validar17.setLimit(50);
		validar17.setOnlyText(true);
		validar17.setAcceptSpace(true);

	}// Fim do Construtor

	DAO dao = new DAO(); // crtl + shift + O
	private JTable table;
	private JLabel lblNewLabel_1;

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

	private void pesquisarnome() {

		// VALIDAÇÃO
		if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite A ID do Cliente");
			txtId.requestFocus();
		} else {

			String read = "select * from clientes where id = ?";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(read);

				pst.setString(1, txtId.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					txtId.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtCpf.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(4));
					txtNumero.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(6));
					txtBairro.setText(rs.getString(8));
					txtCidade.setText(rs.getString(9));
					txtCep.setText(rs.getString(10));
					cboUf.setSelectedItem(rs.getString(11));
					txtFone.setText(rs.getString(12));
					txtEmail.setText(rs.getString(13));
					txtObs.setText(rs.getString(14));

					btnUpdate.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnAdd.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Cliente Inexistente");
					txtId.requestFocus();

					btnAdd.setEnabled(true);

				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}// FIM DA BUSCA DO USUARIO

	private void pesquisaAvançada() {
		String read2 = "select id as ID, Nome as nome, CPF as cpf from clientes where nome like ?";

		try {
			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtNome.getText() + "%"); // ATENÇÃO TEM Q SER DESSE MODO "%"
			ResultSet rs = pst.executeQuery();

			// uso da Biblioteca rx2xml para encher a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs)); /// so da pra usar isso dai se estiver a biblioteca lá

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}// FIM DA Pesquisa avançada

	void addCliente() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Nome");
			txtNome.requestFocus();

		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O CPF");
			txtCpf.requestFocus();

		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Telefone Para Contato");
			txtFone.requestFocus();

		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha A  UF");
			cboUf.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Endereço");
			txtEndereco.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Número");
			txtNumero.requestFocus();

		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Bairro");
			txtBairro.requestFocus();

		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha A Cidade");
			txtCidade.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Email para Contato");
			txtEmail.requestFocus();

		} else {

			// System.out.println("Teste Confirmar");
			String create = "insert into clientes (nome,cpf,endereco,numero,complemento,bairro,cidade,cep,uf,contato,email,obs) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCpf.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtFone.getText());
				pst.setString(11, txtEmail.getText());
				pst.setString(12, txtObs.getText());

				int confirma1 = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma1 == 1) {
					JOptionPane.showMessageDialog(null, "Cliente Adicionado");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ATENÇÃO ESSE CLIENTE NÃO FOI ADICIONADO");

				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse Cliente com Esse CPF já existe!!");
				txtCpf.setText(null);
				txtCpf.requestFocus();
				limpar();

			} catch (Exception e2) {
				System.out.println(e2);

			}
		}
	}// FIM DO ADICIONAR

	private void updateClientes() {
		// System.out.println("teste ");

		// validação de CAMPOS OBRIGATÓRIOS
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Nome");
			txtNome.requestFocus();

		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O CPF");
			txtCpf.requestFocus();

		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Telefone Para Contato");
			txtFone.requestFocus();

		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha A  UF");
			cboUf.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Endereço");
			txtEndereco.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Número");
			txtNumero.requestFocus();

		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Bairro");
			txtBairro.requestFocus();

		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha A Cidade");
			txtCidade.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Email para Contato");
			txtEmail.requestFocus();

		} else {
			// LOGICA PRINCIPAL
			String update = "update clientes set nome = ?,cpf  = ?,endereco = ?,numero = ?,complemento = ?,bairro = ?,cidade = ?,cep = ?,uf = ?,contato = ?,email = ?, obs = ?  where id = ?";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(update);

				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCpf.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtFone.getText());
				pst.setString(11, txtEmail.getText());
				pst.setString(12, txtObs.getText());
				pst.setString(13, txtId.getText());

				int confirma = pst.executeUpdate();
				// System.out.println("confirma");
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente Atualizado");
					limpar();
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse Cliente já existe!!");
				txtCpf.setText(null);
				txtCpf.requestFocus();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// FIM DO UPDATE

	private void excluirCliente() {

		int confirma = JOptionPane.showConfirmDialog(null, "Deseja Excluir Esse Cliente??", "Exluir Fornecedor!!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from clientes where id = ?";

			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());

				int confirmaExcluir = pst.executeUpdate();

				if (confirmaExcluir == 1) {
					JOptionPane.showMessageDialog(null, "Cliente Excluido Com Sucesso!!!");
					limpar();
				} else {
					JOptionPane.showMessageDialog(null, "Ordem de Serviço não deletada");
					limpar();
				}

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				System.out.println(e1);
				JOptionPane.showMessageDialog(null,
						"Cliente não excluído, pois existem Ordens de Serviços vinculados a esse Cliente");

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}
// FIM DO EXCLUIR

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			org.dom4j.Document documento = xml.read(url);
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
					cboUf.setSelectedItem(element.getText());
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

	}// Fim Do cep

	private void setarCampos() {
		int setar = table.getSelectedRow();
		txtNome.setText(table.getModel().getValueAt(setar, 1).toString());
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());

	}

	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtNome.requestFocus();
		txtFone.setText(null);
		txtCpf.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtEmail.setText(null);
		cboUf.setSelectedItem("");
		cboUf.requestFocus();
		txtObs.setText(null);
		btnAdd.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnExcluir.setEnabled(false);
		((DefaultTableModel) table.getModel()).setRowCount(0);

	}
}
// FIM DA VIDA