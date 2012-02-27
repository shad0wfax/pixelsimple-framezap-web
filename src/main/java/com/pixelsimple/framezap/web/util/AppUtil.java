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
		
		// TODO: what is the best way to get the media type? The container format can be wrong :(
		MediaType mediaType = null;
		try {
			Container container = new MediaInspector().createMediaContainer(inputFilePath);
			mediaType = container.getMediaType();

			LOG.debug("getMimeType::mediaType = {} ", mediaType);
		} catch (MediaException e) {
			LOG.error("{}", e);
		}
				
		String extension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1); 
				
		Mime mime = (Mime) RegistryService.getRegisteredEntry(Registrable.SUPPORTED_MIME_TYPES);		
		String type = mime.getMimeType(extension, mediaType);
		
		LOG.debug("getMimeType::mime type from file = {} => {} ", inputFilePath, type);

		return type;
		
		
	}
	
	
}
