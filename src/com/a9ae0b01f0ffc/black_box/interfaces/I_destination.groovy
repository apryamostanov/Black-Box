package com.a9ae0b01f0ffc.black_box.interfaces

interface I_destination extends I_inherited_configurations{

    void log_generic(I_event i_event)

    void set_formatter(I_event_formatter i_formatter)

    ArrayList<I_trace> prepare_trace_list(I_event i_event)

    void add_configuration_event(I_event i_event)

    void set_location(String i_location)

    void store(I_event i_source_event)

    String get_buffer()

    void set_buffer(String i_buffer)

    String get_spool_event()

    void set_spool_event(String i_spool_event)

    void init()

}