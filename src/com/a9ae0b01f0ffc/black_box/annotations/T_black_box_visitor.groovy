package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends CodeVisitorSupport {

    static final String PC_CLASS_NAME = "T_black_box_visitor"
    T_black_box_transformation p_black_box_transformation = T_logging_const.GC_NULL_OBJ_REF as T_black_box_transformation
    String p_class_name = T_logging_const.GC_EMPTY_STRING
    String p_method_name = T_logging_const.GC_EMPTY_STRING
    String p_statement_name = T_logging_const.GC_EMPTY_STRING
    String p_black_box_type = T_logging_const.GC_EMPTY_STRING
    Parameter[] p_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]

    T_black_box_visitor(T_black_box_transformation i_black_box_transformation, String i_class_name, String i_method_name, String i_statement_name, String i_black_box_type, Parameter[] i_parameters = T_logging_const.GC_SKIPPED_ARGS as Parameter[]) {
        p_black_box_transformation = i_black_box_transformation
        p_method_name = i_method_name
        p_class_name = i_class_name
        p_statement_name = i_statement_name
        p_black_box_type = i_black_box_type
        p_parameters = i_parameters
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        final String LC_METHOD_NAME = "visitBlockStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            for (l_statement_to_visit in i_statement.getStatements()) {
                l_statement_to_visit.visit(this)
            }
            ArrayList<Statement> l_modified_statements = new ArrayList<Statement>()
            for (l_statement_to_modify in i_statement.getStatements()) {
                if (l_statement_to_modify instanceof ForStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "for", p_black_box_type, p_parameters))
                } else if (l_statement_to_modify instanceof WhileStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "while", p_black_box_type, p_parameters))
                } else if (l_statement_to_modify instanceof IfStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "if", p_black_box_type, p_parameters))
                } else if (l_statement_to_modify instanceof ExpressionStatement) {
                    if (l_statement_to_modify.getExpression() instanceof DeclarationExpression) {
                        l_modified_statements.add(GeneralUtils.declS(((DeclarationExpression)l_statement_to_modify.getExpression()).getVariableExpression(), new EmptyExpression()))
                        Statement l_modified_statement = p_black_box_transformation.decorate_statement(GeneralUtils.assignS(((DeclarationExpression)l_statement_to_modify.getExpression()).getVariableExpression(), ((DeclarationExpression)l_statement_to_modify.getExpression()).getRightExpression()), p_class_name, p_method_name, "declaration", p_black_box_type, p_parameters)
                        l_modified_statement.setLineNumber(l_statement_to_modify.getLineNumber())
                        l_modified_statements.add(l_modified_statement)
                    } else if (l_statement_to_modify.getExpression() instanceof MethodCallExpression) {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, ((MethodCallExpression)l_statement_to_modify.getExpression()).getMethodAsString(), p_black_box_type, p_parameters))
                    } else if (l_statement_to_modify.getExpression() instanceof StaticMethodCallExpression) {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, ((StaticMethodCallExpression)l_statement_to_modify.getExpression()).getMethodAsString(), p_black_box_type, p_parameters))
                    } else {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, l_statement_to_modify.getExpression().getClass().getSimpleName(), p_black_box_type, p_parameters))
                    }
                } else if (l_statement_to_modify instanceof SwitchStatement) {
                    l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "switch", p_black_box_type, p_parameters))
                } else {
                    l_modified_statements.add(l_statement_to_modify)
                }
            }
            i_statement.getStatements().clear()
            i_statement.getStatements().addAll(l_modified_statements)
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitClosureExpression(ClosureExpression i_statement) {
        final String LC_METHOD_NAME = "visitClosureExpression"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            super.visitClosureExpression(i_statement)
            BlockStatement l_changed_block_statement = new BlockStatement()
            l_changed_block_statement.addStatement(p_black_box_transformation.create_shortcut_declaration_statement())
            l_changed_block_statement.addStatement(p_black_box_transformation.create_logger_declaration_statement())
            l_changed_block_statement.addStatement(p_black_box_transformation.create_l_methodname_declaration_statement(p_method_name))
            l_changed_block_statement.addStatement(p_black_box_transformation.create_l_classname_declaration_statement(p_class_name))
            l_changed_block_statement.addStatement(p_black_box_transformation.decorate_statement(i_statement.getCode(), p_class_name, p_method_name, "closure", p_black_box_type, p_parameters))
            i_statement.setCode(l_changed_block_statement)
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        final String LC_METHOD_NAME = "visitForLoop"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            super.visitForLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "iteration", p_black_box_type, p_parameters))
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        //todo return # number
        final String LC_METHOD_NAME = "visitReturnStatement"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_return_statement, "i_return_statement"), T_s.r(this, "this"))
        try {
            if ([T_logging_const.GC_BLACK_BOX_TYPE_FULL, T_logging_const.GC_BLACK_BOX_TYPE_INVOCATION].contains(p_black_box_type)) {
                i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
            }
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitWhileLoop(WhileStatement i_statement) {
        final String LC_METHOD_NAME = "visitWhileLoop"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            super.visitWhileLoop(i_statement)
            Statement l_statement_to_modify = i_statement.getLoopBlock()
            i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, p_class_name, p_method_name, "iteration", p_black_box_type, p_parameters))
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitIfElse(IfStatement i_statement) {
        final String LC_METHOD_NAME = "visitIfElse"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            super.visitIfElse(i_statement)
            i_statement.setIfBlock(p_black_box_transformation.decorate_statement(i_statement.getIfBlock(), p_class_name, p_method_name, "then", p_black_box_type, p_parameters))
            i_statement.setElseBlock(p_black_box_transformation.decorate_statement(i_statement.getElseBlock(), p_class_name, p_method_name, "else", p_black_box_type, p_parameters))
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

    @Override
    void visitSwitch(SwitchStatement i_statement) {
        final String LC_METHOD_NAME = "visitSwitch"
        I_logger l_logger = T_s.l()
        l_logger.log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_logging_const.GC_STATEMENT_NAME_METHOD, T_logging_const.GC_ZERO, T_s.r(i_statement, "i_statement"), T_s.r(this, "this"))
        try {
            super.visitSwitch(i_statement)
            for (l_statement_to_modify in i_statement.getCaseStatements()) {
                l_statement_to_modify.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify.getCode(), p_class_name, p_method_name, "case", p_black_box_type, p_parameters))
            }
            i_statement.setDefaultStatement(p_black_box_transformation.decorate_statement(i_statement.getDefaultStatement(), p_class_name, p_method_name, "default", p_black_box_type, p_parameters))
            T_s.l().log_result(T_s.r(i_statement.getText(), "modified_statement_text"))
        } catch (Throwable e_others) {
            l_logger.log_error(e_others)
            throw e_others
        } finally {
            T_s.l().log_exit()
        }
    }

}
