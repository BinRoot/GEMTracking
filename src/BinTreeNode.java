import java.util.ArrayList;

public class BinTreeNode {
	ArrayList<BinTreeNode> children;
	GEMStructure structure;
	int range = 2;
	int numPlanes;
	
	public BinTreeNode(GEMStructure structure, int numPlanes) {
		this.structure = structure;
		children = new ArrayList<BinTreeNode>();
		this.numPlanes = numPlanes;
	}
	
	public GEMStructure getStructure() {
		return structure;
	}
	
	public void addChild(BinTreeNode node) {
		children.add(node);
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	public ArrayList<BinTreeNode> getChildren() {
		return children;
	}
	
	@Override
	public String toString() {
		return structure.toString();
	}
	
	public BinTreeNode magnify(double [] hits) {
		for(int i=0; i<this.getChildren().size(); i++) {
			BinTreeNode childNode = this.getChildren().get(i);
			
			boolean matchFailed = false;
			for(int j=0; j<hits.length; j++) {
				if(childNode.matchesHit(j, hits[j]) == false) {
					matchFailed = true;
					continue;
				}
			}
			
			if(matchFailed == false) {
				return childNode;
			}
		}
		return null;
	}
	
	public boolean matchesHit(int pos, double val) {
		
		//System.out.println("matchesHit: "+pos+", "+val);
		
		int hitLoc = structure.getPlaneHits()[pos];
		//System.out.println("hitLoc: "+hitLoc);
		/*
		 * Algorithm to convert hit into position
		 */
		double hitPos = ((val+1.0)/((double)range));
		//System.out.println("hit pos: "+hitPos);
		
		int actualHitLoc = (int)(hitPos*structure.partitions +1);
		//System.out.println("actual hit loc: "+actualHitLoc);
		
		if(hitLoc==actualHitLoc) {
			return true;
		}
		
		return false;
	}

	
	// TODO: fix me! Should not move left AND right!!
	public void createChildren() {
		GEMStructure struc = this.getStructure();
		int partitions[] = struc.getPlaneHits();
		
		IndexPair[] newPartitions = new IndexPair[numPlanes];
		
		boolean nextLeftValid = false;
		boolean nextRightValid = false;
		
		for(int i=0; i<numPlanes; i++) {
			int curIndex = partitions[i];
			int rightPair = curIndex*2;
			int leftPair = curIndex*2-1;
			
			IndexPair ip = new IndexPair(leftPair, rightPair);
			
			if(nextRightValid) {
				ip.setValidRight(true);
			}
			else {
				ip.setValidRight(false);
			}
			
			if(nextLeftValid) {
				ip.setValidLeft(true);
			}
			else {
				ip.setValidLeft(false);
			}
			
			nextLeftValid = false;
			nextRightValid = false;

			if(i<numPlanes-1) {
				int nextIndex = partitions[i+1];
				if(nextIndex > curIndex) {
					//System.out.println("next > cur, setting cur-right to valid and next-left to valid");
					// do not delete curIndex rightPair nor nextIndex leftPair
					ip.setValidRight(true);
					nextLeftValid = true;
				}
				else if(curIndex > nextIndex) {
					//System.out.println("next < cur, setting cur-left to valid and next-right to valid");
					// do not delete curIndex leftPair nor nextIndex rightPair
					ip.setValidLeft(true);
					nextRightValid = true;
				}
				else {
					//System.out.println("next == cur, setting cur-left to valid and cur-right to valid");
					// do not delete curIndex leftPair nor rightPair
					ip.setValidLeft(true);
					ip.setValidRight(true);
				}
			}
			else { // on last block
				if(!ip.validLeft && !ip.validRight) {
					ip.setValidLeft(true);
					ip.setValidRight(true);
				}
			}
			
			newPartitions[i] = ip;
		}
		
		//printNewPartitions(newPartitions);
		
		
		
		for(int aa=newPartitions[0].update().leftPair; aa<=newPartitions[0].update().rightPair; aa++) {
			for(int bb=newPartitions[1].update().leftPair; bb<=newPartitions[1].update().rightPair; bb++) {
				for(int cc=newPartitions[2].update().leftPair; cc<=newPartitions[2].update().rightPair; cc++) {
					//System.out.println("path: "+aa+"->"+bb+"->"+cc);
					int [] newPlanesHit = {aa, bb, cc};
					BinTreeNode childNode = new BinTreeNode(new GEMStructure(newPlanesHit, this.structure.getNextPartitions()), numPlanes);
					this.addChild(childNode);
				}
			}
		}
	}
	
	
	
	public static void printNewPartitions(IndexPair[] newPartitions) {
		for(int i=0; i<newPartitions.length; i++) {
			//System.out.println("-- "+i+" --");
			if(newPartitions[i].validLeft) {
				System.out.print(newPartitions[i].leftPair+" ");
			}
			if(newPartitions[i].validRight) {
				System.out.print(newPartitions[i].rightPair);
			}
			System.out.println("");
		}
	}
	
	
	/**
	 * old partition:
	 * [X][ ] = 1
	 * [X][ ] = 1
	 * [X][ ] = 1
	 * [ ][X] = 2
	 * 
	 * new partition:
	 * given 4 partitions and 4 planes
	 * [X][X][ ][ ] = 1,2
	 * [X][X][ ][ ] = 1,2
	 * [ ][X][ ][ ] = 2
	 * [ ][ ][X][ ] = 3
	 */
}

class IndexPair {
	int leftPair;
	int rightPair;
	boolean validLeft = false;
	boolean validRight = false;
	
	public IndexPair(int leftPair, int rightPair) {
		this.leftPair = leftPair;
		this.rightPair = rightPair;
	}
	
	public void setValidLeft(boolean v) {
		validLeft = v;
	}
	
	public void setValidRight(boolean v) {
		validRight = v;
	}
	
	public IndexPair update() {
		if(!validLeft) {
			leftPair = rightPair;
		}
		if(!validRight) {
			rightPair = leftPair;
		}
		
		return this;
	}
}
