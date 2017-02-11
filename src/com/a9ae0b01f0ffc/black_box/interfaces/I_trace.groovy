package com.a9ae0b01f0ffc.black_box.interfaces

interface I_trace extends I_inherited_configurations, I_object_with_guid {

    Boolean is_muted()

    Boolean is_masked()

    void set_muted(Boolean i_is_muted)

    void set_mask(String i_mask)

    String get_mask()

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

    String get_config_class()

    String get_search_name_config()

    String get_ref_class_name()

    Boolean match_trace(I_trace i_trace_new)

    String get_ref_guid()

    String format_trace(I_event i_source_event)

}