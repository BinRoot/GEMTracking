
public class GEMStructure {
	int[] planesHits;
	int partitions;
	
	public GEMStructure(int[] planesHits, int partitions) {
		this.planesHits = planesHits;
		this.partitions = partitions;
	}
	
	public int[] getPlaneHits() {
		return planesHits;
	}
	
	public int getPartitions() {
		return partitions;
	}
	
	@Override
	public String toString() {
		String retStr = "hits: ";
		for(int i=0; i<planesHits.length; i++) {
			retStr += planesHits[i] + " ";
		}
		return retStr;
	}
	
	public int getNextPartitions() {
		return 2*partitions;
	}
}
