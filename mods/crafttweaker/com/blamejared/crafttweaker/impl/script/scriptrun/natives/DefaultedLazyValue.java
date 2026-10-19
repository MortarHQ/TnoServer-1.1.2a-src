package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import java.lang.reflect.Parameter;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.type.TypeID;

record DefaultedLazyValue(Parameter parameter, TypeID typeId, FunctionParameter functionParameter) {
}
