<%@page import="java.util.Collection"%>
<%@page import="com.pixelsimple.appcore.Registrable"%>
<%@page import="com.pixelsimple.appcore.RegistryService"%>
<%@page import="com.pixelsimple.transcoder.profile.Profile"%>
<%@page import="java.util.Map"%>

<%@page import="com.pixelsimple.framezap.web.util.AppUtil"%><html>
<head>
	<link rel="stylesheet" href="css/styles.css" type="text/css" media="screen" />
	
	<script type="text/javascript">
	
	</script>
</head>
<body>
<h2>Serving Static file!</h2>

	<%
		String inputFile = request.getParameter("inputPath");	
		String type = AppUtil.getType(inputFile);
	%>
	
	<p>Currently Streaming/Serving in progress for the file <%= inputFile %></p>
	<div class="older-entries">    
			<div class="entry"> 
				<video controls>
					<source src="staticmediawithrange?inputPath=<%= inputFile %>&mediaType=<%= type %>" type="<%= type %>"></source>
					<%-- <source src="/media?vidName=C:/Data/video_test/HTTYD_1-021_poor.mov&mediaType=video/webm&handleId=not_Existing" /> --%>
				</video>
			</div> 
	</div> 
</body>
</html>
