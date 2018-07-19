package com.test.service.support;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;

public class HibernateSessionFactory {

	private static String CONFIG_FILE_LOCATION = "META-INF/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;
	
	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creando SessionFactory %%%%" );
			e.printStackTrace();

		}
	}
	
	private HibernateSessionFactory() {
	}
	
	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();

		if ((session == null) || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}

			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	public static void rebuildSessionFactory() throws HibernateException {
		configuration.configure(configFile);
		sessionFactory = configuration.buildSessionFactory();
	}
	
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}
	
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Rollback hibernate Transaction
	 * 
	 */
	public static void rollback() {
		if ((getSession().getTransaction() != null)
				&& (getSession().getTransaction().isActive() == true)) {
			getSession().getTransaction().rollback();
		}
	}

	/**
	 * Begin hibernate Transaction
	 * 
	 */
	public static void beginTransaction() {
		getSession().getTransaction().begin();
	}

	/**
	 * Commit hibernate Transaction
	 * 
	 */
	public static void commit() {
		getSession().getTransaction().commit();
	}

	/**
	 * Create hibernate Query
	 * 
	 */
	public static Query createQuery(String query) {
		return getSession().createQuery(query);
	}

	/**
	 * Flush hibernate Session
	 * 
	 */
	public static void flush() {
		getSession().flush();
	}
	
	/**
	 * Get Connection From Session
	 */
	@SuppressWarnings("deprecation")
	public static Connection getConnection() throws HibernateException{
		SessionFactoryImplementor impl =  null;
		ConnectionProvider cp = null;
		Connection connection = null;
		try {
			impl = (SessionFactoryImplementor) getSessionFactory();
			cp = impl.getConnectionProvider();
			connection = cp.getConnection();
			
			if(connection == null && getSession() != null){
				getSession().connection();
			}
			
		} catch (SQLException e) {
			throw new HibernateException(e);
		}return connection;
	}
}
