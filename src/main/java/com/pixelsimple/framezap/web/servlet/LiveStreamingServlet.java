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
 * Servlet implementation class LiveStreamingServlet
 */
public class LiveStreamingServlet extends AbstractServletHelper {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LiveStreamingServlet.class);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputFilePath = request.getParameter("inputPath");
		String type = request.getParameter("contentType");
		
		LOG.debug("handle::inputFilePath = {} ", inputFilePath);
		
		if (inputFilePath == null)
			return;
		
		if (type == null) {
			String extension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
			type = AppUtil.getMimeTypeFromExtension(extension);
		}
		LOG.debug("handle::content type = {} ", type);

		response.setContentType(type);
		response.setHeader("Transfer-Encoding", "chunked");
		
		LOG.debug("handle::File = {} ", inputFilePath);
//		System.out.println("sysout::handle::File =" + inputFilePath);
		
		request.setAttribute("serving", "inprogress");
		
		// The content length of the file will be known (Even for .m3u8 in any given request)
		serveStaticFile(inputFilePath, request, response, true);
	}

}
