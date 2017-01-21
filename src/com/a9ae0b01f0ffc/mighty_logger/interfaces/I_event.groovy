package com.a9ae0b01f0ffc.mighty_logger.interfaces

import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.runtime.I_method_argument_referenceable
import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.traces.I_trace_nameable
import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.traces.I_trace_referenceable

interface I_event {

    String get_class_name()

    String get_method_name()

    String get_guid()

    Integer get_depth()

    I_trace[] get_method_arguments_event()

    void add_trace(I_trace i_trace)

    void add_trace_config(I_trace i_trace_config)

    ArrayList<I_trace> get_traces()

    ArrayList<I_trace> get_traces_config()

    String get_datetimestamp()

}