package com.a9ae0b01f0ffc.black_box.annotations

import com.a9ae0b01f0ffc.black_box.implementation.T_logger
import groovy.transform.ToString
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils

import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_4_const.getGC_BLACK_BOX_TYPE_FULL
import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.c
import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context.l
import static com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util.*
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.GC_NULL_OBJ_REF
import static com.a9ae0b01f0ffc.commons.implementation.main.T_common_base_1_const.GC_ZERO

@ToString(includeNames = true, includeFields = true)
class T_black_box_visitor extends CodeVisitorSupport {

    static
    final HashMap<String, String> PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS = ["log_info": "info", "log_debug": "debug", "log_warning": "warning", "log_receive_tcp": "receive_tcp", "log_trace": "traces"]
    static final String PC_AUTOREPLACEMENT_TARGET_FUNCTION = "log_generic_with_message"
    static final ArrayList<String> PC_LOGGING_FUNCTIONS_WITH_MESSAGE = ["log_info", "log_warning", "log_debug"]

    static final String PC_CLASS_NAME = "T_black_box_visitor"
    T_black_box_transformation p_black_box_transformation = GC_NULL_OBJ_REF as T_black_box_transformation


    T_black_box_visitor(T_black_box_transformation i_black_box_transformation) {
        p_black_box_transformation = i_black_box_transformation
    }

    @Override
    void visitDoWhileLoop(DoWhileStatement i_statement) {
        String l_orig_code = code2attribute(ast2text(i_statement.getLoopBlock()))
        super.visitDoWhileLoop(i_statement)
        Statement l_statement_to_modify = i_statement.getLoopBlock()
        i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration", l_orig_code))
    }

    @Override
    void visitCaseStatement(CaseStatement i_statement) {
        String l_orig_code_expr = code2attribute(ast2text(i_statement.getExpression()))
        String l_orig_code_stmt = code2attribute(ast2text(i_statement.getCode()))
        super.visitCaseStatement(i_statement)
        Expression l_expression_to_modify = i_statement.getExpression()
        i_statement.setExpression(p_black_box_transformation.decorate_expression(l_expression_to_modify, "case_expression", l_orig_code_expr))
        Statement l_statement_to_modify = i_statement.getCode()
        i_statement.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify, "case_code", l_orig_code_stmt))
    }

    @Override
    void visitSynchronizedStatement(SynchronizedStatement i_statement) {
        String l_orig_code_expr = code2attribute(ast2text(i_statement.getExpression()))
        String l_orig_code_stmt = code2attribute(ast2text(i_statement.getCode()))
        super.visitSynchronizedStatement(i_statement)
        Expression l_expression_to_modify = i_statement.getExpression()
        i_statement.setExpression(p_black_box_transformation.decorate_expression(l_expression_to_modify, "synchronized_expression", l_orig_code_expr))
        Statement l_statement_to_modify = i_statement.getCode()
        i_statement.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify, "synchronized__code", l_orig_code_stmt))
    }

    @Override
    void visitMethodCallExpression(MethodCallExpression i_method_call_expression) {
        final String LC_METHOD_NAME = "visitMethodCallExpression"
        T_logger l_logger = l()
        l_logger.log_enter_method(PC_CLASS_NAME, LC_METHOD_NAME, i_method_call_expression.getLineNumber(), r(i_method_call_expression, "i_method_call_expression"))
        try {
            HashMap<Expression, String> l_orig_codes = new HashMap<Expression, String>()
            for (Expression l_expression in ((ArgumentListExpression)i_method_call_expression.getArguments()).getExpressions()) {
                l_orig_codes.put(l_expression, code2attribute(ast2text(l_expression)))
            }
            String l_orig_receiver_code = code2attribute(ast2text(i_method_call_expression.getObjectExpression()))
            super.visitMethodCallExpression(i_method_call_expression)
            MethodCallExpression l_method_call_expression = i_method_call_expression
            if (PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.keySet().contains(l_method_call_expression.getMethodAsString())) {
                Expression l_orig_args = l_method_call_expression.getArguments()
                ArrayList<Expression> l_modified_args = new ArrayList<Expression>()
                if (l_orig_args instanceof ArgumentListExpression) {
                    l_modified_args.add(GeneralUtils.constX(PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.get(l_method_call_expression.getMethodAsString())))
//event type
                    l_modified_args.add(GeneralUtils.constX(l_method_call_expression.getLineNumber()))
                    if (PC_LOGGING_FUNCTIONS_WITH_MESSAGE.contains(l_method_call_expression.getMethodAsString())) {
                        l_modified_args.add(l_orig_args.getExpressions().first()) //message
                    }
                    for (Expression l_argument in l_orig_args.getExpressions()) {
                        if (l_argument == l_orig_args.getExpressions().first()) {
                            if (!PC_LOGGING_FUNCTIONS_WITH_MESSAGE.contains(l_method_call_expression.getMethodAsString())) {
                                l_modified_args.add(l_orig_args.getExpressions().first()) //not a message
                            }
                        } else {
                            l_modified_args.add(GeneralUtils.callX(l_method_call_expression.getObjectExpression(), "r", GeneralUtils.args(l_argument, GeneralUtils.constX(l_argument.getText()))))
                        }
                    }
                    MethodCallExpression l_new_method_call_expression = GeneralUtils.callX(l_method_call_expression.getObjectExpression(), PC_AUTOREPLACEMENT_TARGET_FUNCTION, GeneralUtils.args(l_modified_args))
                    l_method_call_expression.setMethod(l_new_method_call_expression.getMethod())
                    l_method_call_expression.setArguments(l_new_method_call_expression.getArguments())
                }
            } else {
                if (i_method_call_expression.getObjectExpression() instanceof MethodCallExpression) {
                    i_method_call_expression.setObjectExpression(p_black_box_transformation.decorate_expression(i_method_call_expression.getObjectExpression(), i_method_call_expression.getObjectExpression().getClass().getSimpleName(), l_orig_receiver_code))
                }
                ArrayList<Expression> l_expressions = new ArrayList<Expression>()
                for (Expression l_expression in ((ArgumentListExpression)i_method_call_expression.getArguments()).getExpressions()) {
                    l_expressions.add(p_black_box_transformation.decorate_expression(l_expression, "argument", l_orig_codes.get(l_expression)))
                }
                ((ArgumentListExpression)i_method_call_expression.getArguments()).getExpressions().clear()
                ((ArgumentListExpression)i_method_call_expression.getArguments()).getExpressions().addAll(l_expressions)
            }
        } catch (Throwable e_others) {
            l_logger.log_error_method(PC_CLASS_NAME, LC_METHOD_NAME, GC_ZERO, e_others)
            throw e_others
        } finally {
            l().log_exit_method()
        }
    }
