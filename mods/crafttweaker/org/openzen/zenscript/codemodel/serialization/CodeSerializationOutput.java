package org.openzen.zenscript.codemodel.serialization;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.FunctionHeader;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.annotations.AnnotationDefinition;
import org.openzen.zenscript.codemodel.context.StatementContext;
import org.openzen.zenscript.codemodel.context.TypeContext;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.switchvalue.SwitchValue;
import org.openzen.zenscript.codemodel.generic.TypeParameter;
import org.openzen.zenscript.codemodel.member.EnumConstantMember;
import org.openzen.zenscript.codemodel.member.IDefinitionMember;
import org.openzen.zenscript.codemodel.member.ref.DefinitionMemberRef;
import org.openzen.zenscript.codemodel.member.ref.VariantOptionRef;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.codemodel.type.TypeID;

public interface CodeSerializationOutput {
   void writeBool(boolean var1);

   void writeByte(int var1);

   void writeSByte(byte var1);

   void writeShort(short var1);

   void writeUShort(int var1);

   void writeInt(int var1);

   void writeUInt(int var1);

   void writeLong(long var1);

   void writeULong(long var1);

   void writeFloat(float var1);

   void writeDouble(double var1);

   void writeChar(char var1);

   void writeString(String var1);

   void write(HighLevelDefinition var1);

   void write(EnumConstantMember var1);

   void write(VariantOptionRef var1);

   void write(TypeContext var1, DefinitionMemberRef var2);

   void write(AnnotationDefinition var1);

   void serialize(TypeContext var1, IDefinitionMember var2);

   void serialize(TypeContext var1, TypeID var2);

   void serialize(TypeContext var1, TypeParameter var2);

   void serialize(TypeContext var1, TypeParameter[] var2);

   void serialize(CodePosition var1);

   void serialize(TypeContext var1, FunctionHeader var2);

   void serialize(StatementContext var1, CallArguments var2);

   void serialize(StatementContext var1, Statement var2);

   void serialize(StatementContext var1, Expression var2);

   void serialize(StatementContext var1, SwitchValue var2);

   void enqueueMembers(EncodingOperation var1);

   void enqueueCode(EncodingOperation var1);
}
