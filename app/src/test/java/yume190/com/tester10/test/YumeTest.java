package yume190.com.tester10.test;

import android.test.AndroidTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class YumeTest extends AndroidTestCase {

    @Test
    public void testYume1() throws Exception{
        assertEquals(2,1);
    }

    @Test
    public void testYume2() throws Exception{
        assertEquals(1,1);
    }

}