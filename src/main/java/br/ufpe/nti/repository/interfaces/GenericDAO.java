package br.ufpe.nti.repository.interfaces;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDAO<T> implements Serializable, IGenericDAO<T> {

	private static final long serialVersionUID = 6067158566682014012L;

	 private static EntityManagerFactory emf;
	 private static EntityManager em;

	private Class<?> domain;

	private Class<?> getDomainClass() {
		if (this.domain == null) {
			this.domain = getGenericTypeArgument(this.getClass(), 0);
		}
		return this.domain;
	}

//	public synchronized static EntityManager getEntityManager() {
//		if (GenericDAO.em == null) {
//			GenericDAO.emf = Persistence.createEntityManagerFactory("absaber-pgadmin");
//			GenericDAO.em = GenericDAO.emf.createEntityManager();
//		}
//		return GenericDAO.em;
//	}
//
//	public synchronized static void closeEntityManager() {
//		if (GenericDAO.em != null) {
//			GenericDAO.em.clear();
//			GenericDAO.em.close();
//			GenericDAO.em = null;
//			GenericDAO.emf.getCache().evictAll();
//			GenericDAO.getEntityManager();
//		}
//
//	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericTypeArgument(final Class<?> clazz, final int idx) {
		final Type type = clazz.getGenericSuperclass();
		ParameterizedType paramType;
		try {
			paramType = (ParameterizedType) type;
		} catch (ClassCastException cause) {
			paramType = (ParameterizedType) ((Class<T>) type).getGenericSuperclass();
		}
		return (Class<T>) paramType.getActualTypeArguments()[idx];
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getEntityManager().createQuery("select this from " + getDomainClass().getSimpleName() + " this")
				.getResultList();
	}

	public void insert(T obj) {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().persist(obj);
			getEntityManager().getTransaction().commit();
		} catch (Exception er) {
			System.err.println(er.getMessage());
		} finally {
			closeEntityManager();
			// getEntityManager().close();
		}

		// getEntityManager().persist(obj);
	}

	public void update(T obj) {
		// EntityManager em = getEntityManager();
		// boolean conseguiuAtualizar = true;
		try {
			// em.getTransaction().begin();
			beginTransaction();
			merge(obj);
			commitTransaction();
			// em.merge(obj);
			// em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			// conseguiuAtualizar = false;
		} finally {
			closeEntityManager();
			// em.close();
		}
		// return conseguiuAtualizar;
		// getEntityManager().merge(obj);
	}

	public void delete(T obj) {
		EntityManager em = getEntityManager();
		// boolean conseguiuDeletar = true;
		try {
			// em.getTransaction().begin();
			// em.remove(em.merge(obj));
			// em.getTransaction().commit();

			beginTransaction();
			remove(merge(obj));
			commitTransaction();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			// conseguiuDeletar = false;
		} finally {
			closeEntityManager();
			// em.close();
		}
		// return conseguiuDeletar;
		// getEntityManager().remove(obj);
	}

	public static void main(String[] args) {

		getEntityManager().createQuery("select this from USUARIO this").getResultList();
	}

	@SuppressWarnings("unchecked")
	public T findByID(Object id) {
		return (T) getEntityManager().find(getDomainClass(), id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static final Map<String, ThreadLocal<EntityManager>> ENTITY_MANAGER_MAP = new HashMap<String, ThreadLocal<EntityManager>>();

	private static final Map<String, EntityManagerFactory> ENTITY_MANAGER_FACTORY_MAP = new HashMap<String, EntityManagerFactory>();

	private static String defaultPersistenceUnitName = "absaber-pgadmin";
//
//	public synchronized static void initializeEntityManagerFactory(String persistenceUnitName,
//			IConfigurator<EntityManagerFactory> configurator, boolean defaultPersistenceUnit) {
//		if (defaultPersistenceUnit) {
//			GenericDAO.defaultPersistenceUnitName = persistenceUnitName;
//		}
//		EntityManagerFactory entityManagerFactory = configurator.configure();
//		ENTITY_MANAGER_FACTORY_MAP.put(persistenceUnitName, entityManagerFactory);
//	}
//
//	public synchronized static void initializeEntityManagerFactory(String persistenceUnitName, Map properties,
//			boolean defaultPersistenceUnit) {
//		if (defaultPersistenceUnit) {
//			GenericDAO.defaultPersistenceUnitName = persistenceUnitName;
//		}
//		EntityManagerFactory entityManagerFactory = null;
//		if (properties != null) {
//			entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
//		} else {
//			entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
//		}
//		ENTITY_MANAGER_FACTORY_MAP.put(persistenceUnitName, entityManagerFactory);
//	}
//
//	public static void initializeEntityManagerFactory(String persistenceUnitName, boolean defaultPersistenceUnit) {
//		initializeEntityManagerFactory(persistenceUnitName, (Map) null, defaultPersistenceUnit);
//	}

	/**
	 * 
	 * @param persistenceUnitName
	 * @return
	 */
	public synchronized static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		//EntityManagerFactory entityManagerFactory = ENTITY_MANAGER_FACTORY_MAP.get(persistenceUnitName);
		//TODO colocar o map  e 
		if (emf == null) {
			throw new IllegalStateException(
					"Persistence unit name: " + persistenceUnitName + " has not been initialized!");
		}
		//return entityManagerFactory;
		return emf;
	}

	/**
	 * 
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		return getEntityManagerFactory(GenericDAO.defaultPersistenceUnitName);
	}

	/**
	 * Gets EntityManager for current thread. When finished, users must return
	 * session using {@link #closeEntityManager() closeEntityManager()} method.
	 * This method compatible with
	 * getEntityManagerFactory().getCurrentEntityManager() or
	 * getEntityManagerFactory(null).getCurrentEntityManager();
	 * 
	 * @return EntityManager for current thread.
	 */
	public static EntityManager getEntityManager(String persistenceUnitName) {
		ThreadLocal<EntityManager> threadLocal = getThreadLocalVariable(persistenceUnitName);
		EntityManager entityManager = threadLocal.get();
		if (entityManager == null) {
			entityManager = getEntityManagerFactory(persistenceUnitName).createEntityManager();
			threadLocal.set(entityManager);
		}
		return entityManager;
	}

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public static EntityManager getEntityManager() {
		if(em == null){
			GenericDAO.emf = Persistence.createEntityManagerFactory(GenericDAO.defaultPersistenceUnitName);
			GenericDAO.em = GenericDAO.emf.createEntityManager();
		}
		return getEntityManager(GenericDAO.defaultPersistenceUnitName);
	}

	/**
	 * Closes the EntityManager. Users must call this method after calling
	 * {@link #getEntityManager() getEntityManager()}.
	 */
	public static void closeEntityManager(String persistenceUnitName) {
		ThreadLocal<EntityManager> threadLocal = getThreadLocalVariable(persistenceUnitName);
		EntityManager entityManager = threadLocal.get();
		if (entityManager != null) {
			entityManager.close();
			threadLocal.remove();
		}
	}

	/**
	 * 
	 * @
	 */
	public static void closeEntityManager() {
		closeEntityManager(GenericDAO.defaultPersistenceUnitName);
	}

	/**
	 * 
	 * @
	 */
	public static void beginTransaction(String persistenceUnitName) {
		getEntityManager(persistenceUnitName).getTransaction().begin();
	}

	/**
	 * 
	 * @
	 */
	public static void beginTransaction() {
		getEntityManager(GenericDAO.defaultPersistenceUnitName).getTransaction().begin();
	}

	/**
	 * 
	 * @
	 */
	public static void commitTransaction(String persistenceUnitName) {
		getEntityManager(persistenceUnitName).getTransaction().commit();
	}

	/**
	 * 
	 * @
	 */
	public static Object merge(Object obj) {
		return getEntityManager().merge(obj);
	}

	/**
	 * 
	 * @
	 */
	public static void remove(Object obj) {
		getEntityManager().remove(obj);
	}

	/**
	 * 
	 * @
	 */
	public static void commitTransaction() {
		getEntityManager(GenericDAO.defaultPersistenceUnitName).getTransaction().commit();
	}

	/**
	 * 
	 * @
	 */
	public static void rollbackTransaction(String persistenceUnitName) {
		getEntityManager(persistenceUnitName).getTransaction().rollback();
	}

	/**
	 * 
	 * @
	 */
	public static void rollbackTransaction() {
		getEntityManager(GenericDAO.defaultPersistenceUnitName).getTransaction().rollback();
	}

	/**
	 * 
	 * @param persistenceUnitName
	 * @return
	 */
	private static synchronized ThreadLocal<EntityManager> getThreadLocalVariable(String persistenceUnitName) {
		ThreadLocal<EntityManager> threadLocal = ENTITY_MANAGER_MAP.get(persistenceUnitName);
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<EntityManager>();
			ENTITY_MANAGER_MAP.put(persistenceUnitName, threadLocal);
		}
		return threadLocal;
	}

}
