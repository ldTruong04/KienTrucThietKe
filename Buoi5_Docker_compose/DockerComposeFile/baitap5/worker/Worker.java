import redis.clients.jedis.Jedis;
import org.json.JSONObject;

import java.sql.*;

public class Worker {
    public static void main(String[] args) {
        String redisHost = System.getenv("REDIS_HOST") != null ? System.getenv("REDIS_HOST") : "redis";
        String pgHost = System.getenv("POSTGRES_HOST") != null ? System.getenv("POSTGRES_HOST") : "postgres";
        String pgUser = System.getenv("POSTGRES_USER") != null ? System.getenv("POSTGRES_USER") : "postgres";
        String pgPassword = System.getenv("POSTGRES_PASSWORD") != null ? System.getenv("POSTGRES_PASSWORD") : "postgres";
        String pgDb = System.getenv("POSTGRES_DB") != null ? System.getenv("POSTGRES_DB") : "votes";

        System.out.println("Worker starting...");

        // Wait for dependencies
        try { Thread.sleep(10000); } catch (Exception e) {}

        try {
            // Connect to PostgreSQL
            String jdbcUrl = "jdbc:postgresql://" + pgHost + ":5432/" + pgDb;
            Connection conn = DriverManager.getConnection(jdbcUrl, pgUser, pgPassword);
            System.out.println("Connected to PostgreSQL");

            // Create table if not exists
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS votes (id VARCHAR(255) PRIMARY KEY, vote VARCHAR(255) NOT NULL)"
            );
            System.out.println("Table 'votes' ready");

            // Connect to Redis
            Jedis jedis = new Jedis(redisHost, 6379);
            System.out.println("Connected to Redis");

            // Process votes
            while (true) {
                String voteJson = jedis.lpop("votes");
                if (voteJson != null) {
                    JSONObject voteData = new JSONObject(voteJson);
                    String voterId = voteData.getString("voter_id");
                    String vote = voteData.getString("vote");

                    PreparedStatement upsert = conn.prepareStatement(
                        "INSERT INTO votes (id, vote) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET vote = ?"
                    );
                    upsert.setString(1, voterId);
                    upsert.setString(2, vote);
                    upsert.setString(3, vote);
                    upsert.executeUpdate();
                    System.out.println("Processed vote: " + voterId + " -> " + vote);
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            System.err.println("Worker error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
