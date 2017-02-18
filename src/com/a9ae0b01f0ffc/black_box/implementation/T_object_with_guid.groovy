package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_object_with_guid
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_object_with_guid extends T_logging_const implements I_object_with_guid {

    String p_guid = UUID.randomUUID()

    @Override
    @I_black_box_base("error")
    String get_guid() {
        return p_guid
    }


}
