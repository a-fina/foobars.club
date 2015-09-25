/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import club.foobars.model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.junit.Test;
import static org.junit.Assert.*;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alessio.finamore
 */
public class TestORMLITE {
    
    public TestORMLITE() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws SQLException {
	    Config conf = ConfigFactory.load();
        String DATABASE_URL = conf.getString("dev.db");

		ConnectionSource connectionSource = null;
        connectionSource = new JdbcConnectionSource(DATABASE_URL);


        // Setup Database, Create Table
	    Dao<User, Integer> userDao;
        userDao = DaoManager.createDao(connectionSource, User.class);
		TableUtils.createTableIfNotExists(connectionSource, User.class);
        
        // New user
        User user = new User("foo","foobarswheels@gmail.com");
        userDao.create(user);
        
        // One row size
		List<User> users = userDao.queryForAll();
		assertEquals("Should have found 1 account matching our query", 1, users.size());
        
        // Read User
        String email = user.getEmail();
        assertEquals(email, userDao.queryForEq("email", email).get(0).getEmail());
        
        connectionSource.close();

    }
}
