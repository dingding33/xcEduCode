import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDbTest {
    public static void main(String[] args) {
        //连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient("120.78.200.139", 27017);

        MongoDatabase local = mongoClient.getDatabase("local");

        System.out.println(local.getName());

    }
}
