//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yaomy.control.conf;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 重写PropertiesPropertySourceLoader类，系统内部的PropertiesPropertySourceLoader类的优先级是2147483647，
 * 设置@Order注解，值是2147483646，优先级比系统自带的高
 * @Author 姚明洋
 * @Date 2019/11/28 19:03
 * @Version  1.0
 */
@Order(2147483646)
public class PropertiesPropertySourceLoader implements PropertySourceLoader {

    public PropertiesPropertySourceLoader() {
    }
    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties", "xml"};
    }
    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        Map<String, ?> properties = this.loadProperties(resource);
        return properties.isEmpty() ? Collections.emptyList() : Collections.singletonList(new OriginTrackedMapPropertySource(name, properties));
    }

    private Map<String, ?> loadProperties(Resource resource) throws IOException {
        String filename = resource.getFilename();
        return (Map)(filename != null && filename.endsWith(".xml") ? PropertiesLoaderUtils.loadProperties(resource) : (new OriginTrackedPropertiesLoader(resource)).load());
    }
}
