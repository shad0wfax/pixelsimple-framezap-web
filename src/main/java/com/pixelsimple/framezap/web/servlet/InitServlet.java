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
import com.pixelsimple.transcoder.init.TranscoderInitializer;

/**
 *
 * @author Akshay Sharma
 * Feb 18, 2012
 */
public class InitServlet extends HttpServlet {
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(InitServlet.class);
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		LOG.debug("initializing servlet");
		try {
			BootstrapInitializer initializer = new BootstrapInitializer();
			Map<String, String> configMap = initializer.bootstrap();

			AppInitializer appInitializer = new AppInitializer();

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
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println("<h1>No Quarter</h1>");
//        response.getWriter().println("session=" + request.getSession(true).getId());
		
		request.getRequestDispatcher("/framezap.jsp").forward(request, response);
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
