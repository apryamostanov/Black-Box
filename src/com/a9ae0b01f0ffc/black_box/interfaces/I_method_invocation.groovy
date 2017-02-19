package com.a9ae0b01f0ffc.black_box.interfaces

interface I_method_invocation {

    String get_class_name()
    String get_method_name()

    ArrayList<I_trace> get_method_arguments()

    void set_class_name(String i_class_name)

    void set_method_name(String i_method_name)

    void set_method_arguments(ArrayList<I_trace> i_method_arguments)

    void stop_timing()

    void start_timing()

    Long get_elapsed_time()

    Boolean is_event_logged_for_destination(String i_event_type, I_destination i_destination)

    void set_event_logged_for_destination(String i_event_type, I_destination i_destination)

}