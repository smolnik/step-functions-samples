package cloud.developing.task;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import cloud.developing.model.Ec2Instance;

public class InstanceStateChecker {

	private final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	public Ec2Instance checkState(Ec2Instance ec2Instance, Context context) {
		LambdaLogger logger = context.getLogger();

		String ec2Id = ec2Instance.getId();
		String state = ec2.describeInstances(new DescribeInstancesRequest().withInstanceIds(ec2Id)).getReservations()
				.get(0).getInstances().get(0).getState().getName();
		logger.log(ec2Instance.toString());
		return new Ec2Instance(ec2Id, state);
	}

}
