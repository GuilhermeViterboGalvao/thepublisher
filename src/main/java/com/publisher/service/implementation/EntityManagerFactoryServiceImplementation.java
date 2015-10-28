package com.publisher.service.implementation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import com.publisher.service.EntityManagerFactoryService;

public class EntityManagerFactoryServiceImplementation implements EntityManagerFactoryService {

	private EntityManagerFactory emf;
	
	@PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }	
	
	@Override
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
