package map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Map;

public class DistributedMapDemo {
    public static void main(String[] args) {
        HazelcastInstance hzInstance1 = Hazelcast.newHazelcastInstance();
        HazelcastInstance hzInstance2 = Hazelcast.newHazelcastInstance();
        HazelcastInstance hzInstance3 = Hazelcast.newHazelcastInstance();

        IMap<Integer, String> distributedMap = hzInstance1.getMap( "distributed_map" );
        for (int i = 0; i < 1000; i++) {
            distributedMap.put(i, "Test");
        }
    }
}
