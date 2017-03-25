package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

import java.util.logging.FileHandler
import java.util.logging.Handler
import java.util.logging.LogManager
import java.util.logging.Logger

@ToString(includeNames = true, includeFields = true)
class T_destination_jul extends T_destination {

    Logger p_logger_jul = GC_NULL_OBJ_REF as Logger
    Boolean p_is_jul_init = GC_FALSE

    void init() {
        LogManager.getLogManager().readConfiguration(new FileInputStream(new File(c().GC_JUL_CONF_FILE_NAME)))
        Handler l_file_handler = new FileHandler(process_location(p_location, c()))
        p_logger_jul = Logger.getLogger(this.class.getName())
        p_logger_jul.addHandler(l_file_handler)
        p_is_jul_init = GC_TRUE
    }

    void store(T_event i_source_event) {
        if (! p_is_jul_init) {
            init()
        }
        p_logger_jul.finest(p_formatter.format_event(i_source_event))//todo: map black box event types to JUL log levels
    }

    @Override
    T_destination clone_with_no_async() {
        T_destination_jul l_result = new T_destination_jul()
        l_result.p_logger_jul = this.p_logger_jul
        l_result.p_is_jul_init = p_is_jul_init
        l_result.p_formatter = p_formatter
        l_result.p_configuration_events_by_name = p_configuration_events_by_name
        l_result.p_location = p_location
        return l_result
    }

}