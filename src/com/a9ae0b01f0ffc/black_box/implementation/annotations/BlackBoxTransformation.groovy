//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.a9ae0b01f0ffc.black_box.implementation.annotations

import com.a9ae0b01f0ffc.black_box.main.T_s
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(
        phase = CompilePhase.CANONICALIZATION
)
public class BlackBoxTransformation extends AbstractASTTransformation {
    private static final ClassNode CATCHED_THROWABLE_TYPE = ClassHelper.make(Throwable.class)
    File p_compilation_log_file = new File("c:/LOGS/log_file_name")
    FileWriter p_file_writer = new FileWriter(p_compilation_log_file, true)

    void log(String i_message) {
        p_file_writer.write(i_message + System.lineSeparator())
        p_file_writer.flush()
    }

    public BlackBoxTransformation() {
    }

    public void visit(ASTNode[] nodes, SourceUnit source) {
        if (nodes.length == 2 && nodes[0] instanceof AnnotationNode && nodes[1] instanceof AnnotatedNode) {
            ASTNode node = nodes[1]
            if (!(node instanceof MethodNode)) {
                this.addError("@BlackBox must only be applied on test methods!", node)
            } else {
                MethodNode methodNode = (MethodNode) node
                ArrayList statements = new ArrayList()
                Statement statement = methodNode.getCode()
                if (statement instanceof BlockStatement) {
                    statements.addAll(((BlockStatement) statement).getStatements())
                }
                if (!statements.isEmpty()) {
                    BlockStatement rewrittenMethodCode = new BlockStatement()
                    rewrittenMethodCode.addStatement(create_shortcut_declaration_statement())
                    rewrittenMethodCode.addStatement(create_logger_declaration_statement())
                    rewrittenMethodCode.addStatement(create_log_enter_statement(methodNode))
                    for (Object l_return_statement in statements) {
                        if (l_return_statement instanceof ReturnStatement) {
                            rewrittenMethodCode.addStatement(create_log_exit_statement(methodNode, l_return_statement))
                            rewrittenMethodCode.addStatement((Statement) l_return_statement)
                        } else {
                            rewrittenMethodCode.addStatement((Statement) l_return_statement)
                        }
                    }
                    rewrittenMethodCode.addStatements(statements)

                    /* rewrittenMethodCode.addStatement*/
                    //rewrittenMethodCode.addStatement(this.tryCatchAssertionFailedError(annotationNode, methodNode, statements))
                    //rewrittenMethodCode.addStatement(this.throwAssertionFailedError(annotationNode)) //last statement within Try
                    methodNode.setCode(rewrittenMethodCode)
                }
            }
        } else {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes))
        }
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
        String l_log_enter_code = "l_logger.log_enter(\"" + i_method_node.getDeclaringClass().getName() + "\",\"" + i_method_node.getName() + "\"" + l_serialized_parameters + ")"
        log(l_log_enter_code)
        List<ASTNode> l_resulting_statements = new AstBuilder().buildFromString(CompilePhase.SEMANTIC_ANALYSIS, l_log_enter_code)
        return (Statement) l_resulting_statements.first()
    }

    Statement create_log_exit_statement(MethodNode i_method_node, ReturnStatement i_return_statement) {
        log(new ExpressionStatement(i_return_statement.getExpression()).getText())
        MethodCallExpression l_method_call_expression = new MethodCallExpression(new VariableExpression("l_logger"), "log_exit", new ArgumentListExpression(new ConstantExpression(i_method_node.getDeclaringClass().getName()), new ConstantExpression(i_method_node.getName()), new MethodCallExpression(new VariableExpression("l_shortcuts"), "r", new ArgumentListExpression(i_return_statement.getExpression(), new ConstantExpression(i_return_statement.getExpression().getText())))))
        return new ExpressionStatement(l_method_call_expression)
    }

    /*  private TryCatchStatement tryCatchAssertionFailedError(AnnotationNode annotationNode, MethodNode methodNode, ArrayList<Statement> statements) {
          TryCatchStatement tryCatchStatement = new TryCatchStatement(GeneralUtils.block(methodNode.getVariableScope(), statements), EmptyStatement.INSTANCE)
          tryCatchStatement.addCatch(GeneralUtils.catchS(GeneralUtils.param(CATCHED_THROWABLE_TYPE, "ex"), ReturnStatement.RETURN_NULL_OR_VOID))
          return tryCatchStatement
      }
  */
    /* private Statement throwAssertionFailedError(AnnotationNode annotationNode) {
         ThrowStatement throwStatement = GeneralUtils.throwS(GeneralUtils.ctorX(ASSERTION_FAILED_ERROR_TYPE, GeneralUtils.args(new Expression[] {
             GeneralUtils.constX("Method is marked with @NotYetImplemented but passes unexpectedly")
         })))
         throwStatement.setSourcePosition(annotationNode)
         return throwStatement
     }*/
}
