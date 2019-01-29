

public class Reference {
	Byte refBit = 0;
	Byte count = 0;
	public Byte getRefBit() {
		return refBit;
	}
	public void setRefBit(Byte refBit) {
		this.refBit = refBit;
	}
	public Byte getCount() {
		return count;
	}
	public void setCount(Byte count) {
		this.count = count;
	}
	
	public void shift() {
		this.count = (byte) ((this.count & 0xff) >>> 1);
	}
	
	public void setMin() {
		this.count = (byte) (this.count|(1<<7));
	}
}
