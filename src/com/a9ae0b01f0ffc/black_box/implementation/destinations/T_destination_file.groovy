package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_file extends T_destination {

    File p_file = T_logging_const.GC_NULL_OBJ_REF as File
    FileWriter p_file_writer = T_logging_const.GC_NULL_OBJ_REF as FileWriter
    Boolean p_is_file_init = T_logging_const.GC_FALSE

    @Override
    @I_black_box_base("error")
    void store(ArrayList<I_trace> i_trace_list, I_event i_source_event) {
        init_file()
        p_file_writer.write(p_formatter.format_traces(i_trace_list, i_source_event))
        p_file_writer.flush()
    }

    @I_black_box_base("error")
    void init_file() {
        if (!p_is_file_init) {
            String l_location = T_u.process_location(p_location, T_s.commons())
            p_file = new File(l_location)
            p_file.getParentFile().mkdirs()
            p_file_writer = new FileWriter(p_file, T_logging_const.GC_FILE_APPEND_YES)
            p_is_file_init = T_logging_const.GC_TRUE
        }
    }

}