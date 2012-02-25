package com.pixelsimple.framezap.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtil {
	private static final Logger LOG = LoggerFactory.getLogger(AppUtil.class);

	private AppUtil() {}
	
	public static String getType(String inputFilePath) {
		
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
				
				LOG.debug("getType::content type from file = {} ", inputFilePath);

				return "video/" + inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
		
		
	}
	
	
}
