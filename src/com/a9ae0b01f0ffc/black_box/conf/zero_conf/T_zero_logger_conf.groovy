package com.a9ae0b01f0ffc.black_box.conf.zero_conf

import groovy.util.slurpersupport.GPathResult

class T_zero_logger_conf  {

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

    static final GPathResult PC_LOGGER_CONF = (GPathResult) new XmlSlurper().parseText(PC_DEFAULT_LOGGER_CONFIG)

}
