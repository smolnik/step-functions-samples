package cloud.developing.task;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;

import cloud.developing.model.Ec2Instance;

public class InstanceLauncher {

	private final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	public Ec2Instance launch() {
		Instance instance = ec2.runInstances(new RunInstancesRequest("ami-09693313102a30b2c", 1, 1)).getReservation()
				.getInstances().get(0);
		return new Ec2Instance(instance.getInstanceId(), instance.getState().getName());
	}

}
