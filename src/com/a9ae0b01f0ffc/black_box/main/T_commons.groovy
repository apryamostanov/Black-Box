package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.configuration_utility.implementation.T_conf
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.static_string.T_static_string_builder

import java.lang.management.ManagementFactory

class T_commons {
    static final Object GC_NULL_OBJ_REF = null
    static final Collection<I_trace> GC_SKIPPED_ARG = new ArrayList<I_trace>()
    static final String GC_EMPTY_STRING = ""
    static final String GC_AT_CHAR = "@"
    static final String GC_COMMA = ","
    static final String GC_COLON = ":"
    static final Integer GC_EMPTY_SIZE = 0
    static final Integer GC_FIRST_INDEX = 0
    static final Integer GC_FIRST_CHAR = 0
    static final String GC_NEW_LINE = System.lineSeparator()
    static final String GC_PATH_SEPARATOR = File.separator
    static final String GC_POINT = "."
    static final Integer GC_ONE_CHAR = 1
    static final Boolean GC_TRUE = true
    static final Boolean GC_FALSE = false
    static final Integer GC_ZERO = 0
    static final String GC_DATE_FORMAT_UID = "yyyyMMddHHmmssSSS"
    static final Boolean GC_FILE_APPEND_YES = GC_TRUE
    static final String GC_SUBST_USERNAME = "%USERNAME%"
    static final String GC_SUBST_DATE = "%DATE%"
    static final String GC_SUBST_TIME = "%TIME%"
    static final String GC_SUBST_THREADID = "%THREADID%"
    static final String GC_SUBST_PROCESSID = "%PROCESSID%"
    static final String GC_USERNAME = System.getProperty("user.name")
    static final String GC_THREADID = Long.toString(Thread.currentThread().getId())
    static final String GC_PROCESSID = ManagementFactory.getRuntimeMXBean().getName().substring(GC_FIRST_CHAR, ManagementFactory.getRuntimeMXBean().getName().indexOf(GC_AT_CHAR))//When Java 9 comes: ProcessHandle.current().getPid()
    static final T_static_string_builder GC_STATIC_STRING_BUILDER = new T_static_string_builder()
    static final String GC_TRACE_SOURCE_ALL = "all"
    static final String GC_TRACE_SOURCE_PREDEFINED = "predefined"
    static final String GC_TRACE_SOURCE_RUNTIME = "runtime"
    static final String GC_TRACE_SOURCE_CONTEXT = "context"
    static final String GC_TRACE_SOURCE_EXCEPTION_TRACES = "exception_traces"
    static final String GC_TRACE_MASK_ALL = "all"
    static final String GC_TRACE_MASK_SENSITIVE = "sensitive"
    static final String GC_TRACE_MASK_ALL_EXCEPT_NON_SENSITIVE = "except_non_sensitive"
    static final String GC_TRACE_MASK_NONE = "none"
    static final String GC_FALSE_STRING = "false"
    static final String GC_TRUE_STRING = "true"
    static T_conf GC_CONST_CONF = GC_NULL_OBJ_REF as T_conf
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
    static String GC_DEFAULT_LOGGER_CONF_FILE_NAME = GC_EMPTY_STRING
    static String GC_CLASS_LOADER_CONF_FILE_NAME = GC_EMPTY_STRING
    static String GC_LOGGER_MODE_PRODUCTION = "production"
    static String GC_LOGGER_MODE_DIAGNOSTIC = "diagnostic"
    static String GC_DESTINATION_PURPOSE_DISPLAY = "display"
    static String GC_DESTINATION_PURPOSE_WAREHOUSE = "warehouse"
    static String GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE = "_"
    static String GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER = " "
    static String GC_MESSAGE_FORMAT_TOKEN_TRACE = "Z"

