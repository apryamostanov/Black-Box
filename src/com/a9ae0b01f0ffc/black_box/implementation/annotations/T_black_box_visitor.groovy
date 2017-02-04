package com.a9ae0b01f0ffc.black_box.implementation.annotations

import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.control.SourceUnit

class T_black_box_visitor extends CodeVisitorSupport {

    File p_compilation_log_file = new File("c:/LOGS/log_file_name")
    FileWriter p_file_writer = new FileWriter(p_compilation_log_file, true)
    Boolean p_is_return_added = false

    void log(String i_message) {
        p_file_writer.write(new Date().format("yyyy-MM-dd HH:mm:ss:SS") + " : " + i_message + System.lineSeparator())
        p_file_writer.flush()
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        log("Visiting return statement")
        //i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_exit_automatic", new ArgumentListExpression(new ConstantExpression("classname"), new ConstantExpression("methodname"), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
        i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_exit_automatic", new ArgumentListExpression(new VariableExpression("l_classname"), new VariableExpression("l_methodname"), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
        p_is_return_added = true
        super.visitReturnStatement(i_return_statement)
    }

}
