
public class BinTree {
	BinTreeNode root;
	
	public BinTree(int numPlanes) {
		
		/*
		 * Create initial structure of bins spanning the whole block
		 */
		int[] planes = new int[numPlanes];
		
		for(int i=0; i<numPlanes; i++) {
			planes[i] = 1;
		}
		
		GEMStructure str = new GEMStructure(planes, 1);
		
		root = new BinTreeNode(str, numPlanes);
	}
	
	public BinTreeNode getRoot() {
		return root;
	}
	
}
