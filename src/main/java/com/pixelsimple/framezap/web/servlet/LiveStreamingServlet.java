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

/**
 * Servlet implementation class LiveStreamingServlet
 */
public class LiveStreamingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiveStreamingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String filePath = URLDecoder.decode(request.getPathInfo(), "UTF-8");
		String filePath = request.getPathInfo();
		
		System.out.println("File = " + filePath);
		
		String filename = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
				
		System.out.println("File name = " + filename);
		
//		String filename = request.getParameter("vidName");
		File file = new File("/Users/srivatsasharma/Downloads/ffmpeg", filename);
		
		response.setHeader("Content-Type", "video/mp4");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		
		
		
		try {
		    input = new BufferedInputStream(new FileInputStream(file));
		    output = new BufferedOutputStream(response.getOutputStream());
		
		    byte[] buffer = new byte[8192];
		    for (int length = 0; (length = input.read(buffer)) > 0;) {
		        output.write(buffer, 0, length);
		    }
		} finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
