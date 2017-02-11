package com.a9ae0b01f0ffc.black_box.implementation.annotations

import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ReturnStatement

class T_black_box_visitor_base extends CodeVisitorSupport {

    Boolean p_is_return_added = false

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_exit_automatic", new ArgumentListExpression(new VariableExpression("l_classname"), new VariableExpression("l_methodname"), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
        p_is_return_added = true
        super.visitReturnStatement(i_return_statement)
    }

}
