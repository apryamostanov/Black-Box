package com.a9ae0b01f0ffc.black_box.implementation.annotations

import groovy.transform.ToString
import org.codehaus.groovy.ast.stmt.ReturnStatement

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends T_black_box_visitor_base {

    @Override
    @I_black_box_base
    void visitReturnStatement(ReturnStatement i_return_statement) {
        super.visitReturnStatement(i_return_statement)
    }

}
