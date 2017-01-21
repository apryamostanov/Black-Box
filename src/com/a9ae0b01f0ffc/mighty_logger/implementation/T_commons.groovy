package com.a9ae0b01f0ffc.mighty_logger.implementation

import com.a9ae0b01f0ffc.mighty_conf.implementation.T_conf
import groovy.util.slurpersupport.GPathResult

public class T_commons {
    static final String GC_CONST_CONF_FILE_NAME = "src/com/a9ae0b01f0ffc/mighty_logger/conf/commons.conf"
    static final Object GC_NULL_OBJ_REF = null
    static final Object GC_SKIPPED_ARG = new Object()
    static final String GC_EMPTY_STRING = ""
    static final String GC_COMMA = ","
    static final String GC_COLON = ":"
    static final Integer GC_EMPTY_SIZE = 0
    static final Integer GC_FIRST_INDEX = 0
    static final String GC_NEW_LINE = System.lineSeparator()
    static final String GC_PATH_SEPARATOR = File.separator;
    static final String GC_POINT = "."
    static final Integer GC_ONE_CHAR = 1
    static final Boolean GC_TRUE = true
    static final Boolean GC_FALSE = false
    static final Integer GC_ZERO = 0
    static final String GC_DATE_FORMAT_UID = "yyyyMMddHHmmssSSS"
    static final Boolean GC_IS_INIT = init_default()
    static final Boolean GC_FILE_APPEND_YES = GC_TRUE
    static final String GC_SUBST_PATH_DELIMITER = "/"
    static final String GC_SUBST_USERNAME = "%USERNAME%"
    static final String GC_SUBST_DATE = "%DATE%"
    static final String GC_SUBST_TIME = "%TIME%"
    static final String GC_SUBST_THREADID = "%THREADID%"
    static final String GC_USERNAME = System.getProperty("user.name")
    static final String GC_THREADID = Long.toString(Thread.currentThread().getId())
    static T_conf GC_CONST_CONF = new T_conf(GC_CONST_CONF_FILE_NAME)
    static String GC_LOG_DATETIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS"
    static String GC_LOG_FILENAME_DATE_FORMAT = "yyyyMMdd"
    static String GC_LOG_FILENAME_TIME_FORMAT = "HHmmssSSS"
    static String GC_LOG_CSV_SEPARATOR = "|"
    static String GC_ARGUMENT_POSITIONAL_NAME = "Argument#"
    static String GC_DEFAULT_CLASS_NAME = "Unknown Class"
    static String GC_DEFAULT_METHOD_NAME = "Unknown Method"
    static String GC_DEFAULT_GUID = "Unknown GUID"
    static String GC_DEFAULT_TRACE = "Trace missing"
    static String GC_DEFAULT_TRACE_MASKED = "Trace masked"
    static String GC_DEFAULT_TRACE_MUTED = "Trace muted"
    static Boolean GC_DEDUPLICATE_TRACES = GC_FALSE
    static String GC_DEFAULT_LOGGER_CONF_FILE_NAME = "src/com/a9ae0b01f0ffc/mighty_logger/conf/main.conf"
    static String GC_CLASS_LOADER_CONF_FILE_NAME = "src/com/a9ae0b01f0ffc/mighty_logger/conf/classes.conf"


    static Boolean init_default() {
        init_custom(GC_CONST_CONF_FILE_NAME)
        return GC_TRUE
    }

    static void init_custom(String i_conf_file_name) {
        GC_CONST_CONF = new T_conf(i_conf_file_name)
        GC_CONST_CONF.GC_LOG_DATETIMESTAMP_FORMAT(GC_LOG_DATETIMESTAMP_FORMAT)
        GC_CONST_CONF.GC_LOG_FILENAME_DATE_FORMAT(GC_LOG_FILENAME_DATE_FORMAT)
        GC_CONST_CONF.GC_LOG_FILENAME_TIME_FORMAT(GC_LOG_FILENAME_TIME_FORMAT)
        GC_CONST_CONF.GC_LOG_CSV_SEPARATOR(GC_LOG_CSV_SEPARATOR)
        GC_CONST_CONF.GC_ARGUMENT_POSITIONAL_NAME(GC_ARGUMENT_POSITIONAL_NAME)
        GC_CONST_CONF.GC_DEFAULT_CLASS_NAME(GC_DEFAULT_CLASS_NAME)
        GC_CONST_CONF.GC_DEFAULT_METHOD_NAME(GC_DEFAULT_METHOD_NAME)
        GC_CONST_CONF.GC_DEFAULT_GUID(GC_DEFAULT_GUID)
        GC_CONST_CONF.GC_DEFAULT_TRACE(GC_DEFAULT_TRACE)
        GC_CONST_CONF.GC_DEFAULT_TRACE_MASKED(GC_DEFAULT_TRACE_MASKED)
        GC_CONST_CONF.GC_DEFAULT_TRACE_MUTED(GC_DEFAULT_TRACE_MUTED)
        GC_CONST_CONF.GC_DEDUPLICATE_TRACES(GC_DEDUPLICATE_TRACES)
        GC_CONST_CONF.GC_DEFAULT_LOGGER_CONF_FILE_NAME(GC_DEFAULT_LOGGER_CONF_FILE_NAME)
        GC_CONST_CONF.GC_CLASS_LOADER_CONF_FILE_NAME(GC_CLASS_LOADER_CONF_FILE_NAME)
    }
}
