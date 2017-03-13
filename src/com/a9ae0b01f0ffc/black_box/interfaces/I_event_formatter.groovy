package com.a9ae0b01f0ffc.black_box.interfaces


interface I_event_formatter {

    String format_event(I_event i_source_event)

    I_destination get_parent_destination()

    void set_parent_destination(I_destination i_parent_destination)

}