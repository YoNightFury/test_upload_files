package com.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


// run during spring startup time
// spring container calls this

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

	
	
	
	// static path for folder independent of system "user home directory / .test_upload_storage"
	public static final String storagePath = System.getProperty("user.home") + "\\.test_upload_storage";
	private static final Logger LOG = LoggerFactory.getLogger(CommandLineRunnerImpl.class);
	
	@Override
	public void run(String... args) throws Exception {
		// if the path of the folder does not exists then create new folder

		
		File path = new File(storagePath);
//		if(!path.exists())
//			path.mkdir();
		LOG.info("created {} if not exists ",storagePath);
		if (!Files.exists(Paths.get(storagePath)))
			path.mkdir();
	}

}
