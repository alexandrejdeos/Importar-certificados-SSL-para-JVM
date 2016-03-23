package br.com.sicop.certificado.jdk;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class SaidaConsole extends JFrame {
	private static final long serialVersionUID = 1L;
    JTextArea textArea = new JTextArea();
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SaidaConsole() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		textArea.setEditable(false);
        textArea.setRows(20);
        textArea.setColumns(50);        
        getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        pack();
        setVisible(true);
        
        MessageConsole mc = new MessageConsole(textArea);
        mc.redirectOut();
        mc.redirectErr(Color.RED, null);
        //mc.redirectErr();
	}
}
