<cfcomponent output="false" displayname="Notebook">
	<cfscript>
		variables.instance = {};
		variables.instance.notebook = "";
	</cfscript>
	
	<cffunction name="init" returntype="Notebook" access="public" output="false" hint="I am the constructor for the notbook component" >
		<cfargument name="libDirectory" type="string" required="false" default="lib">
		<cfscript>
			instance.classLoader = createObject("component", "JavaLoader").init(["#getDirectoryFromPath(getCurrentTemplatePath())##libDirectory#/CFEvernote.jar","#getDirectoryFromPath(getCurrentTemplatePath())##libDirectory#/evernote-api-1.18.jar","#getDirectoryFromPath(getCurrentTemplatePath())##libDirectory#/libthrift.jar"]);  
			
			variables.instance.notebook = instance.classLoader.create("com.evernote.edam.type.Notebook").init();
			
			return this;
		</cfscript>
	</cffunction>
	
	<cffunction name="getNotebook" returntype="any" access="public" output="false" hint="I get this objects evernote notebook java object" >
		<cfscript>
			return instance.notebook;
		</cfscript>
	</cffunction>
	
	<cffunction name="setNotebook" returntype="void" access="public" output="false" hint="I set this objects evernote notebook java object" >
		<cfargument name="notebook" type="any" required="true" hint="notebook object" />
		<cfscript>
			instance.notebook = arguments.notebook;
		</cfscript>
	</cffunction>
	
	<cffunction name="getGUID" returntype="String" access="public" output="false" hint="I get this notebooks GUID" >
		<cfscript>
			var guid = instance.notebook.getGUID();
			
			if(isDefined("guid"))
				return guid;
			else
				return "";
		</cfscript>
	</cffunction>
	
	<cffunction name="setGUID" returntype="void" access="public" output="false" hint="I set this notebooks GUID" >
		<cfargument name="guid" type="String" required="false" default="" />
		<cfscript>
			instance.notebook.setGUID(arguments.guid);
		</cfscript>
	</cffunction>
	
	<cffunction name="getName" returntype="String" access="public" output="false" hint="I get this notebooks name" >
		<cfscript>
			var name = instance.notebook.getName();
			
			if(isDefined("name"))
				return name;
			else
				return "";
		</cfscript>
	</cffunction>
	
	<cffunction name="setName" returntype="void" access="public" output="false" hint="I set this notebooks name" >
		<cfargument name="notebookName" type="String" required="false" default="" />
		<cfscript>
			instance.notebook.setName(arguments.notebookName);
		</cfscript>
	</cffunction>
</cfcomponent>