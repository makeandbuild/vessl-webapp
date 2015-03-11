package com.makeandbuild.vessl.sample.fixture;

import java.io.IOException;

import org.testng.annotations.Test;

@Test(groups = { "function" })
public class FixtureUtil_IT {
    @Test(enabled = true)
    public void testLoad() throws IOException{
        new FixtureUtil("purge");
        new FixtureUtil("load");
    }
}
