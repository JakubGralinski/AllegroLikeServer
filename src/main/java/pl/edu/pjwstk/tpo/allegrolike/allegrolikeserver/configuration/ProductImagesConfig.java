package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "product-images")
public class ProductImagesConfig {

    private String storageDirName;

    public String getStorageDirName() {
        return storageDirName;
    }

    public void setStorageDirName(String storageDirName) {
        this.storageDirName = storageDirName;
    }
}
