package com.a9ae0b01f0ffc.mighty_logger.implementation

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_destination
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s
import com.a9ae0b01f0ffc.static_string.T_static_string

class T_logger implements I_logger {

    private ArrayList<I_destination> p_destinations = new ArrayList<I_destination>()
    private String p_logger_id = UUID.randomUUID().toString()
    private Stack<I_method_invocation> p_method_invocation_stack = new LinkedList<I_method_invocation>()
    private ArrayList<Object> p_trace_context_list = new ArrayList<Object>()
    private I_method_invocation PC_DEFAULT_METHOD_INVOCATION = T_s.c().GC_NULL_OBJ_REF as I_method_invocation
    private String p_mode = T_s.c().GC_EMPTY_STRING
    private Boolean p_is_init = init()

    Boolean init() {
        PC_DEFAULT_METHOD_INVOCATION = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        PC_DEFAULT_METHOD_INVOCATION.set_class_name(T_s.c().GC_DEFAULT_CLASS_NAME)
        PC_DEFAULT_METHOD_INVOCATION.set_method_name(T_s.c().GC_DEFAULT_METHOD_NAME)
        return T_s.c().GC_TRUE
    }

    void set_mode(String i_mode) {
        p_mode = i_mode
    }

    I_event create_event(String i_event_type, String i_class_name, String i_method_name) {
        I_event l_event = T_s.ioc().instantiate(i_event_type) as I_event
        l_event.set_event_type(i_event_type)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
        l_event.set_depth(p_method_invocation_stack.size())
        l_event.set_datetimestamp(new Date())
        return l_event
    }

    I_trace trace2trace(I_trace i_trace) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_trace.get_ref())
            l_trace.set_val(i_trace.get_val())
        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_trace.get_ref())
            if (i_trace.get_val() != T_s.c().GC_EMPTY_STRING) {
                l_trace.set_val(i_trace.get_val())
            } else {
                l_trace.set_val(i_trace.toString())
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        return l_trace
    }

    I_trace object2trace(Object i_object) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_object)
        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_object)
            l_trace.set_val(i_object.toString())
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        return l_trace
    }

    I_trace[] objects2traces(Object[] i_objects) {
        ArrayList<I_trace> l_method_args = new ArrayList<I_trace>()
        for (l_object in i_objects) {
            I_trace l_trace = object2trace(l_object)
            l_method_args.add(l_trace)
        }
        return l_method_args
    }

    void pop() {
        if (!p_method_invocation_stack.isEmpty()) {
            p_method_invocation_stack.pop()
        }
    }

    @Override
    void log_generic(I_event i_event) {
        for (l_destination in p_destinations) {
            l_destination.log_generic(i_event)
        }
    }

    @Override
    ArrayList<Object> get_trace_context_list() {
        return p_trace_context_list
    }

    @Override
    void put_to_context(Object i_object) {
        p_trace_context_list.add(i_object)
    }

    @Override
    void add_destination(I_destination i_destination) {
        p_destinations.add(i_destination)
    }

    @Override
    String get_logger_id() {
        p_logger_id
    }

    @Override
    void log_enter(String i_class_name, String i_method_name, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG) {
        I_method_invocation l_method_invocation = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        l_method_invocation.set_class_name(i_class_name)
        l_method_invocation.set_method_name(i_method_name)
        l_method_invocation.set_method_arguments(objects2traces(i_method_arguments))
        p_method_invocation_stack.push(l_method_invocation)
        I_event l_event = create_event("enter", i_class_name, i_method_name)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        log_generic(l_event)
    }

    @Override
    void log_exit(String i_class_name, String i_method_name, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG as I_trace[]) {
        I_event l_event = create_event("exit", i_class_name, i_method_name)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        log_generic(l_event)
        pop()
    }

    @Override
    void log_exception(String i_class_name, String i_method_name, Exception i_exception, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG) {
        I_event l_event = create_event("error", i_class_name, i_method_name)
        l_event.set_exception(i_exception)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        l_event.add_traces_runtime(Arrays.asList(get_current_method_invocation().get_method_arguments()))
        if (i_exception instanceof E_application_exception) {
            l_event.add_traces_runtime(Arrays.asList(objects2traces(((E_application_exception)i_exception).p_supplementary_objects)))
        }
        log_generic(l_event)
        pop()
    }

    @Override
    void log_debug(T_static_string i_static_string_message, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG) {
        I_event l_event = create_event("debug", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_message)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        log_generic(l_event)
    }

    @Override
    void log_info(T_static_string i_static_string_info, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG as Object[]) {
        I_event l_event = create_event("info", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_info)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        log_generic(l_event)
    }

    @Override
    void log_warning(T_static_string i_static_string_warning, Object... i_method_arguments = T_s.c().GC_SKIPPED_ARG as Object[]) {
        I_event l_event = create_event("warning", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_warning)
        l_event.add_traces_runtime(Arrays.asList(objects2traces(i_method_arguments)))
        log_generic(l_event)
    }

    @Override
    Integer get_depth() {
        return p_method_invocation_stack.size()
    }

    I_method_invocation get_current_method_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            return p_method_invocation_stack.peek()
        } else {
            return PC_DEFAULT_METHOD_INVOCATION
        }
    }

}
