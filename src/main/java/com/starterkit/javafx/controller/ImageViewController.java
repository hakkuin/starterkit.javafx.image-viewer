package com.starterkit.javafx.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.starterkit.javafx.dataprovider.DataProvider;
import com.starterkit.javafx.model.ImageViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

public class ImageViewController {

	private static final Logger LOG = Logger.getLogger(ImageViewController.class);

	private static final int DELAY = 3000;
	@FXML
	private final ImageViewModel model = new ImageViewModel();

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	@FXML
	private Button directoryChooserButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button previousButton;

	@FXML
	private Button slideShowButton;

	@FXML
	private Button zoomInButton;

	@FXML
	private Button zoomOutButton;

	@FXML
	private Button resetZoomButton;

	@FXML
	private HBox hBox;

	@FXML
	private ImageView imageView;
	
	@FXML
	private ScrollPane slidePane;
	
	@FXML
	private ListView<String> imageListView;

	private File path;

	public ImageViewController() throws IOException {
		LOG.debug("Constructor: field = ");

	}

	@FXML
	private void initialize() {
		LOG.debug("entering initialize()");

		LOG.debug("initialize(): model.imagesProperty() = " + model.imagesProperty());
		imageListView.itemsProperty().bind(model.imagesProperty());

		imageListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					LOG.debug(newValue + " selected");

					displayImage(model.getImageByFileName(newValue));
				}
			}
		});

		hBox.setAlignment(Pos.CENTER);

		LOG.debug("leaving initialize()");
	}

	@FXML
	public void directoryChooserButtonAction(ActionEvent event) {
		LOG.debug("entering: directoryChooserButtonAction()");

		DirectoryChooser chooser = new DirectoryChooser();

		path = chooser.showDialog(directoryChooserButton.getScene().getWindow());
		model.initializeImages(dataProvider.getImageFilesFromDirectory(path));

		LOG.debug("directoryChooserButtonAction(): chosenPath = " + path);

		setListView(model.getFileNames());

		LOG.debug("leaving: directoryChooserButtonAction()");
	}

	@FXML
	public void nextButtonAction(ActionEvent event) {
		LOG.debug("entering: nextButtonAction()");

		displayNextImage();

		LOG.debug("leaving: nextButtonAction()");
	}

	@FXML
	public void previousButtonAction(ActionEvent event) {
		LOG.debug("entering: previousButtonAction()");

		displayImage(model.getPreviousImage(imageView.getImage()));
//		imageView.setImage(model.getPreviousImage(imageView.getImage()));
//		hBox.resize(model.getPreviousImage(imageView.getImage()).getWidth(), model.getPreviousImage(imageView.getImage()).getHeight());

		LOG.debug("leaving: previousButtonAction()");
	}

	private void displayNextImage() {
		displayImage(model.getNextImage(imageView.getImage()));
//		imageView.setImage(model.getNextImage(imageView.getImage()));
//		hBox.resize(model.getNextImage(imageView.getImage()).getWidth(), model.getNextImage(imageView.getImage()).getHeight());
	}

	private void setListView(List<String> imageNames) {
		LOG.debug("entering: setListView(): imageNames = " + imageNames);

		model.setImages(FXCollections.observableArrayList(imageNames));

		LOG.debug("leaving: setListView()");
	}

	@FXML
	public void imageViewOnScrollAction(ActionEvent event) {
		LOG.debug("entering: imageViewOnScrollAction()");
//		(ScrollEvent) event.getClass();
		double zoomFactor = 1.05;
//		double deltaY = .getDeltaY();
//		if (deltaY < 0) {
//			zoomFactor = 2.0 - zoomFactor;
//		}
//		imageView.setScaleX(imageView.getScaleX() * zoomFactor);
//		imageView.setScaleY(imageView.getScaleY() * zoomFactor);
//		event.consume();
	}

	@FXML
	public void zoomIn(ActionEvent event) {
		LOG.debug("entering: zoomIn()");
		
		double zoomFactor = 1.05;
		imageView.setScaleX(imageView.getScaleX() * zoomFactor);
		imageView.setScaleY(imageView.getScaleY() * zoomFactor);

		hBox.setPrefWidth(imageView.getImage().getWidth() * imageView.getScaleX());
		hBox.setPrefHeight(imageView.getImage().getHeight() * imageView.getScaleY());
//		LOG.debug("zoomIn: HBox pref size: " + hBox.getPrefWidth() + ", " + hBox.getPrefHeight());
//		LOG.debug("zoomIn: HBox size: " + hBox.getWidth() + ", " + hBox.getHeight());
		LOG.debug("leaving: zoomIn()");
	}

	@FXML
	public void zoomOut(ActionEvent event) {
		LOG.debug("entering: zoomOut()");
		
		double zoomFactor = 0.95;
		imageView.setScaleX(imageView.getScaleX() * zoomFactor);
		imageView.setScaleY(imageView.getScaleY() * zoomFactor);
//		hBox.setPrefSize(imageView.getFitWidth() * imageView.getScaleX(), imageView.getFitHeight() * imageView.getScaleY());

		hBox.setPrefWidth(imageView.getImage().getWidth() * imageView.getScaleX());
		hBox.setPrefHeight(imageView.getImage().getHeight() * imageView.getScaleY());

//		LOG.debug("zoomOut: " + imageView.getScaleY() * zoomFactor);
//		LOG.debug("zoomOut: " + imageView.getScaleX() * hBox.getPrefWidth());
//		LOG.debug("zoomOut: " + imageView.getScaleY() * hBox.getPrefHeight());
//		LOG.debug("zoomOut: " + zoomFactor);
//		LOG.debug("zoomOut: " + imageView.getScaleY());
//		
//		LOG.debug("zoomOut: HBox size: " + hBox.getPrefWidth() + ", " + hBox.getPrefHeight());
		LOG.debug("leaving: zoomOut()");
	}

	@FXML
	public void resetZoom(ActionEvent event) {
		LOG.debug("entering: resetZoom()");
		
		imageView.setScaleX(1D);
		imageView.setScaleY(1D);
		hBox.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());

		LOG.debug("leaving: resetZoom()");
	}

	@FXML
	public void slideShowButtonAction(ActionEvent event) {

		nextButton.setDisable(true);
		previousButton.setDisable(true);
		directoryChooserButton.setDisable(true);
		slideShowButton.setDisable(true);

		Task<Void> backgroundTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				LOG.debug("call() called");

				for (Image image : model.getImages()) {
					try {
						imageView.setImage(image);
						hBox.resize(image.getWidth(), image.getHeight());
						Thread.sleep(DELAY);
					} catch (InterruptedException e) {
						throw new RuntimeException("Thread interrupted", e);
					}
				}
				return null;
			}

			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				nextButton.setDisable(false);
				previousButton.setDisable(false);
				directoryChooserButton.setDisable(false);
				slideShowButton.setDisable(false);
			}
		};

		new Thread(backgroundTask).start();
	}

	private void displayImage(Image image) {
		imageView.setImage(image);
		hBox.setPrefSize(image.getWidth(), image.getHeight());
	}

}
