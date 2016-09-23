package com.starterkit.javafx.dataprovider;

import java.io.File;
import java.util.List;

import com.starterkit.javafx.dataprovider.impl.DataProviderImpl;

/**
 * Provides data.
 *
 * @author Leszek
 */
public interface DataProvider {

	DataProvider INSTANCE = new DataProviderImpl();

	List<File> getImageFilesFromDirectory(File path);

}
