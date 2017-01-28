package com.a9ae0b01f0ffc.mighty_logger.interfaces

interface I_method_invocation {

    String get_class_name()
    String get_method_name()

    ArrayList<I_trace> get_method_arguments()

    void set_class_name(String i_class_name)

    void set_method_name(String i_method_name)

    void set_method_arguments(ArrayList<I_trace> i_method_arguments)

}