package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.T_object_with_guid
import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base

abstract class T_event_formatter extends T_object_with_guid implements I_event_formatter {

    String p_print_trace_guid = T_logging_const.GC_TRUE_STRING
    private I_destination p_parent_destination = GC_NULL_OBJ_REF as I_destination

    @I_black_box_base("error")
    String get_print_trace_guid() {
        return p_print_trace_guid
    }

    @I_black_box_base("error")
    void set_print_trace_guid(String i_print_trace_guid) {
        p_print_trace_guid = i_print_trace_guid
    }

    @I_black_box_base("error")
    Boolean is_guid() {
        return p_print_trace_guid == T_logging_const.GC_TRUE_STRING ? T_logging_const.GC_TRUE : T_logging_const.GC_FALSE
    }

    @I_black_box_base("error")
    I_destination get_parent_destination() {
        return p_parent_destination
    }

    @I_black_box_base("error")
    void set_parent_destination(I_destination i_parent_destination) {
        p_parent_destination = i_parent_destination
    }
}
