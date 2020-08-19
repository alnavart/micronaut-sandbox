package com.example.properties;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.env.PropertySource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgrammaticRunPropertiesLoadTest {

    private final Logger logger = LoggerFactory.getLogger(ProgrammaticRunPropertiesLoadTest.class);
    Map<String, Object> properties = Map.of(
            "app.string.sample", "expectedVal",
            "app.map.sample", Map.of("k1", "v1", "k2", "v2"),
            "app.array.sample", new String[]{"s1", "s2"});

    @Test
    void loadsStringValue(){
        ApplicationContext ctx = buildNewApplicationContext(properties);

        SampleConfig config = ctx.getBean(SampleConfig.class);
        assertEquals("expectedVal", config.sampleString);
    }

    @Test
    void loadsStringProp(){
        ApplicationContext ctx = buildNewApplicationContext(properties);

        SampleConfig config = ctx.getBean(SampleConfig.class);
        assertEquals("expectedVal", config.sampleStringProp);
    }

    @Test
    void loadsMapProp(){
        ApplicationContext ctx = buildNewApplicationContext(properties);

        SampleConfig config = ctx.getBean(SampleConfig.class);
        assertEquals(Map.of("k1", "v1", "k2", "v2"), config.sampleMapProp);
    }

    @Test
    void loadsArrayValue(){
        ApplicationContext ctx = buildNewApplicationContext(properties);

        SampleConfig config = ctx.getBean(SampleConfig.class);
        // This is weird
        assertArrayEquals(new String[]{"s1s2"}, config.sampleArray);
    }

    @Test
    void loadsArrayProp(){
        ApplicationContext ctx = buildNewApplicationContext(properties);

        SampleConfig config = ctx.getBean(SampleConfig.class);
        assertArrayEquals(new String[]{"s1", "s2"}, config.sampleArrayProp);
    }


    private ApplicationContext buildNewApplicationContext(Map<String, Object> config) {
        logger.debug("Micronaut test run Application Context with config {}", config);
        PropertySource properties = PropertySource.of(config);
        ApplicationContextBuilder acb = ApplicationContext.build()
                .propertySources(properties);
        ApplicationContext applicationContext = acb.build().start();
        logConfiguration(config);
        return applicationContext;
    }

    private void logConfiguration(Map<String, Object> config) {
        logger.debug("Full running test with configuration");
        PropertySource properties = PropertySource.of(config);
        for (String key : config.keySet()) {
            logger.debug("Config key [{}]: {}", key, properties.get(key));
        }
    }
}
