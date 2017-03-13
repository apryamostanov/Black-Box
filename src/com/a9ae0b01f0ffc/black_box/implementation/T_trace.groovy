package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.commons.interfaces.I_object_with_uid
import groovy.transform.Memoized

class T_trace extends T_inherited_configurations implements I_trace {

    I_trace_formatter p_trace_formatter = GC_NULL_OBJ_REF as I_trace_formatter
    String p_name = GC_EMPTY_STRING
    Object p_ref = GC_NULL_OBJ_REF
    String p_value = GC_EMPTY_STRING
    String p_source = GC_EMPTY_STRING
    String p_config_class = GC_EMPTY_STRING

    @Override
    Boolean is_masked() {
        if (p_mask == GC_EMPTY_STRING || p_mask == GC_FALSE_STRING || p_mask == GC_TRACE_MASK_NONE) {
            return GC_FALSE
        } else {
            return GC_TRUE
        }
    }

    @Override
    String format_trace(I_event i_source_event) {
        String l_result_string = GC_EMPTY_STRING
        if (get_formatter() != GC_NULL_OBJ_REF) {
            l_result_string += get_formatter().format_trace(this, i_source_event)
        } else {
            l_result_string += to_string()
        }
        return l_result_string
    }

    @Override
    String toString() {
        String l_result_string = GC_EMPTY_STRING
        l_result_string += to_string()
        return l_result_string
    }

    private String unmasked() {
        return nvl(p_value, p_ref.toString())
    }

    private String masked() {
        if (p_ref instanceof I_maskable) {
            if (p_value != GC_EMPTY_STRING) {
                return c().GC_DEFAULT_TRACE_MASKED
//there is no control on how objects are serialized, thus it is unknown how to mask their serialized representation.
            } else {
                return ((I_maskable) p_ref).to_string_masked(p_mask)
            }
        } else {
            return c().GC_DEFAULT_TRACE_MASKED
        }
    }

    private Boolean is_trace_missing() {
        return (p_ref == GC_NULL_OBJ_REF && (p_value == GC_NULL_OBJ_REF || p_value == GC_EMPTY_STRING || p_value == c().GC_DEFAULT_TRACE))
    }

    private String to_string() {
        if (is_trace_missing()) {
            return c().GC_DEFAULT_TRACE
        } else if (is_masked()) {
            if (p_mask == GC_TRACE_MASK_ALL || p_mask == GC_TRUE_STRING) {
                masked()
            } else if (p_mask == GC_TRACE_MASK_SENSITIVE) {
                if (p_ref instanceof I_sensitive) {
                    masked()
                } else {
                    return unmasked()
                }
            } else if (p_mask == GC_TRACE_MASK_ALL_EXCEPT_NON_SENSITIVE) {
                if (p_ref instanceof I_non_sensitive) {
                    return unmasked()
                } else {
                    return masked()
                }
            } else {
                return masked() // custom mask parameter, e.g. "last4digits"
            }
        } else {
            return unmasked()
        }
    }

    @Override
    void set_formatter(I_trace_formatter i_trace_formatter) {
        p_trace_formatter = i_trace_formatter
    }

    @Override
    I_trace_formatter get_formatter() {
        return p_trace_formatter
    }

    @Override
    String get_name() {
        return p_name
    }

    @Memoized
    String get_search_name_config() {
        String l_result
        if (get_ref() != GC_NULL_OBJ_REF && p_config_class != GC_EMPTY_STRING) {
            l_result = get_ref_class_name()
        } else if (get_ref() == GC_NULL_OBJ_REF && p_config_class != GC_EMPTY_STRING) {
            l_result = p_config_class
        } else {
            l_result = p_name
        }
        return l_result
    }

    @Memoized
    String get_ref_class_name() {
        String l_result = GC_EMPTY_STRING
        if (get_ref() != GC_NULL_OBJ_REF) {
            l_result = get_ref().getClass().getCanonicalName()
        }
        return l_result
    }

    @Override
    void set_name(String i_name) {
        p_name = i_name
    }

    @Override
    Object get_ref() {
        return p_ref
    }

    @Override
    void set_ref(Object i_ref) {
        p_ref = i_ref
    }

    @Override
    void set_val(String i_value) {
        p_value = i_value
    }

    @Override
    String get_val() {
        return p_value
    }

    @Override
    void set_source(String i_source) {
        p_source = i_source
    }

    @Override
    String get_source() {
        return p_source
    }

    @Override
    void set_class(String i_class) {
        p_config_class = i_class
    }

    @Override
    String get_config_class() {
        return p_config_class
    }

    @Memoized
    Boolean match_trace(String i_ref_class_name, String i_name) {
        return (get_search_name_config() == nvl(i_ref_class_name, i_name) || get_search_name_config() == i_name)
    }

    @Override
    String get_ref_guid() {
        if (p_ref instanceof I_object_with_uid) {
            return p_ref.get_guid()
        } else {
            return GC_EMPTY_STRING
        }
    }

}
