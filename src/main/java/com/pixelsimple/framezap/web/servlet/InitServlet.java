/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.framezap.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.LogUtils;
import com.pixelsimple.commons.util.OSUtils;
import com.pixelsimple.commons.util.StringUtils;
import com.pixelsimple.transcoder.init.TranscoderInitializer;

/**
 *
 * @author Akshay Sharma
 * Feb 18, 2012
 */
public class InitServlet extends HttpServlet {
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(InitServlet.class);
	
	private static final String FRAMEZAP_LOGBACK_CONFIG_FILE = "framezap-logConfig.xml";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		LOG.debug("initializing servlet");
		try {
			// First thing to do is always init the log. This helps log even the app core bootstrapping. 
			initLog();
			
			BootstrapInitializer initializer = new BootstrapInitializer();
			Map<String, String> configMap = initializer.bootstrap();

			AppInitializer appInitializer = new AppInitializer();

			//Add all dependent initializers. A module initializing the app should know this.
			// Depends on Transcode initializer
			appInitializer.addModuleInitializable(new TranscoderInitializer());

			appInitializer.init(configMap);
		} catch (Exception e) {
			LOG.error("Error occurred initalizing the app with system properties passed as : \n\n Exiting the app", e);
			
			// TODO: Hook up a different way to indicate to the user that the system is going down.- Listener?
			System.exit(0);
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/framezap.jsp").forward(request, response);
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/*
	 * The log initialization happens with the assumption that a system property is set/passed during startup.
	 * Each app (ex: framezap/nova) can have its own log folders set. 
	 */
	private void initLog() {
		String appDir = System.getProperty(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR);
		
		// Do nothing, so logging is not initialized and will log to the console (logback's default way)
		if (StringUtils.isNullOrEmpty(appDir))
			return;
		
		// The log config for framezap will be in config folder.
		String logDir = OSUtils.appendFolderSeparator(appDir) + "config" + OSUtils.folderSeparator();
		
		// For framezap we will asume that the log configuration file is also located in the config directory.
		String logConfigFile = logDir + FRAMEZAP_LOGBACK_CONFIG_FILE;
		LogUtils.initLogFromConfigFile(logConfigFile);
	}
}
