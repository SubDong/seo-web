package com.groot.mongodb.base;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by subdong on 2015/1/7.
 */
public class BaseMongoTemplate {
    private static String DATABASE = "seo";
    private static String SYS_DATABASE = "sys";
    private static Mongo mongo;

    static {
        if (mongo == null) {
            InputStream ins = BaseMongoTemplate.class.getResourceAsStream("/mongodb.properties");
            Properties p = new Properties();
            try {
                p.load(ins);
                String hosts = p.getProperty("mongo.host");
                if (hosts != null && !hosts.equals("")) {
                    String[] hostArray = hosts.split(",");
                    List<ServerAddress> serverAddresses = new ArrayList<>();
                    for (String host : hostArray) {
                        String[] hostPort = host.split(":");
                        ServerAddress address = null;
                        if (hostPort.length == 1) {
                            address = new ServerAddress(hostPort[0]);
                        } else {
                            address = new ServerAddress(hostPort[0], Integer.parseInt(hostPort[1]));
                        }
                        serverAddresses.add(address);
                    }
                    mongo = new MongoClient(serverAddresses);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BaseMongoTemplate() {
    }

    public static MongoTemplate getMongoTemplate(String databaseName) {
//        ServerAddress address=null;
//        try {
//            address = new ServerAddress("182.150.24.24",23135);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        Mongo mongo=new MongoClient(address);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName);

        //remove _class
        MappingMongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongoDbFactory, mongoConverter);
    }


    public static MongoTemplate getSeoMongo() {
        return BaseMongoTemplate.getMongoTemplate(DATABASE);
    }

    public static MongoTemplate getSysMong() {
        return BaseMongoTemplate.getMongoTemplate(SYS_DATABASE);
    }

}
