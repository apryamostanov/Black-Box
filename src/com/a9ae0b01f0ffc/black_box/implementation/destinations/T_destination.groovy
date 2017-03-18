package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

abstract class T_destination extends T_logging_base_6_util {

    T_event_formatter p_formatter = GC_NULL_OBJ_REF as T_event_formatter
    HashMap<String, T_event> p_configuration_events_by_name = new HashMap<String, T_event>()
    String p_location = GC_EMPTY_STRING

    void add_configuration_event(T_event i_event) {
        p_configuration_events_by_name.put(i_event.get_event_type(), i_event)
    }

    void set_formatter(T_event_formatter i_formatter) {
        p_formatter = i_formatter
    }

    abstract void store(T_event i_event)

    void log_generic(T_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type()) || p_configuration_events_by_name.containsKey(GC_EVENT_TYPE_ALL)) {
            store(i_event)
        }
    }

    void set_location(String i_location) {
        p_location = i_location
    }

}