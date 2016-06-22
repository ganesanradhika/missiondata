package com.missiondata.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.missiondata.model.Task;

/**
 * Performs read-write operation on the input/output files.
 * @author radyog
 *
 */
public class FileHandler {
	
	public List<String> readInput(){
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("input.txt").getFile());
		
		List<String> inputLines = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				inputLines.add(line);
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inputLines;
	}
	
	public void writeOutput(Set<String> uniqueTasks, Map<String,Task> tasks){
		
		ClassLoader classLoader = getClass().getClassLoader();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(classLoader.getResource("output.txt").toURI()))) {
			for (String taskName : uniqueTasks) {
			String result = tasks.get(taskName).getDependentTasks();
			if(result.trim().equals("")){
				continue;
			}
			List<String> lst = Arrays.asList(result.split(" "));

			Collections.sort(lst);
			Set<String> set = new HashSet<>(lst);
			writer.write(taskName + "   ");
			for (String string : set) {
				writer.write(string + " ");
			}
			writer.write("\n");
			}
        } catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
}
