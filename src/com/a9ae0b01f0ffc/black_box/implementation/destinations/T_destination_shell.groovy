package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_shell extends T_destination {

    void store(T_event i_source_event) {
        System.out.print(p_formatter.format_event(i_source_event))
    }

    @Override
    T_destination clone_with_no_async() {
        T_destination_shell l_result = new T_destination_shell()
        l_result.p_formatter = p_formatter
        l_result.p_configuration_events_by_name = p_configuration_events_by_name
        l_result.p_location = p_location
        return l_result
    }

}