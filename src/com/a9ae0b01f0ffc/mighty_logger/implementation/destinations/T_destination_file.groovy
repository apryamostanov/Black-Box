package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_destination_file extends T_destination {

    File p_file = T_s.c().GC_NULL_OBJ_REF as File
    FileWriter p_file_writer = T_s.c().GC_NULL_OBJ_REF as FileWriter
    Boolean p_is_init = init()

    @Override
    void store(ArrayList<I_trace> i_trace_list) {
        p_file_writer.write(p_formatter.format_traces(i_trace_list))
        p_file_writer.flush()
    }

    static String process_location(String i_location) {
        Date l_current_date = new Date()
        String l_location = i_location
        l_location = l_location.replaceAll(T_s.c().GC_SUBST_PATH_DELIMITER, T_s.c().GC_PATH_SEPARATOR)
        l_location = l_location.replaceAll(T_s.c().GC_SUBST_USERNAME, T_s.c().GC_USERNAME)
        l_location = l_location.replaceAll(T_s.c().GC_SUBST_DATE, l_current_date.format(T_s.c().GC_LOG_FILENAME_DATE_FORMAT))
        l_location = l_location.replaceAll(T_s.c().GC_SUBST_TIME, l_current_date.format(T_s.c().GC_LOG_FILENAME_TIME_FORMAT))
        l_location = l_location.replaceAll(T_s.c().GC_SUBST_THREADID, T_s.c().GC_THREADID)
        return l_location
    }

    Boolean init() {
        String l_location = process_location(p_location)
        p_file = new File(l_location)
        p_file_writer = new FileWriter(p_file, T_s.c().GC_FILE_APPEND_YES)
        return T_s.c().GC_TRUE
    }

}