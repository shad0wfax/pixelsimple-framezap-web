package com.pixelsimple.framezap.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.RegistryService;
import com.pixelsimple.appcore.media.MediaType;
import com.pixelsimple.appcore.mime.Mime;
import com.pixelsimple.commons.media.Container;
import com.pixelsimple.commons.media.MediaInspector;
import com.pixelsimple.commons.media.exception.MediaException;

public class AppUtil {
	private static final Logger LOG = LoggerFactory.getLogger(AppUtil.class);

	private AppUtil() {}
	
	// TODO: Expensive call?? - validate the need for doing an expensive ffmpeg call? - Use a better strategy
	public static String getMimeType(String inputFilePath) {
		
		Container container = getMediaContainer(inputFilePath);
		
		if (container == null)
			return "";
		
//		String extension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1); 
//				
//		Mime mime = (Mime) RegistryService.getRegisteredEntry(Registrable.SUPPORTED_MIME_TYPES);		
//		String type = mime.getMimeType(extension, mediaType);
//		
//		LOG.debug("getMimeType::mime type from file = {} => {} ", inputFilePath, type);
//
//		return type;
		return getMimeType(inputFilePath, container);
		
	}
	
	// TODO: Expensive call?? - validate the need for doing an expensive ffmpeg call? - Use a better strategy
	public static String getMimeType(String inputFilePath, Container container) {
		MediaType mediaType = container.getMediaType();
		
		String extension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1); 
		
		Mime mime = (Mime) RegistryService.getRegisteredEntry(Registrable.SUPPORTED_MIME_TYPES);		
		String type = mime.getMimeType(extension, mediaType);
		
		LOG.debug("getMimeType::mime type from file = {} => {} ", inputFilePath, type);

		return type;
	}

	public static Container getMediaContainer(String inputFilePath) {
		Container container = null;
		
		try {
			container = new MediaInspector().createMediaContainer(inputFilePath);
			return container;
		} catch (MediaException e) {
			LOG.error("{}", e);
			return null;
		}
	}

	
}
