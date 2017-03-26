package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_async_storage
import com.a9ae0b01f0ffc.black_box.implementation.T_event
import com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util

abstract class T_destination extends T_logging_base_6_util {

    T_event_formatter p_formatter = GC_NULL_OBJ_REF as T_event_formatter
    HashMap<String, T_event> p_configuration_events_by_name = new HashMap<String, T_event>()
    String p_location = GC_EMPTY_STRING
    T_async_storage p_async_storage = GC_NULL_OBJ_REF as T_async_storage
    Boolean p_is_auto_zip = GC_FALSE

    Boolean is_auto_zip() {
        return p_is_auto_zip
    }

    void set_auto_zip(Boolean i_is_auto_zip) {
        p_is_auto_zip = i_is_auto_zip
    }

    void set_async_storage(T_async_storage i_async_storage) {
        p_async_storage = i_async_storage
    }

    void add_configuration_event(T_event i_event) {
        p_configuration_events_by_name.put(i_event.get_event_type(), i_event)
    }

    void set_formatter(T_event_formatter i_formatter) {
        p_formatter = i_formatter
    }

    abstract T_destination clone_with_no_async()

    abstract void store(T_event i_event)

    void log_generic(T_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type()) || p_configuration_events_by_name.containsKey(GC_EVENT_TYPE_ALL)) {
            if (is_not_null(p_async_storage)) {
                /*\/\/\/Prevent changes on trace objects from separate threads*/
                i_event.serialize_traces()
                /*/\/\/\This slows down the main thread, however without this there are Concurrent Modification exceptions on ArrayList serialization (when array list was logged but is being accessed from another thread)*/
                p_async_storage.p_event_queue.add(i_event)
                synchronized (p_async_storage) {
                    p_async_storage.notifyAll()
                }
            } else {
                store(i_event)
            }
        }
    }

    void set_location(String i_location) {
        p_location = i_location
    }

    void flush() {
        if (is_not_null(p_async_storage)) {
            p_async_storage.start()
            p_async_storage = p_async_storage.clone()
        }
    }

}