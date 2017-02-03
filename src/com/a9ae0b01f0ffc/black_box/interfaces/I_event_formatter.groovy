package com.a9ae0b01f0ffc.black_box.interfaces

interface I_event_formatter {

    String format_traces(ArrayList<I_trace> i_event_traces)

    String get_print_trace_guid()

    void set_print_trace_guid(String i_print_trace_guid)

    Boolean is_guid()

}