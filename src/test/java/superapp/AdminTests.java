package superapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import superapp.data.CreatedBy;
import superapp.data.Location;
import superapp.data.ObjectId;
import superapp.data.UserId;
import superapp.restApi.boundaries.InvokedBy;
import superapp.restApi.boundaries.MiniAppCommandBoundary;
import superapp.restApi.boundaries.NewUserBoundary;
import superapp.restApi.boundaries.ObjectBoundary;
import superapp.restApi.boundaries.TargetObjectBoundary;
import superapp.restApi.boundaries.UserBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTests {

	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	private String superapp = "2023b.shir.zur";
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.baseUrl = "http://localhost:" + this.port;
		this.restTemplate = new RestTemplate();
	}

	@AfterEach
	public void teardown() {
		this.restTemplate.delete(this.baseUrl + "/superapp/admin/users"); // Change and add more
		this.restTemplate.delete(this.baseUrl + "/superapp/admin/objects"); // Change and add more
		this.restTemplate.delete(this.baseUrl + "/superapp/admin/miniapp"); // Change and add more
	}

	// deleteAllUsers, deleteAllObjects, deleteAllCommands, exportAllUsers,
	// exportAllCommands, exportCommadsForMiniapp

	@Test
	public void testSuccessfulDeleteUsers() throws Exception {
		NewUserBoundary nub1 = new NewUserBoundary();
		nub1.setEmail("daniel@mail.com");
		nub1.setRole("ADMIN");
		nub1.setUsername("Daniel");
		nub1.setAvatar("db");
		this.restTemplate.postForObject(this.baseUrl + "/superapp/users", nub1, NewUserBoundary.class);

		NewUserBoundary nub2 = new NewUserBoundary();
		nub2.setEmail("amit@mail.com");
		nub2.setRole("MINIAPP_USER");
		nub2.setUsername("Amit");
		nub2.setAvatar("ab");
		this.restTemplate.postForObject(this.baseUrl + "/superapp/users", nub2, NewUserBoundary.class);

		this.restTemplate.delete(this.baseUrl + "/superapp/admin/users");

		UserBoundary[] users = this.restTemplate.getForObject(this.baseUrl + "/superapp/admin/users",
				UserBoundary[].class);
		assertThat(users.length == 0).isTrue();
	}

	@Test
	public void testSuccessfulDeleteObjects() throws Exception {
		ObjectBoundary ob1 = new ObjectBoundary();
		Map<String, Object> objectDetails1 = new HashMap<>();
		ObjectId oid1 = new ObjectId();
		ob1.setObjectId(oid1);
		ob1.setType("abc");
		ob1.setAlias("nissan");
		ob1.setActive(false);
		ob1.setLocation(new Location().setLat(45.2).setLng(0.0));
		UserId uid1 = new UserId();
		uid1.setEmail("nissan@mail.com");
		CreatedBy createdBy1 = new CreatedBy();
		createdBy1.setUserId(uid1);
		ob1.setCreatedBy(createdBy1);
		ob1.setObjectDetails(objectDetails1);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/objects", ob1, ObjectBoundary.class);

		ObjectBoundary ob2 = new ObjectBoundary();
		Map<String, Object> objectDetails2 = new HashMap<>();
		ObjectId oid2 = new ObjectId();
		ob2.setObjectId(oid2);
		ob2.setType("bcd");
		ob2.setAlias("daniel");
		ob2.setActive(true);
		ob2.setLocation(new Location().setLat(23.4).setLng(6.0));
		UserId uid2 = new UserId();
		uid2.setEmail("daniel@mail.com");
		CreatedBy createdBy2 = new CreatedBy();
		createdBy2.setUserId(uid2);
		ob2.setCreatedBy(createdBy2);
		ob2.setObjectDetails(objectDetails2);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/objects", ob2, ObjectBoundary.class);

		this.restTemplate.delete(this.baseUrl + "/superapp/admin/objects");

		ObjectBoundary[] objects = this.restTemplate.getForObject(this.baseUrl + "/superapp/admin/objects",
				ObjectBoundary[].class);

		assertThat(objects.length == 0).isTrue();
	}

	@Test
	public void testSuccessfulDeleteCommands() throws Exception {
		MiniAppCommandBoundary comm1 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes1 = new HashMap<>();
		comm1.setCommand("test");
		TargetObjectBoundary target1 = new TargetObjectBoundary(new ObjectId("123",this.superapp));
		comm1.setTargetObject(target1);
		InvokedBy invokedBy1 = new InvokedBy(new UserId("daniel@mail.com",this.superapp));
		comm1.setInvokedBy(invokedBy1);
		comm1.setCommandAttributes(commandAttributes1);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/BDIKA", comm1, MiniAppCommandBoundary.class);

		MiniAppCommandBoundary comm2 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes2 = new HashMap<>();
		comm2.setCommand("testing");
		TargetObjectBoundary target2 = new TargetObjectBoundary(new ObjectId("345",this.superapp));
		comm2.setTargetObject(target2);
		InvokedBy invokedBy2 = new InvokedBy(new UserId("nissan@mail.com",this.superapp));
		comm2.setInvokedBy(invokedBy2);
		comm2.setCommandAttributes(commandAttributes2);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/ODBDIKA", comm2,
				MiniAppCommandBoundary.class);

		this.restTemplate.delete(this.baseUrl + "/superapp/admin/miniapp");

		MiniAppCommandBoundary[] commands = this.restTemplate.getForObject(this.baseUrl + "/superapp/admin/miniapp",
				MiniAppCommandBoundary[].class);

		assertThat(commands.length == 0).isTrue();
	}

	@Test
	public void testSuccessfulExportUsers() throws Exception {
		NewUserBoundary nub1 = new NewUserBoundary();
		nub1.setEmail("daniel@mail.com");
		nub1.setRole("ADMIN");
		nub1.setUsername("Daniel");
		nub1.setAvatar("db");
		this.restTemplate.postForObject(this.baseUrl + "/superapp/users", nub1, NewUserBoundary.class);

		NewUserBoundary nub2 = new NewUserBoundary();
		nub2.setEmail("amit@mail.com");
		nub2.setRole("MINIAPP_USER");
		nub2.setUsername("Amit");
		nub2.setAvatar("ab");
		this.restTemplate.postForObject(this.baseUrl + "/superapp/users", nub2, NewUserBoundary.class);

		UserBoundary[] users = this.restTemplate.getForObject(this.baseUrl + "/superapp/admin/users",
				UserBoundary[].class);
		assertThat(users).isNotNull();
		assertThat(users[0]).isNotNull().extracting("userId.email", "role", "username", "avatar")
				.containsExactly(nub1.getEmail(), nub1.getRole(), nub1.getUsername(), nub1.getAvatar());
		assertThat(users[1]).isNotNull().extracting("userId.email", "role", "username", "avatar")
				.containsExactly(nub2.getEmail(), nub2.getRole(), nub2.getUsername(), nub2.getAvatar());
	}

	@Test
	public void testSuccessfulExportCommands() throws Exception {
		MiniAppCommandBoundary comm1 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes1 = new HashMap<>();
		comm1.setCommand("test");
		TargetObjectBoundary target1 = new TargetObjectBoundary(new ObjectId("123",this.superapp));
		comm1.setTargetObject(target1);
		InvokedBy invokedBy1 = new InvokedBy(new UserId("daniel@mail.com",this.superapp));
		comm1.setInvokedBy(invokedBy1);
		comm1.setCommandAttributes(commandAttributes1);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/BDIKA", comm1, MiniAppCommandBoundary.class);

		MiniAppCommandBoundary comm2 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes2 = new HashMap<>();
		comm2.setCommand("testing");
		TargetObjectBoundary target2 = new TargetObjectBoundary(new ObjectId("345",this.superapp));
		comm2.setTargetObject(target2);
		InvokedBy invokedBy2 = new InvokedBy(new UserId("nissan@mail.com",this.superapp));
		comm2.setInvokedBy(invokedBy2);
		comm2.setCommandAttributes(commandAttributes2);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/ODBDIKA", comm2,
				MiniAppCommandBoundary.class);

		MiniAppCommandBoundary[] commands = this.restTemplate.getForObject(this.baseUrl + "/superapp/admin/miniapp",
				MiniAppCommandBoundary[].class);

		assertThat(commands).isNotNull();
		assertThat(commands[0]).isNotNull()
				.extracting("command", "targetObject.objectId.internalObjectId", "invokedBy.userId.email",
						"commandAttributes")
				.containsExactly(comm1.getCommand(), comm1.getTargetObject().getObjectId().getInternalObjectId(),
						comm1.getInvokedBy().getUserId().getEmail(), comm1.getCommandAttributes());
		assertThat(commands[1]).isNotNull()
				.extracting("command", "targetObject.objectId.internalObjectId", "invokedBy.userId.email",
						"commandAttributes")
				.containsExactly(comm2.getCommand(), comm2.getTargetObject().getObjectId().getInternalObjectId(),
						comm2.getInvokedBy().getUserId().getEmail(), comm2.getCommandAttributes());
	}

	@Test
	public void testSuccessfulExportCommandsFromMiniapp() throws Exception {
		MiniAppCommandBoundary comm1 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes1 = new HashMap<>();
		comm1.setCommand("test");
		TargetObjectBoundary target1 = new TargetObjectBoundary(new ObjectId("123",this.superapp));
		comm1.setTargetObject(target1);
		InvokedBy invokedBy1 = new InvokedBy(new UserId("daniel@mail.com",this.superapp));
		comm1.setInvokedBy(invokedBy1);
		comm1.setCommandAttributes(commandAttributes1);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/BDIKA", comm1, MiniAppCommandBoundary.class);

		MiniAppCommandBoundary comm2 = new MiniAppCommandBoundary();
		Map<String, Object> commandAttributes2 = new HashMap<>();
		comm2.setCommand("testing");
		TargetObjectBoundary target2 = new TargetObjectBoundary(new ObjectId("345",this.superapp));
		comm2.setTargetObject(target2);
		InvokedBy invokedBy2 = new InvokedBy(new UserId("nissan@mail.com",this.superapp));
		comm2.setInvokedBy(invokedBy2);
		comm2.setCommandAttributes(commandAttributes2);
		this.restTemplate.postForObject(this.baseUrl + "/superapp/miniapp/ODBDIKA", comm2,
				MiniAppCommandBoundary.class);

		String miniAppToCheck = "BDIKA";

		MiniAppCommandBoundary[] commands = this.restTemplate.getForObject(
				this.baseUrl + "/superapp/admin/miniapp/" + miniAppToCheck, MiniAppCommandBoundary[].class);

		assertThat(commands).isNotNull();
		assertThat(commands[0]).isNotNull()
				.extracting("command", "targetObject.objectId.internalObjectId", "invokedBy.userId.email",
						"commandAttributes")
				.containsExactly(comm1.getCommand(), comm1.getTargetObject().getObjectId().getInternalObjectId(),
						comm1.getInvokedBy().getUserId().getEmail(), comm1.getCommandAttributes());

	}

}
