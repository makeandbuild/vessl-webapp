package com.makeandbuild.vessl.sample.fixture;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.makeandbuild.vessl.fixture.Fixture;

public class FixtureUtil {
    public static void main(String[] args) throws IOException{
        String command = args[0];
        new FixtureUtil(command);
        System.exit(0);
    }
    @SuppressWarnings("resource")
    public FixtureUtil(String command) throws IOException{
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:applicationContext*.xml");
        if (command.equals("seedload")){
            Fixture seedFixture = (Fixture) ctx.getBean("seedFixture");
            seedFixture.load();
        } else if (command.equals("seedpurge")){
            Fixture seedFixture = (Fixture) ctx.getBean("seedFixture");
            seedFixture.purge();
        } else if (command.equals("megaload")){
          Fixture megaFixture = (Fixture) ctx.getBean("megaFixture");
            megaFixture.load();
        } else if (command.equals("megapurge")){
            Fixture megaFixture = (Fixture) ctx.getBean("megaFixture");
            megaFixture.purge();
        } else if (command.equals("load")){
            Fixture fixture = (Fixture) ctx.getBean("fixture");
            fixture.load();
        } else if (command.equals("purge")){
            Fixture fixture = (Fixture) ctx.getBean("fixture");
            fixture.purge();
        } else {
            throw new RuntimeException("command "+command +" not known.  Please use load or purge");
        }

    }
}
