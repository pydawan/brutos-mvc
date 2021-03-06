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

package org.brandao.brutos;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ActionID;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ControllerID;


/**
 * 
 * @author Brandao
 */
public class DefaultActionResolver 
	extends AbstractActionResolver {

	public void registry(String controllerID, Controller controller,
			String actionID, Action action) throws ActionResolverException {
	}

	public void remove(String controllerID, Controller controller,
			String actionID, Action action) throws ActionResolverException {
	}

	public ResourceAction getResourceAction(
			ControllerManager controllerManager, MutableMvcRequest request)
			throws ActionResolverException {
		return null;
	}

	public ResourceAction getResourceAction(Controller controller,
			MutableMvcRequest request) throws ActionResolverException {
		return null;
	}

	public ResourceAction getResourceAction(Controller controller,
			String actionId, MutableMvcRequest request)
			throws ActionResolverException {
		return null;
	}

	public void registry(ControllerID controllerID, Controller controller,
			ActionID actionID, Action action) throws ActionResolverException {
		// TODO Auto-generated method stub
		
	}

	public void remove(ControllerID controllerID, Controller controller,
			ActionID actionID, Action action) throws ActionResolverException {
		// TODO Auto-generated method stub
		
	}

}
