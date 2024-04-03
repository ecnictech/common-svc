package ecnic.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeTags({"services", "controller", "integration"})
@SelectPackages({
        "ecnic.service.services.impl",
        "ecnic.service.controllers",
        "ecnic.service.integration"
})
@IncludeClassNamePatterns(".*Test")
@Slf4j
public class SuiteTest {
}