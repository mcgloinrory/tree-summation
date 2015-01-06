/**
* Implementation of Employee Object used in Algorithm
*
* @author Rory McGloin
*
* @version Northeastern Fall Semester 2014
*/
public class Employee {
  int pid;      // ID of parent
  int init_val; // Initial utility value
  int pot_val;  // Potential utility value if influenced

  public Employee(int p, int iv, int pv) {
    this.pid = p;
    this.init_val = iv;
    this.pot_val = pv;
  }

// Used in Debugging
  public String toString() {
    return "pid: " + this.pid + " init_val: " + this.init_val + " pot_val: " + this.pot_val;
  }
}
