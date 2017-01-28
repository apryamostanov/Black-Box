package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_trace

class T_destination_shell extends T_destination {

    @Override
    void store(ArrayList<I_trace> i_trace_list) {
        System.out.println(p_formatter.format_traces(i_trace_list))
    }

}