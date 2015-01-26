package yume190.com.tester10.test;/**
 * Created by yume on 15/1/26.
 */

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import yume190.com.tester10.R;

// Robolectric
// http://robolectric.org/resource-qualifiers/

// Qualifier List
// http://developer.android.com/guide/topics/resources/providing-resources.html#table2

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class QualifierStringsTest extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @After
    public void tearDown() throws Exception {

        super.tearDown();
    }

    @Test
    @Config(qualifiers="fr")
    public void testHelloFranch() throws Exception {
        assertEquals("bonjour", Robolectric.application.getString(R.string.hello));

        assertEquals("Tester10", Robolectric.application.getString(R.string.app_name));
    }

    @Test
    @Config(qualifiers="ja")
    public void testHelloJapan() throws Exception {
        assertEquals("こんにちは", Robolectric.application.getString(R.string.hello));

        assertEquals("Tester10", Robolectric.application.getString(R.string.app_name));
    }

    @Test
    @Config(qualifiers="zh")
    public void testHelloChinese() throws Exception {
        assertEquals("你好", Robolectric.application.getString(R.string.hello));

        assertEquals("Tester10", Robolectric.application.getString(R.string.app_name));
    }

    @Test
    public void testHellodefault() throws Exception {
        assertEquals("hello", Robolectric.application.getString(R.string.hello));

        assertEquals("Tester10", Robolectric.application.getString(R.string.app_name));
    }
}