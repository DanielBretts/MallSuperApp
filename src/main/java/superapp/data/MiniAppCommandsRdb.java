package superapp.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import superapp.boundaries.MiniAppCommandBoundary;
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
		command.getCommandId().setInternalCommandID(UUID.randomUUID().toString());
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
		boundary.getCommandId().setInternalCommandID(entity.getId());
		boundary.setInvocationTimeStamp(entity.getInvocationTimeStamp());
		boundary.setCommandAttributes(entity.getCommandAttributes());
		boundary.setInvokedBy(entity.getInvokedBy());
		boundary.setTargetObject(entity.getTargetObject());
		return boundary;
	}

	private MiniAppCommandEntity toEntity(MiniAppCommandBoundary command) {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		entity.setCommand(command.getCommand());
		entity.setInvocationTimeStamp(new Date());
		if(command.getInvokedBy() != null) {
			command.getInvokedBy().get("userId").setSuperapp(this.superapp);
			entity.setInvokedBy(command.getInvokedBy());
		}
		entity.setMiniApp(command.getCommandId().getMiniApp());
		
		/*
		 * 	The result for entity.setId()
		 * 	id = superapp_miniApp_internalCommandID
		 * 
		 */
		String id = command.getCommandId().getSuperapp() + "_" + 
					command.getCommandId().getMiniApp()  + "_" + 
					command.getCommandId().getInternalCommandID();
		
		entity.setId(id);
		
		entity.setInternalCommandId(command.getCommandId().getInternalCommandID());
		
		if (command.getTargetObject() != null) {
			command.getTargetObject().get("objectId").setSuperapp(this.superapp);
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
	@Transactional(readOnly = true)
	public List<MiniAppCommandBoundary> getAllCommands() {
		Iterable<MiniAppCommandEntity> iterable = this.miniAppCommandsCrud.findAll();
		Iterator<MiniAppCommandEntity> iterator = iterable.iterator();
		List<MiniAppCommandBoundary> list = new ArrayList<>();
		while (iterator.hasNext()) {
			MiniAppCommandBoundary boundary = toBoundary(iterator.next());
			list.add(boundary);
		}
		return list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
		
		Iterable<MiniAppCommandEntity> iterable = this.miniAppCommandsCrud.findAll();
		Iterator<MiniAppCommandEntity> iterator = iterable.iterator();
		List<MiniAppCommandBoundary> list = new ArrayList<>();
		while (iterator.hasNext()) {
			MiniAppCommandBoundary boundary = toBoundary(iterator.next());
			if(boundary.getCommandId().getMiniApp().equals(miniAppName))		
				list.add(boundary);
		}
		return list;
	}
	

	@Override
	public void deleteAllCommands() {
		this.miniAppCommandsCrud.deleteAll();		
	}

}
