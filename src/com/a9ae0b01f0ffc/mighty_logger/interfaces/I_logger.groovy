package com.a9ae0b01f0ffc.mighty_logger.interfaces

import com.a9ae0b01f0ffc.static_string.T_static_string

interface I_logger {

    String get_logger_id()

    Integer get_depth()

    void log_generic(I_event i_event)

    ArrayList<Object> get_trace_context_list()

    void put_to_context(Object i_object)

    void add_destination(I_destination i_destination)

    Boolean init()

    I_method_invocation get_current_method_invocation()

    void log_enter(
            String i_class_name
            , String i_method_name
            , Object... i_trace_args
    )

    void log_exit(
            String i_class_name
            , String i_method_name
            , Object... i_trace_args
    )

    void log_exception(String i_class_name, String i_method_name, Exception i_exception, Object... i_method_arguments)

    void log_debug(
            T_static_string i_static_string_message
            , Object... i_trace_args
    )

    void log_info(
            T_static_string i_static_string_info
            , Object... i_trace_args
    )

    void log_warning(
            T_static_string i_static_string_warning
            , Object... i_trace_args
    )

    I_event create_event(String i_event_type, String i_class_name, String i_method_name)

    I_trace object2trace(Object i_object)

    I_trace[] objects2traces(Object[] i_objects)

    I_trace trace2trace(I_trace i_trace)

    void set_mode(String i_mode)

}