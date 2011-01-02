/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.mapping;

import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class MappingWrapper extends Mapping{

    protected Mapping mapping;
    
    public MappingWrapper( Mapping mapping ){
        this.mapping = mapping;
        throw new UnsupportedOperationException( "deprecated: use DefaultContextWrapper" );
    }

    public void destroy() {
        mapping.destroy();
    }

    public void loadIOCManager(IOCManager iocManager) {
        mapping.loadIOCManager(iocManager);
    }

    public void loadWebFrameManager(WebFrameManager webFrameManager) {
        mapping.loadWebFrameManager(webFrameManager);
    }

    public void loadInterceptorManager(InterceptorManager interceptorManager) {
        mapping.loadInterceptorManager(interceptorManager);
    }

}
