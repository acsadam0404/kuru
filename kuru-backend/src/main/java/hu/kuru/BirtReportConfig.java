package hu.kuru;

import hu.si.birt.XmlReportExecutor;

import java.nio.file.Paths;

import org.eclipse.birt.core.exception.BirtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BirtReportConfig {
	@Value("${birt.input}")
	private String birtInput;
	@Value("${birt.output}")
	private String birtOutput;
	
	@Bean
	public XmlReportExecutor xmlReportExecutor() {
		try {
			return new XmlReportExecutor(Paths.get(birtInput), Paths.get(birtOutput));
		} catch (BirtException e) {
			e.printStackTrace();
		}
		return null;
	}
}
