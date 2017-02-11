package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_inherited_configurations
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_inherited_configurations extends T_object_with_guid implements I_inherited_configurations {

    String p_mask = T_logging_const.GC_EMPTY_STRING

    @Override
    @I_black_box("error")
    String get_mask() {
        return p_mask
    }

    @Override
    @I_black_box("error")
    void set_mask(String i_mask) {
        p_mask = i_mask
    }

}
