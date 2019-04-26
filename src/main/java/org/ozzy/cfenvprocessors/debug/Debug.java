package org.ozzy.cfenvprocessors.debug;

import com.mongodb.client.MongoDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class Debug implements CommandLineRunner{

    @Autowired
    MongoDbFactory f;

    @Override
    public void run(String... args) throws Exception {

            System.out.println("Debug dump of mongo ::");
            MongoDatabase md = f.getDb();
            System.out.println(md.getName());
        
    }

}