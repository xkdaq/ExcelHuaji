package cangdun.bean;

import java.io.IOException;

public enum Type {
    EMPTY;

    public String toValue() {
        switch (this) {
            case EMPTY:
                return "\u5355\u9009";
        }
        return null;
    }

    public static Type forValue(String value) throws IOException {
        if (value.equals("\u5355\u9009")) return EMPTY;
        throw new IOException("Cannot deserialize Type");
    }
}
