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

import com.pixelsimple.appcore.queue.QueueService;
import com.pixelsimple.transcoder.Handle;
import com.pixelsimple.transcoder.TranscodeStatus;

/**
 * Servlet implementation class FileServlet
 */
public class TranscodingFileServlet extends AbstractServletHelper {
	private static final long serialVersionUID = 1L;
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(TranscodingFileServlet.class);

    /**
     * Default constructor. 
     */
    public TranscodingFileServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Give 3s delay to allow the transcoding to start. Should be configurable.
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			LOG.error("{}", e);
		}
		
//		String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
		String filename = request.getParameter("mediaPath");
		String type = (String) request.getParameter("contentType");
		String handleId = (String) request.getParameter("handleId");
		
		Handle handle = new Handle(handleId);
		TranscodeStatus status = QueueService.getQueue().peek(handle);
		
		if (status == null) {
			// Sleep for 1s, so that status could be init
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOG.error("{}", e);
			}
			status = QueueService.getQueue().peek(handle);
		}
		
		if (status == null) {
			// Might indicate that transcoding has already completed!. So just serve the file.
			serveStaticFile(filename, request, response, true);
			response.sendRedirect("/static_media.jsp?inputPath=" + filename);
			return;
		}
		File file = new File(filename);
		
//		response.setHeader("Content-Type", type);
		response.setContentType(type);
//		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Transfer-Encoding", "chunked");
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		
		LOG.debug("File = {} ", filename);
		
		try {
		    input = new BufferedInputStream(new FileInputStream(file));
		    output = new BufferedOutputStream(response.getOutputStream());
		
//		    byte[] buffer = new byte[8192];
//		    for (int length = 0; (length = input.read(buffer)) > 0;) {
//		        output.write(buffer, 0, length);
//		    }
		    
//		    while ((status.getStatus() == TranscodeStatus.STATUS.notstarted) 
//		    		|| (status.getStatus() == TranscodeStatus.STATUS.running)) {
//		        byte[] bytes = new byte[8192];
//		        int readN = input.read(bytes);
//		        if (readN > 0) {
//		        	output.write(bytes, 0, readN);
//		        }
//		        try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					LOG.error("{}", e);
//					break;
//				}
//		    }
		    
		    while (true) {
		        byte[] bytes = new byte[8192];
		        int readN = -1;
		    	
		    	if (status.getStatus() == TranscodeStatus.STATUS.completed 
		    			|| (status.getStatus() == TranscodeStatus.STATUS.failed)) {
		    		// Read until end of file and break
		    		while ((readN = input.read(bytes)) != -1) {
		    			output.write(bytes, 0, readN);
		    		}
		    		break;
		    	} else {
		    		// keep reading until status become one of the above
			        readN = input.read(bytes);
			        if (readN > 0) {
			        	output.write(bytes, 0, readN);
			        }
		    	}
		    	
		    	
		    }
		    
		    
		    
			LOG.debug("Completed serving the file");
		} finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		}
		
	}

}
