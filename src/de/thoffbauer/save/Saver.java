package de.thoffbauer.save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.thoffbauer.ui.swing.Session;
import de.thoffbauer.utils.Weight;

public class Saver {
	
	public static String load(File file) throws FileNotFoundException {
		if(!file.getName().endsWith(".sca")) {
			file = new File(file.getAbsolutePath() + ".sca");
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		try {
			line = br.readLine();
			br.close();
			if(line == null) {
				return null;
			} else {
				return line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void save(Session session, File file) throws IOException {
		if(!file.getName().endsWith(".sca")) {
			file = new File(file.getAbsolutePath() + ".sca");
		}
		ArrayList<Weight> weights = session.getWeights();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < weights.size(); i++) {
			Weight w = weights.get(i);
			boolean allowed[] = w.getAllowed();
			sb.append(w.getWeight()).append(" ");
			sb.append(allowed[0]).append(" ");
			sb.append(allowed[1]).append(" ");
			sb.append(allowed[2]).append(" ");
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}

}
