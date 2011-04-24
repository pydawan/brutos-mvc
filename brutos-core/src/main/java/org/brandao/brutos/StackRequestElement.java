/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.mapping.ThrowableSafeData;

/**
 *
 * @author Brandao
 */
public interface StackRequestElement {

    Throwable getObjectThrow();

    ThrowableSafeData getThrowableSafeData();

    Object[] getParameters();

    Form getController();

    ResourceAction getAction();

    Object getResultAction();

    Object getResource();
    
    InterceptorHandler getHandler();

    String getView();

    DispatcherType getDispatcherType();

    void setObjectThrow(Throwable objectThrow);

    void setThrowableSafeData(ThrowableSafeData throwableSafeData);

    void setParameters(Object[] parameters);

    void setController(Form controller);

    void setAction(ResourceAction action);

    void setResultAction(Object resultAction);

    void setHandler(InterceptorHandler handler);

    void setResource( Object resource );

    void setView( String view );

    void setDispatcherType( DispatcherType dispatcherType );

}
