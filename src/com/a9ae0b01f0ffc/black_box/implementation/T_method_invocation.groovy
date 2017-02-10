package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_const
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_method_invocation implements I_method_invocation {

    String p_class_name = T_const.GC_EMPTY_STRING
    String p_method_name = T_const.GC_EMPTY_STRING
    ArrayList<I_trace> p_method_arguments = T_const.GC_SKIPPED_ARGS as ArrayList<I_trace>

    @Override
    String get_class_name() {
        return p_class_name
    }

    @Override
    String get_method_name() {
        return p_method_name
    }

    @Override
    ArrayList<I_trace> get_method_arguments() {
        return p_method_arguments
    }

    @Override
    void set_class_name(String i_class_name) {
        this.p_class_name = i_class_name
    }

    @Override
    void set_method_name(String i_method_name) {
        this.p_method_name = i_method_name
    }

    @Override
    void set_method_arguments(ArrayList<I_trace> i_method_arguments) {
        this.p_method_arguments = i_method_arguments
    }


    @Override
    public String toString() {
        return "T_method_invocation{" +
                "p_class_name='" + p_class_name + '\'' +
                ", p_method_name='" + p_method_name + '\'' +
                ", p_method_arguments=" + p_method_arguments +
                '}';
    }
}
