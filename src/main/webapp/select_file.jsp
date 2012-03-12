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
	<link rel="stylesheet" href="static/css/jqueryFileTree.css" type="text/css" media="screen" />

	<script type="text/javascript" src="static/js/jquery.min.1.6.4.js"></script>
	<script type="text/javascript" src="static/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="static/js/jqueryFileTree.js"></script>
	
	<script type="text/javascript">
		function selectFile(file)
		{
			alert("Going to stream - " + file);
			opener.location.href = "static_media.jsp?inputPath=" + file;
			close();
		}
	
		$(document).ready( function() {
			
			$('#fileTreeDisplay').fileTree({ root: '/', script: 'jqueryFileTree.jsp' }, function(file) { 
				selectFile(file);
			});
			
		});
		
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
		  <h2>Select a video / audio file.</h2>
		</p>
		
		<p>
			<div class="example">
				<div id="fileTreeDisplay"></div>
			</div>
		</p>
	</div>
	
</body>
</html>
