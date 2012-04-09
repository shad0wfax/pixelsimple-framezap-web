<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.pixelsimple.commons.media.Video"%>
<%@page import="com.pixelsimple.appcore.media.MediaType"%>
<%@page import="com.pixelsimple.commons.media.Container"%>
<%@page import="com.pixelsimple.framezap.web.util.AppUtil"%><html>
<head>
	<link rel="stylesheet" href="static/css/styles.css" type="text/css" media="screen" />
	
	<script type="text/javascript">
	
	</script>
</head>
<body>
     <div id="header" align="center"> 
         <div id="logo"></div> 
     </div> 

	<div id="main" align="center">

		<%
			String inputFile = request.getParameter("inputPath");	
			Container container = null;
			String type = null;
			
			// TODO: Change the damn logic below. :)
			if (!inputFile.endsWith(".m3u8")) {
				container = AppUtil.getMediaContainer(inputFile);
				type = AppUtil.getMimeType(container);
			} else {
				// TODO: Son of a bitch way to do stuff. Clean this shit.
				// TODO: Horrible assumption that media is of type Video :(. Have to fix these damn logic.
				Video vid = new Video();
				Map<String, String> atts = new HashMap<String, String>();
				atts.put(Container.CONTAINER_FORMAT_ATTRIBUTES.filename.name(), inputFile);
				vid.addContainerAttributes(atts);
				container = vid;
				type = AppUtil.getMimeTypeFromExtension("m3u8");
			}
			
			boolean isVideo = false;
			boolean isAudio = false;
			boolean isPhoto = false;
			String media = null;
			
			if (container.getMediaType() == MediaType.VIDEO) {
				isVideo = true;
				media = "Video";
			} else if (container.getMediaType() == MediaType.AUDIO) {
				isAudio = true;
				media = "Audio";
			} else if (container.getMediaType() == MediaType.PHOTO) {
				isPhoto = true;
				media = "Photo";
			}
			
			request.setAttribute("MEDIA_TYPE", media);
			request.setAttribute("FILE_TYPE", type);
		%>
		
		<h2>Serving Static file of Type - <%= media %> </h2>
		
		<p>Currently Streaming/Serving in progress for the file <%= inputFile %></p>
		<div class="older-entries">    
				<div class="entry"> 
				
<%-- 				<%
				
					if (isVideo) {
				%>
					<video controls width="640" height="480">
						<source src="staticmedia?inputPath=<%= inputFile %>&contentType=<%= type %>" type="<%= type %>"></source>
					</video>
				<%
					} else if (isAudio) {
				%>
					<audio controls>
						<source src="staticmedia?inputPath=<%= inputFile %>&contentType=<%= type %>" type="<%= type %>"></source>
					</audio>
				<%
					}  else if (isPhoto) {
				%>
					<img src="staticmedia?inputPath=<%= inputFile %>&contentType=<%= type %>" />
				<%
					}
				%>
 --%>				
 
 				<%
 					// TODO: Change the damn logic below :)
 					if (container.getFilePathWithName().endsWith(".flv") || container.getFilePathWithName().endsWith(".f4v")) {
 				%>
 					<jsp:include page="static_media_flash_fragment.jsp" flush="true"></jsp:include>
				<%
					} else if (container.getFilePathWithName().endsWith(".m3u8")) {
		 		%>
	 				<jsp:include page="static_media_hls_fragment.jsp" flush="true"></jsp:include>
				<%
					} else {
				%>
 					<jsp:include page="static_media_html5_fragment.jsp" flush="true"></jsp:include>
				<%
					}
				%>
				</div> 
		</div>
	</div>	 
</body>
</html>
