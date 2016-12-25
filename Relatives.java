// Elizabeth Koshelev
// COSI 12B, Spring 2015 
// Programming Assignment #7, 4/11/16
// This program uses recursion to trace the lineage of a specified person from a file.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Relatives {
	//The main method gains information from the users.
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("What is the input file?");
		Scanner console = new Scanner(System.in);
		Scanner input = new Scanner(new File(console.next()));
		String name = "";
		List<String> fullList = new ArrayList<String>(); 
		fullList = allWords(input);
		while (!name.equals("quit")){
			System.out.println("Person's name ('quit' to end)?");
			Scanner console2 = new Scanner(System.in);
			name = console2.nextLine();
			if (!name.equals("quit")){
				System.out.println("Ancestors:");
				int ind = 1;
				printAncestorMap(makeParents(fullList), name, ind); //This calls the method that prints the ancestors.
				System.out.println("Descendents:");
				ind = 1;
				makeChildren(fullList);
				printChildMap(makeChildren(fullList), name, ind); //This calls the method that prints the descendents.
			}
		}
	}
	//This method takes as a parameter the file scanner and returns an arraylist of all the words.
	public static List<String> allWords(Scanner in) {
		List<String> allWords = new ArrayList<String>(); 
		while (in.hasNextLine()) { 
			String word = in.nextLine();
			allWords.add(word); 
			}
		return allWords;
	}
	//This method returns a map of all the ancestors from the arraylist.
	public static Map<String, ArrayList<String>> makeParents(List<String> fullList) {//Map<String, ArrayList<String>>
		int split = fullList.indexOf("END");
		List<String> allNames = new ArrayList<String>(); 
		List<String> allRoots = new ArrayList<String>(); 
		allNames =  fullList.subList(0, split);//Here I split the arraylist into two arraylists: the unique names and all the relationships.
		allRoots = fullList.subList(split+1, fullList.size()-1);
		Map<String, ArrayList<String>> parentMap = new HashMap<String, ArrayList<String>>();
		String name = "";
		String mom = "";
		String dad = "";
		int index = 0;
		ArrayList<String> parentPeople = new ArrayList<String>();
		for (int i=0; i<allRoots.size()/3; i++){ //This maps every person to an arraylist of their parents.
			ArrayList<String> parents = new ArrayList<String>();
			index = i*3;
			name = allRoots.get(index);
			parentPeople.add(name);
			mom = allRoots.get(index+1);
			dad = allRoots.get(index+2);
			parents.add(mom);
			parents.add(dad);
			parentMap.put(name, parents);
		}
		for (int i=0; i < allNames.size(); i++){
			if (!parentPeople.contains(allNames.get(i))){//This adds everyone without parents to the map by comparing names in the relationship list with unique names.
				name=allNames.get(i);
				ArrayList<String> parents = new ArrayList<String>();
				parents.add("");
				parents.add("");
				parentMap.put(name, parents);
			}
		}
		return parentMap;
	}
	//This method is very similar to the ancestor mapping method above, but maps the descendents instead.
	public static Map<String, ArrayList<String>> makeChildren(List<String> fullList) {
		int split = fullList.indexOf("END");
		List<String> allNames = new ArrayList<String>(); 
		List<String> allRoots = new ArrayList<String>(); 
		allNames =  fullList.subList(0, split);
		allRoots = fullList.subList(split+1, fullList.size()-1);
		Map<String, ArrayList<String>> childMap = new HashMap<String, ArrayList<String>>();
		String name = "";
		String mom = "";
		String dad = "";
		String childname= "";
		int index = 0;
		for (int i=0; i < allNames.size(); i++){
			ArrayList<String> children = new ArrayList<String>();
			name = allNames.get(i);
			for (int i1=0; i1<allRoots.size()/3; i1++){
				index = i1*3;	
				childname = allRoots.get(index);
				mom = allRoots.get(index+1);
				dad = allRoots.get(index+2);
				if ( name.equals(mom) || name.equals(dad))
				children.add(childname);
			}
			childMap.put(name, children);
		}
		return childMap;
	}
	//This method recursively prints the ancestors.
	public static void printAncestorMap(Map<String, ArrayList<String>> parentMap, String name, int ind){
		indentation(ind); //This calls the indentation method to print the correct spacing.
		System.out.println(name);
		String parent = "";
		ind++;
		for (int i=0; i<2; i++){
			parent = parentMap.get(name).get(i);
			if (!parent.equals("") && !parent.equals("unknown") ){
				printAncestorMap(parentMap, parent, ind); //Here is the recursive call.
			}	
		}
		ind--;
	}
	//This method prints all the children.
	public static void printChildMap(Map<String, ArrayList<String>> childMap, String name, int ind){
		indentation(ind);
		System.out.println(name);
		String child = "";
		ind++;
		for (int i=0; i<childMap.get(name).size(); i++){
			child = childMap.get(name).get(i);
			printChildMap(childMap, child, ind); 
	}
		ind--;
	}
	//This method keeps track of the indentation during the recursion.
	public static void indentation(int indent){
		for (int i=0; i< indent; i++){
			System.out.print("     ");
		}
	}
}