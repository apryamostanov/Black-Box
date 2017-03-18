package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string

interface I_event extends I_inherited_configurations {

    String get_class_name()

    String get_method_name()

    void add_trace_runtime(I_trace i_trace)

    void add_trace_config(I_trace i_trace_config)

    ArrayList<I_trace> get_traces_runtime()

    ArrayList<I_trace> get_traces_config()

    String get_datetimestamp()

    String get_message()

    Throwable get_throwable()

    String get_event_type()

    void set_event_type(String i_event_type)

    void set_class_name(String i_class_name)

    void set_method_name(String i_method_name)

    void set_datetimestamp(Date i_datetimestamp)

    void add_traces_runtime(Collection<I_trace> i_traces_runtime)

    void set_message(T_static_string i_message)

    void set_throwable(Throwable i_throwable)

    Boolean is_trace_masked(I_trace i_trace)

    void set_invocation(I_method_invocation i_method_invocation)

    I_method_invocation get_invocation()

    Integer get_line_number()

    void set_line_number(Integer i_line_number)

    String get_statement_name()

    void set_statement_name(String i_statement_name)

}