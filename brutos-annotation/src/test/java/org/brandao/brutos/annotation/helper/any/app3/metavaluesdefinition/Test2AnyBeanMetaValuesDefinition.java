package org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition;

import java.util.Map;

import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.EnumerationType;
import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.helper.any.app3.Property;
import org.brandao.brutos.annotation.helper.any.app3.PropertyType;

public class Test2AnyBeanMetaValuesDefinition {

	@Basic(bean="propertyA")
	@KeyCollection(
		bean="key",
		any=
			@Any(
				metaBean=@Basic(bean="propertyType"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValuesDefinition=TestEnumMetaValuesDefinition.class
			)
		)
	public Map<Property,String> property1;
	
	private Map<Property,String> property2;

	@Transient
	private Map<Property,String> property3;
	
	public Test2AnyBeanMetaValuesDefinition(
			@Basic(bean="propertyC")
			@KeyCollection(
				bean="key",
				any=
					@Any(
						metaBean=@Basic(bean="propertyType3"),
						metaType=PropertyType.class,
						metaEnumerated=EnumerationType.STRING,
						metaValuesDefinition=TestEnumMetaValuesDefinition.class
					)
				)
			Map<Property,String> property3 ){
		this.property3 = property3;
	}
	
	public Map<Property,String> getProperty2() {
		return property2;
	}

	@Basic(bean="propertyB")
	@KeyCollection(
		bean="key",
		any=
			@Any(
				metaBean=@Basic(bean="propertyType2"),
				metaType=PropertyType.class,
				metaEnumerated=EnumerationType.STRING,
				metaValuesDefinition=TestEnumMetaValuesDefinition.class
			)
		)
	public void setProperty2(Map<Property,String> property2) {
		this.property2 = property2;
	}

	public Map<Property,String> getProperty3() {
		return property3;
	}

	public void setProperty3(Map<Property,String> property3) {
		this.property3 = property3;
	}

	
}
