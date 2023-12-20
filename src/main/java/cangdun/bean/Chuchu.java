package cangdun.bean;

import java.io.IOException;

public enum Chuchu {
    THE_24_X1000_190;

    public String toValue() {
        switch (this) {
            case THE_24_X1000_190:
                return "24\u7248X1000\u80cc\u8bf5\u624b\u518c190\u9898";
        }
        return null;
    }

    public static Chuchu forValue(String value) throws IOException {
        if (value.equals("24\u7248X1000\u80cc\u8bf5\u624b\u518c190\u9898")) return THE_24_X1000_190;
        throw new IOException("Cannot deserialize Chuchu");
    }
}
