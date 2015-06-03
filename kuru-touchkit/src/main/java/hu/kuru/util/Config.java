package hu.kuru.util;

import java.nio.file.Path;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class Config {
	private PropertiesConfiguration config;

	public Config(Path configPath) throws ConfigurationException {
		config = new PropertiesConfiguration(configPath.toFile());
		FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
		strategy.setRefreshDelay(60_000);
		config.setReloadingStrategy(strategy);
		config.load();
	}

	public String get(String topic) {
		Iterator<String> keys = config.getKeys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (topic.matches(key)) {
				return config.getString(key);
			}
		}
		return null;
	}

}
