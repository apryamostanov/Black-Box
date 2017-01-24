package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_destination
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

abstract class T_destination implements I_destination {

    final static String PC_STATIC_TRACE_NAME_CLASS_NAME = "class"
    final static String PC_STATIC_TRACE_NAME_METHOD_NAME = "method"
    final static String PC_STATIC_TRACE_NAME_DEPTH = "depth"
    final static String PC_STATIC_TRACE_NAME_EVENT_TYPE = "event"
    final static String PC_STATIC_TRACE_NAME_DATETIMESTAMP = "datetimestamp"
    final static String PC_STATIC_TRACE_NAME_EXCEPTION = "exception"
    final static String PC_STATIC_TRACE_NAME_MESSAGE = "message"
    final static String PC_STATIC_TRACE_NAME_THREADID = "thread"
    final static String PC_STATIC_TRACE_NAME_PROCESSID = "process"
    final static ArrayList<String> PC_ALL_POSSIBLE_PREDEFINED_TRACES = new ArrayList<String>()
    static Boolean p_is_init = init()

    I_event_formatter p_formatter = T_s.c().GC_NULL_OBJ_REF as I_event_formatter
    String p_purpose = T_s.c().GC_EMPTY_STRING
    HashMap<String, I_event> p_configuration_events_by_name = new HashMap<String, I_event>()
    String p_location = T_s.c().GC_EMPTY_STRING

