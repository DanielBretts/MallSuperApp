package superapp.boundaries;

import java.util.Date;
import java.util.List;
import java.util.Map;

import superapp.data.CommandID;
import superapp.data.ObjectId;
import superapp.data.UserId;

public class MiniAppCommandBoundary {
	private CommandID commandId;
	private String command;
	private Date invocationTimeStamp;
	private Map<String,ObjectId> targetObject;
	private Map<String,UserId> invokedBy;
	private Map<String,Object> commandAttributes;
	
	public MiniAppCommandBoundary() {
		
	}

	public MiniAppCommandBoundary(CommandID commandId, String command, Map<String,ObjectId> targetObject,
			Map<String,UserId> invokedBy, Map<String, Object> commandAttributes) {
		super();
		this.commandId = commandId;
		this.command = command;
		this.targetObject = targetObject;
		this.invocationTimeStamp = new Date();
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

	public Date getInvocationTimeStamp() {
		return invocationTimeStamp;
	}

	public void setInvocationTimeStamp(Date invocationTimeStamp) {
		this.invocationTimeStamp = invocationTimeStamp;
	}

	public Map<String, ObjectId> getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(Map<String, ObjectId> targetObject) {
		this.targetObject = targetObject;
	}

	public Map<String, UserId> getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(Map<String, UserId> invokedBy) {
		this.invokedBy = invokedBy;
	}

	

	public Map<String, Object> getCommandAttributes() {
		return commandAttributes;
	}

	public void setCommandAttributes(Map<String, Object> commandAttributes) {
		this.commandAttributes = commandAttributes;
	}

	
	
}