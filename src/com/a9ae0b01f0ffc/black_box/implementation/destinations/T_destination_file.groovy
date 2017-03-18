package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_file extends T_destination {

    File p_file = GC_NULL_OBJ_REF as File
    FileWriter p_file_writer = GC_NULL_OBJ_REF as FileWriter
    Boolean p_is_file_init = GC_FALSE

    @Override
    void store(T_event i_source_event) {
        init_file()
        p_file_writer.write(p_formatter.format_event(i_source_event))
        p_file_writer.flush()
    }

    void init_file() {
        if (!p_is_file_init) {
            String l_location = process_location(p_location, c())
            p_file = new File(l_location)
            p_file.getParentFile().mkdirs()
            p_file_writer = new FileWriter(p_file, GC_FILE_APPEND_YES)
            p_is_file_init = GC_TRUE
        }
    }

}