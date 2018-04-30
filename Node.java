
public class Node {

	public String name;
	public String rating;
	public String address;
	
	public Node(String name, String rating, String address) {
		this.name=name;
		this.rating=rating;
		this.address=address;
	}	
	
	@Override
	public String toString() {
		return name;
	}

}
