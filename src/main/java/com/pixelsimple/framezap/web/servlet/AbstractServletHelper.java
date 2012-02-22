package com.pixelsimple.framezap.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class FileServlet
 */
abstract public class AbstractServletHelper extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractServletHelper.class);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handle(request, response);
	}
	
	abstract protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	/**
	 * @param request
	 * @param response
	 */
	protected void serveStaticFile(String filename, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		
		LOG.debug("serveStaticFile::Serving static File = {} ", filename);
		
		try {
			File file = new File(filename);
			
			response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			
			if (!file.isFile() || !file.exists()) {
				throw new ServletException("Looks like the file to serve is not valid");
			}
			
			input = new BufferedInputStream(new FileInputStream(file));
		    output = new BufferedOutputStream(response.getOutputStream());
		
//		    byte[] buffer = new byte[8192];
//		    for (int length = 0; (length = input.read(buffer)) > 0;) {
//		        output.write(buffer, 0, length);
//		    }
//		    
//		    
		    while (true) {
		        byte[] bytes = new byte[8192];
		        int readN = -1;
	    		// keep reading until status become one of the above
		        readN = input.read(bytes);
		        if (readN > 0) {
		        	output.write(bytes, 0, readN);
		        } else {
		        	break;
		        }
	    	}

		    
		    
		    
		    
		    
		    
			LOG.debug("Completed serving the file");
		} finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		}
	}

}
