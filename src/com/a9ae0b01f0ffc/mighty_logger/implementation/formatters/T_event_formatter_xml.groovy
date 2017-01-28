package com.a9ae0b01f0ffc.mighty_logger.implementation.formatters

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_event_formatter_xml implements I_event_formatter {

    static String attr(String i_attr_name, String i_attr_val) {
        return i_attr_name + "=\"" + i_attr_val + "\" "
    }


    Boolean p_header_was_added = T_s.c().GC_FALSE

    static String format_trace(I_trace i_trace) {
        String l_result = T_s.c().GC_EMPTY_STRING
        l_result += "<trace "
        l_result += attr("muted", i_trace.is_muted().toString())
        l_result += attr("masked", i_trace.is_masked().toString())
        l_result += attr("mask", i_trace.get_mask())
        l_result += attr("name", i_trace.get_name())
        l_result += attr("source", i_trace.get_source())
        l_result += attr("config_class", i_trace.get_config_class())
        l_result += attr("search_name_config", i_trace.get_search_name_config())
        l_result += attr("ref_class_name", i_trace.get_ref_class_name())
        l_result += attr("serialized_representation", i_trace.toString())
        l_result += "/>"
        return l_result
    }

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces) {
        String l_result = T_s.c().GC_EMPTY_STRING
        if (!p_header_was_added) {
            l_result += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                    "\n" +
                    "    <xsl:output method=\"html\" />\n" +
                    "\n" +
                    "    <xsl:template match=\"/root\">\n" +
                    "        <table>\n" +
                    "            <tbody>\n" +
                    "                <tr>\n" +
                    "                    <xsl:apply-templates select=\"row[1]\" mode=\"header\"/>\n" +
                    "                </tr>\n" +
                    "                <xsl:apply-templates select=\"row\" mode=\"rows\"/>\n" +
                    "            </tbody>\n" +
                    "        </table>\n" +
                    "    </xsl:template>\n" +
                    "\n" +
                    "    <xsl:template match=\"row\" mode=\"header\">\n" +
                    "        <xsl:for-each select=\"attribute::*\">\n" +
                    "            <th><xsl:value-of select=\"local-name(.)\" /></th>\n" +
                    "        </xsl:for-each>\n" +
                    "    </xsl:template>\n" +
                    "\n" +
                    "    <xsl:template match=\"row\" mode=\"rows\">\n" +
                    "        <tr>\n" +
                    "            <xsl:for-each select=\"attribute::*\">\n" +
                    "                <td><xsl:value-of select=\".\" /></td>\n" +
                    "            </xsl:for-each>\n" +
                    "        </tr>\n" +
                    "    </xsl:template>\n" +
                    "\n" +
                    "</xsl:stylesheet><traces>"
            p_header_was_added = T_s.c().GC_TRUE
        }
        l_result += "<event>"
        for (I_trace l_trace : i_event_traces) {
            l_result += format_trace(l_trace)
        }
        l_result += "</event>" + System.lineSeparator()
        return l_result
    }
}
