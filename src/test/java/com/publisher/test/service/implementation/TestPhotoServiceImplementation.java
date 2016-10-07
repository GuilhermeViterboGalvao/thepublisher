package com.publisher.test.service.implementation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.publisher.entity.Account;
import com.publisher.entity.Photo;
import com.publisher.service.AccountService;
import com.publisher.service.PhotoService;
import com.publisher.test.config.DefaultTest;
import com.publisher.utils.ResultList;

public class TestPhotoServiceImplementation extends DefaultTest<Photo> implements PhotoService {

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private AccountService accountService;
	
	private Account entityAccount;
	
	@Before
	@Override
	public void init() {
		assertNotNull(photoService);
		
		entity = new Photo();
		entity.setCreated(new Date());
		
		entityAccount = new Account();
		entityAccount.setActive(true);
		entityAccount.setEmail("test@junit.com");
		entityAccount.setHash(accountService.hash("123test456"));
		entityAccount.setName("Test JUnit");
		entityAccount.setSecurityHole("admin");
		accountService.persist(entityAccount);
		entity.setCreatedBy(entityAccount);
		
		entity.setCredits("Test Photo for JUnit...");
		entity.setDate(new Date());
		entity.setDescription("Test Photo for JUnit...");
		entity.setEvent(false);
		entity.setGym(false);
		entity.setHeight(250);
		entity.setHorizontalCenter(0.5f);
		entity.setLastModified(new Date());
		entity.setLastModifiedBy(entityAccount);
		entity.setPublished(true);
		entity.setTags("test photo junit");
		entity.setVerticalCenter(0.5f);
		entity.setWidth(300);
	}

	@Test
	@Override
	public void testIt() {
		persist(entity);
		
		persistedEntity = get(entity.getId());
		
		persistedEntity.setCredits("Category Test for JUnit...");
		update(persistedEntity);
		
		count();
		
		count(true);
		
		list();
		
		list(0, 50);
		
		list(0, 50, new Date());
		
		list(0, 50, new Date(), true);
		
		search("Test");
		
		search("Test", 0, 50);
		
		//TODO
		//setPictureToUploadFolder(picture)
		//setPictureToUploadFolder(picture, dir);
		//getPictureFromUploadFolder(id);
		//getPictureFromUploadFolder(id, dir);
		//removePictureFromUploadFolder(id);
		//removePictureFromUploadFolder(id, dir);
		//removePictureFromUploadTempFolder(id);
		//removePictureFromUploadTempFolder(id, dir);
	}

	@After
	@Override
	public void finish() {
		delete(persistedEntity);
		persistedEntity = photoService.get(entity.getId());
		assertNull(persistedEntity);
		
		Long id = entityAccount.getId();
		accountService.delete(entityAccount);
		assertNull(accountService.get(id));
	}
	
	@Override
	public Photo get(Long id) {
		assertNotNull(id);
		Photo photo = photoService.get(id);
		assertNotNull(photo);
		return photo;
	}

	@Override
	public void persist(Photo entity) {
		assertNotNull(entity);
		photoService.persist(entity);
	}

	@Override
	public void update(Photo entity) {
		assertNotNull(entity);
		photoService.update(entity);
	}

	@Override
	public void delete(Photo entity) {
		assertNotNull(entity);
		photoService.delete(entity);
	}

	@Override
	public Collection<Photo> list() {
		Collection<Photo> list = photoService.list();
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Photo> search(String query) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		Collection<Photo> search = photoService.search(query);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count() {
		long qtd = photoService.count();
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public void indexAll() {
		//photoService.indexAll();
	}

	@Override
	public void persist(Photo photo, File picture) {
		assertNotNull(photo);
		assertNotNull(picture);
		assertTrue(picture.exists());
		assertTrue(picture.isFile());
		photoService.persist(photo, picture);
	}

	@Override
	public void update(Photo photo, File picture) {
		assertNotNull(photo);
		assertNotNull(picture);
		assertTrue(picture.exists());
		assertTrue(picture.isFile());
		photoService.update(photo, picture);
	}

	@Override
	public Collection<Photo> list(int page, int pageSize) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		Collection<Photo> list = photoService.list(page, pageSize);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Photo> list(int page, int pageSize, Date publishedUntil) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(publishedUntil);
		Collection<Photo> list = photoService.list(page, pageSize, publishedUntil);
		assertNotNull(list);
		return list;
	}

	@Override
	public Collection<Photo> list(int page, int pageSize, Date publishedUntil, Boolean published) {
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		assertNotNull(publishedUntil);
		assertNotNull(published);
		Collection<Photo> list = photoService.list(page, pageSize, publishedUntil, published);
		assertNotNull(list);
		return list;
	}

	@Override
	public ResultList<Photo> search(String query, int page, int pageSize) {
		assertNotNull(query);
		assertTrue(!query.isEmpty());
		assertTrue(page >= 0);
		assertTrue(pageSize >= 0);
		ResultList<Photo> search = photoService.search(query, page, pageSize);
		assertNotNull(search);
		return search;
	}

	@Override
	public long count(Boolean published) {
		assertNotNull(published);
		long qtd = photoService.count(published);
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public long setPictureToUploadFolder(File picture) {
		assertNotNull(picture);
		long qtd = photoService.setPictureToUploadFolder(picture);
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public long setPictureToUploadFolder(File picture, File dir) {
		assertNotNull(picture);
		assertNotNull(dir);
		long qtd = photoService.setPictureToUploadFolder(picture, dir);
		assertTrue(qtd >= 0);
		return qtd;
	}

	@Override
	public File getPictureFromUploadFolder(long id) {
		assertTrue(id >= 0);
		File file = photoService.getPictureFromUploadFolder(id);
		assertNotNull(file);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		return file;
	}

	@Override
	public File getPictureFromUploadFolder(long id, File dir) {
		assertTrue(id >= 0);
		assertNotNull(dir);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		File file = photoService.getPictureFromUploadFolder(id, dir);
		assertNotNull(file);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		return file;
	}

	@Override
	public void removePictureFromUploadFolder(long id) {
		assertTrue(id >= 0);
		photoService.removePictureFromUploadFolder(id);
	}

	@Override
	public void removePictureFromUploadFolder(long id, File dir) {
		assertTrue(id >= 0);
		assertNotNull(dir);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		photoService.removePictureFromUploadFolder(id, dir);
	}

	@Override
	public void removePictureFromUploadTempFolder(long id) {
		assertTrue(id >= 0);
		photoService.removePictureFromUploadTempFolder(id);
	}

	@Override
	public void removePictureFromUploadTempFolder(long id, File dir) {
		assertTrue(id >= 0);
		assertNotNull(dir);
		assertTrue(dir.exists());
		assertTrue(dir.isDirectory());
		photoService.removePictureFromUploadTempFolder(id, dir);
	}
}