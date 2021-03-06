package org.jenkinsci.plugins.variant;

import hudson.model.Action;
import org.jenkinsci.plugins.variant.pkg.Negative5;
import org.jenkinsci.plugins.variant.pkg.Positive5;
import org.jenkinsci.plugins.variant.pkg2.Negative6;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.Issue;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kohsuke Kawaguchi
 */
public class VariantTest extends Assert {
    @Rule
    public JenkinsRule j = new JenkinsRule();

    @BeforeClass
    public static void setUp() {
        VariantSet.INSTANCE = new VariantSet("test");
    }

    @AfterClass
    public static void tearDown() {
        VariantSet.INSTANCE = new VariantSet();
    }

    @Test
    public void test() {
        List<Class> classes = new ArrayList<Class>();
        for (Action a : j.jenkins.getActions()) {
            classes.add(a.getClass());
        }
        assertTrue(classes.contains(Positive1.class));
        assertTrue(classes.contains(Positive2.class));
        assertTrue(classes.contains(Positive3.class));
        assertTrue(classes.contains(Positive5.class));
        assertTrue(!classes.contains(Negative1.class));
        assertTrue(!classes.contains(Negative2.class));
        assertTrue(!classes.contains(Negative5.class));
        assertTrue(!classes.contains(Negative6.class));
    }

    @Test
    @Issue("JENKINS-37317")
    public void testRequiredClass() {
        List<Class> classes = new ArrayList<Class>();
        for (Action a : j.jenkins.getActions()) {
            classes.add(a.getClass());
        }
        assertTrue("Negative 3 should not exist", !classes.contains(Negative3.class));

        for (Class klass : classes) {
            System.out.println(klass.getCanonicalName());
            assertTrue("Negative 4 should not exist",
                    !klass.getCanonicalName().equals("org.jenkinsci.plugins.variant.Negative4"));
        }

        assertTrue(classes.contains(Positive4.class));
    }

}
