package com.publisher.service;

import javax.persistence.EntityManager;

public interface EntityManagerFactoryService {

	EntityManager getEntityManager();
}