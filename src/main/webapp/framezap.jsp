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
     <div id="header" align="center"> 
         <div id="logo"></div> 
     </div> 

	<%
	
		Map<String, Profile> profiles = (Map<String, Profile>) RegistryService.getRegisteredEntry(Registrable.MEDIA_PROFILES);
		Collection<Profile> profileList = profiles.values();
	%>
	<div id="main" align="center">
		<p>
		  Zap! and done 
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
			%>
				<p>Currently there is a transcoding in progress. The transcoded file is: <br/> 
					<strong>
						<%= outputFileComputed %>
					</strong>
				</p>
			<%
				}
			%>
		</form>
   </div>	  	
</body>
</html>
