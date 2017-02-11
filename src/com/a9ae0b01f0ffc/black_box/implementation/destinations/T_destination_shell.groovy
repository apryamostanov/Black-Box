package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_shell extends T_destination {

    @Override
    @I_black_box("error")
    void store(ArrayList<I_trace> i_trace_list, I_event i_source_event) {
        System.out.print(p_formatter.format_traces(i_trace_list, i_source_event))
    }

}