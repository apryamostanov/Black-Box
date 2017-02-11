package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.implementation.T_inherited_configurations
import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception

abstract class T_destination extends T_inherited_configurations implements I_destination {

    final static I_trace PC_STATIC_TRACE_NAME_CLASS_NAME = init_predefined_trace("class")
    final static I_trace PC_STATIC_TRACE_NAME_METHOD_NAME = init_predefined_trace("method")
    final static I_trace PC_STATIC_TRACE_NAME_DEPTH = init_predefined_trace("depth")
    final static I_trace PC_STATIC_TRACE_NAME_EVENT_TYPE = init_predefined_trace("event")
    final static I_trace PC_STATIC_TRACE_NAME_DATETIMESTAMP = init_predefined_trace("datetimestamp")
    final static I_trace PC_STATIC_TRACE_NAME_EXCEPTION = init_predefined_trace("exception")
    final static I_trace PC_STATIC_TRACE_NAME_EXCEPTION_MESSAGE = init_predefined_trace("exception_message")
    final static I_trace PC_STATIC_TRACE_NAME_MESSAGE = init_predefined_trace("message")
    final static I_trace PC_STATIC_TRACE_NAME_THREADID = init_predefined_trace("thread")
    final static I_trace PC_STATIC_TRACE_NAME_PROCESSID = init_predefined_trace("process")
    final static I_trace PC_STATIC_TRACE_NAME_METHOD_INVOCATION = init_predefined_trace("invocation")
    final static I_trace PC_STATIC_TRACE_NAME_METHOD_STACK = init_predefined_trace("stack")
    final static I_trace PC_TRACE_SOURCE_PREDEFINED = init_predefined_trace("predefined")
    final static I_trace PC_TRACE_SOURCE_RUNTIME = init_predefined_trace("runtime")
    final static I_trace PC_TRACE_SOURCE_CONTEXT = init_predefined_trace("context")
    final static I_trace PC_TRACE_SOURCE_EXCEPTION_TRACES = init_predefined_trace("exception_traces")
    final static ArrayList<I_trace> PC_ALL_POSSIBLE_PREDEFINED_TRACES = new ArrayList<I_trace>()
    final static ArrayList<I_trace> PC_ALL_POSSIBLE_SOURCES = new ArrayList<I_trace>()
    static Boolean p_is_init = init()

    I_event_formatter p_formatter = T_logging_const.GC_NULL_OBJ_REF as I_event_formatter
    String p_purpose = T_logging_const.GC_EMPTY_STRING
    HashMap<String, I_event> p_configuration_events_by_name = new HashMap<String, I_event>()
    String p_location = T_logging_const.GC_EMPTY_STRING
    String p_buffer = T_logging_const.GC_EMPTY_STRING
    Integer p_buffer_size
    String p_spool_event = T_logging_const.GC_EMPTY_STRING
    String p_mask = T_logging_const.GC_EMPTY_STRING

    @I_black_box("error")
    String get_buffer() {
        return p_buffer
    }

    @I_black_box("error")
    void set_buffer(String i_buffer) {
        this.p_buffer = i_buffer
        p_buffer_size = Integer.parseInt(p_buffer)
    }

    @I_black_box("error")
    String get_spool_event() {
        return p_spool_event
    }

    @I_black_box("error")
    void set_spool_event(String i_spool_event) {
        this.p_spool_event = i_spool_event
    }

