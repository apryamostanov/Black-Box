package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string

interface I_logger {

    void log_generic(I_event i_event)

    ArrayList<I_trace> get_trace_context_list()

    void put_to_context(Object i_object, String i_name)

    void add_destination(I_destination i_destination)

    Boolean init()

    LinkedList<I_method_invocation> get_invocation_stack()

    I_method_invocation get_current_method_invocation()

    void print_stats()

    void log_method_enter(String i_class_name, String i_method_name, Integer i_line_number, I_trace... i_traces)

    void log_method_exit()

    void log_statement_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, I_trace... i_traces)

    void log_statement_exit()

    void profile_method_enter(String i_class_name, String i_method_name, Integer i_line_number)

    void profile_method_exit()

    void profile_statement_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number)

    void profile_statement_exit()

    void log_method_error(String i_class_name, String i_method_name, Integer i_line_number, Throwable i_throwable, I_trace... i_traces)

    void log_statement_error(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, Throwable i_throwable, I_trace... i_traces)

    void log_generic_automatic(String i_event_type, Integer i_line_number, T_static_string i_message, String i_class_name, String i_statement_name, I_trace... i_traces)

    Object log_result(I_trace i_return_object_trace)

    void log_debug(T_static_string i_static_string_message, Object... i_traces)

    void log_trace(Object... i_traces)

    void log_info(T_static_string i_static_string_info, Object... i_traces)

    void log_warning(T_static_string i_static_string_warning, Object... i_traces)

    void log_receive_tcp(Object... i_traces)

    void log_send_tcp(Object... i_traces)

    void log_send_sql(Object... i_traces)

    void log_receive_sql(Object... i_traces)

    void log_send_http(Object... i_traces)

    void log_receive_http(Object... i_traces)

    void log_send_i2c(Object... i_traces)

    void log_receive_i2c(Object... i_traces)

    void log_send_spi(Object... i_traces)

    void log_receive_spi(Object... i_traces)

    void log_send_serial(Object... i_traces)

    void log_receive_serial(Object... i_traces)

    void log_send_bluetooth(Object... i_traces)

    void log_receive_bluetooth(Object... i_traces)

    I_event create_event(String i_event_type, String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number)

    I_method_invocation get_default_method_invocation()

}