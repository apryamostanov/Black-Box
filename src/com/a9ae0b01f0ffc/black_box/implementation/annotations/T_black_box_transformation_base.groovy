package com.a9ae0b01f0ffc.black_box.implementation.annotations

import com.a9ae0b01f0ffc.black_box.main.T_s
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation_base extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    private T_black_box_visitor_base p_return_expression_visitor = new T_black_box_visitor_base()

    static {
        T_s.x().init_custom("C:/COMPILE/commons.conf")
    }

    T_black_box_visitor_base get_return_expression_visitor() {
        return p_return_expression_visitor
    }

    T_black_box_transformation_base() {
    }


    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        if (i_ast_nodes.length == 2 && i_ast_nodes[0] instanceof AnnotationNode && i_ast_nodes[1] instanceof AnnotatedNode) {
            ASTNode l_first_node = i_ast_nodes[1]
            Expression l_type_member = ((AnnotationNode) i_ast_nodes[0]).getMember("value")
            String l_black_box_type = "full"
            if (l_type_member != null) {
                l_black_box_type = l_type_member.getText()
            }
            T_s.l().log_debug(T_s.s().Black_box_type_Z1, l_black_box_type)
            if (l_first_node instanceof MethodNode) {
                MethodNode l_method_node = (MethodNode) l_first_node
                T_s.l().log_debug(T_s.s().Processing_method_Z1, l_method_node.getName())
                ArrayList<Statement> l_statements = new ArrayList<Statement>()
                Statement statement = l_method_node.getCode()
                if (statement instanceof BlockStatement) {
                    l_statements.addAll(((BlockStatement) statement).getStatements())
                }
                if (!l_statements.isEmpty()) {
                    BlockStatement l_changed_block_statement = new BlockStatement()
                    l_changed_block_statement.addStatement(create_shortcut_declaration_statement())
                    l_changed_block_statement.addStatement(create_logger_declaration_statement())
                    l_changed_block_statement.addStatement(create_l_methodname_declaration_statement(l_method_node.getName()))
                    l_changed_block_statement.addStatement(create_l_classname_declaration_statement(l_method_node.getDeclaringClass().getName()))
                    if (l_black_box_type != "error") {
                        l_changed_block_statement.addStatement(create_log_enter_statement(l_method_node))
                    }
                    BlockStatement l_inside_try = new BlockStatement()
                    T_black_box_visitor_base l_return_expression_visitor = get_return_expression_visitor()
                    l_return_expression_visitor.p_is_return_added = false
                    for (Statement l_return_statement in l_statements) {
                        if (l_black_box_type != "error") {
                            l_return_statement.visit(l_return_expression_visitor)
                        }
                        l_inside_try.addStatement(l_return_statement)
                    }
                    if (!l_return_expression_visitor.p_is_return_added) {
                        if (l_black_box_type != "error") {
                            l_inside_try.addStatement(create_log_exit_statement(l_method_node))
                        }
                    }
                    if (l_black_box_type != "error") {
                        l_changed_block_statement.addStatement(create_try_catch_statement(l_inside_try, (AnnotationNode) i_ast_nodes[0], l_method_node))
                    } else {
                        l_changed_block_statement.addStatement(create_try_catch_statement_error_only(l_inside_try, (AnnotationNode) i_ast_nodes[0], l_method_node))
                    }
                    l_method_node.setCode(l_changed_block_statement)
                    T_s.l().log_debug(T_s.s().Finished_Processing_method_Z1, l_method_node.getName())
                }
            } else {
                this.addError("@I_black_box should be applied to Methods.", l_first_node)
            }
        } else {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(i_ast_nodes))
        }
    }

    Statement create_l_methodname_declaration_statement(String i_methodname) {
        return new ExpressionStatement(new DeclarationExpression(new VariableExpression("l_methodname"), Token.newSymbol(Types.EQUAL, 0, 0), new ConstantExpression(i_methodname)))
    }

    Statement create_l_classname_declaration_statement(String i_classname) {
        return new ExpressionStatement(new DeclarationExpression(new VariableExpression("l_classname"), Token.newSymbol(Types.EQUAL, 0, 0), new ConstantExpression(i_classname)))
    }

    Statement create_logger_declaration_statement() {
        return new ExpressionStatement(new DeclarationExpression(new VariableExpression("l_logger"), Token.newSymbol(Types.EQUAL, 0, 0), new MethodCallExpression(new VariableExpression("l_shortcuts"), "l", new ArgumentListExpression())))
    }

    Statement create_shortcut_declaration_statement() {
        return new ExpressionStatement(new DeclarationExpression(new VariableExpression("l_shortcuts"), Token.newSymbol(Types.EQUAL, 0, 0), new ConstructorCallExpression(new ClassNode(T_s.class), new ArgumentListExpression())))
    }

    Statement create_log_enter_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_serialized_parameters = ""
        if (l_arguments.size() != 0) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += "," + "l_shortcuts.r(" + l_argument.getName() + ",\"" + l_argument.getName() + "\")"
            }
        }
        String l_log_enter_code = "l_logger.log_enter(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\"" + l_serialized_parameters + ", l_shortcuts.r(this, \"this\"))"
        T_s.l().log_debug(T_s.s().l_log_enter_code_Z1, l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_exit_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_log_enter_code = "l_logger.log_exit(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\")"
        T_s.l().log_debug(T_s.s().l_log_enter_code_Z1, l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_error_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_serialized_parameters = ""
        if (l_arguments.size() != 0) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += "," + "l_shortcuts.r(" + l_argument.getName() + ",\"" + l_argument.getName() + "\")"
            }
        }
        String l_log_enter_code = "l_logger.log_exception(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\", e_others" + l_serialized_parameters + ")"
        T_s.l().log_debug(T_s.s().l_log_enter_code_Z1, l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_error_statement_error_only(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_serialized_parameters = ""
        if (l_arguments.size() != 0) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += "," + "l_shortcuts.r(" + l_argument.getName() + ",\"" + l_argument.getName() + "\")"
            }
        }
        String l_log_enter_code = "l_logger.log_error(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\", e_others" + l_serialized_parameters + ")"
        T_s.l().log_debug(T_s.s().l_log_enter_code_Z1, l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    TryCatchStatement create_try_catch_statement(BlockStatement i_block_statement, AnnotationNode i_annotation_node, MethodNode i_method_node) {
        TryCatchStatement tryCatchStatement = new TryCatchStatement(i_block_statement, EmptyStatement.INSTANCE)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_error_statement(i_method_node))
        l_throw_block.addStatement(rethrow(i_annotation_node))
        tryCatchStatement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, "e_others"), l_throw_block))
        return tryCatchStatement
    }

    TryCatchStatement create_try_catch_statement_error_only(BlockStatement i_block_statement, AnnotationNode i_annotation_node, MethodNode i_method_node) {
        TryCatchStatement tryCatchStatement = new TryCatchStatement(i_block_statement, EmptyStatement.INSTANCE)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_error_statement_error_only(i_method_node))
        l_throw_block.addStatement(rethrow(i_annotation_node))
        tryCatchStatement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, "e_others"), l_throw_block))
        return tryCatchStatement
    }

    Statement rethrow(AnnotationNode i_annotation_node) {
        ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.varX("e_others"))
        throwStatement.setSourcePosition(i_annotation_node)
        return throwStatement
    }

}