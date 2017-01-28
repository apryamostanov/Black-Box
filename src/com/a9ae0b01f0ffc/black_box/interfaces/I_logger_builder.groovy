package com.a9ae0b01f0ffc.black_box.interfaces

import com.a9ae0b01f0ffc.implementation.T_class_loader

interface I_logger_builder {

    I_logger create_logger(String i_log_config_file_name)
    T_class_loader get_class_loader()

}