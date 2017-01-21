package com.a9ae0b01f0ffc.mighty_logger.interfaces

import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.formatters.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.model.interfaces.traces.I_destination_purpose

interface I_destination {

    void log_generic(I_event i_event)

    void add_event(I_event i_event)

    void set_formatter(I_event_formatter i_formatter)

    ArrayList<I_trace> prepare_trace_list(I_event i_event)

    void add_configuration_event(I_event i_event)

}