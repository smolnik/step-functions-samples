package cloud.developing.model;

public class Ec2Instance {

	private String id, state;

	public Ec2Instance(String id, String state) {
		this.id = id;
		this.state = state;
	}

	public Ec2Instance() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Ec2Instance [id=" + id + ", state=" + state + "]";
	}

}
