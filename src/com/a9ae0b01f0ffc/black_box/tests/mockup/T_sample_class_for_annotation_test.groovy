package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.implementation.annotations.BlackBox
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_sample_class_for_annotation_test {

    @BlackBox
    String do_something(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD)
        return "z" + new T_pan("34234234234234234").toString()
    }

    @BlackBox
    void do_something2(String i_argument_one, String i_argument_two) {
        T_s.l().log_info(T_s.s().INSIDE_METHOD2)
    }

}
