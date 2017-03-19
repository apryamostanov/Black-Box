package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.implementation.T_trace
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string

import java.util.logging.Level

class T_logging_base_6_util extends T_logging_base_5_context {
    
    private static final String PC_CLASS_NAME = "T_logging_base_5_context"

    static Boolean is_primitive(String i_class_name_short) {
        if (["Byte", "Short", "Integer", "Long", "Float", "Double", "Boolean", "Char", "String", "GString"].contains(i_class_name_short)) {
            return GC_TRUE
        } else {
            return GC_FALSE
        }
    }

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

    static T_trace r(Object i_object, String T_trace_name) {
        T_trace l_trace = object2trace(i_object)
        l_trace.set_name(T_trace_name)
        return l_trace
    }

    static T_trace t(Object i_object, T_static_string T_trace_name) {
        return r(i_object, T_trace_name.toString())
    }


    static T_trace object2trace(Object i_object) {
        T_trace l_trace = get_ioc().instantiate("I_trace") as T_trace
        l_trace.set_ref(i_object)
        l_trace.set_val(i_object.toString())
        l_trace.set_name(c().GC_DEFAULT_TRACE_NAME)
        return l_trace
    }

    static String make_method_execution_node_name(String i_class_name, String i_method_name, Integer i_line_number) {
        return i_class_name + GC_POINT + i_method_name + GC_COLON + i_line_number.toString()
    }

    static Object run_closure(Closure i_closure) {
        System.out.println(i_closure.getClass().getName())
        for (l_method in i_closure.getClass().getDeclaredMethods()) {
            System.out.println(l_method.getName())
        }
        System.out.println(i_closure instanceof Class)
        return i_closure.call()
    }

    static ArrayList<T_trace> objects2traces(Collection<Object> i_objects) {
        ArrayList<T_trace> l_method_args = new ArrayList<T_trace>()
        for (Object l_object : i_objects) {
            if (l_object instanceof T_trace) {
                l_method_args.add((T_trace) l_object)
            } else {
                T_trace l_trace = object2trace(l_object)
                l_method_args.add(l_trace)
            }
        }
        return l_method_args
    }


    static ArrayList<T_trace> objects2traces_array(Object[] i_objects) {
        return objects2traces(Arrays.asList(i_objects))
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
