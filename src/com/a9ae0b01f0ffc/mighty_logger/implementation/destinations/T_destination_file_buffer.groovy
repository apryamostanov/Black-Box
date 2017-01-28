package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace

class T_destination_file_buffer extends T_destination_file {

    LinkedList<String> p_serialized_events = new LinkedList<String>()

    @Override
    void store(ArrayList<I_trace> i_trace_list) {
        init_file()
        String l_serialized_event = p_formatter.format_traces(i_trace_list)
        p_serialized_events.push(l_serialized_event)
        while (p_serialized_events.size() > p_buffer_size) {
            String l_serialized_event_to_discard = p_serialized_events.pollFirst()
        }
    }

    @Override
    void log_generic(I_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type())) {
            ArrayList<I_trace> l_trace_list = prepare_trace_list(i_event)
            store(l_trace_list)
        }
        if (i_event.get_event_type() == p_spool_event) {
            while (!p_serialized_events.isEmpty()) {
                String l_event_to_write = p_serialized_events.pollFirst()
                p_file_writer.write(l_event_to_write)
            }
        }
    }

}