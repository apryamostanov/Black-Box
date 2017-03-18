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
    I_event create_event(String i_event_type, String i_class_name = get_default_method_invocation().get_class_name(), String i_method_name = get_default_method_invocation().get_method_name(), String i_statement_name = get_default_method_invocation().get_statement_name(), Integer i_line_number = get_default_method_invocation().get_line_number()) {
        I_event l_event = get_ioc().instantiate("I_event") as I_event
        l_event.set_event_type(i_event_type)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
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
    I_method_invocation get_current_method_invocation() {
        if (!p_method_invocation_stack.isEmpty()) {
            return p_method_invocation_stack.peek()
        } else {
            return PC_DEFAULT_METHOD_INVOCATION
        }
    }

    @Override
    I_method_invocation get_default_method_invocation() {
        return PC_DEFAULT_METHOD_INVOCATION
    }

    @Override
    LinkedList<I_method_invocation> get_invocation_stack() {
        return p_method_invocation_stack
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

    void add_invocation(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number) {
        I_method_invocation l_method_invocation = get_ioc().instantiate("I_method_invocation") as I_method_invocation
        l_method_invocation.set_class_name(i_class_name)
        l_method_invocation.set_method_name(i_method_name)
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
    void profile_method_enter(String i_class_name, String i_method_name, Integer i_line_number) {
            add_invocation(i_class_name, i_method_name, GC_STATEMENT_NAME_METHOD, i_line_number)
    }

    @Override
    void profile_method_exit() {
            pop_invocation()
    }

    @Override
    void profile_statement_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number) {
            add_invocation(i_class_name, i_method_name, i_statement_name, i_line_number)
    }

    @Override
    void profile_statement_exit() {
            pop_invocation()
    }

    @Override
    void log_method_enter(String i_class_name, String i_method_name, Integer i_line_number, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        ArrayList<I_trace> l_method_arguments = objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME)
        profile_method_enter(i_class_name, i_method_name, i_line_number)
        I_event l_event = create_event(GC_EVENT_TYPE_METHOD_ENTER, i_class_name, i_method_name, GC_STATEMENT_NAME_METHOD, i_line_number)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(l_method_arguments)
        }
        log_generic(l_event)
    }

    @Override
    void log_method_exit() {
        I_event l_event = create_event(GC_EVENT_TYPE_METHOD_EXIT, get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), GC_STATEMENT_NAME_METHOD, get_current_method_invocation().get_line_number())
        profile_method_exit()
        log_generic(l_event)
    }

    @Override
    void log_statement_enter(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        ArrayList<I_trace> l_method_arguments = objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME)
        profile_statement_enter(i_class_name, i_method_name, i_statement_name, i_line_number)
        I_event l_event = create_event(GC_EVENT_TYPE_STATEMENT_ENTER, i_class_name, i_method_name, i_statement_name, i_line_number)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(l_method_arguments)
        }
        log_generic(l_event)
    }

    @Override
    void log_statement_exit() {
        I_event l_event = create_event(GC_EVENT_TYPE_STATEMENT_EXIT, get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        profile_statement_exit()
        log_generic(l_event)
    }

    @Override
    Object log_result(I_trace i_return_object_trace) {
        I_event l_event = create_event(GC_EVENT_TYPE_RESULT, get_current_method_invocation().get_class_name(), get_current_method_invocation().get_method_name(), get_current_method_invocation().get_statement_name(), get_current_method_invocation().get_line_number())
        l_event.add_trace_runtime(i_return_object_trace)
        log_generic(l_event)
        return i_return_object_trace.get_ref() //wow smart code like a real programmar is doin lol
    }

    @Override
    void log_method_error(String i_class_name, String i_method_name, Integer i_line_number, Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event(GC_EVENT_TYPE_METHOD_ERROR, i_class_name, i_method_name, GC_STATEMENT_NAME_METHOD, i_line_number)
        l_event.set_throwable(i_throwable)
        l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        log_generic(l_event)
    }

    @Override
    void log_statement_error(String i_class_name, String i_method_name, String i_statement_name, Integer i_line_number, Throwable i_throwable, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event(GC_EVENT_TYPE_STATEMENT_ERROR, i_class_name, i_method_name, i_statement_name, i_line_number)
        l_event.set_throwable(i_throwable)
        l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        log_generic(l_event)
    }
    @Override
    void log_trace(Object... i_traces) {
        I_event l_event = create_event(GC_EVENT_TYPE_TRACES)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_debug(T_static_string i_static_string_message, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event(GC_EVENT_TYPE_DEBUG)
        l_event.set_message(i_static_string_message)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_info(T_static_string i_static_string_info, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event(GC_EVENT_TYPE_INFO)
        l_event.set_message(i_static_string_info)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_generic_automatic(String i_event_type, Integer i_line_number, T_static_string i_message, String i_class_name, String i_method_name, I_trace... i_traces = GC_SKIPPED_ARGS as I_trace[]) {
        I_event l_event = create_event(i_event_type, i_class_name, i_method_name, GC_STATEMENT_NAME_METHOD, i_line_number)
        l_event.set_message(i_message)
        l_event.set_line_number(i_line_number)
        l_event.set_class_name(i_class_name)
        l_event.set_method_name(i_method_name)
        l_event.set_statement_name(GC_STATEMENT_NAME_METHOD)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(Arrays.asList(i_traces))
        }
        log_generic(l_event)
    }

    @Override
    void log_warning(T_static_string i_static_string_warning, Object... i_traces = GC_SKIPPED_ARGS as Object[]) {
        I_event l_event = create_event(GC_EVENT_TYPE_WARNING)
        l_event.set_message(i_static_string_warning)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    void log_send_receive_generic(String i_event_type, Object[] i_traces) {
        I_event l_event = create_event(i_event_type)
        if (method_arguments_present(i_traces)) {
            l_event.add_traces_runtime(objects2traces_array(i_traces, GC_TRACE_SOURCE_RUNTIME))
        }
        log_generic(l_event)
    }

    @Override
    void log_receive_tcp(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_TCP, i_traces)
    }

    @Override
    void log_send_tcp(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_TCP, i_traces)
    }

    @Override
    void log_send_sql(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SQL, i_traces)
    }

    @Override
    void log_receive_sql(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SQL, i_traces)
    }

    @Override
    void log_send_http(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_HTTP, i_traces)
    }

    @Override
    void log_receive_http(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_HTTP, i_traces)
    }

    @Override
    void log_send_i2c(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_I2C, i_traces)
    }

    @Override
    void log_receive_i2c(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_I2C, i_traces)
    }

    @Override
    void log_send_spi(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SPI, i_traces)
    }

    @Override
    void log_receive_spi(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SPI, i_traces)
    }

    @Override
    void log_send_serial(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SERIAL, i_traces)
    }

    @Override
    void log_receive_serial(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SERIAL, i_traces)
    }

    @Override
    void log_send_bluetooth(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_BLUETOOTH, i_traces)
    }

    @Override
    void log_receive_bluetooth(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_BLUETOOTH, i_traces)
    }
}
