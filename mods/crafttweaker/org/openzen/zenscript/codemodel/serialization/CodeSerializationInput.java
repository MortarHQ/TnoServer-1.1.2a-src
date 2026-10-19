package org.openzen.zenscript.codemodel.serialization;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.context.StatementContext;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.TypeID;

public interface CodeSerializationInput {
   boolean readBool();

   int readByte();

   byte readSByte();

   short readShort();

   int readUShort();

   int readInt();

   int readUInt();

   long readLong();

   long readULong();

   float readFloat();

   double readDouble();

   char readChar();

   String readString();

   HighLevelDefinition readDefinition();

   DefinitionMemberRef readMember(TypeContext var1, TypeID var2);

   EnumConstantMember readEnumConstant(TypeContext var1);

   VariantOptionRef readVariantOption(TypeContext var1, TypeID var2);

   AnnotationDefinition readAnnotationType();

   TypeID deserializeType(TypeContext var1);

   CodePosition deserializePosition();

   FunctionHeader deserializeHeader(TypeContext var1);

   CallArguments deserializeArguments(StatementContext var1);

   Statement deserializeStatement(StatementContext var1);

   Expression deserializeExpression(StatementContext var1);

   TypeParameter deserializeTypeParameter(TypeContext var1);

   TypeParameter[] deserializeTypeParameters(TypeContext var1);

   void enqueueMembers(DecodingOperation var1);

   void enqueueCode(DecodingOperation var1);
}
