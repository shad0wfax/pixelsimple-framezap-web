<%@page import="com.pixelsimple.appcore.media.MediaType"%>
<%@page import="com.pixelsimple.framezap.web.util.AppUtil"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="com.pixelsimple.appcore.Registrable"%>
<%@page import="com.pixelsimple.appcore.RegistryService"%>
<%@page import="com.pixelsimple.transcoder.profile.Profile"%>
<%@page import="java.util.Map"%>
<html>
<head>
	<link rel="stylesheet" href="static/css/styles.css" type="text/css" media="screen" />
	
	<script type="text/javascript">
		function stream()
		{
			var inputFile = document.getElementById('streamFileName').value;
			var url = "static_media.jsp?inputPath=" + inputFile;
			
			// Will let you download.
			//var url = "/media?mediaPath=" + input + "&contentType=video/webm&handleId=not_Existing";
			window.location = url;
		}
	
	</script>
</head>
<body>
     <div id="header" align="center"> 
         <div id="logo"></div> 
     </div> 

	<%
	
		Map<String, Profile> profiles = (Map<String, Profile>) RegistryService.getRegisteredEntry(Registrable.MEDIA_PROFILES);
		Collection<Profile> profileList = profiles.values();
	%>
	
	<div id="main" align="center">
		<p>
		  <h2>Play an Audio or Video</h2>
		</p>
		
		<p>
		  <strong>File to stream: </strong><br/>
		  <input type="text" id="streamFileName" name="streamFileName" value="C:/Data/video_test/HTTYD_1-021_poor.mov" size="80" width="600" />
		  <!--  
		  <input type="file" id="streamFileNameBrowse" name="streamFileName" value="C:/Data/video_test/HTTYD_1-021_poor.mov" size="80" width="600" />
		   -->
		</p>
		
		<p>
			<input type="button" value="Play" name="Play" onclick="stream()" />
		</p>	
		
	</div>
	
	<div id="main" align="center">
		<p>
		  <h2>Transcode - Zap! and done</h2> 
		</p>
	
		<form action="transcode" name="transcode">
		
			<p>
			  <strong>Input file Name: </strong><br/>
			  <input type="text" name="inputPath" value="C:/Data/video_test/HTTYD_1-021_poor.mov" size="80" width="600" /> 
			</p>
			
			<p>
			  <strong>Output Directory: </strong><br/>
			  <input type="text" name="outputPath" value="C:\Data\video_test\transcoded" width="600" size="80" />
			</p>
	
			<p>
			  <strong>Output File Name (Without extension):</strong><br/>
			  <input type="text" name="outputFileName" value="web_transcoding" width="600" size="80" />
			</p>
			
			<p>
				
			  <strong>Choose a profile to transcode with:</strong><br/>
				<select name="profileId">
					<%
						for (Profile profile : profileList) {
					%>
						<option><%=profile.getId()%></option>
					<%
						}
					%>
				</select>
			</p>
			<p>
				<input type="submit" value="Transcode" name="Transcode" />
			</p>	
	
			<%
				String t = (String) request.getAttribute("transcoding");
				if ("inprogress".equalsIgnoreCase(t)) {
					
					String outputFileComputed = (String) request.getAttribute("outputFileComputed");
					String type = (String) request.getAttribute("contentType");
					String handleId = (String) request.getAttribute("handleId");
					MediaType mediaType = (MediaType) request.getAttribute("mediaType");
					boolean isVideo = (mediaType == MediaType.VIDEO) ? true : false;

			%>
				<p>Currently there is a transcoding in progress. The transcoded file is: <br/> 
					<strong>
						<%= outputFileComputed %>
					</strong>
					<br/>
					Attempting to stream...(works for webm / ogg transcodes on Chrome and Firefox)
				</p>
				<p>
				
					<div class="older-entries">    
							<div class="entry"> 
							
								<%
								
									if (isVideo) {
								%>
							
									<video controls>
										<source src="transcodingmedia?mediaPath=<%= outputFileComputed %>&contentType=<%= type %>&handleId=<%= handleId %>" type="<%= type %>"></source>
									</video>
								
								<%
								
									} else {
								%>
									
									<audio controls>
										<source src="transcodingmedia?mediaPath=<%= outputFileComputed %>&contentType=<%= type %>&handleId=<%= handleId %>" type="<%= type %>"></source>
									</audio>
									
								<%
								
									}
								%>
												
								
								
								
							</div> 
					</div> 
				
				</p>
				
				
			<%
				}
			%>
		</form>
   </div>	  	
</body>
</html>
