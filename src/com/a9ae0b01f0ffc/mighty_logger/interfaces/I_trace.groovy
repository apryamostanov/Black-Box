package com.a9ae0b01f0ffc.mighty_logger.interfaces

interface I_trace {

    Boolean is_muted()

    Boolean is_masked()

    void set_muted(Boolean i_is_muted)

    void set_masked(Boolean i_is_masked)

    void set_formatter(I_trace_formatter i_trace_formatter)

    I_trace_formatter get_formatter()

    Object get_ref()

    void set_ref(Object i_ref)

    String get_val()

    void set_val(String i_val)

    String get_name()

    void set_name(String i_name)

    void set_source(String i_source)

    String get_source()

    void set_class(String i_class)

    String get_class()

    String get_search_name_config()

    String get_class_name_ref()

}