package superapp.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import superapp.data.exceptions.MiniAppCommandException;
import superapp.logic.MiniAppCommandsCrud;
import superapp.logic.MiniAppCommandsQueries;
import superapp.restApi.boundaries.MiniAppCommandBoundary;

@Service
public class MiniAppCommandsDb implements MiniAppCommandsQueries {

	private MiniAppCommandsCrud miniAppCommandsCrud;
	private String superapp;
	private char delimeter = '_';
	private ObjectMapper jackson;
	private JmsTemplate jmsTemplate;

	@Autowired
	public void setMiniAppCommandsCrud(MiniAppCommandsCrud miniAppCommandsCrud) {
		this.miniAppCommandsCrud = miniAppCommandsCrud;
	}

	@PostConstruct
	public void setup() {
		this.jackson = new ObjectMapper();
	}

	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Value("${spring.application.name:2023b.shir.zur}")
	public void setSuperapp(String name) {
		this.superapp = name;
	}

	@Override
	public Object invokeCommand(MiniAppCommandBoundary command) {
		command.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
		if (command.getTargetObject().getObjectId().getSuperapp() != null)
			command.getCommandId().setSuperapp(this.superapp);
		else
			throw new MiniAppCommandException("Superapp inside commandId can not be empty!");
		if (command.getInvokedBy().getUserId().getSuperapp() != null)
			command.getCommandId().setSuperapp(this.superapp);
		else
			throw new MiniAppCommandException("Superapp inside userId can not be empty!");

		MiniAppCommandEntity entity = this.toEntity(command);
		entity = this.miniAppCommandsCrud.save(entity);
		return (MiniAppCommandBoundary) toBoundary(entity);
	}

	private MiniAppCommandBoundary toBoundary(MiniAppCommandEntity entity) {
		MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();
		boundary.setCommand(entity.getCommand());
		boundary.setCommandId(new CommandId(entity.getMiniApp()));
		boundary.getCommandId().setSuperapp(this.superapp);
		boundary.getCommandId().setInternalCommandId(entity.getId());
		boundary.setInvocationTimestamp(entity.getInvocationTimeStamp());
		boundary.setCommandAttributes(entity.getCommandAttributes());
		boundary.setInvokedBy(entity.getInvokedBy());
		boundary.setTargetObject(entity.getTargetObject());
		return boundary;
	}

	private MiniAppCommandEntity toEntity(MiniAppCommandBoundary command) {
		MiniAppCommandEntity entity = new MiniAppCommandEntity();
		if (command.getCommand() == null) {
			throw new MiniAppCommandException("Command can not be empty");
		} else {
			entity.setCommand(command.getCommand());
		}
		entity.setInvocationTimeStamp(new Date());
		if (command.getInvokedBy() == null || command.getInvokedBy().getUserId().getEmail() == null) {
			throw new MiniAppCommandException("InvokedBy can not be empty");
		} else {
			command.getInvokedBy().getUserId().setSuperapp(this.superapp);
			entity.setInvokedBy(command.getInvokedBy());
		}
		entity.setMiniApp(command.getCommandId().getMiniApp());

		/*
		 * The result for entity.setId() id = superapp_miniApp_internalCommandID
		 * 
		 */
		String id = command.getCommandId().getSuperapp() + delimeter + command.getCommandId().getMiniApp() + delimeter
				+ command.getCommandId().getInternalCommandId();

		entity.setId(id);
		entity.setInternalCommandId(command.getCommandId().getInternalCommandId());
		if (command.getTargetObject() == null
				|| command.getTargetObject().getObjectId().getInternalObjectId() == null) {
			throw new MiniAppCommandException("TargetObject can not be empty");
		} else {
			command.getTargetObject().getObjectId().setSuperapp(this.superapp);
			entity.setTargetObject(command.getTargetObject());
		}
		if (command.getCommandAttributes() != null) {
			entity.setCommandAttributes(command.getCommandAttributes());
		} else {
			entity.setCommandAttributes(new HashMap<>());
		}
		return entity;
	}

	@Deprecated
	@Override
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

	@Deprecated
	@Override
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {

		Iterable<MiniAppCommandEntity> iterable = this.miniAppCommandsCrud.findAll();
		Iterator<MiniAppCommandEntity> iterator = iterable.iterator();
		List<MiniAppCommandBoundary> list = new ArrayList<>();
		while (iterator.hasNext()) {
			MiniAppCommandBoundary boundary = toBoundary(iterator.next());
			if (boundary.getCommandId().getMiniApp().equals(miniAppName))
				list.add(boundary);
		}
		return list;
	}

	@Deprecated
	@Override
	public void deleteAllCommands() {
		this.miniAppCommandsCrud.deleteAll();
	}

	@Override
	public MiniAppCommandBoundary handleLater(MiniAppCommandBoundary miniAppCommandBoundary, boolean isAsync) {
		miniAppCommandBoundary.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
		if (miniAppCommandBoundary.getTargetObject().getObjectId().getSuperapp() != null)
			miniAppCommandBoundary.getCommandId().setSuperapp(this.superapp);
		else
			throw new MiniAppCommandException("Superapp inside commandId can not be empty!");
		if (miniAppCommandBoundary.getInvokedBy().getUserId().getSuperapp() != null)
			miniAppCommandBoundary.getCommandId().setSuperapp(this.superapp);
		else
			throw new MiniAppCommandException("Superapp inside userId can not be empty!");
		if (isAsync) {
			try {
				String json = this.jackson.writeValueAsString(miniAppCommandBoundary);
				this.jmsTemplate.convertAndSend("handleLaterCommand", json);
				return miniAppCommandBoundary;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			MiniAppCommandEntity entity = this.toEntity(miniAppCommandBoundary);
			entity = this.miniAppCommandsCrud.save(entity);
			return (MiniAppCommandBoundary) toBoundary(entity);
		}

	}

	@JmsListener(destination = "handleLaterCommand")
	public void listenToMyMom(String json) {
		try {
			System.err.println("*** received: " + json);
			MiniAppCommandBoundary miniAppCommandBoundary = this.jackson.readValue(json, MiniAppCommandBoundary.class);
			if (miniAppCommandBoundary.getCommandId().getInternalCommandId() == null) {
				miniAppCommandBoundary.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
			}
			if (miniAppCommandBoundary.getInvocationTimestamp() == null) {
				miniAppCommandBoundary.setInvocationTimestamp(new Date());
			}

			MiniAppCommandEntity entity = this.toEntity(miniAppCommandBoundary);
			entity = this.miniAppCommandsCrud.save(entity);
			System.err.println("*** saved: " + this.toBoundary(entity));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public List<MiniAppCommandBoundary> getCommandsByEmail(String superapp, String email, int size, int page) {
		return this.miniAppCommandsCrud.findAllByEmail(superapp,email,PageRequest.of(page, size,Direction.DESC,"invocationTimestamp",
				"commandId.internalCommandId")).stream()
				.map(this::toBoundary)
				.toList();
	}

	@Override
	public List<MiniAppCommandBoundary> getMiniAppCommandsByEmail(String miniAppName, String superapp, String email,
			int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCommandsByEmail(String superapp, String email) {
		// TODO Auto-generated method stub

	}

}
