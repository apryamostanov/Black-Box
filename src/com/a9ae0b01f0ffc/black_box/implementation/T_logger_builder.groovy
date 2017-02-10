package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader
import groovy.util.slurpersupport.GPathResult

class T_logger_builder implements I_logger_builder {

    GPathResult p_conf = T_logging_const.GC_NULL_OBJ_REF as GPathResult

    I_logger create_logger(String i_conf_file_name) {
        p_conf = (GPathResult) new XmlSlurper().parse(i_conf_file_name)
        I_logger l_logger = (I_logger) T_s.ioc().instantiate("I_logger")
        if (!p_conf.@mode.isEmpty()) {
            l_logger.set_mode(p_conf.@mode.text())
        }
        for (l_destination_xml in p_conf.children()) {
            l_logger.add_destination(init_destination(l_destination_xml as GPathResult))
        }
        return l_logger
    }

    I_destination init_destination(GPathResult i_destination_xml) {
        I_destination l_destination = (I_destination) T_s.ioc().instantiate(i_destination_xml.name())
        if (!i_destination_xml.@purpose.isEmpty()) {
            l_destination.set_destination_purpose(i_destination_xml.@purpose.text())
        } else {
            throw new E_application_exception(T_s.s().PURPOSE_IS_MANDATORY_FOR_DESTINATIONS, i_destination_xml.name())
        }
        if (!i_destination_xml.@formatter.isEmpty()) {
            I_event_formatter l_event_formatter = T_s.ioc().instantiate(i_destination_xml.@formatter.text()) as I_event_formatter
            if (!i_destination_xml.@guid.isEmpty()) {
                l_event_formatter.set_print_trace_guid(i_destination_xml.@guid.text())
            }
            l_destination.set_formatter(l_event_formatter)
        } else {
            throw new E_application_exception(T_s.s().FORMATTER_IS_MANDATORY_FOR_DESTINATIONS, i_destination_xml.name())
        }
        if (!i_destination_xml.@mask.isEmpty()) {
            l_destination.set_mask(i_destination_xml.@mask.text())
        }
        if (!i_destination_xml.@location.isEmpty()) {
            l_destination.set_location(i_destination_xml.@location.text())
        }
        if (!i_destination_xml.@buffer.isEmpty()) {
            l_destination.set_buffer(i_destination_xml.@buffer.text())
        }
        if (!i_destination_xml.@spool_event.isEmpty()) {
            l_destination.set_spool_event(i_destination_xml.@spool_event.text())
        }
        if (!i_destination_xml.@mask.isEmpty()) {
            l_destination.set_mask(i_destination_xml.@mask.text())
        }
        for (l_event_xml in i_destination_xml.children()) {
            l_destination.add_configuration_event(init_event((GPathResult) l_event_xml, l_destination))
        }
        return l_destination
    }

    I_event init_event(GPathResult i_event_xml, I_inherited_configurations i_inherited_configurations) {
        I_event l_event = (I_event) T_s.ioc().instantiate(i_event_xml.name())
        l_event.set_event_type(i_event_xml.name())
        if (!i_event_xml.@mask.isEmpty()) {
            l_event.set_mask(i_event_xml.@mask.text())
        } else {
            l_event.set_mask(i_inherited_configurations.get_mask())
        }
        for (l_trace_xml in i_event_xml.children()) {
            l_event.add_trace_config(init_trace((GPathResult) l_trace_xml, l_event))
        }
        return l_event
    }

    I_trace init_trace(GPathResult i_trace_xml, I_inherited_configurations i_inherited_configurations) {
        I_trace l_trace_config = (I_trace) T_s.ioc().instantiate("I_trace")
        l_trace_config.set_name(i_trace_xml.name())
        if (!i_trace_xml.@mask.isEmpty()) {
            l_trace_config.set_mask(i_trace_xml.@mask.text())
        } else {
            l_trace_config.set_mask(i_inherited_configurations.get_mask())
        }
        if (!i_trace_xml.@mute.isEmpty()) {
            l_trace_config.set_muted(i_trace_xml.@mute.asBoolean())
        }
        if (!i_trace_xml.@formatter.isEmpty()) {
            l_trace_config.set_formatter((I_trace_formatter) T_s.ioc().instantiate(i_trace_xml.@formatter.text()))
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
        return T_s.ioc()
    }
}
