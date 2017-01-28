package com.a9ae0b01f0ffc.mighty_logger.interfaces

interface I_destination {

    void log_generic(I_event i_event)

    void set_formatter(I_event_formatter i_formatter)

    ArrayList<I_trace> prepare_trace_list(I_event i_event)

    void add_configuration_event(I_event i_event)

    void set_destination_purpose(String i_destination_purpose)

    void set_location(String i_location)

    String get_destination_purpose()

    void store(ArrayList<I_trace> i_trace_list)

    String get_buffer()

    void set_buffer(String i_buffer)

    String get_spool_event()

    void set_spool_event(String i_spool_event)

}