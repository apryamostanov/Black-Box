package com.a9ae0b01f0ffc.mighty_logger.interfaces

interface I_trace {

    Boolean is_muted()

    Boolean is_masked()

    void set_muted(Boolean i_is_muted)

    void set_masked(Boolean i_is_masked)

    void set_formatter(I_trace_formatter i_trace_formatter)

    I_trace_formatter get_formatter()

    void set_child_traces(ArrayList<I_trace> i_child_traces)

    ArrayList<I_trace> get_child_traces()

    void add_child_trace(I_trace i_child_trace_config)

}