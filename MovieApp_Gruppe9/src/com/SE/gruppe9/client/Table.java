package com.SE.gruppe9.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Table {

	private VerticalPanel vPanel = new VerticalPanel();
	private FlexTable flexibleTable = new FlexTable();
	private Map<String, String> resultMap = new HashMap<String, String>();
	private Map<String, String> tmpMap = new HashMap<String, String>();
	private boolean tableIsEmpty;

	// async object used for the server side import of the data
	private DataImportServiceAsync filter = GWT.create(DataImportService.class);

	public void createFlexTable() {
		// name of each column
		flexibleTable.setText(0, 0, "Wikipedia ID");
		flexibleTable.setText(0, 1, "Movie Name");
		flexibleTable.setText(0, 2, "Release Year");
		flexibleTable.setText(0, 3, "Box Office Revenue");
		flexibleTable.setText(0, 4, "Runtime");
		flexibleTable.setText(0, 5, "Language");
		flexibleTable.setText(0, 6, "Country");
		flexibleTable.setText(0, 7, "Genre");

		flexibleTable.setCellPadding(2);
		flexibleTable.setCellPadding(3);
		flexibleTable.setBorderWidth(2);

		// Assemble the main panel
		vPanel.add(flexibleTable);
		// Associate the main panel with the HTML host page
		RootPanel.get("filterTable").add(vPanel);
	}

	/**
	 * remove all entries from the table
	 */
	public void clearFlexTable() {
		flexibleTable.removeAllRows();
		flexibleTable.clear();
	}
	
	public void clearMap(){
		resultMap.clear();
	}

	/**
	 * checks if the table has no entries
	 */
	public boolean getTableIsEmpty() {
		return tableIsEmpty;
	}
	
/**
 * 
 * @param name
 * @param column
 */
	public void firstFilter(String name, int column) {
		
		switch (column) {

		// filter for movie name
		case 2:
			importMap(name, 2);
			/*
			 * if (map.size() < 1){ tableIsEmpty = true; } else tableIsEmpty =
			 * false;
			 */
			break;

		// filter for release year
		case 3:
			importMap(name, column);
			break;

		// filter for box office revenue
		case 4:
			importMap(name, column);
			break;

		// filter for runtime
		case 5:
			importMap(name, column);
			break;

		// filter for language
		/*case 6:
			importMap(name, 6);
			break;

		// filter for country
		case 7:
			importMap(name, column);
			break;

		// filter for genre
		case 8:
			importMap(name, column);
			break;*/
		}
	}

/**
 * 
 * @param name
 * @param column
 */
	public void importMap(String name, int column) {
		// Initialize the service proxy.
		if (filter == null) {
			filter = GWT.create(DataImportService.class);
		}
		// Set up the callback object.
		final AsyncCallback<Map<String, String>> callback = new AsyncCallback<Map<String, String>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(Map<String, String> result) {
				int i = 1;
				resultMap.putAll(result);
					for (Map.Entry<String, String> entry : resultMap.entrySet()){
						String[] tmp = entry.getValue().split("==");
						flexibleTable.setText(i, 0, entry.getKey());
						for (int j = 0; j < 7; j++) {
							flexibleTable.setText(i, j + 1, tmp[j]);
						}
						i++;
						if (i > 100){
							break;
						}
					}
			}
		};

		filter.filterData(name, column, callback);
	}

	public void secondFilter(String name, int column) {
		int i = 1;
		switch (column) {
		// filter for movie name
		/*case 0:
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (movie[1].trim().toUpperCase().contains(name.trim().toUpperCase())) {
					flexibleTable.setText(i, 0, entry.getKey());
					for (int j = 0; j < 7; j++) {
						flexibleTable.setText(i, j + 1, movie[j]);
					}
					
				}
				putDataInMap();
			}
			break;*/

		// filter for release year
		case 1:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (tmp[1].equals(name)) {
					flexibleTable.setText(i, 0, entry.getKey());
					for (int j = 0; j < 7; j++) {
						flexibleTable.setText(i, j + 1, tmp[j]);
					}
					i++;
					tmpMap.put(entry.getKey(), entry.getValue());
				}
			}
			//restore the filtered data in the result map
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		// filter for box office revenue
		case 2:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (name.equals("< 100'000")) {
					if (tmp[2].length() > 0) {
						if (Long.parseLong(tmp[2]) < 100000) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++) {
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}

				if (name.equals("100'000-1'000'000")) {
					if (tmp[2].length() > 0) {
						if (Long.parseLong(tmp[2]) < 1000000 && Long.parseLong(tmp[2]) >= 100000) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++) {
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}

				if (name.equals("> 1'000'000")) {
					if (tmp[2].length() > 0) {
						if (Long.parseLong(tmp[2]) > 1000000) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++) {
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}
			}
			//restore the filtered data in the result map
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		// filter for runtime
		case 3:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (name.equals("≤ 60")) {
					if (tmp[3].length() > 0) {
						if (Double.parseDouble(tmp[3]) <= 60) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++) {
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}else if (name.equals("≤ 90")) {
					if (tmp[3].length() > 0) {
						if (Double.parseDouble(tmp[3]) <= 90) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++) {
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}else if (name.equals("> 90")) {
					if (tmp[3].length() > 0) {
						if (Double.parseDouble(tmp[3]) > 90) {
							flexibleTable.setText(i, 0, entry.getKey());
							for (int j = 0; j < 7; j++){
								flexibleTable.setText(i, j + 1, tmp[j]);
							}
							i++;
							tmpMap.put(entry.getKey(), entry.getValue());
						}
					}
				}
			}
			//restore the filtered data in the result map
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		// filter for language
		case 4:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (tmp[4].toUpperCase().trim().contains(name.toUpperCase())) {
					flexibleTable.setText(i, 0, entry.getKey());
					for (int j = 0; j < 7; j++){
						flexibleTable.setText(i, j + 1, tmp[j]);
					}
					i++;
					tmpMap.put(entry.getKey(), entry.getValue());
				}
			}
			//restore the filtered data in the result map
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		// filter for country
		case 5:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (tmp[5].toUpperCase().contains(name.toUpperCase())) {
					flexibleTable.setText(i, 0, entry.getKey());
					for (int j = 0; j < 7; j++) {
						flexibleTable.setText(i, j + 1, tmp[j]);
					}
					i++;
					tmpMap.put(entry.getKey(), entry.getValue());
				}
			}
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		// filter for genre
		case 6:
			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
				String[] tmp = entry.getValue().split("==");
				if (tmp[6].toUpperCase().contains(name.toUpperCase())) {
					flexibleTable.setText(i, 0, entry.getKey());
					for (int j = 0; j < 7; j++){
						flexibleTable.setText(i, j + 1, tmp[j]);
					}
					i++;
					tmpMap.put(entry.getKey(), entry.getValue());
				}
			}
			resultMap.clear();
			for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
			tmpMap.clear();
			break;

		}
	}
	
	public Map<String, String> getResultMap(){
		return resultMap;
	}
}
