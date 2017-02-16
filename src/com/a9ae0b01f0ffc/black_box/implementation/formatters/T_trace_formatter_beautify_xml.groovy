package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_trace_formatter_beautify_xml implements I_trace_formatter {
    @Override
    @I_black_box_base("error")
    String format_trace(I_trace i_trace, I_event i_parent_event) {
        try {
            String l_formatted_trace = T_logging_const.GC_EMPTY_STRING
            Node l_node = T_logging_const.GC_NULL_OBJ_REF as Node
            if (i_trace.get_val() != T_logging_const.GC_EMPTY_STRING) {
                l_node = new XmlParser().parseText(i_trace.get_val())
            } else {
                l_node = new XmlParser().parseText(i_trace.get_ref().toString())
            }
            StringWriter l_string_writer = new StringWriter()
            XmlNodePrinter l_xml_node_printer = new XmlNodePrinter(new PrintWriter(l_string_writer))
            l_xml_node_printer.print(l_node)
            return l_string_writer.toString()
        } catch (Exception e_others) {
            T_s.l().log_warning(T_s.s().Invalid_XML, i_trace, e_others)
            if (i_trace.get_val() != T_logging_const.GC_EMPTY_STRING) {
                return i_trace.get_val()
            } else {
                return i_trace.get_ref().toString()
            }
        }
    }
}
