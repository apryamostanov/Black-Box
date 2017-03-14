package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import groovy.transform.ToString

import java.util.logging.FileHandler
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

@ToString(includeNames = true, includeFields = true)
class T_destination_jul extends T_destination {

    Logger p_logger = GC_NULL_OBJ_REF as Logger

    LinkedList<String> p_serialized_events = new LinkedList<String>()

    void init() {
        LogManager.getLogManager().readConfiguration(new FileInputStream(new File(c().GC_JUL_CONF_FILE_NAME)))
        Handler l_file_handler = new FileHandler(process_location(p_location, c()))
        p_logger = Logger.getLogger(this.class.getName())
        p_logger.addHandler(l_file_handler)
    }

    @Override
    void store(I_event i_source_event) {
        String l_serialized_event = p_formatter.format_event(i_source_event)
        p_serialized_events.push(l_serialized_event)
        if (is_not_null(p_buffer)) {
            while (p_serialized_events.size() > p_buffer_size) {
                String l_serialized_event_to_discard = p_serialized_events.pollFirst()
            }
        }
    }

    @Override
    void log_generic(I_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type())) {
            store(i_event)
            i_event.get_invocation().set_event_logged_for_destination(i_event.get_event_type(), this)
        }
        if (i_event.get_event_type() == p_spool_event || is_null(p_buffer)) {
            while (!p_serialized_events.isEmpty()) {
                String l_event_to_write = p_serialized_events.pollFirst()
                p_logger.finest(l_event_to_write)
            }
        }
    }
}