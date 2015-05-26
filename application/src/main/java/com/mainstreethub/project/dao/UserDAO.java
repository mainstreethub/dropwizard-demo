package com.mainstreethub.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public interface UserDAO {
  @SqlUpdate("INSERT INTO users (username, first, last, salt, hash) "
             + "VALUES (:username, :first, :last, :salt, :hash)")
  @GetGeneratedKeys
  long createUser(@Bind("username") String username,
                  @Bind("first") String first,
                  @Bind("last") String last,
                  @Bind("salt") String salt,
                  @Bind("hash") String hash);

  @SqlQuery("SELECT username, first, last FROM users WHERE id=:id")
  @Mapper(UserMapper.class)
  DBUser findUserById(@Bind("id") long id);

  @SqlUpdate("DELETE FROM users WHERE id=:id")
  int deleteUser(@Bind("id") long id);

  @SqlUpdate("UPDATE users SET salt=:salt, hash=:hash WHERE id=:id")
  int updatePassword(@Bind("id") long id,
                     @Bind("salt") String salt,
                     @Bind("hash") String hash);

  static final class DBUser {
    public final String username;
    public final String first;
    public final String last;

    public DBUser(String username, String first, String last) {
      this.username = username;
      this.first = first;
      this.last = last;
    }
  }

  static final class UserMapper implements ResultSetMapper<DBUser> {
    @Override
    public DBUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
      return new DBUser(r.getString("username"), r.getString("first"), r.getString("last"));
    }
  }
}
