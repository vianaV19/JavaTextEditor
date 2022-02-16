package com.fileditor;

import java.io.*;

import java.nio.file.Files;
import java.util.ArrayList;
public final class FileModify {
	private File file;
	private String filename;
	
	public String getCaminho() {
		return file.getPath();
	}
	
	public String getFilename() {
		return filename;
	}


	public FileModify(String caminho, String format) {
		filename = caminho+"."+format;
		file = new File(filename);
		
	}
	public FileModify(String caminho) {
		file = new File(caminho);
	}
	
	private boolean validate() {
		return file.exists();
	}
	
	public boolean createFile() throws Exception{
		if(!validate()) {
			file.createNewFile();
			return true;
		}else {return false;}
	}
	
	public void Save(String content) throws Exception{
	
			FileWriter w = new FileWriter(file.getPath());
			w.write(content);
			w.close();
		
	}
	public ArrayList<String> Load() throws Exception{
		 return (ArrayList<String>) Files.readAllLines(file.toPath());	
	}
	
	public void Delete() throws Exception{
		if(validate()) {
			if(file.delete()) {
				System.out.print("Arquivo excluido com sucesso!");
			}else {
				System.out.print("Falha ao exlcuir arquivo!");
			}
		}else{
			System.out.println("Arquivo não ou existe ou diretorio esta incorreto!");
		}
	}
	@Override
	public String toString() {
		return this.getClass().toString();
	}
}
