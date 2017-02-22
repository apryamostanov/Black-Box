package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString
import groovy.util.slurpersupport.GPathResult

@ToString(includeNames = true, includeFields = true)
class T_logger_builder_default extends T_logger_builder {

    static final String PC_DEFAULT_LOGGER_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<logger mode=\"diagnostic\">\n" +
            "    <file formatter=\"xml_hierarchical\" location=\"c:/LOGS/WAREHOUSE/COMPILE_LOG_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.xml\" mask=\"sensitive\" purpose=\"display\">\n" +
            "        <enter>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <depth/>\n" +
            "            <runtime/>\n" +
            "        </enter>\n" +
            "        <exit>\n" +
            "            <runtime/>\n" +
            "        </exit>\n" +
            "        <debug>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <depth/>\n" +
            "            <event/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "            <runtime/>\n" +
            "        </debug>\n" +
            "        <warning>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <depth/>\n" +
            "            <event/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "            <runtime/>\n" +
            "        </warning>\n" +
            "        <info>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <depth/>\n" +
            "            <event/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "            <runtime/>\n" +
            "        </info>\n" +
            "        <error>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <depth/>\n" +
            "            <event/>\n" +
            "            <exception/>\n" +
            "            <exception_message/>\n" +
            "            <exception_traces/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "            <runtime/>\n" +
            "        </error>\n" +
            "    </file>\n" +
            "    <shell formatter=\"csv\" mask=\"sensitive\" purpose=\"display\">\n" +
            "        <info>\n" +
            "            <message formatter=\"message\"/>\n" +
            "        </info>\n" +
            "        <warning>\n" +
            "            <event/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "        </warning>\n" +
            "        <error>\n" +
            "            <event/>\n" +
            "            <depth/>\n" +
            "            <exception_message/>\n" +
            "            <exception_traces/>\n" +
            "        </error>\n" +
            "    </shell>\n" +
            "</logger>"

    @Override
    @I_black_box_base("error")
    I_logger create_logger(String i_conf_file_name) {
        p_conf = (GPathResult) new XmlSlurper().parseText(PC_DEFAULT_LOGGER_CONFIG)
        I_logger l_logger = (I_logger) T_s.ioc().instantiate("I_logger")
        if (!p_conf.@mode.isEmpty()) {
            l_logger.set_mode(p_conf.@mode.text())
        }
        for (l_destination_xml in p_conf.children()) {
            l_logger.add_destination(init_destination(l_destination_xml as GPathResult))
        }
        return l_logger
    }

}
