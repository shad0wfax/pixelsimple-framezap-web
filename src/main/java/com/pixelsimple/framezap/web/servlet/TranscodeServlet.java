/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.framezap.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Registrable;
import com.pixelsimple.appcore.RegistryService;
import com.pixelsimple.commons.media.Container;
import com.pixelsimple.commons.media.MediaInspector;
import com.pixelsimple.commons.util.StringUtils;
import com.pixelsimple.transcoder.Handle;
import com.pixelsimple.transcoder.Transcoder;
import com.pixelsimple.transcoder.TranscoderOutputSpec;
import com.pixelsimple.transcoder.profile.Profile;

/**
 *
 * @author Akshay Sharma
 * Feb 18, 2012
 */
public class TranscodeServlet extends HttpServlet {
	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(TranscodeServlet.class);
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputFilePath = request.getParameter("inputPath");
		String outputFilePath = request.getParameter("outputPath");
		String outputFileName = request.getParameter("outputFileName");
		String profileId = request.getParameter("profileId");
		
		LOG.debug("Params : {}, {}", inputFilePath, outputFilePath);
		LOG.debug("profileId : {}", profileId);
		
		@SuppressWarnings("unchecked")
		Map<String, Profile> profiles = (Map<String, Profile>) RegistryService.getRegisteredEntry(Registrable.MEDIA_PROFILES);
		
		Profile profile = profiles.get(profileId);
		
		TranscoderOutputSpec spec = new TranscoderOutputSpec(profile, outputFilePath, outputFileName);
		
		LOG.debug("transcode::Traget profile::{} and output file:: {}", profile,  outputFileName);
		
		if (!StringUtils.isNullOrEmpty(inputFilePath) && !StringUtils.isNullOrEmpty(outputFilePath) 
				&& !StringUtils.isNullOrEmpty(outputFileName) && !StringUtils.isNullOrEmpty(profileId)) {
			
			try {
				MediaInspector inspector = new MediaInspector();
				Container inputMedia = null;
				inputMedia = inspector.createMediaContainer(inputFilePath);
				Transcoder transcoder = new Transcoder();
				Handle handle = transcoder.transcode(inputMedia, spec);
				request.setAttribute("transcoding", "inprogress");
				
				request.setAttribute("outputFileComputed", handle.getOutputFileCreated());
				request.setAttribute("mediaType", "video/" + profile.getContainerFormat());
				request.setAttribute("handleId", handle.getHandleId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("{}", e);
			}
		}
		
		request.getRequestDispatcher("/framezap.jsp").forward(request, response);
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
