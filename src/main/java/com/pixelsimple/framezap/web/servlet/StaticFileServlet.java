package com.pixelsimple.framezap.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.framezap.web.util.AppUtil;

/**
 * Servlet implementation class FileServlet
 */
public class StaticFileServlet extends AbstractServletHelper {
	private static final long serialVersionUID = 1L;
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(StaticFileServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
		String inputFilePath = request.getParameter("inputPath");
		String type = request.getParameter("contentType");
		
		LOG.debug("handle::inputFilePath = {} ", inputFilePath);
//		System.out.println("sysout::handle::inputFilePath =" + inputFilePath);
		
		if (inputFilePath == null)
			return;
		
		if (type == null) {
			type = AppUtil.getMimeType(inputFilePath);
		}
		LOG.debug("handle::content type = {} ", type);
//		System.out.println("sysout::handle::content type =" + type);
		
//		response.setHeader("Content-Type", type);
		response.setContentType(type);
//		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Transfer-Encoding", "chunked");
		
		LOG.debug("handle::File = {} ", inputFilePath);
//		System.out.println("sysout::handle::File =" + inputFilePath);
		
		request.setAttribute("serving", "inprogress");
		
		// Assume the content length of the file is unknown (it might be getting written)
		serveStaticFile(inputFilePath, request, response, false);
		
	}

}
