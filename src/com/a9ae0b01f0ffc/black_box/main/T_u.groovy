package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.main.T_common_utils

class T_u extends T_common_utils {

    @I_black_box_base("error")
    static String escape_xml(String i_unescaped_xml_string) {
        if (i_unescaped_xml_string.contains(T_logging_const.GC_XML_DOUBLE_QUOTE)
                || i_unescaped_xml_string.contains(T_logging_const.GC_XML_DOUBLE_QUOTE)
                || i_unescaped_xml_string.contains(T_logging_const.GC_XML_LESS)
                || i_unescaped_xml_string.contains(T_logging_const.GC_XML_GREATER)
                || i_unescaped_xml_string.contains(T_logging_const.GC_XML_AMP)) {
            return T_logging_const.GC_XML_CDATA_OPEN + i_unescaped_xml_string + T_logging_const.GC_XML_CDATA_CLOSE
        } else {
            return i_unescaped_xml_string
        }
    }

}
