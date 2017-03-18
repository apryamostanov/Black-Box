package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

import java.util.logging.FileHandler
import java.util.logging.Handler
import java.util.logging.LogManager
import java.util.logging.Logger

@ToString(includeNames = true, includeFields = true)
class T_destination_jul extends T_destination {

    Logger p_logger = GC_NULL_OBJ_REF as Logger

    void init() {
        LogManager.getLogManager().readConfiguration(new FileInputStream(new File(c().GC_JUL_CONF_FILE_NAME)))
        Handler l_file_handler = new FileHandler(process_location(p_location, c()))
        p_logger = Logger.getLogger(this.class.getName())
        p_logger.addHandler(l_file_handler)
    }

    void store(T_event i_source_event) {
        p_logger.finest(p_formatter.format_event(i_source_event))//todo: map black box event types to JUL log levels
    }

}