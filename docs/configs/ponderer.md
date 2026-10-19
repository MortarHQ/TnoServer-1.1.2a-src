---
页面: configs/ponderer
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `ponderer` 配置项

> **17 个文件 / 404 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/ponderer](../../mods/ponderer/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `ponderer-server.toml` | `enableBlueprintItem` | `False` | bool | but ponderer:blueprint will not appear in creative tabs and  | `ponderer:Config` |
| `ponderer-client.toml` | `blueprintCarrierItem` | `minecraft:paper` | str | If set to 'ponderer:blueprint', it only works when the serve | `ponderer:Config` |
| `ponderer-client.toml` | `developerMode` | `False` | bool | This is intended for pack authors and advanced maintenance. | `configured:Config`、`netherexp:JNEConfigImpl`、`ponderer:Config` |
| `ponderer-client.toml` | `defaultEditable` | `True` | bool | Default editable value for newly created scenes and scenes w | `ponderer:Config`、`ponderer:DslScene` |
| `ponderer-client.toml` | `ai.provider` | `anthropic` | str | Use 'openai' for OpenAI, DeepSeek, Groq, Ollama, LM Studio,  | `architectury:BlockEntityRendererRegistry`、`architectury:BlockEntityRendererRegistryImpl`、`architectury:EntityRendererRegistry` |
| `ponderer-client.toml` | `ai.apiBaseUrl` | `` | str | For compatible APIs, set to e.g. https://api.deepseek.com | `ponderer:Config` |
| `ponderer-client.toml` | `ai.apiKey` | `` | str | API key for the selected provider. | `ponderer:AiSceneGenerator`、`ponderer:AnthropicProvider`、`ponderer:Config` |
| `ponderer-client.toml` | `ai.model` | `` | str | OpenAI default: gpt-4o | `ancient_artifacts:AmuletLayer`、`ancient_artifacts:AmuletModel`、`ancient_artifacts:AncientArtifactsModelProvider` |
| `ponderer-client.toml` | `ai.proxy` | `` | str | Leave empty for no proxy. | `architectury:EventFactory`、`balm:Balm`、`balm:BalmRuntime` |
| `ponderer-client.toml` | `ai.trustAllSsl` | `True` | bool | WARNING: only enable when using a trusted local proxy. | `ponderer:Config` |
| `ponderer-client.toml` | `ai.webUseProxy` | `True` | bool | When disabled, reference URLs are fetched with a direct conn | `ponderer:Config` |
| `ponderer-client.toml` | `ai.maxTokens` | `16384` | int | Range: 1024 ~ 65536 | `ponderer:Config` |
| `ponderer-client.toml` | `pack.orphanPrompt` | `True` | bool | Set to false to suppress these prompts. | `ponderer:Config` |
| `ponderer/trigger_state.json` | `shown` | `["ponderer:carrion_spawn_egg:subtitle"]` | list |  | `artifacts:ClientConfig`、`emi:ButtonVisibility`、`jade:ClientProxy` |
| `ponderer/trigger_state.json` | `read` | `["ponderer:carrion_spawn_egg:title", "po` | list |  | `architectury:FluidStack`、`architectury:FluidStackHooks`、`architectury:FluidStackHooksImpl` |
| `ponderer/scripts/toms_storage_tutorial.json` | `id` | `ponderer:toms_storage_tutorial` | str |  | — |
| `ponderer/scripts/toms_storage_tutorial.json` | `items` | `["toms_storage:storage_terminal"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/toms_storage_tutorial.json` | `title.en_us` | `Tom's Simple Storage Tutorial` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/toms_storage_tutorial.json` | `title.zh_cn` | `汤姆的简易存储教程` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/toms_storage_tutorial.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/toms_storage_tutorial.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/toms_storage_tutorial.json` | `editable` | `False` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/toms_storage_tutorial.json` | `scenes` | `[{"id": "core_chests_terminal", "title":` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/mithril_block.json` | `id` | `ponderer:mithril_block` | str |  | — |
| `ponderer/scripts/mithril_block.json` | `items` | `["tensura:mithril_block"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/mithril_block.json` | `title.zh_cn` | `费尔德维的召唤仪式（需要在路径之门维度进行）` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/mithril_block.json` | `title.en_us` | `Feldway’s summoning ritual` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/mithril_block.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/mithril_block.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/mithril_block.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/mithril_block.json` | `scenes` | `[{"id": "scene_1", "title": {"zh_cn": "费` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/magic_ore_shard.json` | `id` | `ponderer:magic_ore_shard` | str |  | — |
| `ponderer/scripts/magic_ore_shard.json` | `items` | `["tensura:magic_ore_shard"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/magic_ore_shard.json` | `title.en_us` | `Some uses of Magic Ore Shards.` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/magic_ore_shard.json` | `title.zh_cn` | `魔矿碎片的一些使用` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/magic_ore_shard.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/magic_ore_shard.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/magic_ore_shard.json` | `scenes` | `[{"id": "scene_1", "title": {"en_us": "U` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/smithing_bench.json` | `id` | `ponderer:smithing_bench` | str |  | — |
| `ponderer/scripts/smithing_bench.json` | `items` | `["tensura:smithing_bench"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/smithing_bench.json` | `title.en_us` | `Usage of the Smithing Bench` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/smithing_bench.json` | `title.zh_cn` | `锻造台的使用` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/smithing_bench.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/smithing_bench.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/smithing_bench.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/smithing_bench.json` | `scenes` | `[{"id": "scene_1", "title": {"en_us": "P` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/charybdis_core.json` | `id` | `ponderer:charybdis_core` | str |  | — |
| `ponderer/scripts/charybdis_core.json` | `items` | `["tensura:charybdis_core"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/charybdis_core.json` | `title.zh_cn` | `卡律布狄斯核心的介绍` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/charybdis_core.json` | `title.en_us` | `Introduction to the Charybdis Co` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/charybdis_core.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/charybdis_core.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/charybdis_core.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/charybdis_core.json` | `scenes` | `[{"id": "scene_1", "title": {"zh_cn": "地` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/mining_station.json` | `id` | `ponderer:mining_station` | str |  | — |
| `ponderer/scripts/mining_station.json` | `items` | `["tensura:mining_station"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/mining_station.json` | `title.en_us` | `New Scene - mining_station` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/mining_station.json` | `title.zh_cn` | `Usage of the Mining Station` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/mining_station.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/mining_station.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/mining_station.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/mining_station.json` | `scenes` | `[{"id": "scene_1", "title": {"en_us": "S` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/orb_of_prophec.json` | `id` | `ponderer:orb_of_prophec` | str |  | — |
| `ponderer/scripts/orb_of_prophec.json` | `items` | `["eternal_starlight:orb_of_prophec"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/orb_of_prophec.json` | `title.en_us` | `New Scene - orb_of_prophec` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/orb_of_prophec.json` | `title.zh_cn` | `新场景 - orb_of_prophec` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/orb_of_prophec.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/orb_of_prophec.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/orb_of_prophec.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/orb_of_prophec.json` | `scenes` | `[{"id": "scene_1", "title": {"en_us": "S` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/kiln_usage.json` | `id` | `ponderer:kiln_usage` | str |  | — |
| `ponderer/scripts/kiln_usage.json` | `items` | `["tensura:kiln"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/kiln_usage.json` | `title.en_us` | `Kiln Usage Tutorial` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/kiln_usage.json` | `title.zh_cn` | `窑炉使用教程` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/kiln_usage.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/kiln_usage.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/kiln_usage.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/kiln_usage.json` | `scenes` | `[{"id": "s_90d04", "title": {"zh_cn": "G` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/alchemy_furnace.json` | `id` | `ponderer:alchemy_furnace` | str |  | — |
| `ponderer/scripts/alchemy_furnace.json` | `items` | `["mortal_cultivation:alchemy_furnace"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/alchemy_furnace.json` | `title.en_us` | `New Scene - alchemy_furnace` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/alchemy_furnace.json` | `title.zh_cn` | `炼丹炉的介绍` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/alchemy_furnace.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/alchemy_furnace.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/alchemy_furnace.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/alchemy_furnace.json` | `scenes` | `[{"id": "s_5c277", "title": {"zh_cn": ""` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/orb_of_prophecy.json` | `id` | `ponderer:orb_of_prophecy` | str |  | — |
| `ponderer/scripts/orb_of_prophecy.json` | `items` | `["eternal_starlight:orb_of_prophecy"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/orb_of_prophecy.json` | `title.en_us` | `New Scene - orb_of_prophecy` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/orb_of_prophecy.json` | `title.zh_cn` | `新场景 - orb_of_prophecy` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/orb_of_prophecy.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/orb_of_prophecy.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/orb_of_prophecy.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/orb_of_prophecy.json` | `scenes` | `[{"id": "scene_1", "title": {"en_us": "S` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/book.json` | `id` | `ponderer:book` | str |  | — |
| `ponderer/scripts/book.json` | `items` | `["ftbquests:book"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/book.json` | `title.zh_cn` | `这里是任务书？` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/book.json` | `title.en_us` | `Is this the quest book?` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/book.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/book.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/book.json` | `editable` | `True` | bool |  | `cloth_config:AbstractConfigListEntry`、`cloth_config:AbstractConfigScreen`、`cloth_config:ConfigBuilderImpl` |
| `ponderer/scripts/book.json` | `scenes` | `[{"id": "scene_1", "title": {"zh_cn": "利` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/scripts/battlewill_manual.json` | `id` | `ponderer:battlewill_manual` | str |  | — |
| `ponderer/scripts/battlewill_manual.json` | `items` | `["tensura:battlewill_manual"]` | list |  | `ancient_artifacts:PlatformEquipmentImpl`、`architectury:ColorHandlerRegistry`、`architectury:ColorHandlerRegistryImpl` |
| `ponderer/scripts/battlewill_manual.json` | `title.en_us` | `Battlewill Manual - Dungeon Loot & Skill` | str |  | `artifacts:Language`、`block_factorys_bosses:BossesRiseLangProvider`、`chronodawn:LocalizedText` |
| `ponderer/scripts/battlewill_manual.json` | `title.zh_cn` | `战斗意志手册 - 地牢战利品与技能学习` | str |  | `constructionwand:LanguageGenerator`、`foxablazeaqzl_wiki:FoxItemWikiData`、`foxablazeaqzl_wiki:FoxWikiData` |
| `ponderer/scripts/battlewill_manual.json` | `structures` | `["ponderer:basic"]` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `ponderer/scripts/battlewill_manual.json` | `tags` | `[]` | list |  | `architectury:BiomeModifications`、`architectury:BiomeModificationsImpl`、`architectury:RegistrySupplierImpl` |
| `ponderer/scripts/battlewill_manual.json` | `scenes` | `[{"id": "obtainment", "title": {"en_us":` | list |  | `kaleidoscope_cookery:EnamelBasinScenes`、`kaleidoscope_cookery:KitchenBlockPonderScreen`、`kaleidoscope_cookery:MillstoneScenes` |
| `ponderer/prompts/json_system.txt` | `Top-level fields` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "id"` | `string (required) - Unique scene identif` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "items"` | `string[] (required) - Item IDs that trig` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "title"` | `{"en_us": "...", "zh_cn": "..."} - Local` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "structures"` | `string[] - Structure pool; each entry is` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "tags"` | `string[] - Tags for categorization (usua` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "steps"` | `[] - MUST be empty array; all steps go i` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "scenes"` | `array of scene segments (each segment is` | string |  | — |
| `ponderer/prompts/json_system.txt` | `Each scene segment` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "id"` | `string - Segment identifier (unique with` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "title"` | `{"en_us": "...", "zh_cn": "..."} - Segme` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- "steps"` | `array of step objects executed sequentia` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Renders the 3D structure into the scene.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `At the beginning of every scene segment.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- structure (string, required)` | `resource id referencing an entry in the ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- height (int, optional)` | `if set (≥0), only shows blocks from this` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- scale (float, optional): scene zoom factor. >1` | `zoom in (larger), <1 = zoom out (smaller` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"show_structure", "structure":"p` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example with scale**` | `{"type":"show_structure", "structure":"p` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Pauses the scene for a given number of t` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `Between almost every other step. Without` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- duration (int, optional, default 20): pause length in game ticks (20` | `1 second)` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"idle", "duration":60}` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Shows a floating text tooltip in the 3D ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `Whenever you need to annotate, explain, ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**IMPORTANT — overlap**` | `a text label stays visible for exactly "` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- text (LocalizedText, required)` | `{"en_us":"English text", "zh_cn":"中文文本"}` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- point (float[3], required): the 3D coordinate the text points at. Us` | `center of block at [2,1,3])` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- duration (int, optional, default 40)` | `how long the text stays visible (ticks)` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- color (string, optional)` | `text color theme. Values: "green" (gener` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- placeNearTarget (bool, optional)` | `if true, the text label floats near the ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"text", "text":{"en_us":"This le` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Places a block at a position (or fills a` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When demonstrating block placement, show` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**IMPORTANT**` | `Check the structure's Y-layer layout in ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- block (string, required)` | `block id (e.g. "minecraft:redstone_lamp"` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `position to place the block [x, y, z]` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], optional)` | `if provided, fills the entire region fro` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockProperties (object, optional)` | `block state properties as key-value pair` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- spawnParticles (bool, optional, default false)` | `if true, shows block break particles whe` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"set_block", "block":"minecraft:` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Shows an on-screen control hint icon wit` | string |  | — |
| `ponderer/prompts/json_system.txt` | `1. **Player interaction hint**` | `Show a mouse action (left-click, right-c` | string |  | — |
| `ponderer/prompts/json_system.txt` | `2. **Machine input/output indicator**` | `Show an item, fluid, or chemical at a ma` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**CRITICAL**` | `show_controls WITHOUT an "item" field di` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- Teaching player interaction` | `"right-click this lever", "scroll on the` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- Showing machine I/O` | `"this port accepts Water", "this outputs` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- Pointing at a component to explain it: set "item" to that component'` | `"mekanismgenerators:electromagnetic_coil` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- point (float[3], required)` | `position in the scene where the indicato` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- direction (string, required)` | `which side of the point the icon appears` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- action (string, optional)` | `input type. Values: "left" (left-click),` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- duration (int, optional, default 40)` | `how long the indicator is shown` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- item (string, REQUIRED)` | `registry ID of the item, block, fluid, o` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- whileSneaking (bool, optional)` | `if true, shows a "while sneaking" modifi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- whileCTRL (bool, optional)` | `if true, shows a "while holding CTRL" mo` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example (player interaction)**` | `{"type":"show_controls", "point":[2.5,2.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example (machine I/O)**` | `{"type":"show_controls", "point":[3.5,1.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Spawns a floating item entity (dropped i` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When showing item drops, hopper transpor` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- item (string, required)` | `item id (e.g. "minecraft:diamond", "mine` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- pos (float[3], required)` | `spawn position (can also use "point" fie` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- motion (float[3], REQUIRED)` | `initial velocity [vx, vy, vz]. ALWAYS in` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- count (int, optional, default 1)` | `number of items to display in the stack` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"create_item_entity", "item":"mi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Removes item entities (dropped items) fr` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When cleaning up after demonstrating ite` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- item (string, optional)` | `item id to filter (e.g. "minecraft:diamo` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- fullScene (bool, optional)` | `if true, clears item entities in the ent` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], optional)` | `one corner of the region to clear (requi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], optional)` | `opposite corner of the region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"clear_item_entities", "fullScen` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example (filtered)**` | `{"type":"clear_item_entities", "item":"m` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Smoothly rotates the camera around the Y` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When you want to show the build from a d` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- degrees (float, optional, default 90): rotation angle in degrees. Po` | `clockwise when viewed from above. Use 90` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"rotate_camera_y", "degrees":-90` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Plays a sound effect during the scene. A` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When a block activates (piston extending` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- sound (string, required)` | `sound event id (e.g. "minecraft:block.pi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- soundVolume (float, optional, default 1.0)` | `volume multiplier` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- pitch (float, optional, default 1.0): pitch multiplier (>1` | `higher, <1 = lower)` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- source (string, optional, default "master")` | `sound category. Values: "master", "music` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"play_sound", "sound":"minecraft` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Hides a rectangular section of blocks wi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When you want to remove part of the stru` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `one corner of the section to hide` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], required)` | `opposite corner` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- direction (string, required)` | `slide-out direction. Values: "up", "down` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"hide_section", "blockPos":[0,2,` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Shows a rectangular section of blocks wi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When building up a structure piece by pi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `one corner of the section` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], required)` | `opposite corner` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- direction (string, required)` | `direction the section slides IN from. Va` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- linkId (string, optional, default "default")` | `identifier for this section. Use unique ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"show_section_and_merge", "block` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Removes a block from the scene, optional` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When showing what happens when a block i` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `position of the block to destroy` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- destroyParticles (bool, optional, default true)` | `if true, shows block breaking particle e` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"destroy_block", "blockPos":[2,1` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Replaces all blocks in a region with a n` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When swapping materials in a region, or ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- block (string, required)` | `the new block id` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `one corner of the region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], required)` | `opposite corner of the region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockProperties (object, optional)` | `block state properties` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- spawnParticles (bool, optional, default false)` | `show particles during replacement` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"replace_blocks", "block":"minec` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Toggles redstone power state for blocks ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When demonstrating redstone circuits — f` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `one corner of the region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], optional)` | `opposite corner. If omitted, affects onl` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"toggle_redstone_power", "blockP` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Shows a pulsing green checkmark/success ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `Sparingly. Use only at true conclusion p` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `position to show the indicator` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"indicate_success", "blockPos":[` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Shows a pulsing red particle indicator a` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When you want to highlight that a specif` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `position to show the indicator` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"indicate_redstone", "blockPos":` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `key (string), point (float[3]), duration` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `bounds (int[3]) — new scene dimensions [` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Directly modifies the NBT data of a bloc` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When you need to show specific block ent` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], required)` | `position of the block entity` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], optional)` | `opposite corner for a region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- nbt (string, required)` | `SNBT string to merge into the block enti` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- reDrawBlocks (bool, optional, default false)` | `if true, forces the block to re-render a` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"modify_block_entity_nbt", "bloc` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Spawns a living entity (mob) into the sc` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When the tutorial involves mobs — e.g. s` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- entity (string, required)` | `entity type id (e.g. "minecraft:zombie",` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- pos (float[3], required)` | `spawn position (can also use "point" fie` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- yaw (float, optional)` | `horizontal rotation in degrees (facing d` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- pitch (float, optional)` | `vertical head tilt in degrees` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- lookAt (float[3], optional)` | `alternative to yaw/pitch — entity will f` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"create_entity", "entity":"minec` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Removes living entities (mobs) from the ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- entity (string, optional)` | `entity type id to filter. If omitted, cl` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- fullScene (bool, optional)` | `if true, clears entities in the entire s` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos (int[3], optional)` | `one corner of the region to clear (requi` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- blockPos2 (int[3], optional)` | `opposite corner of the region` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"clear_entities", "fullScene":tr` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Smoothly rotates a section of blocks aro` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When demonstrating mechanical rotation. ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- linkId (string, required)` | `the section to rotate (must match a prev` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- duration (int, optional, default 20)` | `animation duration in ticks` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- rotX (float, optional)` | `rotation around X axis in degrees` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- rotY (float, optional)` | `rotation around Y axis in degrees (if om` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- rotZ (float, optional)` | `rotation around Z axis in degrees` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- degrees (float, optional)` | `shorthand for rotY if rotY is not specif` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"rotate_section", "linkId":"gear` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Purpose**` | `Smoothly translates a section of blocks ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**When to use**` | `When showing pistons pushing blocks, doo` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Fields**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- linkId (string, required)` | `the section to move (must match a previo` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- duration (int, optional, default 20)` | `animation duration in ticks` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- offset (float[3], required)` | `movement offset [dx, dy, dz] in blocks` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Example**` | `{"type":"move_section", "linkId":"piston` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- X` | `east(+)/west(-), Y: up(+)/down(-), Z: so` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- For "point" fields (text, show_controls, entity pos)` | `use float values.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- Block center` | `[bx+0.5, by+0.5, bz+0.5]` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- **Block face targeting** (for annotations)` | `use an INTEGER value on one axis to sele` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- West face (visible): X` | `bx (integer), e.g. block [2,1,3] → west ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- North face (visible): Z` | `bz (integer), e.g. block [2,1,3] → north` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- East face (hidden): X` | `bx+1 — AVOID unless camera is rotated` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- South face (hidden): Z` | `bz+1 — AVOID unless camera is rotated` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- For "blockPos" fields (set_block, destroy_block, etc.)` | `use integer values [x, y, z]` | string |  | — |
| `ponderer/prompts/json_system.txt` | `The player sees the **north face (Z` | `0 side)** and the **west face (X=0 side)` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**CRITICAL — Point Coordinate Rules**` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `1. For show_controls "position" and text "point", target a VISIBLE fac` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- North face of block [bx,by,bz]: point` | `[bx+0.5, by+0.5, bz] (Z = integer = nort` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- West face of block [bx,by,bz]: point` | `[bx, by+0.5, bz+0.5] (X = integer = west` | string |  | — |
| `ponderer/prompts/json_system.txt` | `2. **Choose which block to annotate**: If equivalent blocks exist on m` | `0 or Z=0 (visible faces).` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- Example: valves at X` | `0, X=6, Z=0, Z=6 → choose X=0 or Z=0 val` | string |  | — |
| `ponderer/prompts/json_system.txt` | `4. **Default rule**: Without a preceding rotate_camera_y, ALL show_con` | `block center = wrong for annotations.` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**Faithful to source**` | `Only include information from the outlin` | string |  | — |
| `ponderer/prompts/json_system.txt` | `Additional formatting rules` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `2. **Duration guidelines**` | `Default idle is 60 ticks. Default text/s` | string |  | — |
| `ponderer/prompts/json_system.txt` | `3. Set placeNearTarget` | `true on text steps` | string |  | — |
| `ponderer/prompts/json_system.txt` | `4. Use attachKeyFrame` | `true on important steps` | string |  | — |
| `ponderer/prompts/json_system.txt` | `7. Color conventions: "green"` | `general info, "red" = warning/important,` | string |  | — |
| `ponderer/prompts/json_system.txt` | `8. **Item entity lifecycle**` | `When demonstrating multiple items in seq` | string |  | — |
| `ponderer/prompts/json_system.txt` | `9. **show_controls explanation pattern**` | `After a show_controls step, always follo` | string |  | — |
| `ponderer/prompts/json_system.txt` | `10. **Text points to the relevant block**` | `When a text step describes a specific bl` | string |  | — |
| `ponderer/prompts/json_system.txt` | `When writing zh_cn (Chinese) text in text steps` | `` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- **Preserve proper nouns exactly**` | `Use the ORIGINAL Chinese display names f` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- **CRITICAL — ID resolution**` | `The "block" field in set_block/replace_b` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- If the mapping says `endoflame → botania` | `endoflame`, use `"block": "botania:endof` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- NEVER use a generic parent block + blockProperties/variant to select` | `"botania:specialflower"` with `"blockPro` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- **CRITICAL — Step type selection based on [kind] tag**` | `The registry mapping labels each ID with` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**As part of the world (placing/spawning in the scene) — use the FIRST` | `**` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**CORRECTION EXAMPLE**` | `If the outline writes `create_item_entit` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**As machine input/output (showing at a port/interface)` | `**` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- [item] → use **show_controls** with optional action` | `"left"/"right" (for player interaction l` | string |  | — |
| `ponderer/prompts/json_system.txt` | `**IMPORTANT**` | `If the outline uses the wrong step type ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `- **CRITICAL — No naked text**` | `Every text step MUST be preceded by (or ` | string |  | — |
| `ponderer/prompts/json_system.txt` | `After composing the complete JSON, mentally scan each scene's steps ar` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Based on the structure info and user instruction, write a concise outl` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- For each segment` | `narrative flow, which step types to use,` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Emphasize visual actions over text` | `set_block, toggle_redstone_power, show_c` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `You MUST only include information that is explicitly stated in the pro` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Content focus by subject type` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Single blocks/items**` | `Focus on FUNCTION and KEY NUMBERS (e.g.,` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Machines/multi-block structures**` | `Focus on OPERATIONAL STEPS (input → proc` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Note` | `This is a relative emphasis, not absolut` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Scene segmentation rules` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Typical scene` | `1-4 segments, each with 8-20+ steps. Avo` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Input/Output segment**` | `If the subject has inputs and/or outputs` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Default idle duration` | `60 ticks (3 seconds). Use this between m` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Default text/show_controls duration` | `50 ticks.` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **No idle needed between set_block and the text that immediately ann` | `set_block → text → idle 60.` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Visual-first ordering` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Pattern` | `set_block → text → idle 60 (no idle betw` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**Step 1 — Extract all items**` | `Before writing the outline, scan ALL inp` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Note` | `The JSON generation pass will adjust the` | string |  | `betterstrongholds:ConfigGeneralNeoForge`、`foxablazeultimate:RaphaelConfig`、`ftbxmodcompat:FTBXModConfig` |
| `ponderer/prompts/outline_system.txt` | `**Step 2 — Visual storytelling rules` | `**` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **CRITICAL**` | `When your text mentions "use [item] with` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Machine I/O**` | `When a machine inputs/outputs substances` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **CRITICAL — Text must point at the relevant block**` | `When a text step describes a specific bl` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Example` | `"阀门输入水" → show_controls at the valve blo` | string |  | `cloth_config:ClothConfigDemo`、`emi_loot:ServerResourceData`、`fdlib:TextBlockParser` |
| `ponderer/prompts/outline_system.txt` | `**BAD**: text "阀门输入水" with point` | `[3.5, 4.5, 3.5] (center of structure, no` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**GOOD**` | `show_controls at valve [1.0, 2.5, 2.0] s` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Dropped items**` | `When items exist physically in the world` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **HARD RULE — No naked text**` | `EVERY text step MUST be preceded by (or ` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**BAD pattern** (naked text wall)` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**GOOD pattern** (every text has a visual)` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `show_controls at flower item` | `"煤块" → text "最大燃烧耗时16000 tick" → idle → ` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Item entity lifecycle**` | `When showing multiple items in sequence,` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **show_controls + text explanation pattern**` | `After a show_controls step, follow with ` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **show_controls MUST have item**: Every show_controls step MUST spec` | `"电磁线圈".` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**Bad example** (text-only, avoid this)` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**Good example** (visual-first demonstration)` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Key constraints to respect in your plan` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Floor awareness` | `read the structure's Y-layer layout to i` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Default structure (ponderer` | `basic): if no user NBT is provided, plan` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Available step types` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Coordinate system: X` | `east(+)/west(-), Y=up(+)/down(-), Z=sout` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `The player sees the **north face (Z` | `0 side)** and the **west face (X=0 side)` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**CRITICAL — Annotation Positioning Rules**` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- To target the **north face** (visible) of block [bx, by, bz]: use po` | `integer = north face).` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- To target the **west face** (visible) of block [bx, by, bz]: use poi` | `integer = west face).` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `2. **Example**` | `A 7×9×7 structure has a valve at block p` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- CORRECT: show_controls position` | `[0, 2.5, 3.5] — targets the WEST face (X` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- WRONG: show_controls position` | `[1, 2.5, 3.5] — targets the EAST face (h` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `Similarly, a valve at [3,2,0]: position` | `[3.5, 2.5, 0] — targets the NORTH face (` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `3. **When choosing which block to annotate**` | `If multiple equivalent blocks exist (e.g` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Example: If turbine valves exist at X` | `0 (west), X=6 (east), Z=0 (north), Z=6 (` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `4. If you MUST annotate a block on the hidden side, insert rotate_came` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `5. **Default rule**: When no camera rotation has been applied, ALL sho` | `0 or Z=0 face visible. If you find yours` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `At the very end of your response, on its own line, list every block, i` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `REQUIRED_ELEMENTS` | `<comma-separated list>` | string |  | `ponderer:AiSceneGenerator` |
| `ponderer/prompts/outline_system.txt` | `**CRITICAL**` | `The REQUIRED_ELEMENTS list MUST include ` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Entities spawned (e.g. minecraft` | `zombie)` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Sounds played (e.g. minecraft` | `block.piston.extend)` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**REQUIRED_ELEMENTS naming — THIS IS CRITICAL**` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- NEVER mix languages` | `do NOT write English names when the refe` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Only use registry ID format (mod` | `name) for entities (minecraft:zombie), s` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Example (Chinese context)` | `REQUIRED_ELEMENTS: 活塞, 红石灯, TNT, 打火石, 水,` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Example (English context)` | `REQUIRED_ELEMENTS: Piston, Redstone Lamp` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Especially do NOT skip` | `warnings, restrictions, special notes, a` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `When generating zh_cn (Chinese) text content` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- **Preserve proper nouns exactly**` | `Block names, item names, fluid names, ch` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `**Do NOT use registry IDs (e.g. minecraft` | `tnt, minecraft:flint_and_steel) for bloc` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `For modded content whose display name you are unsure of, use the ID pa` | `endoflame").` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `For entities use registry IDs (e.g. "minecraft` | `zombie", "minecraft:villager").` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `For sounds use registry IDs (e.g. "minecraft` | `block.piston.extend", "minecraft:block.l` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `CRITICAL` | `In the outline, ONLY use display names o` | string |  | `ponderer:AiSceneGenerator`、`stextras:GifTextureRenderer` |
| `ponderer/prompts/outline_system.txt` | `- Full registry IDs (e.g. do NOT write "botania` | `specialflower")` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `- Block state properties or variant selectors (e.g. do NOT write "with` | `endoflame")` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `After writing your complete outline, re-read it and check for **3 or m` | `` | string |  | — |
| `ponderer/prompts/outline_system.txt` | `2. Insert a visual step BEFORE every 2nd text onward` | `show_controls with the relevant item, cr` | string |  | — |
