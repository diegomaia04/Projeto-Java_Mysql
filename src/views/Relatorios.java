package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import models.DAO;

public class Relatorios extends JDialog {

	private JPanel contentPane;
	private JButton btnPendentes;
	private JButton btnTodas;
	private JButton btnEntregues;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios frame = new Relatorios();
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
	public Relatorios() {
		setModal(true);
		setTitle("Relatórios");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/Report.png")));
		setBounds(100, 100, 498, 336);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Osclientes();
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText(" Clientes");
		btnClientes.setBackground(SystemColor.desktop);
		btnClientes.setBorderPainted(false);
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Clients.png")));
		btnClientes.setBounds(389, 106, 64, 64);
		contentPane.add(btnClientes);

		btnTodas = new JButton("");
		btnTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TodasOs();
			}
		});
		btnTodas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTodas.setIcon(new ImageIcon(Relatorios.class.getResource("/img/os todas.png")));
		btnTodas.setToolTipText("Todas as Ordens de Serviço");
		btnTodas.setBorderPainted(false);
		btnTodas.setBackground(Color.BLACK);
		btnTodas.setBounds(22, 106, 64, 64);
		contentPane.add(btnTodas);

		btnPendentes = new JButton("");
		btnPendentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ospendentes();
			}
		});
		btnPendentes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPendentes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/servico pendente.png")));
		btnPendentes.setToolTipText("Ordem De Serviço Pendentes");
		btnPendentes.setBorderPainted(false);
		btnPendentes.setBackground(Color.BLACK);
		btnPendentes.setBounds(269, 106, 64, 64);
		contentPane.add(btnPendentes);

		btnEntregues = new JButton("");
		btnEntregues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Osentregues();
			}
		});
		btnEntregues.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEntregues.setIcon(new ImageIcon(Relatorios.class.getResource("/img/serviço feito.png")));
		btnEntregues.setToolTipText("Ordem de Serviço Entregues");
		btnEntregues.setBorderPainted(false);
		btnEntregues.setBackground(Color.BLACK);
		btnEntregues.setBounds(143, 106, 64, 64);
		contentPane.add(btnEntregues);
	}// FIM DO CONSTRUTOR

	DAO dao = new DAO();

	private void TodasOs() {
		Document document = new Document(PageSize.A4.rotate(), 60f, 60f, 50f, 20f);
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Os.pdf"));

			document.open();
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Ordem de Serviço "));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(13);
			PdfPCell col1 = new PdfPCell(new Paragraph("ID Da Ordem De Serviço"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Nome do Cliente"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Modelo Do Console"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Numero De Serie"));
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

			String relClientes = "select idfor,tecnico,modelo,serie,defeito,datacad,solucao,pendencia,total,garantia,nomee,forma,razao from os";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(11));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(10));
					tabela.addCell(rs.getString(9));
					tabela.addCell(rs.getString(12));
					tabela.addCell(rs.getString(13));
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
	}// Fim de Todas As Os

	private void Osentregues() {
		// System.out.println("Teste");

		// criar objeto para construir a página pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Os_Entregues.pdf"));
			document.open();
			// gerar o conteúdo do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add() Serve pra adicionar coisas na linha do PDF.
			document.add(new Paragraph(new Paragraph(formatador.format(data)))); // é pra colocar a data no pdf.
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Ordem De Serviços Entregues"));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(8); // esse 5 siginifica o número de colunas.

			// Cabeçalho da Tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("ID da Ordem de Serviço"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Data de Entrada da OS"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Modelo"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Número de Série"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Serviço Prestado"));
			PdfPCell col6 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col7 = new PdfPCell(new Paragraph("Custo Total Do Serviço "));
			PdfPCell col8 = new PdfPCell(new Paragraph("Nome do Cliente "));

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);
			tabela.addCell(col7);
			tabela.addCell(col8);

			// Acessar o banco de dados
			String relReposicao = "select idfor,date_format(os.datacad,'%d/%m/%Y'),modelo,serie,solucao,pendencia,total,nomee from os inner join clientes on os.id = clientes.id where pendencia = 'resolvido'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relReposicao);
				ResultSet rs = pst.executeQuery();

				// while é uma estrutura de repetição Infinita, ou seja enquanto tiver dados na
				// tabela do bnaod ele vai obter o valor.
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(8));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);

		} finally { // executa o código independente do resultado OK ou não, que serve pra fechar o
					// documento
			document.close();
		}

		// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("OS_Entregues.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}

	}// FIM DAS OS ENTREGUES.

	private void Ospendentes() {
		// System.out.println("Teste");

		// criar objeto para construir a página pdf
		Document document = new Document(PageSize.A4.rotate(), 30f, 30f, 20f, 0f);
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Os_Pendentes.pdf"));
			document.open();
			// gerar o conteúdo do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			// document.add() Serve pra adicionar coisas na linha do PDF.
			document.add(new Paragraph(new Paragraph(formatador.format(data)))); // é pra colocar a data no pdf.
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Ordem De Serviços Pendentes"));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(9); // esse 5 siginifica o número de colunas.

			// Cabeçalho da Tabela
			PdfPCell col1 = new PdfPCell(new Paragraph("ID da Ordem de Serviço"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Data de Entrada da OS"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Modelo"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Número De Série"));
			PdfPCell col5 = new PdfPCell(new Paragraph("Serviço Prestado"));
			PdfPCell col6 = new PdfPCell(new Paragraph("Status"));
			PdfPCell col7 = new PdfPCell(new Paragraph("Justificativa Da Pendência"));
			PdfPCell col8 = new PdfPCell(new Paragraph("Custo Total Do Serviço "));
			PdfPCell col9 = new PdfPCell(new Paragraph("Nome do Cliente "));

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);
			tabela.addCell(col5);
			tabela.addCell(col6);
			tabela.addCell(col7);
			tabela.addCell(col8);
			tabela.addCell(col9);

			// Acessar o banco de dados
			String relReposicao = "select idfor,date_format(os.datacad,'%d/%m/%Y'),modelo,serie,solucao,pendencia,razao,total,nomee from os inner join clientes on os.id = clientes.id where pendencia = 'pendente'";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relReposicao);
				ResultSet rs = pst.executeQuery();

				// while é uma estrutura de repetição Infinita, ou seja enquanto tiver dados na
				// tabela do bnaod ele vai obter o valor.
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(9));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			// Adicionar a tabela ao documento pdf
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);

		} finally { // executa o código independente do resultado OK ou não, que serve pra fechar o
					// documento
			document.close();
		}

		// abrir o documento que foi gerado no leitor padrão de pdf do sistema (PC)
		try {
			Desktop.getDesktop().open(new File("OS_Pendentes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}

	}// FIM DAS OS PENDENTES.

	private void Osclientes() {
		// criar objeto para construir a página pdf
		Document document = new Document();
		// gerar o documento pdf
		try {
			// cria um documento pdf em branco de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("Clientes.pdf"));
			document.open();
			// gerar o conteúdo do documento
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(new Paragraph(formatador.format(data))));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Clientes cadastrados"));
			document.add(new Paragraph(" "));
			// ... Demais conteúdos (imagem, tabela, gráfico, etc)
			PdfPTable tabela = new PdfPTable(4);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("CPF"));
			PdfPCell col4 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			tabela.addCell(col4);

			// Acessar o banco de dados
			String relClientes = "select nome,contato,cpf,email from clientes";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(relClientes);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
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
			Desktop.getDesktop().open(new File("Clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}// FIM DA VIDA
