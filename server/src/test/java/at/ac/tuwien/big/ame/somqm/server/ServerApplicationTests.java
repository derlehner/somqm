package at.ac.tuwien.big.ame.somqm.server;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("at.ac.tuwien.big.ame.somqm.server")
@IncludeClassNamePatterns("^.*(IT|UT)$")
public class ServerApplicationTests {

}
