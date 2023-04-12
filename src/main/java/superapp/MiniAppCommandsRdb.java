package superapp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import superapp.data.MiniAppCommandEntity;
import superapp.logic.MiniAppCommandsCrud;
import superapp.logic.MiniAppCommandsService;

@Service
public class MiniAppCommandsRdb implements MiniAppCommandsService {
	
	private MiniAppCommandsCrud miniAppCommandsCrud;
	private String superapp;
	
	@Autowired
	public void setMiniAppCommandsCrud(MiniAppCommandsCrud miniAppCommandsCrud) {
		this.miniAppCommandsCrud = miniAppCommandsCrud;
	}
	
	@Value("${spring.application.name:2023b.shir.zur}")
	public void setSuperapp(String name) {
		this.superapp = name;
	}

	@Override
	public Object invokeCommand(MiniAppCommandBoundary command) {
		command.getCommandId().setInternalCommandID(UUID.randomUUID() + "");
		command.getCommandId().setSuperapp(this.superapp);
		MiniAppCommandEntity entity = this.toEntity(command);
		entity = this.miniAppCommandsCrud.save(entity);
		return (MiniAppCommandBoundary) toBoundary(entity);
	}

	private MiniAppCommandBoundary toBoundary(MiniAppCommandEntity entity) {
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();
		boundary.setCommand(entity.getCommand());
		boundary.setCommandId(new CommandID(entity.getMiniApp()));
		boundary.getCommandId().setSuperapp(this.superapp);
		boundary.getCommandId().setInternalCommandID(entity.getCommandId());
		boundary.setInvocationTimeStamp(entity.getInvocationTimeStamp());
		boundary.setCommandAttributes(entity.getCommandAttributes());
		boundary.setInvokedBy(entity.getInvokedBy());
		boundary.setTargetObject(entity.getTargetObject());
		return null;
	}

	private MiniAppCommandEntity toEntity(MiniAppCommandBoundary command) {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		entity.setCommand(command.getCommand());
		entity.setInvocationTimeStamp(new Date());
		entity.setInvokedBy(command.getInvokedBy());
		entity.setMiniApp(command.getCommandId().getMiniApp());
		entity.setCommandId(command.getCommandId().getInternalCommandID());
		if (command.getTargetObject() != null) {
			entity.setTargetObject(command.getTargetObject());
		}else {
			entity.setTargetObject(new HashMap<>());
		}
		if (command.getCommandAttributes() != null) {
			entity.setCommandAttributes(command.getCommandAttributes());
		}else {
			entity.setCommandAttributes(new HashMap<>());
		}
		return entity;
	}

	@Override
	public List<MiniAppCommandBoundary> getAllCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllCommands() {
		// TODO Auto-generated method stub
		
	}

}
