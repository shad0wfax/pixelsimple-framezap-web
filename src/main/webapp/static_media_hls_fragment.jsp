<%
	String mediaType = (String) request.getAttribute("MEDIA_TYPE");
	String inputFile = request.getParameter("inputPath");
	String type = (String) request.getAttribute("FILE_TYPE");
	
	if (mediaType.equalsIgnoreCase("Video")) {
%>
	<video controls width="640" height="480">
		<source src="hls?inputPath=<%= inputFile %>&contentType=<%= type %>" type="<%= type %>"></source>
	</video>
<%
	} else if (mediaType.equalsIgnoreCase("Audio")) {
%>
	<audio controls>
		<source src="hls?inputPath=<%= inputFile %>&contentType=<%= type %>" type="<%= type %>"></source>
	</audio>
<%
	}  else if (mediaType.equalsIgnoreCase("Photo")) {
%>
	<img src="hls?inputPath=<%= inputFile %>&contentType=<%= type %>" />
<%
	}
%>
