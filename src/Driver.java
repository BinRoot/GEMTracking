

public class Driver {

	static double hit1 = 0.03;
	static double hit2 = 0.01;
	static double hit3 = -0.01;
	static double hits[] = {hit1, hit2, hit3};
	static int numPlanes = 3;
	
	public static void main(String [] args) {
		BinTree bintree = new BinTree(numPlanes);
		BinTreeNode root = bintree.getRoot();
		
		BinTreeNode node = root;
		
		boolean go = true;
		while(go) {
			node.createChildren();
			
			BinTreeNode childNode = node.magnify(hits);
			
			if(childNode == null) {
				go = false;
			}
			else {
				node = childNode;
			}
		}
		
		System.out.println("get partitions: "+node.structure.partitions);
		System.out.println(node.toString());
		
	}
	
	
	
}



