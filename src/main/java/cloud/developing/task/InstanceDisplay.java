package cloud.developing.task;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import cloud.developing.model.Ec2Instance;

public class InstanceDisplay {

	private final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

	public void display(Ec2Instance ec2Instance, Context context) {
		LambdaLogger logger = context.getLogger();
		String ec2Id = ec2Instance.getId();
		TerminateInstancesResult result = ec2
				.terminateInstances(new TerminateInstancesRequest().withInstanceIds(ec2Id));
		logger.log(result.toString());
	}

}
