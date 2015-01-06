import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

/**
* Implementation of Algorithm to take list of employees and output optimal utility
*
* @author Rory McGloin
*
* @version Northeastern Fall Semester 2014
*/
public class MyProject {

  /**
  * Reads file from stdin, proccesses list of employees and runs algorithm
  */
  public static void main(String[] args) {
    Scanner input = null;
    input = new Scanner(System.in);
    int utility = 0;
    int size = Integer.parseInt(input.next());
    int targets = Integer.parseInt(input.next());
    Employee[] employees = init(input, size);
    ArrayList[] children = new ArrayList[size];
    boolean[] isleaf = new boolean[size];
    for (int i = 0; i < size; i++) {
      isleaf[i] = true;
      children[i] = new ArrayList();
      if (i > 0) {
        employees[i].pot_val = employees[i].init_val + employees[employees[i].pid - 1].pot_val;
        isleaf[employees[i].pid - 1] = false;
        children[employees[i].pid - 1].add(i);
      }
    }
    int[] leaves = new int[size];
    int leafnum = 0;
    for (int i = 0; i < size; i++) {
      if (isleaf[i]) {
        leaves[leafnum] = i;
        leafnum++;
      }
    }
    while (targets > 0) {
      utility += influence(employees, leaves, leafnum, size, children);
      targets--;
    }
    try {
      BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
      log.write(Integer.toString(utility) + "\n");
      log.flush();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static int influence(Employee[] employees, int[] leaves, int leafnum, int size, ArrayList[] children) {
    int highvalue = 0;
    int index = 0;
    for (int i = 0; i < leafnum; i++) {
      int temp = employees[leaves[i]].pot_val;
      if (temp > highvalue) {
        highvalue = temp;
        index = leaves[i];
      }
    }
    reset(employees, index, children);
    return highvalue;
  }

  public static void reset(Employee[] employees, int index, ArrayList[] children) {
    int child = 0;
    while (employees[index].pid != 0) {
      if (employees[index].pid == 1) {
        child = index;
      }
      employees[index].pot_val = 0;
      employees[index].init_val = 0;
      int temp = employees[index].pid;
      employees[index].pid = 1;
      index = temp - 1;
    }
    add(employees, child, children);
  }

  public static void add(Employee[] employees, int index, ArrayList[] children) {
    employees[index].pot_val = employees[index].init_val + employees[employees[index].pid - 1].pot_val;
    for (Object i: children[index])
    add(employees, (int) i, children);
  }

  public static Employee[] init(Scanner file, int size) {
    Employee[] employees = new Employee[size];
    int i = 0;
    file.nextLine();
    while (file.hasNext()) {
      String temp = file.nextLine();
      String[] ints = temp.split(" ");
      employees[i] = new Employee(Integer.parseInt(ints[1]), Integer.parseInt(ints[2]), 0);
      i++;
    }
    return employees;
  }
}