    @I_black_box("error")
    static I_trace init_predefined_trace(String i_predefined_trace_name) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace.set_source(T_logging_const.GC_TRACE_SOURCE_PREDEFINED)
        l_trace.set_name(i_predefined_trace_name)
        return l_trace
    }

    @I_black_box("error")
    static Boolean init() {
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_CLASS_NAME)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_METHOD_NAME)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_DEPTH)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_EVENT_TYPE)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_DATETIMESTAMP)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_EXCEPTION)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_EXCEPTION_MESSAGE)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_MESSAGE)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_THREADID)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_PROCESSID)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_METHOD_INVOCATION)
        PC_ALL_POSSIBLE_PREDEFINED_TRACES.add(PC_STATIC_TRACE_NAME_METHOD_STACK)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_PREDEFINED)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_RUNTIME)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_CONTEXT)
        PC_ALL_POSSIBLE_SOURCES.add(PC_TRACE_SOURCE_EXCEPTION_TRACES)
        return T_logging_const.GC_TRUE
    }

    @Override
    @I_black_box("error")
    void add_configuration_event(I_event i_event) {
        p_configuration_events_by_name.put(i_event.get_event_type(), i_event)
    }

    @Override
    @I_black_box("error")
    void set_formatter(I_event_formatter i_formatter) {
        p_formatter = i_formatter
    }

    @Override
    @I_black_box("error")
    void log_generic(I_event i_event) {
        if (p_configuration_events_by_name.containsKey(i_event.get_event_type())) {
            ArrayList<I_trace> l_trace_list = prepare_trace_list(i_event)
            store(l_trace_list, i_event)
        }
    }

    @I_black_box("error")
    static I_trace find_and_build_trace_inclusive(I_event i_event_runtime, I_trace i_trace_config) {
        String l_trace_config_source = i_trace_config.get_source()
        if (l_trace_config_source == T_logging_const.GC_EMPTY_STRING || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_ALL) {
            for (I_trace l_trace_predefined : PC_ALL_POSSIBLE_PREDEFINED_TRACES) {
                if (i_trace_config.match_trace(l_trace_predefined)) {
                    return build_predefined_trace(l_trace_predefined, i_event_runtime, i_trace_config)
                }
            }
        }
        if (l_trace_config_source == T_logging_const.GC_EMPTY_STRING || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_ALL || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_RUNTIME) {
            for (I_trace l_trace_runtime : i_event_runtime.get_traces_runtime()) {
                if (i_trace_config.match_trace(l_trace_runtime)) {
                    return T_s.l().spawn_trace(l_trace_runtime, i_trace_config)
                }
            }
        }
        if (l_trace_config_source == T_logging_const.GC_EMPTY_STRING || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_ALL || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_CONTEXT) {
            for (I_trace l_trace_context : T_s.l().get_trace_context_list()) {
                if (i_trace_config.match_trace(l_trace_context)) {
                    return T_s.l().spawn_trace(l_trace_context, i_trace_config)
                }
            }
        }
        if (l_trace_config_source == T_logging_const.GC_EMPTY_STRING || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_ALL || l_trace_config_source == T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES) {
            if (i_event_runtime.get_throwable() != T_logging_const.GC_NULL_OBJ_REF) {
                if (i_event_runtime.get_throwable() instanceof E_application_exception) {
                    for (I_trace l_trace_from_exception : T_s.l().objects2traces(((E_application_exception) i_event_runtime.get_throwable()).get_traces(), T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES)) {
                        if (i_trace_config.match_trace(l_trace_from_exception)) {
                            return T_s.l().spawn_trace(l_trace_from_exception, i_trace_config)
                        }
                    }
                }
            }
        }
        I_trace l_trace_not_found = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace_not_found.set_name(i_trace_config.get_name())
        l_trace_not_found.set_val(T_s.c().GC_DEFAULT_TRACE)
        return l_trace_not_found
    }

    @I_black_box("error")
    static I_trace build_predefined_trace(I_trace i_predefined_trace, I_event i_event_runtime, I_trace i_trace_config) {
        I_trace l_result_trace = T_s.l().spawn_trace(i_predefined_trace, i_trace_config)
        l_result_trace.set_val(T_logging_const.GC_EMPTY_STRING)
        l_result_trace.set_source(T_logging_const.GC_TRACE_SOURCE_PREDEFINED)
        l_result_trace.set_name(i_predefined_trace.get_name())
        l_result_trace.set_mask(T_logging_const.GC_EMPTY_STRING)
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
            l_result_trace.set_ref(i_event_runtime.get_throwable())
        } else if (PC_STATIC_TRACE_NAME_EXCEPTION_MESSAGE.match_trace(i_predefined_trace)) {
            if (i_event_runtime.get_throwable() != T_logging_const.GC_NULL_OBJ_REF) {
                l_result_trace.set_val(i_event_runtime.get_throwable().getMessage())
            }
        } else if (PC_STATIC_TRACE_NAME_MESSAGE.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(i_event_runtime.get_message().toString())
        } else if (PC_STATIC_TRACE_NAME_THREADID.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(T_logging_const.GC_THREADID)
        } else if (PC_STATIC_TRACE_NAME_PROCESSID.match_trace(i_predefined_trace)) {
            l_result_trace.set_val(T_logging_const.GC_PROCESSID)
        } else if (PC_STATIC_TRACE_NAME_METHOD_INVOCATION.match_trace(i_predefined_trace)) {
            l_result_trace.set_ref(T_s.l().get_current_method_invocation())
        } else if (PC_STATIC_TRACE_NAME_METHOD_STACK.match_trace(i_predefined_trace)) {
            l_result_trace.set_ref(T_s.l().get_invocation_stack())
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_STATIC_TRACE_NAME_Z1, i_predefined_trace.get_name())
        }
        return l_result_trace
    }

    @I_black_box("error")
    static ArrayList<I_trace> build_predefined_traces(ArrayList<I_trace> i_predefined_traces, I_event i_event_runtime, I_trace i_trace_config) {
        ArrayList<I_trace> l_traces_to_add = new ArrayList<I_trace>()
        for (I_trace l_predefined_trace : i_predefined_traces) {
            l_traces_to_add.add(build_predefined_trace(l_predefined_trace, i_event_runtime, i_trace_config))
        }
        return l_traces_to_add
    }

    static ArrayList<I_trace> process_source_exclusive(I_trace i_predefined_source_trace, I_event i_event_config, List<I_trace> i_traces_from_source) {
        ArrayList<I_trace> l_traces_to_add = new ArrayList<I_trace>()
        for (I_trace l_trace_from_source : i_traces_from_source) {
            if ((!i_event_config.is_trace_muted(i_predefined_source_trace)) || i_event_config.get_corresponding_trace(l_trace_from_source) != T_logging_const.GC_NULL_OBJ_REF) {
                if (!i_event_config.is_trace_muted(l_trace_from_source)) {
                    I_trace l_trace_config = T_u.nvl(i_event_config.get_corresponding_trace(l_trace_from_source), i_event_config.get_corresponding_trace(i_predefined_source_trace)) as I_trace
                    l_traces_to_add.add(T_s.l().spawn_trace(l_trace_from_source, l_trace_config))
                }
            }
        }
        return l_traces_to_add
    }

    @Override
    @I_black_box("error")
    ArrayList<I_trace> prepare_trace_list(I_event i_event_runtime) {
        I_event l_event_config = p_configuration_events_by_name.get(i_event_runtime.get_event_type())
        ArrayList<I_trace> l_trace_list = new ArrayList<I_trace>()
        if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_DISPLAY) { //including only those defined
            for (I_trace l_trace_config : l_event_config.get_traces_config()) {
                if (!l_trace_config.is_muted()) {
                    if (l_trace_config.match_trace(PC_TRACE_SOURCE_PREDEFINED)) {
                        l_trace_list.addAll(build_predefined_traces(process_source_exclusive(PC_TRACE_SOURCE_PREDEFINED, l_event_config, PC_ALL_POSSIBLE_PREDEFINED_TRACES), i_event_runtime, l_trace_config))
                    } else if (l_trace_config.match_trace(PC_TRACE_SOURCE_RUNTIME)) {
                        l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_RUNTIME, l_event_config, i_event_runtime.get_traces_runtime()))
                    } else if (l_trace_config.match_trace(PC_TRACE_SOURCE_CONTEXT)) {
                        l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_CONTEXT, l_event_config, T_s.l().get_trace_context_list()))
                    } else if (l_trace_config.match_trace(PC_TRACE_SOURCE_EXCEPTION_TRACES)) {
                        if (i_event_runtime.get_throwable() != T_logging_const.GC_NULL_OBJ_REF && i_event_runtime.get_throwable() instanceof E_application_exception) {
                            l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_EXCEPTION_TRACES, l_event_config, T_s.l().objects2traces(((E_application_exception) i_event_runtime.get_throwable()).get_traces(), T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES)))
                        }
                    } else {
                        l_trace_list.add(find_and_build_trace_inclusive(i_event_runtime, l_trace_config))
                    }
                }
            }
        } else if (p_purpose == T_s.c().GC_DESTINATION_PURPOSE_WAREHOUSE) { //excluding defined
            l_trace_list.addAll(build_predefined_traces(process_source_exclusive(PC_TRACE_SOURCE_PREDEFINED, l_event_config, PC_ALL_POSSIBLE_PREDEFINED_TRACES), i_event_runtime, PC_TRACE_SOURCE_PREDEFINED))
            l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_RUNTIME, l_event_config, i_event_runtime.get_traces_runtime()))
            l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_CONTEXT, l_event_config, T_s.l().get_trace_context_list()))
            if (i_event_runtime.get_throwable() != T_logging_const.GC_NULL_OBJ_REF && i_event_runtime.get_throwable() instanceof E_application_exception) {
                l_trace_list.addAll(process_source_exclusive(PC_TRACE_SOURCE_EXCEPTION_TRACES, l_event_config, T_s.l().objects2traces(((E_application_exception) i_event_runtime.get_throwable()).get_traces(), T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES)))
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_DESTINATION_PURPOSE_Z1, p_purpose)
        }
        return l_trace_list
    }

    @Override
    @I_black_box("error")
    void set_destination_purpose(String i_destination_purpose) {
        p_purpose = i_destination_purpose
    }

    @Override
    @I_black_box("error")
    void set_location(String i_location) {
        p_location = i_location
    }

    @Override
    @I_black_box("error")
    String get_destination_purpose() {
        return p_purpose
    }

    @Override
    @I_black_box("error")
    String get_mask() {
        return p_mask
    }

    @Override
    @I_black_box("error")
    void set_mask(String i_mask) {
        p_mask = i_mask
    }
}
