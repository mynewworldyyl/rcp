package net.techgy.community;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestOpenJpa {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void doTest() {
		System.out.println("=====Hello OpenJPA " + entityManager + "=======");
	}
	

	@Test
	@Transactional
	@Rollback(false)
	public void doPersist() {
	  
	}


}
