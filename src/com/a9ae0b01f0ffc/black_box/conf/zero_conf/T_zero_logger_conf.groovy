package com.a9ae0b01f0ffc.black_box.conf.zero_conf

import groovy.util.slurpersupport.GPathResult

class T_zero_logger_conf  {

    static final String PC_DEFAULT_LOGGER_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<logger mode=\"diagnostic\">\n" +
            "    <file formatter=\"xml_hierarchical\" location=\"c:/LOGS/WAREHOUSE/COMPILE_LOG_%DATE%_%TIME%_%USERNAME%_%PROCESSID%.xml\" mask=\"sensitive\" purpose=\"display\">\n" +
            "        <enter>\n" +
            "            <datetimestamp/>\n" +
            "            <line/>\n" +
            "            <class formatter=\"short\"/>\n" +
            "            <runtime/>\n" +
            "            <this mute=\"true\"/>\n" +
            "        </enter>\n" +
            "        <result>\n" +
            "            <runtime/>\n" +
            "        </result>\n" +
            "        <exit/>\n" +
            "        <debug>\n" +
            "            <message formatter=\"message\"/>\n" +
            "        </debug>\n" +
            "        <info>\n" +
            "            <datetimestamp/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "        </info>\n" +
            "        <warning>\n" +
            "            <datetimestamp/>\n" +
            "            <message formatter=\"message\"/>\n" +
            "        </warning>\n" +
            "        <error>\n" +
            "            <datetimestamp/>\n" +
            "            <class/>\n" +
            "            <method/>\n" +
            "            <exception_message/>\n" +
            "            <exception_traces/>\n" +
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
