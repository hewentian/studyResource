package com.hewentian.jdk.c03.s06;

public class ProperVar {
    private int i_int;
    private byte b_byte;
    private char c_char;
    private long l_long;
    private boolean b_boolean;
    private float f_float;
    private double d_double;
    private String s_string;

    private Integer i_Integer;
    private Byte b_Byte;
    private Character c_Character;
    private Long l_Long;
    private Boolean b_Boolean;
    private Float f_Float;
    private Double d_Double;

    // 输出属性的默认值
    public void print() {
        System.out.println("i_int = " + i_int); // 0
        System.out.println("b_byte = " + b_byte); // 0
        System.out.println("c_char = " + c_char); //
        System.out.println("l_long = " + l_long); // 0
        System.out.println("b_boolean = " + b_boolean); // false
        System.out.println("f_float = " + f_float); // 0.0
        System.out.println("d_double = " + d_double); // 0.0
        System.out.println("s_string = " + s_string); // null

        System.out.println("i_Integer = " + i_Integer); // null
        System.out.println("b_Byte = " + b_Byte); // null
        System.out.println("c_Character = " + c_Character); // null
        System.out.println("l_Long = " + l_Long); // null
        System.out.println("b_Boolean = " + b_Boolean); // null
        System.out.println("f_Float = " + f_Float); // null
        System.out.println("d_Double = " + d_Double); // null
    }

    public static void main(String[] args) {
        ProperVar pv = new ProperVar();
        pv.print();
    }
}
