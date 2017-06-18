/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.DataType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.RequestTypeException;
import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.ResponseProvider;
import org.brandao.brutos.ResponseTypeException;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.DataTypeMap;
import org.brandao.brutos.web.mapping.MediaTypeMap;
import org.brandao.brutos.web.scope.HeaderScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 * 
 * @author Brandao
 */
public class WebInvoker extends Invoker{
    
    public void invoker(HttpServletRequest request, 
            HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
    	
    	WebMvcRequestImp webRequest   = new WebMvcRequestImp((HttpServletRequest)request);
    	WebMvcResponseImp webResponse = new WebMvcResponseImp((HttpServletResponse)response, webRequest);
    	
    	try{
    		SessionScope.setServletRequest(request);
    		ParamScope.setRequest(webRequest);
    		RequestScope.setRequest(webRequest);
    		HeaderScope.setRequest(webRequest);
    		
            if(!super.invoke(webRequest, webResponse)){
                if(chain == null)
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                else
                    chain.doFilter(request, response);
            }
    		
    	}
    	catch(RequestTypeException e){
    		response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
    	}
    	catch(RequestMethodException e){
    		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    	}
    	catch(ResponseTypeException e){
    		response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
    	}
    	finally{
    		SessionScope.removeServletRequest(request);
    		ParamScope.removeRequest(webRequest);
    		RequestScope.removeRequest(webRequest);
    		HeaderScope.removeRequest(webRequest);
    	}
    }

	public Object invoke(Controller controller, ResourceAction action,
			Object resource, Object[] parameters) throws RequestTypeException{

		if (controller == null)
			throw new IllegalArgumentException("controller not found");

		if (action == null)
			throw new IllegalArgumentException("action not found");

		MutableWebMvcRequest webRequest   = (MutableWebMvcRequest) RequestProvider.getRequest();
		MutableWebMvcResponse webResponse = (MutableWebMvcResponse) ResponseProvider.getResponse();
		
    	webRequest   = new WebMvcRequestImp((HttpServletRequest)webRequest.getServletRequest());
    	webResponse = new WebMvcResponseImp((HttpServletResponse)webResponse.getServletResponse(), webRequest);
		
    	webRequest.setResource(resource);
    	webRequest.setResourceAction(action);
    	webRequest.setParameters(parameters);
		
		StackRequestElement element = this.createStackRequestElement();

		element.setAction(webRequest.getResourceAction());
		element.setController(controller);
		element.setRequest(webRequest);
		element.setResponse(webResponse);
		element.setResource(webRequest.getResource());
		element.setObjectThrow(webRequest.getThrowable());
		this.invoke(element);
		return webResponse.getResult();
	}
    
    public boolean invoke(StackRequestElement element){
    	
    	WebMvcRequest request            = (WebMvcRequest) element.getRequest();
		WebResourceAction resourceAction = (WebResourceAction)element.getAction();
		RequestMethodType requestMethod  = resourceAction.getRequestMethod();
		
		if(!request.getRequestMethodType().equals(requestMethod)){
			throw new RequestMethodException(request.getRequestMethodType().getId());
		}
		
		return super.invoke(element);
	}
    
	protected DataType selectResponseType(ResourceAction action, MutableMvcRequest request){
		
    	DataTypeMap supportedResponseTypes = action.getResponseTypes();
    	List<DataType> responseTypes       = request.getAcceptResponse();
    	
    	/*
    	if(supportedResponseTypes.isEmpty()){
    		supportedResponseTypes = action.getController().getRequestTypes();
    	}
    	*/
    	
    	if(supportedResponseTypes.isEmpty()){
    		
    		MediaType defaultDataType = (MediaType)this.renderView.getDefaultRenderViewType();
    		
	    	for(DataType dataType: responseTypes){
	    		if(defaultDataType.match((MediaType)dataType)){
	    			return defaultDataType;
	    		}
	    	}
	    	
    	}
    	else{
    		MediaTypeMap supportedMediaType = (MediaTypeMap)supportedResponseTypes;
    		
	    	for(DataType dataType: responseTypes){
	    		MediaType selected = 
    				supportedMediaType.getMatch((MediaType)dataType);
	    			
	    		if(selected != null){
	    			return selected;
	    		}
	    	}
	    	
    	}
    	
    	return null;
	}
        
}
