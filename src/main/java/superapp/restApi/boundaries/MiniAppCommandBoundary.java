package superapp.restApi.boundaries;

import java.util.Date;
import java.util.Map;

import superapp.data.CommandID;

public class MiniAppCommandBoundary {
	private CommandID commandId;
	private String command;
	private Date invocationTimestamp;
	private TargetObjectBoundary targetObject;
	private InvokedBy invokedBy;
	private Map<String,Object> commandAttributes;
	
	public MiniAppCommandBoundary() {
		
	}

	//constructor without commandId
	public MiniAppCommandBoundary(String command, TargetObjectBoundary targetObject,
			InvokedBy invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimestamp = new Date();
		this.invokedBy = invokedBy;
		this.commandAttributes = commandAttributes;
	}
	
	
	public MiniAppCommandBoundary(CommandID commandId, String command, TargetObjectBoundary targetObject,
			InvokedBy invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimestamp = new Date();
		this.invokedBy = invokedBy;
		this.commandAttributes = commandAttributes;
	}
	


	public CommandID getCommandId() {
		return commandId;
	}

	public void setCommandId(CommandID commandId) {
		this.commandId = commandId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Date getInvocationTimestamp() {
		return invocationTimestamp;
	}

	public void setInvocationTimestamp(Date invocationTimeStamp) {
		this.invocationTimestamp = invocationTimeStamp;
	}

	public TargetObjectBoundary getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(TargetObjectBoundary targetObject) {
		this.targetObject = targetObject;
	}

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}

	@Override
	public String toString() {
		return "MiniAppCommandBoundary [commandId=" + commandId + ", command=" + command + ", invocationTimestamp="
				+ invocationTimestamp + ", targetObject=" + targetObject + ", invokedBy=" + invokedBy
				+ ", commandAttributes=" + commandAttributes + "]";
	}	
}
