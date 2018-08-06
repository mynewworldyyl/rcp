package com.digitnexus.core.provider;

import java.util.Map;

public interface IKeyValueProvider extends IValueProvider{

	Map<String,String> keyValues();
}
