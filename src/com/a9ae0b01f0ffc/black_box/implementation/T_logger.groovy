package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_logger extends T_logging_base_6_util implements I_logger {

    private ArrayList<I_destination> p_destinations = new ArrayList<I_destination>()
    private Stack<I_method_invocation> p_method_invocation_stack = new LinkedList<I_method_invocation>()
    private ArrayList<I_trace> p_trace_context_list = new ArrayList<I_trace>()
    private I_method_invocation PC_DEFAULT_METHOD_INVOCATION = GC_NULL_OBJ_REF as I_method_invocation
    private Boolean p_is_init = init()
    private HashMap<String, Integer> p_statistics_method_calls_count = new HashMap<String, Integer>()
    private HashMap<String, Long> p_statistics_method_calls_duration = new HashMap<String, Long>()

    @Override
    Boolean init() {
        PC_DEFAULT_METHOD_INVOCATION = get_ioc().instantiate("I_method_invocation") as I_method_invocation
        PC_DEFAULT_METHOD_INVOCATION.set_class_name(c().GC_DEFAULT_CLASS_NAME)
        PC_DEFAULT_METHOD_INVOCATION.set_method_name(c().GC_DEFAULT_METHOD_NAME)
        return GC_TRUE
    }

    @Override
    I_event create_event(String i_event_type, String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number) {
        I_event l_event = get_ioc().instantiate(i_event_type) as I_event
        l_event.set_event_type(i_event_type)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
        l_event.set_depth(p_method_invocation_stack.size())
        l_event.set_datetimestamp(new Date())
        l_event.set_invocation(get_current_method_invocation())
        l_event.set_statement_name(i_statement_name)
        l_event.set_line_number(i_line_number)
        return l_event
    }

    @Override
    void log_generic(I_event i_event) {
        for (I_destination l_destination : p_destinations) {
            l_destination.log_generic(i_event)
        }
    }

    @Override
    ArrayList<Object> get_trace_context_list() {
        return p_trace_context_list
    }

    @Override
    void put_to_context(Object i_object, String i_name) {
        I_trace l_trace = object2trace(i_object, GC_TRACE_SOURCE_CONTEXT)
        l_trace.set_name(i_name)
        p_trace_context_list.add(l_trace)
    }

    @Override
    void add_destination(I_destination i_destination) {
        p_destinations.add(i_destination)
    }

    @Override
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
            System.out.println(get_short_name(l_entry.getValue()) + " : " + l_entry.getKey() + " milliseconds for " + p_statistics_method_calls_count.get(l_entry.getValue()) + " calls")
        }
    }

    void add_invocation(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, ArrayList<I_trace> i_traces = GC_SKIPPED_ARGS as ArrayList<I_trace>) {
        I_method_invocation l_method_invocation = get_ioc().instantiate("I_method_invocation") as I_method_invocation
        l_method_invocation.set_class_name(i_class_name)
        l_method_invocation.set_method_name(i_method_name)
        l_method_invocation.set_method_arguments(i_traces)
        l_method_invocation.set_statement_name(i_statement_name)
        l_method_invocation.set_line_number(i_line_number)
        p_method_invocation_stack.push(l_method_invocation)
        l_method_invocation.start_timing()
        String l_stat_key = i_class_name + "->" + i_method_name + "->" + i_statement_name + "->" + i_line_number.toString()
        if (p_statistics_method_calls_count.containsKey(l_stat_key)) {
            p_statistics_method_calls_count.put(l_stat_key, p_statistics_method_calls_count.get(l_stat_key) + GC_ONE_ONLY)
        } else {
            p_statistics_method_calls_count.put(l_stat_key, GC_ONE_ONLY)
        }
    }

    void pop_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            I_method_invocation l_curr_invocation = get_current_method_invocation()
            l_curr_invocation.stop_timing()
            String l_stat_key = l_curr_invocation.get_class_name() + "->" + l_curr_invocation.get_method_name() + "->" + l_curr_invocation.get_statement_name() + "->" + l_curr_invocation.get_line_number().toString()
            if (p_statistics_method_calls_duration.containsKey(l_stat_key)) {
                p_statistics_method_calls_duration.put(l_stat_key, p_statistics_method_calls_duration.get(l_stat_key) + l_curr_invocation.get_elapsed_time())
            } else {
                p_statistics_method_calls_duration.put(l_stat_key, l_curr_invocation.get_elapsed_time())
            }
            p_method_invocation_stack.pop()
        }
    }

    @Override
    void profile_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number) {
        add_invocation(i_class_name, i_method_name, i_statement_name, i_line_number)
    }

    @Override
    void profile_exit() {
        pop_invocation()
    }

    @Override
    void log_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        ArrayList<I_trace> l_method_arguments = objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME)
        add_invocation(i_class_name, i_method_name, i_statement_name, i_line_number, l_method_arguments)
        I_event l_event = create_event("enter", i_class_name, i_method_name, i_statement_name, i_line_number)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(l_method_arguments)
        }
        log_generic(l_event)
    }

    @Override
    void log_exit() {
        I_event l_event = create_event("exit", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        log_generic(l_event)
        pop_invocation()
    }

    @Override
    Object log_result(I_trace i_return_object_trace) {
        I_event l_event = create_event("result", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.add_trace_runtime(i_return_object_trace)
        log_generic(l_event)
        return i_return_object_trace.get_ref() //wow smart code like a real programmar is doin lol
    }

    @Override
    Object profile_exit_automatic(Object i_return_object) {
        profile_exit()
        return i_return_object //wow smart code like a real programmar is doin lol
    }

    @Override
    void log_error(Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event("error", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.set_throwable(i_throwable)
        l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        log_generic(l_event)
    }

    @Override
    void log_debug(T_static_string i_static_string_message, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("debug", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.set_message(i_static_string_message)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_info(T_static_string i_static_string_info, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("info", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.set_message(i_static_string_info)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_warning(T_static_string i_static_string_warning, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event("warning", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.set_message(i_static_string_warning)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_receive(Object i_incoming_data) {
        I_event l_event = create_event("receive", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.add_trace_runtime(r(i_incoming_data, "incoming_data"))
        log_generic(l_event)
    }

    @Override
    void log_send(Object i_outgoing_data) {
        I_event l_event = create_event("send", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.add_trace_runtime(r(i_outgoing_data, "outgoing_data"))
        log_generic(l_event)
    }

    @Override
    void log_sql(String i_sql_operation, String i_sql_string, String... i_bind_variables) {
        I_event l_event = create_event("sql", get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.add_trace_runtime(r(i_sql_operation, "sql_operation"))
        l_event.add_trace_runtime(r(i_sql_string, "sql_string"))
        l_event.add_trace_runtime(r(i_bind_variables, "bind_variables"))
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

    I_method_invocation get_default_method_invocation() {
        return PC_DEFAULT_METHOD_INVOCATION
    }

    @Override
    LinkedList<I_method_invocation> get_invocation_stack() {
        return p_method_invocation_stack
    }
}
