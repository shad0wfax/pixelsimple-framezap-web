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
			Container container = AppUtil.getMediaContainer(inputFile);
			String type = AppUtil.getMimeType(inputFile, container);
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
			
		%>
		
		<h2>Serving Static file of Type - <%= media %> </h2>
		
		<p>Currently Streaming/Serving in progress for the file <%= inputFile %></p>
		<div class="older-entries">    
				<div class="entry"> 
				
				<%
				
					if (isVideo) {
				%>
					<video controls>
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
					
				</div> 
		</div>
	</div>	 
</body>
</html>
