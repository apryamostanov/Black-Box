package com.a9ae0b01f0ffc.black_box_base.annotations

import groovy.transform.ToString
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
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
