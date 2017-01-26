package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_destination
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

import java.lang.reflect.Array


abstract class T_destination implements I_destination {

    final static I_trace PC_STATIC_TRACE_NAME_CLASS_NAME = init_predefined_trace("class")
    final static I_trace PC_STATIC_TRACE_NAME_METHOD_NAME = init_predefined_trace("method")
    final static I_trace PC_STATIC_TRACE_NAME_DEPTH = init_predefined_trace("depth")
    final static I_trace PC_STATIC_TRACE_NAME_EVENT_TYPE = init_predefined_trace("event")
    final static I_trace PC_STATIC_TRACE_NAME_DATETIMESTAMP = init_predefined_trace("datetimestamp")
    final static I_trace PC_STATIC_TRACE_NAME_EXCEPTION = init_predefined_trace("exception")
    final static I_trace PC_STATIC_TRACE_NAME_MESSAGE = init_predefined_trace("message")
    final static I_trace PC_STATIC_TRACE_NAME_THREADID = init_predefined_trace("thread")
    final static I_trace PC_STATIC_TRACE_NAME_PROCESSID = init_predefined_trace("process")
    final static I_trace PC_TRACE_SOURCE_PREDEFINED = init_predefined_trace("predefined")
    final static I_trace PC_TRACE_SOURCE_RUNTIME = init_predefined_trace("runtime")
    final static I_trace PC_TRACE_SOURCE_CONTEXT = init_predefined_trace("context")
    final static ArrayList<I_trace> PC_ALL_POSSIBLE_PREDEFINED_TRACES = new ArrayList<I_trace>()
    final static ArrayList<I_trace> PC_ALL_POSSIBLE_SOURCES = new ArrayList<I_trace>()
    static Boolean p_is_init = init()

    I_event_formatter p_formatter = T_s.c().GC_NULL_OBJ_REF as I_event_formatter
    String p_purpose = T_s.c().GC_EMPTY_STRING
    HashMap<String, I_event> p_configuration_events_by_name = new HashMap<String, I_event>()
    String p_location = T_s.c().GC_EMPTY_STRING

