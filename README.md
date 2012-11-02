##Multilevel feedback :
There are 4 queues which are ordered according to priorities. Each queue has it's own quantum and is the quantum is doubles for each step in the queues. Whenever the quantum is over, the process is moved to the next queue. In the lowest queue 

##Lottery scheduling :  
Each process have a number of tickets and each ticket is assigned to only one process. Priorities are simulated by giving more tickets to processes with higher priorities. 

##Fair-share : 
According to the priorities, each user have a percentage of the CPU time. The more the priority the more CPU time will be assigned for this user.The processes of each user are simulated using the round robin algorithm.	

------

##Main Classes 

Class Simulation
An abstract class to be extended by all simulation. 

Class Process
It contains basic information about processes like name, runtime, arrivalTime, userName


FairShareSimulation 
this class contains the procedures main methods to simulate scheduling procedures of the fair share policy. 

MultilevelFeedback 
this class contains the procedures main methods to simulate scheduling procedures of the Multi-level feedback policy. 

LotterySimulation
this class contains the procedures main methods to simulate scheduling procedures of the Lottery policy. 


-------

##How simulation work

Based on the scheduling policy, the flow will differ. 

##Multi level feedback :
Loops on all queues (in a descending order) till a process is met. once a process is met, first it checks if it's not the same as the CURRENT_PROCESS and it's not in the same activeQueue (same process in  the same queue will be handled whenever a process finishes its runtime) 

##Lottery : 
Whenever a process arrives, a ticket will be assigned in both Maps (ticket => process and process => list_of_tickets). Each cycle, the scheduler chooses a random ticket and run the corresponding process. 

##FairShare :
The scheduler checks (1) if there is a current process, and the current streak hasn't exceeded the quantum of the user.  If all is satisfied, then the scheduler checks if the runt time of the current process is not equal to 0, if it is equal to zero, this process gets removed from the process list and the hash. If not, the scheduler does another check for to apply round robin for the current user. If condition (1) is not satisfied, the scheduler gets the next process and in all cases, it runs. 





--------

Open simulation.java. uncomment the desired simulation, compile and run. 