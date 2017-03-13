package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import groovy.transform.ToString

import java.util.logging.Logger

@ToString(includeNames = true, includeFields = true)
class T_destination_jul extends T_destination {

    Logger l_logger = Logger.getLogger(this.class.getName())

    @Override
    void store(I_event i_source_event) {
    }



}