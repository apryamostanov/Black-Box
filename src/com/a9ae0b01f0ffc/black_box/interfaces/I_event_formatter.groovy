package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base

interface I_event_formatter {

    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event)

    String get_print_trace_guid()

    void set_print_trace_guid(String i_print_trace_guid)

    Boolean is_guid()

    @I_black_box_base("error")
    I_destination get_parent_destination()

    @I_black_box_base("error")
    void set_parent_destination(I_destination i_parent_destination)

}