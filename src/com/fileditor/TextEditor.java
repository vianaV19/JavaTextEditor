package com.fileditor;
import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public final class TextEditor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  final JTextArea content = new JTextArea();
	private  final JScrollPane pane = new JScrollPane(content);
	private final JMenuBar menu = new JMenuBar();
	private String currentDirectory;
	public TextEditor() {
		Design();
		OrganizatingComponents();
		
	}
	private void OrganizatingComponents() {
		add(menu, BorderLayout.NORTH);
		
		add(pane, BorderLayout.CENTER);
	}
	//Define o Design
	private void Design() {
		setTitle("Java Text Editor (JTE)");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 900);
		setVisible(true);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		Font f = new Font("arial", 23, 20);
		
		content.setLineWrap(true);
		content.setFont(f);
		
		JMenu options = new JMenu("Opções");
		JMenu archive = new JMenu("Arquivo");
		JMenuItem newArchive = new JMenuItem("Novo");
		JMenuItem saveas = new JMenuItem("Salvar como");
		JMenuItem save = new JMenuItem("Salvar");
		JMenuItem open = new JMenuItem("Abrir");
		options.add(archive); 
		archive.add(newArchive); archive.add(save); archive.add(open); archive.add(saveas);
		
		MenuItemEvents(newArchive, saveas, open, save);
		
		menu.add(options);

	}
	//Define os eventos 
	private void MenuItemEvents(JMenuItem newArchive, JMenuItem saveas, JMenuItem open, JMenuItem save) {
		newArchive.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new TextEditor();
			}
		});
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(currentDirectory != null) {
					SaveEvent();
				}else {
					SaveasEvent(false);
				}
			}
		});
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					open();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Falha ao Abrir o Arquivo!\n"+ex.getStackTrace(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		saveas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SaveasEvent(true);
			}
		});
	}
	//Abrir arquivo
	private void  open() throws Exception {
		final JFileChooser search = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JTE Files", "jte","txt");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("TXT Files", "txt");
		search.setFileFilter(filter);
		search.setFileFilter(filter2);
		int response = search.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION) {
			String directory = search.getSelectedFile().getAbsolutePath();
			currentDirectory = directory;
			FileModify file = new FileModify(directory);
			String lineBuf = "";
			for(String line : file.Load()) {
				 lineBuf += line + "\n";
			}
			content.setText(lineBuf);
		}
	}
	//Salvar como
	private boolean SaveasEvent(boolean bySaveas) {
		try {
			final JFileChooser search = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JTE Files", "jte");
			FileNameExtensionFilter filter2 = new FileNameExtensionFilter("TXT Files", "txt");
			search.setFileFilter(filter);
			search.setFileFilter(filter2);
			if(bySaveas) {search.setDialogTitle("Salvar Como");}else{search.setDialogTitle("Salvar");}
			int response = search.showSaveDialog(null);
			if(response == JFileChooser.APPROVE_OPTION) {
				String directory = search.getSelectedFile().getAbsolutePath();
				System.out.print(directory);
				String format =  search.getFileFilter().toString().contains("JTE") ? "jte" : "txt";
				FileModify file = new FileModify(directory, format);
				currentDirectory =  bySaveas ? null : file.getFilename();
				if(file.createFile()) {
					file.Save(content.getText());
				}else {
					int dialog = JOptionPane.showConfirmDialog(null, "Este arquivo já existe! Deseja Substitui-lo", "Aviso!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if(dialog == JOptionPane.OK_OPTION) {
						file.Save(content.getText());
					}
				}
			}
			return true;
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Falha ao Salvar o Arquivo!\n"+ex.getStackTrace(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	//Salvar
	private boolean SaveEvent() {
		try {
			FileModify file = new FileModify(currentDirectory);
			file.Save(content.getText());
			return true;
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Falha ao Salvar o Arquivo!\n"+ex.getStackTrace(), "Error!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
}
