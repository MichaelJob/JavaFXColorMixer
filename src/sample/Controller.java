package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Locale;

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
    private Label textColor;
    @FXML
    private StackPane colorPane;

    private Locale locale  = new Locale("en", "UK");


    public void initialize() {

        ObjectBinding<Background> backgroundColorBinding = Bindings.createObjectBinding(() ->
        {
            int red = sliderR.valueProperty().intValue();
            int green = sliderG.valueProperty().intValue();
            int blue = sliderB.valueProperty().intValue();
            Color color = Color.rgb(red, green, blue);
            CornerRadii radii = new CornerRadii(20);
            Insets insets = new Insets(10);
            return new Background(new BackgroundFill(color, radii, insets));
        }, sliderR.valueProperty(), sliderG.valueProperty(), sliderB.valueProperty());

        textRedValue.textProperty().bind(Bindings.format(locale, "%,.0f", sliderR.valueProperty()));
        textGreenValue.textProperty().bind(Bindings.format(locale, "%,.0f", sliderG.valueProperty()));
        textBlueValue.textProperty().bind(Bindings.format(locale, "%,.0f", sliderB.valueProperty()));

        colorPane.backgroundProperty().bind(backgroundColorBinding);
    }

    public void showColorLabel(){
        int red = sliderR.valueProperty().intValue();
        int green = sliderG.valueProperty().intValue();
        int blue = sliderB.valueProperty().intValue();
        Color color = Color.rgb(red, green, blue);
        int hue = (int)color.getHue();
        int sat = (int)(color.getSaturation()*100);
        int bri = (int)(color.getBrightness()*100);

        textColor.setTextFill(color.invert().darker());
        textColor.setFont(Font.font("Arial Black", 16));
        textColor.setText("RGB "+red+" / "+green+" / "+blue+"\nHSB "+hue+" / "+sat+" / "+bri);
    }

    public void hideColorLabel(){
        textColor.setText("");
    }
}