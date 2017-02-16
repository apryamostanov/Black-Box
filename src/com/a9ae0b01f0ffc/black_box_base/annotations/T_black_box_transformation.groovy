package com.a9ae0b01f0ffc.black_box_base.annotations

import com.a9ae0b01f0ffc.black_box.annotations.T_black_box_visitor
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
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
class T_black_box_transformation extends com.a9ae0b01f0ffc.black_box.annotations.T_black_box_transformation {

    @Override
    void visit(ASTNode[] i_ast_nodes, SourceUnit i_source_unit) {
        return
    }
}
