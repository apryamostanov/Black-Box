package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination

import java.util.concurrent.LinkedBlockingQueue

import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.init_custom
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_3_utils.not

class T_async_storage extends Thread {

    T_destination p_synchronous_destination = GC_NULL_OBJ_REF as T_destination
    String p_conf_name = GC_EMPTY_STRING
    Thread p_parent_thread = GC_NULL_OBJ_REF as Thread
    LinkedBlockingQueue<T_event> p_event_queue = new LinkedBlockingQueue<T_event>()

    T_async_storage(T_destination i_synchronous_destination, String i_conf_file_name, Thread i_parent_thread) {
        p_conf_name = i_conf_file_name
        p_synchronous_destination = i_synchronous_destination
        p_parent_thread = i_parent_thread
    }

    @Override
    void run() {
        init_custom(p_conf_name)
        while (GC_TRUE) {
            while (not(p_event_queue.isEmpty())) {
                p_synchronous_destination.store(p_event_queue.poll())
            }
            synchronized (this) {
                wait()
            }
        }
    }

    void flush() {
        while (not(p_event_queue.isEmpty())) {
            p_synchronous_destination.store(p_event_queue.poll())
        }
    }

}