import nlvm.math3d.*;

public class AStar {

	public static Node[] findFastestRoute(Node[] n, int nodeCount, int source, int destination){
		
		Node startingNode = n[source];
		Node endingNode = n[destination];
		int current = source;
		double[] localGoal = new double[nodeCount];
		double[] globalGoal = new double[nodeCount];
		boolean[] visited = new boolean[nodeCount];
		Node[] parents = new Node[nodeCount];
		
		int[] nodesToTest = new int[1000];
		int nodeTestCount = 1;
		nodesToTest[0] = source;
		
		//Setup goals
		for(int i = 0; i < nodeCount; i++){
		
			if(i == startingNode){
				localGoal[i] = 0; 
				globalGoal[i] = Triangulation.distance(n[current], endingNode);
				discovered[i] = true;
			}else{
				localGoal[i] = 1000000;
				globalGoal[i] = 1000000;
				discovered[i] = false;
			}

		}
		
		while(current != destination){
			
			//Remove current node from testing pool
			visited[current] = true;
			nodeTestCount--;
			
			//Test connections
			for(int i = 0; i < current.connections; i++){
				
				int nodeC = getNodeFromArray(n, nodeCount, current.connection[i]);
				if(!visited[nodeC]){
					
					nodesToTest[nodeTestCount] = nodeC;
					nodeTestCount++;
					
					if(localGoal[current] + current.connectionDistance[i] < localGoal[nodeC]){
						
						parents[nodeC] = current;
						localGoal[nodeC] = localGoal[current] + current.connectionDistance[i];
						globalGoal[nodeC] = localGoal[nodeC] + Triangulation.distance(n[current], endingNode);
						
					}
					
				}
				
			}
			
			nodesToTest = sortList(nodesToTest);
			
			current = nodesToTest[nodeTestCount];
			
		}
		
		int back = destination;
		int backIndex = 0;
		
		Node[] pathTemp = new Node[100];
		
		while(back != source){
			
			pathTemp[backIndex] = back;
			backIndex++;
			back = parents[back];
			
		}
		
		Node[] path = new Node[backIndex];
		
		for(int i = 0; i < backIndex; i++){
			
			path[i] = pathTemp[i];
			
		}
		
		return path;
		
	}
	
	public static int[] sortList(int[] list){
		
		int count = 0;
		int[] newList = new int[1000];
		for(int i = 0; i < list.length; i++){
			
			boolean inserted = false;
			
			for(int j = 0; j < count; j++){
			
				if(list[i] > newList[j]){
					
					newList = insertIntoArray(newList, j, list[i]);
					count++;
					
				}
			
			}
			
			if(!inserted){
				
				newList[count] = list[i];
				count++;
				
			}
			
		}
		
	}
	
	public static int[] insertIntoArray(int[] arr, int index, int value){
		
		int[] out;
		
		for(int j = 0; j < index; j++){
			
			out[j] = arr[j];
			
		}
		
		for(int j = index; j < arr.length; j++){
			
			out[j+1] = arr[j];
			
		}
		
		out[index] = value;
		
		return out;
		
	}
	
	public static int getNodeFromArray(Node[] nodes, int nodeCount, Node n){
		
		for(int i = 0; i < nodeCount; i++){
			
			if(nodes[i] == n){
				
				return i;
				
			}
			
		}
		
	}
	
}