    static void init_custom(String i_conf_file_name) {
        GC_CONST_CONF = new T_conf(i_conf_file_name)
        GC_LOG_DATETIMESTAMP_FORMAT = GC_CONST_CONF.GC_LOG_DATETIMESTAMP_FORMAT(GC_LOG_DATETIMESTAMP_FORMAT)
        GC_LOG_FILENAME_DATE_FORMAT = GC_CONST_CONF.GC_LOG_FILENAME_DATE_FORMAT(GC_LOG_FILENAME_DATE_FORMAT)
        GC_LOG_FILENAME_TIME_FORMAT = GC_CONST_CONF.GC_LOG_FILENAME_TIME_FORMAT(GC_LOG_FILENAME_TIME_FORMAT)
        GC_LOG_CSV_SEPARATOR = GC_CONST_CONF.GC_LOG_CSV_SEPARATOR(GC_LOG_CSV_SEPARATOR)
        GC_ARGUMENT_POSITIONAL_NAME = GC_CONST_CONF.GC_ARGUMENT_POSITIONAL_NAME(GC_ARGUMENT_POSITIONAL_NAME)
        GC_DEFAULT_CLASS_NAME = GC_CONST_CONF.GC_DEFAULT_CLASS_NAME(GC_DEFAULT_CLASS_NAME)
        GC_DEFAULT_METHOD_NAME = GC_CONST_CONF.GC_DEFAULT_METHOD_NAME(GC_DEFAULT_METHOD_NAME)
        GC_DEFAULT_GUID = GC_CONST_CONF.GC_DEFAULT_GUID(GC_DEFAULT_GUID)
        GC_DEFAULT_TRACE = GC_CONST_CONF.GC_DEFAULT_TRACE(GC_DEFAULT_TRACE)
        GC_DEFAULT_TRACE_MASKED = GC_CONST_CONF.GC_DEFAULT_TRACE_MASKED(GC_DEFAULT_TRACE_MASKED)
        GC_DEFAULT_TRACE_MUTED = GC_CONST_CONF.GC_DEFAULT_TRACE_MUTED(GC_DEFAULT_TRACE_MUTED)
        GC_DEDUPLICATE_TRACES = GC_CONST_CONF.GC_DEDUPLICATE_TRACES(GC_DEDUPLICATE_TRACES)
        GC_DEFAULT_LOGGER_CONF_FILE_NAME = GC_CONST_CONF.GC_DEFAULT_LOGGER_CONF_FILE_NAME(GC_DEFAULT_LOGGER_CONF_FILE_NAME)
        GC_CLASS_LOADER_CONF_FILE_NAME = GC_CONST_CONF.GC_CLASS_LOADER_CONF_FILE_NAME(GC_CLASS_LOADER_CONF_FILE_NAME)
        GC_LOGGER_MODE_PRODUCTION = GC_CONST_CONF.GC_LOGGER_MODE_PRODUCTION(GC_LOGGER_MODE_PRODUCTION)
        GC_LOGGER_MODE_DIAGNOSTIC = GC_CONST_CONF.GC_LOGGER_MODE_DIAGNOSTIC(GC_LOGGER_MODE_DIAGNOSTIC)
        GC_DESTINATION_PURPOSE_DISPLAY = GC_CONST_CONF.GC_DESTINATION_PURPOSE_DISPLAY(GC_DESTINATION_PURPOSE_DISPLAY)
        GC_DESTINATION_PURPOSE_WAREHOUSE = GC_CONST_CONF.GC_DESTINATION_PURPOSE_WAREHOUSE(GC_DESTINATION_PURPOSE_WAREHOUSE)
        GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE = GC_CONST_CONF.GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE(GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE)
        GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER = GC_CONST_CONF.GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER(GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER)
        GC_MESSAGE_FORMAT_TOKEN_TRACE = GC_CONST_CONF.GC_MESSAGE_FORMAT_TOKEN_TRACE(GC_MESSAGE_FORMAT_TOKEN_TRACE)
    }
}
