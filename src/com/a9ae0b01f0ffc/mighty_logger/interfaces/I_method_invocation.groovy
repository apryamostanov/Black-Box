package com.a9ae0b01f0ffc.mighty_logger.interfaces

interface I_method_invocation {

    String get_class_name()
    String get_method_name()
    String get_guid()

    I_trace[] get_method_arguments()

}