package com.a9ae0b01f0ffc.mighty_logger.main

import com.a9ae0b01f0ffc.implementation.T_class_loader
import com.a9ae0b01f0ffc.mighty_conf.implementation.T_conf
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_destination
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace_formatter
import groovy.util.slurpersupport.GPathResult

class T_logger_builder implements I_logger_builder {

    T_class_loader p_class_loader = T_s.c().GC_NULL_OBJ_REF as T_class_loader
    GPathResult p_conf = T_s.c().GC_NULL_OBJ_REF as GPathResult

    T_logger_builder(String i_class_conf_file_name) {
        p_class_loader = new T_class_loader(i_class_conf_file_name)

    }

    I_logger create_logger(String i_conf_file_name) {
        p_conf = (GPathResult) new XmlSlurper().parse(i_conf_file_name)
        I_logger l_logger = (I_logger) p_class_loader.instantiate("I_logger")
        if (!p_conf.@mode.isEmpty()) {
            l_logger.set_mode(p_conf.@mode.text())
        }
        for (l_destination_xml in p_conf.children()) {
            l_logger.add_destination(init_destination(l_destination_xml as GPathResult))
        }
        return l_logger
    }

    I_destination init_destination(GPathResult i_destination_xml) {
        I_destination l_destination = (I_destination) p_class_loader.instantiate(i_destination_xml.name())
        l_destination.set_destination_purpose(i_destination_xml.@purpose.text())
        l_destination.set_formatter((I_event_formatter) p_class_loader.instantiate(i_destination_xml.@formatter.text()))
        l_destination.set_location(i_destination_xml.@location.text())
        for (l_event_xml in i_destination_xml.children()) {
            l_destination.add_configuration_event(init_event((GPathResult) l_event_xml))
        }
        return l_destination
    }

    I_event init_event(GPathResult i_event_xml) {
        I_event l_event = (I_event) p_class_loader.instantiate(i_event_xml.name())
        l_event.set_event_type(i_event_xml.name())
        if (!i_event_xml.@mute.isEmpty()) {
            l_event.set_muted(i_event_xml.@mute.asBoolean())
        }
        for (l_trace_xml in i_event_xml.children()) {
            l_event.add_trace_config(init_trace((GPathResult) l_trace_xml))
        }
        return l_event
    }

    I_trace init_trace(GPathResult i_trace_xml) {
        I_trace l_trace_config = (I_trace) p_class_loader.instantiate("I_trace")
        l_trace_config.set_name(i_trace_xml.name())
        if (!i_trace_xml.@mask.isEmpty()) {
            l_trace_config.set_masked(i_trace_xml.@mask.asBoolean())
        }
        if (!i_trace_xml.@mute.isEmpty()) {
            l_trace_config.set_muted(i_trace_xml.@mute.asBoolean())
        }
        if (!i_trace_xml.@formatter.isEmpty()) {
            l_trace_config.set_formatter((I_trace_formatter) p_class_loader.instantiate(i_trace_xml.@formatter.text()))
        }
        if (!i_trace_xml.@source.isEmpty()) {
            l_trace_config.set_source(i_trace_xml.@source.text())
        }
        if (!i_trace_xml.@class.isEmpty()) {
            l_trace_config.set_class(i_trace_xml.@class.text())
        }
        return l_trace_config
    }

    T_class_loader get_class_loader() {
        return p_class_loader
    }
}
