package cangdun.bean;

import java.io.IOException;

public enum Text {
    A, B, C, D,AB,AC,AD,BC,BD,CD,ABC,ABD,ACD,BCD,ABCD;

    public int toValue() {
        switch (this) {
            case A:
                return 1;
            case B:
                return 2;
            case C:
                return 3;
            case D:
                return 4;
            case AB:
                return 12;
            case AC:
                return 13;
            case AD:
                return 14;
            case BC:
                return 23;
            case BD:
                return 24;
            case CD:
                return 34;
            case ABC:
                return 123;
            case ABD:
                return 124;
            case ACD:
                return 134;
            case BCD:
                return 234;
            case ABCD:
                return 1234;
        }
        return 0;
    }

    public String toStrValue() {
        switch (this) {
            case A:
                return "A";
            case B:
                return "B";
            case C:
                return "C";
            case D:
                return "D";
            case AB:
                return "AB";
            case AC:
                return "AC";
            case AD:
                return "AD";
            case BC:
                return "BC";
            case BD:
                return "BD";
            case CD:
                return "CD";
            case ABC:
                return "ABC";
            case ABD:
                return "ABD";
            case ACD:
                return "ACD";
            case BCD:
                return "BCD";
            case ABCD:
                return "ABCD";
        }
        return "";
    }

    public static Text forValue(String value) throws IOException {
        if (value.equals("A")) return A;
        if (value.equals("B")) return B;
        if (value.equals("C")) return C;
        if (value.equals("D")) return D;
        throw new IOException("Cannot deserialize Text");
    }
}
