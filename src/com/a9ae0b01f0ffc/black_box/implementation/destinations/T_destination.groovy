package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_inherited_configurations
import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace

abstract class T_destination extends T_inherited_configurations implements I_destination {

    I_event_formatter p_formatter = GC_NULL_OBJ_REF as I_event_formatter
    HashMap<String, I_event> p_configuration_events_by_name = new HashMap<String, I_event>()
    String p_location = GC_EMPTY_STRING
    String p_buffer = GC_EMPTY_STRING
    Integer p_buffer_size
    String p_spool_event = GC_EMPTY_STRING
    String p_mask = GC_EMPTY_STRING

    String get_buffer() {
        return p_buffer
    }

    void set_buffer(String i_buffer) {
        this.p_buffer = i_buffer
        p_buffer_size = Integer.parseInt(p_buffer)
    }

    String get_spool_event() {
        return p_spool_event
    }

    void set_spool_event(String i_spool_event) {
        this.p_spool_event = i_spool_event
    }

    @Override
    void add_configuration_event(I_event i_event) {
        p_configuration_events_by_name.put(i_event.get_event_type(), i_event)
    }

    @Override
    void set_formatter(I_event_formatter i_formatter) {
        p_formatter = i_formatter
    }

    @Override
    void log_generic(I_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type())) {
            store(i_event)
            i_event.get_invocation().set_event_logged_for_destination(i_event.get_event_type(), this)
        }
    }

    @Override
    ArrayList<I_trace> prepare_trace_list(I_event i_event_runtime) {
        return i_event_runtime.get_traces_runtime()
    }

    @Override
    void set_location(String i_location) {
        p_location = i_location
    }

    @Override
    String get_mask() {
        return p_mask
    }

    @Override
    void set_mask(String i_mask) {
        p_mask = i_mask
    }
}
