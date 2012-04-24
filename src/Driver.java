

public class Driver {

	static double hit1 = 0.1;
	static double hit2 = -0.2;
	static double hit3 = -0.1;
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
			//System.out.println("got child: "+childNode);
			if(childNode == null) {
				go = false;
			}
			else {
				node = childNode;
			}
		}
		
		System.out.println("resolution: "+node.structure.partitions);
		System.out.println(node.toString());
		
	}
	
	
	
}



