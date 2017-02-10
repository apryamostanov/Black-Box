package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_inherited_configurations
import com.a9ae0b01f0ffc.black_box.main.T_const

class T_inherited_configurations extends T_object_with_guid implements I_inherited_configurations {

    String p_mask = T_const.GC_EMPTY_STRING

    @Override
    String get_mask() {
        return p_mask
    }

    @Override
    void set_mask(String i_mask) {
        p_mask = i_mask
    }

}
