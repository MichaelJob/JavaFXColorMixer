package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller {

	@FXML
	private Slider sliderR;
	@FXML
	private Slider sliderG;
	@FXML
	private Slider sliderB;
	@FXML
	private Label textRedValue;
	@FXML
	private Label textGreenValue;
	@FXML
	private Label textBlueValue;
	@FXML
	private TextField txtRed;
	@FXML
	private TextField txtGreen;
	@FXML
	private TextField txtBlue;
	@FXML
	private Label textColor;
	@FXML
	private StackPane colorPane;
	@FXML
	private MenuItem menuClose;
	@FXML
	private MenuItem menuAbout;
	// GUI RadioButtons
	@FXML
	private RadioButton rbRed;
	@FXML
	private RadioButton rbBlue;
	@FXML
	private RadioButton rbGreen;
	@FXML
	private RadioButton rbYellow;
	@FXML
	private RadioButton rbCyan;
	@FXML
	private RadioButton rbOrange;
	@FXML
	private RadioButton rbBlack;
	@FXML
	private ToggleGroup tgGUI = new ToggleGroup();
	// Menu CheckButtons
	@FXML
	private RadioMenuItem rbMenuRed;
	@FXML
	private RadioMenuItem rbMenuBlue;
	@FXML
	private RadioMenuItem rbMenuGreen;
	@FXML
	private RadioMenuItem rbMenuYellow;
	@FXML
	private RadioMenuItem rbMenuCyan;
	@FXML
	private RadioMenuItem rbMenuOrange;
	@FXML
	private RadioMenuItem rbMenuBlack;
	@FXML
	private ToggleGroup tgMenu = new ToggleGroup();
	// Buttons
	@FXML
	private Button btDarker;
	@FXML
	private Button btBrighter;

	private StringProperty spHexRed;
	private StringProperty spHexGreen;
	private StringProperty spHexBlue;

	private Color thisColor = Color.rgb(125, 125, 125);

	public void initialize() {

		setupMenu();
		addSliderListeners();
		addTextFieldListeners();
		addRadioButtonListeners();
		addMenuRadioButtonListeners();
		addButtonListener();
		setupHEX();

		// create Binding of Color for Pane
		ObjectBinding<Background> backgroundColorBinding = Bindings.createObjectBinding(() -> {
			int red = sliderR.valueProperty().intValue();
			int green = sliderG.valueProperty().intValue();
			int blue = sliderB.valueProperty().intValue();
			thisColor = Color.rgb(red, green, blue);
			CornerRadii radii = new CornerRadii(20);
			Insets insets = new Insets(10);
			return new Background(new BackgroundFill(thisColor, radii, insets));
		}, sliderR.valueProperty(), sliderG.valueProperty(), sliderB.valueProperty());

		// bind ColorPane
		colorPane.backgroundProperty().bind(backgroundColorBinding);
	}

	private void setupHEX() {
		// create HEX Values
		spHexRed = new SimpleStringProperty(Integer.toHexString(sliderR.valueProperty().intValue()));
		spHexGreen = new SimpleStringProperty(Integer.toHexString(sliderG.valueProperty().intValue()));
		spHexBlue = new SimpleStringProperty(Integer.toHexString(sliderB.valueProperty().intValue()));
		// Labels with HEX
		textRedValue.textProperty().bind(spHexRed);
		textGreenValue.textProperty().bind(spHexGreen);
		textBlueValue.textProperty().bind(spHexBlue);
	}

	private void addButtonListener() {
		btDarker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				thisColor = thisColor.darker();
				sliderR.setValue(thisColor.getRed() * 255.0);
				sliderG.setValue(thisColor.getGreen() * 255.0);
				sliderB.setValue(thisColor.getBlue() * 255.0);
			}
		});

		btBrighter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				thisColor = thisColor.brighter();
				sliderR.setValue(thisColor.getRed() * 255.0);
				sliderG.setValue(thisColor.getGreen() * 255.0);
				sliderB.setValue(thisColor.getBlue() * 255.0);
			}
		});
	}

	private void addRadioButtonListeners() {
		tgGUI.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (tgGUI.getSelectedToggle() != null) {
					String farbe = ((RadioButton) tgGUI.getSelectedToggle()).getText();
					setFarbe(farbe);
					setMenuFarbe(farbe);
				}
			}
		});
	}

	private void addMenuRadioButtonListeners() {
		tgMenu.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (tgMenu.getSelectedToggle() != null) {
					String farbe = ((RadioMenuItem) tgMenu.getSelectedToggle()).getText();
					setFarbe(farbe);
					setGUIrbFarbe(farbe);
				}
			}
		});
	}

	private void setMenuFarbe(String farbe) {
		switch (farbe) {
		case "red":
			rbMenuRed.setSelected(true);
			break;
		case "blue":
			rbMenuBlue.setSelected(true);
			break;
		case "green":
			rbMenuGreen.setSelected(true);
			break;
		case "yellow":
			rbMenuYellow.setSelected(true);
			break;
		case "cyan":
			rbMenuCyan.setSelected(true);
			break;
		case "orange":
			rbMenuOrange.setSelected(true);
			break;
		case "black":
			rbMenuBlack.setSelected(true);
			break;
		default:
			break;
		}
	}

	private void setGUIrbFarbe(String farbe) {
		switch (farbe) {
		case "red":
			tgGUI.selectToggle(rbRed);
			break;
		case "blue":
			tgGUI.selectToggle(rbBlue);
			break;
		case "green":
			tgGUI.selectToggle(rbGreen);
			break;
		case "yellow":
			tgGUI.selectToggle(rbYellow);
			break;
		case "cyan":
			tgGUI.selectToggle(rbCyan);
			break;
		case "orange":
			tgGUI.selectToggle(rbOrange);
			break;
		case "black":
			tgGUI.selectToggle(rbBlack);
			break;
		default:
			break;
		}
	}

	private void setFarbe(String farbe) {
		switch (farbe) {
		case "red":
			sliderR.setValue(255);
			sliderG.setValue(0);
			sliderB.setValue(0);
			break;
		case "blue":
			sliderR.setValue(0);
			sliderG.setValue(0);
			sliderB.setValue(255);
			break;
		case "green":
			sliderR.setValue(0);
			sliderG.setValue(255);
			sliderB.setValue(0);
			break;
		case "yellow":
			sliderR.setValue(255);
			sliderG.setValue(255);
			sliderB.setValue(0);
			break;
		case "cyan":
			sliderR.setValue(0);
			sliderG.setValue(255);
			sliderB.setValue(255);
			break;
		case "orange":
			sliderR.setValue(255);
			sliderG.setValue(165);
			sliderB.setValue(0);
			break;
		case "black":
			sliderR.setValue(0);
			sliderG.setValue(0);
			sliderB.setValue(0);
			break;
		default:
			break;
		}
	}

	// bind textfields to Sliders
	private void addTextFieldListeners() {
		txtRed.textProperty().addListener((observable, oldValue, newValue) -> {
			Double newValueD = Double.valueOf(oldValue);
			try {
				newValueD = Double.valueOf(newValue);
				if (newValueD < 0) {
					newValueD = 0.0;
				} else if (newValueD > 255) {
					newValueD = 255.0;
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter values from 0-255");
				alert.showAndWait().filter(response -> response == ButtonType.OK);
			}
			sliderR.setValue(newValueD);
		});
		txtGreen.textProperty().addListener((observable, oldValue, newValue) -> {
			Double newValueD = Double.valueOf(oldValue);
			try {
				newValueD = Double.valueOf(newValue);
				if (newValueD < 0) {
					newValueD = 0.0;
				} else if (newValueD > 255) {
					newValueD = 255.0;
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter values from 0-255");
				alert.showAndWait().filter(response -> response == ButtonType.OK);
			}
			sliderG.setValue(newValueD);
		});
		txtBlue.textProperty().addListener((observable, oldValue, newValue) -> {
			Double newValueD = Double.valueOf(oldValue);
			try {
				newValueD = Double.valueOf(newValue);
				if (newValueD < 0) {
					newValueD = 0.0;
				} else if (newValueD > 255) {
					newValueD = 255.0;
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.WARNING, "Please enter values from 0-255");
				alert.showAndWait().filter(response -> response == ButtonType.OK);
			}
			sliderB.setValue(newValueD);
		});
	}

	// bind slider to textfields and hex labels
	private void addSliderListeners() {
		sliderR.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				spHexRed.set(Integer.toHexString(sliderR.valueProperty().intValue()));
				txtRed.setText(Integer.toString(sliderR.valueProperty().intValue()));
			}
		});
		sliderG.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				spHexGreen.set(Integer.toHexString(sliderG.valueProperty().intValue()));
				txtGreen.setText(Integer.toString(sliderG.valueProperty().intValue()));
			}
		});
		sliderB.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				spHexBlue.set(Integer.toHexString(sliderB.valueProperty().intValue()));
				txtBlue.setText(Integer.toString(sliderB.valueProperty().intValue()));
			}
		});
	}

	private void setupMenu() {
		menuClose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Alert alert = new Alert(AlertType.INFORMATION, "depa ColorPicker with JavaFX - Michael Job");
				alert.showAndWait().filter(response -> response == ButtonType.OK);
			}
		});
	}

	// show (and hide) Color Values in Pane on MouseOver
	public void showColorLabel() {
		int red = sliderR.valueProperty().intValue();
		int green = sliderG.valueProperty().intValue();
		int blue = sliderB.valueProperty().intValue();
		Color color = Color.rgb(red, green, blue);
		int hue = (int) color.getHue();
		int sat = (int) (color.getSaturation() * 100);
		int bri = (int) (color.getBrightness() * 100);

		textColor.setTextFill(color.invert().darker());
		textColor.setFont(Font.font("Arial Black", 12));
		textColor.setText("RGB " + red + " / " + green + " / " + blue + "\nHSB " + hue + " / " + sat + " / " + bri);
	}

	// show (and hide) Color Values in Pane on MouseOver
	public void hideColorLabel() {
		textColor.setText("");
	}

}