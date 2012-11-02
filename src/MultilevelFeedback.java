import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MultilevelFeedback extends Simulation
{
	int currentStreak; // This reflects the number of time slices which 
					// the current queue has been active for. 
	
	int currentActiveQueue; // This reflects the index of the current
						// queue in the qs array. 
	
	// Feedback queues.
	Queue<Process> q1 = new LinkedList<Process>(); // highest prio
	Queue<Process> q2 = new LinkedList<Process>();
	Queue<Process> q3 = new LinkedList<Process>();
	Queue<Process> q4 = new LinkedList<Process>(); // lowest prio
	
	@SuppressWarnings("unchecked")
	// Array of queus (from high to low) 
	Queue qs[] = {q1, q2, q3, q4};
	
	// This is a list of available processes. 
	ArrayList<Process> availableProcs = new ArrayList<Process>();
	
	/**
	 * This is main the method. Takes no parameters but it modifies 
	 * all the variables in the both classes (Simulation.java and 
	 * MultilevelFeedback.java) 
	 * @author yahiaelgamal
	 */
	void simulate()
	{
		// Checks if there are any available processes. if not, 
		// it returns and ends the simulation. 
		if (PROCESSES.isEmpty())
			return;
		
		// Check the arrival for new processes.
		checkArrival();
//		printQueues();
		
		// Loops on all queues (in a descending order) till a process is met.
		// once a process is met, first it checks if it's not the same as the
		// CURRENT_PROCESS and it's not in the same activeQueue (same process in 
		// the same queue will be handled whenever a process finsihes its runtim) 
		for (int i = 0; i < 4; i++) {
			Process temp = (Process) qs[i].peek();
			if (temp != null)
			{
				// if it's not the same process. or the same but in a new queue
				if (CURRENT_PROCESS != temp || currentActiveQueue != i) { 
//					System.out.println("NEW PROCESS");
					currentStreak = 0;
					CURRENT_PROCESS = temp;
				}
				currentActiveQueue = i;
				break;
			}
		}
		
		// after setting the CURRENT_PROCESS, run. 
		run();
		
		// If the process has finished execution, it gets removed from both
		// available processes and PROCESS list (Process is all processes in 
		// that will arrive in the future, but available resources are processes
		// that are currently ready and waiting for execution.
		if (CURRENT_PROCESS.runTime == 0) {
			availableProcs.remove(CURRENT_PROCESS);
			PROCESSES.remove(CURRENT_PROCESS);
			qs[currentActiveQueue].poll();
		}else {
			// checks if the currentActiveQueue has been active for 
			// the maximum number of slices for that queues. 
			if (currentStreak == Math.pow(2, currentActiveQueue)) {
//				System.out.println("QUANTUM HAS FINSIHED");
				if (currentActiveQueue != 3) {
					qs[currentActiveQueue+1].add(qs[currentActiveQueue].poll());
				} else {
					// If it was the lowest queue, apply round-robin
//					System.out.println("at lowest queue");
					qs[currentActiveQueue].add(qs[currentActiveQueue].poll());
				}
			
			}
		}
		
		CURRENT_TIME++;
//		System.out.println("----------------------- " + CURRENT_PROCESS
//				+ " current Queue is " + currentActiveQueue
//				+ " current Qunatum " + currentStreak 
//				+ " current time is " + CURRENT_TIME +  "\n\n");
		// If the streak is over, reset it. 
		if (currentStreak == Math.pow(2, currentActiveQueue)) 
			currentStreak = 0;
		simulate();
	}
		

	@SuppressWarnings("unchecked")
	// This method checks for the arrival of the processes.   
	public void checkArrival() {
//		System.out.println("Checking arrival of new processes.");
		for (Process p : PROCESSES) {
			if (p.arrivalTime == CURRENT_TIME) {
//				System.out.println(p + " has arrived at "  + CURRENT_TIME);
				availableProcs.add(p);
				qs[0].add(p);
				currentStreak=0;
				
//				System.out.println("Added " + p + " to "  + availableProcs);
			}
		}
//		System.out.println("avaialble process are " + availableProcs);
	}
	
	// This simply modifies runtime and streak of the current process and 
	// queue respectively 
	public void run() {
		if (CURRENT_PROCESS == null)
			return;
		System.out.println("At slot " + CURRENT_TIME + " process " + CURRENT_PROCESS);
		CURRENT_PROCESS.runTime--;
		currentStreak++;
//		System.out.println("remaining rutime for "  + 
//				CURRENT_PROCESS + " is " + CURRENT_PROCESS.runTime);
	}
	
	public void printQueues() {
		for (Queue q : qs) {
			System.out.println(q);
		}
	}
}
