package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.commons.implementation.ioc.T_class_loader
import groovy.util.slurpersupport.GPathResult

interface I_logger_builder {

    I_logger create_logger(String i_log_config_file_name)
    I_logger create_logger(GPathResult i_log_conf)
    T_class_loader get_class_loader()

}