# Rhino（`rhino`）

> jar：`[犀牛]rhino.jar` ｜ 版本：`2101.2.7-build.81` ｜ 作者：latvian.dev, Mozilla
> 规模：**268 个类 / 9 个包 / 56315 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 0

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.latvian.mods.rhino` | 127 | `NativeDate`、`CodeGenerator`、`IRFactory` |
| `dev.latvian.mods.rhino.ast` | 63 | `FunctionNode`、`ScriptNode`、`AstNode` |
| `dev.latvian.mods.rhino.type` | 23 | `ParameterizedTypeInfo`、`RecordTypeInfo`、`ClassTypeInfo` |
| `dev.latvian.mods.rhino.util` | 21 | `ArrayValueProvider`、`JavaSetWrapper`、`DataObject` |
| `dev.latvian.mods.rhino.regexp` | 12 | `NativeRegExp`、`RegExp`、`NativeRegExpCtor` |
| `dev.latvian.mods.rhino.classfile` | 10 | `ClassFileWriter`、`SuperBlock`、`FieldOrMethodRef` |
| `dev.latvian.mods.rhino.v8dtoa` | 6 | `FastDtoaBuilder`、`DoubleConversion`、`DoubleHelper` |
| `dev.latvian.mods.rhino.util.wrap` | 5 | `TypeWrappers`、`TypeWrapperFactory`、`TypeWrapper` |
| `dev.latvian.mods.rhino.json` | 1 | `JsonParser` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NativeDate` | class | IdScriptableObject | — | 46 | 1794 | [NativeDate.java](dev/latvian/mods/rhino/NativeDate.java) |
| `CodeGenerator` | class | Icode | — | 40 | 1409 | [CodeGenerator.java](dev/latvian/mods/rhino/CodeGenerator.java) |
| `IRFactory` | class | Parser | — | 67 | 1539 | [IRFactory.java](dev/latvian/mods/rhino/IRFactory.java) |
| `NativeArray` | class | IdScriptableObject | — | 104 | 2179 | [NativeArray.java](dev/latvian/mods/rhino/NativeArray.java) |
| `ScriptRuntime` | class | ScriptRuntime.MessageProvider | — | 167 | 2817 | [ScriptRuntime.java](dev/latvian/mods/rhino/ScriptRuntime.java) |
| `ScriptableObject` | class | ScriptableObject.Slot | — | 107 | 1425 | [ScriptableObject.java](dev/latvian/mods/rhino/ScriptableObject.java) |
| `TokenStream` | class | — | — | 30 | 1313 | [TokenStream.java](dev/latvian/mods/rhino/TokenStream.java) |
| `Context` | class | — | — | 99 | 1342 | [Context.java](dev/latvian/mods/rhino/Context.java) |
| `NativeString` | class | IdScriptableObject | — | 32 | 1159 | [NativeString.java](dev/latvian/mods/rhino/NativeString.java) |
| `Parser` | class | RuntimeException | — | 117 | 3495 | [Parser.java](dev/latvian/mods/rhino/Parser.java) |
| `Interpreter` | class | Icode | — | 55 | 2275 | [Interpreter.java](dev/latvian/mods/rhino/Interpreter.java) |
| `ClassFileWriter` | class | RuntimeException | — | 118 | 3417 | [ClassFileWriter.java](dev/latvian/mods/rhino/classfile/ClassFileWriter.java) |
| `NativeRegExp` | class | IdScriptableObject | — | 55 | 2553 | [NativeRegExp.java](dev/latvian/mods/rhino/regexp/NativeRegExp.java) |
| `IdScriptableObject` | class | ScriptableObject | — | 45 | 920 | [IdScriptableObject.java](dev/latvian/mods/rhino/IdScriptableObject.java) |
| `NativeObject` | class | IdScriptableObject | — | 42 | 879 | [NativeObject.java](dev/latvian/mods/rhino/NativeObject.java) |
| `Node` | class | Iterable<Node> | — | 60 | 827 | [Node.java](dev/latvian/mods/rhino/Node.java) |
| `JavaAdapter` | class | IdFunctionCall | — | 25 | 899 | [JavaAdapter.java](dev/latvian/mods/rhino/JavaAdapter.java) |
| `NativeJavaList` | class | NativeJavaObject | — | 30 | 350 | [NativeJavaList.java](dev/latvian/mods/rhino/NativeJavaList.java) |
| `BaseFunction` | class | IdScriptableObject | — | 29 | 472 | [BaseFunction.java](dev/latvian/mods/rhino/BaseFunction.java) |
| `ObjArray` | class | — | — | 25 | 419 | [ObjArray.java](dev/latvian/mods/rhino/ObjArray.java) |
| `FunctionNode` | class | ScriptNode | — | 38 | 251 | [FunctionNode.java](dev/latvian/mods/rhino/ast/FunctionNode.java) |
| `DToA` | class | — | — | 6 | 1022 | [DToA.java](dev/latvian/mods/rhino/DToA.java) |
| `JavaMembers` | class | — | — | 18 | 624 | [JavaMembers.java](dev/latvian/mods/rhino/JavaMembers.java) |
| `NativeJavaObject` | class | Scriptable | — | 27 | 289 | [NativeJavaObject.java](dev/latvian/mods/rhino/NativeJavaObject.java) |
| `ScriptNode` | class | Scope | — | 29 | 244 | [ScriptNode.java](dev/latvian/mods/rhino/ast/ScriptNode.java) |
| `NativeSymbol` | class | IdScriptableObject | — | 24 | 300 | [NativeSymbol.java](dev/latvian/mods/rhino/NativeSymbol.java) |
| `Arguments` | class | IdScriptableObject | — | 22 | 376 | [Arguments.java](dev/latvian/mods/rhino/Arguments.java) |
| `NativeGlobal` | class | IdFunctionCall | — | 13 | 711 | [NativeGlobal.java](dev/latvian/mods/rhino/NativeGlobal.java) |
| `ObjToIntMap` | class | — | — | 23 | 360 | [ObjToIntMap.java](dev/latvian/mods/rhino/ObjToIntMap.java) |
| `ArrayValueProvider` | interface | ArrayValueProvider | — | 21 | 235 | [ArrayValueProvider.java](dev/latvian/mods/rhino/util/ArrayValueProvider.java) |
| `CachedClassInfo` | class | — | — | 13 | 423 | [CachedClassInfo.java](dev/latvian/mods/rhino/CachedClassInfo.java) |
| `Undefined` | class | — | — | 23 | 126 | [Undefined.java](dev/latvian/mods/rhino/Undefined.java) |
| `NativeWith` | class | Scriptable | — | 22 | 186 | [NativeWith.java](dev/latvian/mods/rhino/NativeWith.java) |
| `NativeMath` | class | IdScriptableObject | — | 8 | 636 | [NativeMath.java](dev/latvian/mods/rhino/NativeMath.java) |
| `UintMap` | class | — | — | 16 | 329 | [UintMap.java](dev/latvian/mods/rhino/UintMap.java) |
| `Hashtable` | class | Iterable<Hashtable.Entry> | — | 15 | 205 | [Hashtable.java](dev/latvian/mods/rhino/Hashtable.java) |
| `NativeNumber` | class | IdScriptableObject | — | 16 | 308 | [NativeNumber.java](dev/latvian/mods/rhino/NativeNumber.java) |
| `RhinoException` | class | RuntimeException | — | 17 | 241 | [RhinoException.java](dev/latvian/mods/rhino/RhinoException.java) |
| `NativeError` | class | IdScriptableObject | — | 18 | 277 | [NativeError.java](dev/latvian/mods/rhino/NativeError.java) |
| `JsonParser` | class | Exception | — | 15 | 395 | [JsonParser.java](dev/latvian/mods/rhino/json/JsonParser.java) |
| `AstNode` | class | Node | — | 23 | 191 | [AstNode.java](dev/latvian/mods/rhino/ast/AstNode.java) |
| `RegExp` | class | — | — | 13 | 526 | [RegExp.java](dev/latvian/mods/rhino/regexp/RegExp.java) |
| `EqualObjectGraphs` | class | — | — | 12 | 285 | [EqualObjectGraphs.java](dev/latvian/mods/rhino/EqualObjectGraphs.java) |
| `NativeMap` | class | IdScriptableObject | — | 14 | 300 | [NativeMap.java](dev/latvian/mods/rhino/NativeMap.java) |
| `NativeJSON` | class | IdScriptableObject | — | 11 | 247 | [NativeJSON.java](dev/latvian/mods/rhino/NativeJSON.java) |
| `NativeJavaClass` | class | NativeJavaObject | — | 13 | 228 | [NativeJavaClass.java](dev/latvian/mods/rhino/NativeJavaClass.java) |
| `ES6Generator` | class | IdScriptableObject | — | 11 | 375 | [ES6Generator.java](dev/latvian/mods/rhino/ES6Generator.java) |
| `NativeIterator` | class | IdScriptableObject | — | 12 | 202 | [NativeIterator.java](dev/latvian/mods/rhino/NativeIterator.java) |
| `EmbeddedSlotMap` | class | SlotMap | — | 14 | 282 | [EmbeddedSlotMap.java](dev/latvian/mods/rhino/EmbeddedSlotMap.java) |
| `NativeJavaArray` | class | NativeJavaObject | — | 15 | 135 | [NativeJavaArray.java](dev/latvian/mods/rhino/NativeJavaArray.java) |
| `NativeSet` | class | IdScriptableObject | — | 13 | 261 | [NativeSet.java](dev/latvian/mods/rhino/NativeSet.java) |
| `Kit` | class | — | — | 13 | 282 | [Kit.java](dev/latvian/mods/rhino/Kit.java) |
| `NodeTransformer` | class | — | — | 9 | 479 | [NodeTransformer.java](dev/latvian/mods/rhino/NodeTransformer.java) |
| `ParameterizedTypeInfo` | class | TypeInfoBase.OptionallyConsolidatabl | — | 17 | 131 | [ParameterizedTypeInfo.java](dev/latvian/mods/rhino/type/ParameterizedTypeInfo.java) |
| `RecordTypeInfo` | class | ClassTypeInfo | — | 13 | 201 | [RecordTypeInfo.java](dev/latvian/mods/rhino/type/RecordTypeInfo.java) |
| `ClassTypeInfo` | class | TypeInfoBase | — | 16 | 97 | [ClassTypeInfo.java](dev/latvian/mods/rhino/type/ClassTypeInfo.java) |
| `Scope` | class | Jump | — | 17 | 158 | [Scope.java](dev/latvian/mods/rhino/ast/Scope.java) |
| `IfStatement` | class | AstNode | — | 15 | 94 | [IfStatement.java](dev/latvian/mods/rhino/ast/IfStatement.java) |
| `NativeRegExpCtor` | class | BaseFunction | — | 11 | 250 | [NativeRegExpCtor.java](dev/latvian/mods/rhino/regexp/NativeRegExpCtor.java) |
| `IdFunctionObject` | class | BaseFunction | — | 14 | 127 | [IdFunctionObject.java](dev/latvian/mods/rhino/IdFunctionObject.java) |

## 按包浏览

- `dev.latvian.mods.rhino`（127 类）
- `dev.latvian.mods.rhino.ast`（63 类）
- `dev.latvian.mods.rhino.classfile`（10 类）
- `dev.latvian.mods.rhino.json`（1 类）
- `dev.latvian.mods.rhino.regexp`（12 类）
- `dev.latvian.mods.rhino.type`（23 类）
- `dev.latvian.mods.rhino.util`（21 类）
- `dev.latvian.mods.rhino.util.wrap`（5 类）
- `dev.latvian.mods.rhino.v8dtoa`（6 类）
