package queue;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class QueueReadDemo {
    public static void main( String[] args ) throws Exception {
        Config config = new Config();
        QueueConfig queueConfig = new QueueConfig();
        queueConfig.setName("queue").setMaxSize(10);
        config.addQueueConfig(queueConfig);

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
        IQueue<Integer> queue = hz.getQueue( "queue" );
        while ( true ) {
            int item = queue.take();
            System.out.println( "Consumed: " + item );
            if ( item == -1 ) {
                queue.put( -1 );
                break;
            }
            Thread.sleep( 5000 );
        }
        System.out.println( "Consumer Finished!" );
    }
}