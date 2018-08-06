package net.techgy.community;

import java.net.UnknownHostException;
import java.util.Set;

import net.techgy.common.models.MessageImpl;
import net.techgy.common.models.MessageState;
import net.techgy.common.models.MessageType;
import net.techgy.common.models.MsgBody;
import net.techgy.common.models.MsgHeader;
import net.techgy.im.net.dao.IMessageDao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class TestMongoDB {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private MongoClient mongoClient = null;
	
	@Before
	public void initTest() throws UnknownHostException {
	    mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
	}
	
	@After
	public void afterTest() {
		mongoClient.close();
	}
	
	@Test
	public void testConnectDb() {
		 DB db = mongoClient.getDB( "mydb" );
        logger.debug("hello mongo");
	}
	
	@Test
	public void testGetCollection(){
		DB db = mongoClient.getDB( "test" );
    	DBCollection coll = db.getCollection("testCollection");
    	logger.debug(coll.getName());
	}
	
	@Test
	public void testGetCollections() {
        DB db = mongoClient.getDB( "test" );
        Set<String> colls = db.getCollectionNames();
    	for (String s : colls) {
    	    logger.debug(s);
    	}
	}
	
	@Test
	public void testInsert() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject doc = new BasicDBObject("_id", "MongoDB")
				.append("type", "database").append("count", 1)
				.append("info", new BasicDBObject("x", 203).append("y", 102));
		coll.insert(doc);
	}
	
	@Test
	public void testFindOne() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		logger.debug(coll.findOne());
	}
	
	@Test
	public void testInsertMores() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		for(int count=0; count < 10; count++) {
			BasicDBObject doc = new BasicDBObject("_id", count)
			.append("type", "database").append("count", 1);
	        coll.insert(doc);
		}
		
	}
	
	@Test
	public void testFindAll() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		DBCursor cursor = coll.find();
		while(cursor.hasNext()) {
			logger.debug(cursor.next());
		}
		cursor.close();
	}
	
	@Test
	public void testGetCount() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		logger.debug(coll.getCount());
		
	}
	
	@Test
	public void testWithQurey1() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject query = new BasicDBObject("_id", "MongoDB1");
		DBCursor cursor = coll.find(query);
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
		
	}
	
	@Test
	public void testWithQurey2() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject query = new BasicDBObject("_id",new BasicDBObject("$gte",8));
		DBCursor cursor = coll.find(query);
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
		
	}
	
	@Test
	public void testCreateIndex() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject query = new BasicDBObject("type",1);
		coll.createIndex(query);
	}
	
	@Test
	public void testGetIndexs() {
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		logger.debug(coll.getIndexInfo().toString());
	}
	
	@Test
	public void testGetDatabases() {
		logger.debug(mongoClient.getDatabaseNames().toString());	
	}
		
	@Test
	public void testGson() {
		Gson gson = new Gson();
	    
		String json = 
				"{\"type\":13,\"bodies\":{},\"headers\":{\"userName\":{\"name\":\"userName\",\"value\":\"test2\"},\"password\":{\"name\":\"password\",\"value\":\"test2\"},\"undefined\":{}}}";
		MessageImpl mi = gson.fromJson(json, MessageImpl.class);
		
		
	}
	
	@Test
	public void testMongoSave1() {
		MessageImpl m = new MessageImpl();
        m.setType(MessageType.REQ_ADD_FRIEND);
        
        m.setClientMessage(true);
        m.setStatus(MessageState.OK);
        
        m.addHeader("youname", "headervalue");
        
        MsgBody mb = new MsgBody();
        mb.setContent("Hell world");
        mb.setType(22);
        m.addBody(22, "Hell world");
        
        DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		//coll.save(m);		
	}
	
	
	@Test
	public void testMongoQuery1() {
		Gson gson = new Gson();
	    
		String json = 
				"{\"type\":13,\"bodies\":{},\"headers\":{\"userName\":{\"name\":\"userName\",\"value\":\"test2\"},\"password\":{\"name\":\"password\",\"value\":\"test2\"},\"undefined\":{}}}";
		MessageImpl mi = gson.fromJson(json, MessageImpl.class);
		
		
	}
	
	
	
	
	//{"type":13,"bodies":{},"headers":{"userName":{"name":"userName","value":"test2"},"password":{"name":"password","value":"test2"},"undefined":{}}}
}
