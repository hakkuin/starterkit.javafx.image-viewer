package com.starterkit.javafx.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 * Data displayed on the account management screen.
 *
 */
public class ImageViewModel {

	private final ListProperty<String> imagesProperty = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	private List<String> fileNames = new ArrayList<>();

	// REV: klasa Image nie powinna znajdowac sie w modelu
	private List<Image> images = new ArrayList<>();

	public ImageViewModel() {
	}

	public final void setImages(List<String> value) {
		imagesProperty.setAll(value);
	}

	public ListProperty<String> imagesProperty() {
		return imagesProperty;
	}

	public void initializeImages(List<File> files) {
		fileNames.addAll(files.stream().map(m -> m.getName()).collect(Collectors.toList()));
		
		for (File file : files) {
			images.add(new Image("file:" + file));
		}
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public Image getImageByFileName(String fileName) {
		return images.get(fileNames.indexOf(fileName));
	}

	public List<Image> getImages() {
		return images;
	}

	public Image getNextImage(Image image) {
		if (images.indexOf(image) != images.size() - 1) {
			return images.get(images.indexOf(image) + 1);
		} else {
			// REV: rzucasz wyjatek, ale go nie obslugujesz
			throw new IndexOutOfBoundsException();
		}
	}

	public Image getPreviousImage(Image image) {
		if (images.indexOf(image) != 0) {
			return images.get(images.indexOf(image) - 1);
		} else {
			// REV: j.w.
			throw new IndexOutOfBoundsException();
		}
	}
}