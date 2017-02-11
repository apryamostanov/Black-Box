package com.a9ae0b01f0ffc.black_box.implementation.annotations

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.TryCatchStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation extends T_black_box_transformation_base {

    private T_black_box_visitor_base p_return_expression_visitor = new T_black_box_visitor()

    @Override
    @I_black_box_base
    T_black_box_visitor_base get_return_expression_visitor() {
        return p_return_expression_visitor
    }

    @Override
    @I_black_box_base
    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        super.visit(i_ast_nodes, i_source_unit)
    }

    @Override
    @I_black_box_base
    Statement create_l_methodname_declaration_statement(String i_methodname) {
        return super.create_l_methodname_declaration_statement(i_methodname)
    }

    @Override
    @I_black_box_base
    Statement create_l_classname_declaration_statement(String i_classname) {
        return super.create_l_classname_declaration_statement(i_classname)
    }

    @Override
    @I_black_box_base
    Statement create_logger_declaration_statement() {
        return super.create_logger_declaration_statement()
    }

    @Override
    @I_black_box_base
    Statement create_shortcut_declaration_statement() {
        return super.create_shortcut_declaration_statement()
    }

    @Override
    @I_black_box_base
    Statement create_log_enter_statement(MethodNode i_method_node) {
        return super.create_log_enter_statement(i_method_node)
    }

    @Override
    @I_black_box_base
    Statement create_log_exit_statement(MethodNode i_method_node) {
        return super.create_log_exit_statement(i_method_node)
    }

    @Override
    @I_black_box_base
    Statement create_log_error_statement(MethodNode i_method_node, String i_log_function_name) {
        return super.create_log_error_statement(i_method_node, i_log_function_name)
    }

    @Override
    @I_black_box_base
    TryCatchStatement create_try_catch_statement(BlockStatement i_block_statement, AnnotationNode i_annotation_node, MethodNode i_method_node, String i_log_function_name) {
        return super.create_try_catch_statement(i_block_statement, i_annotation_node, i_method_node, i_log_function_name)
    }

    @Override
    @I_black_box_base
    Statement rethrow(AnnotationNode i_annotation_node) {
        return super.rethrow(i_annotation_node)
    }
}
