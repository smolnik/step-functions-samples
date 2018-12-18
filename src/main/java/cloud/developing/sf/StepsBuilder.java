package cloud.developing.sf;

import static java.lang.System.*;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.choice;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.choiceState;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.end;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.eq;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.next;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.not;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.seconds;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.stateMachine;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.taskState;
import static com.amazonaws.services.stepfunctions.builder.StepFunctionBuilder.waitState;

import com.amazonaws.services.stepfunctions.builder.StateMachine;
import com.amazonaws.services.stepfunctions.builder.conditions.Condition;
import com.amazonaws.services.stepfunctions.builder.states.ChoiceState;
import com.amazonaws.services.stepfunctions.builder.states.TaskState;

public class StepsBuilder {

	public static void main(String[] args) throws Exception {
		String arnPrefix = getProperty("arnPrefix");

		TaskState.Builder launcher = taskState().resource(arnPrefix + "instance-launcher")
				.transition(next("InstanceStateChecker"));

		TaskState.Builder checker = taskState().resource(arnPrefix + "instance-state-checker")
				.transition(next("ChoiceState"));
		TaskState.Builder display = taskState().resource(arnPrefix + "instance-display").transition(end());

		Condition.Builder runningCondition = eq("$.state", "running");
		Condition.Builder notRunningCondition = not(runningCondition);
		ChoiceState.Builder choiceState = choiceState().choices(
				choice().condition(runningCondition).transition(next("InstanceStateDisplay")),
				choice().condition(notRunningCondition).transition(next("Wait")));

		final StateMachine stateMachine = stateMachine().startAt("InstanceLauncher").state("InstanceLauncher", launcher)
				.state("InstanceStateChecker", checker).state("ChoiceState", choiceState)
				.state("InstanceStateDisplay", display)
				.state("Wait", waitState().waitFor(seconds(30)).transition(next("InstanceStateChecker"))).build();
		System.out.println(stateMachine.toPrettyJson());

	}
}
