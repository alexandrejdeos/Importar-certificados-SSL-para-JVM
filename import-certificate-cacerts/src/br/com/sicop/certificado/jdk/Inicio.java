package br.com.sicop.certificado.jdk;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Inicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922800352991573088L;
	private JPanel contentPane;
	private JTextField textFieldCertificate;
	private JTextField textFieldCacerts;
	private String diretorioCertificado = "";
	private String diretorioCacerts = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
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
	public Inicio() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/img/150px-Wave.svg.png")));
		ThemaSO();
		
		setTitle("Importando certificados SSL para a JVM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Certificado SSL");
		lblNewLabel_1.setBounds(24, 27, 75, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cacerts (JVM)");
		lblNewLabel_2.setBounds(24, 70, 75, 14);
		contentPane.add(lblNewLabel_2);
		
		textFieldCertificate = new JTextField();
		textFieldCertificate.setEditable(false);
		textFieldCertificate.setBounds(109, 24, 272, 20);
		contentPane.add(textFieldCertificate);
		textFieldCertificate.setColumns(10);
		
		textFieldCacerts = new JTextField();
		textFieldCacerts.setEditable(false);
		textFieldCacerts.setBounds(109, 67, 272, 20);
		contentPane.add(textFieldCacerts);
		textFieldCacerts.setColumns(10);
		
		JButton btnFindCertificate = new JButton("...");
		btnFindCertificate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("cer","cer");
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Selecione o certificado!");
				jFileChooser.setFileFilter(filter);
				
				int result = jFileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jFileChooser.getSelectedFile();
					setDiretorioCertificado(selectedFile.getAbsolutePath());
					textFieldCertificate.setText(selectedFile.getAbsolutePath());
					textFieldCertificate.setEditable(false);
				}

			}
		});
		btnFindCertificate.setBounds(391, 23, 33, 23);
		contentPane.add(btnFindCertificate);
		
		JButton btnFindCacerts = new JButton("...");
		btnFindCacerts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Selecione o cacerts!");
				String javaHome = System.getenv("JAVA_HOME");
				jFileChooser.setCurrentDirectory(new File(javaHome + "\\jre\\lib\\security"));
				
				int result = jFileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jFileChooser.getSelectedFile();
					setDiretorioCertificado(selectedFile.getAbsolutePath());
					textFieldCacerts.setText(selectedFile.getAbsolutePath());
					textFieldCacerts.setEditable(false);
				}

			}
		});
		btnFindCacerts.setBounds(391, 66, 33, 23);
		contentPane.add(btnFindCacerts);
		
		JButton btnAddCertificate = new JButton("Gerar Arquivo (.bat) para add certificado");
		btnAddCertificate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!textFieldCacerts.getText().isEmpty() && !textFieldCertificate.getText().isEmpty()){
					
					try {
						JFileChooser fc = new JFileChooser();
						int resultado = fc.showSaveDialog(new JFrame());
						if (resultado != JFileChooser.SAVE_DIALOG) {
							File salvarArquivoEscolhido = fc.getSelectedFile();
							Certificado.addCertificado(textFieldCertificate.getText(), textFieldCacerts.getText(), salvarArquivoEscolhido.getPath());
							JOptionPane.showMessageDialog(null, "Arquivo foi salvo com suscesso!: " + salvarArquivoEscolhido.getName() + " " +  salvarArquivoEscolhido.getPath());
						}
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
						
				}else{
					JOptionPane.showMessageDialog(null,"Informe o diretório do cacerts e do certificado para possamos adicionar o mesmo!.", "INFO",2,null);
				}
				
			}
		});
		btnAddCertificate.setBounds(168, 119, 256, 45);
		contentPane.add(btnAddCertificate);
		
		JButton btnListarCertificados = new JButton("Listar Certificados");
		btnListarCertificados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String line;
				if(!textFieldCacerts.getText().isEmpty()){
					
					try {
						Process p = Certificado.listarCertificados(textFieldCacerts.getText());
						BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
						SaidaConsole saida = new SaidaConsole();
						saida.setVisible(true);
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						in.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null,"Informe o diretório do cacerts para a listagem dos certificados.", "INFO",2,null);
				}
			}
		});
		btnListarCertificados.setBounds(24, 119, 121, 45);
		contentPane.add(btnListarCertificados);
	}

	private void ThemaSO() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

	public String getDiretorioCertificado() {
		return diretorioCertificado;
	}

	public String getDiretorioCacerts() {
		return diretorioCacerts;
	}

	public void setDiretorioCacerts(String diretorioCacerts) {
		this.diretorioCacerts = diretorioCacerts;
	}

	public void setDiretorioCertificado(String diretorioCertificado) {
		this.diretorioCertificado = diretorioCertificado;
	}
}
