package com.studiomediatech.contessa.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * Configuration properties for the Contessa UI modules.
 */
@ConfigurationProperties(prefix = "contessa.ui")
public class ContessaUIConfigurationProperties {

    @ConfigurationProperties(prefix = "contessa.ui.rest")
    public static class RestUiConfiguration {

        private boolean enabled = false;

        public boolean isEnabled() {

            return enabled;
        }


        public void setEnabled(boolean enabled) {

            this.enabled = enabled;
        }
    }
}
