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

    //Util data
    public static final String[] UPOS_VERSIONS = new String[]{null, "32.02.05", "32.01.15",
            "31.00.18", "30.26.04", "30.01.05"};
    public static final String[] DRIVERS_VERSIONS = new String[]{null, "Verifone_5.0.5.2_B3",
            "Pax_2.28", "Ingenico_3.26"};
    public static final String[] PINPAD_CONNECTIONS = new String[]{"USB/COM", "ETHERNET"};
    public static final String[] PRINTEREND_VALUES = new String[]{"01", "010D0A", "010D0A", "1B37", "1B401B633002",
            "1B55", "1B550D0A", "1B69", "1B690D0A", "0D0A080D0A", "1B6702"};
    public static final String[] COMPORTS = new String[]{"1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    public static final String[] DEPARTMENTS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static final String[] PINPAD_MODELS = new String[]{"VERIFONE VX 820 CTLS", "VX 805", "S 300", "PAX SP30ARM", "IPP 350", "TELIUM IPP 320 A"};

    public enum PINPADINI_VALUES_EXPLANATIONS {
        SHOWSCREENS("Выключить/Включить дублирование интерфейса на экран кассира"),
        PRINTEREND("Последовательность символов в конце слипа"),
        CARD_HOLDER_SIGNATURE_IMAGE("Путь к файлу для сохранения подписи клиента на ККМ\n"
                + "Путь относительный, либо абсолютный к файлу\n"
                + "По умолчанию текущая папка"),
        DAYLIREPORT("Задать имя файла содержащего информацию подобную вызову Меню > Помощь.\n"
                + "Формируется автоматически при сверке итогов.\n" +
                "%s в имени автоматически заменяется на номер терминала."),
        DEPARTMENT("Используется для принудительной замены номера отдела.\n"
                + "При использовании параметра все команды от ККМ выполняются по указанному отделу.\n"
                + "Переданное от ККМ значение игнорируется."),
        DOCLIMIT("Максимальный размер буфера чека возвращаемый через интерфейс sbrf.dll.\n" +
                "Число от 0 до 4294967296\n" +
                "По умолчанию 0 – не задан"),
        ENABLEUSB("Автоматическое распознавание USB-пинпадов.\n"
                + "Начиная с релиза 31.18 поиск выполняется по PID и VID оборудования,\n"
                + "перечисленным в файле findcom.ini.\n"
                + "Если явно не указано EnableUSB = 0, терминал будет пытаться найти пинпад"),
        EXTRADELAY("Дополнительная задержка к стандартному таймауту при установке скорости работы с пинпадом.\n"
                + "Время в миллисекундах.По умолчанию 0."),
        FAKEMODEL("Имя модели терминала. Используется для удаленной загрузки"),
        FAKESERIALNUMBER("Имя серийного номера терминала. Используется для удаленной загрузки.\n"
                + "PAX: J00+s/n, INGENICO: K+s/n после букв PP, VERIFONE: G00+s/n без дефисов"),
        FORCEHOSTADDR("Переадресует запросы на хост на указанный на адрес. IPv4 адрес."),
        FORCEHOSTPORT("Перенаправляет соединение с порта YYY на ZZZ. Например, с боевого YYY=670 на на тестовый ZZZ=668.\n"
                + "YYY - порт указанный в вариантах связи. ZZZ - новый порт"),
        HEADER("Установка заголовка чека в пинпаде с помощью файла pinpad.ini.\n"
                + "Несколько строк разделенных символом «|». Полная длинна 254 символа."),
        IMAGEOUTPUTFORMAT("Формат сохранения подписи клиента на ККМ.\n"
                + "При наличии значения параметра:\n"
                + "«Дополнительные опции – Экранная подпись» отличном от «Не используется»"),
        LEFTTOPCORNER("Выводить окно в левом-верхнем углу"),
        LOSTWAITPACKETS("Значения, меньшие чем 3, игнорируются и параметр считается равным 3.\n"
        +"Функциональность MSB WAIT"),
        MERCHANTID("Установка номера терминала в пинпаде с помощью файла pinpad.ini.");

        private String explanation;

        PINPADINI_VALUES_EXPLANATIONS(String explanation) {
            this.explanation = explanation;
        }

        public String getExplanation() {
            return explanation;
        }

        }
}
