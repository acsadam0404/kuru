package hu.kuru.util;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.configuration.ConfigurationException;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

public class KuruUtils {
	
	public static String ARTICLEPICTUREPATH = "articlePicturePath";

	public static Image getArticleImageFromFileSystem(String fileName) {
		Image image = null;
		try {
			Config config = new Config(Paths.get("/resources/application.properties"));
			config.get(ARTICLEPICTUREPATH);
			FileResource resource = new FileResource(new File(getArticlePicturePathFromFileSystem() + fileName));
			image = new Image(null, resource);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static String getArticlePicturePathFromFileSystem() {
		try {
			Config config = new Config(Paths.get("/resources/application.properties"));
			return config.get(ARTICLEPICTUREPATH);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
