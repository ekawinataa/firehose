package com.gojek.esb.config;


import com.gojek.esb.config.converter.RangeToHashMapConverter;
import org.aeonbits.owner.Config;

import java.util.Map;

public interface GrpcConfig extends AppConfig {


    @Config.Key("SERVICE_HOST")
    String getServiceHost();

    @Config.Key("SERVICE_PORT")
    Integer getServicePort();

    @Config.Key("CONNECTION_POOL_SIZE")
    @Config.DefaultValue("1")
    Integer getConnectionPoolSize();

    @Config.Key("GRPC_METHOD_URL")
    String getGrpcMethodUrl();

    @Config.Key("CONNECTION_POOL_MAX_IDLE")
    @Config.DefaultValue("1")
    Integer getConnectionPoolMaxIdle();

    @Config.Key("CONNECTION_POOL_MIN_IDLE")
    @Config.DefaultValue("1")
    Integer getConnectionPoolMinIdle();

    @Config.Key("CONNECTION_POOL_MAX_WAIT_MILLIS")
    @Config.DefaultValue("500")
    Integer getConnectionPoolMaxWaitMillis();

    @Config.Key("CONSUL_SERVICE_DISCOVERY")
    @Config.DefaultValue("false")
    Boolean getConsulServiceDiscovery();

    @Config.Key("CONSUL_CLIENT_HOST")
    @Config.DefaultValue("localhost")
    String getConsulHost();

    @Config.Key("CONSUL_CLIENT_PORT")
    @Config.DefaultValue("8500")
    Integer getConsulPort();

    @Config.Key("CONSUL_SERVICE_NAME")
    String getConsulServiceName();

    @Config.Key("CONSUL_OVERLOADED_THRESHOLD")
    @Config.DefaultValue("40")
    float getConsulOverloadedThreshold();

    @Config.Key("GRPCSINK_RETRY_STATUS_CODE_RANGES")
    @Config.DefaultValue("500-500")
    @Config.ConverterClass(RangeToHashMapConverter.class)
    Map<Integer, Boolean> retryStatusCodeRanges();

}
