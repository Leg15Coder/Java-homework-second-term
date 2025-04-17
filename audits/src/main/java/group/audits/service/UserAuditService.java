package group.audits.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import group.audits.dto.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserAuditService {

  @Autowired
  private CqlSession session;

  public enum Action {
    SELECT, UPDATE, INSERT, DELETE, DROPPED_DATABASE
  }

  public void insertUserAction(Audit audit) {
    PreparedStatement preparedStatement = session.prepare(
        "INSERT INTO my_keyspace.user_audit (user_id, event_time, event_type, event_details) " +
            "VALUES (?, ?, ?, ?)"
    );

    BoundStatement boundStatement = preparedStatement.bind(
        audit.userId(),
        audit.time(),
        audit.action(),
        audit.message()
    );

    session.execute(boundStatement);
  }

  public Collection<Audit> getAllUserActions(UUID userId) {
    PreparedStatement preparedStatement = session.prepare(
        "SELECT * FROM my_keyspace.user_audit WHERE user_id = ?"
    );

    BoundStatement boundStatement = preparedStatement.bind(userId);

    return session.execute(boundStatement).all().stream()
        .map(row -> new Audit(
            row.getUuid("user_id"),
            row.getString("event_type"),
            row.getInstant("event_time"),
            row.getString("event_details")))
        .toList();
  }
}