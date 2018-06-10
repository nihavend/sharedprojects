package com.likya.royale;
import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompareUtil {
	
	public static void main(String[] args) throws IOException {

		String fileName = "D:\\dev\\git\\emulation_works\\royale-asjs\\frameworks\\projects\\MXRoyale\\src\\main\\royale";
		String classListFile = "D:\\dev\\git\\emulation_works\\royale-asjs\\frameworks\\projects\\MXRoyale\\src\\main\\royale\\MXRoyaleClasses.as";
	    String apiReportFile = "D:\\dev\\Apache Royale\\ApiReport\\api_report_mx_spark.txt";

		/*
		
		// Path sourcePath = Paths.get(fileName); 
	    //System.out.println("D:\\dev\\git\\emulation_works\\royale-asjs\\frameworks\\projects\\MXRoyale\\src\\main\\royale\\mx\\validators".replaceAll("\\\\", "."));
		
		
	    // System.out.println("*******************************************************");
		 */

		// diffApiUsageWithClassList(classListFile, apiReportFile);
	    diffApiUsageWithFolderList(fileName, apiReportFile);
		
	
	}

	public static void diffApiUsageWithFolderList(String fileName, String apiReportFile) throws IOException {
		
		List<String> filesInFolder = new ArrayList<>();
		filesInFolder = listFilesForFolder(filesInFolder, new File(fileName));
	    List<String> apiUsageList = readApiReportWithFilter("mx.", apiReportFile);

	    apiUsageList.removeAll(filesInFolder);
	    apiUsageList.forEach(System.out::println);
			
	}

	public static void diffApiUsageWithClassList(String classListFile, String apiReportFile) throws IOException {
	
		List<String> filesInFile = readMXRoyaleClasses(classListFile);
	    List<String> apiUsageList = readApiReportWithFilter("mx.", apiReportFile);
	    
	    apiUsageList.removeAll(filesInFile);
	    apiUsageList.forEach(System.out::println);
		
	}
	
	public static void diffFolderListWithClassList(String fileName, String classListFile) throws IOException {
		
		List<String> filesInFolder = new ArrayList<>();
		filesInFolder = listFilesForFolder(filesInFolder, new File(fileName));
	    List<String> filesInFile = readMXRoyaleClasses(classListFile);

	    filesInFolder.removeAll(filesInFile);
	    filesInFolder.forEach(System.out::println);

	}

	public static void diffClassListWithFolderList(String fileName, String classListFile) throws IOException {
		
		List<String> filesInFolder = new ArrayList<>();
		filesInFolder = listFilesForFolder(filesInFolder, new File(fileName));
	    List<String> filesInFile = readMXRoyaleClasses(classListFile);

	    filesInFile.removeAll(filesInFolder);
	    filesInFile.forEach(System.out::println);

	}

	public static List<String> listFilesForFolder(List<String> fileList, final File folder) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileList, fileEntry);
	        } else {
	        	String str = fileEntry.getAbsolutePath();
	        	str = str.replaceAll("\\\\", ".");
	        	str = str.substring(str.indexOf("main.royale") + "main.royale".length(), str.length());
	        	str = str.substring(0, str.indexOf(".as"));
	        	if(str.startsWith(".")) {
	        		str = str.substring(1);
	        	}
	    		fileList.add(str.trim());
	            // System.out.println(str.trim());
	        }
	    }
	    
	    // fileList.forEach(System.out::println);
	    
	    return fileList;
	}

	public void listFilesForFolderLambdaStyle(Path sourcePath) throws IOException {

		ArrayList<String> fileList = new ArrayList<>();
		// https://stackoverflow.com/questions/39683834/how-to-get-the-only-file-names-from-the-folder-in-java8
		// https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
		try (Stream<Path> paths = Files.walk(sourcePath)) {
		    paths
		        .filter(Files::isRegularFile)
		        // .map(Path::getFileName)
		        //.map(p -> p.getFileName().toString())
		        .map(p -> p.getParent().toString().replaceAll("\\\\", "."))
		        .map(s -> s.substring(s.indexOf("main.royale") + "main.royale".length(), s.length()))
		        // .map(s -> s.substring(0, s.indexOf(".")))
		        // .forEach(System.out::println);
		        .forEach(s -> fileList.add(s));
		}
		
		fileList.forEach(System.out::println);
	}
	
	public static ArrayList<String> readMXRoyaleClasses(String classListFile) throws IOException {
		
		ArrayList<String> fileList = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(classListFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	line = line.trim();
		    	if(line.startsWith("import ")) { 
		    		//if(line.startsWith("\t")) {
		    			line = line.substring("import ".length()).split(";")[0].trim();
		    			fileList.add(line.trim());
		    		//}
		    	}		    	
		    }
		    // line is not visible here.
		}
		
		// fileList.forEach(System.out::println);
		
		return  fileList;
	}

	public static void readMXRoyaleClassesLambdaStyle(String classListFile) throws IOException {
		
		// https://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
		try (Stream<String> stream = Files.lines(Paths.get(classListFile))) {
	        stream
	        .filter(s -> s.trim().startsWith("import "))
	        .forEach(System.out::println);
		}	
		
	}
	
	public static ArrayList<String> readApiReportWithFilter(String filter, String classListFile) throws IOException {
		
		ArrayList<String> fileList = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(classListFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	line = line.trim();
		    	if(line.startsWith(filter)) { 
		    		//if(line.startsWith("\t")) {
		    			//line = line.substring("import ".length()).split(";")[0].trim();
		    		line = line.split(",")[0].trim();
		    		line = line.split(":")[0].trim();
		    		fileList.add(line.trim());
		    		//}
		    	}		    	
		    }
		    // line is not visible here.
		}
		
		// fileList.forEach(System.out::println);
		
		// https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist?page=1&tab=votes#tab-top
		List<String> deduped = fileList.stream().distinct().collect(Collectors.toList());
		
		return  new ArrayList<String>(deduped);
	}

	
}
