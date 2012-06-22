package com.pixelsimple.framezap.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Resource;
import com.pixelsimple.appcore.Resource.RESOURCE_TYPE;
import com.pixelsimple.appcore.media.MediaType;
import com.pixelsimple.appcore.mime.Mime;
import com.pixelsimple.appcore.registry.Registrable;
import com.pixelsimple.appcore.registry.RegistryService;
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
		return getMimeType(container);
		
	}
	
	public static String getMimeType(Container container) {
		MediaType mediaType = container.getMediaType();
		String extension = container.getFormatFromFileExtension() == null ? "" : container.getFormatFromFileExtension();
		String type = getMimeTypeFromExtension(extension, mediaType);
		
		LOG.debug("getMimeType::mime type from container = {} => {} ", container, type);

		return type;
	}

	public static Container getMediaContainer(String inputFilePath) {
		Container container = null;
		
		try {
			container = new MediaInspector().createMediaContainer(new Resource(inputFilePath, RESOURCE_TYPE.FILE));
			return container;
		} catch (MediaException e) {
			LOG.error("{}", e);
			return null;
		}
	}

	public static String getMimeTypeFromExtension(String extension, MediaType mediaType) {
		Mime mime = (Mime) RegistryService.getRegisteredEntry(Registrable.SUPPORTED_MIME_TYPES);
		return mime.getMimeType(extension, mediaType);
	}
	
	public static String getMimeTypeFromExtension(String extension) {
		Mime mime = (Mime) RegistryService.getRegisteredEntry(Registrable.SUPPORTED_MIME_TYPES);
		return mime.getMimeType(extension);
	}
}
