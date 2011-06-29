package com.sudios714.cfevernote;

/**
 *
 * @author Ryan Anknlam
 * 
 * Copyright (c) 2011 Ryan Anklam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

//import evernote objects
import com.evernote.edam.notestore.*;
import com.evernote.edam.userstore.*;
import com.evernote.edam.error.*;
import com.evernote.edam.userstore.Constants;
import com.evernote.edam.type.*;

//imprt thrift
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;

public class CFEvernote {
   
    private static final short VERSION_MAJOR = 0;
    private static final short VERSION_MINOR = 1;
    
    private String hostName;
    private String userStoreURL;
    private String noteStoreURLBase;
    private String noteStoreURL;
    private String userAgent;
    
    private String authToken;
    private String shard;
    private String userID;
    
    private String userStoreQueryParam = "/edam/user";
    private String noteStoreBaseQueryParam = "/edam/note/";
    
    private static final String MAJOR = "0";
    private static final String MINOR = "1";
    
    private UserStore.Client userStore;
    private NoteStore.Client noteStore;
    
    /**
     * Default Constructer
     */
    public CFEvernote(){
        this.hostName = "";
    }
    
    public CFEvernote(String hostName){
        this.hostName = hostName;
        
        this.userStoreURL = "https://".concat(hostName).concat(this.userStoreQueryParam);
        this.noteStoreURLBase = "https://".concat(hostName).concat(this.noteStoreBaseQueryParam) ;
        
        this.userAgent = "Java";
    }
    
    public CFEvernote(String authTolken, String shard, String userID, String hostName, String userAgent){
        this.hostName = hostName;
        this.userAgent = userAgent;
        this.authToken = authTolken;
        this.shard = shard;
        this.userID = userID;
        
        this.userStoreURL = "https://".concat(hostName).concat(this.userStoreQueryParam);
        this.noteStoreURLBase = "https://".concat(hostName).concat(this.noteStoreBaseQueryParam) ;
        
        this.userAgent = userAgent;
    }
    
    /************************************************
     *                methods                       *
     ***********************************************/
    private boolean intitialize(String username, String password) throws Exception {

        // Check that we can talk to the server
        boolean versionOk = userStore.checkVersion(this.userAgent, VERSION_MAJOR, VERSION_MINOR);
        
        if (!versionOk) {
          System.err.println("Incomatible EDAM client protocol version");
          return false;
        }

        // Set up the NoteStore client 
        String noteStoreUrl = this.noteStoreURLBase + this.shard;
        
        THttpClient noteStoreTrans = new THttpClient(noteStoreUrl);
        noteStoreTrans.setCustomHeader("User-Agent", this.userAgent);
        
        TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
        
        this.noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);

        return true;
      }
      
    public void listNotes(){
        try{
            this.noteStore.listNotebooks(this.authToken);
        }
        catch(com.evernote.edam.error.EDAMUserException ex){
        
        }
        catch(com.evernote.edam.error.EDAMSystemException ex){
        
        }
        catch(org.apache.thrift.TException ex){
            
        }
    }
    
    /************************************************
     *           mutators and accessors             *
     ***********************************************/
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    /**
     * 
     * @return returns the version number of this
     */
    public String getVersionNumber(){
        return MAJOR.concat(".").concat(MINOR);
    }
    
    public String getUserStoreURL(){
        return this.userStoreURL;
    }
    
    public String getNoteStoreURLBase(){
        return this.noteStoreURLBase;
    }

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
