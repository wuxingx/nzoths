package com.nzoths.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {

	public static void main(String[] args) {

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		try {
			File configurationFile = new File(MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").getPath());
            FileInputStream fileInputStream = new FileInputStream(configurationFile);
            byte[] a = new byte[1024];
            int i = 0;
            while ((i = fileInputStream.read(a)) != -1) {
                System.out.write(a,0,i);
            }
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
