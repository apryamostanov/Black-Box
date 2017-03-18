package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_shell extends T_destination {

    void store(T_event i_source_event) {
        System.out.print(p_formatter.format_event(i_source_event))
    }

}