package yume190.com.tester10.test;

import android.os.Handler;
import android.os.Message;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;


@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class HandlerTest extends TestCase{

    int counter;
    Runnable runnable;
    Handler handler;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        counter = 0;

        runnable = new Runnable() {
            @Override
            public void run() {
                counter++;
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        counter += 10;
                        break;
                    default:
                        counter += msg.what;
                        break;
                }
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        runnable = null;
        handler = null;
        super.tearDown();
    }

    @Test
    public void testM1() throws Exception{
        assertEquals(0,counter);

        handler.sendEmptyMessage(0);

        assertEquals(10, counter);

        handler.sendEmptyMessageDelayed(0, 0);
        assertEquals(20,counter);

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0, 1000);
        handler.sendEmptyMessageDelayed(0,10000);

        assertEquals(20, counter);
    }

    @Test
    public void testR1() throws Exception{
        assertEquals(0,counter);

        handler.post(runnable);

        assertEquals(1, counter);

        handler.postDelayed(runnable,0);
        assertEquals(2,counter);

        handler.postDelayed(runnable,1);
        handler.postDelayed(runnable,1000);
        handler.postDelayed(runnable,10000);

        assertEquals(2, counter);
    }

    @Test
    public void testM2() throws Exception{
        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        Robolectric.idleMainLooper(10000);
        assertEquals(30, counter);

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        ShadowLooper.idleMainLooper(10000);
        assertEquals(60, counter);

        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());
        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        shadowLooper.idle(10000);
        assertEquals(90, counter);
    }

    @Test
    public void testR2() throws Exception{
        handler.postDelayed(runnable,1);
        handler.postDelayed(runnable,1000);
        handler.postDelayed(runnable,10000);
        Robolectric.idleMainLooper(10000);
        assertEquals(3, counter);

        handler.postDelayed(runnable,1);
        handler.postDelayed(runnable,1000);
        handler.postDelayed(runnable,10000);
        ShadowLooper.idleMainLooper(10000);
        assertEquals(6, counter);

        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());
        handler.postDelayed(runnable,1);
        handler.postDelayed(runnable,1000);
        handler.postDelayed(runnable,10000);
        shadowLooper.idle(10000);
        assertEquals(9, counter);
    }

    @Test
    public void testM3() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        assertEquals(10, counter);

        handler.sendEmptyMessageDelayed(0,1000);
        shadowLooper.runOneTask();
        assertEquals(20, counter);

        handler.sendEmptyMessageDelayed(0,10000);
        shadowLooper.runOneTask();
        assertEquals(30, counter);
    }

    @Test
    public void testR3() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.postDelayed(runnable,1);
        shadowLooper.runOneTask();
        assertEquals(1, counter);

        handler.postDelayed(runnable,1000);
        shadowLooper.runOneTask();
        assertEquals(2, counter);

        handler.postDelayed(runnable,10000);
        shadowLooper.runOneTask();
        assertEquals(3, counter);
    }

    @Test
    public void testM4() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.postDelayed(runnable,1);
        handler.postDelayed(runnable,1000);
        handler.postDelayed(runnable,10000);
        shadowLooper.runToEndOfTasks();
        assertEquals(3, counter);
    }

    @Test
    public void testR4() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        shadowLooper.runToEndOfTasks();
        assertEquals(30, counter);
    }

    @Test
    public void test5() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();
        assertEquals(10,counter);

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runToNextTask();
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();
        assertEquals(40,counter);
    }

    @Test
    public void test6() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        assertEquals(1,shadowLooper.getScheduler().getCurrentTime());

        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        assertEquals(2,shadowLooper.getScheduler().getCurrentTime());

        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        assertEquals(3,shadowLooper.getScheduler().getCurrentTime());

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1);
        shadowLooper.runOneTask();
        shadowLooper.runOneTask();
        shadowLooper.runOneTask();
        assertEquals(4,shadowLooper.getScheduler().getCurrentTime());
    }

    @Test
    public void test7() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        handler.sendEmptyMessageDelayed(0,20000);
        shadowLooper.runOneTask();
        handler.removeCallbacksAndMessages(null);
        assertEquals(10, counter);

        handler.sendEmptyMessageDelayed(0,1);
        handler.sendEmptyMessageDelayed(0,1000);
        handler.sendEmptyMessageDelayed(0,10000);
        handler.sendEmptyMessageDelayed(0,20000);

        shadowLooper.runOneTask();
        assertEquals(20, counter);
        assertEquals(2,shadowLooper.getScheduler().getCurrentTime());

        shadowLooper.runOneTask();
        assertEquals(20, counter);
        assertEquals(1000,shadowLooper.getScheduler().getCurrentTime());//r

        shadowLooper.runOneTask();
        assertEquals(30, counter);
        assertEquals(1001,shadowLooper.getScheduler().getCurrentTime());

        shadowLooper.runOneTask();
        assertEquals(30, counter);
        assertEquals(10000,shadowLooper.getScheduler().getCurrentTime());//r

        shadowLooper.runOneTask();
        assertEquals(40, counter);
        assertEquals(10001,shadowLooper.getScheduler().getCurrentTime());

        shadowLooper.runOneTask();
        assertEquals(40, counter);
        assertEquals(20000,shadowLooper.getScheduler().getCurrentTime());//r

        shadowLooper.runOneTask();
        assertEquals(50, counter);
        assertEquals(20001,shadowLooper.getScheduler().getCurrentTime());
    }

    @Test
    public void test7a() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runToNextTask();
        assertEquals(30, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();

        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runToNextTask();
        handler.sendEmptyMessageDelayed(10,1);
        assertEquals(50, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();
        assertEquals(3,shadowLooper.getScheduler().getCurrentTime());

        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runToNextTask();
        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        assertEquals(60, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();
    }

    @Test
    public void test7b() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());

        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runOneTask();
        assertEquals(10, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();

        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runOneTask();
        handler.sendEmptyMessageDelayed(10,1);
        assertEquals(20, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();

        handler.sendEmptyMessageDelayed(10,1);
        shadowLooper.runOneTask();
        handler.sendEmptyMessageDelayed(10,1);
        handler.sendEmptyMessageDelayed(10,1);
        assertEquals(30, counter);
        handler.removeCallbacksAndMessages(null);
        shadowLooper.runToEndOfTasks();
    }

    @Test
    public void test8() throws Exception{
        ShadowLooper shadowLooper = Robolectric.shadowOf(handler.getLooper());
        shadowLooper.idleConstantly(true);

        handler.sendEmptyMessageDelayed(0,1);
        assertEquals(10, counter);
        handler.sendEmptyMessageDelayed(0,1000);
        assertEquals(20, counter);
        handler.sendEmptyMessageDelayed(0,10000);
        assertEquals(30, counter);
        handler.sendEmptyMessageDelayed(0,20000);
        assertEquals(40, counter);

        assertEquals(0,shadowLooper.getScheduler().getCurrentTime());
    }

}