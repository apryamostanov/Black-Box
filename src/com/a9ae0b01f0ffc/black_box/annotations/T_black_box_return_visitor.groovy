package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_s
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
    //todo return # number

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        final String LC_METHOD_NAME = "visitReturnStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(i_return_statement, "i_return_statement"), T_s.r(this, "this"))
        try {
            i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new VariableExpression("l_classname"), new VariableExpression("l_methodname"), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
        } catch (Throwable e_others) {
            l_logger.log_error(PC_CLASS_NAME, LC_METHOD_NAME, e_others)
            throw e_others
        } finally {
            T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME)
        }
    }

}
