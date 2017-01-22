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
    final static ArrayList<String> PC_ALL_POSSIBLE_PREDEFINED_TRACES = new ArrayList<String>()
    static Boolean p_is_init = init ()

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

    static Boolean is_name_muted(String i_name, I_event i_event_config) {
        Boolean l_result = T_s.c().GC_FALSE
        for (I_trace l_trace_config in i_event_config.get_traces_config()) {
            if (l_trace_config.get_name() == i_name) {
                l_result = l_trace_config.is_muted()
                break
            }
        }
        return l_result
    }

    static Boolean is_object_muted(Object i_object, I_event i_event_config) {
        Boolean l_result = T_s.c().GC_FALSE
        if (i_object instanceof I_trace) {
            l_result = ((I_trace)i_object).is_muted()
        }
        for (I_trace l_trace_config in i_event_config.get_traces_config()) {
            if (l_trace_config.get_name() == i_object.getClass().getSimpleName() || l_trace_config.get_name() == i_object.getClass().getCanonicalName()) {
                l_result = l_trace_config.is_muted()
                break
            }
        }
        return l_result
    }

    static Boolean match_names(String i_target_name, Object i_object) {
        Boolean l_result = T_s.c().GC_FALSE
        if (i_object instanceof I_trace) {
            if (i_target_name == ((I_trace)i_object).get_name()) {
                l_result = T_s.c().GC_TRUE
            }
        }
        if (i_object.getClass().getSimpleName() == i_target_name || i_object.getClass().getCanonicalName() == i_target_name) {
            l_result = T_s.c().GC_TRUE
        }
        return l_result
    }

    static I_trace find_and_build_trace(I_event i_event_runtime, String i_trace_config_name) {
        I_trace l_result_trace = T_s.c().GC_NULL_OBJ_REF as I_trace
        for (Object l_object in T_s.l().get_trace_context_list()) {
            if (l_object instanceof I_trace) {
                if (match_names(i_trace_config_name, l_object)) {
                    l_result_trace = T_s.l().trace2trace((I_trace)l_object)
                    break
                }
            } else {
                if (match_names(i_trace_config_name, l_object)) {
                    l_result_trace = T_s.l().object2trace((I_trace)l_object)
                    break
                }
            }
        }
        for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
            if (i_trace_config_name == l_trace_runtime.get_name()) {
                l_result_trace = T_s.l().trace2trace(l_trace_runtime)
            }
        }
        for (String l_trace_predefined_name in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
            if (i_trace_config_name == l_trace_predefined_name) {
                l_result_trace = build_predefined_trace_by_name(i_trace_config_name, i_event_runtime)
                break
            }
        }
        return l_result_trace
    }

    static I_trace build_predefined_trace_by_name(String i_trace_name, I_event i_event_runtime) {
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
                l_trace_list.add(find_and_build_trace(i_event_runtime, l_trace_config.get_name()))
            }
        } else if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_WAREHOUSE) { //exclusive
            for (String l_trace_predefined_name in PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                if (!is_name_muted(l_trace_predefined_name, l_event_config)) {
                    l_trace_list.add(build_predefined_trace_by_name(l_trace_predefined_name, i_event_runtime))
                }
            }
            for (I_trace l_trace_runtime in i_event_runtime.get_traces_runtime()) {
                if (!is_object_muted(l_trace_runtime, l_event_config)) {
                    l_trace_list.add(l_trace_runtime)
                }
            }
            for (Object l_object in T_s.l().get_trace_context_list()) {
                if (l_object instanceof I_trace) {
                    if (!is_object_muted(l_object, l_event_config)) {
                        l_trace_list.add(l_object)
                    }
                } else {
                    if (!is_object_muted(l_object, l_event_config)) {
                        l_trace_list.add(T_s.l().object2trace(l_object))
                    }
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
