package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

abstract class T_event_formatter extends T_logging_base_6_util implements I_event_formatter {

    String p_print_trace_guid = GC_TRUE_STRING
    private I_destination p_parent_destination = GC_NULL_OBJ_REF as I_destination

    @Override
    I_destination get_parent_destination() {
        return p_parent_destination
    }

    @Override
    void set_parent_destination(I_destination i_parent_destination) {
        p_parent_destination = i_parent_destination
    }
}
