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
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.THttpClient;

public class CFEvernote {
    
    private String apiKey;
    private String apiAccount;
    private String hostName;
    
    public CFEvernote(){
        this.apiAccount = "";
        this.apiKey = "";
        this.hostName = "";
    }
    
    public CFEvernote(String apiKey, String apiAccount, String hostName){
        
        this.apiAccount = apiAccount;
        this.apiKey = apiKey;
        this.hostName = hostName;
    }
    
    /************************************************
     *           mutators and accessors             *
     ***********************************************/
    
    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the apiAccount
     */
    public String getApiAccount() {
        return apiAccount;
    }

    /**
     * @param apiAccount the apiAccount to set
     */
    public void setApiAccount(String apiAccount) {
        this.apiAccount = apiAccount;
    }

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
}
