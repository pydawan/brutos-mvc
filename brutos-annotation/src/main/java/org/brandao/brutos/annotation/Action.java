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

package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define uma ação de um controlador. Pode ser representada por um método com ou
 * sem parâmetros. Um parâmetro pode ser um objeto ou um tipo primitivo. Se for
 * um objeto, pode ser criado um mapeamentos para definir como os dados de uma
 * solicitação serão injetados em suas propriedades.
 * <p>
 * Se o método produzir um resultado, este será processado e incluído na
 * requisição para posteriormente ser usada na visão.
 * </p>
 * <p>
 * As exceções lançadas dentro do método podem alterar o fluxo lógico da
 * aplicação.
 * </p>
 * 
 * <pre>
 * Ex1:
 * public class TestController{
 * 
 *    &#064;Action
 *    public void root(){
 *       ...
 *    }
 * }
 * 
 * </pre>
 * 
 * @author Afonso Brandao
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

	/**
	 * Identificação da ação.
	 */
	String[] value() default {};

	/**
	 * Define a vista da ação.
	 */
	View view() default @View;

}
