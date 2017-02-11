package com.a9ae0b01f0ffc.black_box.implementation.annotations

import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.commons.main.T_common_const
import groovy.transform.ToString
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.tools.GeneralUtils
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@ToString(includeNames = true, includeFields = true)
@GroovyASTTransformation(
        phase = CompilePhase.SEMANTIC_ANALYSIS
)
class T_black_box_transformation_base extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    public static final String PC_BLACK_BOX_TYPE_ERROR = "error"
    public static final String PC_BLACK_BOX_TYPE_FULL = "full"
    private T_black_box_visitor_base p_return_expression_visitor = new T_black_box_visitor_base()

    static {
        com.a9ae0b01f0ffc.black_box_base.main.T_s.x().init_custom("C:/COMPILE/commons.conf")
        T_s.x().init_custom("C:/COMPILE/with_logging/commons.conf")
    }

    T_black_box_visitor_base get_return_expression_visitor() {
        return p_return_expression_visitor
    }

    T_black_box_transformation_base() {
    }


    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        if (T_s.c().GC_BLACK_BOX_ENABLED != T_common_const.GC_TRUE_STRING) {
            return
        }
        if (i_ast_nodes.length == 2 && i_ast_nodes[0] instanceof AnnotationNode && i_ast_nodes[1] instanceof AnnotatedNode) {
            ASTNode l_first_node = i_ast_nodes[1]
            Expression l_type_member = ((AnnotationNode) i_ast_nodes[0]).getMember("value")
            String l_black_box_type = PC_BLACK_BOX_TYPE_FULL
            if (l_type_member != T_common_const.GC_NULL_OBJ_REF) {
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
                    if (l_black_box_type == PC_BLACK_BOX_TYPE_FULL) {
                        l_changed_block_statement.addStatement(create_log_enter_statement(l_method_node))
                        BlockStatement l_inside_try = new BlockStatement()
                        T_black_box_visitor_base l_return_expression_visitor = get_return_expression_visitor()
                        l_return_expression_visitor.p_is_return_added = T_common_const.GC_FALSE
                        for (Statement l_return_statement in l_statements) {
                            l_return_statement.visit(l_return_expression_visitor)
                            l_inside_try.addStatement(l_return_statement)
                        }
                        if (!l_return_expression_visitor.p_is_return_added) {
                            l_inside_try.addStatement(create_log_exit_statement(l_method_node))
                        }
                        l_changed_block_statement.addStatement(create_try_catch_statement(l_inside_try, (AnnotationNode) i_ast_nodes[T_common_const.GC_FIRST_INDEX], l_method_node, "log_exception"))
                    } else if (l_black_box_type == PC_BLACK_BOX_TYPE_ERROR) {
                        if (T_s.c().GC_PROFILE_ALL != T_common_const.GC_TRUE_STRING) {
                            BlockStatement l_inside_try = new BlockStatement()
                            T_black_box_visitor_base l_return_expression_visitor = get_return_expression_visitor()
                            l_return_expression_visitor.p_is_return_added = T_common_const.GC_FALSE
                            for (Statement l_return_statement in l_statements) {
                                l_inside_try.addStatement(l_return_statement)
                            }
                            l_changed_block_statement.addStatement(create_try_catch_statement(l_inside_try, (AnnotationNode) i_ast_nodes[T_common_const.GC_FIRST_INDEX], l_method_node, "log_error"))
                        } else {
                            l_changed_block_statement.addStatement(create_profile_enter_statement(l_method_node))
                            BlockStatement l_inside_try = new BlockStatement()
                            T_black_box_visitor_base l_return_expression_visitor = get_return_expression_visitor()
                            l_return_expression_visitor.set_profile_only()
                            l_return_expression_visitor.p_is_return_added = T_common_const.GC_FALSE
                            for (Statement l_return_statement in l_statements) {
                                l_return_statement.visit(l_return_expression_visitor)
                                l_inside_try.addStatement(l_return_statement)
                            }
                            if (!l_return_expression_visitor.p_is_return_added) {
                                l_inside_try.addStatement(create_profile_exit_statement(l_method_node))
                            }
                            l_changed_block_statement.addStatement(create_try_catch_statement(l_inside_try, (AnnotationNode) i_ast_nodes[T_common_const.GC_FIRST_INDEX], l_method_node, "log_exception"))
                        }
                    } else {
                        throw new RuntimeException("Unsupported Black Box type $l_black_box_type")
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
        return GeneralUtils.declS(GeneralUtils.varX("l_methodname"), GeneralUtils.constX(i_methodname))
    }

    Statement create_l_classname_declaration_statement(String i_classname) {
        return GeneralUtils.declS(GeneralUtils.varX("l_classname"), GeneralUtils.constX(i_classname))
    }

    Statement create_logger_declaration_statement() {
        return GeneralUtils.declS(GeneralUtils.varX("l_logger"), GeneralUtils.callX(GeneralUtils.varX("l_shortcuts"), "l", new ArgumentListExpression()))
    }

    Statement create_shortcut_declaration_statement() {
        return GeneralUtils.declS(GeneralUtils.varX("l_shortcuts"), new ConstructorCallExpression(new ClassNode(T_s.class), new ArgumentListExpression()))
    }

    Statement create_log_enter_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_serialized_parameters = T_common_const.GC_EMPTY_STRING
        if (l_arguments.size() != 0) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += ", l_shortcuts.r(${l_argument.getName()}, \"${l_argument.getName()}\")"
            }
        }
        String l_statement_code = "l_logger.log_enter(\"${i_method_node.getDeclaringClass().getName()}\", \"${i_method_node.getName()}\" $l_serialized_parameters, l_shortcuts.r(this, \"this\"))"
        T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_profile_enter_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_statement_code = "l_logger.profile_enter(\"${i_method_node.getDeclaringClass().getName()}\", \"${i_method_node.getName()}\")"
        T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_exit_statement(MethodNode i_method_node) {
        String l_statement_code = "l_logger.log_exit(\"${i_method_node.getDeclaringClass().getName()}\",\"${i_method_node.getName()}\")"
        T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_profile_exit_statement(MethodNode i_method_node) {
        String l_statement_code = "l_logger.profile_exit(\"${i_method_node.getDeclaringClass().getName()}\",\"${i_method_node.getName()}\")"
        T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_error_statement(MethodNode i_method_node, String i_log_function_name) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_serialized_parameters = T_common_const.GC_EMPTY_STRING
        if (l_arguments.size() != 0) {
            for (l_argument in l_arguments) {
                l_serialized_parameters += ", l_shortcuts.r(${l_argument.getName()}, \"${l_argument.getName()}\")"
            }
        }
        String l_statement_code = "l_logger.${i_log_function_name}(\"${i_method_node.getDeclaringClass().getName()}\", \"${i_method_node.getName()}\", e_others $l_serialized_parameters)"
        T_s.l().log_debug(T_s.s().l_statement_code_Z1, l_statement_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_statement_code)
        return (Statement) l_resulting_statements.first()
    }

    TryCatchStatement create_try_catch_statement(BlockStatement i_block_statement, AnnotationNode i_annotation_node, MethodNode i_method_node, String i_log_function_name) {
        TryCatchStatement tryCatchStatement = new TryCatchStatement(i_block_statement, EmptyStatement.INSTANCE)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_error_statement(i_method_node, i_log_function_name))
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