    static Boolean init() {
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_CLASS_NAME)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_METHOD_NAME)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_DEPTH)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_EVENT_TYPE)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_DATETIMESTAMP)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_EXCEPTION)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_MESSAGE)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_THREADID)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_PROCESSID)
        return T_s.c().GC_TRUE
    }

    @Override
    void add_configuration_event(I_event i_event) {
        p_configuration_events_by_name.put(i_event.get_event_type(), i_event)
    }

    @Override
    void set_formatter(I_event_formatter i_formatter) {
        p_formatter = i_formatter
    }

    @Override
    void log_generic(I_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type())) {
            ArrayList<I_trace> l_trace_list = prepare_trace_list(i_event)
            store(l_trace_list)
        }
    }

    static Boolean is_trace_muted_predefined(String i_name, I_event i_event_config) {
        Boolean l_result = T_s.c().GC_FALSE
        for (I_trace l_trace_config in i_event_config.get_traces_config()) {
            if (l_trace_config.get_name() == i_name) {
                l_result = l_trace_config.is_muted()
                break
            }
        }
        return l_result
    }

    static Boolean is_trace_muted_context_and_runtime(I_trace i_trace, I_event i_event_config) {
        Boolean l_result = T_s.c().GC_FALSE
        if (i_trace.get_ref() != T_s.c().GC_NULL_OBJ_REF) {
            for (I_trace l_trace_config in i_event_config.get_traces_config()) {
                if (l_trace_config.get_name() == i_trace.get_ref().getClass().getSimpleName() || l_trace_config.get_name() == i_trace.get_ref().getClass().getCanonicalName()) {
                    l_result = l_trace_config.is_muted()
                    break
                }
            }
        }
        return l_result
    }

    static I_trace find_and_build_trace_inclusive(I_event i_event_runtime, I_trace i_trace_config) {
        String l_trace_config_name = i_trace_config.get_name()
        String l_trace_config_source = i_trace_config.get_source()
        I_trace l_result_trace = T_s.c().GC_NULL_OBJ_REF as I_trace
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL) {
            for (String l_trace_predefined_name in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                if (l_trace_config_name == l_trace_predefined_name) {
                    l_result_trace = build_predefined_trace_by_name_exclusive(l_trace_config_name, i_event_runtime)
                    return l_result_trace
                }
            }
        }
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_RUNTIME) {
            for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
                if (l_trace_config_name == l_trace_runtime.get_name()) {
                    l_result_trace = T_s.l().spawn_trace(l_trace_runtime, i_trace_config)
                    return l_result_trace
                }
                if (l_trace_runtime.get_ref() != T_s.c().GC_NULL_OBJ_REF) {
                    if (l_trace_config_name == l_trace_runtime.get_ref().getClass().getSimpleName() || l_trace_config_name == l_trace_runtime.get_ref().getClass().getCanonicalName()) {
                        l_result_trace = T_s.l().spawn_trace(l_trace_runtime, i_trace_config)
                        return l_result_trace
                    }
                }
            }
        }
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_CONTEXT) {
            for (I_trace l_trace_context in T_s.l().get_trace_context_list()) {
                if (l_trace_config_name == l_trace_context.get_name()) {
                    l_result_trace = T_s.l().spawn_trace(l_trace_context, i_trace_config)
                    return l_result_trace
                }
            }
        }
        I_trace l_trace_not_found = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace_not_found.set_name(l_trace_config_name)
        l_trace_not_found.set_val(T_s.c().GC_DEFAULT_TRACE)
        return l_trace_not_found
    }

    static I_trace build_predefined_trace_by_name_exclusive(String i_trace_name, I_event i_event_runtime) {
        I_trace l_result_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_result_trace.set_name(i_trace_name)
        if (i_trace_name == PC_STATIC_TRACE_NAME_CLASS_NAME) {
            l_result_trace.set_val(i_event_runtime.get_class_name())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_METHOD_NAME) {
            l_result_trace.set_val(i_event_runtime.get_method_name())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_DEPTH) {
            l_result_trace.set_val(i_event_runtime.get_depth().toString())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_EVENT_TYPE) {
            l_result_trace.set_val(i_event_runtime.get_event_type())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_DATETIMESTAMP) {
            l_result_trace.set_val(i_event_runtime.get_datetimestamp())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_EXCEPTION) {
            l_result_trace.set_ref(i_event_runtime.get_exception())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_MESSAGE) {
            l_result_trace.set_val(i_event_runtime.get_message().toString())
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_THREADID) {
            l_result_trace.set_val(T_s.c().GC_THREADID)
        } else if (i_trace_name == PC_STATIC_TRACE_NAME_PROCESSID) {
            l_result_trace.set_val(T_s.c().GC_PROCESSID)
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_STATIC_TRACE_NAME, i_trace_name)
        }
        return l_result_trace
    }

    @Override
    ArrayList<I_trace> prepare_trace_list(I_event i_event_runtime) {
        I_event l_event_config = p_configuration_events_by_name.get(i_event_runtime.get_event_type())
        ArrayList<I_trace> l_trace_list = new ArrayList<I_trace>()
        if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_DISPLAY) { //inclusive
            for (I_trace l_trace_config in l_event_config.get_traces_config()) {
                if (!l_trace_config.is_muted()) {
                    l_trace_list.add(find_and_build_trace_inclusive(i_event_runtime, l_trace_config))
                }
            }
        } else if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_WAREHOUSE) { //exclusive
            for (String l_trace_predefined_name in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                if (!is_trace_muted_predefined(l_trace_predefined_name, l_event_config)) {
                    l_trace_list.add(build_predefined_trace_by_name_exclusive(l_trace_predefined_name, i_event_runtime))
                }
            }
            for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
                if (!is_trace_muted_context_and_runtime(l_trace_runtime, l_event_config)) {
                    l_trace_list.add(l_trace_runtime)
                }
            }
            for (I_trace l_trace_context in T_s.l().get_trace_context_list()) {
                if (!is_trace_muted_context_and_runtime(l_trace_context, l_event_config)) {
                    l_trace_list.add(l_trace_context)
                }
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_DESTINATION_PURPOSE, p_purpose)
        }
        return l_trace_list
    }

    @Override
    void set_destination_purpose(String i_destination_purpose) {
        p_purpose = i_destination_purpose
    }

    @Override
    void set_location(String i_location) {
        p_location = i_location
    }

    @Override
    String get_destination_purpose() {
        return p_purpose
    }
}
