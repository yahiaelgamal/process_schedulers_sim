import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;


public class FairShare extends Simulation
{

	TreeMap<String, Queue<Process>> USER_PROCESSES_HASH 
								= new TreeMap<String, Queue<Process>>();
	int currentUserIndex = 0;
	String currentUserName = null;
	int currentStreak = 0; // will switch users with this is equal to priority
	static int QUANTUM = 2;
	
	public FairShare() {
	}

	void simulate()
	{
		// assing percentages for usrs
		if (PROCESSES.isEmpty())
			return ;
		
		checkArrival();
		if (CURRENT_PROCESS != null && CURRENT_PROCESS.runTime != 0 
				&& currentStreak != USER_PRIORITIES_HASH.get(currentUserName)*QUANTUM)
		{
			if (CURRENT_PROCESS.runTime == 0) {
				Queue<Process> temp = USER_PROCESSES_HASH.get(currentUserName);
				temp.poll();
				PROCESSES.remove(CURRENT_PROCESS);
				CURRENT_PROCESS = temp.peek();
				
			}else if (currentStreak%QUANTUM == 0) {
				CURRENT_PROCESS = USER_PROCESSES_HASH.get(currentUserName).poll();
				USER_PROCESSES_HASH.get(currentUserName).add(CURRENT_PROCESS);
			} 
			run();
		} else { // if the streak is over or user is null
			setNextProcess();
			if (currentUserName != null)
				run();
		}
		CURRENT_TIME++;
		simulate();

	}
	
	@SuppressWarnings("unchecked")
	private void setNextProcess()
	{
		Map.Entry[] it = new Map.Entry[USER_PROCESSES_HASH.size()];
		USER_PROCESSES_HASH.entrySet().toArray(it);
		
		int i = 0;
		int offset = 0;
		while (offset < USER_PROCESSES_HASH.size()
				&& !((String) it[offset].getKey()).equals(currentUserName))
		{
			offset++;
		}
		offset++;
		
		currentUserName = null;
		while (i < USER_PROCESSES_HASH.size()) {
			Map.Entry tempEntry = it[(i+offset) % USER_PROCESSES_HASH.size()];
			if (( (Queue<Process>)(tempEntry.getValue()) ).size() != 0)
			{
				currentStreak = 0;
				
				currentUserIndex = (i+offset)%USER_PROCESSES_HASH.size();
				
				currentUserName = (String) tempEntry.getKey();
				
				CURRENT_PROCESS =  ((Queue<Process>)tempEntry.getValue()).poll();
				((Queue<Process>) tempEntry.getValue()).add(CURRENT_PROCESS);
				break;
			}
			i++;
		}
	}
	
	private void run() {
		System.out.println("At slot " + CURRENT_TIME + " process " + CURRENT_PROCESS);
		CURRENT_PROCESS.runTime--;
		currentStreak++;
		if (CURRENT_PROCESS.runTime == 0) { 
			PROCESSES.remove(CURRENT_PROCESS);
			USER_PROCESSES_HASH.get(CURRENT_PROCESS.userName).remove(CURRENT_PROCESS);
		}
	}

	private void addProcess(Process p)
	{
		if (USER_PROCESSES_HASH.containsKey(p.userName)) {
			USER_PROCESSES_HASH.get(p.userName).add(p);
		} else
		{
			Queue<Process> temp = new LinkedList<Process>();
			temp.add(p);
			USER_PROCESSES_HASH.put(p.userName, temp);
		}
	}

	@SuppressWarnings("unchecked")
	public void checkArrival() {
		for (Process p : PROCESSES) {
			if (p.arrivalTime == CURRENT_TIME) {
				addProcess(p);
			}
		}
	}
	
	public void printQueues(){
		System.out.println("--------------------");
	    Iterator it = USER_PROCESSES_HASH.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	    }
		System.out.println("--------------------");
	}

}
