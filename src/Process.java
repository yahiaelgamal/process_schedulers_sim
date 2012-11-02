class Process implements Comparable<Process>
{
	public String name;
	public int arrivalTime;
	public int runTime;
	public String userName;
	static int counter = 1;
	
	public Process(int aTime, int rTime, String userName) {
		this.name = "PROCESS " + counter++;
		this.arrivalTime = aTime;
		this.runTime = rTime;
		this.userName = userName;
	}
	
	public int compareTo(Process p)
	{
		return this.runTime - p.runTime;
	}
	
	public String toString() {
		return this.name /*+ "  runTime: " + runTime + "  Arrival: " 
		+ arrivalTime + "  User: " + this.userName*/; 
	}
}
