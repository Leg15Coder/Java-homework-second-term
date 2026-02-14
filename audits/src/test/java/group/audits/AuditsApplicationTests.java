package group.audits;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import group.audits.dto.Audit;
import group.audits.service.UserAuditService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import static group.audits.service.UserAuditService.Action;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuditsApplicationTests {

	@Autowired
	private UserAuditService userAuditService;

	@Autowired
	private CqlSession cqlSession;

	private UUID testUserId;
	private static final String KEYSPACE = "my_keyspace";

	@BeforeEach
	void setUp() {
		testUserId = UUID.randomUUID();
		cqlSession.execute("TRUNCATE " + KEYSPACE + ".user_audit");
	}

	@AfterEach
	void tearDown() {
		cqlSession.execute("TRUNCATE " + KEYSPACE + ".user_audit");
	}

	@Test
	void insertUserActionPositive() {
		Instant testTime = Instant.now();
		String testMessage = "Test message";

		userAuditService.insertUserAction(new Audit(testUserId, Action.SELECT.toString(), testTime, testMessage));

		ResultSet result = cqlSession.execute(
				"SELECT * FROM " + KEYSPACE + ".user_audit WHERE user_id = ?",
				testUserId
		);

		assertEquals(1, result.all().size());
		var row = result.one();
		assertNotNull(row);
		assertEquals(testUserId, row.getUuid("user_id"));
		assertEquals("SELECT", row.getString("event_type"));
		assertEquals(testTime, row.getInstant("event_time"));
		assertEquals(testMessage, row.getString("event_details"));
	}

	@Test
	void insertUserActionNegativeInvalidUserId() {
		Instant testTime = Instant.now();
		String testMessage = "Test message";

		assertThrows(IllegalArgumentException.class, () -> {
			userAuditService.insertUserAction(new Audit(null, Action.SELECT.toString(), testTime, testMessage));
		});
	}

	@Test
	void getAllUserActionsPositive() {
		Instant testTime1 = Instant.now();
		Instant testTime2 = testTime1.plusSeconds(60);
		userAuditService.insertUserAction(new Audit(testUserId, Action.INSERT.toString(), testTime1, "First action"));
		userAuditService.insertUserAction(new Audit(testUserId, Action.UPDATE.toString(), testTime2, "Second action"));

		Collection<Audit> actions = userAuditService.getAllUserActions(testUserId);

		assertEquals(2, actions.size());

		Audit[] actionsArray = actions.toArray(new Audit[0]);
		assertEquals("INSERT", actionsArray[0].action());
		assertEquals("First action", actionsArray[0].message());
		assertEquals("UPDATE", actionsArray[1].action());
		assertEquals("Second action", actionsArray[1].message());
	}

	@Test
	void getAllUserActionsNegativeUserNotFound() {
		UUID nonExistentUserId = UUID.randomUUID();

		Collection<Audit> actions = userAuditService.getAllUserActions(nonExistentUserId);

		assertTrue(actions.isEmpty());
	}

	@Test
	void getAllUserActionsNegativeInvalidUserId() {
		assertThrows(IllegalArgumentException.class, () -> {
			userAuditService.getAllUserActions(null);
		});
	}
}
