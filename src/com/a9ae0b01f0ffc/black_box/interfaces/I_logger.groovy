package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string

interface I_logger {

    Integer get_depth()

    void log_generic(I_event i_event)

    ArrayList<I_trace> get_trace_context_list()

    void put_to_context(Object i_object, String i_name)

    void add_destination(I_destination i_destination)

    Boolean init()

    I_method_invocation get_current_method_invocation()

    LinkedList<I_method_invocation> get_invocation_stack()

    void print_stats()

    void log_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, I_trace... i_traces)

    void profile_exit()

    void profile_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number)

    void log_exit()

    Object log_result(I_trace i_return_object_trace)

    Object profile_exit_automatic(Object i_return_object)

    void log_error(Throwable i_throwable, I_trace... i_traces)

    void log_debug(T_static_string i_static_string_message, Object... i_traces)

    void log_info(T_static_string i_static_string_info, Object... i_traces)

    void log_warning(T_static_string i_static_string_warning, Object... i_traces)

    void log_receive(Object i_incoming_data)

    void log_send(Object i_outgoing_data)

    void log_sql(String i_sql_operation, String i_sql_string, String... i_bind_variables)

    I_event create_event(String i_event_type, String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number)

    I_method_invocation get_default_method_invocation()

}