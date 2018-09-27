package concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapTest {

    @Test
    public void testConcurrentSkipListMap(){
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for(int i = 0; i < 10; i++){
            map.put(i,i);
        }
        for(Integer key : map.keySet()){
            System.out.println(key + " : " + map.get(key));
        }
    }
}
