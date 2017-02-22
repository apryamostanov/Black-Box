package com.a9ae0b01f0ffc.black_box.conf

import com.a9ae0b01f0ffc.commons.config_helper.T_conf
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.static_string.T_static_string_builder
import groovy.util.slurpersupport.GPathResult

class T_zero_ioc_conf extends T_conf {

    static final String PC_IOC_CONF_TEXT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<classes_config>\n" +
            "    <!--IOC-->\n" +
            "    <I_logger_builder value=\"com.a9ae0b01f0ffc.black_box.implementation.T_logger_builder_default\"/>\n" +
            "    <I_logger value=\"com.a9ae0b01f0ffc.black_box.implementation.T_logger\"/>\n" +
            "    <I_trace value=\"com.a9ae0b01f0ffc.black_box.implementation.T_trace\"/>\n" +
            "    <I_method_invocation value=\"com.a9ae0b01f0ffc.black_box.implementation.T_method_invocation\"/>\n" +
            "    <!--Loggers-->\n" +
            "    <logger value=\"com.a9ae0b01f0ffc.black_box.implementation.T_logger\"/>\n" +
            "    <!--Destinations-->\n" +
            "    <file value=\"com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_file\"/>\n" +
            "    <file_buffer value=\"com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_file_buffer\"/>\n" +
            "    <shell value=\"com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_shell\"/>\n" +
            "    <memory value=\"com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_array\"/>\n" +
            "    <variable value=\"com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_variable\"/>\n" +
            "    <!--Events-->\n" +
            "    <enter value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <exit value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <debug value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <info value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <warning value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <error value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <send value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <receive value=\"com.a9ae0b01f0ffc.black_box.implementation.T_event\"/>\n" +
            "    <!--Formatters-->\n" +
            "    <xml value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_xml\"/>\n" +
            "    <xml_hierarchical value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_xml_hierarchical\"/>\n" +
            "    <csv value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_csv\"/>\n" +
            "    <html value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_html\"/>\n" +
            "    <message value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_trace_formatter_for_messages\"/>\n" +
            "    <beautify_xml value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_trace_formatter_beautify_xml\"/>\n" +
            "    <default value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_trace_formatter_shorten_class_names\"/>\n" +
            "    <!--Misc-->\n" +
            "    <trace_name_resolver_default value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_csv\"/>\n" +
            "    <trace_name_resolver_class_name value=\"com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter_csv\"/>\n" +
            "</classes_config>"

    T_zero_ioc_conf() {
        try {
            p_gpathresult = (GPathResult) new XmlSlurper().parseText(PC_IOC_CONF_TEXT)
        } catch (FileNotFoundException e_file_not_found) {
            throw new E_application_exception(T_static_string_builder.s().Configuration_file_not_found_for_path_Z1, l_conf_file_handle.getAbsolutePath(), e_file_not_found)
        }
        for (l_child_node in p_gpathresult.children()) {
            p_values_by_name.put(l_child_node.name(), l_child_node.@value.text())
            p_names_by_value.put(l_child_node.@value.text(), l_child_node.name())
        }
    }

    private T_zero_ioc_conf(String i_conf_file_name) {
        super(i_conf_file_name)
    }
}
