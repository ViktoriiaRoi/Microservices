package queue;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class QueueWriteDemo {
    public static void main( String[] args ) throws Exception {
        Config config = new Config();
        QueueConfig queueConfig = new QueueConfig();
        queueConfig.setName("queue").setMaxSize(10);
        config.addQueueConfig(queueConfig);

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
        IQueue<Integer> queue = hz.getQueue( "queue" );
        for ( int k = 1; k < 100; k++ ) {
            queue.put( k );
            System.out.println( "Producing: " + k );
            Thread.sleep(1000);
        }
        queue.put( -1 );
        System.out.println( "Producer Finished!" );
    }
}