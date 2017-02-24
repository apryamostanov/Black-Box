package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.static_string.T_static_string
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_logger extends T_object_with_guid implements I_logger {

    private ArrayList<I_destination> p_destinations = new ArrayList<I_destination>()
    private String p_logger_id = UUID.randomUUID().toString()
    private Stack<I_method_invocation> p_method_invocation_stack = new LinkedList<I_method_invocation>()
    private ArrayList<I_trace> p_trace_context_list = new ArrayList<I_trace>()
    private I_method_invocation PC_DEFAULT_METHOD_INVOCATION = GC_NULL_OBJ_REF as I_method_invocation
    private String p_mode = GC_EMPTY_STRING
    private Boolean p_is_init = init()
    private HashMap<String, Integer> p_statistics_method_calls_count = new HashMap<String, Integer>()
    private HashMap<String, Long> p_statistics_method_calls_duration = new HashMap<String, Long>()
    static String p_class_guid = UUID.randomUUID()

    @I_black_box_base("error")
    String get_class_guid() {
        return p_class_guid
    }

    @I_black_box_base("error")
    static String get_static_class_guid() {
        return p_class_guid
    }

    @Override
    @I_black_box_base("error")
    Boolean init() {
        PC_DEFAULT_METHOD_INVOCATION = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        PC_DEFAULT_METHOD_INVOCATION.set_class_name(T_s.c().GC_DEFAULT_CLASS_NAME)
        PC_DEFAULT_METHOD_INVOCATION.set_method_name(T_s.c().GC_DEFAULT_METHOD_NAME)
        return GC_TRUE
    }

    @Override
    @I_black_box_base("error")
    void set_mode(String i_mode) {
        p_mode = i_mode
    }

    @Override
    @I_black_box_base("error")
    I_event create_event(String i_event_type, String i_class_name, String i_method_name) {
        I_event l_event = T_s.ioc().instantiate(i_event_type) as I_event
        l_event.set_event_type(i_event_type)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
        l_event.set_depth(p_method_invocation_stack.size())
        l_event.set_datetimestamp(new Date())
        l_event.set_invocation(get_current_method_invocation())
        return l_event
    }

    @Override
    @I_black_box_base("error")
    I_trace spawn_trace(I_trace i_trace_new, I_trace i_trace_config) {
        I_trace l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        l_trace.set_name(i_trace_new.get_name())
        l_trace.set_source(i_trace_new.get_source())
        if (i_trace_config != GC_NULL_OBJ_REF) {
            l_trace.set_mask(i_trace_config.get_mask())
            l_trace.set_muted(i_trace_config.is_muted())
            l_trace.set_source(T_u.nvl(l_trace.get_source(), i_trace_config.get_source()) as String)
            l_trace.set_formatter(i_trace_config.get_formatter())
            l_trace.set_class(i_trace_config.get_config_class())
        }
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_trace_new.get_ref())
            l_trace.set_val(i_trace_new.get_val())

        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_trace_new.get_ref())
            if (i_trace_new.get_val() != GC_EMPTY_STRING) {
                l_trace.set_val(i_trace_new.get_val())
            } else {
                l_trace.set_val(i_trace_new.toString())
            }
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        return l_trace
    }

    @Override
    @I_black_box_base("error")
    I_trace object2trace(Object i_object, String i_source) {
        I_trace l_trace = GC_NULL_OBJ_REF as I_trace
        l_trace = T_s.ioc().instantiate("I_trace") as I_trace
        if (p_mode == T_s.c().GC_LOGGER_MODE_PRODUCTION) {
            l_trace.set_ref(i_object)
        } else if (p_mode == T_s.c().GC_LOGGER_MODE_DIAGNOSTIC) {
            l_trace.set_ref(i_object)
            l_trace.set_val(i_object.toString())
        } else {
            throw new E_application_exception(T_s.s().UNSUPPORTED_LOGGER_MODE, p_mode)
        }
        l_trace.set_source(i_source)
        return l_trace
    }

    @Override
    @I_black_box_base("error")
    ArrayList<I_trace> objects2traces(Collection<Object> i_objects, String i_source) {
        ArrayList<I_trace> l_method_args = new ArrayList<I_trace>()
        for (Object l_object : i_objects) {
            if (l_object instanceof I_trace) {
                l_method_args.add((I_trace) l_object)
            } else {
                I_trace l_trace = object2trace(l_object, i_source)
                l_method_args.add(l_trace)
            }
        }
        return l_method_args
    }

    @Override
    @I_black_box_base("error")
    ArrayList<I_trace> objects2traces_array(Object[] i_objects, String i_source) {
        return objects2traces(Arrays.asList(i_objects), i_source)
    }


    @Override
    @I_black_box_base("error")
    void log_generic(I_event i_event) {
        for (I_destination l_destination : p_destinations) {
            l_destination.log_generic(i_event)
        }
    }

    @Override
    @I_black_box_base("error")
    ArrayList<Object> get_trace_context_list() {
        return p_trace_context_list
    }

    @Override
    @I_black_box_base("error")
    void put_to_context(Object i_object, String i_name) {
        I_trace l_trace = object2trace(i_object, GC_TRACE_SOURCE_CONTEXT)
        l_trace.set_name(i_name)
        p_trace_context_list.add(l_trace)
    }

    @Override
    @I_black_box_base("error")
    void add_destination(I_destination i_destination) {
        p_destinations.add(i_destination)
    }

    @Override
    @I_black_box_base("error")
    void print_stats() {
        System.out.println("Call counts:")
        TreeMap<Integer, String> l_sorted_statistics_method_calls_count = new TreeMap<Integer, String>()
        for (String l_key in p_statistics_method_calls_count.keySet()) {
            if (l_sorted_statistics_method_calls_count.containsKey(p_statistics_method_calls_count.get(l_key))) {
                l_sorted_statistics_method_calls_count.put(p_statistics_method_calls_count.get(l_key), l_sorted_statistics_method_calls_count.get(p_statistics_method_calls_count.get(l_key)) + System.lineSeparator() + l_key)
            } else {
                l_sorted_statistics_method_calls_count.put(p_statistics_method_calls_count.get(l_key), l_key)
            }
        }
        for (Map.Entry<Float, String> l_entry in l_sorted_statistics_method_calls_count.entrySet()) {
            System.out.println(l_entry.getValue() + " : " + l_entry.getKey())
        }
        System.out.println("Call durations:")
        TreeMap<Long, String> l_sorted_statistics_method_calls_duration = new TreeMap<Long, String>()
        for (String l_key in p_statistics_method_calls_duration.keySet()) {
            if (l_sorted_statistics_method_calls_duration.containsKey(p_statistics_method_calls_duration.get(l_key))) {
                l_sorted_statistics_method_calls_duration.put(p_statistics_method_calls_duration.get(l_key), l_sorted_statistics_method_calls_duration.get(p_statistics_method_calls_duration.get(l_key)) + System.lineSeparator() + l_key)
            } else {
                l_sorted_statistics_method_calls_duration.put(p_statistics_method_calls_duration.get(l_key), l_key)
            }
        }
        for (Map.Entry<Float, String> l_entry in l_sorted_statistics_method_calls_duration.entrySet()) {
            System.out.println(T_u.get_short_name(l_entry.getValue()) + " : " + l_entry.getKey() + " milliseconds for " + p_statistics_method_calls_count.get(l_entry.getValue()) + " calls")
        }
    }

    @I_black_box_base("error")
    void add_invocation(String i_class_name, String i_method_name, ArrayList<I_trace> i_traces = GC_SKIPPED_ARGS as ArrayList<I_trace>) {
        I_method_invocation l_method_invocation = T_s.ioc().instantiate("I_method_invocation") as I_method_invocation
        l_method_invocation.set_class_name(i_class_name)
        l_method_invocation.set_method_name(i_method_name)
        l_method_invocation.set_method_arguments(i_traces)
        p_method_invocation_stack.push(l_method_invocation)
        l_method_invocation.start_timing()
        String l_stat_key = i_class_name + "->" + i_method_name
        if (p_statistics_method_calls_count.containsKey(l_stat_key)) {
            p_statistics_method_calls_count.put(l_stat_key, p_statistics_method_calls_count.get(l_stat_key) + GC_ONE_ONLY)
        } else {
            p_statistics_method_calls_count.put(l_stat_key, GC_ONE_ONLY)
        }
    }

    @I_black_box_base("error")
    void pop_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            I_method_invocation l_curr_invocation = get_current_method_invocation()
            l_curr_invocation.stop_timing()
            String l_stat_key = l_curr_invocation.get_class_name() + "->" + l_curr_invocation.get_method_name()
            if (p_statistics_method_calls_duration.containsKey(l_stat_key)) {
                p_statistics_method_calls_duration.put(l_stat_key, p_statistics_method_calls_duration.get(l_stat_key) + l_curr_invocation.get_elapsed_time())
            } else {
                p_statistics_method_calls_duration.put(l_stat_key, l_curr_invocation.get_elapsed_time())
            }
            p_method_invocation_stack.pop()
        }
    }

    @Override
    @I_black_box_base("error")
    void profile_enter(String i_class_name, String i_method_name) {
        add_invocation(i_class_name, i_method_name)
    }

    @Override
    @I_black_box_base("error")
    void profile_exit(String i_class_name, String i_method_name) {
        pop_invocation()
    }

    @Override
    @I_black_box_base("error")
    void log_enter(String i_class_name, String i_method_name, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        ArrayList<I_trace> l_method_arguments = objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME)
        add_invocation(i_class_name, i_method_name, l_method_arguments)
        I_event l_event = create_event("enter", i_class_name, i_method_name)
        if (T_u.method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(l_method_arguments)
        }
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_exit(String i_class_name, String i_method_name, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event("exit", i_class_name, i_method_name)
        if (T_u.method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
        pop_invocation()
    }

    @Override
    @I_black_box_base("error")
    Object log_exit_automatic(String i_class_name, String i_method_name, I_trace i_return_object_trace) {
        log_exit(i_class_name, i_method_name, i_return_object_trace)
        return i_return_object_trace.get_ref() //wow smart code like a real programmar is doin lol
    }

    @Override
    @I_black_box_base("error")
    Object profile_exit_automatic(String i_class_name, String i_method_name, Object i_return_object) {
        profile_exit(i_class_name, i_method_name)
        return i_return_object //wow smart code like a real programmar is doin lol
    }

    @Override
    @I_black_box_base("error")
    void log_exception(String i_class_name, String i_method_name, Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        log_error(i_class_name, i_method_name, i_throwable, i_traces)
        pop_invocation()
    }

    @Override
    @I_black_box_base("error")
    void log_error(String i_class_name, String i_method_name, Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event("error", i_class_name, i_method_name)
        l_event.set_throwable(i_throwable)
        l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_error(T_static_string i_message, Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event("error", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_message)
        log_error(get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), i_throwable, i_traces)
        l_event.set_throwable(i_throwable)
        l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_debug(T_static_string i_static_string_message, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("debug", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_message)
        if (T_u.method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_statement(T_static_string i_message, Integer i_line_number) {
        I_event l_event = create_event("statement", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_message)
        l_event.add_trace_runtime(T_s.r(i_line_number, "line_number"))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_info(T_static_string i_static_string_info, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("info", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_info)
        if (T_u.method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_warning(T_static_string i_static_string_warning, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("warning", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.set_message(i_static_string_warning)
        if (T_u.method_arguments_exist(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_receive(Object i_incoming_data) {
        I_event l_event = create_event("receive", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.add_trace_runtime(T_s.r(i_incoming_data, "incoming_data"))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_send(Object i_outgoing_data) {
        I_event l_event = create_event("send", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.add_trace_runtime(T_s.r(i_outgoing_data, "outgoing_data"))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    void log_sql(String i_sql_operation, String i_sql_string, String... i_bind_variables) {
        I_event l_event = create_event("sql", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name())
        l_event.add_trace_runtime(T_s.r(i_sql_operation, "sql_operation"))
        l_event.add_trace_runtime(T_s.r(i_sql_string, "sql_string"))
        l_event.add_trace_runtime(T_s.r(i_bind_variables, "bind_variables"))
        log_generic(l_event)
    }

    @Override
    @I_black_box_base("error")
    Integer get_depth() {
        return p_method_invocation_stack.size()
    }

    @I_black_box_base("error")
    I_method_invocation get_current_method_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            return p_method_invocation_stack.peek()
        } else {
            return PC_DEFAULT_METHOD_INVOCATION
        }
    }

    @I_black_box_base("error")
    I_method_invocation get_default_method_invocation() {
        return PC_DEFAULT_METHOD_INVOCATION
    }

    @Override
    @I_black_box_base("error")
    LinkedList<I_method_invocation> get_invocation_stack() {
        return p_method_invocation_stack
    }
}
