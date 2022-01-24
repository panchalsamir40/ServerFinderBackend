package com.server;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SolveNoSuchFileException {
	public static void main(String[] args) {
		int i = 0;
		String filename = "result.csv";
		Path pathToFile = Paths.get(filename);
		System.out.println(pathToFile.toAbsolutePath());
	}
}
