package ru.geekbrais.eda.utils;

public enum Images {

    POSITIVE("src/test/resources/kosmonavt.jpg"),
    FROM_URL("https://cdn1.savepice.ru/uploads/2021/2/25/202bea9667357e346c31262a8976b06a-full.jpg"),
    BIG_SIZE("src/test/resources/big.png"),
    SMALL("src/test/resources/small.jpg");


    public final String path;

    Images(String path) {
        this.path = path;

    }
}
