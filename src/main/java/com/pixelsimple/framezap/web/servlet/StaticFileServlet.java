package com.pixelsimple.framezap.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.commons.media.Container;
import com.pixelsimple.commons.media.MediaInspector;
import com.pixelsimple.commons.media.exception.MediaException;

/**
 * Servlet implementation class FileServlet
 */
public class StaticFileServlet extends AbstractServletHelper {
	private static final long serialVersionUID = 1L;
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(FileServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
		String inputFilePath = request.getParameter("inputPath");
		
		LOG.debug("handle::inputFilePath = {} ", inputFilePath);
//		System.out.println("sysout::handle::inputFilePath =" + inputFilePath);
		
		if (inputFilePath == null)
			return;
		
		String type = null;
		
		// TODO: what is the best way to get the media type? The container format can be wrong :(
//		try {
//			Container container = new MediaInspector().createMediaContainer(inputFilePath);
//			String format = container.getContainerFormat();
//			
//			if (format.indexOf(",") != -1) {
//				format = format.substring(0, format.indexOf(","));
//			}
//			type = "video/" + format;
//
//			LOG.debug("content type = {} ", type);
//
//			
//		} catch (MediaException e) {
//			LOG.error("{}", e);
//			throw new ServletException("Invalid input file");
//		}
		
		
		type = "video/" + inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
		LOG.debug("handle::content type = {} ", type);
//		System.out.println("sysout::handle::content type =" + type);
		
		response.setHeader("Content-Type", type);
//		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Transfer-Encoding", "chunked");
		
		LOG.debug("handle::File = {} ", inputFilePath);
//		System.out.println("sysout::handle::File =" + inputFilePath);
		
		request.setAttribute("serving", "inprogress");
		
		serveStaticFile(inputFilePath, request, response);
		
	}

}
