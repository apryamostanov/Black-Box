package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.commons.main.T_common_const
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ReturnStatement

@ToString(includeNames = true, includeFields = true)
class T_black_box_return_visitor extends CodeVisitorSupport {

    static final String PC_CLASS_NAME = "T_black_box_return_visitor"
    Boolean p_is_log_exit_added = T_common_const.GC_FALSE
    Boolean p_is_profile_only = T_common_const.GC_FALSE
    //todo return # number

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, "visitReturnStatement", T_s.r(i_return_statement, "i_return_statement"), T_s.r(this, "this"))
        try {
            if (!p_is_profile_only) {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_exit_automatic", new ArgumentListExpression(new VariableExpression("l_classname"), new VariableExpression("l_methodname"), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
            } else {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "profile_exit_automatic", new ArgumentListExpression(new VariableExpression("l_classname"), new VariableExpression("l_methodname"), i_return_statement.getExpression())))
            }
            p_is_log_exit_added = T_common_const.GC_TRUE
            super.visitReturnStatement(i_return_statement)
            T_s.l().log_exit(PC_CLASS_NAME, "visitReturnStatement")
        } catch (Throwable e_others) {
            l_logger.log_exception(PC_CLASS_NAME, "create_profile_enter_statement", e_others)
            throw e_others
        }
    }

    void set_profile_only() {
        p_is_profile_only = T_common_const.GC_TRUE
    }

}
