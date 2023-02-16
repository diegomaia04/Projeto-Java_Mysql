package views;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

//import org.jdom.Document;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import Atxy2k.CustomTextField.RestrictedTextField;
import models.DAO;
import net.proteanit.sql.DbUtils;

public class Os extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCliente;
	private JTextField txtCodigo;
	private JTable table;
	private JTextField txtModelo;
	private JTextField txtCusto;
	private JTextField txtSerie;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Os dialog = new Os();
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
	public Os() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Os.class.getResource("/img/OS.png")));
		addWindowListener(new WindowAdapter() {

		});
		getContentPane().setBackground(Color.BLACK);
		setTitle("Ordem de Serviço");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 763, 734);
		getContentPane().setLayout(null);

		datePedido = new JDateChooser();
		datePedido.setEnabled(false);
		datePedido.setBounds(16, 532, 147, 20);
		getContentPane().add(datePedido);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			// LEITOR DE CODIGO DE BARRAS
			// evento ao pressionar uma tecla expecifica (ENTER)
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				}
			}
		});
		txtCliente.setBounds(16, 284, 216, 20);
		getContentPane().add(txtCliente);
		txtCliente.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Id da OS:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(16, 31, 64, 14);
		getContentPane().add(lblNewLabel_1);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(68, 28, 57, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(394, 72, 293, 155);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtClienteT = new JTextField();
		txtClienteT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisaAvançada();
			}
		});
		txtClienteT.setBounds(10, 23, 152, 20);
		panel.add(txtClienteT);
		txtClienteT.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 54, 273, 74);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "ID", "Clientes", "Telefone" }));
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_3 = new JLabel("ID");
		lblNewLabel_3.setBounds(172, 26, 24, 14);
		panel.add(lblNewLabel_3);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(194, 23, 86, 20);
		panel.add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Modelo*:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(253, 259, 63, 14);
		getContentPane().add(lblNewLabel_4);

		txtModelo = new JTextField();
		txtModelo.setBounds(256, 284, 216, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Defeito*:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(16, 323, 70, 14);
		getContentPane().add(lblNewLabel_5);

		txtaDefeito = new JTextArea();
		txtaDefeito.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtaDefeito.setBounds(16, 348, 197, 74);
		getContentPane().add(txtaDefeito);

		JLabel lblNewLabel_6 = new JLabel("Entrada do pedido:");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(16, 507, 109, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Selecione o tempo \r\nde Garantia*:");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_7.setBounds(16, 439, 197, 20);
		getContentPane().add(lblNewLabel_7);

		dateGarantia = new JDateChooser();
		dateGarantia.setBounds(16, 470, 147, 20);
		getContentPane().add(dateGarantia);

		JLabel lblNewLabel_8 = new JLabel("Custo total do Serviço:");
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(226, 442, 134, 14);
		getContentPane().add(lblNewLabel_8);

		txtCusto = new JTextField();
		txtCusto.setText("0");
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
		txtCusto.setBounds(226, 470, 123, 20);
		getContentPane().add(txtCusto);
		txtCusto.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("Número de Série*:");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_11.setBounds(523, 259, 197, 14);
		getContentPane().add(lblNewLabel_11);

		txtSerie = new JTextField();
		txtSerie.setBounds(523, 284, 182, 20);
		getContentPane().add(txtSerie);
		txtSerie.setColumns(10);

		btnAdd = new JButton("");
		btnAdd.setToolTipText("Adicionar");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addOs();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setIcon(new ImageIcon(Os.class.getResource("/img/2303130_add_create_new_plus_icon.png")));
		btnAdd.setBorder(null);
		btnAdd.setBounds(378, 620, 64, 64);
		getContentPane().add(btnAdd);

		btnUpdate = new JButton("");
		btnUpdate.setToolTipText("Atualizar");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateOs();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBorder(null);
		btnUpdate.setIcon(new ImageIcon(Os.class.getResource("/img/9027543_rotate_refresh_icon.png")));
		btnUpdate.setBackground(Color.BLACK);
		btnUpdate.setBounds(469, 620, 64, 64);
		getContentPane().add(btnUpdate);

		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Deletar");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOs();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Os.class.getResource("/img/Delete.png")));
		btnExcluir.setBackground(Color.BLACK);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(554, 620, 64, 64);
		getContentPane().add(btnExcluir);

		RestrictedTextField validar2 = new RestrictedTextField(txtCliente);
		validar2.setLimit(255);

		RestrictedTextField validar = new RestrictedTextField(txtCodigo);
		validar.setLimit(25);
		validar.setOnlyNums(true);

		RestrictedTextField validar3 = new RestrictedTextField(txtModelo);
		validar3.setLimit(50);

		// Descrição
		// RestrictedTextField validar11 = new RestrictedTextField(txtaDescricao);
		// validar11.setLimit(50);

		// Fabricante
		RestrictedTextField validar5 = new RestrictedTextField(txtSerie);
		validar5.setLimit(50);

		// Custo
		RestrictedTextField validar9 = new RestrictedTextField(txtCusto);
		validar9.setLimit(10);

		JLabel lblNewLabel_1_1 = new JLabel("Digite o nome do Cliente*:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(16, 259, 216, 14);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_5_1 = new JLabel("Descrição do Serviço");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		lblNewLabel_5_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(253, 323, 202, 14);
		getContentPane().add(lblNewLabel_5_1);

		txtaServico1 = new JTextArea();
		txtaServico1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtaServico1.setBounds(253, 346, 189, 74);
		getContentPane().add(txtaServico1);

		cboStatus1 = new JComboBox();
		cboStatus1.setModel(new DefaultComboBoxModel(new String[] { "", "Pendente", "Resolvido" }));
		cboStatus1.setBounds(520, 406, 98, 22);
		getContentPane().add(cboStatus1);

		JLabel lblNewLabel_8_1 = new JLabel("Status*:");
		lblNewLabel_8_1.setForeground(Color.WHITE);
		lblNewLabel_8_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_8_1.setBounds(523, 381, 46, 14);
		getContentPane().add(lblNewLabel_8_1);

		JLabel lblNewLabel_5_2 = new JLabel("Técnico*:");
		lblNewLabel_5_2.setForeground(Color.WHITE);
		lblNewLabel_5_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_5_2.setBounds(523, 323, 70, 14);
		getContentPane().add(lblNewLabel_5_2);

		txtTecnico = new JTextField();
		txtTecnico.setColumns(10);
		txtTecnico.setBounds(523, 350, 172, 20);
		getContentPane().add(txtTecnico);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOs();

			}
		});
		btnBuscar.setBorderPainted(false);
		btnBuscar.setBackground(Color.BLACK);
		btnBuscar.setIcon(new ImageIcon(Os.class.getResource("/img/Search.png")));
		btnBuscar.setBounds(131, 11, 64, 57);
		getContentPane().add(btnBuscar);

		btnPrint = new JButton("");
		btnPrint.setEnabled(false);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOs();
			}
		});
		btnPrint.setIcon(
				new ImageIcon(Os.class.getResource("/img/2303185_device_document_paper_print_printer_icon.png")));
		btnPrint.setToolTipText("Imprimir OS");
		btnPrint.setBorder(null);
		btnPrint.setBackground(Color.BLACK);
		btnPrint.setBounds(645, 620, 64, 64);
		getContentPane().add(btnPrint);

		RestrictedTextField validar10 = new RestrictedTextField(txtClienteT);

		panel_1 = new JPanel();
		panel_1.setBounds(20, 72, 296, 155);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Pesquisa Da OS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		txtClienteO = new JTextField();
		txtClienteO.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				tabelaOS();
			}
		});
		txtClienteO.setColumns(10);
		txtClienteO.setBounds(25, 23, 152, 20);
		panel_1.add(txtClienteO);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setEnabled(false);
		scrollPane_1.setBounds(10, 54, 276, 74);
		panel_1.add(scrollPane_1);

		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos1();
			}
		});
		table_1.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, }, new String[] { "ID da OS", "Cliente", "ID dos Clientes" }));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(74);
		scrollPane_1.setViewportView(table_1);

		lblNewLabel = new JLabel("Campos com  *  são obrigatórios");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(513, 11, 212, 14);
		getContentPane().add(lblNewLabel);
		validar10.setLimit(100);
		validar10.setOnlyText(true);

		RestrictedTextField validar11 = new RestrictedTextField(txtTecnico);

		cboStatus2 = new JComboBox();
		cboStatus2.setModel(
				new DefaultComboBoxModel(new String[] { "", "Dinheiro", "Pix", "Débito", "Crédito", "Boleto" }));
		cboStatus2.setBounds(226, 530, 98, 22);
		getContentPane().add(cboStatus2);

		JLabel lblNewLabel_8_1_1 = new JLabel("Pagamento*:");
		lblNewLabel_8_1_1.setForeground(Color.WHITE);
		lblNewLabel_8_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_8_1_1.setBounds(226, 507, 98, 14);
		getContentPane().add(lblNewLabel_8_1_1);

		txtaRazao = new JTextArea();
		txtaRazao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtaRazao.setBounds(516, 477, 189, 74);
		getContentPane().add(txtaRazao);

		JLabel lblNewLabel_11_1 = new JLabel("Justificativa Da Pendência: ");
		lblNewLabel_11_1.setForeground(Color.WHITE);
		lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_11_1.setBounds(520, 452, 197, 14);
		getContentPane().add(lblNewLabel_11_1);
		validar11.setLimit(50);
		validar11.setOnlyText(true);
		validar11.setAcceptSpace(true);

	}// fim do Construtor

	DAO dao = new DAO();
	private JTextField txtClienteT;
	private JTextArea txtaDefeito;
	private JDateChooser datePedido;
	private JDateChooser dateGarantia;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnExcluir;

	private JTextField txtTecnico;
	private JTextArea txtaServico1;
	private JComboBox<?> cboStatus1;
	private JTextField txtId;
	private JButton btnBuscar;
	private JButton btnPrint;
	private JTextField txtClienteO;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JTable table_1;
	private JLabel lblNewLabel;
	private JComboBox cboStatus2;
	private JTextArea txtaRazao;

	private void pesquisaAvançada() {
		String read3 = "select id as Id, nome as cliente, contato as contato from clientes where nome like ?";

		try {
			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, txtClienteT.getText() + "%"); // ATENÇÃO TEM Q SER DESSE MODO "%"
			ResultSet rs = pst.executeQuery();

			// uso da Biblioteca rx2xml para encher a tabela
			table.setModel(DbUtils.resultSetToTableModel(rs)); /// so da pra usar isso dai se estiver a biblioteca lá

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}// Fim DA PESQUISA AVANÇADA

	private void tabelaOS() {
		String read4 = "select idfor as ID_OS, nomee as Cliente, id as ID_Clientes from os where nomee like ?";

		try {
			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read4);
			pst.setString(1, txtClienteO.getText() + "%"); // ATENÇÃO TEM Q SER DESSE MODO "%"
			ResultSet rs = pst.executeQuery();

			// uso da Biblioteca rx2xml para encher a tabela
			table_1.setModel(DbUtils.resultSetToTableModel(rs)); /// so da pra usar isso dai se estiver a biblioteca lá

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}// Fim DA PESQUISA AVANÇADA

	private void pesquisarOs() {
		if (txtCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Id da OS ");
			txtCodigo.requestFocus();
		} else {
			// System.out.println();
			String read = "select * from os where idfor = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtCodigo.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtTecnico.setText(rs.getString(2));
					txtModelo.setText(rs.getString(3));
					txtSerie.setText(rs.getString(4));
					txtaDefeito.setText(rs.getString(5));
					txtaServico1.setText(rs.getString(7));
					cboStatus1.setSelectedItem(rs.getString(8));
					txtCusto.setText(rs.getString(9));
					txtCliente.setText(rs.getString(12));
					cboStatus2.setSelectedItem(rs.getString(13));
					txtaRazao.setText(rs.getString(14));
					// formatação da data recebida do MYsql
					// JCalendar - formatação para exibição
					String setarData = rs.getString(6); // x -> número do campo da tabela
					// System.out.println(setarData);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					datePedido.setDate(dataFormatada);
					String setarData2 = rs.getString(10);
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					dateGarantia.setDate(dataFormatada2);
					txtId.setEnabled(false);
					txtId.setEnabled(false);
					// habilitar os botoes
					btnAdd.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnPrint.setEnabled(true);
					txtCliente.setEnabled(false);

					;
				} else {
					JOptionPane.showMessageDialog(null, " Ordem de Serviço não cadastrato");
					limpar();

				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}// FIM DA Pesquisa avançada

	void addOs() throws SQLException {

		// validação de CAMPOS OBRIGATÓRIOS

		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Modelo do aparelho");
			txtModelo.requestFocus();

		} else if (txtSerie.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero da série");
			txtSerie.requestFocus();

		} else if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente e sua ID");
			txtCliente.requestFocus();

		} else if (txtTecnico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Técnico");
			txtTecnico.requestFocus();

		} else if (txtaDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha Qual foi o Defeito do Aparelho");
			txtaDefeito.requestFocus();

		} else if (cboStatus1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Status do Serviço");
			cboStatus1.requestFocus();

		} else if (dateGarantia.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha A Garantia Do Aparelho");
			dateGarantia.requestFocus();
		} else {

			// System.out.println("Teste Confirmar");
			String insert = "insert into os (tecnico, modelo, serie, defeito, solucao, pendencia, total, garantia,id,nomee,forma,razao) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(insert);
				pst.setString(1, txtTecnico.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtSerie.getText());
				pst.setString(4, txtaDefeito.getText());
				pst.setString(5, txtaServico1.getText());
				pst.setString(6, cboStatus1.getSelectedItem().toString());
				pst.setString(7, txtCusto.getText());
				pst.setString(9, txtId.getText());
				pst.setString(10, txtCliente.getText());
				pst.setString(11, cboStatus2.getSelectedItem().toString());
				pst.setString(12, txtaRazao.getText());

				// Formatar o valor do JCalendar para inserção correta no banco ele vai pegar a
				// data que for inserida e vai tranformar para a forma que é aceita no banco de
				// dados.
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateGarantia.getDate());
				pst.setString(8, dataFormatada); // x -> parâmetro do componente dateChooser

				int confirma1 = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma1 == 1) {
					JOptionPane.showMessageDialog(null, "Ordem de serviço Cadastrado Com Sucesso!!!");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "ATENÇÃO ORDEM DE SERVIÇO NÃO CADASTRADO");
					limpar();
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Essa ORDEM DE SERVIÇO JÁ ESTÁ CADASTRADO!!");
				txtCodigo.setText(null);
				txtCodigo.requestFocus();

			}

			catch (Exception e) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse Cliente NÃO EXISTE!!");
				txtCliente.setText(null);
				txtCliente.requestFocus();
			}

		}
	}// FIM DA Pesquisa De OS

	private void updateOs() throws SQLException {

		// validação de CAMPOS OBRIGATÓRIOS

		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Modelo do aparelho");
			txtModelo.requestFocus();

		} else if (txtSerie.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero da série");
			txtSerie.requestFocus();

		} else if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente");
			txtCliente.requestFocus();

		} else if (txtTecnico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Técnico");
			txtTecnico.requestFocus();

		} else if (txtaDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha Qual foi o Defeito do Aparelho");
			txtaDefeito.requestFocus();

		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha O Custo Da Ordem de Serviço");
			txtCusto.requestFocus();

		} else if (txtaServico1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual foi a Solução do Defeito");
			txtaServico1.requestFocus();

		} else if (txtClienteT.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome na Tabela Clientes e sua ID");
			txtClienteT.requestFocus();

		}

		else if (cboStatus1.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Status do Serviço");
			cboStatus1.requestFocus();

		} else if (cboStatus2.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha Qual Será a Forma de Pagamento");
			cboStatus2.requestFocus();

		}

		else if (dateGarantia.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha A Garantia Do Aparelho");
			dateGarantia.requestFocus();
		} else {

			String update = "update os set tecnico  = ? ,modelo = ? ,serie = ? ,defeito = ? ,solucao = ? ,pendencia  = ?,total = ? ,garantia = ?,id = ?,nomee = ?, forma = ?, razao = ? where idfor = ?";
			try {
				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtTecnico.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtSerie.getText());
				pst.setString(4, txtaDefeito.getText());
				pst.setString(5, txtaServico1.getText());
				pst.setString(6, cboStatus1.getSelectedItem().toString());
				pst.setString(7, txtCusto.getText());
				pst.setString(9, txtId.getText());
				pst.setString(10, txtCliente.getText());
				pst.setString(11, cboStatus2.getSelectedItem().toString());
				pst.setString(12, txtaRazao.getText());
				pst.setString(13, txtCodigo.getText());

				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateGarantia.getDate());
				pst.setString(8, dataFormatada);

				int confirma = pst.executeUpdate();
				// System.out.println("confrima");
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Ordem De Serviço Atualizada");
					limpar();
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) { // personalizar os erros do java
				JOptionPane.showMessageDialog(null, "Esse OS Já Existe!!");
				txtCliente.setText(null);
				txtCliente.requestFocus();
			}
		}
	}// FIM DO UPDATE

	private void excluirOs() {

		int confirma = JOptionPane.showConfirmDialog(null, "Deseja Excluir Essa Ordem De Serviço??", "Exluir Ordem!!",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from os where idfor = ?";

			try {

				Connection con = dao.conectar();

				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());

				int confirmaExcluir = pst.executeUpdate();

				if (confirmaExcluir == 1) {
					JOptionPane.showMessageDialog(null, " Ordem De Serviço Excluida Com Sucesso");
					limpar();

				} else {
					JOptionPane.showMessageDialog(null, "Ordem de Serviço não deletada");
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
		txtCliente.setText(table.getModel().getValueAt(setar, 1).toString());
		txtClienteT.setText(table.getModel().getValueAt(setar, 1).toString());

	}

	private void setarCampos1() {
		int setar1 = table_1.getSelectedRow();

		txtCodigo.setText(table_1.getModel().getValueAt(setar1, 0).toString());
		txtClienteT.setText(table_1.getModel().getValueAt(setar1, 1).toString());
		txtId.setText(table_1.getModel().getValueAt(setar1, 2).toString());

	}

	private void imprimirOs() {
		Document document = new Document(PageSize.A4.rotate(), 60f, 60f, 50f, 20f);
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Os.pdf"));
			document.open();
			Paragraph p = new Paragraph("Ordem de Serviço");
			p.setAlignment(100);

			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(13);

			PdfPCell col1 = new PdfPCell(new Paragraph("ID Da Ordem De Serviço"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Nome do Cliente"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Modelo Do Console"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Número De Serie"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Defeito"));
			PdfPCell col6 = new PdfPCell(new Paragraph("Serviço Prestado"));
			PdfPCell col7 = new PdfPCell(new Paragraph("Técnico"));
			PdfPCell col8 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col9 = new PdfPCell(new Paragraph("Entrada Do Pedido"));
			PdfPCell col10 = new PdfPCell(new Paragraph("Garantia"));
			PdfPCell col11 = new PdfPCell(new Paragraph("Custo Total Do Serviço"));
			PdfPCell col12 = new PdfPCell(new Paragraph("Forma de Pagamento"));
			PdfPCell col13 = new PdfPCell(new Paragraph("Justificativa Da Pendência"));

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);
			tabela.addCell(col7);
			tabela.addCell(col8);
			tabela.addCell(col9);
			tabela.addCell(col10);
			tabela.addCell(col11);
			tabela.addCell(col12);
			tabela.addCell(col13);

			String relClientes = "select * from os where idfor  = ?";
			// String relClientes = "select
			// idfor,tecnico,modelo,serie,defeito,datacad,solucao,pendencia,total,garantia,nomee
			// from os like idfor =?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				pst.setString(1, txtCodigo.getText());
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(12));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(10));
					tabela.addCell(rs.getString(9));
					tabela.addCell(rs.getString(13));
					tabela.addCell(rs.getString(14));

				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally { // executa o código independente do resultado OK ou não
			document.close();
		}

		// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("Os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void limpar() {
		txtaRazao.setText(null);
		txtClienteO.setText(null);
		txtCliente.setText(null);
		txtTecnico.setText(null);
		txtCliente.requestFocus();
		txtCodigo.setText(null);
		txtModelo.setText(null);
		txtaDefeito.setText(null);
		txtSerie.setText(null);
		txtClienteT.setText(null);
		txtaServico1.setText(null);
		cboStatus1.setSelectedItem("");
		cboStatus2.setSelectedItem("");
		txtCusto.setText(null);
		txtId.setText(null);
		dateGarantia.setDate(null);
		datePedido.setDate(null);
		btnAdd.setEnabled(true);
		btnUpdate.setEnabled(false);
		((DefaultTableModel) table.getModel()).setRowCount(0);
		((DefaultTableModel) table_1.getModel()).setRowCount(0);

	}
}
// FIM DA VIDA