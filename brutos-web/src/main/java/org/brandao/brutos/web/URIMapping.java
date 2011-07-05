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

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandao
 */
public class URIMapping {

        private String uriPattern;

        private List parameters;

        public URIMapping( String uri ) throws MalformedURLException{
            createMap( uri );
            this.uriPattern = getURIPattern( uri );
        }

        private void createMap( String uri ) throws MalformedURLException{
            //fragmentos da uri
            List frags = new ArrayList();
            // identificados detectados
            List ids   = new ArrayList();

            //inicio de um identificador
            int index = uri.indexOf("{");
            //identificador
            String id;

            //se index for igual a -1 entao nao existe identificador. Sua definicao é null
            //se index for diferente de null entao existe um identificador. E extraido o
            //fragmento que inicia em 0 e termina em index
            if( index == -1 )
                frags.add( null );
            else
                frags.add( uri.substring( 0, index ) );

            //enquanto index for diferente de -1, a procura por identificadores continua
            while( index != -1 ){
                //fim do identificador
                int index2 = uri.indexOf("}", index );

                id = index+1 < index2? uri.substring( index+1, index2 ) : null;


                if( id == null )
                    throw new MalformedURLException();

                //adiciona o identificador
                ids.add(getId(id));

                //procura o proximo identificador para obter o proximo fragmento
                int nextIndex = uri.indexOf( "{", index2 );

                if( nextIndex == -1 ){
                    nextIndex = uri.length();
                }

                //fragmento atual
                String frag = index2+1 < nextIndex? uri.substring(index2+1, nextIndex) : null;

                //adiciona o fragmento
                frags.add( frag );

                index = uri.indexOf("{", index + 1 );
            }

            //se a quantidade de identificadores for impar, entao o ultimo identificador
            // foi encontrado no fim da uri
            if( frags.size() % 2 == 1 )
                frags.add(null);

            parameters = new ArrayList<URIParameter>();

            for( int i=0;i<ids.size();i++ ){
                parameters.add(
                        new URIParameter(
                            (String)ids.get(i),
                            (String)frags.get(i),
                            (String)frags.get(i+1) ) );
            }
        }

        private String getId(String value){
            int index = value.indexOf(":");
            return index == -1? value : value.substring(0,index);
        }

        private String getRegex(String value){
            int index = value.indexOf(":");
            return index == -1? null : value.substring(index+1,value.length());
        }

        private String getURIPattern( String uri ){

            if( uri == null )
                throw new NullPointerException();

            String regex = "";

            int index = uri.indexOf("{");
            int index2 = -1;
            int old = 0;
            while( index != -1 ){
                index2 = uri.indexOf("}", index );

                String id = index+1 < index2? uri.substring( index+1, index2 ) : null;

                if( id == null )
                    return "";

                String words = uri.substring( old, index );
                String regexParam = getRegex(id);

                regex += words;
                
                regex += regexParam == null || regexParam.trim().length() == 0?
                    "\\w{1,}" :
                    regexParam;

                old = index2+1;
                index = uri.indexOf("{", index + 1 );
            }

            if( index2 == -1 )
                regex = uri;
            else
                regex += uri.substring( index2+1, uri.length() );

            return regex;
        }

        public Map getParameters( String uri ){
            int start = 0;
            int end   = 0;
            Map<String,String> params = new HashMap<String,String>();

            for( int i=0;i<parameters.size();i++ ){
                URIParameter p = (URIParameter)parameters.get(i);
                start = p.getStart() == null? 0 : uri.indexOf( p.getStart(), start ) + p.getStart().length();
                end   = p.getEnd() == null? uri.length() : uri.indexOf( p.getEnd(), start + 1 );

                params.put(p.getId(), uri.substring(start, end) );
            }

            return params;
        }

        public boolean matches( String uri ){
            return uri.matches(this.uriPattern);
        }
}