package com.SE.gruppe9.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>DataImportService</code>.
 */
public interface DataImportServiceAsync {
	void filterData(String name, int column,
			AsyncCallback<Map<String, String>> callback);

	void filterColumnValues(String[] keys, int constant,
			AsyncCallback<String[]> callback);
}
