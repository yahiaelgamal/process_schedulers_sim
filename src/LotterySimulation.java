import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class LotterySimulation extends Simulation {
	static int QUANTUM = 2;
	
	// This is a mapping between tickets number to processes 
	Map<Integer, Process> lotteryTickets = new HashMap<Integer, Process>();
	
	// This is a mapping between processes and an array list of tickets. 
	Map<Process, ArrayList<Integer>> 
				lotteryTicketsRev = new HashMap<Process, ArrayList<Integer>>();
	
	// this is the ticket counter to set the tickets for each new ticket.
	int ticketCounter = 1;
	
	// This indicates the number of time cycles that are done. 
	int currentQuantum = 0;
	ArrayList<Process> availableProcs  = new ArrayList<Process>();
	
	
	/**
	 * @author yahiaelgamal
	 */
	public void simulate()
	{
		if (PROCESSES.isEmpty())
			return;
		
		checkArrival();
		if (CURRENT_PROCESS != null) {
			if (CURRENT_PROCESS.runTime == 0) {
				availableProcs.remove(CURRENT_PROCESS);
				PROCESSES.remove(CURRENT_PROCESS);
				removeAllTickets(CURRENT_PROCESS);
				if (PROCESSES.isEmpty())
					return;

				CURRENT_PROCESS = chooseNewProcess();
				run();
			} else {
				if (currentQuantum == QUANTUM) {
					CURRENT_PROCESS = chooseNewProcess();
					run();
				} else {
					run();
				}
			}
		} else {
			if (!availableProcs.isEmpty()) {
				CURRENT_PROCESS = chooseNewProcess();
				run();
			}
		}
		CURRENT_TIME++;
		
		simulate();
	}
	
	private void removeAllTickets(Process p)
	{
		ArrayList<Integer> tickets = lotteryTicketsRev.get(p);
		for (Integer i : tickets) {
			lotteryTickets.remove(i);
		}
	}

	private Process chooseNewProcess()
	{
		if (availableProcs.isEmpty())
		{
			return null;
		}
		Process temp = null;
		int ticket = 0; 
		while (temp == null) {
			ticket = chooseTicket();
			currentQuantum = 0;
			temp = lotteryTickets.get(ticket);
		}
		
		return temp;

	}

	public void checkArrival() {
		for (Process p : PROCESSES) {
			if (p.arrivalTime == CURRENT_TIME) {
				availableProcs.add(p);
				int prio = this.USER_PRIORITIES_HASH.get(p.userName);
				addNTickets(prio, p);
			}
		}
	}

	public void run() {
		if (CURRENT_PROCESS == null)
			return;
		System.out.println("At slot " + CURRENT_TIME + " process " + CURRENT_PROCESS);

		CURRENT_PROCESS.runTime--;
		currentQuantum++;
	}
	

	public int chooseTicket() {
		return (int) (Math.random() * (ticketCounter+1));
	}
	
	public void addNTickets(int n, Process p) {
		for (int i = 0; i < n; i++)  {
			lotteryTickets.put(ticketCounter, p);
			ArrayList<Integer> temp = lotteryTicketsRev.get(p);
			if (temp == null) {
				temp = new ArrayList<Integer>();
				lotteryTicketsRev.put(p, temp);
			}
			temp.add(ticketCounter++);
		}
	}
}

