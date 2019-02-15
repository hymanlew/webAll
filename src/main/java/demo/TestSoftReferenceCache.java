package demo;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试类
 * User: yfzhangbin
 * Date: 13-8-16
 * Time: 下午5:18
 */
public class TestSoftReferenceCache {

    private static final Logger log = Logger.getLogger(TestSoftReferenceCache.class);
    private static int MAX_COUNT = 100000;
    private static String KEY_PREFIX = "KEY_";
    private static SoftReferenceCache<String, byte[]> cache = new SoftReferenceCache<String, byte[]>();

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(new Customer());
        es.submit(new Customer());
        es.submit(new Customer());
        es.submit(new Customer());
        es.submit(new Customer());
        es.shutdown();
    }

    static class Customer implements Runnable {

        @Override
        public void run() {
            while (true) {
                for (int i = 0; i < MAX_COUNT; i ++) {
                    byte[] a = cache.get(KEY_PREFIX + i);
                    if (a == null) {
                        a = new byte[1024];
                        cache.put(KEY_PREFIX + i, a);
                        log.info(Thread.currentThread().getName() + " 向缓存池中添加对象[" + (KEY_PREFIX + i) + "]: " + a);
                    } else {
                        log.info(Thread.currentThread().getName() + " 从缓存池中获取对象[" + (KEY_PREFIX + i) + "]: " + a);
                    }
                }
            }
        }
    }
}