    static I_trace init_predefined_trace(String i_predefined_trace_name) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace.set_name(i_predefined_trace_name)
        return l_trace
    }

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
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_PREDEFINED)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_RUNTIME)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_CONTEXT)
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

    static I_trace find_and_build_trace_inclusive(I_event i_event_runtime, I_trace i_trace_config) {
        String l_trace_config_source = i_trace_config.get_source()
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL) {
            for (I_trace l_trace_predefined in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                if (i_trace_config.match_trace(l_trace_predefined)) {
                    return build_predefined_trace(l_trace_predefined, i_event_runtime)
                }
            }
        }
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_RUNTIME) {
            for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
                if (i_trace_config.match_trace(l_trace_runtime)) {
                    return T_s.l().spawn_trace(l_trace_runtime, i_trace_config)
                }
            }
        }
        if (l_trace_config_source == T_s.c().GC_EMPTY_STRING || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_ALL || l_trace_config_source == T_s.c().GC_TRACE_SOURCE_CONTEXT) {
            for (I_trace l_trace_context in T_s.l().get_trace_context_list()) {
                if (i_trace_config.match_trace(l_trace_context)) {
                    return T_s.l().spawn_trace(l_trace_context, i_trace_config)
                }
            }
        }
        I_trace l_trace_not_found = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace_not_found.set_name(i_trace_config.get_name())
        l_trace_not_found.set_val(T_s.c().GC_DEFAULT_TRACE)
        return l_trace_not_found
    }

    static I_trace build_predefined_trace(I_trace i_predefined_trace, I_event i_event_runtime) {
        I_trace l_result_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_result_trace.set_name(i_predefined_trace.get_name())
        if (PC_STATIC_TRACE_NAME_CLASS_NAME.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_class_name())
        } else if (PC_STATIC_TRACE_NAME_METHOD_NAME.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_method_name())
        } else if (PC_STATIC_TRACE_NAME_DEPTH.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_depth().toString())
        } else if (PC_STATIC_TRACE_NAME_EVENT_TYPE.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_event_type())
        } else if (PC_STATIC_TRACE_NAME_DATETIMESTAMP.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_datetimestamp())
        } else if (PC_STATIC_TRACE_NAME_EXCEPTION.match_trace(i_predefined_trace)) {
            l_result_trace.set_ref(i_event_runtime.get_exception())
        } else if (PC_STATIC_TRACE_NAME_MESSAGE.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_message().toString())
        } else if (PC_STATIC_TRACE_NAME_THREADID.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(T_s.c().GC_THREADID)
        } else if (PC_STATIC_TRACE_NAME_PROCESSID.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(T_s.c().GC_PROCESSID)
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_STATIC_TRACE_NAME, i_predefined_trace.get_name())
        }
        return l_result_trace
    }

    static ArrayList<I_trace> build_predefined_trace_source_by_name(I_trace i_predefined_trace, I_event i_event_runtime, I_event i_event_config) {
        ArrayList<I_trace> l_result_traces = new ArrayList<I_trace>()
        if (PC_TRACE_SOURCE_PREDEFINED.match_trace(i_predefined_trace)) {
            for (I_trace l_trace_predefined in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                l_result_traces.add(build_predefined_trace(l_trace_predefined, i_event_runtime))
            }
        } else if (PC_TRACE_SOURCE_CONTEXT.match_trace(i_predefined_trace)) {
            for (I_trace l_trace_context in T_s.l().get_trace_context_list()) {
                l_result_traces.add(T_s.l().spawn_trace(l_trace_context, i_event_config.get_corresponding_trace(i_predefined_trace)))
            }
        } else if (PC_TRACE_SOURCE_RUNTIME.match_trace(i_predefined_trace)) {
            for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
                l_result_traces.add(T_s.l().spawn_trace(l_trace_runtime, i_event_config.get_corresponding_trace(i_predefined_trace)))
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_STATIC_TRACE_NAME, i_predefined_trace)
        }
        return l_result_traces
    }

    static ArrayList<I_trace> process_source_exclusive(I_trace i_predefined_source_trace, I_event i_event_config, ArrayList<I_trace> i_traces_from_source) {
        ArrayList<I_trace> l_traces_to_add = new ArrayList<I_trace>()
        if (!i_event_config.is_trace_muted(i_predefined_source_trace)) {
            for (I_trace l_trace_context in i_traces_from_source) {
                if (!i_event_config.is_trace_muted(l_trace_context)) {
                    I_trace l_trace_config = T_s.nvl(i_event_config.get_corresponding_trace(l_trace_context), i_event_config.get_corresponding_trace(i_predefined_source_trace)) as I_trace
                    l_traces_to_add.add(T_s.l().spawn_trace(l_trace_context, l_trace_config))
                }
            }
        }
        return l_traces_to_add
    }

    @Override
    ArrayList<I_trace> prepare_trace_list(I_event i_event_runtime) {
        I_event l_event_config = p_configuration_events_by_name.get(i_event_runtime.get_event_type())
        ArrayList<I_trace> l_trace_list = new ArrayList<I_trace>()
        if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_DISPLAY) { //including only those defined
            for (I_trace l_trace_config in l_event_config.get_traces_config()) {
                if (!l_trace_config.is_muted()) {
                    l_trace_list.add(find_and_build_trace_inclusive(i_event_runtime, l_trace_config))
                }
            }
        } else if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_WAREHOUSE) { //excluding defined
            if (!l_event_config.is_trace_muted(PC_TRACE_SOURCE_PREDEFINED)) {
                for (I_trace l_trace_predefined in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                    if (!l_event_config.is_trace_muted(l_trace_predefined)) {
                        l_trace_list.add(build_predefined_trace(l_trace_predefined, i_event_runtime))
                    }
                }
            }
            l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_RUNTIME, l_event_config, i_event_runtime.get_traces_runtime()))
            l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_CONTEXT, l_event_config, T_s.l().get_trace_context_list()))
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
