package org.brandao.brutos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.ActionType;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.mapping.WebControllerID;
import org.brandao.brutos.web.util.WebUtil;

public class WebActionType extends ActionType{

	public static final WebActionType PARAMETER = new WebActionType() {
		
		public String id(){
			return "PARAMETER";
		}

		public String name(){
			return "Parameter";
		}
		
		public String getControllerID(String className){
			return "/" + className.toLowerCase().replaceAll("controller$", "");
		}

		public String getActionID(String actionName){
			//return "/" + actionName;
			return actionName.toLowerCase().replaceAll("action$", "");
		}
		
		/*
		public boolean isComposite(){
			return false;
		}

		public boolean isDelegate(){
			return true;
		}
		*/
		
		public boolean isValidControllerId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public boolean isValidActionId(String value){
			return true;
		}

		public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
				ActionID actionID, Action action){
			//ações não possuem ids
			if(action != null){
				return null;
			}
			WebControllerID wcid = (WebControllerID)controllerID;
			return Arrays.asList(
				(ActionID)new WebActionID(wcid.getId(), wcid.getRequestMethodType()));
		}
		
	};	
	
	public static final WebActionType HEADER = new WebActionType() {
		
		public String id(){
			return "HEADER";
		}

		public String name(){
			return "Header";
		}
		
		public String getControllerID(String className){
			return "/" + className.toLowerCase().replaceAll("controller$", "");
		}

		public String getActionID(String actionName){
			//return "/" + actionName;
			return actionName.toLowerCase().replaceAll("action$", "");
		}
		
		/*
		public boolean isComposite(){
			return false;
		}

		public boolean isDelegate(){
			return true;
		}
		*/
		
		public boolean isValidControllerId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public boolean isValidActionId(String value){
			return true;
		}

		public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
				ActionID actionID, Action action){
			return PARAMETER.getIDs(controllerID, controller, 
					actionID, action);
		}
		
	};
	
	public static final WebActionType HIERARCHY = new WebActionType() {
		
		public String id(){
			return "HIERARCHY";
		}

		public String name(){
			return "Hierarchy";
		}

		public String getControllerID(String className){
			return "/" + className.toLowerCase().replaceAll("controller$", "");
		}

		public String getActionID(String actionName){
			return "/" + actionName.toLowerCase().replaceAll("action$", "");
		}
		
		/*
		public boolean isComposite(){
			return true;
		}

		public boolean isDelegate(){
			return false;
		}
		*/
		
		public boolean isValidControllerId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}
		
		public boolean isValidActionId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
				ActionID actionID, Action action){
			
			List<ActionID> result            = new ArrayList<ActionID>();
			WebAction webAction              = (WebAction)action;
			List<ControllerID> controllerIds = new ArrayList<ControllerID>();
			List<ActionID> actionIds         = new ArrayList<ActionID>();
			
			if(controllerID.equals(controller.getId())){
				controllerIds.add(controller.getId());
				controllerIds.addAll(controller.getAlias());
			}
			else{
				controllerIds.add(controllerID);
			}
			
			for(ControllerID cID: controllerIds){
				
				WebControllerID wcid = (WebControllerID)cID;
				
				if(action == null){
					result.add(new WebActionID(wcid.getId(), wcid.getRequestMethodType()));
				}
				else{
					if(actionID.equals(webAction)){
						actionIds.add(action.getId());
						actionIds.addAll(action.getAlias());
					}
					else{
						actionIds.add(actionID);
					}
					
					for(ActionID aID: actionIds){
						WebActionID waID = (WebActionID)aID;
						result.add(new WebActionID(wcid.getId() + waID.getId(), waID.getRequestMethodType()));
					}
				}
				
			}
			
			return result;
		}
		
	};

	public static final WebActionType DETACHED = new WebActionType() {

		public String id(){
			return "DETACHED";
		}

		public String name(){
			return "Detached";
		}

		public String getControllerID(String className){
			//throw new UnsupportedOperationException();
			return null;
		}

		public String getActionID(String actionName){
			return "/" + actionName.toLowerCase().replaceAll("action$", "");
		}
		/*
		public boolean isComposite(){
			return false;
		}

		public boolean isDelegate(){
			return false;
		}
		*/
		
		public boolean isValidControllerId(String value){
			return value == null;
		}
		
		public boolean isValidActionId(String value){
			try{
				WebUtil.checkURI(value, true);
				return true;
			}
			catch(Throwable e){
				return false;
			}
		}

		public List<ActionID> getIDs(ControllerID controllerID, Controller controller, 
				ActionID actionID, Action action){
			
			List<ActionID> result       = new ArrayList<ActionID>();
			WebAction webAction         = (WebAction)action;

			List<ActionID> actionIds     = new ArrayList<ActionID>();
			
			if(action == null){
				return result;
			}
				
			if(actionID.equals(webAction.getName())){
				actionIds.add(action.getId());
				actionIds.addAll(action.getAlias());
			}
			else{
				actionIds.add(actionID);
			}
			
			for(ActionID aID: actionIds){
				result.add(aID);
			}
				
			return result;
		}
		
	};

	private final static Map<String, WebActionType> defaultTypes = 
			new HashMap<String, WebActionType>();
	
	static {
		defaultTypes.put(PARAMETER.id(),	PARAMETER);
		defaultTypes.put(HIERARCHY.id(),	HIERARCHY);
		defaultTypes.put(DETACHED.id(),		DETACHED);
		defaultTypes.put(HEADER.id(),		HEADER);
	}

	public static WebActionType valueOf(String value) {
		if (value == null)
			return null;
		else
			return defaultTypes.get(value);
	}
	
}
