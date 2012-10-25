/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ResourceAction;

/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractInterceptor implements InterceptorController{
    
    protected Map props;
    private List excludeMethods;
    
    public AbstractInterceptor() {
    }


    public boolean accept(InterceptorHandler handler) {
        ResourceAction rm = handler.getResourceAction();
        if( rm != null && excludeMethods != null )
            return !excludeMethods.contains( rm.getMethod().getName() );
        else
            return true;
    }
    
    public void setProperties( Map props ){
        this.props = props;
        
        if( props != null ){
            if( props.containsKey( "excludeMethods" ) ){
                String em = (String)props.get( "excludeMethods" );
                String[] ems = em.split( "," );
                this.excludeMethods = Arrays.asList( ems );
            }
        }
    }

    public boolean isConfigured(){
        return props != null;
    }

}
