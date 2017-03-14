package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string

import java.util.logging.Level

class T_logging_base_6_util extends T_logging_base_5_context {

    static String escape_xml(String i_unescaped_xml_string) {
        if (i_unescaped_xml_string.contains(GC_XML_DOUBLE_QUOTE)
                || i_unescaped_xml_string.contains(GC_XML_DOUBLE_QUOTE)
                || i_unescaped_xml_string.contains(GC_XML_LESS)
                || i_unescaped_xml_string.contains(GC_XML_GREATER)
                || i_unescaped_xml_string.contains(GC_XML_AMP)) {
            return GC_XML_CDATA_OPEN + i_unescaped_xml_string + GC_XML_CDATA_CLOSE
        } else {
            return i_unescaped_xml_string
        }
    }

    static I_trace r(Object i_object, String i_trace_name) {
        I_trace l_trace = object2trace(i_object, GC_TRACE_SOURCE_RUNTIME)
        l_trace.set_name(i_trace_name)
        return l_trace
    }

    static I_trace t(Object i_object, T_static_string i_trace_name) {
        return r(i_object, i_trace_name.toString())
    }


    static I_trace object2trace(Object i_object, String i_source) {
        I_trace l_trace = get_ioc().instantiate("I_trace") as I_trace
        l_trace.set_ref(i_object)
        l_trace.set_val(i_object.toString())
        l_trace.set_source(i_source)
        l_trace.set_name(c().GC_DEFAULT_TRACE_NAME)
        return l_trace
    }


    static ArrayList<I_trace> objects2traces(Collection<Object> i_objects, String i_source) {
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


    static ArrayList<I_trace> objects2traces_array(Object[] i_objects, String i_source) {
        return objects2traces(Arrays.asList(i_objects), i_source)
    }

    static Level jul_level_by_name(String i_level_name) {
        Level l_level
        if (i_level_name == "ALL") {
            l_level = Level.ALL
        } else if (i_level_name == "CONFIG") {
            l_level = Level.CONFIG
        } else if (i_level_name == "FINE") {
            l_level = Level.FINE
        } else if (i_level_name == "FINER") {
            l_level = Level.FINER
        } else if (i_level_name == "FINEST") {
            l_level = Level.FINEST
        } else if (i_level_name == "INFO") {
            l_level = Level.INFO
        } else if (i_level_name == "OFF") {
            l_level = Level.OFF
        } else if (i_level_name == "SEVERE") {
            l_level = Level.SEVERE
        } else if (i_level_name == "WARNING") {
            l_level = Level.WARNING
        } else {
            throw new E_application_exception(s.Unsupported_java_util_logging_level_Z1, i_level_name)
        }
        return l_level
    }

}
