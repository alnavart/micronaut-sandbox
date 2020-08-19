package com.example.properties;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;

import java.util.Map;

@Factory
public class SampleConfig {

    @Value("${app.string.sample}")
    public String sampleString;
    @Property(name ="app.string.sample")
    public String sampleStringProp;
    @Property(name = "app.map.sample")
    public Map<Object, Object> sampleMapProp;
    @Value("${app.array.sample}")
    public String[] sampleArray;
    @Property(name ="app.array.sample")
    public String[] sampleArrayProp;
}
