# Searchables（`searchables`）

> jar：`[前置]Searchables-neoforge-1.21.1-1.0.2.jar` ｜ 版本：`1.0.2` ｜ 作者：Jaredlll08
> 规模：**30 个类 / 10 个包 / 1607 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blamejared.searchables.lang` | 5 | `SLParser`、`Token`、`SLScanner` |
| `com.blamejared.searchables.api.context` | 5 | `SearchPredicate`、`SearchLiteral`、`ContextVisitor` |
| `com.blamejared.searchables.api` | 4 | `TokenRange`、`SearchableType`、`SearchableComponent` |
| `com.blamejared.searchables.api.autcomplete` | 4 | `AutoComplete`、`CompletionVisitor`、`AutoCompletingEditBox` |
| `com.blamejared.searchables.lang.expression.type` | 4 | `GroupingExpression`、`LiteralExpression`、`ComponentExpression` |
| `com.blamejared.searchables.api.formatter` | 3 | `FormattingVisitor`、`FormattingConstants`、`FormattingContext` |
| `com.blamejared.searchables.lang.expression.visitor` | 2 | `ContextAwareVisitor`、`Visitor` |
| `com.blamejared.searchables` | 1 | `SearchablesNeoForge` |
| `com.blamejared.searchables.mixin` | 1 | `AccessEditBox` |
| `com.blamejared.searchables.lang.expression` | 1 | `Expression` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SearchablesNeoForge` | class | — | Mod | 0 | 11 | [SearchablesNeoForge.java](com/blamejared/searchables/SearchablesNeoForge.java) |
| `AccessEditBox` | interface | — | Mixin | 0 | 19 | [AccessEditBox.java](com/blamejared/searchables/mixin/AccessEditBox.java) |
| `TokenRange` | class | Comparable<TokenRange> | — | 27 | 178 | [TokenRange.java](com/blamejared/searchables/api/TokenRange.java) |
| `AutoComplete` | class | AbstractWidget | — | 17 | 185 | [AutoComplete.java](com/blamejared/searchables/api/autcomplete/AutoComplete.java) |
| `SLParser` | class | — | — | 10 | 99 | [SLParser.java](com/blamejared/searchables/lang/SLParser.java) |
| `SearchableType` | class | — | — | 13 | 130 | [SearchableType.java](com/blamejared/searchables/api/SearchableType.java) |
| `FormattingVisitor` | class | ContextAwareVisitor<TokenRange | — | 11 | 115 | [FormattingVisitor.java](com/blamejared/searchables/api/formatter/FormattingVisitor.java) |
| `CompletionVisitor` | class | Visitor<TokenRange> | — | 14 | 101 | [CompletionVisitor.java](com/blamejared/searchables/api/autcomplete/CompletionVisitor.java) |
| `AutoCompletingEditBox` | class | EditBox | — | 12 | 140 | [AutoCompletingEditBox.java](com/blamejared/searchables/api/autcomplete/AutoCompletingEditBox.java) |
| `Token` | class | — | — | 6 | 43 | [Token.java](com/blamejared/searchables/lang/Token.java) |
| `SLScanner` | class | — | — | 9 | 94 | [SLScanner.java](com/blamejared/searchables/lang/SLScanner.java) |
| `GroupingExpression` | class | Expression | — | 6 | 46 | [GroupingExpression.java](com/blamejared/searchables/lang/expression/type/GroupingExpression.java) |
| `LiteralExpression` | class | Expression | — | 5 | 39 | [LiteralExpression.java](com/blamejared/searchables/lang/expression/type/LiteralExpression.java) |
| `ComponentExpression` | class | Expression | — | 6 | 46 | [ComponentExpression.java](com/blamejared/searchables/lang/expression/type/ComponentExpression.java) |
| `SearchableComponent` | class | — | — | 7 | 51 | [SearchableComponent.java](com/blamejared/searchables/api/SearchableComponent.java) |
| `TokenType` | enum | — | — | 0 | 10 | [TokenType.java](com/blamejared/searchables/lang/TokenType.java) |
| `StringSearcher` | class | — | — | 3 | 24 | [StringSearcher.java](com/blamejared/searchables/lang/StringSearcher.java) |
| `Expression` | class | — | — | 2 | 11 | [Expression.java](com/blamejared/searchables/lang/expression/Expression.java) |
| `PairedExpression` | class | Expression | — | 4 | 34 | [PairedExpression.java](com/blamejared/searchables/lang/expression/type/PairedExpression.java) |
| `ContextAwareVisitor` | interface | — | — | 0 | 21 | [ContextAwareVisitor.java](com/blamejared/searchables/lang/expression/visitor/ContextAwareVisitor.java) |
| `Visitor` | interface | — | — | 0 | 21 | [Visitor.java](com/blamejared/searchables/lang/expression/visitor/Visitor.java) |
| `SearchablesConstants` | class | — | — | 1 | 51 | [SearchablesConstants.java](com/blamejared/searchables/api/SearchablesConstants.java) |
| `SearchPredicate` | interface | — | — | 0 | 9 | [SearchPredicate.java](com/blamejared/searchables/api/context/SearchPredicate.java) |
| `SearchLiteral` | record | SearchPredicate<T> | — | 1 | 13 | [SearchLiteral.java](com/blamejared/searchables/api/context/SearchLiteral.java) |
| `ContextVisitor` | class | Visitor<SearchContext<T>> | — | 4 | 37 | [ContextVisitor.java](com/blamejared/searchables/api/context/ContextVisitor.java) |
| `SearchContext` | class | — | — | 2 | 19 | [SearchContext.java](com/blamejared/searchables/api/context/SearchContext.java) |
| `SearchComponent` | record | SearchPredicate<T> | — | 1 | 13 | [SearchComponent.java](com/blamejared/searchables/api/context/SearchComponent.java) |
| `FormattingConstants` | class | — | — | 0 | 10 | [FormattingConstants.java](com/blamejared/searchables/api/formatter/FormattingConstants.java) |
| `FormattingContext` | record | — | — | 4 | 22 | [FormattingContext.java](com/blamejared/searchables/api/formatter/FormattingContext.java) |
| `CompletionSuggestion` | record | — | — | 2 | 15 | [CompletionSuggestion.java](com/blamejared/searchables/api/autcomplete/CompletionSuggestion.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessEditBox` | `AccessEditBox` | [AccessEditBox.java](com/blamejared/searchables/mixin/AccessEditBox.java) |

## 全部类（30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SearchablesNeoForge` | class | — | Mod | 0 | 11 | [SearchablesNeoForge.java](com/blamejared/searchables/SearchablesNeoForge.java) |
| `AccessEditBox` | interface | — | Mixin | 0 | 19 | [AccessEditBox.java](com/blamejared/searchables/mixin/AccessEditBox.java) |
| `TokenRange` | class | Comparable<TokenRange> | — | 27 | 178 | [TokenRange.java](com/blamejared/searchables/api/TokenRange.java) |
| `AutoComplete` | class | AbstractWidget | — | 17 | 185 | [AutoComplete.java](com/blamejared/searchables/api/autcomplete/AutoComplete.java) |
| `SLParser` | class | — | — | 10 | 99 | [SLParser.java](com/blamejared/searchables/lang/SLParser.java) |
| `SearchableType` | class | — | — | 13 | 130 | [SearchableType.java](com/blamejared/searchables/api/SearchableType.java) |
| `FormattingVisitor` | class | ContextAwareVisitor<TokenRange | — | 11 | 115 | [FormattingVisitor.java](com/blamejared/searchables/api/formatter/FormattingVisitor.java) |
| `CompletionVisitor` | class | Visitor<TokenRange> | — | 14 | 101 | [CompletionVisitor.java](com/blamejared/searchables/api/autcomplete/CompletionVisitor.java) |
| `AutoCompletingEditBox` | class | EditBox | — | 12 | 140 | [AutoCompletingEditBox.java](com/blamejared/searchables/api/autcomplete/AutoCompletingEditBox.java) |
| `Token` | class | — | — | 6 | 43 | [Token.java](com/blamejared/searchables/lang/Token.java) |
| `SLScanner` | class | — | — | 9 | 94 | [SLScanner.java](com/blamejared/searchables/lang/SLScanner.java) |
| `GroupingExpression` | class | Expression | — | 6 | 46 | [GroupingExpression.java](com/blamejared/searchables/lang/expression/type/GroupingExpression.java) |
| `LiteralExpression` | class | Expression | — | 5 | 39 | [LiteralExpression.java](com/blamejared/searchables/lang/expression/type/LiteralExpression.java) |
| `ComponentExpression` | class | Expression | — | 6 | 46 | [ComponentExpression.java](com/blamejared/searchables/lang/expression/type/ComponentExpression.java) |
| `SearchableComponent` | class | — | — | 7 | 51 | [SearchableComponent.java](com/blamejared/searchables/api/SearchableComponent.java) |
| `TokenType` | enum | — | — | 0 | 10 | [TokenType.java](com/blamejared/searchables/lang/TokenType.java) |
| `StringSearcher` | class | — | — | 3 | 24 | [StringSearcher.java](com/blamejared/searchables/lang/StringSearcher.java) |
| `Expression` | class | — | — | 2 | 11 | [Expression.java](com/blamejared/searchables/lang/expression/Expression.java) |
| `PairedExpression` | class | Expression | — | 4 | 34 | [PairedExpression.java](com/blamejared/searchables/lang/expression/type/PairedExpression.java) |
| `ContextAwareVisitor` | interface | — | — | 0 | 21 | [ContextAwareVisitor.java](com/blamejared/searchables/lang/expression/visitor/ContextAwareVisitor.java) |
| `Visitor` | interface | — | — | 0 | 21 | [Visitor.java](com/blamejared/searchables/lang/expression/visitor/Visitor.java) |
| `SearchablesConstants` | class | — | — | 1 | 51 | [SearchablesConstants.java](com/blamejared/searchables/api/SearchablesConstants.java) |
| `SearchPredicate` | interface | — | — | 0 | 9 | [SearchPredicate.java](com/blamejared/searchables/api/context/SearchPredicate.java) |
| `SearchLiteral` | record | SearchPredicate<T> | — | 1 | 13 | [SearchLiteral.java](com/blamejared/searchables/api/context/SearchLiteral.java) |
| `ContextVisitor` | class | Visitor<SearchContext<T>> | — | 4 | 37 | [ContextVisitor.java](com/blamejared/searchables/api/context/ContextVisitor.java) |
| `SearchContext` | class | — | — | 2 | 19 | [SearchContext.java](com/blamejared/searchables/api/context/SearchContext.java) |
| `SearchComponent` | record | SearchPredicate<T> | — | 1 | 13 | [SearchComponent.java](com/blamejared/searchables/api/context/SearchComponent.java) |
| `FormattingConstants` | class | — | — | 0 | 10 | [FormattingConstants.java](com/blamejared/searchables/api/formatter/FormattingConstants.java) |
| `FormattingContext` | record | — | — | 4 | 22 | [FormattingContext.java](com/blamejared/searchables/api/formatter/FormattingContext.java) |
| `CompletionSuggestion` | record | — | — | 2 | 15 | [CompletionSuggestion.java](com/blamejared/searchables/api/autcomplete/CompletionSuggestion.java) |
