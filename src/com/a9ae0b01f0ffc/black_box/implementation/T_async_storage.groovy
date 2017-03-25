package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination

import java.util.concurrent.SynchronousQueue

import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.c
import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.init_custom
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_3_utils.is_not_null

class T_async_storage extends Thread {

    T_destination p_synchronous_destination = GC_NULL_OBJ_REF as T_destination
    T_event p_current_event = GC_NULL_OBJ_REF as T_event
    String p_conf_name = GC_EMPTY_STRING
    Thread p_parent_thread = GC_NULL_OBJ_REF as Thread
    SynchronousQueue<T_event> l_event_queue = new SynchronousQueue<T_event>()

    T_async_storage(T_destination i_synchronous_destination, String i_conf_file_name, Thread i_parent_thread) {
        p_conf_name = i_conf_file_name
        p_synchronous_destination = i_synchronous_destination
        p_parent_thread = i_parent_thread
    }

    synchronized void push(T_event i_event) {
        i_event.set_previous_event(p_current_event)
        if (is_not_null(p_current_event)) {
            p_current_event.set_next_event(i_event)
        }
        p_current_event = i_event
    }

    synchronized T_event pop() {
        T_event l_head_event = p_current_event
        p_current_event = GC_NULL_OBJ_REF as T_event
        return l_head_event
    }
//do not store event stack on event; it is not thread safe; store it on async storage level
    @Override
    void run() {
        init_custom(p_conf_name)
        while (p_parent_thread.isAlive() || is_not_null(p_current_event)) {
            T_event l_head_event = pop()
            if (is_not_null(l_head_event)) {
                T_event l_event_to_store = l_head_event.get_root_event()
                while (is_not_null(l_event_to_store)) {
                    p_synchronous_destination.store(l_event_to_store)
                    l_event_to_store = l_event_to_store.get_next_event()
                }
            }
            sleep(new Long(c().GC_ASYNC_STORER_POLL_PERIOD_MILLIS))
        }
    }

}