/*
    @Override
    void visitArgumentlistExpression(ArgumentListExpression i_expression) {
        HashMap<Expression, String> l_orig_codes = new HashMap<Expression, String>()
        for (Expression l_expression in i_expression.getExpressions()) {
            l_orig_codes.put(l_expression, code2attribute(ast2text(l_expression)))
        }
        super.visitArgumentlistExpression(i_expression)
        ArrayList<Expression> l_expressions = new ArrayList<Expression>()
        for (Expression l_expression in i_expression.getExpressions()) {
            l_expressions.add(p_black_box_transformation.decorate_expression(l_expression, "argument", l_orig_codes.get(l_expression)))
        }
        i_expression.getExpressions().clear()
        i_expression.getExpressions().addAll(l_expressions)
    }*/

    @Override
    void visitBinaryExpression(BinaryExpression i_binary_expression) {
        super.visitBinaryExpression(i_binary_expression)
        String l_text = i_binary_expression.getRightExpression().getText()
        if (l_text.contains("\"") || l_text.contains("'") || l_text.contains("\n") || l_text.length() > new Integer(c().GC_MAX_CODE_LENGTH)) {
            l_text = i_binary_expression.getRightExpression().getType().getNameWithoutPackage()
        }
        if (!(i_binary_expression.getRightExpression() instanceof ConstantExpression || i_binary_expression.getRightExpression() instanceof ClassExpression || i_binary_expression.getRightExpression() instanceof EmptyExpression)) {
            i_binary_expression.setRightExpression(p_black_box_transformation.decorate_expression(i_binary_expression.getRightExpression(), i_binary_expression.getRightExpression().getClass().getSimpleName(), l_text))
        }
    }

    @Override
    void visitBlockStatement(BlockStatement i_statement) {
        HashMap<Statement, String> l_orig_codes_map = new HashMap<Statement, String>()
        for (l_statement_to_modify in i_statement.getStatements()) {
            l_orig_codes_map.put(l_statement_to_modify, code2attribute(ast2text(l_statement_to_modify)))
        }
        super.visitBlockStatement(i_statement)
        ArrayList<Statement> l_modified_statements = new ArrayList<Statement>()
        for (l_statement_to_modify in i_statement.getStatements()) {
            if (l_statement_to_modify instanceof ForStatement) {
                l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "for", l_orig_codes_map.get(l_statement_to_modify)))
            } else if (l_statement_to_modify instanceof WhileStatement) {
                l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "while", l_orig_codes_map.get(l_statement_to_modify)))
            } else if (l_statement_to_modify instanceof DoWhileStatement) {
                l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "do_while", l_orig_codes_map.get(l_statement_to_modify)))
            } else if (l_statement_to_modify instanceof IfStatement) {
                l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, "if", l_orig_codes_map.get(l_statement_to_modify)))
            } else if (l_statement_to_modify instanceof ExpressionStatement) {
                if (l_statement_to_modify.getExpression() instanceof MethodCallExpression) {
                    MethodCallExpression l_method_call_expression = l_statement_to_modify.getExpression() as MethodCallExpression
                    if (!(PC_LOGGING_AUTOREPLACEMENT_FUNCTIONS.keySet().contains(l_method_call_expression.getMethodAsString()) || PC_AUTOREPLACEMENT_TARGET_FUNCTION == l_method_call_expression.getMethodAsString())) {
                        l_modified_statements.add(p_black_box_transformation.decorate_statement(l_statement_to_modify, l_method_call_expression.getClass().getSimpleName(), l_orig_codes_map.get(l_statement_to_modify)))
                    } else {
                        l_modified_statements.add(l_statement_to_modify)
                    }
                } else {
                    l_modified_statements.add(l_statement_to_modify)
                }
            } else {
                l_modified_statements.add(l_statement_to_modify) //todo: add warning here
            }
        }
        i_statement.getStatements().clear()
        i_statement.getStatements().addAll(l_modified_statements)
    }

    @Override
    void visitClosureExpression(ClosureExpression i_expression) {
        String l_orig_code = code2attribute(ast2text(i_expression))
        super.visitClosureExpression(i_expression)
        BlockStatement l_changed_block_statement = new BlockStatement()
        T_black_box_transformation l_black_box_transformation = new T_black_box_transformation()
        l_black_box_transformation.p_black_box_level = p_black_box_transformation.p_black_box_level
        l_black_box_transformation.p_class_name = p_black_box_transformation.p_class_name
        l_black_box_transformation.p_method_name = p_black_box_transformation.p_method_name
        l_black_box_transformation.set_annotation_node(p_black_box_transformation.get_annotation_node())
        l_changed_block_statement.addStatement(l_black_box_transformation.decorate_statement(i_expression.getCode(), i_expression.getClass().getSimpleName(), l_orig_code))
        i_expression.setCode(l_changed_block_statement)
    }

    @Override
    void visitForLoop(ForStatement i_statement) {
        String l_orig_code = code2attribute(ast2text(i_statement.getLoopBlock()))
        super.visitForLoop(i_statement)
        Statement l_statement_to_modify = i_statement.getLoopBlock()
        i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration", l_orig_code))
    }

    @Override
    void visitReturnStatement(ReturnStatement i_return_statement) {
        //todo return # number
        super.visitReturnStatement(i_return_statement)
        if (GC_BLACK_BOX_TYPE_FULL == p_black_box_transformation.p_black_box_level) {
            i_return_statement.setExpression(new MethodCallExpression(new VariableExpression("l_logger"), "log_result", new ArgumentListExpression(new MethodCallExpression(new VariableExpression("l_util"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText()))))))
        }
    }

    @Override
    void visitWhileLoop(WhileStatement i_statement) {
        String l_orig_code = code2attribute(ast2text(i_statement.getLoopBlock()))
        super.visitWhileLoop(i_statement)
        Statement l_statement_to_modify = i_statement.getLoopBlock()
        i_statement.setLoopBlock(p_black_box_transformation.decorate_statement(l_statement_to_modify, "iteration", l_orig_code))
    }

    @Override
    void visitIfElse(IfStatement i_statement) {
        String l_orig_code = code2attribute(ast2text(i_statement))
        super.visitIfElse(i_statement)
        i_statement.setIfBlock(p_black_box_transformation.decorate_statement(i_statement.getIfBlock(), "then", l_orig_code))
        i_statement.setElseBlock(p_black_box_transformation.decorate_statement(i_statement.getElseBlock(), "else", l_orig_code))
    }

    @Override
    void visitSwitch(SwitchStatement i_statement) {
        HashMap<Statement, String> l_orig_codes = new HashMap<Statement, String>()
        String l_orig_code_default = code2attribute(ast2text(i_statement.getDefaultStatement()))
        for (l_statement_to_modify in i_statement.getCaseStatements()) {
            l_orig_codes.put(l_statement_to_modify.getCode(), code2attribute(ast2text(l_statement_to_modify.getCode())))
        }
        super.visitSwitch(i_statement)
        for (l_statement_to_modify in i_statement.getCaseStatements()) {
            l_statement_to_modify.setCode(p_black_box_transformation.decorate_statement(l_statement_to_modify.getCode(), "case", l_orig_codes.get(l_statement_to_modify.getCode())))
        }
        i_statement.setDefaultStatement(p_black_box_transformation.decorate_statement(i_statement.getDefaultStatement(), "default", l_orig_code_default))
    }

}
