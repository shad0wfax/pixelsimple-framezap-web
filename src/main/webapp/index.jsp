<%@page import="java.util.Collection"%>
<%@page import="com.pixelsimple.appcore.Registrable"%>
<%@page import="com.pixelsimple.appcore.RegistryService"%>
<%@page import="com.pixelsimple.transcoder.profile.Profile"%>
<%@page import="java.util.Map"%>
<html>
<head>
	<link rel="stylesheet" href="css/styles.css" type="text/css" media="screen" />
	
	<script type="text/javascript">
		function clear(element) {
			element.value = "";
		} 
		
		function open_win()
		{
			var input = document.getElementById('staticServePath').value;
			var url = "/static_media.jsp?inputPath=" + input;
			
			// Will let you download.
			//var url = "/media?vidName=" + input + "&mediaType=video/webm&handleId=not_Existing";
			window.open(url);
		}
	
	</script>
</head>
<body>
<h2>Hello World!</h2>

	<%
	
		Map<String, Profile> profiles = (Map<String, Profile>) RegistryService.getRegisteredEntry(Registrable.MEDIA_PROFILES);
		Collection<Profile> profileList = profiles.values();
	%>
	
	<form action="/transcode" name="transcode">
	
		<strong>Input file: </strong><input type="text" name="inputPath" value="C:/Data/video_test/HTTYD_1-021_poor.mov" size="80" width="600" /><br/>
		<strong>Output Path:</strong><input type="text" name="outputPath" value="C:\Data\video_test\transcoded" width="600" size="80" /><br/>
		<strong>Output File:</strong><input type="text" name="outputFileName" value="web_transcoding" width="600" size="80" /><br/>
		<select name="profileId">
			<%
				for (Profile profile : profileList) {
			%>
				<option><%=profile.getId()%></option>
			<%
				}
			%>
		</select>
		<input type="submit" value="Transcode" name="Transcode" />

		<%
			String t = (String) request.getAttribute("transcoding");
			if ("inprogress".equalsIgnoreCase(t)) {
				
				String outputFileComputed = (String) request.getAttribute("outputFileComputed");
				String type = (String) request.getAttribute("mediaType");
				String handleId = (String) request.getAttribute("handleId");
		%>
			<p>Currently there is a transcoding in progress...</p>
			<div class="older-entries">    
					<div class="entry"> 
						<video controls>
							<source src="/media?vidName=<%= outputFileComputed %>&mediaType=<%= type %>&handleId=<%= handleId %>" type="<%= type %>" />
						</video>
					</div> 
			</div> 
		<%
			}
		%>

	
	</form>
	<p>
		Stream an Existing File (not when transcoding):
	</p>

	<input type="text" id="staticServePath" name="staticServePath" value="C:\Data\video_test\transcoded\web_transcoding.webm" size="80" width="600" /><br/>
	<input type="button" value="Stream" name="Stream" onclick="open_win()"/>
</body>
</html>
