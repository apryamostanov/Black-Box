package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_logger extends T_logging_base_6_util {

    private ArrayList<T_destination> p_destinations = new ArrayList<T_destination>()
    private T_execution_node p_current_execution_node = GC_NULL_OBJ_REF as T_execution_node
    private ArrayList<T_trace> p_trace_context_list = new ArrayList<T_trace>()
    final T_execution_node PC_INITIAL_STARTING_EXECUTION_NODE = new T_execution_node(c().GC_INITIAL_STARTING_EXECUTION_NODE_NAME)
    private HashMap<String, Integer> p_stat_exec_node_calls_count = new HashMap<String, Integer>()
    private HashMap<String, Long> p_statistics_method_calls_duration = new HashMap<String, Long>()
    String p_commons_conf_file_name = GC_EMPTY_STRING

    void set_commons_conf_file_name(String i_commons_conf_file_name) {
        p_commons_conf_file_name
    }

    T_event create_event(String i_event_type) {
        T_event l_event = new T_event()
        l_event.set_event_type(i_event_type)
        l_event.set_datetimestamp(new Date())
        l_event.set_execution_node(get_current_execution_node())
        return l_event
    }

    void flush() {
        for (T_destination l_destination in p_destinations) {
            l_destination.flush()
        }
    }

    void log_generic(T_event i_event) {
        for (T_destination l_destination : p_destinations) {
            l_destination.log_generic(i_event)
        }
    }

    ArrayList<T_trace> get_trace_context_list() {
        return p_trace_context_list
    }

    void put_to_context(Object i_object, String i_name) {
        T_trace l_trace = object2trace(i_object)
        l_trace.set_name(i_name)
        p_trace_context_list.add(l_trace)
    }

    void add_destination(T_destination T_destination) {
        p_destinations.add(T_destination)
    }

    T_execution_node get_current_execution_node() {
        return nvl(p_current_execution_node, PC_INITIAL_STARTING_EXECUTION_NODE) as T_execution_node
    }

    T_execution_node get_initial_starting_execution_node() {
        return PC_INITIAL_STARTING_EXECUTION_NODE
    }

    void print_stats() {
        System.out.println("Call counts:")
        TreeMap<Integer, String> l_sorted_statistics_method_calls_count = new TreeMap<Integer, String>()
        for (String l_key in p_stat_exec_node_calls_count.keySet()) {
            if (l_sorted_statistics_method_calls_count.containsKey(p_stat_exec_node_calls_count.get(l_key))) {
                l_sorted_statistics_method_calls_count.put(p_stat_exec_node_calls_count.get(l_key), l_sorted_statistics_method_calls_count.get(p_stat_exec_node_calls_count.get(l_key)) + System.lineSeparator() + l_key)
            } else {
                l_sorted_statistics_method_calls_count.put(p_stat_exec_node_calls_count.get(l_key), l_key)
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
            System.out.println(get_short_name(l_entry.getValue()) + " : " + l_entry.getKey() + " milliseconds for " + p_stat_exec_node_calls_count.get(l_entry.getValue()) + " calls")
        }
    }

    void set_current_execution_node(T_execution_node i_execution_node) {
        p_current_execution_node = i_execution_node
    }

    void add_new_execution_node(String i_type, String i_execution_node_name, Integer i_line_number, T_trace... i_traces) {
        T_execution_node l_new_execution_node = new T_execution_node()
        l_new_execution_node.set_type(i_type)
        ArrayList<T_trace> l_method_arguments = objects2traces_array(i_traces)
        if (method_arguments_present(i_traces)) {
            l_new_execution_node.set_parameter_traces(l_method_arguments)
        }
        l_new_execution_node.set_name(i_execution_node_name)
        l_new_execution_node.set_line_number(i_line_number)
        l_new_execution_node.set_parent_node(get_current_execution_node())
        set_current_execution_node(l_new_execution_node)
        l_new_execution_node.start_timing()
        String l_stat_key = l_new_execution_node.get_profiling_key()
        if (p_stat_exec_node_calls_count.containsKey(l_stat_key)) {
            p_stat_exec_node_calls_count.put(l_stat_key, p_stat_exec_node_calls_count.get(l_stat_key) + GC_ONE_ONLY)
        } else {
            p_stat_exec_node_calls_count.put(l_stat_key, GC_ONE_ONLY)
        }
    }

    void remove_execution_node() {
        T_execution_node l_curr_invocation = get_current_execution_node()
        l_curr_invocation.stop_timing()
        String l_stat_key = l_curr_invocation.get_profiling_key()
        if (p_statistics_method_calls_duration.containsKey(l_stat_key)) {
            p_statistics_method_calls_duration.put(l_stat_key, p_statistics_method_calls_duration.get(l_stat_key) + l_curr_invocation.get_elapsed_time())
        } else {
            p_statistics_method_calls_duration.put(l_stat_key, l_curr_invocation.get_elapsed_time())
        }
        set_current_execution_node(get_current_execution_node().get_parent_node())
    }


    void profile_start_method(String i_class_name, String i_method_name, Integer i_line_number, T_trace... i_traces) {
        add_new_execution_node(GC_EXECUTION_NODE_TYPE_METHOD, make_method_execution_node_name(i_class_name, i_method_name, i_line_number), i_line_number, i_traces)
    }

    void profile_start_statement(String i_name, String i_code, Integer i_line_number) {
        add_new_execution_node(GC_EXECUTION_NODE_TYPE_STATEMENT, i_name, i_line_number)
        get_current_execution_node().set_code(i_code)
    }

    void profile_start_expression(String i_name, String i_code, Integer i_line_number) {
        add_new_execution_node(GC_EXECUTION_NODE_TYPE_EXPRESSION, i_name, i_line_number)
        get_current_execution_node().set_code(i_code)
    }

    void profile_stop_any() {
        remove_execution_node()
    }

    void log_enter_method(String i_class_name, String i_method_name, Integer i_line_number, T_trace... i_traces) {
        profile_start_method(i_class_name, i_method_name, i_line_number, i_traces)
        log_generic(create_event(GC_EVENT_TYPE_METHOD_ENTER))
    }

    void log_exit_method() {
        /*\/\/\/ There is no Finally block for Statements and Expressions, so we implicitly close them upon reaching to Method's Finally block*/
        while (get_current_execution_node().get_type() != GC_EXECUTION_NODE_TYPE_METHOD) {
            if (get_current_execution_node().get_type() == GC_EXECUTION_NODE_TYPE_EXPRESSION) {
                log_exit_expression()
            } else if (get_current_execution_node().get_type() == GC_EXECUTION_NODE_TYPE_STATEMENT) {
                log_exit_statement()
            } else {
                throw new E_application_exception(s.Internal_error_unexpected_execution_node_type_Z1, get_current_execution_node().get_type())
            }
        }
        T_event l_event = create_event(GC_EVENT_TYPE_METHOD_EXIT)
        profile_stop_any()
        log_generic(l_event)
    }

    void log_enter_statement(String i_statement_name, String i_code, Integer i_line_number) {
        profile_start_statement(i_statement_name, i_code, i_line_number)
        log_generic(create_event(GC_EVENT_TYPE_STATEMENT_ENTER))
    }

    void log_enter_expression(String i_name, String i_code, Integer i_line_number) {
        profile_start_expression(i_name, i_code, i_line_number)
        log_generic(create_event(GC_EVENT_TYPE_EXPRESSION_ENTER))
    }

    void log_exit_statement() {
        T_event l_event = create_event(GC_EVENT_TYPE_STATEMENT_EXIT)
        profile_stop_any()
        log_generic(l_event)
    }

    Object log_run_closure(String i_name, String i_code, Integer i_line_number, Closure i_method_closure) {
        log_enter_expression(i_name, i_code, i_line_number)
        Object l_result = i_method_closure.call()
        if (is_not_null(l_result)) {
            log_result(r(l_result, GC_EMPTY_STRING))
        }
        log_exit_expression()
        return l_result
    }

    void log_exit_expression() {
        T_event l_event = create_event(GC_EVENT_TYPE_EXPRESSION_EXIT)
        profile_stop_any()
        log_generic(l_event)
    }

    Object log_result(T_trace i_return_object_trace) {
        get_current_execution_node().set_result(i_return_object_trace)
        T_event l_event = create_event(GC_EVENT_TYPE_RESULT)
        log_generic(l_event)
        return i_return_object_trace.get_ref()
    }

    void log_error_method_standalone(String i_class_name, String i_method_name, Integer i_line_number, Throwable i_throwable, T_trace... i_traces) {
        T_execution_node l_execution_node
        T_execution_node l_standalone_execution_node = get_ioc().instantiate("T_execution_node") as T_execution_node
        l_standalone_execution_node.set_name(make_method_execution_node_name(i_class_name, i_method_name, i_line_number))
        l_standalone_execution_node.set_throwable(i_throwable)
        l_standalone_execution_node.set_line_number(i_line_number)
        l_standalone_execution_node.set_parameter_traces(Arrays.asList(i_traces) as ArrayList<T_trace>)
        l_execution_node = l_standalone_execution_node
        T_event l_event = create_event(GC_EVENT_TYPE_METHOD_ERROR)
        l_event.set_execution_node(l_execution_node)
        log_generic(l_event)
    }

    void log_error_method(String i_class_name, String i_method_name, Integer i_line_number, Throwable i_throwable, T_trace... i_traces) {
        while (get_current_execution_node().get_type() != GC_EXECUTION_NODE_TYPE_METHOD) {
            if (get_current_execution_node().get_type() == GC_EXECUTION_NODE_TYPE_EXPRESSION) {
                log_error_expression(i_throwable)
                log_exit_expression()
            } else if (get_current_execution_node().get_type() == GC_EXECUTION_NODE_TYPE_STATEMENT) {
                log_error_statement(i_throwable)
                log_exit_statement()
            } else {
                throw new E_application_exception(s.Internal_error_unexpected_execution_node_type_Z1, get_current_execution_node().get_type())
            }
        }
        T_execution_node l_execution_node
        get_current_execution_node().set_throwable(i_throwable)
        l_execution_node = get_current_execution_node()
        T_event l_event = create_event(GC_EVENT_TYPE_METHOD_ERROR)
        l_event.set_execution_node(l_execution_node)
        log_generic(l_event)
    }

    void log_error_statement(Throwable i_throwable) {
        T_event l_event = create_event(GC_EVENT_TYPE_STATEMENT_ERROR)
        get_current_execution_node().set_throwable(i_throwable)
        log_generic(l_event)
    }

    void log_error_expression(Throwable i_throwable) {
        T_event l_event = create_event(GC_EVENT_TYPE_EXPRESSION_ERROR)
        get_current_execution_node().set_throwable(i_throwable)
        log_generic(l_event)
    }

    void log_trace(Object... i_traces) {
        T_event l_event = create_event(GC_EVENT_TYPE_TRACES)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(objects2traces_array(i_traces))
        }
        log_generic(l_event)
    }

    void log_debug(T_static_string i_static_string_message, Object... i_traces) {
        T_event l_event = create_event(GC_EVENT_TYPE_DEBUG)
        l_event.set_message(i_static_string_message)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(objects2traces_array(i_traces))
        }
        log_generic(l_event)
    }

    void log_info(T_static_string i_static_string_info, Object... i_traces) {
        T_event l_event = create_event(GC_EVENT_TYPE_INFO)
        l_event.set_message(i_static_string_info)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(objects2traces_array(i_traces))
        }
        log_generic(l_event)
    }

    void log_generic_with_message(String i_event_type, Integer i_line_number, T_static_string i_message, T_trace... i_traces) {
        T_event l_event = create_event(i_event_type)
        l_event.set_message(i_message)
        l_event.set_line_number(i_line_number)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(Arrays.asList(i_traces) as ArrayList<T_trace>)
        }
        log_generic(l_event)
    }

    void log_warning(T_static_string i_static_string_warning, Object... i_traces) {
        T_event l_event = create_event(GC_EVENT_TYPE_WARNING)
        l_event.set_message(i_static_string_warning)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(objects2traces_array(i_traces))
        }
        log_generic(l_event)
    }

    void log_send_receive_generic(String i_event_type, Object[] i_traces) {
        T_event l_event = create_event(i_event_type)
        if (method_arguments_present(i_traces)) {
            l_event.set_standalone_traces(objects2traces_array(i_traces))
        }
        log_generic(l_event)
    }

    void log_receive_tcp(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_TCP, i_traces)
    }

    void log_send_tcp(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_TCP, i_traces)
    }

    void log_send_sql(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SQL, i_traces)
    }

    void log_receive_sql(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SQL, i_traces)
    }

    void log_send_http(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_HTTP, i_traces)
    }

    void log_receive_http(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_HTTP, i_traces)
    }

    void log_send_i2c(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_I2C, i_traces)
    }

    void log_receive_i2c(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_I2C, i_traces)
    }

    void log_send_spi(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SPI, i_traces)
    }

    void log_receive_spi(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SPI, i_traces)
    }

    void log_send_serial(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_SERIAL, i_traces)
    }

    void log_receive_serial(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_SERIAL, i_traces)
    }

    void log_send_bluetooth(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_SEND_BLUETOOTH, i_traces)
    }

    void log_receive_bluetooth(Object... i_traces) {
        log_send_receive_generic(GC_EVENT_TYPE_RECEIVE_BLUETOOTH, i_traces)
    }
}
