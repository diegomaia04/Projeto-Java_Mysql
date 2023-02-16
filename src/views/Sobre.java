package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Sobre extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel Sobre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre frame = new Sobre();
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
	public Sobre() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/About.png")));
		setTitle("Sobre");
		setBounds(100, 100, 475, 331);
		Sobre = new JPanel();
		Sobre.setBackground(Color.WHITE);
		Sobre.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Sobre);
		Sobre.setLayout(null);

		JEditorPane dtrpnOlMeuNome = new JEditorPane();
		dtrpnOlMeuNome.setFont(new Font("Tahoma", Font.BOLD, 12));
		dtrpnOlMeuNome.setText(
				"  Projeto utilizando Java e MYSQL para um sistema de Assistencia técnicas de VideoGames, incluindo ordem de serviço, gereciamento de usuários, clientes e relatórios. ");
		dtrpnOlMeuNome.setEditable(false);
		dtrpnOlMeuNome.setBounds(10, 106, 340, 131);
		Sobre.add(dtrpnOlMeuNome);

		JLabel lblNewLabel = new JLabel("Versão 1.0");
		lblNewLabel.setBounds(395, 58, 84, 14);
		Sobre.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Autores do Sistema: Diego Maia e Bruno Henrique");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(20, 242, 291, 39);
		Sobre.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(
				Sobre.class.getResource("/img/6646622_console_controller_games_joystick_video_icon.png")));
		lblNewLabel_3.setBounds(385, 213, 64, 68);
		Sobre.add(lblNewLabel_3);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Sobre.class.getResource("/img/MIT.png")));
		lblNewLabel_1.setBounds(385, 4, 73, 52);
		Sobre.add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("Projeto para Assitência Técnica");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(46, 4, 304, 33);
		Sobre.add(lblNewLabel_4);

	}
}