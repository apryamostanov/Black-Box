//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.a9ae0b01f0ffc.black_box.implementation.annotations

import com.a9ae0b01f0ffc.black_box.main.T_s
import jdk.nashorn.internal.ir.Block
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.EmptyStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codehaus.groovy.ast.stmt.TryCatchStatement
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
public class T_black_box_transformation extends AbstractASTTransformation {

    private static final ClassNode PC_CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    File p_compilation_log_file = new File("c:/LOGS/log_file_name")
    FileWriter p_file_writer = new FileWriter(p_compilation_log_file, true)

    void log(String i_message) {
        p_file_writer.write(new Date().format("yyyy-MM-dd HH:mm:ss:SS") + " : " + i_message + System.lineSeparator())
        p_file_writer.flush()
    }

    public T_black_box_transformation() {
    }


    public void visit(ASTNode[] nodes, SourceUnit source) {
        try {
            if (nodes.length == 2 && nodes[0] instanceof AnnotationNode && nodes[1] instanceof AnnotatedNode) {
                ASTNode node = nodes[1]
                if (node instanceof MethodNode) {
                    CodeVisitorSupport l_return_expression_visitor = new T_black_box_visitor()
                    MethodNode l_method_node = (MethodNode) node
                    log("Processing method: "+l_method_node.getName())
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
                        l_changed_block_statement.addStatement(create_log_enter_statement(l_method_node))
                        BlockStatement l_inside_try = new BlockStatement()
                        if (!l_method_node.isVoidMethod()) {
                            for (Statement l_return_statement in l_statements) {
                                l_return_statement.visit(l_return_expression_visitor)
                                l_inside_try.addStatement(l_return_statement)
                            }
                        } else {
                            l_inside_try.addStatements(l_statements)
                            l_inside_try.addStatement(create_log_exit_statement(l_method_node))
                        }
                        l_changed_block_statement.addStatement(create_try_catch_statement(l_inside_try, (AnnotationNode) nodes[0], l_method_node))
                        l_method_node.setCode(l_changed_block_statement)
                        log("Finished Processing method: "+l_method_node.getName())
                    }
                } else {
                    this.addError("@I_black_box should be applied to Methods.", node)
                }
            } else {
                throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes))
            }
        } catch (Exception e) {
            log(e.toString())
            throw (e)
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
        log(l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_exit_statement(MethodNode i_method_node) {
        Parameter[] l_arguments = i_method_node.getParameters()
        String l_log_enter_code = "l_logger.log_exit(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\")"
        log(l_log_enter_code)
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
        log(l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    TryCatchStatement create_try_catch_statement(BlockStatement i_block_statement, AnnotationNode i_annotation_node, MethodNode i_method_node) {
        log("create_try_catch_statement")
        TryCatchStatement tryCatchStatement = new TryCatchStatement(i_block_statement, EmptyStatement.INSTANCE)
        BlockStatement l_throw_block = new BlockStatement()
        l_throw_block.addStatement(create_log_error_statement(i_method_node))
        l_throw_block.addStatement(rethrow(i_annotation_node))
        tryCatchStatement.addCatch(GeneralUtils.catchS(GeneralUtils.param(PC_CATCHED_THROWABLE_TYPE, "e_others"), l_throw_block))
        log("finished create_try_catch_statement")
        return tryCatchStatement
    }

    Statement rethrow(AnnotationNode i_annotation_node) {
        log("rethrow")
        ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.varX("e_others"))
        throwStatement.setSourcePosition(i_annotation_node)
        return throwStatement
    }

}
