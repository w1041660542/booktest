package iotest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;

import pojo.Employee;

public class TextFileTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Employee[] staff = new Employee[3];
		staff[0]  = new Employee("xiaoming", 1000.0, 1996, 11, 22);
		staff[1]  = new Employee("xiaolan", 2000.0, 1996, 11, 24);
		staff[2]  = new Employee("xiaozhu", 3000.0, 1996, 1, 22);
		
		try(PrintWriter out = new PrintWriter("employee.dat","utf-8")){
			
			writeData(staff,out);
		}
		
		try(Scanner in = new Scanner(new FileInputStream("employee.dat"),"utf-8")){
			
			Employee[] newstaff = readData(in);
			
			for (Employee e : newstaff) {
				
				System.out.println(e);
			}
		}
			
		
		
		
	}

	private static Employee[] readData(Scanner in) {
		
		int n = in.nextInt();
		in.nextLine();
		Employee[] employees =  new Employee[n];
		for (int i = 0; i < n; i++) {
			employees[i] = readEmployee(in);
		}
		return employees;
	}

	private static Employee readEmployee(Scanner in) {
	    String line = in.nextLine();
	    String[] tokens = line.split("\\|");
	    String name = tokens[0];

	    double salary = Double.parseDouble(tokens[1]);
	    int year = Integer.parseInt(tokens[2]);
	    int month = Integer.parseInt(tokens[3]);
	    int day = Integer.parseInt(tokens[4]);
	    return new Employee(name,salary,year,month,day);
	    
	
	}

	private static void writeData(Employee[] employees, PrintWriter out) {
		
		out.println(employees.length);
		for (Employee employee : employees) {
			writeEmployee(out,employee);
		}
		
	}

	private static void writeEmployee(PrintWriter out, Employee employee) {
		
		out.println(employee.getName()+"|"+employee.getSalary()+"|"+employee.getYear()+"|"+
		employee.getMonth()+"|"+employee.getDay());

	}

}
