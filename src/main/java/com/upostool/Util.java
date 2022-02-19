package com.upostool;

import javafx.scene.image.Image;

public class Util {
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

    //Inner instruments
    public static final String[] UPOS_VERSIONS = new String[]{"32.02.05", "32.01.15",
            "31.00.18", "30.26.04", "30.01.05","NULL"};
    public static final String[] DRIVERS_VERSIONS = new String[]{ "Verifone_5.0.5.2_B3",
            "Pax_2.28", "Ingenico_3.26","NULL"};
    public static final String[] PINPAD_CONNECTIONS = new String[]{"USB/COM", "ETHERNET"};
    public static final String[] PRINTEREND_VALUES = new String[]{"01", "010D0A", "010D0A", "1B37", "1B401B633002",
            "1B55", "1B550D0A", "1B69", "1B690D0A", "0D0A080D0A", "1B6702"};

    public enum PINPADINI_VALUES_EXPLANATIONS {
        SHOWSCREENS("Выключить/Включить дублирование интерфейса на экран кассира"),
        PRINTEREND("Последовательность символов в конце слипа"),
        CARD_HOLDER_SIGNATURE_IMAGE("Путь к файлу для сохранения подписи клиента на ККМ\n"
                + "Путь относительный, либо абсолютный к файлу\n"
                + "По умолчанию текущая папка");
        private String explanation;

        PINPADINI_VALUES_EXPLANATIONS(String explanation) {
            this.explanation = explanation;
        }

        public String getExplanation() {
            return explanation;
        }

    }
}
