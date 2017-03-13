package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_array extends T_destination {

    static ArrayList<String> p_log_lines = new ArrayList<String>()

    @Override
    void store(I_event i_source_event) {
        p_log_lines.add(p_formatter.format_event(i_source_event))
    }

}