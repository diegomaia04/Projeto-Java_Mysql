package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Produtos extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtBarcode;
	private JTextField txtCodigo;
	private JTable table;
	private JTextField txtId;
	private JTextField txtProduto;
	private JTextField txtCusto;
	private JTextField txtLucro;
	private JTextField txtFabricante;
	private JTextField txtEstoque;
	private JTextField txtEstoquemin;
	private JTextField txtLocal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtBarcode.requestFocus();
			}
		});
		getContentPane().setBackground(Color.BLACK);
		setTitle("Produtos");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 502);
		getContentPane().setLayout(null);

		dateEntrada = new JDateChooser();
		dateEntrada.setBounds(379, 226, 147, 20);
		getContentPane().add(dateEntrada);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Produtos.class.getResource("/img/barcode.png")));
		lblNewLabel.setBounds(22, 31, 64, 45);
		getContentPane().add(lblNewLabel);

		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			// LEITOR DE CODIGO DE BARRAS
			// evento ao pressionar uma tecla expecifica (ENTER)
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarProdutoBarcode();

				}
			}
		});
		txtBarcode.setBounds(96, 43, 216, 20);
		getContentPane().add(txtBarcode);
		txtBarcode.setColumns(10);

		dateEntrada.setEnabled(false);

		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(39, 97, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(96, 94, 103, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		JButton btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}
		});
		btnPesquisar.setBackground(Color.BLACK);
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Search.png")));
		btnPesquisar.setBounds(209, 74, 38, 45);
		getContentPane().add(btnPesquisar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(348, 31, 395, 151);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisaAvançada();
			}
		});
		txtFornecedor.setBounds(25, 23, 152, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Produtos.class.getResource("/img/search.png")));
		lblNewLabel_2.setBounds(187, 11, 48, 42);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(25, 54, 349, 74);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
				new String[] { "ID", "Fornecedor", "Nome do Ct.", "Telefone" }));
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_3 = new JLabel("ID");
		lblNewLabel_3.setBounds(263, 26, 24, 14);
		panel.add(lblNewLabel_3);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(288, 23, 86, 20);
		panel.add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Produto:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(39, 146, 46, 14);
		getContentPane().add(lblNewLabel_4);

		txtProduto = new JTextField();
		txtProduto.setBounds(96, 143, 216, 20);
		getContentPane().add(txtProduto);
		txtProduto.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Descri\u00E7\u00E3o:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(10, 200, 70, 14);
		getContentPane().add(lblNewLabel_5);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtaDescricao.setBounds(96, 195, 229, 74);
		getContentPane().add(txtaDescricao);

		JLabel lblNewLabel_6 = new JLabel("Entrada:");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(379, 201, 58, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Validade:");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_7.setBounds(576, 200, 64, 14);
		getContentPane().add(lblNewLabel_7);

		dateValidade = new JDateChooser();
		dateValidade.setBounds(577, 226, 147, 20);
		getContentPane().add(dateValidade);

		JLabel lblNewLabel_8 = new JLabel("Custo:");
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(379, 291, 46, 14);
		getContentPane().add(lblNewLabel_8);

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (aceita somente os caracteres da String)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtCusto.setBounds(418, 288, 121, 20);
		getContentPane().add(txtCusto);
		txtCusto.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Lucro:");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_9.setBounds(576, 291, 46, 14);
		getContentPane().add(lblNewLabel_9);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (aceita somente os caracteres da String)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtLucro.setBounds(619, 288, 77, 20);
		getContentPane().add(txtLucro);
		txtLucro.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("%");
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_10.setBounds(706, 291, 46, 14);
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Fabricante:");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_11.setBounds(22, 310, 64, 14);
		getContentPane().add(lblNewLabel_11);

		txtFabricante = new JTextField();
		txtFabricante.setBounds(96, 307, 229, 20);
		getContentPane().add(txtFabricante);
		txtFabricante.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("Estoque:");
		lblNewLabel_12.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setBounds(28, 362, 58, 14);
		getContentPane().add(lblNewLabel_12);

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// validação (aceita somente os caracteres da String)
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtEstoque.setBounds(96, 359, 51, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setColumns(10);

		JLabel lblNewLabel_13 = new JLabel("Estoque m\u00EDnimo:");
		lblNewLabel_13.setForeground(Color.WHITE);
		lblNewLabel_13.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_13.setBounds(169, 362, 95, 14);
		getContentPane().add(lblNewLabel_13);

		txtEstoquemin = new JTextField();
		txtEstoquemin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

		});
		txtEstoquemin.setColumns(10);
		txtEstoquemin.setBounds(274, 359, 51, 20);
		getContentPane().add(txtEstoquemin);

		JLabel lblNewLabel_14 = new JLabel("Unidade:");
		lblNewLabel_14.setForeground(Color.WHITE);
		lblNewLabel_14.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_14.setBounds(39, 416, 58, 14);
		getContentPane().add(lblNewLabel_14);

		cboUnidade = new JComboBox();
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] { "", "UN", "PC", "CX", "KG", "g", "M", "CM" }));
		cboUnidade.setBounds(96, 412, 51, 22);
		getContentPane().add(cboUnidade);

		JLabel lblNewLabel_15 = new JLabel("Local:");
		lblNewLabel_15.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setBounds(161, 416, 38, 14);
		getContentPane().add(lblNewLabel_15);

		txtLocal = new JTextField();
		txtLocal.setBounds(209, 413, 116, 20);
		getContentPane().add(txtLocal);
		txtLocal.setColumns(10);

		btnAdd = new JButton("");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addProdutos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setIcon(new ImageIcon(Produtos.class.getResource("/img/Add.png")));
		btnAdd.setBorder(null);
		btnAdd.setBounds(424, 375, 64, 64);
		getContentPane().add(btnAdd);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateProdutos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBorder(null);
		btnUpdate.setIcon(new ImageIcon(Produtos.class.getResource("/img/Update.png")));
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(529, 375, 64, 64);
		getContentPane().add(btnUpdate);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProdutos();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/Delete.png")));
		btnExcluir.setBackground(Color.BLACK);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(638, 375, 64, 64);
		getContentPane().add(btnExcluir);

		// BARCODE
		RestrictedTextField validar2 = new RestrictedTextField(txtBarcode);
		validar2.setLimit(255);
		validar2.setOnlyNums(true);

		// CODIGO
		RestrictedTextField validar = new RestrictedTextField(txtCodigo);
		validar.setLimit(25);
		validar.setOnlyNums(true);

		// Produto
		RestrictedTextField validar3 = new RestrictedTextField(txtProduto);
		validar3.setLimit(50);

		// Descrição
		// RestrictedTextField validar11 = new RestrictedTextField(txtaDescricao);
		// validar11.setLimit(50);

		// Fabricante
		RestrictedTextField validar5 = new RestrictedTextField(txtFabricante);
		validar5.setLimit(50);

		// Estoque
		RestrictedTextField validar6 = new RestrictedTextField(txtEstoque);
		validar6.setLimit(10);

		// EstoqueMIN
		RestrictedTextField validar7 = new RestrictedTextField(txtEstoquemin);
		validar7.setLimit(10);

		// Localização
		RestrictedTextField validar8 = new RestrictedTextField(txtLocal);
		validar8.setLimit(50);

		// Custo
		RestrictedTextField validar9 = new RestrictedTextField(txtCusto);
		validar9.setLimit(10);

		// Lucro
		RestrictedTextField validar10 = new RestrictedTextField(txtLucro);
		validar10.setLimit(10);

		// Id
		RestrictedTextField validar11 = new RestrictedTextField(txtId);
		validar11.setLimit(12);
		validar11.setOnlyNums(true);

		;

	}// fim do Construtor

	DAO dao = new DAO();
	private JTextField txtFornecedor;
	private JComboBox<?> cboUnidade;
	private JTextArea txtaDescricao;
	private JDateChooser dateEntrada;
	private JDateChooser dateValidade;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnExcluir;

	private void pesquisaAvançada() {
		String read3 = "select idFor as ID, fantasia as Fornecedor, fone, nomeContato as contato from fornecedores where fantasia like ?";

		try {
			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtFornecedor.getText() + "%"); // ATENÇÃO TEM Q SER DESSE MODO "%"
			ResultSet rs = pst.executeQuery();

			// uso da Biblioteca rx2xml para encher a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs)); /// so da pra usar isso dai se estiver a biblioteca lá

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}// FIM DA Pesquisa avançada

	private void pesquisarProduto() {
		// System.out.println("Teste");

		if (txtCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Código");
			txtCodigo.requestFocus();
		} else {

			String read = "select * from produtos where codigo = ?";

			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(read);

				pst.setString(1, txtCodigo.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					txtBarcode.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtaDescricao.setText(rs.getString(4));
					txtFabricante.setText(rs.getString(5));
					txtEstoque.setText(rs.getString(8));
					txtEstoquemin.setText(rs.getString(9));
					cboUnidade.setSelectedItem(rs.getString(10));
					txtLocal.setText(rs.getString(11));
					txtId.setText(rs.getString(14));
					txtCusto.setText(rs.getString(12));
					txtLucro.setText(rs.getString(13));

					// formatação da data recevbida do MySQL
					// JCalendar - formatação para exibição
					String setarData = rs.getString(6); // x -> número do campo da tabela
					// apoio a lógica
					// System.out.println(setarData);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					dateEntrada.setDate(dataFormatada);

					String setarData2 = rs.getString(7); // x -> número do campo da tabela
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					dateValidade.setDate(dataFormatada2);

					dateValidade.setEnabled(false);
					dateEntrada.setEnabled(false);
					btnAdd.setEnabled(false);
					btnUpdate.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Produto Não Cadastrado");
					txtCodigo.requestFocus();
					limpar();
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);

			}
		}
	}// FIM DA BUSCA DO USUARIO

	private void pesquisarProdutoBarcode() {
		// System.out.println("Teste");

		String read = "select * from produtos where barcode = ?";

		try {

			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read);

			pst.setString(1, txtBarcode.getText());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(3));
				txtaDescricao.setText(rs.getString(4));
				txtFabricante.setText(rs.getString(5));
				txtEstoque.setText(rs.getString(8));
				txtEstoquemin.setText(rs.getString(9));
				cboUnidade.setSelectedItem(rs.getString(10));
				txtLocal.setText(rs.getString(11));
				txtId.setText(rs.getString(14));
				txtCusto.setText(rs.getString(12));
				txtLucro.setText(rs.getString(13));

				// formatação da data recevbida do MySQL
				// JCalendar - formatação para exibição
				String setarData = rs.getString(6); // x -> número do campo da tabela
				// apoio a lógica
				// System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				dateEntrada.setDate(dataFormatada);

				String setarData2 = rs.getString(7); // x -> número do campo da tabela
				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
				dateValidade.setDate(dataFormatada2);

				dateValidade.setEnabled(false);
				dateEntrada.setEnabled(false);
				btnAdd.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "Produto Não Cadastrado");
				txtBarcode.requestFocus();

			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}// FIM DA BUSCA O BARCODE

	void addProdutos() throws SQLException {

		// validação de CAMPOS OBRIGATÓRIOS

		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Nome Do Produto");
			txtProduto.requestFocus();

		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Nome Do Fabricante");
			txtFabricante.requestFocus();

		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Estoque");
			txtEstoque.requestFocus();

		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Estoque Mínimo");
			txtEstoquemin.requestFocus();

		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha A Localização Do Produto");
			txtLocal.requestFocus();

		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Custo Do Produto");
			txtCusto.requestFocus();

		} else if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Id Do Fornecedor");
			txtId.requestFocus();

		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha A Validade Do Produto");
			dateValidade.requestFocus();

		} else if (dateEntrada.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha A Entarada Do Produto");
			dateEntrada.requestFocus();

		} else {

			// System.out.println("Teste Confirmar");
			String insert = "insert into produtos (barcode,produto,descricao,fabricante,dataval,estoque,estoquemin,unidade,localizacao,custo,lucro,idFor) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(insert);
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtaDescricao.getText());
				pst.setString(4, txtFabricante.getText());
				// ESTA ABAIXO ALI
				pst.setString(6, txtEstoque.getText());
				pst.setString(7, txtEstoquemin.getText());
				pst.setString(8, cboUnidade.getSelectedItem().toString());
				pst.setString(9, txtLocal.getText());
				pst.setString(10, txtCusto.getText());
				pst.setString(11, txtLucro.getText());
				pst.setString(12, txtId.getText());

				// Formatar o valor do JCalendar para inserção correta no banco ele vai pegar a
				// data que for inserida e vai tranformar para a forma que é aceita no banco de
				// dados.
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(5, dataFormatada); // x -> parâmetro do componente dateChooser

				int confirma1 = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma1 == 1) {
					JOptionPane.showMessageDialog(null, "Produto Cadastrado Com Sucesso!!!");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ATENÇÃO PRODUTO JÁ CADASTRADO");
					limpar();
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse PRODUTO JÁ ESTÁ CADASTRADO!!");
				txtBarcode.setText(null);
				txtBarcode.requestFocus();

			} catch (Exception e) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse Fornecedor NÃO EXISTE!!");
				txtId.setText(null);
				txtId.requestFocus();
			}
		}
	}// FIM DO ADICIONAR

	private void updateProdutos() throws SQLException {

		// validação de CAMPOS OBRIGATÓRIOS

		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite O Nome Do Produto");
			txtProduto.requestFocus();

		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Nome Do Fabricante");
			txtFabricante.requestFocus();

		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Estoque");
			txtEstoque.requestFocus();

		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Estoque Mínimo");
			txtEstoquemin.requestFocus();

		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha A Localização Do Produto");
			txtLocal.requestFocus();

		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Custo Do Produto");
			txtCusto.requestFocus();

		} else if (txtId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Id Do Fornecedor");
			txtId.requestFocus();

		} else if (dateValidade.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha A Validade Do Produto");
			dateValidade.requestFocus();

		} else if (dateEntrada.getDate() == null) { 
			JOptionPane.showMessageDialog(null, "Preencha A Entarada Do Produto");
			dateEntrada.requestFocus();

		} else {

			String update = "update produtos set  produto  = ? ,descricao = ? ,fabricante = ? ,dataval = ? ,estoque = ? ,estoquemin = ? ,unidade = ? ,localizacao = ? ,custo = ? ,lucro = ? ,idFor = ? where codigo =?";
			try {
				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtaDescricao.getText());
				pst.setString(3, txtFabricante.getText());
				// ESTA ABAIXO ALI
				pst.setString(5, txtEstoque.getText());
				pst.setString(6, txtEstoquemin.getText());
				pst.setString(7, cboUnidade.getSelectedItem().toString());
				pst.setString(8, txtLocal.getText());
				pst.setString(9, txtCusto.getText());
				pst.setString(10, txtLucro.getText());
				pst.setString(11, txtId.getText());
				pst.setString(12, txtCodigo.getText());

				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(4, dataFormatada);

				int confirma = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto Atualizado");
					limpar();
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse Fornecedor Já Existe!!");
				txtBarcode.setText(null);
				txtBarcode.requestFocus();
			}
		}
	}// FIM DO UPDATE

	private void excluirProdutos() {

		int confirma = JOptionPane.showConfirmDialog(null, "Deseja Excluir Esse Produto??", "Exluir Produto!!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from produtos where codigo = ?";

			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());

				int confirmaExcluir = pst.executeUpdate();

				if (confirmaExcluir == 1) {
					JOptionPane.showMessageDialog(null, "Produto Excluido Com Sucesso");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ATENÇÃO PRODUTO JÁ CADASTRADO");
					limpar();
				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}

		}

	}// FIM DO EXCLUIR

	// metodo usado para setar os campos do formulario com os dados da tabela.
	private void setarCampos() {
		int setar = table.getSelectedRow();
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());

	}

	private void limpar() {
		txtBarcode.setText(null);
		txtBarcode.requestFocus();
		txtCodigo.setText(null);
		txtProduto.setText(null);
		txtaDescricao.setText(null);
		txtFabricante.setText(null);
		txtEstoque.setText(null);
		txtEstoquemin.setText(null);
		txtFornecedor.setText(null);
		cboUnidade.requestFocus();
		cboUnidade.setSelectedItem("");
		txtLocal.setText(null);
		txtCusto.setText(null);
		txtLucro.setText(null);
		txtId.setText(null);
		dateValidade.setDate(null);
		dateEntrada.setDate(null);
		btnAdd.setEnabled(true);
		btnUpdate.setEnabled(false);

		((DefaultTableModel) table.getModel()).setRowCount(0);

	}
}
// FIM DA VIDA