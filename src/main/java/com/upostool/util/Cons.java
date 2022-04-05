package com.upostool.util;

import com.upostool.MainApplication;
import javafx.scene.image.Image;

public class Cons {
    //Styling
    public static final Image BUTTON_ICON_30x30 = new Image(MainApplication.class
            .getResourceAsStream("icon_button30x30.png"));
    public static final Image LOGO_TRANSPARENT = new Image(MainApplication.class
            .getResourceAsStream("logo_transparent120x60.png"));
    public static final Image ATM_ICON_MAIN = new Image(MainApplication.class
            .getResourceAsStream("atm_icon.png"));
    public static final String BLACK_THEME = "-fx-base: #373e43;\n" +
            "    -fx-control-inner-background: derive(-fx-base, 35%);\n" +
            "    -fx-control-inner-background-alt: -fx-control-inner-background ";

    //Util data
    public static final String[] UPOS_VERSIONS = new String[]{null, "32.04.04","32.02.05", "32.01.15", "31.11.02",
            "31.00.18", "30.26.04", "30.01.05","29.00.00"};
    public static final String[] DRIVERS_VERSIONS = new String[]{null, "Verifone_5.0.5.2_B3",
            "Pax_2.28", "Ingenico_3.26"};
    public static final String[] PINPAD_CONNECTIONS = new String[]{"USB/COM", "ETHERNET"};
    public static final String[] PRINTEREND_VALUES = new String[]{"01", "010D0A", "010D0A", "1B37", "1B401B633002",
            "1B55", "1B550D0A", "1B69", "1B690D0A", "0D0A080D0A", "1B6702"};
    public static final String[] COMPORTS = new String[]{"1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static final String[] DEPARTMENTS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10","11","12","13","14","15"};
    public static final String[] PINPAD_MODELS = new String[]{"VERIFONE VX 820 CTLS", "VX 805", "S 300",
            "PAX SP30ARM", "IPP 350", "TELIUM IPP 320 A"};
    public static final String[] PINPAD_SPEED = new String[]{"300", "1200", "2400", "4800", "9600", "14400",
            "19200", "38400", "57600", "115200"};
    public static final String[] PRINTER_TYPE = new String[]{"Shtrih-PTRK1","Epson-TM950","Generic_32_chars",
    "Generic_35_chars","Generic_36_chars","Generic_40_chars","Generic_50_chars"};

}
