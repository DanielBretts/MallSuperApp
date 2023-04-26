package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "MiniApp")
public class MiniAppCommandEntity {
	@Id
	private String id;
	private String internalCommandId;
	private String miniApp;
	private String command;
	private Date invocationTimeStamp;
	private Map<String,ObjectId> targetObject;
	private Map<String,UserId> invokedBy;
	private Map<String,Object> commandAttributes;
	
	
	public MiniAppCommandEntity() {
		this.targetObject = new HashMap<>();
		this.invokedBy = new HashMap<>();
		this.commandAttributes = new HashMap<>();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String getInternalCommandId() {
		return internalCommandId;
	}

	public void setInternalCommandId(String internalCommandId) {
		this.internalCommandId = internalCommandId;
	}


	public String getMiniApp() {
		return miniApp;
	}


	public void setMiniApp(String miniApp) {
		this.miniApp = miniApp;
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
