package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.implementation.formatters.T_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import groovy.transform.ToString
import groovy.util.slurpersupport.GPathResult

@ToString(includeNames = true, includeFields = true)
class T_logger_builder extends T_logging_base_6_util {

    GPathResult p_conf = GC_NULL_OBJ_REF as GPathResult

    T_logger create_logger(GPathResult i_log_conf, String i_commons_conf_file_name) {
        p_conf = i_log_conf
        T_logger l_logger = new T_logger()
        for (l_destination_xml in p_conf.children()) {
            l_logger.add_destination(init_destination(l_destination_xml as GPathResult, i_commons_conf_file_name))
        }
        return l_logger
    }

    T_logger create_logger(String i_conf_file_name, String i_commons_conf_file_name) {
        T_logger l_logger = create_logger((GPathResult) new XmlSlurper().parse(i_conf_file_name), i_commons_conf_file_name)
        l_logger.set_commons_conf_file_name(i_commons_conf_file_name)
        return l_logger
    }

    static T_destination init_destination(GPathResult T_destination_xml, String i_commons_conf_file_name) {
        T_destination l_destination = (T_destination) get_ioc().instantiate(T_destination_xml.name())
        if (!T_destination_xml.@formatter.isEmpty()) {
            T_event_formatter l_event_formatter = get_ioc().instantiate(T_destination_xml.@formatter.text()) as T_event_formatter
            l_event_formatter.set_parent_destination(l_destination)
            l_destination.set_formatter(l_event_formatter)
        } else {
            throw new E_application_exception(s.FORMATTER_IS_MANDATORY_FOR_DESTINATIONS, T_destination_xml.name())
        }
        if (!T_destination_xml.@location.isEmpty()) {
            l_destination.set_location(T_destination_xml.@location.text())
        }
        if (!T_destination_xml.@async.isEmpty()) {
            if (T_destination_xml.@async.text() == GC_TRUE_STRING) {
                l_destination.set_async_storer(new T_async_storage(l_destination.clone_with_no_async(), i_commons_conf_file_name, Thread.currentThread()))
                if (T_destination_xml.@async_mode.text() != GC_ASYNC_MODE_FLUSH) {
                    l_destination.p_async_storage.start()
                }
            }
        }
        for (l_event_xml in T_destination_xml.children()) {
            l_destination.add_configuration_event(init_event((GPathResult) l_event_xml))
        }
        return l_destination
    }

    static T_event init_event(GPathResult i_event_xml) {
        T_event l_event = new T_event()
        l_event.set_event_type(i_event_xml.name())
        return l_event
    }

}
