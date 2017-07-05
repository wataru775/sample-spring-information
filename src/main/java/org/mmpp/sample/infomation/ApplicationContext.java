package org.mmpp.sample.infomation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="info.application")
public class ApplicationContext {

        private String name;

        private String version;
}
