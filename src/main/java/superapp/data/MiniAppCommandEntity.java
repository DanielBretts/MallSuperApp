package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import superapp.ConverterOfMapToJson;
import superapp.ObjectId;
import superapp.UserId;

@Entity
@Table(name = "MINIAPP_COMMANDS")
public class MiniAppCommandEntity {
	@Id
	private String commandId;
	private String miniApp;
	private String command;
	@Temporal(TemporalType.TIMESTAMP)
	private Date invocationTimeStamp;
	@Convert(converter = ConverterOfMapToJson.class)
	@Lob
	private Map<String,ObjectId> targetObject;
	@Convert(converter = ConverterOfMapToJson.class)
	@Lob
	private Map<String,UserId> invokedBy;
	@Convert(converter = ConverterOfMapToJson.class)
	@Lob
	private Map<String,Object> commandAttributes;
	
	
	public MiniAppCommandEntity() {
		this.targetObject = new HashMap<>();
		this.invokedBy = new HashMap<>();
		this.commandAttributes = new HashMap<>();
	}


	public String getCommandId() {
		return commandId;
	}


	public void setCommandId(String commandId) {
		this.commandId = commandId;
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
