<%@page import="com.pixelsimple.appcore.media.MediaType"%>
<%@page import="com.pixelsimple.commons.media.Container"%>
<%@page import="com.pixelsimple.framezap.web.util.AppUtil"%><html>
<head>
	<link rel="stylesheet" href="static/css/styles.css" type="text/css" media="screen" />
	
	<script type="text/javascript" src="static/js/swfobject.js" ></script>
	<script type="text/javascript" src="static/js/ParsedQueryString.js" ></script>
	<script type="text/javascript" src="static/js/parseUri.js" ></script>
	
	<script type="text/javascript">

	    // Collect query parameters in an object that we can
	    // forward to SWFObject:
	    var parsed = parseUri(document.URL);
		var port = parsed["port"] != null ? ":" + parsed["port"] : "";
	    var fileToServe = parsed["protocol"] +"://" + parsed["host"] + port + parsed["directory"] + "staticmedia?inputPath=" + getURLParameter("inputPath");
		alert("the file to serve is : " + fileToServe);

		
        // Collect query parameters in an object that we can
        // forward to SWFObject:
        
        var pqs = new ParsedQueryString();
        var parameterNames = pqs.params(false);
        var parameters = {
            src: fileToServe,
            autoPlay: "false",
            verbose: true,
            controlBarAutoHide: "false",
            controlBarPosition: "bottom"
        };
        
        for (var i = 0; i < parameterNames.length; i++) {
            var parameterName = parameterNames[i];
            parameters[parameterName] = pqs.param(parameterName) ||
            parameters[parameterName];
        }
        
   	    var wmodeValue = "direct";
        var wmodeOptions = ["direct", "opaque", "transparent", "window"];
        if (parameters.hasOwnProperty("wmode"))
        {
        	if (wmodeOptions.indexOf(parameters.wmode) >= 0)
        	{
        		wmodeValue = parameters.wmode;
        	}	            	
        	delete parameters.wmode;
        }
        
        // Embed the player SWF:	            
        swfobject.embedSWF(
			"static/swf/StrobeMediaPlayback.swf"
			, "StrobeMediaPlayback"
			, 640
			, 480
			, "10.1.0"
			, "expressInstall.swf"
			, parameters
			, {
                allowFullScreen: "true",
                wmode: wmodeValue
            }
			, {
                name: "StrobeMediaPlayback"
            }
		);
        
        /* Uncomment this code to be notified of playback errors in JavaScript:
        
         function onMediaPlaybackError(playerId, code, message, detail)            
         {
         	alert(playerId + "\n\n" + code + "\n" + message + "\n" + detail);            
         }
         
         */
	     
	     function getURLParameter(name) {
	    	    return decodeURI(
	    	        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
	    	    );
	    }
	
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
				
	               <div id="StrobeMediaPlayback">
					<p>
						No support for html5 flv video in this browser.
					</p>
	               </div>
		
				</div> 
		</div>
	</div>	 
</body>
</html>
