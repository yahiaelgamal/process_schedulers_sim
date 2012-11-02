import java.util.ArrayList;
import java.util.HashMap;

public abstract class Simulation
{
	
	int CURRENT_TIME; 
	ArrayList<Process> PROCESSES = new ArrayList<Process>();
	HashMap<String, Integer> USER_PRIORITIES_HASH = new HashMap<String, Integer>();
	Process CURRENT_PROCESS = null;
	
	abstract void simulate();
	
	public static void main(String[] args)
	{
//		Simulation sim = new MultilevelFeedback();
//		Simulation sim = new LotterySimulation();
		Simulation sim = new FairShare();
		sim.USER_PRIORITIES_HASH.put("user1", 1);
		sim.USER_PRIORITIES_HASH.put("user2", 2);
		sim.USER_PRIORITIES_HASH.put("user3", 4);
		sim.USER_PRIORITIES_HASH.put("user4", 1);
		sim.USER_PRIORITIES_HASH.put("user5", 1);
		
		Process p1 = new Process(0, 15, "user1");
		Process p2 = new Process(1, 18, "user2");
		Process p3 = new Process(5, 10, "user3");
		Process p4 = new Process(7,  2, "user4");
		Process p5 = new Process(12, 4, "user5");
		
		sim.PROCESSES.add(p1);
		sim.PROCESSES.add(p2);
		sim.PROCESSES.add(p3);
		sim.PROCESSES.add(p4);
		sim.PROCESSES.add(p5);
		 
		sim.simulate();
	}
}

