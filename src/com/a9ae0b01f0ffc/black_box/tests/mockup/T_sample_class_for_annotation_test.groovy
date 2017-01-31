package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.implementation.annotations.BlackBox
import com.a9ae0b01f0ffc.black_box.implementation.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_sample_class_for_annotation_test {

    @I_black_box
    String do_something(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD)
        return "z" + new T_pan("34234234234234234").toString()
    }

    @I_black_box
    void do_something2(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD2)
    }

    @I_black_box
    String do_something3(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD3)
        if (i_argument_one==i_argument_two) {
            T_s.l().log_info(T_s.s().INSIDE_METHOD5)
            if (i_argument_one==i_argument_two) {
                for (int z : 1..2) {
                    if (i_argument_one==i_argument_two) {
                        T_s.l().log_info(T_s.s().INSIDE_METHODz7)
                        return "xcvcx"
                    }
                }
                return "lfdgh"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
            return "jrtnxdn"
        } else {
            T_s.l().log_info(T_s.s().INSIDE_METHOD6)
            if (i_argument_one!=i_argument_two) {
                T_s.l().log_info(T_s.s().INSIDE_METHOD17)
                return "irtgkjbdf"
            } else {
                T_s.l().log_info(T_s.s().INSIDE_METHOD8)
            }
        }
        T_s.l().log_info(T_s.s().INSIDE_METHOD19)
        T_s.l().log_info(T_s.s().INSIDE_METHOD4)
        return "dflkgnjdlfk"
    }


}
