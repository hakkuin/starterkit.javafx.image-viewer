package com.starterkit.javafx.dataprovider.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.dataprovider.filter.ImageFilter;

/**
 * Provides data. Data is stored locally in this object. Additionally a call
 * delay is simulated.
 *
 * @author Leszek
 */
public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	public DataProviderImpl() {

	}

	@Override
	public List<File> getImageFilesFromDirectory(File path) {
		LOG.debug("Entering getImagesFromDirectory()");

		File folder = new File(path.toString());
		
		List<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles(new ImageFilter())));
		LOG.debug("getImagesFromDirectory(): images = " + files);

		LOG.debug("Leaving getImagesFromDirectory()");
		return files;
	}
}
