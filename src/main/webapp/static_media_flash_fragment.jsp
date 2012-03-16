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
        	if (wmodeOptions.indexOf(parameters.wmode) >= 0) {
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
<div id="StrobeMediaPlayback">
	<p>
		No support for flash video in this browser.
	</p>
</div>