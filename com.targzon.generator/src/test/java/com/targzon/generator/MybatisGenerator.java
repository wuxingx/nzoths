package com.targzon.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {

	public static void main(String[] args) {

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		try {
			File configurationFile = new File("src/main/resources/generatorConfig.xml");
			if (configurationFile.exists() && configurationFile.isFile()) {
				ConfigurationParser cp = new ConfigurationParser(warnings);
				Configuration config = cp.parseConfiguration(configurationFile);
				DefaultShellCallback shellCallback = new DefaultShellCallback(overwrite);
				MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
				myBatisGenerator.generate(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
