package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.static_string.T_static_string

interface I_logger {

    String get_logger_id()

    Integer get_depth()

    void log_generic(I_event i_event)

    ArrayList<I_trace> get_trace_context_list()

    void put_to_context(Object i_object, String i_name)

    void add_destination(I_destination i_destination)

    Boolean init()

    I_method_invocation get_current_method_invocation()

    LinkedList<I_method_invocation> get_invocation_stack()

    void print_stats()

    void log_enter(
            String i_class_name
            , String i_method_name
            , I_trace... i_traces
    )

    void log_exit(
            String i_class_name
            , String i_method_name
            , I_trace... i_traces
    )

    Object log_exit_automatic(
            String i_class_name
            , String i_method_name
            , I_trace i_return_object_trace
    )

    void log_exception(String i_class_name, String i_method_name, Throwable i_throwable, I_trace... i_traces)

    void log_error(String i_class_name, String i_method_name, Throwable i_throwable, I_trace... i_traces)

    void log_debug(
            T_static_string i_static_string_message
            , Object... i_traces
    )

    void log_info(
            T_static_string i_static_string_info
            , Object... i_traces
    )

    void log_warning(
            T_static_string i_static_string_warning
            , Object... i_traces
    )

    I_event create_event(String i_event_type, String i_class_name, String i_method_name)

    I_trace object2trace(Object i_object, String i_source)

    ArrayList<I_trace> objects2traces(Collection<Object> i_objects, String i_source)

    ArrayList<I_trace> objects2traces_array(Object[] i_objects, String i_source)

    I_trace spawn_trace(I_trace i_trace_runtime_or_context, I_trace i_trace_config)

    void set_mode(String i_mode)

}