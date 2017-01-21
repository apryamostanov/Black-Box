package com.a9ae0b01f0ffc.mighty_logger.interfaces

import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.destinations.I_destination
import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.events.I_event
import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.runtime.I_method_invocation
import com.a9ae0b01f0ffc.static_string.T_static_string

interface I_logger {

    String get_logger_id()

    Integer get_depth()

    void log_generic(I_event i_event)

    Object get_from_context(String i_short_name)

    void put_to_context(Object i_object)

    void add_destination(I_destination i_destination)

    void init()

    I_method_invocation get_current_method_invocation()

    void log_enter(
            String i_class_name
            , String i_method_name
            , Object... i_trace_args
    )

    void log_exit(
            Object... i_trace_args
    )

    void log_exception(
            Exception i_exception
            , Object... i_trace_args
    )

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

}