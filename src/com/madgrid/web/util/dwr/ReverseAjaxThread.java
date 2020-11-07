package  com.madgrid.web.util.dwr;

import org.directwebremoting.ScriptSession;

import com.madgrid.model.Grid;
import com.madgrid.web.util.UserSession;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set; 
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;


public class ReverseAjaxThread extends Thread{
   private static ReverseAjaxThread INSTANCE;
   private Hashtable<String,Set<ScriptSession>> scriptSessionsIndex = new Hashtable<String, Set<ScriptSession>>();
   private Hashtable<String,Hashtable<String,Set<ScriptSession>>> scriptSessionsItem = new Hashtable<String,Hashtable<String,Set<ScriptSession>>>();
   
   public static synchronized ReverseAjaxThread getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ReverseAjaxThread();
         INSTANCE.start();
      }
      return INSTANCE;
   }
   
   public void run() {
      while (true) {
    	  synchronized(scriptSessionsIndex) { 
    		  
    		 List<String> keysToRemove = new ArrayList<String>();
	    	 for (String key : scriptSessionsIndex.keySet()) {
	    		 Set<ScriptSession> set = scriptSessionsIndex.get(key);
	    		 
	    		 List<ScriptSession> scriptSessionToRemove = new ArrayList<ScriptSession>();
		         for (ScriptSession scriptSession : set) {
		            if (scriptSession.isInvalidated()) {
	            	   scriptSessionToRemove.add(scriptSession);
		            }
		         }
		         
		         for(ScriptSession scriptSession: scriptSessionToRemove){
		        	 set.remove( scriptSession);
		        	 if( set.size() == 0){
	            		   keysToRemove.add( key);
	            	 }
		         }
	    	 }
	    	 
	    	 for(String key:keysToRemove){
            	scriptSessionsIndex.remove(key);
	         }
    	  }
	    	 
    	  synchronized(scriptSessionsItem) { 
    		 List<String> keysToRemove1 = new ArrayList<String>();
	    	 for (String key : scriptSessionsItem.keySet()) {
	    		 Hashtable<String,Set<ScriptSession>> hashtable = scriptSessionsItem.get(key);
	    		 List<String> keysToRemove2 = new ArrayList<String>();
	    		 for (String key2 : hashtable.keySet()) {
	    			 Set<ScriptSession> set = hashtable.get(key2);
	    			 List<ScriptSession> scriptSessionToRemove = new ArrayList<ScriptSession>();
			         for (ScriptSession scriptSession : set) {
			            if (scriptSession.isInvalidated()) {
			            	scriptSessionToRemove.add( scriptSession);
			            }
			         }
			         
			         for(ScriptSession scriptSession: scriptSessionToRemove){
			        	 set.remove( scriptSession);
			        	 if( set.size() == 0){
			        		 	keysToRemove2.add( key2);
		            	 }
			         }
	    		 }
	    		 
	    		 for(String key2:keysToRemove2){
	    			 hashtable.remove(key2);
	    			 if( hashtable.size() == 0){
	    				 keysToRemove1.add(key);
	            	 }
	 	         }
	    	 }
	    	 for(String key:keysToRemove1){
	            scriptSessionsIndex.remove(key);
		     }
    	  }
    	 
    	 try{
        	 Thread.sleep(4000);
         } catch (Exception e){
        	 e.printStackTrace();
         }
      }
   }
   
   public synchronized void addScriptSessionIndex(HttpServletRequest request, ScriptSession scriptSession) {
      Hashtable<String,Set<ScriptSession>> scriptSessionsCopy = new Hashtable<String,Set<ScriptSession>>(scriptSessionsIndex);
      
      UserSession userSession = (UserSession) request.getSession().getAttribute( "userSession");
      if( userSession != null){
    	  Set<ScriptSession> set = scriptSessionsCopy.get(userSession.getUser().getId().toString());
    	  if( set == null){
    		  set = new HashSet<ScriptSession>();
    	  }
    	  set.add( scriptSession);
    	  scriptSessionsCopy.put(userSession.getUser().getId().toString(), set);
      } else{
    	  Set<ScriptSession> set = new HashSet<ScriptSession>();
    	  set.add( scriptSession);
    	  scriptSessionsCopy.put("n" + scriptSession.getId(), set);
      }
      scriptSessionsIndex = scriptSessionsCopy;
   }
   
   public synchronized void addScriptSessionItem(HttpServletRequest request, ScriptSession scriptSession, Grid grid) {
	   Hashtable<String, Hashtable<String,Set<ScriptSession>>> scriptSessionsCopy = new Hashtable<String, Hashtable<String,Set<ScriptSession>>>(scriptSessionsItem);
	   
	   UserSession userSession = (UserSession) request.getSession().getAttribute( "userSession");
	   
	   Hashtable<String,Set<ScriptSession>> gridHashtable = scriptSessionsItem.get( grid.getId().toString());
	   
	   if( gridHashtable == null){
		   gridHashtable = new Hashtable<String,Set<ScriptSession>>();
		   if( userSession != null){
			   Set<ScriptSession> set =  new HashSet<ScriptSession>();
			   set.add( scriptSession);
			   gridHashtable.put(userSession.getUser().getId().toString(),set);
			   scriptSessionsCopy.put(grid.getId().toString(), gridHashtable);
		   } else{
			   Set<ScriptSession> set =  new HashSet<ScriptSession>();
			   set.add( scriptSession);
			   gridHashtable.put("n"+scriptSession.getId(), set);
			   scriptSessionsCopy.put(grid.getId().toString(), gridHashtable);
		   }
	   } else{
		   if( userSession != null){
			   Set<ScriptSession> set = gridHashtable.get(userSession.getUser().getId().toString());
			   if( set == null){
				   set = new HashSet<ScriptSession>();
			   }
			   set.add( scriptSession);
			   gridHashtable.put(userSession.getUser().getId().toString(), set);
			   scriptSessionsCopy.put(grid.getId().toString(), gridHashtable);
		   } else{
			   Set<ScriptSession> set =  new HashSet<ScriptSession>();
			   set.add( scriptSession);
			   gridHashtable.put("n"+scriptSession.getId(), set);
			   scriptSessionsCopy.put(grid.getId().toString(), gridHashtable);
		   }
	   }
	   
	   scriptSessionsItem = scriptSessionsCopy;
   }
   
   public synchronized Set<ScriptSession> getAllScriptSessionsIndex() 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   if( scriptSessionsIndex.values() == null || scriptSessionsIndex.size() == 0){
		   return new HashSet<ScriptSession>();
	   }
	   for( Set<ScriptSession> set: scriptSessionsIndex.values()){
		   result.addAll( set);
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getAllScriptSessionsItem() 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsItem.values() == null){
		   return new HashSet<ScriptSession>();
	   }
	   
	   for( String gridId: scriptSessionsItem.keySet()){
		   Hashtable<String,Set<ScriptSession>> gridScriptSession = scriptSessionsItem.get( gridId);
		   
		   if( gridScriptSession != null){
			   if( gridScriptSession.values() != null){
				   for( Set<ScriptSession> set: gridScriptSession.values()){
					   result.addAll( set);
				   }
			   }
		   }
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getAllScriptSessionsItem(String gridId) 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsItem.values() == null){
		   return new HashSet<ScriptSession>();
	   }
	   
	   Hashtable<String,Set<ScriptSession>> gridScriptSession = scriptSessionsItem.get( gridId);
	   
	   if( gridScriptSession != null){
		   if( gridScriptSession.values() == null){
			   return new HashSet<ScriptSession>();
		   }
		   for( Set<ScriptSession> set: gridScriptSession.values()){
			   result.addAll( set);
		   }
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getUserScriptSessionsIndex( String userId) 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsIndex.get( userId) == null){
		   return new HashSet<ScriptSession>();
	   }
	   
	   for( ScriptSession set: scriptSessionsIndex.get( userId)){
		   result.add( set);
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getUserScriptSessionsItem( String gridId, String userId) 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsItem.values() == null){
		   return new HashSet<ScriptSession>();
	   }
	   
	   Hashtable<String,Set<ScriptSession>> gridScriptSession = scriptSessionsItem.get( gridId);
	   
	   if( gridScriptSession != null){
		   if( gridScriptSession.get( userId) == null){
			   return new HashSet<ScriptSession>();
		   }
		   for( ScriptSession set: gridScriptSession.get( userId)){
			   result.add( set);
		   }
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getExceptUserScriptSessionsIndex( String userId) 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsIndex.values() == null || scriptSessionsIndex.size() == 0){
		   return new HashSet<ScriptSession>();
	   }
	   
	   for( String id: scriptSessionsIndex.keySet()){
		   if( !id.equals( userId)){
			   Set<ScriptSession> set = scriptSessionsIndex.get( id);
			   result.addAll( set);
		   }
	   }
	   return result;
   }
   
   public synchronized Set<ScriptSession> getExceptUserScriptSessionsItem( String gridId, String userId) 
   {
	   Set<ScriptSession> result = new HashSet<ScriptSession>();
	   
	   if( scriptSessionsItem.values() == null || scriptSessionsItem.size() == 0){
		   return new HashSet<ScriptSession>();
	   }
	   
	   Hashtable<String,Set<ScriptSession>> gridScriptSession = scriptSessionsItem.get( gridId);
	   
	   if( gridScriptSession == null || gridScriptSession.get( userId) == null){
		   return new HashSet<ScriptSession>();
	   }
	   
	   for( String id: gridScriptSession.keySet()){
		   if( !id.equals( userId)){
			   Set<ScriptSession> set = gridScriptSession.get( id);
			   result.addAll( set);
		   }
	   }
	   return result;
   }
   
   
}
