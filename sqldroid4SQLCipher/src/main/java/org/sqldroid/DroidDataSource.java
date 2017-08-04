package org.sqldroid;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DroidDataSource implements DataSource {
    Connection connection = null;    
    protected String description = "Android Sqlite Data Source";
    protected String path;
    protected String databaseName;
    protected String password;

    public DroidDataSource() {

    }

    public DroidDataSource(String path, String databaseName) {
      	setPath(path);
       	setDatabaseName(databaseName);
        setPassword("");
    }

    public DroidDataSource(String path, String databaseName, String password) {
        setPath(path);
        setDatabaseName(databaseName);
        setPassword(password);
    }

    @Override
    public Connection getConnection() throws SQLException {
      	String url = "jdbc:sqldroid:" +
                (this.path.endsWith("/") ? this.path : this.path + "/") +
                databaseName;

        Properties properties = new Properties();
        properties.put("password", password);

        connection = new SQLDroidDriver().connect(url , properties);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password)
    			throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        PrintWriter logWriter = null;
        try {
            logWriter = new PrintWriter("droid.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logWriter;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        try {
            DriverManager.setLogWriter(new PrintWriter("droid.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    public String getDescription() {
      	return description;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public String getPath() {
     	return path;
    }

    public void setPath(String path) {
     	this.path = path;
    }

    public String getDatabaseName() {
    	return databaseName;
    }

    public void setDatabaseName(String databaseName) {
     	this.databaseName = databaseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return iface != null && iface.isAssignableFrom(getClass());
    }

    @Override
    public  <T> T unwrap(Class<T> iface) throws SQLException {
      if (isWrapperFor(iface)) {
        return (T) this;
      }
      throw new SQLException(getClass() + " does not wrap " + iface);
    }
        
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      	return null;
    }
        
}
