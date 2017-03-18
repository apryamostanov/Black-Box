package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

abstract class T_event_formatter extends T_logging_base_6_util {

    private T_destination p_parent_destination = GC_NULL_OBJ_REF as T_destination

    T_destination get_parent_destination() {
        return p_parent_destination
    }

    void set_parent_destination(T_destination i_parent_destination) {
        p_parent_destination = i_parent_destination
    }

    abstract String format_event(T_event i_event)
}
