package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_file extends T_destination {

    File p_file = GC_NULL_OBJ_REF as File
    FileWriter p_file_writer = GC_NULL_OBJ_REF as FileWriter
    Boolean p_is_dest_init = GC_FALSE
    String p_current_location = GC_EMPTY_STRING

    @Override
    T_destination clone_with_no_async() {
        T_destination_file l_result = new T_destination_file()
        l_result.p_file = p_file
        l_result.p_file_writer = p_file_writer
        l_result.p_is_dest_init = p_is_dest_init
        l_result.p_current_location = p_current_location
        l_result.p_is_auto_zip = p_is_auto_zip
        l_result.p_formatter = p_formatter
        l_result.p_configuration_events_by_name = p_configuration_events_by_name
        l_result.p_location = p_location
        return l_result
    }

    @Override
    void store(T_event i_source_event) {
        init()
        p_file_writer.write(p_formatter.format_event(i_source_event))
        p_file_writer.flush()
    }

    void init() {
        if (!p_is_dest_init) {
            p_current_location = process_location(p_location, c())
            p_file = new File(p_current_location)
            p_file.getParentFile().mkdirs()
            p_file_writer = new FileWriter(p_file, GC_FILE_APPEND_YES)
            p_is_dest_init = GC_TRUE
        } else if (p_is_auto_zip) {
            if (p_location.indexOf(c().GC_SUBST_TIME) != GC_CHAR_INDEX_NOT_EXISTING) {
                throw new E_application_exception(s.Auto_zip_is_not_supported_when_file_name_mask_contains_Time_Z1, p_location)
            }
            String l_new_location = process_location(p_location, c())
            if (p_current_location != l_new_location) {
                File l_file_to_zip = p_file
                Thread.start({
                    zip_file(l_file_to_zip)
                    l_file_to_zip.delete()
                })
                p_current_location = l_new_location
                p_file = new File(p_current_location)
                p_file.getParentFile().mkdirs()
                p_file_writer = new FileWriter(p_file, GC_FILE_APPEND_YES)
                p_is_dest_init = GC_TRUE
            }
        }
    }

}