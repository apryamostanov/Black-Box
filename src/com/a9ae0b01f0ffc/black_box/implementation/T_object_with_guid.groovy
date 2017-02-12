package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.interfaces.I_object_with_guid
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_object_with_guid implements I_object_with_guid{

    String p_guid = UUID.randomUUID()

    @Override
    @I_black_box("error")
    String get_guid() {
        return p_guid
    }
}
