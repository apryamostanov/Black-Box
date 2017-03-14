package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.ioc.T_class_loader
import groovy.transform.ToString
import groovy.util.slurpersupport.GPathResult

@ToString(includeNames = true, includeFields = true)
class T_logger_builder extends T_logging_base_6_util implements I_logger_builder {

    GPathResult p_conf = GC_NULL_OBJ_REF as GPathResult

    I_logger create_logger(GPathResult i_log_conf) {
        p_conf = i_log_conf
        I_logger l_logger = (I_logger) get_ioc().instantiate("I_logger")
        for (l_destination_xml in p_conf.children()) {
            l_logger.add_destination(init_destination(l_destination_xml as GPathResult))
        }
        return l_logger
    }

    I_logger create_logger(String i_conf_file_name) {
        return create_logger((GPathResult) new XmlSlurper().parse(i_conf_file_name))
    }

    I_destination init_destination(GPathResult i_destination_xml) {
        I_destination l_destination = (I_destination) get_ioc().instantiate(i_destination_xml.name())
        if (!i_destination_xml.@formatter.isEmpty()) {
            I_event_formatter l_event_formatter = get_ioc().instantiate(i_destination_xml.@formatter.text()) as I_event_formatter
            l_event_formatter.set_parent_destination(l_destination)
            l_destination.set_formatter(l_event_formatter)
        } else {
            throw new E_application_exception(s.FORMATTER_IS_MANDATORY_FOR_DESTINATIONS, i_destination_xml.name())
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
        l_destination.init()
        return l_destination
    }

    I_event init_event(GPathResult i_event_xml, I_inherited_configurations i_inherited_configurations) {
        I_event l_event = (I_event) get_ioc().instantiate(i_event_xml.name())
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
        I_trace l_trace_config = (I_trace) get_ioc().instantiate("I_trace")
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
            l_trace_config.set_formatter((I_trace_formatter) get_ioc().instantiate(i_trace_xml.@formatter.text()))
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
        return get_ioc()
    }
}
