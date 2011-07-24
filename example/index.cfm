<cffile action="read" file="#expandPath('/')#example/config.txt" variable="variables.filecontents" />
<cfset configArray = listToArray(variables.filecontents)/>
<cfparam name="action" default="" />
<cfscript>
	//its best to persist the cfevernote object in a scope since the oauth process will requires some state
	if(NOT structKeyExists(session,"cfEvernote") OR isDefined("url.reset")){
		
		session.cfEvernote = new com.714studios.cfevernote.CFEvernote(configArray[1],configArray[2],"sandbox.evernote.com","http://localhost/cfevernote/example/index.cfm","#ExpandPath('../')#/lib");
		//call the authenticateAPIUser, this will give you your temporaty credentials that you'll pass into evernote when the user verifies access
		if(NOT session.cfEvernote.authenticateAPIUser()){
			writeDump(var="API Key and Username were not authenticated by evernote",abort=true);
		}
	}

	//once a suer authentictaes with everhote, it will pass them back to the callback when you intialized evernote and pass a token and verifier
	//we can set get credentials in two ways, by first seetting the token and verifier then calling it with no properties, or calling it using
	//them as arguments		
	if(isDefined("url.oauth_Token") AND isDefined("url.oauth_verifier")){
		session.cfEvernote.setAuthToken(url.oauth_Token);
		session.cfEvernote.setAuthVerifier(url.oauth_verifier);
		session.cfEvernote.getTokenCredentials();
	}
	
	switch(action){
		case "createNote":
		{
			//to create a note first create a note object, and pass in the lib directory where the evernote jars
			note = createObject("component","com.714studios.cfevernote.note").init("#ExpandPath('../')#/lib");
			//then set its content
			note.setContent(form.content);
			//lastly add the note to evernote using the cfevernote
			note = session.cfEvernote.addNote(note);
			
			writedump(var=note.getGUID());
			writedump(var=note,abort=true);
			
			break;
		}
		case "getNotebooks":
		{
			notebooks = session.cfEvernote.getNotebooks();
			writedump(var=getMetaData(notebooks[1]));
			writedump(var=session.cfEvernote.getNotebooks());
			break;
		}
		case "getNotes":
		{
			writedump(var=session.cfEvernote.getNotes());
			break;
		}
		case "reinitClassLoader":
		{
			session.cfEvernote.reInitClassLoader();
			break;	
		}
	}
</cfscript>

<html>
<head>
	<title>CFEvernote Example</title>
	<link rel="stylesheet" type="text/css" href="jquery.cleditor.css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" type="text/javascript"></script>
	<script src="jquery.cleditor.min.js" type="text/javascript"></script>
	<script language="javascript">
		 $(document).ready(function() {
       		 $("#content").cleditor();
    	 });
	</script>
</head>
<body>
<cfoutput>
	<div id="evernoteInfo">
		<cfif Len(session.cfEvernote.getAuthToken())>
			<h2>Evernote Information</h2>
			<ul id="evernoteInfo">
				<li>Token: #session.cfEvernote.getAuthToken()#</li>
				<li>User ID: #session.cfEvernote.getUserID()#</li>
			</ul>
		<cfelse>
			<h2>You have note yet authenticated with evernote.</h2>
			<a href="#session.cfEvernote.getEvernoteOAuthVerifyURL()#">Click here to authorize</a>
		</cfif>
	</div>
	
	<div id="actions">
		<h2>Evernote Actions</h2>
		<ul>
			<li>
				<a href="index.cfm?reset=true">Reset Session Evernote Object (clears credentials)</a>
			</li>
			<li>
				<a href="index.cfm?action=reinitClassLoader">Reinit ClassLoader</a>
			<li>
				<a href="index.cfm?action=getNotebooks">Get Notebooks</a>
			</li>
			<li>
				<a href="index.cfm?action=getNotes">Get Notes</a>
			</li>
		</ul>
	</div>
	
	<div id="createNote">
		<h2>Create Note</h2>
		<form action="index.cfm?action=createNote" method="post">
			<textarea name="content" id="content" rows="4" cols="30"></textarea>
			<input type="submit" value="create note" />
		</form>
	</div>
	
	<cfif isDefined("url.method") AND url.method eq "getNotebooks">
		
	<cfelseif isDefined("url.method") AND url.method eq "getNotes">
		
	</cfif>
</cfoutput>
</body>
</html>