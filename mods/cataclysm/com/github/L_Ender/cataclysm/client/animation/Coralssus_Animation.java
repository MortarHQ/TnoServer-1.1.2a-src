package com.github.L_Ender.cataclysm.client.animation;

import com.github.L_Ender.lionfishapi.client.model.AdvancedAnimations.AdvancedAnimationDefinition;

public class Coralssus_Animation {
   public static final AdvancedAnimationDefinition IDLE;
   public static final AdvancedAnimationDefinition WALK;
   public static final AdvancedAnimationDefinition RIGHT_FIST;
   public static final AdvancedAnimationDefinition LEFT_FIST;
   public static final AdvancedAnimationDefinition JUMPING_READY;
   public static final AdvancedAnimationDefinition JUMPING_LOOP;
   public static final AdvancedAnimationDefinition JUMPING_FISH;
   public static final AdvancedAnimationDefinition ANGRY;
   public static final AdvancedAnimationDefinition NANTA;
   public static final AdvancedAnimationDefinition DEATH;
   public static final AdvancedAnimationDefinition SWIM;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.getCopy(FastSparseSetFactory.java:95)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.getCopy(SFormsFastMapDirect.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.updateLiveMap(SSAUConstructorSparseEx.java:269)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.varReadSingleVersion(SSAUConstructorSparseEx.java:110)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.varRead(SFormsConstructor.java:168)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.VarExprent.processSforms(VarExprent.java:574)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.Exprent.processSforms(Exprent.java:317)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.processSforms(InvocationExprent.java:1894)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.processSforms(AssignmentExprent.java:311)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:127)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.splitVariables(SSAUConstructorSparseEx.java:45)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:238)
      //
      // Bytecode:
      // 0000: ldc 3.0
      // 0002: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0005: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.looping ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0008: ldc "body"
      // 000a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 000d: dup
      // 000e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0011: bipush 3
      // 0012: anewarray 28
      // 0015: dup
      // 0016: bipush 0
      // 0017: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 001a: dup
      // 001b: fconst_0
      // 001c: fconst_0
      // 001d: fconst_0
      // 001e: fconst_0
      // 001f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0022: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0025: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0028: aastore
      // 0029: dup
      // 002a: bipush 1
      // 002b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 002e: dup
      // 002f: ldc 1.5417
      // 0031: ldc 5.0
      // 0033: fconst_0
      // 0034: fconst_0
      // 0035: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0038: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 003b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 003e: aastore
      // 003f: dup
      // 0040: bipush 2
      // 0041: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0044: dup
      // 0045: ldc 3.0
      // 0047: fconst_0
      // 0048: fconst_0
      // 0049: fconst_0
      // 004a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 004d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0050: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0053: aastore
      // 0054: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0057: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 005a: ldc "chest"
      // 005c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 005f: dup
      // 0060: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0063: bipush 3
      // 0064: anewarray 28
      // 0067: dup
      // 0068: bipush 0
      // 0069: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 006c: dup
      // 006d: fconst_0
      // 006e: fconst_0
      // 006f: fconst_0
      // 0070: fconst_0
      // 0071: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0074: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0077: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 007a: aastore
      // 007b: dup
      // 007c: bipush 1
      // 007d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0080: dup
      // 0081: ldc 1.25
      // 0083: fconst_0
      // 0084: fconst_0
      // 0085: fconst_0
      // 0086: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0089: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 008c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 008f: aastore
      // 0090: dup
      // 0091: bipush 2
      // 0092: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0095: dup
      // 0096: ldc 3.0
      // 0098: fconst_0
      // 0099: fconst_0
      // 009a: fconst_0
      // 009b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 009e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 00a1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 00a4: aastore
      // 00a5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 00a8: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 00ab: ldc "head"
      // 00ad: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 00b0: dup
      // 00b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 00b4: bipush 4
      // 00b5: anewarray 28
      // 00b8: dup
      // 00b9: bipush 0
      // 00ba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 00bd: dup
      // 00be: fconst_0
      // 00bf: fconst_0
      // 00c0: fconst_0
      // 00c1: fconst_0
      // 00c2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00c5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 00c8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 00cb: aastore
      // 00cc: dup
      // 00cd: bipush 1
      // 00ce: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 00d1: dup
      // 00d2: fconst_1
      // 00d3: ldc -7.5
      // 00d5: fconst_0
      // 00d6: fconst_0
      // 00d7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00da: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 00dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 00e0: aastore
      // 00e1: dup
      // 00e2: bipush 2
      // 00e3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 00e6: dup
      // 00e7: ldc 2.25
      // 00e9: ldc 0.78
      // 00eb: fconst_0
      // 00ec: fconst_0
      // 00ed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 00f3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 00f6: aastore
      // 00f7: dup
      // 00f8: bipush 3
      // 00f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 00fc: dup
      // 00fd: ldc 3.0
      // 00ff: fconst_0
      // 0100: fconst_0
      // 0101: fconst_0
      // 0102: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0105: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0108: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 010b: aastore
      // 010c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 010f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0112: ldc "eye"
      // 0114: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0117: dup
      // 0118: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 011b: bipush 4
      // 011c: anewarray 28
      // 011f: dup
      // 0120: bipush 0
      // 0121: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0124: dup
      // 0125: fconst_0
      // 0126: fconst_0
      // 0127: fconst_0
      // 0128: fconst_0
      // 0129: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 012c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 012f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0132: aastore
      // 0133: dup
      // 0134: bipush 1
      // 0135: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0138: dup
      // 0139: ldc 1.25
      // 013b: fconst_0
      // 013c: ldc 0.5
      // 013e: fconst_0
      // 013f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0142: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0145: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0148: aastore
      // 0149: dup
      // 014a: bipush 2
      // 014b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 014e: dup
      // 014f: ldc 2.5
      // 0151: fconst_0
      // 0152: ldc -0.2
      // 0154: fconst_0
      // 0155: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0158: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 015b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 015e: aastore
      // 015f: dup
      // 0160: bipush 3
      // 0161: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0164: dup
      // 0165: ldc 3.0
      // 0167: fconst_0
      // 0168: fconst_0
      // 0169: fconst_0
      // 016a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 016d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0170: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0173: aastore
      // 0174: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0177: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 017a: ldc "right_arm"
      // 017c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 017f: dup
      // 0180: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0183: bipush 4
      // 0184: anewarray 28
      // 0187: dup
      // 0188: bipush 0
      // 0189: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 018c: dup
      // 018d: fconst_0
      // 018e: fconst_0
      // 018f: fconst_0
      // 0190: fconst_0
      // 0191: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0194: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0197: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 019a: aastore
      // 019b: dup
      // 019c: bipush 1
      // 019d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 01a0: dup
      // 01a1: ldc 1.25
      // 01a3: ldc -5.0
      // 01a5: fconst_0
      // 01a6: fconst_0
      // 01a7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01aa: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 01ad: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 01b0: aastore
      // 01b1: dup
      // 01b2: bipush 2
      // 01b3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 01b6: dup
      // 01b7: fconst_2
      // 01b8: ldc -5.29
      // 01ba: fconst_0
      // 01bb: fconst_0
      // 01bc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01bf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 01c2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 01c5: aastore
      // 01c6: dup
      // 01c7: bipush 3
      // 01c8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 01cb: dup
      // 01cc: ldc 3.0
      // 01ce: fconst_0
      // 01cf: fconst_0
      // 01d0: fconst_0
      // 01d1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01d4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 01d7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 01da: aastore
      // 01db: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 01de: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 01e1: ldc "right_arm"
      // 01e3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 01e6: dup
      // 01e7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 01ea: bipush 3
      // 01eb: anewarray 28
      // 01ee: dup
      // 01ef: bipush 0
      // 01f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 01f3: dup
      // 01f4: fconst_0
      // 01f5: fconst_0
      // 01f6: fconst_0
      // 01f7: fconst_0
      // 01f8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01fb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 01fe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0201: aastore
      // 0202: dup
      // 0203: bipush 1
      // 0204: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0207: dup
      // 0208: ldc 1.25
      // 020a: fconst_0
      // 020b: fconst_1
      // 020c: fconst_0
      // 020d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0210: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0213: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0216: aastore
      // 0217: dup
      // 0218: bipush 2
      // 0219: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 021c: dup
      // 021d: ldc 3.0
      // 021f: fconst_0
      // 0220: fconst_0
      // 0221: fconst_0
      // 0222: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0225: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0228: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 022b: aastore
      // 022c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 022f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0232: ldc "right_arm2"
      // 0234: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0237: dup
      // 0238: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 023b: bipush 2
      // 023c: anewarray 28
      // 023f: dup
      // 0240: bipush 0
      // 0241: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0244: dup
      // 0245: fconst_0
      // 0246: fconst_0
      // 0247: fconst_0
      // 0248: fconst_0
      // 0249: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 024c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 024f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0252: aastore
      // 0253: dup
      // 0254: bipush 1
      // 0255: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0258: dup
      // 0259: ldc 3.0
      // 025b: fconst_0
      // 025c: fconst_0
      // 025d: fconst_0
      // 025e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0261: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0264: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0267: aastore
      // 0268: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 026b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 026e: ldc "left_arm"
      // 0270: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0273: dup
      // 0274: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0277: bipush 4
      // 0278: anewarray 28
      // 027b: dup
      // 027c: bipush 0
      // 027d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0280: dup
      // 0281: fconst_0
      // 0282: fconst_0
      // 0283: fconst_0
      // 0284: fconst_0
      // 0285: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0288: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 028b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 028e: aastore
      // 028f: dup
      // 0290: bipush 1
      // 0291: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0294: dup
      // 0295: ldc 1.25
      // 0297: ldc -5.0
      // 0299: fconst_0
      // 029a: fconst_0
      // 029b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 029e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 02a1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 02a4: aastore
      // 02a5: dup
      // 02a6: bipush 2
      // 02a7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 02aa: dup
      // 02ab: fconst_2
      // 02ac: ldc -5.29
      // 02ae: fconst_0
      // 02af: fconst_0
      // 02b0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02b3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 02b6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 02b9: aastore
      // 02ba: dup
      // 02bb: bipush 3
      // 02bc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 02bf: dup
      // 02c0: ldc 3.0
      // 02c2: fconst_0
      // 02c3: fconst_0
      // 02c4: fconst_0
      // 02c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 02cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 02ce: aastore
      // 02cf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 02d2: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 02d5: ldc "left_arm"
      // 02d7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 02da: dup
      // 02db: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 02de: bipush 3
      // 02df: anewarray 28
      // 02e2: dup
      // 02e3: bipush 0
      // 02e4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 02e7: dup
      // 02e8: fconst_0
      // 02e9: fconst_0
      // 02ea: fconst_0
      // 02eb: fconst_0
      // 02ec: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 02ef: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 02f2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 02f5: aastore
      // 02f6: dup
      // 02f7: bipush 1
      // 02f8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 02fb: dup
      // 02fc: ldc 1.25
      // 02fe: fconst_0
      // 02ff: fconst_1
      // 0300: fconst_0
      // 0301: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0304: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0307: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 030a: aastore
      // 030b: dup
      // 030c: bipush 2
      // 030d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0310: dup
      // 0311: ldc 3.0
      // 0313: fconst_0
      // 0314: fconst_0
      // 0315: fconst_0
      // 0316: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0319: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 031c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 031f: aastore
      // 0320: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0323: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0326: ldc "left_arm2"
      // 0328: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 032b: dup
      // 032c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 032f: bipush 2
      // 0330: anewarray 28
      // 0333: dup
      // 0334: bipush 0
      // 0335: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0338: dup
      // 0339: fconst_0
      // 033a: fconst_0
      // 033b: fconst_0
      // 033c: fconst_0
      // 033d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0340: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0343: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0346: aastore
      // 0347: dup
      // 0348: bipush 1
      // 0349: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 034c: dup
      // 034d: ldc 3.0
      // 034f: fconst_0
      // 0350: fconst_0
      // 0351: fconst_0
      // 0352: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0355: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0358: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 035b: aastore
      // 035c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 035f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0362: ldc "right_leg"
      // 0364: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0367: dup
      // 0368: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 036b: bipush 2
      // 036c: anewarray 28
      // 036f: dup
      // 0370: bipush 0
      // 0371: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0374: dup
      // 0375: fconst_0
      // 0376: fconst_0
      // 0377: fconst_0
      // 0378: fconst_0
      // 0379: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 037c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 037f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0382: aastore
      // 0383: dup
      // 0384: bipush 1
      // 0385: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0388: dup
      // 0389: ldc 3.0
      // 038b: fconst_0
      // 038c: fconst_0
      // 038d: fconst_0
      // 038e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0391: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0394: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0397: aastore
      // 0398: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 039b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 039e: ldc "left_leg"
      // 03a0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 03a3: dup
      // 03a4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 03a7: bipush 2
      // 03a8: anewarray 28
      // 03ab: dup
      // 03ac: bipush 0
      // 03ad: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 03b0: dup
      // 03b1: fconst_0
      // 03b2: fconst_0
      // 03b3: fconst_0
      // 03b4: fconst_0
      // 03b5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03b8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 03bb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 03be: aastore
      // 03bf: dup
      // 03c0: bipush 1
      // 03c1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 03c4: dup
      // 03c5: ldc 3.0
      // 03c7: fconst_0
      // 03c8: fconst_0
      // 03c9: fconst_0
      // 03ca: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03cd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 03d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 03d3: aastore
      // 03d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 03d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 03da: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 03dd: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.IDLE Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 03e0: fconst_1
      // 03e1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 03e4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.looping ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 03e7: ldc "body"
      // 03e9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 03ec: dup
      // 03ed: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 03f0: bipush 3
      // 03f1: anewarray 28
      // 03f4: dup
      // 03f5: bipush 0
      // 03f6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 03f9: dup
      // 03fa: fconst_0
      // 03fb: ldc -1.864
      // 03fd: ldc 7.466
      // 03ff: ldc -0.2849
      // 0401: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0404: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0407: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 040a: aastore
      // 040b: dup
      // 040c: bipush 1
      // 040d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0410: dup
      // 0411: ldc 0.5
      // 0413: ldc -1.864
      // 0415: ldc -7.466
      // 0417: ldc 0.2849
      // 0419: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 041c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 041f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0422: aastore
      // 0423: dup
      // 0424: bipush 2
      // 0425: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0428: dup
      // 0429: fconst_1
      // 042a: ldc -1.864
      // 042c: ldc 7.466
      // 042e: ldc -0.2849
      // 0430: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0433: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0436: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0439: aastore
      // 043a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 043d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0440: ldc "body"
      // 0442: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0445: dup
      // 0446: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0449: bipush 7
      // 044b: anewarray 28
      // 044e: dup
      // 044f: bipush 0
      // 0450: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0453: dup
      // 0454: fconst_0
      // 0455: fconst_0
      // 0456: fconst_0
      // 0457: fconst_0
      // 0458: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 045b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 045e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0461: aastore
      // 0462: dup
      // 0463: bipush 1
      // 0464: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0467: dup
      // 0468: ldc 0.125
      // 046a: fconst_0
      // 046b: ldc -2.0
      // 046d: fconst_0
      // 046e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0471: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0474: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0477: aastore
      // 0478: dup
      // 0479: bipush 2
      // 047a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 047d: dup
      // 047e: ldc 0.25
      // 0480: fconst_0
      // 0481: fconst_0
      // 0482: fconst_0
      // 0483: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0486: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0489: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 048c: aastore
      // 048d: dup
      // 048e: bipush 3
      // 048f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0492: dup
      // 0493: ldc 0.5
      // 0495: fconst_0
      // 0496: fconst_0
      // 0497: fconst_0
      // 0498: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 049b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 049e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 04a1: aastore
      // 04a2: dup
      // 04a3: bipush 4
      // 04a4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 04a7: dup
      // 04a8: ldc 0.625
      // 04aa: fconst_0
      // 04ab: ldc -2.0
      // 04ad: fconst_0
      // 04ae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 04b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 04b7: aastore
      // 04b8: dup
      // 04b9: bipush 5
      // 04ba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 04bd: dup
      // 04be: ldc 0.75
      // 04c0: fconst_0
      // 04c1: fconst_0
      // 04c2: fconst_0
      // 04c3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04c6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 04c9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 04cc: aastore
      // 04cd: dup
      // 04ce: bipush 6
      // 04d0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 04d3: dup
      // 04d4: fconst_1
      // 04d5: fconst_0
      // 04d6: fconst_0
      // 04d7: fconst_0
      // 04d8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04db: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 04de: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 04e1: aastore
      // 04e2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 04e5: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 04e8: ldc "chest"
      // 04ea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 04ed: dup
      // 04ee: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 04f1: bipush 3
      // 04f2: anewarray 28
      // 04f5: dup
      // 04f6: bipush 0
      // 04f7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 04fa: dup
      // 04fb: fconst_0
      // 04fc: ldc 5.2921
      // 04fe: ldc -11.987
      // 0500: ldc 2.0843
      // 0502: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0505: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0508: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 050b: aastore
      // 050c: dup
      // 050d: bipush 1
      // 050e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0511: dup
      // 0512: ldc 0.5
      // 0514: ldc 5.2921
      // 0516: ldc 11.987
      // 0518: ldc -2.0843
      // 051a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 051d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0520: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0523: aastore
      // 0524: dup
      // 0525: bipush 2
      // 0526: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0529: dup
      // 052a: fconst_1
      // 052b: ldc 5.2921
      // 052d: ldc -11.987
      // 052f: ldc 2.0843
      // 0531: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0534: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0537: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 053a: aastore
      // 053b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 053e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0541: ldc "chest"
      // 0543: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0546: dup
      // 0547: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 054a: bipush 6
      // 054c: anewarray 28
      // 054f: dup
      // 0550: bipush 0
      // 0551: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0554: dup
      // 0555: fconst_0
      // 0556: fconst_0
      // 0557: fconst_0
      // 0558: fconst_0
      // 0559: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 055c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 055f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0562: aastore
      // 0563: dup
      // 0564: bipush 1
      // 0565: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0568: dup
      // 0569: ldc 0.25
      // 056b: fconst_0
      // 056c: fconst_0
      // 056d: fconst_0
      // 056e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0571: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0574: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0577: aastore
      // 0578: dup
      // 0579: bipush 2
      // 057a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 057d: dup
      // 057e: ldc 0.375
      // 0580: fconst_0
      // 0581: ldc -0.5
      // 0583: fconst_0
      // 0584: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0587: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 058a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 058d: aastore
      // 058e: dup
      // 058f: bipush 3
      // 0590: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0593: dup
      // 0594: ldc 0.75
      // 0596: fconst_0
      // 0597: fconst_0
      // 0598: fconst_0
      // 0599: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 059c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 059f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 05a2: aastore
      // 05a3: dup
      // 05a4: bipush 4
      // 05a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 05a8: dup
      // 05a9: ldc 0.875
      // 05ab: fconst_0
      // 05ac: ldc -0.5
      // 05ae: fconst_0
      // 05af: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05b2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 05b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 05b8: aastore
      // 05b9: dup
      // 05ba: bipush 5
      // 05bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 05be: dup
      // 05bf: fconst_1
      // 05c0: fconst_0
      // 05c1: fconst_0
      // 05c2: fconst_0
      // 05c3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05c6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 05c9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 05cc: aastore
      // 05cd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 05d0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 05d3: ldc "head"
      // 05d5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 05d8: dup
      // 05d9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 05dc: bipush 3
      // 05dd: anewarray 28
      // 05e0: dup
      // 05e1: bipush 0
      // 05e2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 05e5: dup
      // 05e6: fconst_0
      // 05e7: ldc 2.4526
      // 05e9: ldc 0.729
      // 05eb: ldc -1.6586
      // 05ed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 05f3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 05f6: aastore
      // 05f7: dup
      // 05f8: bipush 1
      // 05f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 05fc: dup
      // 05fd: ldc 0.5
      // 05ff: ldc 2.4526
      // 0601: ldc -0.729
      // 0603: ldc 1.6586
      // 0605: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0608: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 060b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 060e: aastore
      // 060f: dup
      // 0610: bipush 2
      // 0611: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0614: dup
      // 0615: fconst_1
      // 0616: ldc 2.4526
      // 0618: ldc 0.729
      // 061a: ldc -1.6586
      // 061c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 061f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0622: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0625: aastore
      // 0626: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0629: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 062c: ldc "eye"
      // 062e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0631: dup
      // 0632: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0635: bipush 7
      // 0637: anewarray 28
      // 063a: dup
      // 063b: bipush 0
      // 063c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 063f: dup
      // 0640: fconst_0
      // 0641: fconst_0
      // 0642: fconst_0
      // 0643: fconst_0
      // 0644: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0647: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 064a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 064d: aastore
      // 064e: dup
      // 064f: bipush 1
      // 0650: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0653: dup
      // 0654: ldc 0.125
      // 0656: fconst_0
      // 0657: ldc 0.4
      // 0659: fconst_0
      // 065a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 065d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0660: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0663: aastore
      // 0664: dup
      // 0665: bipush 2
      // 0666: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0669: dup
      // 066a: ldc 0.3333
      // 066c: fconst_0
      // 066d: ldc -0.2
      // 066f: fconst_0
      // 0670: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0673: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0676: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0679: aastore
      // 067a: dup
      // 067b: bipush 3
      // 067c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 067f: dup
      // 0680: ldc 0.5
      // 0682: fconst_0
      // 0683: fconst_0
      // 0684: fconst_0
      // 0685: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0688: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 068b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 068e: aastore
      // 068f: dup
      // 0690: bipush 4
      // 0691: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0694: dup
      // 0695: ldc 0.625
      // 0697: fconst_0
      // 0698: ldc 0.4
      // 069a: fconst_0
      // 069b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 069e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 06a1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 06a4: aastore
      // 06a5: dup
      // 06a6: bipush 5
      // 06a7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 06aa: dup
      // 06ab: ldc 0.8333
      // 06ad: fconst_0
      // 06ae: ldc -0.2
      // 06b0: fconst_0
      // 06b1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06b4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 06b7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 06ba: aastore
      // 06bb: dup
      // 06bc: bipush 6
      // 06be: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 06c1: dup
      // 06c2: fconst_1
      // 06c3: fconst_0
      // 06c4: fconst_0
      // 06c5: fconst_0
      // 06c6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06c9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 06cc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 06cf: aastore
      // 06d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 06d3: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 06d6: ldc "right_arm"
      // 06d8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 06db: dup
      // 06dc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 06df: bipush 4
      // 06e0: anewarray 28
      // 06e3: dup
      // 06e4: bipush 0
      // 06e5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 06e8: dup
      // 06e9: fconst_0
      // 06ea: ldc -55.411
      // 06ec: ldc 5.7162
      // 06ee: ldc -8.2189
      // 06f0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06f3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 06f6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 06f9: aastore
      // 06fa: dup
      // 06fb: bipush 1
      // 06fc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 06ff: dup
      // 0700: ldc 0.0833
      // 0702: ldc -29.24
      // 0704: fconst_0
      // 0705: fconst_0
      // 0706: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0709: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 070c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 070f: aastore
      // 0710: dup
      // 0711: bipush 2
      // 0712: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0715: dup
      // 0716: ldc 0.5
      // 0718: ldc -7.5
      // 071a: fconst_0
      // 071b: fconst_0
      // 071c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 071f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0722: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0725: aastore
      // 0726: dup
      // 0727: bipush 3
      // 0728: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 072b: dup
      // 072c: fconst_1
      // 072d: ldc -55.411
      // 072f: ldc 5.7162
      // 0731: ldc -8.2189
      // 0733: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0736: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0739: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 073c: aastore
      // 073d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0740: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0743: ldc "right_arm"
      // 0745: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0748: dup
      // 0749: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 074c: bipush 6
      // 074e: anewarray 28
      // 0751: dup
      // 0752: bipush 0
      // 0753: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0756: dup
      // 0757: fconst_0
      // 0758: fconst_0
      // 0759: ldc 3.0
      // 075b: fconst_0
      // 075c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 075f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0762: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0765: aastore
      // 0766: dup
      // 0767: bipush 1
      // 0768: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 076b: dup
      // 076c: ldc 0.0833
      // 076e: fconst_0
      // 076f: ldc 2.23
      // 0771: fconst_0
      // 0772: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0775: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0778: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 077b: aastore
      // 077c: dup
      // 077d: bipush 2
      // 077e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0781: dup
      // 0782: ldc 0.25
      // 0784: fconst_0
      // 0785: ldc -0.93
      // 0787: fconst_0
      // 0788: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 078b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 078e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0791: aastore
      // 0792: dup
      // 0793: bipush 3
      // 0794: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0797: dup
      // 0798: ldc 0.5
      // 079a: fconst_0
      // 079b: fconst_0
      // 079c: ldc 3.0
      // 079e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07a1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 07a4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 07a7: aastore
      // 07a8: dup
      // 07a9: bipush 4
      // 07aa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 07ad: dup
      // 07ae: ldc 0.75
      // 07b0: fconst_0
      // 07b1: ldc 1.56
      // 07b3: fconst_0
      // 07b4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07b7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 07ba: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 07bd: aastore
      // 07be: dup
      // 07bf: bipush 5
      // 07c0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 07c3: dup
      // 07c4: fconst_1
      // 07c5: fconst_0
      // 07c6: ldc 3.0
      // 07c8: fconst_0
      // 07c9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07cc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 07cf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 07d2: aastore
      // 07d3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 07d6: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 07d9: ldc "right_arm2"
      // 07db: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 07de: dup
      // 07df: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 07e2: bipush 4
      // 07e3: anewarray 28
      // 07e6: dup
      // 07e7: bipush 0
      // 07e8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 07eb: dup
      // 07ec: fconst_0
      // 07ed: ldc 30.0
      // 07ef: fconst_0
      // 07f0: fconst_0
      // 07f1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07f4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 07f7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 07fa: aastore
      // 07fb: dup
      // 07fc: bipush 1
      // 07fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0800: dup
      // 0801: ldc 0.0833
      // 0803: ldc 20.0
      // 0805: fconst_0
      // 0806: fconst_0
      // 0807: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 080a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 080d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0810: aastore
      // 0811: dup
      // 0812: bipush 2
      // 0813: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0816: dup
      // 0817: ldc 0.5
      // 0819: ldc 5.0
      // 081b: fconst_0
      // 081c: fconst_0
      // 081d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0820: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0823: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0826: aastore
      // 0827: dup
      // 0828: bipush 3
      // 0829: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 082c: dup
      // 082d: fconst_1
      // 082e: ldc 30.0
      // 0830: fconst_0
      // 0831: fconst_0
      // 0832: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0835: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0838: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 083b: aastore
      // 083c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 083f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0842: ldc "left_arm"
      // 0844: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0847: dup
      // 0848: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 084b: bipush 4
      // 084c: anewarray 28
      // 084f: dup
      // 0850: bipush 0
      // 0851: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0854: dup
      // 0855: fconst_0
      // 0856: ldc -7.5
      // 0858: fconst_0
      // 0859: fconst_0
      // 085a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 085d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0860: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0863: aastore
      // 0864: dup
      // 0865: bipush 1
      // 0866: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0869: dup
      // 086a: ldc 0.5
      // 086c: ldc -55.411
      // 086e: ldc -5.7162
      // 0870: ldc 8.2189
      // 0872: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0875: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0878: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 087b: aastore
      // 087c: dup
      // 087d: bipush 2
      // 087e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0881: dup
      // 0882: ldc 0.5833
      // 0884: ldc -29.24
      // 0886: fconst_0
      // 0887: fconst_0
      // 0888: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 088b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 088e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0891: aastore
      // 0892: dup
      // 0893: bipush 3
      // 0894: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0897: dup
      // 0898: fconst_1
      // 0899: ldc -7.5
      // 089b: fconst_0
      // 089c: fconst_0
      // 089d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 08a3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 08a6: aastore
      // 08a7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 08aa: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 08ad: ldc "left_arm"
      // 08af: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 08b2: dup
      // 08b3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 08b6: bipush 5
      // 08b7: anewarray 28
      // 08ba: dup
      // 08bb: bipush 0
      // 08bc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 08bf: dup
      // 08c0: fconst_0
      // 08c1: fconst_0
      // 08c2: fconst_1
      // 08c3: ldc 3.0
      // 08c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 08cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 08ce: aastore
      // 08cf: dup
      // 08d0: bipush 1
      // 08d1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 08d4: dup
      // 08d5: ldc 0.25
      // 08d7: fconst_0
      // 08d8: ldc 0.95
      // 08da: fconst_0
      // 08db: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08de: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 08e1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 08e4: aastore
      // 08e5: dup
      // 08e6: bipush 2
      // 08e7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 08ea: dup
      // 08eb: ldc 0.5
      // 08ed: fconst_0
      // 08ee: ldc 3.0
      // 08f0: fconst_0
      // 08f1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08f4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 08f7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 08fa: aastore
      // 08fb: dup
      // 08fc: bipush 3
      // 08fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0900: dup
      // 0901: ldc 0.75
      // 0903: fconst_0
      // 0904: ldc -0.92
      // 0906: fconst_0
      // 0907: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 090a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 090d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0910: aastore
      // 0911: dup
      // 0912: bipush 4
      // 0913: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0916: dup
      // 0917: fconst_1
      // 0918: fconst_0
      // 0919: fconst_1
      // 091a: ldc 3.0
      // 091c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 091f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0922: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0925: aastore
      // 0926: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0929: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 092c: ldc "left_arm2"
      // 092e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0931: dup
      // 0932: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0935: bipush 4
      // 0936: anewarray 28
      // 0939: dup
      // 093a: bipush 0
      // 093b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 093e: dup
      // 093f: fconst_0
      // 0940: ldc 5.0
      // 0942: fconst_0
      // 0943: fconst_0
      // 0944: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0947: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 094a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 094d: aastore
      // 094e: dup
      // 094f: bipush 1
      // 0950: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0953: dup
      // 0954: ldc 0.5
      // 0956: ldc 30.0
      // 0958: fconst_0
      // 0959: fconst_0
      // 095a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 095d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0960: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0963: aastore
      // 0964: dup
      // 0965: bipush 2
      // 0966: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0969: dup
      // 096a: ldc 0.5833
      // 096c: ldc 20.0
      // 096e: fconst_0
      // 096f: fconst_0
      // 0970: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0973: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0976: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0979: aastore
      // 097a: dup
      // 097b: bipush 3
      // 097c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 097f: dup
      // 0980: fconst_1
      // 0981: ldc 5.0
      // 0983: fconst_0
      // 0984: fconst_0
      // 0985: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0988: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 098b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 098e: aastore
      // 098f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0992: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0995: ldc "right_leg"
      // 0997: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 099a: dup
      // 099b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 099e: bipush 3
      // 099f: anewarray 28
      // 09a2: dup
      // 09a3: bipush 0
      // 09a4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 09a7: dup
      // 09a8: fconst_0
      // 09a9: ldc 13.9906
      // 09ab: ldc 3.795
      // 09ad: ldc -3.2653
      // 09af: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09b2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 09b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 09b8: aastore
      // 09b9: dup
      // 09ba: bipush 1
      // 09bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 09be: dup
      // 09bf: ldc 0.5
      // 09c1: ldc -10.3228
      // 09c3: ldc -0.938
      // 09c5: ldc -5.4
      // 09c7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09ca: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 09cd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 09d0: aastore
      // 09d1: dup
      // 09d2: bipush 2
      // 09d3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 09d6: dup
      // 09d7: fconst_1
      // 09d8: ldc 13.9906
      // 09da: ldc 3.795
      // 09dc: ldc -3.2653
      // 09de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 09e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 09e7: aastore
      // 09e8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 09eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 09ee: ldc "right_leg"
      // 09f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 09f3: dup
      // 09f4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 09f7: bipush 4
      // 09f8: anewarray 28
      // 09fb: dup
      // 09fc: bipush 0
      // 09fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a00: dup
      // 0a01: fconst_0
      // 0a02: fconst_0
      // 0a03: fconst_0
      // 0a04: ldc 8.0
      // 0a06: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a09: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a0c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a0f: aastore
      // 0a10: dup
      // 0a11: bipush 1
      // 0a12: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a15: dup
      // 0a16: ldc 0.5
      // 0a18: fconst_0
      // 0a19: ldc 4.0
      // 0a1b: ldc -6.0
      // 0a1d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a20: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a23: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a26: aastore
      // 0a27: dup
      // 0a28: bipush 2
      // 0a29: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a2c: dup
      // 0a2d: ldc 0.5833
      // 0a2f: fconst_0
      // 0a30: ldc -0.43
      // 0a32: ldc -4.93
      // 0a34: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a37: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a3a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a3d: aastore
      // 0a3e: dup
      // 0a3f: bipush 3
      // 0a40: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a43: dup
      // 0a44: fconst_1
      // 0a45: fconst_0
      // 0a46: fconst_0
      // 0a47: ldc 7.0
      // 0a49: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a4c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a4f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a52: aastore
      // 0a53: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0a56: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0a59: ldc "left_leg"
      // 0a5b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0a5e: dup
      // 0a5f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0a62: bipush 3
      // 0a63: anewarray 28
      // 0a66: dup
      // 0a67: bipush 0
      // 0a68: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a6b: dup
      // 0a6c: fconst_0
      // 0a6d: ldc -10.3228
      // 0a6f: ldc 0.938
      // 0a71: ldc 5.4
      // 0a73: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a76: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a79: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a7c: aastore
      // 0a7d: dup
      // 0a7e: bipush 1
      // 0a7f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a82: dup
      // 0a83: ldc 0.5
      // 0a85: ldc 13.9906
      // 0a87: ldc -3.795
      // 0a89: ldc 3.2653
      // 0a8b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a8e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0a91: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0a94: aastore
      // 0a95: dup
      // 0a96: bipush 2
      // 0a97: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0a9a: dup
      // 0a9b: fconst_1
      // 0a9c: ldc -10.3228
      // 0a9e: ldc 0.938
      // 0aa0: ldc 5.4
      // 0aa2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0aa5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0aa8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0aab: aastore
      // 0aac: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0aaf: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0ab2: ldc "left_leg"
      // 0ab4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0ab7: dup
      // 0ab8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0abb: bipush 4
      // 0abc: anewarray 28
      // 0abf: dup
      // 0ac0: bipush 0
      // 0ac1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ac4: dup
      // 0ac5: fconst_0
      // 0ac6: fconst_0
      // 0ac7: ldc 4.0
      // 0ac9: ldc -6.0
      // 0acb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ace: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ad1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ad4: aastore
      // 0ad5: dup
      // 0ad6: bipush 1
      // 0ad7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ada: dup
      // 0adb: ldc 0.0833
      // 0add: fconst_0
      // 0ade: ldc -0.25
      // 0ae0: ldc -5.37
      // 0ae2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ae5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ae8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0aeb: aastore
      // 0aec: dup
      // 0aed: bipush 2
      // 0aee: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0af1: dup
      // 0af2: ldc 0.5
      // 0af4: fconst_0
      // 0af5: fconst_0
      // 0af6: ldc 8.0
      // 0af8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0afb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0afe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b01: aastore
      // 0b02: dup
      // 0b03: bipush 3
      // 0b04: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b07: dup
      // 0b08: fconst_1
      // 0b09: fconst_0
      // 0b0a: ldc 4.0
      // 0b0c: ldc -6.0
      // 0b0e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b11: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0b14: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b17: aastore
      // 0b18: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0b1b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0b1e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 0b21: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.WALK Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 0b24: ldc 1.5
      // 0b26: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0b29: ldc "body"
      // 0b2b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0b2e: dup
      // 0b2f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0b32: bipush 6
      // 0b34: anewarray 28
      // 0b37: dup
      // 0b38: bipush 0
      // 0b39: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b3c: dup
      // 0b3d: fconst_0
      // 0b3e: fconst_0
      // 0b3f: fconst_0
      // 0b40: fconst_0
      // 0b41: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b44: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0b47: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b4a: aastore
      // 0b4b: dup
      // 0b4c: bipush 1
      // 0b4d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b50: dup
      // 0b51: ldc 0.25
      // 0b53: ldc -27.5896
      // 0b55: ldc -4.4339
      // 0b57: ldc 2.3134
      // 0b59: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b5c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0b5f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b62: aastore
      // 0b63: dup
      // 0b64: bipush 2
      // 0b65: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b68: dup
      // 0b69: ldc 0.5
      // 0b6b: ldc -28.1336
      // 0b6d: ldc 6.8547
      // 0b6f: ldc 7.6379
      // 0b71: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b74: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0b77: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b7a: aastore
      // 0b7b: dup
      // 0b7c: bipush 3
      // 0b7d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b80: dup
      // 0b81: ldc 0.625
      // 0b83: ldc -31.4254
      // 0b85: ldc -28.3238
      // 0b87: ldc -9.3577
      // 0b89: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b8c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0b8f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0b92: aastore
      // 0b93: dup
      // 0b94: bipush 4
      // 0b95: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0b98: dup
      // 0b99: fconst_1
      // 0b9a: ldc -26.4254
      // 0b9c: ldc -28.3238
      // 0b9e: ldc -9.3577
      // 0ba0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ba3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ba6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ba9: aastore
      // 0baa: dup
      // 0bab: bipush 5
      // 0bac: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0baf: dup
      // 0bb0: ldc 1.5
      // 0bb2: fconst_0
      // 0bb3: fconst_0
      // 0bb4: fconst_0
      // 0bb5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bb8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0bbb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0bbe: aastore
      // 0bbf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0bc2: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0bc5: ldc "body"
      // 0bc7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0bca: dup
      // 0bcb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0bce: bipush 3
      // 0bcf: anewarray 28
      // 0bd2: dup
      // 0bd3: bipush 0
      // 0bd4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0bd7: dup
      // 0bd8: fconst_0
      // 0bd9: fconst_0
      // 0bda: fconst_0
      // 0bdb: fconst_0
      // 0bdc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bdf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0be2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0be5: aastore
      // 0be6: dup
      // 0be7: bipush 1
      // 0be8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0beb: dup
      // 0bec: ldc 0.625
      // 0bee: fconst_0
      // 0bef: ldc -4.0
      // 0bf1: ldc -6.0
      // 0bf3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bf6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0bf9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0bfc: aastore
      // 0bfd: dup
      // 0bfe: bipush 2
      // 0bff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c02: dup
      // 0c03: ldc 1.5
      // 0c05: fconst_0
      // 0c06: fconst_0
      // 0c07: fconst_0
      // 0c08: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c0b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c0e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c11: aastore
      // 0c12: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0c15: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0c18: ldc "chest"
      // 0c1a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0c1d: dup
      // 0c1e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0c21: bipush 6
      // 0c23: anewarray 28
      // 0c26: dup
      // 0c27: bipush 0
      // 0c28: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c2b: dup
      // 0c2c: fconst_0
      // 0c2d: fconst_0
      // 0c2e: fconst_0
      // 0c2f: fconst_0
      // 0c30: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c33: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c36: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c39: aastore
      // 0c3a: dup
      // 0c3b: bipush 1
      // 0c3c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c3f: dup
      // 0c40: ldc 0.25
      // 0c42: ldc 2.4363
      // 0c44: ldc 7.082
      // 0c46: ldc 17.1819
      // 0c48: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c4b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c4e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c51: aastore
      // 0c52: dup
      // 0c53: bipush 2
      // 0c54: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c57: dup
      // 0c58: ldc 0.5
      // 0c5a: ldc 3.4023
      // 0c5c: ldc 11.8649
      // 0c5e: ldc 23.0407
      // 0c60: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c63: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c66: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c69: aastore
      // 0c6a: dup
      // 0c6b: bipush 3
      // 0c6c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c6f: dup
      // 0c70: ldc 0.625
      // 0c72: ldc 53.4023
      // 0c74: ldc 11.8649
      // 0c76: ldc 23.0407
      // 0c78: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c7b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c7e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c81: aastore
      // 0c82: dup
      // 0c83: bipush 4
      // 0c84: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c87: dup
      // 0c88: fconst_1
      // 0c89: ldc 42.8764
      // 0c8b: ldc 11.8928
      // 0c8d: ldc 20.4861
      // 0c8f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c92: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0c95: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0c98: aastore
      // 0c99: dup
      // 0c9a: bipush 5
      // 0c9b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0c9e: dup
      // 0c9f: ldc 1.5
      // 0ca1: fconst_0
      // 0ca2: fconst_0
      // 0ca3: fconst_0
      // 0ca4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ca7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0caa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0cad: aastore
      // 0cae: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0cb1: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0cb4: ldc "head"
      // 0cb6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0cb9: dup
      // 0cba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0cbd: bipush 6
      // 0cbf: anewarray 28
      // 0cc2: dup
      // 0cc3: bipush 0
      // 0cc4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0cc7: dup
      // 0cc8: fconst_0
      // 0cc9: fconst_0
      // 0cca: fconst_0
      // 0ccb: fconst_0
      // 0ccc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ccf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0cd2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0cd5: aastore
      // 0cd6: dup
      // 0cd7: bipush 1
      // 0cd8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0cdb: dup
      // 0cdc: ldc 0.25
      // 0cde: ldc 34.987
      // 0ce0: ldc -19.3289
      // 0ce2: ldc -5.5392
      // 0ce4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ce7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0cea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ced: aastore
      // 0cee: dup
      // 0cef: bipush 2
      // 0cf0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0cf3: dup
      // 0cf4: ldc 0.5
      // 0cf6: ldc 34.2775
      // 0cf8: ldc -26.6412
      // 0cfa: ldc -3.7261
      // 0cfc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cff: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d02: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d05: aastore
      // 0d06: dup
      // 0d07: bipush 3
      // 0d08: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d0b: dup
      // 0d0c: ldc 0.625
      // 0d0e: ldc -6.8062
      // 0d10: ldc -2.2153
      // 0d12: ldc -9.2775
      // 0d14: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d17: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d1a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d1d: aastore
      // 0d1e: dup
      // 0d1f: bipush 4
      // 0d20: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d23: dup
      // 0d24: fconst_1
      // 0d25: ldc 5.6844
      // 0d27: ldc 1.8465
      // 0d29: ldc -6.3612
      // 0d2b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d2e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d31: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d34: aastore
      // 0d35: dup
      // 0d36: bipush 5
      // 0d37: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d3a: dup
      // 0d3b: ldc 1.5
      // 0d3d: fconst_0
      // 0d3e: fconst_0
      // 0d3f: fconst_0
      // 0d40: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d43: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d46: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d49: aastore
      // 0d4a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0d4d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0d50: ldc "eye"
      // 0d52: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0d55: dup
      // 0d56: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0d59: bipush 5
      // 0d5a: anewarray 28
      // 0d5d: dup
      // 0d5e: bipush 0
      // 0d5f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d62: dup
      // 0d63: fconst_0
      // 0d64: fconst_0
      // 0d65: fconst_0
      // 0d66: fconst_0
      // 0d67: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d6a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d6d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d70: aastore
      // 0d71: dup
      // 0d72: bipush 1
      // 0d73: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d76: dup
      // 0d77: ldc 0.5
      // 0d79: fconst_0
      // 0d7a: ldc -0.6
      // 0d7c: fconst_0
      // 0d7d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d80: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d83: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d86: aastore
      // 0d87: dup
      // 0d88: bipush 2
      // 0d89: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0d8c: dup
      // 0d8d: ldc 0.75
      // 0d8f: fconst_0
      // 0d90: fconst_1
      // 0d91: fconst_0
      // 0d92: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d95: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0d98: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0d9b: aastore
      // 0d9c: dup
      // 0d9d: bipush 3
      // 0d9e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0da1: dup
      // 0da2: fconst_1
      // 0da3: fconst_0
      // 0da4: ldc -0.5
      // 0da6: fconst_0
      // 0da7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0daa: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0dad: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0db0: aastore
      // 0db1: dup
      // 0db2: bipush 4
      // 0db3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0db6: dup
      // 0db7: ldc 1.5
      // 0db9: fconst_0
      // 0dba: fconst_0
      // 0dbb: fconst_0
      // 0dbc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dbf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0dc2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0dc5: aastore
      // 0dc6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0dc9: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0dcc: ldc "right_arm"
      // 0dce: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0dd1: dup
      // 0dd2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0dd5: bipush 6
      // 0dd7: anewarray 28
      // 0dda: dup
      // 0ddb: bipush 0
      // 0ddc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ddf: dup
      // 0de0: fconst_0
      // 0de1: fconst_0
      // 0de2: fconst_0
      // 0de3: fconst_0
      // 0de4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0de7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0dea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ded: aastore
      // 0dee: dup
      // 0def: bipush 1
      // 0df0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0df3: dup
      // 0df4: ldc 0.25
      // 0df6: ldc -0.1981
      // 0df8: ldc 7.6388
      // 0dfa: ldc 53.0657
      // 0dfc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dff: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e02: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e05: aastore
      // 0e06: dup
      // 0e07: bipush 2
      // 0e08: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e0b: dup
      // 0e0c: ldc 0.5
      // 0e0e: ldc 2.1209
      // 0e10: ldc 7.3428
      // 0e12: ldc 70.715
      // 0e14: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e17: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e1a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e1d: aastore
      // 0e1e: dup
      // 0e1f: bipush 3
      // 0e20: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e23: dup
      // 0e24: ldc 0.625
      // 0e26: ldc -51.1635
      // 0e28: ldc -21.4568
      // 0e2a: ldc -33.9585
      // 0e2c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e2f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e32: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e35: aastore
      // 0e36: dup
      // 0e37: bipush 4
      // 0e38: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e3b: dup
      // 0e3c: fconst_1
      // 0e3d: ldc -47.2865
      // 0e3f: ldc -25.3148
      // 0e41: ldc -37.4248
      // 0e43: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e46: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e49: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e4c: aastore
      // 0e4d: dup
      // 0e4e: bipush 5
      // 0e4f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e52: dup
      // 0e53: ldc 1.5
      // 0e55: fconst_0
      // 0e56: fconst_0
      // 0e57: fconst_0
      // 0e58: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e5b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e5e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e61: aastore
      // 0e62: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0e65: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0e68: ldc "right_arm"
      // 0e6a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0e6d: dup
      // 0e6e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0e71: bipush 5
      // 0e72: anewarray 28
      // 0e75: dup
      // 0e76: bipush 0
      // 0e77: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e7a: dup
      // 0e7b: fconst_0
      // 0e7c: fconst_0
      // 0e7d: fconst_0
      // 0e7e: fconst_0
      // 0e7f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e82: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e85: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e88: aastore
      // 0e89: dup
      // 0e8a: bipush 1
      // 0e8b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0e8e: dup
      // 0e8f: ldc 0.25
      // 0e91: fconst_0
      // 0e92: ldc 1.75
      // 0e94: ldc -1.88
      // 0e96: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0e99: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0e9c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0e9f: aastore
      // 0ea0: dup
      // 0ea1: bipush 2
      // 0ea2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ea5: dup
      // 0ea6: ldc 0.5
      // 0ea8: fconst_0
      // 0ea9: ldc 3.99
      // 0eab: ldc -4.15
      // 0ead: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0eb0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0eb3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0eb6: aastore
      // 0eb7: dup
      // 0eb8: bipush 3
      // 0eb9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ebc: dup
      // 0ebd: ldc 0.625
      // 0ebf: fconst_0
      // 0ec0: fconst_2
      // 0ec1: ldc -5.0
      // 0ec3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ec6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ec9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ecc: aastore
      // 0ecd: dup
      // 0ece: bipush 4
      // 0ecf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0ed2: dup
      // 0ed3: ldc 1.5
      // 0ed5: fconst_0
      // 0ed6: fconst_0
      // 0ed7: fconst_0
      // 0ed8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0edb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ede: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ee1: aastore
      // 0ee2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0ee5: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0ee8: ldc "right_arm2"
      // 0eea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0eed: dup
      // 0eee: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0ef1: bipush 6
      // 0ef3: anewarray 28
      // 0ef6: dup
      // 0ef7: bipush 0
      // 0ef8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0efb: dup
      // 0efc: fconst_0
      // 0efd: fconst_0
      // 0efe: fconst_0
      // 0eff: fconst_0
      // 0f00: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f03: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f06: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f09: aastore
      // 0f0a: dup
      // 0f0b: bipush 1
      // 0f0c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f0f: dup
      // 0f10: ldc 0.25
      // 0f12: ldc -35.0
      // 0f14: fconst_0
      // 0f15: fconst_0
      // 0f16: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f19: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f1c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f1f: aastore
      // 0f20: dup
      // 0f21: bipush 2
      // 0f22: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f25: dup
      // 0f26: ldc 0.5
      // 0f28: ldc -33.735
      // 0f2a: ldc -9.9319
      // 0f2c: ldc -14.4817
      // 0f2e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f31: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f34: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f37: aastore
      // 0f38: dup
      // 0f39: bipush 3
      // 0f3a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f3d: dup
      // 0f3e: ldc 0.625
      // 0f40: fconst_0
      // 0f41: fconst_0
      // 0f42: fconst_0
      // 0f43: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f46: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f49: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f4c: aastore
      // 0f4d: dup
      // 0f4e: bipush 4
      // 0f4f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f52: dup
      // 0f53: fconst_1
      // 0f54: ldc -2.5
      // 0f56: fconst_0
      // 0f57: fconst_0
      // 0f58: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f5e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f61: aastore
      // 0f62: dup
      // 0f63: bipush 5
      // 0f64: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f67: dup
      // 0f68: ldc 1.5
      // 0f6a: fconst_0
      // 0f6b: fconst_0
      // 0f6c: fconst_0
      // 0f6d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f70: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f73: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f76: aastore
      // 0f77: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 0f7a: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 0f7d: ldc "left_arm"
      // 0f7f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 0f82: dup
      // 0f83: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 0f86: bipush 6
      // 0f88: anewarray 28
      // 0f8b: dup
      // 0f8c: bipush 0
      // 0f8d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0f90: dup
      // 0f91: fconst_0
      // 0f92: fconst_0
      // 0f93: fconst_0
      // 0f94: fconst_0
      // 0f95: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f98: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0f9b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0f9e: aastore
      // 0f9f: dup
      // 0fa0: bipush 1
      // 0fa1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0fa4: dup
      // 0fa5: ldc 0.25
      // 0fa7: ldc -2.5
      // 0fa9: fconst_0
      // 0faa: ldc -10.0
      // 0fac: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0faf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0fb2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0fb5: aastore
      // 0fb6: dup
      // 0fb7: bipush 2
      // 0fb8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0fbb: dup
      // 0fbc: ldc 0.5
      // 0fbe: ldc -9.5459
      // 0fc0: ldc 2.9932
      // 0fc2: ldc 7.25
      // 0fc4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fc7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0fca: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0fcd: aastore
      // 0fce: dup
      // 0fcf: bipush 3
      // 0fd0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0fd3: dup
      // 0fd4: ldc 0.625
      // 0fd6: ldc 18.092
      // 0fd8: ldc -3.4049
      // 0fda: ldc -29.7197
      // 0fdc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fdf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0fe2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0fe5: aastore
      // 0fe6: dup
      // 0fe7: bipush 4
      // 0fe8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 0feb: dup
      // 0fec: fconst_1
      // 0fed: ldc 27.7445
      // 0fef: ldc -4.9441
      // 0ff1: ldc -24.9497
      // 0ff3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ff6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 0ff9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 0ffc: aastore
      // 0ffd: dup
      // 0ffe: bipush 5
      // 0fff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1002: dup
      // 1003: ldc 1.5
      // 1005: fconst_0
      // 1006: fconst_0
      // 1007: fconst_0
      // 1008: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 100b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 100e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1011: aastore
      // 1012: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1015: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1018: ldc "left_arm"
      // 101a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 101d: dup
      // 101e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1021: bipush 4
      // 1022: anewarray 28
      // 1025: dup
      // 1026: bipush 0
      // 1027: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 102a: dup
      // 102b: fconst_0
      // 102c: fconst_0
      // 102d: fconst_0
      // 102e: fconst_0
      // 102f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1032: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1035: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1038: aastore
      // 1039: dup
      // 103a: bipush 1
      // 103b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 103e: dup
      // 103f: ldc 0.5
      // 1041: fconst_0
      // 1042: fconst_0
      // 1043: fconst_0
      // 1044: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1047: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 104a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 104d: aastore
      // 104e: dup
      // 104f: bipush 2
      // 1050: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1053: dup
      // 1054: ldc 0.625
      // 1056: fconst_0
      // 1057: ldc 3.0
      // 1059: fconst_0
      // 105a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 105d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1060: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1063: aastore
      // 1064: dup
      // 1065: bipush 3
      // 1066: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1069: dup
      // 106a: ldc 1.5
      // 106c: fconst_0
      // 106d: fconst_0
      // 106e: fconst_0
      // 106f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1072: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1075: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1078: aastore
      // 1079: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 107c: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 107f: ldc "left_arm2"
      // 1081: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1084: dup
      // 1085: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1088: bipush 6
      // 108a: anewarray 28
      // 108d: dup
      // 108e: bipush 0
      // 108f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1092: dup
      // 1093: fconst_0
      // 1094: fconst_0
      // 1095: fconst_0
      // 1096: fconst_0
      // 1097: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 109d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 10a0: aastore
      // 10a1: dup
      // 10a2: bipush 1
      // 10a3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 10a6: dup
      // 10a7: ldc 0.25
      // 10a9: ldc -17.3727
      // 10ab: ldc -0.5463
      // 10ad: ldc 1.003
      // 10af: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 10b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 10b8: aastore
      // 10b9: dup
      // 10ba: bipush 2
      // 10bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 10be: dup
      // 10bf: ldc 0.5
      // 10c1: ldc -47.3727
      // 10c3: ldc -0.5463
      // 10c5: ldc 1.003
      // 10c7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10ca: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 10cd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 10d0: aastore
      // 10d1: dup
      // 10d2: bipush 3
      // 10d3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 10d6: dup
      // 10d7: ldc 0.625
      // 10d9: ldc -23.2281
      // 10db: ldc 14.0447
      // 10dd: ldc 14.8166
      // 10df: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 10e5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 10e8: aastore
      // 10e9: dup
      // 10ea: bipush 4
      // 10eb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 10ee: dup
      // 10ef: fconst_1
      // 10f0: ldc -15.7281
      // 10f2: ldc 14.0447
      // 10f4: ldc 14.8166
      // 10f6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10f9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 10fc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 10ff: aastore
      // 1100: dup
      // 1101: bipush 5
      // 1102: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1105: dup
      // 1106: ldc 1.5
      // 1108: fconst_0
      // 1109: fconst_0
      // 110a: fconst_0
      // 110b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 110e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1111: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1114: aastore
      // 1115: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1118: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 111b: ldc "right_leg"
      // 111d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1120: dup
      // 1121: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1124: bipush 4
      // 1125: anewarray 28
      // 1128: dup
      // 1129: bipush 0
      // 112a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 112d: dup
      // 112e: fconst_0
      // 112f: fconst_0
      // 1130: fconst_0
      // 1131: fconst_0
      // 1132: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1135: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1138: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 113b: aastore
      // 113c: dup
      // 113d: bipush 1
      // 113e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1141: dup
      // 1142: ldc 0.5
      // 1144: fconst_0
      // 1145: fconst_0
      // 1146: fconst_0
      // 1147: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 114a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 114d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1150: aastore
      // 1151: dup
      // 1152: bipush 2
      // 1153: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1156: dup
      // 1157: ldc 0.625
      // 1159: ldc 70.0
      // 115b: fconst_0
      // 115c: fconst_0
      // 115d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1160: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1163: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1166: aastore
      // 1167: dup
      // 1168: bipush 3
      // 1169: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 116c: dup
      // 116d: ldc 1.5
      // 116f: fconst_0
      // 1170: fconst_0
      // 1171: fconst_0
      // 1172: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1175: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1178: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 117b: aastore
      // 117c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 117f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1182: ldc "right_leg"
      // 1184: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1187: dup
      // 1188: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 118b: bipush 6
      // 118d: anewarray 28
      // 1190: dup
      // 1191: bipush 0
      // 1192: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1195: dup
      // 1196: fconst_0
      // 1197: fconst_0
      // 1198: fconst_0
      // 1199: fconst_0
      // 119a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 119d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 11a0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 11a3: aastore
      // 11a4: dup
      // 11a5: bipush 1
      // 11a6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 11a9: dup
      // 11aa: ldc 0.25
      // 11ac: ldc -1.0
      // 11ae: fconst_0
      // 11af: fconst_0
      // 11b0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 11b3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 11b6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 11b9: aastore
      // 11ba: dup
      // 11bb: bipush 2
      // 11bc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 11bf: dup
      // 11c0: ldc 0.5
      // 11c2: ldc -1.0
      // 11c4: fconst_0
      // 11c5: fconst_2
      // 11c6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 11c9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 11cc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 11cf: aastore
      // 11d0: dup
      // 11d1: bipush 3
      // 11d2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 11d5: dup
      // 11d6: ldc 0.625
      // 11d8: ldc -1.0
      // 11da: ldc -4.0
      // 11dc: ldc -6.0
      // 11de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 11e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 11e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 11e7: aastore
      // 11e8: dup
      // 11e9: bipush 4
      // 11ea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 11ed: dup
      // 11ee: fconst_1
      // 11ef: ldc -1.0
      // 11f1: ldc -4.0
      // 11f3: ldc -7.0
      // 11f5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 11f8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 11fb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 11fe: aastore
      // 11ff: dup
      // 1200: bipush 5
      // 1201: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1204: dup
      // 1205: ldc 1.5
      // 1207: fconst_0
      // 1208: fconst_0
      // 1209: fconst_0
      // 120a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 120d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1210: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1213: aastore
      // 1214: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1217: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 121a: ldc "left_leg"
      // 121c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 121f: dup
      // 1220: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1223: bipush 5
      // 1224: anewarray 28
      // 1227: dup
      // 1228: bipush 0
      // 1229: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 122c: dup
      // 122d: fconst_0
      // 122e: fconst_0
      // 122f: fconst_0
      // 1230: fconst_0
      // 1231: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1234: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1237: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 123a: aastore
      // 123b: dup
      // 123c: bipush 1
      // 123d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1240: dup
      // 1241: ldc 0.5
      // 1243: ldc 20.4
      // 1245: fconst_0
      // 1246: fconst_0
      // 1247: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 124a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 124d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1250: aastore
      // 1251: dup
      // 1252: bipush 2
      // 1253: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1256: dup
      // 1257: ldc 0.625
      // 1259: ldc 25.0
      // 125b: fconst_0
      // 125c: fconst_0
      // 125d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1260: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1263: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1266: aastore
      // 1267: dup
      // 1268: bipush 3
      // 1269: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 126c: dup
      // 126d: fconst_1
      // 126e: ldc 37.5
      // 1270: fconst_0
      // 1271: fconst_0
      // 1272: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1275: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1278: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 127b: aastore
      // 127c: dup
      // 127d: bipush 4
      // 127e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1281: dup
      // 1282: ldc 1.5
      // 1284: fconst_0
      // 1285: fconst_0
      // 1286: fconst_0
      // 1287: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 128a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 128d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1290: aastore
      // 1291: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1294: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1297: ldc "left_leg"
      // 1299: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 129c: dup
      // 129d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 12a0: bipush 6
      // 12a2: anewarray 28
      // 12a5: dup
      // 12a6: bipush 0
      // 12a7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 12aa: dup
      // 12ab: fconst_0
      // 12ac: fconst_0
      // 12ad: fconst_0
      // 12ae: fconst_0
      // 12af: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12b2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 12b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 12b8: aastore
      // 12b9: dup
      // 12ba: bipush 1
      // 12bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 12be: dup
      // 12bf: ldc 0.25
      // 12c1: fconst_0
      // 12c2: fconst_0
      // 12c3: ldc 3.0
      // 12c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 12cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 12ce: aastore
      // 12cf: dup
      // 12d0: bipush 2
      // 12d1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 12d4: dup
      // 12d5: ldc 0.5
      // 12d7: fconst_0
      // 12d8: fconst_0
      // 12d9: ldc 7.0
      // 12db: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12de: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 12e1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 12e4: aastore
      // 12e5: dup
      // 12e6: bipush 3
      // 12e7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 12ea: dup
      // 12eb: ldc 0.625
      // 12ed: fconst_0
      // 12ee: fconst_0
      // 12ef: fconst_2
      // 12f0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12f3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 12f6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 12f9: aastore
      // 12fa: dup
      // 12fb: bipush 4
      // 12fc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 12ff: dup
      // 1300: fconst_1
      // 1301: fconst_0
      // 1302: fconst_0
      // 1303: fconst_1
      // 1304: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1307: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 130a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 130d: aastore
      // 130e: dup
      // 130f: bipush 5
      // 1310: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1313: dup
      // 1314: ldc 1.5
      // 1316: fconst_0
      // 1317: fconst_0
      // 1318: fconst_0
      // 1319: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 131c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 131f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1322: aastore
      // 1323: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1326: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1329: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 132c: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.RIGHT_FIST Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 132f: ldc 1.5
      // 1331: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1334: ldc "body"
      // 1336: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1339: dup
      // 133a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 133d: bipush 6
      // 133f: anewarray 28
      // 1342: dup
      // 1343: bipush 0
      // 1344: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1347: dup
      // 1348: fconst_0
      // 1349: fconst_0
      // 134a: fconst_0
      // 134b: fconst_0
      // 134c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 134f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1352: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1355: aastore
      // 1356: dup
      // 1357: bipush 1
      // 1358: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 135b: dup
      // 135c: ldc 0.25
      // 135e: ldc -27.5896
      // 1360: ldc 4.4339
      // 1362: ldc -2.3134
      // 1364: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1367: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 136a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 136d: aastore
      // 136e: dup
      // 136f: bipush 2
      // 1370: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1373: dup
      // 1374: ldc 0.5
      // 1376: ldc -28.1336
      // 1378: ldc -6.8547
      // 137a: ldc -7.6379
      // 137c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 137f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1382: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1385: aastore
      // 1386: dup
      // 1387: bipush 3
      // 1388: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 138b: dup
      // 138c: ldc 0.625
      // 138e: ldc -31.4254
      // 1390: ldc 28.3238
      // 1392: ldc 9.3577
      // 1394: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1397: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 139a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 139d: aastore
      // 139e: dup
      // 139f: bipush 4
      // 13a0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 13a3: dup
      // 13a4: fconst_1
      // 13a5: ldc -26.4254
      // 13a7: ldc 28.3238
      // 13a9: ldc 9.3577
      // 13ab: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ae: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 13b1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 13b4: aastore
      // 13b5: dup
      // 13b6: bipush 5
      // 13b7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 13ba: dup
      // 13bb: ldc 1.5
      // 13bd: fconst_0
      // 13be: fconst_0
      // 13bf: fconst_0
      // 13c0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13c3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 13c6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 13c9: aastore
      // 13ca: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 13cd: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 13d0: ldc "body"
      // 13d2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 13d5: dup
      // 13d6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 13d9: bipush 3
      // 13da: anewarray 28
      // 13dd: dup
      // 13de: bipush 0
      // 13df: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 13e2: dup
      // 13e3: fconst_0
      // 13e4: fconst_0
      // 13e5: fconst_0
      // 13e6: fconst_0
      // 13e7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 13ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 13ed: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 13f0: aastore
      // 13f1: dup
      // 13f2: bipush 1
      // 13f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 13f6: dup
      // 13f7: ldc 0.625
      // 13f9: fconst_0
      // 13fa: ldc -4.0
      // 13fc: ldc -6.0
      // 13fe: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1401: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1404: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1407: aastore
      // 1408: dup
      // 1409: bipush 2
      // 140a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 140d: dup
      // 140e: ldc 1.5
      // 1410: fconst_0
      // 1411: fconst_0
      // 1412: fconst_0
      // 1413: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1416: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1419: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 141c: aastore
      // 141d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1420: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1423: ldc "chest"
      // 1425: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1428: dup
      // 1429: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 142c: bipush 6
      // 142e: anewarray 28
      // 1431: dup
      // 1432: bipush 0
      // 1433: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1436: dup
      // 1437: fconst_0
      // 1438: fconst_0
      // 1439: fconst_0
      // 143a: fconst_0
      // 143b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 143e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1441: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1444: aastore
      // 1445: dup
      // 1446: bipush 1
      // 1447: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 144a: dup
      // 144b: ldc 0.25
      // 144d: ldc 2.4363
      // 144f: ldc -7.082
      // 1451: ldc -17.1819
      // 1453: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1456: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1459: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 145c: aastore
      // 145d: dup
      // 145e: bipush 2
      // 145f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1462: dup
      // 1463: ldc 0.5
      // 1465: ldc 3.4023
      // 1467: ldc -11.8649
      // 1469: ldc -23.0407
      // 146b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 146e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1471: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1474: aastore
      // 1475: dup
      // 1476: bipush 3
      // 1477: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 147a: dup
      // 147b: ldc 0.625
      // 147d: ldc 53.4023
      // 147f: ldc -11.8649
      // 1481: ldc -23.0407
      // 1483: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1486: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1489: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 148c: aastore
      // 148d: dup
      // 148e: bipush 4
      // 148f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1492: dup
      // 1493: fconst_1
      // 1494: ldc 42.8764
      // 1496: ldc -11.8928
      // 1498: ldc -20.4861
      // 149a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 149d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 14a0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 14a3: aastore
      // 14a4: dup
      // 14a5: bipush 5
      // 14a6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 14a9: dup
      // 14aa: ldc 1.5
      // 14ac: fconst_0
      // 14ad: fconst_0
      // 14ae: fconst_0
      // 14af: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14b2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 14b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 14b8: aastore
      // 14b9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 14bc: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 14bf: ldc "head"
      // 14c1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 14c4: dup
      // 14c5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 14c8: bipush 6
      // 14ca: anewarray 28
      // 14cd: dup
      // 14ce: bipush 0
      // 14cf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 14d2: dup
      // 14d3: fconst_0
      // 14d4: fconst_0
      // 14d5: fconst_0
      // 14d6: fconst_0
      // 14d7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14da: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 14dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 14e0: aastore
      // 14e1: dup
      // 14e2: bipush 1
      // 14e3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 14e6: dup
      // 14e7: ldc 0.25
      // 14e9: ldc 34.987
      // 14eb: ldc 19.3289
      // 14ed: ldc 5.5392
      // 14ef: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14f2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 14f5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 14f8: aastore
      // 14f9: dup
      // 14fa: bipush 2
      // 14fb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 14fe: dup
      // 14ff: ldc 0.5
      // 1501: ldc 34.2775
      // 1503: ldc_w 26.6412
      // 1506: ldc_w 3.7261
      // 1509: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 150c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 150f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1512: aastore
      // 1513: dup
      // 1514: bipush 3
      // 1515: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1518: dup
      // 1519: ldc 0.625
      // 151b: ldc -6.8062
      // 151d: ldc_w 2.2153
      // 1520: ldc_w 9.2775
      // 1523: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1526: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1529: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 152c: aastore
      // 152d: dup
      // 152e: bipush 4
      // 152f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1532: dup
      // 1533: fconst_1
      // 1534: ldc 5.6844
      // 1536: ldc_w -1.8465
      // 1539: ldc_w 6.3612
      // 153c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 153f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1542: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1545: aastore
      // 1546: dup
      // 1547: bipush 5
      // 1548: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 154b: dup
      // 154c: ldc 1.5
      // 154e: fconst_0
      // 154f: fconst_0
      // 1550: fconst_0
      // 1551: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1554: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1557: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 155a: aastore
      // 155b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 155e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1561: ldc "right_arm"
      // 1563: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1566: dup
      // 1567: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 156a: bipush 6
      // 156c: anewarray 28
      // 156f: dup
      // 1570: bipush 0
      // 1571: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1574: dup
      // 1575: fconst_0
      // 1576: fconst_0
      // 1577: fconst_0
      // 1578: fconst_0
      // 1579: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 157c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 157f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1582: aastore
      // 1583: dup
      // 1584: bipush 1
      // 1585: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1588: dup
      // 1589: ldc 0.25
      // 158b: ldc -2.5
      // 158d: fconst_0
      // 158e: ldc_w 10.0
      // 1591: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1594: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1597: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 159a: aastore
      // 159b: dup
      // 159c: bipush 2
      // 159d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 15a0: dup
      // 15a1: ldc 0.5
      // 15a3: ldc -9.5459
      // 15a5: ldc_w -2.9932
      // 15a8: ldc_w -7.25
      // 15ab: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ae: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 15b1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 15b4: aastore
      // 15b5: dup
      // 15b6: bipush 3
      // 15b7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 15ba: dup
      // 15bb: ldc 0.625
      // 15bd: ldc 18.092
      // 15bf: ldc_w 3.4049
      // 15c2: ldc_w 29.7197
      // 15c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 15cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 15ce: aastore
      // 15cf: dup
      // 15d0: bipush 4
      // 15d1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 15d4: dup
      // 15d5: fconst_1
      // 15d6: ldc 27.7445
      // 15d8: ldc_w 4.9441
      // 15db: ldc_w 24.9497
      // 15de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 15e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 15e7: aastore
      // 15e8: dup
      // 15e9: bipush 5
      // 15ea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 15ed: dup
      // 15ee: ldc 1.5
      // 15f0: fconst_0
      // 15f1: fconst_0
      // 15f2: fconst_0
      // 15f3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15f6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 15f9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 15fc: aastore
      // 15fd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1600: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1603: ldc "right_arm"
      // 1605: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1608: dup
      // 1609: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 160c: bipush 5
      // 160d: anewarray 28
      // 1610: dup
      // 1611: bipush 0
      // 1612: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1615: dup
      // 1616: fconst_0
      // 1617: fconst_0
      // 1618: fconst_0
      // 1619: fconst_0
      // 161a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 161d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1620: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1623: aastore
      // 1624: dup
      // 1625: bipush 1
      // 1626: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1629: dup
      // 162a: ldc 0.25
      // 162c: fconst_0
      // 162d: ldc 1.75
      // 162f: ldc -1.88
      // 1631: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1634: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1637: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 163a: aastore
      // 163b: dup
      // 163c: bipush 2
      // 163d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1640: dup
      // 1641: ldc 0.5
      // 1643: fconst_0
      // 1644: fconst_0
      // 1645: fconst_0
      // 1646: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1649: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 164c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 164f: aastore
      // 1650: dup
      // 1651: bipush 3
      // 1652: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1655: dup
      // 1656: ldc 0.625
      // 1658: fconst_0
      // 1659: ldc 3.0
      // 165b: fconst_0
      // 165c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 165f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1662: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1665: aastore
      // 1666: dup
      // 1667: bipush 4
      // 1668: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 166b: dup
      // 166c: ldc 1.5
      // 166e: fconst_0
      // 166f: fconst_0
      // 1670: fconst_0
      // 1671: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1674: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1677: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 167a: aastore
      // 167b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 167e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1681: ldc "right_arm2"
      // 1683: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1686: dup
      // 1687: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 168a: bipush 6
      // 168c: anewarray 28
      // 168f: dup
      // 1690: bipush 0
      // 1691: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1694: dup
      // 1695: fconst_0
      // 1696: fconst_0
      // 1697: fconst_0
      // 1698: fconst_0
      // 1699: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 169c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 169f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 16a2: aastore
      // 16a3: dup
      // 16a4: bipush 1
      // 16a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 16a8: dup
      // 16a9: ldc 0.25
      // 16ab: ldc -17.3727
      // 16ad: ldc_w 0.5463
      // 16b0: ldc_w -1.003
      // 16b3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16b6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 16b9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 16bc: aastore
      // 16bd: dup
      // 16be: bipush 2
      // 16bf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 16c2: dup
      // 16c3: ldc 0.5
      // 16c5: ldc -47.3727
      // 16c7: ldc_w 0.5463
      // 16ca: ldc_w -1.003
      // 16cd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16d0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 16d3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 16d6: aastore
      // 16d7: dup
      // 16d8: bipush 3
      // 16d9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 16dc: dup
      // 16dd: ldc 0.625
      // 16df: ldc -23.2281
      // 16e1: ldc_w -14.0447
      // 16e4: ldc_w -14.8166
      // 16e7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 16ed: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 16f0: aastore
      // 16f1: dup
      // 16f2: bipush 4
      // 16f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 16f6: dup
      // 16f7: fconst_1
      // 16f8: ldc -15.7281
      // 16fa: ldc_w -14.0447
      // 16fd: ldc_w -14.8166
      // 1700: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1703: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1706: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1709: aastore
      // 170a: dup
      // 170b: bipush 5
      // 170c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 170f: dup
      // 1710: ldc 1.5
      // 1712: fconst_0
      // 1713: fconst_0
      // 1714: fconst_0
      // 1715: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1718: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 171b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 171e: aastore
      // 171f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1722: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1725: ldc "left_arm"
      // 1727: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 172a: dup
      // 172b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 172e: bipush 6
      // 1730: anewarray 28
      // 1733: dup
      // 1734: bipush 0
      // 1735: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1738: dup
      // 1739: fconst_0
      // 173a: fconst_0
      // 173b: fconst_0
      // 173c: fconst_0
      // 173d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1740: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1743: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1746: aastore
      // 1747: dup
      // 1748: bipush 1
      // 1749: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 174c: dup
      // 174d: ldc 0.25
      // 174f: ldc -0.1981
      // 1751: ldc_w -7.6388
      // 1754: ldc_w -53.0657
      // 1757: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 175a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 175d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1760: aastore
      // 1761: dup
      // 1762: bipush 2
      // 1763: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1766: dup
      // 1767: ldc 0.5
      // 1769: ldc 2.1209
      // 176b: ldc_w -7.3428
      // 176e: ldc_w -70.715
      // 1771: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1774: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1777: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 177a: aastore
      // 177b: dup
      // 177c: bipush 3
      // 177d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1780: dup
      // 1781: ldc 0.625
      // 1783: ldc -51.1635
      // 1785: ldc_w 21.4568
      // 1788: ldc_w 33.9585
      // 178b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 178e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1791: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1794: aastore
      // 1795: dup
      // 1796: bipush 4
      // 1797: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 179a: dup
      // 179b: fconst_1
      // 179c: ldc -47.2865
      // 179e: ldc_w 25.3148
      // 17a1: ldc_w 37.4248
      // 17a4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17a7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 17aa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 17ad: aastore
      // 17ae: dup
      // 17af: bipush 5
      // 17b0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 17b3: dup
      // 17b4: ldc 1.5
      // 17b6: fconst_0
      // 17b7: fconst_0
      // 17b8: fconst_0
      // 17b9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17bc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 17bf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 17c2: aastore
      // 17c3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 17c6: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 17c9: ldc "left_arm"
      // 17cb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 17ce: dup
      // 17cf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 17d2: bipush 5
      // 17d3: anewarray 28
      // 17d6: dup
      // 17d7: bipush 0
      // 17d8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 17db: dup
      // 17dc: fconst_0
      // 17dd: fconst_0
      // 17de: fconst_0
      // 17df: fconst_0
      // 17e0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17e3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 17e6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 17e9: aastore
      // 17ea: dup
      // 17eb: bipush 1
      // 17ec: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 17ef: dup
      // 17f0: ldc 0.25
      // 17f2: fconst_0
      // 17f3: ldc 1.75
      // 17f5: ldc -1.88
      // 17f7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17fa: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 17fd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1800: aastore
      // 1801: dup
      // 1802: bipush 2
      // 1803: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1806: dup
      // 1807: ldc 0.5
      // 1809: fconst_0
      // 180a: ldc 3.99
      // 180c: ldc -4.15
      // 180e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1811: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1814: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1817: aastore
      // 1818: dup
      // 1819: bipush 3
      // 181a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 181d: dup
      // 181e: ldc 0.625
      // 1820: fconst_0
      // 1821: fconst_2
      // 1822: ldc -5.0
      // 1824: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1827: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 182a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 182d: aastore
      // 182e: dup
      // 182f: bipush 4
      // 1830: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1833: dup
      // 1834: ldc 1.5
      // 1836: fconst_0
      // 1837: fconst_0
      // 1838: fconst_0
      // 1839: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 183c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 183f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1842: aastore
      // 1843: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1846: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1849: ldc "left_arm2"
      // 184b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 184e: dup
      // 184f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1852: bipush 6
      // 1854: anewarray 28
      // 1857: dup
      // 1858: bipush 0
      // 1859: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 185c: dup
      // 185d: fconst_0
      // 185e: fconst_0
      // 185f: fconst_0
      // 1860: fconst_0
      // 1861: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1864: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1867: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 186a: aastore
      // 186b: dup
      // 186c: bipush 1
      // 186d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1870: dup
      // 1871: ldc 0.25
      // 1873: ldc -35.0
      // 1875: fconst_0
      // 1876: fconst_0
      // 1877: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 187a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 187d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1880: aastore
      // 1881: dup
      // 1882: bipush 2
      // 1883: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1886: dup
      // 1887: ldc 0.5
      // 1889: ldc -33.735
      // 188b: ldc_w 9.9319
      // 188e: ldc_w 14.4817
      // 1891: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1894: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1897: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 189a: aastore
      // 189b: dup
      // 189c: bipush 3
      // 189d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 18a0: dup
      // 18a1: ldc 0.625
      // 18a3: fconst_0
      // 18a4: fconst_0
      // 18a5: fconst_0
      // 18a6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18a9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 18ac: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 18af: aastore
      // 18b0: dup
      // 18b1: bipush 4
      // 18b2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 18b5: dup
      // 18b6: fconst_1
      // 18b7: ldc -2.5
      // 18b9: fconst_0
      // 18ba: fconst_0
      // 18bb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18be: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 18c1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 18c4: aastore
      // 18c5: dup
      // 18c6: bipush 5
      // 18c7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 18ca: dup
      // 18cb: ldc 1.5
      // 18cd: fconst_0
      // 18ce: fconst_0
      // 18cf: fconst_0
      // 18d0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18d3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 18d6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 18d9: aastore
      // 18da: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 18dd: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 18e0: ldc "right_leg"
      // 18e2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 18e5: dup
      // 18e6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 18e9: bipush 5
      // 18ea: anewarray 28
      // 18ed: dup
      // 18ee: bipush 0
      // 18ef: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 18f2: dup
      // 18f3: fconst_0
      // 18f4: fconst_0
      // 18f5: fconst_0
      // 18f6: fconst_0
      // 18f7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18fa: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 18fd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1900: aastore
      // 1901: dup
      // 1902: bipush 1
      // 1903: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1906: dup
      // 1907: ldc 0.5
      // 1909: fconst_0
      // 190a: fconst_0
      // 190b: fconst_0
      // 190c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 190f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1912: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1915: aastore
      // 1916: dup
      // 1917: bipush 2
      // 1918: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 191b: dup
      // 191c: ldc 0.625
      // 191e: ldc 25.0
      // 1920: fconst_0
      // 1921: fconst_0
      // 1922: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1925: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1928: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 192b: aastore
      // 192c: dup
      // 192d: bipush 3
      // 192e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1931: dup
      // 1932: fconst_1
      // 1933: ldc 37.5
      // 1935: fconst_0
      // 1936: fconst_0
      // 1937: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 193a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 193d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1940: aastore
      // 1941: dup
      // 1942: bipush 4
      // 1943: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1946: dup
      // 1947: ldc 1.5
      // 1949: fconst_0
      // 194a: fconst_0
      // 194b: fconst_0
      // 194c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 194f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1952: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1955: aastore
      // 1956: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1959: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 195c: ldc "right_leg"
      // 195e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1961: dup
      // 1962: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1965: bipush 6
      // 1967: anewarray 28
      // 196a: dup
      // 196b: bipush 0
      // 196c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 196f: dup
      // 1970: fconst_0
      // 1971: fconst_0
      // 1972: fconst_0
      // 1973: fconst_0
      // 1974: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1977: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 197a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 197d: aastore
      // 197e: dup
      // 197f: bipush 1
      // 1980: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1983: dup
      // 1984: ldc 0.25
      // 1986: fconst_0
      // 1987: fconst_0
      // 1988: ldc 3.0
      // 198a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 198d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1990: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1993: aastore
      // 1994: dup
      // 1995: bipush 2
      // 1996: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1999: dup
      // 199a: ldc 0.5
      // 199c: fconst_0
      // 199d: fconst_0
      // 199e: ldc 7.0
      // 19a0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19a3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 19a6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 19a9: aastore
      // 19aa: dup
      // 19ab: bipush 3
      // 19ac: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 19af: dup
      // 19b0: ldc 0.625
      // 19b2: fconst_0
      // 19b3: fconst_0
      // 19b4: fconst_2
      // 19b5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19b8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 19bb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 19be: aastore
      // 19bf: dup
      // 19c0: bipush 4
      // 19c1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 19c4: dup
      // 19c5: fconst_1
      // 19c6: fconst_0
      // 19c7: fconst_0
      // 19c8: fconst_1
      // 19c9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19cc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 19cf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 19d2: aastore
      // 19d3: dup
      // 19d4: bipush 5
      // 19d5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 19d8: dup
      // 19d9: ldc 1.5
      // 19db: fconst_0
      // 19dc: fconst_0
      // 19dd: fconst_0
      // 19de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 19e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 19e7: aastore
      // 19e8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 19eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 19ee: ldc "left_leg"
      // 19f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 19f3: dup
      // 19f4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 19f7: bipush 4
      // 19f8: anewarray 28
      // 19fb: dup
      // 19fc: bipush 0
      // 19fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a00: dup
      // 1a01: fconst_0
      // 1a02: fconst_0
      // 1a03: fconst_0
      // 1a04: fconst_0
      // 1a05: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a08: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a0b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a0e: aastore
      // 1a0f: dup
      // 1a10: bipush 1
      // 1a11: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a14: dup
      // 1a15: ldc 0.5
      // 1a17: fconst_0
      // 1a18: fconst_0
      // 1a19: fconst_0
      // 1a1a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a1d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a20: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a23: aastore
      // 1a24: dup
      // 1a25: bipush 2
      // 1a26: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a29: dup
      // 1a2a: ldc 0.625
      // 1a2c: ldc 70.0
      // 1a2e: fconst_0
      // 1a2f: fconst_0
      // 1a30: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a33: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a36: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a39: aastore
      // 1a3a: dup
      // 1a3b: bipush 3
      // 1a3c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a3f: dup
      // 1a40: ldc 1.5
      // 1a42: fconst_0
      // 1a43: fconst_0
      // 1a44: fconst_0
      // 1a45: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a48: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a4b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a4e: aastore
      // 1a4f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1a52: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1a55: ldc "left_leg"
      // 1a57: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1a5a: dup
      // 1a5b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1a5e: bipush 6
      // 1a60: anewarray 28
      // 1a63: dup
      // 1a64: bipush 0
      // 1a65: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a68: dup
      // 1a69: fconst_0
      // 1a6a: fconst_0
      // 1a6b: fconst_0
      // 1a6c: fconst_0
      // 1a6d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a70: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a73: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a76: aastore
      // 1a77: dup
      // 1a78: bipush 1
      // 1a79: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a7c: dup
      // 1a7d: ldc 0.25
      // 1a7f: fconst_1
      // 1a80: fconst_0
      // 1a81: fconst_0
      // 1a82: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a85: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a88: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1a8b: aastore
      // 1a8c: dup
      // 1a8d: bipush 2
      // 1a8e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1a91: dup
      // 1a92: ldc 0.5
      // 1a94: fconst_1
      // 1a95: fconst_0
      // 1a96: fconst_2
      // 1a97: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1a9a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1a9d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1aa0: aastore
      // 1aa1: dup
      // 1aa2: bipush 3
      // 1aa3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1aa6: dup
      // 1aa7: ldc 0.625
      // 1aa9: fconst_1
      // 1aaa: ldc -4.0
      // 1aac: ldc -6.0
      // 1aae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ab1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ab4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ab7: aastore
      // 1ab8: dup
      // 1ab9: bipush 4
      // 1aba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1abd: dup
      // 1abe: fconst_1
      // 1abf: fconst_1
      // 1ac0: ldc -4.0
      // 1ac2: ldc -7.0
      // 1ac4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ac7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1aca: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1acd: aastore
      // 1ace: dup
      // 1acf: bipush 5
      // 1ad0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1ad3: dup
      // 1ad4: ldc 1.5
      // 1ad6: fconst_0
      // 1ad7: fconst_0
      // 1ad8: fconst_0
      // 1ad9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1adc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1adf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ae2: aastore
      // 1ae3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1ae6: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1ae9: ldc "eye"
      // 1aeb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1aee: dup
      // 1aef: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1af2: bipush 5
      // 1af3: anewarray 28
      // 1af6: dup
      // 1af7: bipush 0
      // 1af8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1afb: dup
      // 1afc: fconst_0
      // 1afd: fconst_0
      // 1afe: fconst_0
      // 1aff: fconst_0
      // 1b00: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b03: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b06: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b09: aastore
      // 1b0a: dup
      // 1b0b: bipush 1
      // 1b0c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b0f: dup
      // 1b10: ldc 0.5
      // 1b12: fconst_0
      // 1b13: ldc -0.6
      // 1b15: fconst_0
      // 1b16: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b19: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b1c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b1f: aastore
      // 1b20: dup
      // 1b21: bipush 2
      // 1b22: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b25: dup
      // 1b26: ldc 0.75
      // 1b28: fconst_0
      // 1b29: fconst_1
      // 1b2a: fconst_0
      // 1b2b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b2e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b31: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b34: aastore
      // 1b35: dup
      // 1b36: bipush 3
      // 1b37: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b3a: dup
      // 1b3b: fconst_1
      // 1b3c: fconst_0
      // 1b3d: ldc -0.5
      // 1b3f: fconst_0
      // 1b40: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b43: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b46: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b49: aastore
      // 1b4a: dup
      // 1b4b: bipush 4
      // 1b4c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b4f: dup
      // 1b50: ldc 1.5
      // 1b52: fconst_0
      // 1b53: fconst_0
      // 1b54: fconst_0
      // 1b55: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b58: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b5b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b5e: aastore
      // 1b5f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1b62: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1b65: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 1b68: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.LEFT_FIST Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 1b6b: fconst_1
      // 1b6c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1b6f: ldc "body"
      // 1b71: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1b74: dup
      // 1b75: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1b78: bipush 3
      // 1b79: anewarray 28
      // 1b7c: dup
      // 1b7d: bipush 0
      // 1b7e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b81: dup
      // 1b82: fconst_0
      // 1b83: fconst_0
      // 1b84: fconst_0
      // 1b85: fconst_0
      // 1b86: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b89: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1b8c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1b8f: aastore
      // 1b90: dup
      // 1b91: bipush 1
      // 1b92: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1b95: dup
      // 1b96: ldc 0.5
      // 1b98: ldc_w 9.4752
      // 1b9b: ldc_w -0.2629
      // 1b9e: ldc_w -3.2116
      // 1ba1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ba4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ba7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1baa: aastore
      // 1bab: dup
      // 1bac: bipush 2
      // 1bad: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1bb0: dup
      // 1bb1: fconst_1
      // 1bb2: ldc_w -25.0
      // 1bb5: fconst_0
      // 1bb6: fconst_0
      // 1bb7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1bbd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1bc0: aastore
      // 1bc1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1bc4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1bc7: ldc "body"
      // 1bc9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1bcc: dup
      // 1bcd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1bd0: bipush 4
      // 1bd1: anewarray 28
      // 1bd4: dup
      // 1bd5: bipush 0
      // 1bd6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1bd9: dup
      // 1bda: fconst_0
      // 1bdb: fconst_0
      // 1bdc: fconst_0
      // 1bdd: fconst_0
      // 1bde: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1be1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1be4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1be7: aastore
      // 1be8: dup
      // 1be9: bipush 1
      // 1bea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1bed: dup
      // 1bee: ldc 0.5
      // 1bf0: fconst_0
      // 1bf1: ldc_w -3.0
      // 1bf4: fconst_0
      // 1bf5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bf8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1bfb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1bfe: aastore
      // 1bff: dup
      // 1c00: bipush 2
      // 1c01: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c04: dup
      // 1c05: ldc 0.75
      // 1c07: fconst_0
      // 1c08: ldc_w -3.7
      // 1c0b: fconst_0
      // 1c0c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c0f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1c12: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1c15: aastore
      // 1c16: dup
      // 1c17: bipush 3
      // 1c18: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c1b: dup
      // 1c1c: fconst_1
      // 1c1d: fconst_0
      // 1c1e: ldc_w 5.3
      // 1c21: fconst_0
      // 1c22: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c25: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1c28: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1c2b: aastore
      // 1c2c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1c2f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1c32: ldc "chest"
      // 1c34: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1c37: dup
      // 1c38: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1c3b: bipush 3
      // 1c3c: anewarray 28
      // 1c3f: dup
      // 1c40: bipush 0
      // 1c41: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c44: dup
      // 1c45: fconst_0
      // 1c46: fconst_0
      // 1c47: fconst_0
      // 1c48: fconst_0
      // 1c49: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c4c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1c4f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1c52: aastore
      // 1c53: dup
      // 1c54: bipush 1
      // 1c55: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c58: dup
      // 1c59: ldc 0.5
      // 1c5b: ldc_w 10.0
      // 1c5e: fconst_0
      // 1c5f: fconst_0
      // 1c60: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c63: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1c66: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1c69: aastore
      // 1c6a: dup
      // 1c6b: bipush 2
      // 1c6c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c6f: dup
      // 1c70: fconst_1
      // 1c71: fconst_0
      // 1c72: fconst_0
      // 1c73: fconst_0
      // 1c74: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c77: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1c7a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1c7d: aastore
      // 1c7e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1c81: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1c84: ldc "head"
      // 1c86: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1c89: dup
      // 1c8a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1c8d: bipush 4
      // 1c8e: anewarray 28
      // 1c91: dup
      // 1c92: bipush 0
      // 1c93: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1c96: dup
      // 1c97: fconst_0
      // 1c98: fconst_0
      // 1c99: fconst_0
      // 1c9a: fconst_0
      // 1c9b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c9e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ca1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ca4: aastore
      // 1ca5: dup
      // 1ca6: bipush 1
      // 1ca7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1caa: dup
      // 1cab: ldc 0.5
      // 1cad: ldc -10.0
      // 1caf: fconst_0
      // 1cb0: fconst_0
      // 1cb1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cb4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1cb7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1cba: aastore
      // 1cbb: dup
      // 1cbc: bipush 2
      // 1cbd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1cc0: dup
      // 1cc1: ldc 0.75
      // 1cc3: ldc_w -15.0
      // 1cc6: fconst_0
      // 1cc7: fconst_0
      // 1cc8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ccb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1cce: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1cd1: aastore
      // 1cd2: dup
      // 1cd3: bipush 3
      // 1cd4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1cd7: dup
      // 1cd8: fconst_1
      // 1cd9: ldc_w 40.0
      // 1cdc: fconst_0
      // 1cdd: fconst_0
      // 1cde: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ce1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ce4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ce7: aastore
      // 1ce8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1ceb: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1cee: ldc "eye"
      // 1cf0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1cf3: dup
      // 1cf4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1cf7: bipush 3
      // 1cf8: anewarray 28
      // 1cfb: dup
      // 1cfc: bipush 0
      // 1cfd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d00: dup
      // 1d01: fconst_0
      // 1d02: fconst_0
      // 1d03: fconst_0
      // 1d04: fconst_0
      // 1d05: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d08: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d0b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d0e: aastore
      // 1d0f: dup
      // 1d10: bipush 1
      // 1d11: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d14: dup
      // 1d15: ldc 0.5
      // 1d17: fconst_0
      // 1d18: ldc 0.4
      // 1d1a: fconst_0
      // 1d1b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d1e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d21: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d24: aastore
      // 1d25: dup
      // 1d26: bipush 2
      // 1d27: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d2a: dup
      // 1d2b: fconst_1
      // 1d2c: fconst_0
      // 1d2d: ldc_w -0.3
      // 1d30: fconst_0
      // 1d31: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d34: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d37: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d3a: aastore
      // 1d3b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1d3e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1d41: ldc "right_arm"
      // 1d43: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1d46: dup
      // 1d47: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1d4a: bipush 4
      // 1d4b: anewarray 28
      // 1d4e: dup
      // 1d4f: bipush 0
      // 1d50: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d53: dup
      // 1d54: fconst_0
      // 1d55: fconst_0
      // 1d56: fconst_0
      // 1d57: fconst_0
      // 1d58: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d5b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d5e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d61: aastore
      // 1d62: dup
      // 1d63: bipush 1
      // 1d64: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d67: dup
      // 1d68: ldc 0.5
      // 1d6a: ldc_w -57.5
      // 1d6d: fconst_0
      // 1d6e: fconst_0
      // 1d6f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d72: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d75: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d78: aastore
      // 1d79: dup
      // 1d7a: bipush 2
      // 1d7b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d7e: dup
      // 1d7f: ldc 0.75
      // 1d81: ldc_w -67.5
      // 1d84: fconst_0
      // 1d85: fconst_0
      // 1d86: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d89: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1d8c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1d8f: aastore
      // 1d90: dup
      // 1d91: bipush 3
      // 1d92: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1d95: dup
      // 1d96: fconst_1
      // 1d97: ldc_w -145.1839
      // 1d9a: ldc_w 14.9084
      // 1d9d: ldc_w -20.3018
      // 1da0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1da3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1da6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1da9: aastore
      // 1daa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1dad: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1db0: ldc "right_arm"
      // 1db2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1db5: dup
      // 1db6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1db9: bipush 2
      // 1dba: anewarray 28
      // 1dbd: dup
      // 1dbe: bipush 0
      // 1dbf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1dc2: dup
      // 1dc3: fconst_0
      // 1dc4: fconst_0
      // 1dc5: fconst_0
      // 1dc6: fconst_0
      // 1dc7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dca: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1dcd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1dd0: aastore
      // 1dd1: dup
      // 1dd2: bipush 1
      // 1dd3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1dd6: dup
      // 1dd7: ldc 0.5
      // 1dd9: fconst_0
      // 1dda: ldc 5.0
      // 1ddc: fconst_0
      // 1ddd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1de0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1de3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1de6: aastore
      // 1de7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1dea: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1ded: ldc "right_arm2"
      // 1def: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1df2: dup
      // 1df3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1df6: bipush 4
      // 1df7: anewarray 28
      // 1dfa: dup
      // 1dfb: bipush 0
      // 1dfc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1dff: dup
      // 1e00: fconst_0
      // 1e01: fconst_0
      // 1e02: fconst_0
      // 1e03: fconst_0
      // 1e04: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e07: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e0a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e0d: aastore
      // 1e0e: dup
      // 1e0f: bipush 1
      // 1e10: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e13: dup
      // 1e14: ldc 0.5
      // 1e16: ldc_w 42.5
      // 1e19: fconst_0
      // 1e1a: fconst_0
      // 1e1b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e1e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e21: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e24: aastore
      // 1e25: dup
      // 1e26: bipush 2
      // 1e27: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e2a: dup
      // 1e2b: ldc 0.75
      // 1e2d: ldc_w 47.5
      // 1e30: fconst_0
      // 1e31: fconst_0
      // 1e32: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e35: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e38: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e3b: aastore
      // 1e3c: dup
      // 1e3d: bipush 3
      // 1e3e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e41: dup
      // 1e42: fconst_1
      // 1e43: ldc_w -40.0
      // 1e46: fconst_0
      // 1e47: fconst_0
      // 1e48: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e4b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e4e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e51: aastore
      // 1e52: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1e55: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1e58: ldc "left_arm"
      // 1e5a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1e5d: dup
      // 1e5e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1e61: bipush 4
      // 1e62: anewarray 28
      // 1e65: dup
      // 1e66: bipush 0
      // 1e67: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e6a: dup
      // 1e6b: fconst_0
      // 1e6c: fconst_0
      // 1e6d: fconst_0
      // 1e6e: fconst_0
      // 1e6f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e72: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e75: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e78: aastore
      // 1e79: dup
      // 1e7a: bipush 1
      // 1e7b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e7e: dup
      // 1e7f: ldc 0.5
      // 1e81: ldc_w -57.5
      // 1e84: fconst_0
      // 1e85: fconst_0
      // 1e86: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e89: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1e8c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1e8f: aastore
      // 1e90: dup
      // 1e91: bipush 2
      // 1e92: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1e95: dup
      // 1e96: ldc 0.75
      // 1e98: ldc_w -67.5
      // 1e9b: fconst_0
      // 1e9c: fconst_0
      // 1e9d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ea0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ea3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ea6: aastore
      // 1ea7: dup
      // 1ea8: bipush 3
      // 1ea9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1eac: dup
      // 1ead: fconst_1
      // 1eae: ldc_w -145.1839
      // 1eb1: ldc_w -14.9084
      // 1eb4: ldc_w 20.3018
      // 1eb7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1eba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ebd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ec0: aastore
      // 1ec1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1ec4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1ec7: ldc "left_arm"
      // 1ec9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1ecc: dup
      // 1ecd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1ed0: bipush 2
      // 1ed1: anewarray 28
      // 1ed4: dup
      // 1ed5: bipush 0
      // 1ed6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1ed9: dup
      // 1eda: fconst_0
      // 1edb: fconst_0
      // 1edc: fconst_0
      // 1edd: fconst_0
      // 1ede: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ee1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ee4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ee7: aastore
      // 1ee8: dup
      // 1ee9: bipush 1
      // 1eea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1eed: dup
      // 1eee: ldc 0.5
      // 1ef0: fconst_0
      // 1ef1: ldc 5.0
      // 1ef3: fconst_0
      // 1ef4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ef7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1efa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1efd: aastore
      // 1efe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1f01: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1f04: ldc "left_arm2"
      // 1f06: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1f09: dup
      // 1f0a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1f0d: bipush 4
      // 1f0e: anewarray 28
      // 1f11: dup
      // 1f12: bipush 0
      // 1f13: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f16: dup
      // 1f17: fconst_0
      // 1f18: fconst_0
      // 1f19: fconst_0
      // 1f1a: fconst_0
      // 1f1b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f1e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1f21: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1f24: aastore
      // 1f25: dup
      // 1f26: bipush 1
      // 1f27: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f2a: dup
      // 1f2b: ldc 0.5
      // 1f2d: ldc_w 42.5
      // 1f30: fconst_0
      // 1f31: fconst_0
      // 1f32: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f35: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1f38: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1f3b: aastore
      // 1f3c: dup
      // 1f3d: bipush 2
      // 1f3e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f41: dup
      // 1f42: ldc 0.75
      // 1f44: ldc_w 47.5
      // 1f47: fconst_0
      // 1f48: fconst_0
      // 1f49: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f4c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1f4f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1f52: aastore
      // 1f53: dup
      // 1f54: bipush 3
      // 1f55: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f58: dup
      // 1f59: fconst_1
      // 1f5a: ldc_w -40.0
      // 1f5d: fconst_0
      // 1f5e: fconst_0
      // 1f5f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f62: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1f65: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1f68: aastore
      // 1f69: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1f6c: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1f6f: ldc "right_leg"
      // 1f71: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1f74: dup
      // 1f75: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1f78: bipush 3
      // 1f79: anewarray 28
      // 1f7c: dup
      // 1f7d: bipush 0
      // 1f7e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f81: dup
      // 1f82: fconst_0
      // 1f83: fconst_0
      // 1f84: fconst_0
      // 1f85: fconst_0
      // 1f86: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f89: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1f8c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1f8f: aastore
      // 1f90: dup
      // 1f91: bipush 1
      // 1f92: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1f95: dup
      // 1f96: ldc 0.5
      // 1f98: ldc_w -15.0
      // 1f9b: fconst_0
      // 1f9c: fconst_0
      // 1f9d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fa0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1fa3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1fa6: aastore
      // 1fa7: dup
      // 1fa8: bipush 2
      // 1fa9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1fac: dup
      // 1fad: fconst_1
      // 1fae: ldc_w -20.0
      // 1fb1: fconst_0
      // 1fb2: fconst_0
      // 1fb3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fb6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1fb9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1fbc: aastore
      // 1fbd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 1fc0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 1fc3: ldc "right_leg"
      // 1fc5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 1fc8: dup
      // 1fc9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 1fcc: bipush 4
      // 1fcd: anewarray 28
      // 1fd0: dup
      // 1fd1: bipush 0
      // 1fd2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1fd5: dup
      // 1fd6: fconst_0
      // 1fd7: fconst_0
      // 1fd8: fconst_0
      // 1fd9: fconst_0
      // 1fda: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fdd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1fe0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1fe3: aastore
      // 1fe4: dup
      // 1fe5: bipush 1
      // 1fe6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 1fe9: dup
      // 1fea: ldc 0.5
      // 1fec: fconst_0
      // 1fed: ldc -2.0
      // 1fef: ldc -2.0
      // 1ff1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ff4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 1ff7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 1ffa: aastore
      // 1ffb: dup
      // 1ffc: bipush 2
      // 1ffd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2000: dup
      // 2001: ldc 0.75
      // 2003: fconst_0
      // 2004: ldc -2.0
      // 2006: ldc_w -3.0
      // 2009: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 200c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 200f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2012: aastore
      // 2013: dup
      // 2014: bipush 3
      // 2015: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2018: dup
      // 2019: fconst_1
      // 201a: ldc -1.0
      // 201c: ldc 8.0
      // 201e: ldc -5.0
      // 2020: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2023: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2026: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2029: aastore
      // 202a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 202d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2030: ldc "left_leg"
      // 2032: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2035: dup
      // 2036: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2039: bipush 2
      // 203a: anewarray 28
      // 203d: dup
      // 203e: bipush 0
      // 203f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2042: dup
      // 2043: fconst_0
      // 2044: fconst_0
      // 2045: fconst_0
      // 2046: fconst_0
      // 2047: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 204a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 204d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2050: aastore
      // 2051: dup
      // 2052: bipush 1
      // 2053: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2056: dup
      // 2057: fconst_1
      // 2058: ldc_w 35.0
      // 205b: fconst_0
      // 205c: fconst_0
      // 205d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2060: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2063: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2066: aastore
      // 2067: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 206a: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 206d: ldc "left_leg"
      // 206f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2072: dup
      // 2073: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2076: bipush 4
      // 2077: anewarray 28
      // 207a: dup
      // 207b: bipush 0
      // 207c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 207f: dup
      // 2080: fconst_0
      // 2081: fconst_0
      // 2082: fconst_0
      // 2083: fconst_0
      // 2084: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2087: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 208a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 208d: aastore
      // 208e: dup
      // 208f: bipush 1
      // 2090: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2093: dup
      // 2094: ldc 0.5
      // 2096: fconst_0
      // 2097: ldc -1.0
      // 2099: fconst_0
      // 209a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 209d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 20a0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 20a3: aastore
      // 20a4: dup
      // 20a5: bipush 2
      // 20a6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 20a9: dup
      // 20aa: ldc 0.75
      // 20ac: fconst_0
      // 20ad: ldc -1.0
      // 20af: ldc_w 1.6
      // 20b2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20b5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 20b8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 20bb: aastore
      // 20bc: dup
      // 20bd: bipush 3
      // 20be: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 20c1: dup
      // 20c2: fconst_1
      // 20c3: fconst_0
      // 20c4: ldc_w 9.0
      // 20c7: ldc_w 5.6
      // 20ca: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20cd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 20d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 20d3: aastore
      // 20d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 20d7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 20da: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 20dd: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.JUMPING_READY Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 20e0: fconst_1
      // 20e1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 20e4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.looping ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 20e7: ldc "body"
      // 20e9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 20ec: dup
      // 20ed: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 20f0: bipush 3
      // 20f1: anewarray 28
      // 20f4: dup
      // 20f5: bipush 0
      // 20f6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 20f9: dup
      // 20fa: fconst_0
      // 20fb: ldc_w -25.0
      // 20fe: fconst_0
      // 20ff: fconst_0
      // 2100: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2103: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2106: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2109: aastore
      // 210a: dup
      // 210b: bipush 1
      // 210c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 210f: dup
      // 2110: ldc_w 0.4583
      // 2113: ldc_w -33.0
      // 2116: fconst_0
      // 2117: fconst_0
      // 2118: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 211b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 211e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2121: aastore
      // 2122: dup
      // 2123: bipush 2
      // 2124: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2127: dup
      // 2128: fconst_1
      // 2129: ldc_w -25.0
      // 212c: fconst_0
      // 212d: fconst_0
      // 212e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2131: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2134: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2137: aastore
      // 2138: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 213b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 213e: ldc "body"
      // 2140: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2143: dup
      // 2144: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2147: bipush 2
      // 2148: anewarray 28
      // 214b: dup
      // 214c: bipush 0
      // 214d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2150: dup
      // 2151: fconst_0
      // 2152: fconst_0
      // 2153: ldc_w 5.3
      // 2156: fconst_0
      // 2157: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 215a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 215d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2160: aastore
      // 2161: dup
      // 2162: bipush 1
      // 2163: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2166: dup
      // 2167: fconst_1
      // 2168: fconst_0
      // 2169: ldc_w 5.3
      // 216c: fconst_0
      // 216d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2170: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2173: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2176: aastore
      // 2177: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 217a: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 217d: ldc "chest"
      // 217f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2182: dup
      // 2183: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2186: bipush 3
      // 2187: anewarray 28
      // 218a: dup
      // 218b: bipush 0
      // 218c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 218f: dup
      // 2190: fconst_0
      // 2191: fconst_0
      // 2192: fconst_0
      // 2193: fconst_0
      // 2194: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2197: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 219a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 219d: aastore
      // 219e: dup
      // 219f: bipush 1
      // 21a0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 21a3: dup
      // 21a4: ldc 0.5
      // 21a6: ldc 2.5
      // 21a8: fconst_0
      // 21a9: fconst_0
      // 21aa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21ad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 21b0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 21b3: aastore
      // 21b4: dup
      // 21b5: bipush 2
      // 21b6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 21b9: dup
      // 21ba: fconst_1
      // 21bb: fconst_0
      // 21bc: fconst_0
      // 21bd: fconst_0
      // 21be: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21c1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 21c4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 21c7: aastore
      // 21c8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 21cb: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 21ce: ldc "head"
      // 21d0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 21d3: dup
      // 21d4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 21d7: bipush 3
      // 21d8: anewarray 28
      // 21db: dup
      // 21dc: bipush 0
      // 21dd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 21e0: dup
      // 21e1: fconst_0
      // 21e2: ldc_w 40.0
      // 21e5: fconst_0
      // 21e6: fconst_0
      // 21e7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 21ed: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 21f0: aastore
      // 21f1: dup
      // 21f2: bipush 1
      // 21f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 21f6: dup
      // 21f7: ldc 0.625
      // 21f9: ldc_w 35.0
      // 21fc: fconst_0
      // 21fd: fconst_0
      // 21fe: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2201: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2204: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2207: aastore
      // 2208: dup
      // 2209: bipush 2
      // 220a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 220d: dup
      // 220e: fconst_1
      // 220f: ldc_w 40.0
      // 2212: fconst_0
      // 2213: fconst_0
      // 2214: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2217: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 221a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 221d: aastore
      // 221e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2221: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2224: ldc "eye"
      // 2226: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2229: dup
      // 222a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 222d: bipush 3
      // 222e: anewarray 28
      // 2231: dup
      // 2232: bipush 0
      // 2233: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2236: dup
      // 2237: fconst_0
      // 2238: fconst_0
      // 2239: ldc_w -0.3
      // 223c: fconst_0
      // 223d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2240: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2243: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2246: aastore
      // 2247: dup
      // 2248: bipush 1
      // 2249: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 224c: dup
      // 224d: ldc 0.5
      // 224f: fconst_0
      // 2250: ldc_w -0.8
      // 2253: fconst_0
      // 2254: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2257: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 225a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 225d: aastore
      // 225e: dup
      // 225f: bipush 2
      // 2260: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2263: dup
      // 2264: fconst_1
      // 2265: fconst_0
      // 2266: ldc_w -0.3
      // 2269: fconst_0
      // 226a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 226d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2270: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2273: aastore
      // 2274: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2277: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 227a: ldc "right_arm"
      // 227c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 227f: dup
      // 2280: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2283: bipush 3
      // 2284: anewarray 28
      // 2287: dup
      // 2288: bipush 0
      // 2289: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 228c: dup
      // 228d: fconst_0
      // 228e: ldc_w -145.1839
      // 2291: ldc_w 14.9084
      // 2294: ldc_w -20.3018
      // 2297: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 229a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 229d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 22a0: aastore
      // 22a1: dup
      // 22a2: bipush 1
      // 22a3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 22a6: dup
      // 22a7: ldc 0.375
      // 22a9: ldc_w -150.1839
      // 22ac: ldc_w 14.9084
      // 22af: ldc_w -20.3018
      // 22b2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22b5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 22b8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 22bb: aastore
      // 22bc: dup
      // 22bd: bipush 2
      // 22be: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 22c1: dup
      // 22c2: fconst_1
      // 22c3: ldc_w -145.1839
      // 22c6: ldc_w 14.9084
      // 22c9: ldc_w -20.3018
      // 22cc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22cf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 22d2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 22d5: aastore
      // 22d6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 22d9: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 22dc: ldc "right_arm2"
      // 22de: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 22e1: dup
      // 22e2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 22e5: bipush 3
      // 22e6: anewarray 28
      // 22e9: dup
      // 22ea: bipush 0
      // 22eb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 22ee: dup
      // 22ef: fconst_0
      // 22f0: ldc_w -40.0
      // 22f3: fconst_0
      // 22f4: fconst_0
      // 22f5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22f8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 22fb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 22fe: aastore
      // 22ff: dup
      // 2300: bipush 1
      // 2301: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2304: dup
      // 2305: ldc 0.5
      // 2307: ldc_w -47.5
      // 230a: fconst_0
      // 230b: fconst_0
      // 230c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 230f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2312: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2315: aastore
      // 2316: dup
      // 2317: bipush 2
      // 2318: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 231b: dup
      // 231c: fconst_1
      // 231d: ldc_w -40.0
      // 2320: fconst_0
      // 2321: fconst_0
      // 2322: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2325: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2328: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 232b: aastore
      // 232c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 232f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2332: ldc "left_arm"
      // 2334: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2337: dup
      // 2338: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 233b: bipush 3
      // 233c: anewarray 28
      // 233f: dup
      // 2340: bipush 0
      // 2341: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2344: dup
      // 2345: fconst_0
      // 2346: ldc_w -145.1839
      // 2349: ldc_w -14.9084
      // 234c: ldc_w 20.3018
      // 234f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2352: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2355: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2358: aastore
      // 2359: dup
      // 235a: bipush 1
      // 235b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 235e: dup
      // 235f: ldc 0.375
      // 2361: ldc_w -150.1839
      // 2364: ldc_w -14.9084
      // 2367: ldc_w 20.3018
      // 236a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 236d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2370: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2373: aastore
      // 2374: dup
      // 2375: bipush 2
      // 2376: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2379: dup
      // 237a: fconst_1
      // 237b: ldc_w -145.1839
      // 237e: ldc_w -14.9084
      // 2381: ldc_w 20.3018
      // 2384: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2387: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 238a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 238d: aastore
      // 238e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2391: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2394: ldc "left_arm2"
      // 2396: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2399: dup
      // 239a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 239d: bipush 3
      // 239e: anewarray 28
      // 23a1: dup
      // 23a2: bipush 0
      // 23a3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 23a6: dup
      // 23a7: fconst_0
      // 23a8: ldc_w -40.0
      // 23ab: fconst_0
      // 23ac: fconst_0
      // 23ad: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23b0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 23b3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 23b6: aastore
      // 23b7: dup
      // 23b8: bipush 1
      // 23b9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 23bc: dup
      // 23bd: ldc 0.5
      // 23bf: ldc_w -47.5
      // 23c2: fconst_0
      // 23c3: fconst_0
      // 23c4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 23ca: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 23cd: aastore
      // 23ce: dup
      // 23cf: bipush 2
      // 23d0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 23d3: dup
      // 23d4: fconst_1
      // 23d5: ldc_w -40.0
      // 23d8: fconst_0
      // 23d9: fconst_0
      // 23da: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23dd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 23e0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 23e3: aastore
      // 23e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 23e7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 23ea: ldc "right_leg"
      // 23ec: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 23ef: dup
      // 23f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 23f3: bipush 3
      // 23f4: anewarray 28
      // 23f7: dup
      // 23f8: bipush 0
      // 23f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 23fc: dup
      // 23fd: fconst_0
      // 23fe: ldc_w -20.0
      // 2401: fconst_0
      // 2402: fconst_0
      // 2403: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2406: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2409: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 240c: aastore
      // 240d: dup
      // 240e: bipush 1
      // 240f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2412: dup
      // 2413: ldc 0.625
      // 2415: ldc_w -30.0
      // 2418: fconst_0
      // 2419: fconst_0
      // 241a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 241d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2420: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2423: aastore
      // 2424: dup
      // 2425: bipush 2
      // 2426: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2429: dup
      // 242a: fconst_1
      // 242b: ldc_w -20.0
      // 242e: fconst_0
      // 242f: fconst_0
      // 2430: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2433: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2436: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2439: aastore
      // 243a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 243d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2440: ldc "right_leg"
      // 2442: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2445: dup
      // 2446: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2449: bipush 3
      // 244a: anewarray 28
      // 244d: dup
      // 244e: bipush 0
      // 244f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2452: dup
      // 2453: fconst_0
      // 2454: ldc -1.0
      // 2456: ldc 8.0
      // 2458: ldc -5.0
      // 245a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 245d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2460: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2463: aastore
      // 2464: dup
      // 2465: bipush 1
      // 2466: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2469: dup
      // 246a: ldc 0.625
      // 246c: ldc -1.0
      // 246e: ldc_w 9.0
      // 2471: ldc -6.0
      // 2473: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2476: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2479: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 247c: aastore
      // 247d: dup
      // 247e: bipush 2
      // 247f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2482: dup
      // 2483: fconst_1
      // 2484: ldc -1.0
      // 2486: ldc 8.0
      // 2488: ldc -5.0
      // 248a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 248d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2490: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2493: aastore
      // 2494: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2497: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 249a: ldc "left_leg"
      // 249c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 249f: dup
      // 24a0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 24a3: bipush 3
      // 24a4: anewarray 28
      // 24a7: dup
      // 24a8: bipush 0
      // 24a9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 24ac: dup
      // 24ad: fconst_0
      // 24ae: ldc_w 35.0
      // 24b1: fconst_0
      // 24b2: fconst_0
      // 24b3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24b6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 24b9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 24bc: aastore
      // 24bd: dup
      // 24be: bipush 1
      // 24bf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 24c2: dup
      // 24c3: ldc 0.5
      // 24c5: ldc_w 40.0
      // 24c8: fconst_0
      // 24c9: fconst_0
      // 24ca: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24cd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 24d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 24d3: aastore
      // 24d4: dup
      // 24d5: bipush 2
      // 24d6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 24d9: dup
      // 24da: fconst_1
      // 24db: ldc_w 35.0
      // 24de: fconst_0
      // 24df: fconst_0
      // 24e0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24e3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 24e6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 24e9: aastore
      // 24ea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 24ed: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 24f0: ldc "left_leg"
      // 24f2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 24f5: dup
      // 24f6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 24f9: bipush 3
      // 24fa: anewarray 28
      // 24fd: dup
      // 24fe: bipush 0
      // 24ff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2502: dup
      // 2503: fconst_0
      // 2504: fconst_0
      // 2505: ldc_w 9.0
      // 2508: ldc_w 5.6
      // 250b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 250e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2511: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2514: aastore
      // 2515: dup
      // 2516: bipush 1
      // 2517: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 251a: dup
      // 251b: ldc 0.5
      // 251d: fconst_0
      // 251e: ldc 8.0
      // 2520: ldc_w 6.6
      // 2523: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2526: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2529: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 252c: aastore
      // 252d: dup
      // 252e: bipush 2
      // 252f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2532: dup
      // 2533: fconst_1
      // 2534: fconst_0
      // 2535: ldc_w 9.0
      // 2538: ldc_w 5.6
      // 253b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 253e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2541: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2544: aastore
      // 2545: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2548: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 254b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 254e: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.JUMPING_LOOP Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 2551: fconst_1
      // 2552: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2555: ldc "body"
      // 2557: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 255a: dup
      // 255b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 255e: bipush 4
      // 255f: anewarray 28
      // 2562: dup
      // 2563: bipush 0
      // 2564: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2567: dup
      // 2568: fconst_0
      // 2569: ldc_w -25.0
      // 256c: fconst_0
      // 256d: fconst_0
      // 256e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2571: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2574: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2577: aastore
      // 2578: dup
      // 2579: bipush 1
      // 257a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 257d: dup
      // 257e: ldc 0.125
      // 2580: fconst_0
      // 2581: fconst_0
      // 2582: fconst_0
      // 2583: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2586: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2589: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 258c: aastore
      // 258d: dup
      // 258e: bipush 2
      // 258f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2592: dup
      // 2593: ldc 0.5
      // 2595: ldc 5.0
      // 2597: fconst_0
      // 2598: fconst_0
      // 2599: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 259c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 259f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 25a2: aastore
      // 25a3: dup
      // 25a4: bipush 3
      // 25a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 25a8: dup
      // 25a9: fconst_1
      // 25aa: fconst_0
      // 25ab: fconst_0
      // 25ac: fconst_0
      // 25ad: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25b0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 25b3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 25b6: aastore
      // 25b7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 25ba: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 25bd: ldc "body"
      // 25bf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 25c2: dup
      // 25c3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 25c6: bipush 4
      // 25c7: anewarray 28
      // 25ca: dup
      // 25cb: bipush 0
      // 25cc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 25cf: dup
      // 25d0: fconst_0
      // 25d1: fconst_0
      // 25d2: ldc_w 5.3
      // 25d5: fconst_0
      // 25d6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25d9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 25dc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 25df: aastore
      // 25e0: dup
      // 25e1: bipush 1
      // 25e2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 25e5: dup
      // 25e6: ldc 0.125
      // 25e8: fconst_0
      // 25e9: ldc -4.0
      // 25eb: ldc -6.0
      // 25ed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 25f3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 25f6: aastore
      // 25f7: dup
      // 25f8: bipush 2
      // 25f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 25fc: dup
      // 25fd: ldc 0.5
      // 25ff: fconst_0
      // 2600: ldc -4.0
      // 2602: ldc -7.0
      // 2604: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2607: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 260a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 260d: aastore
      // 260e: dup
      // 260f: bipush 3
      // 2610: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2613: dup
      // 2614: fconst_1
      // 2615: fconst_0
      // 2616: fconst_0
      // 2617: fconst_0
      // 2618: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 261b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 261e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2621: aastore
      // 2622: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2625: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2628: ldc "chest"
      // 262a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 262d: dup
      // 262e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2631: bipush 4
      // 2632: anewarray 28
      // 2635: dup
      // 2636: bipush 0
      // 2637: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 263a: dup
      // 263b: fconst_0
      // 263c: fconst_0
      // 263d: fconst_0
      // 263e: fconst_0
      // 263f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2642: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2645: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2648: aastore
      // 2649: dup
      // 264a: bipush 1
      // 264b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 264e: dup
      // 264f: ldc 0.125
      // 2651: ldc_w 7.5
      // 2654: fconst_0
      // 2655: fconst_0
      // 2656: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2659: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 265c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 265f: aastore
      // 2660: dup
      // 2661: bipush 2
      // 2662: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2665: dup
      // 2666: ldc 0.5
      // 2668: ldc_w 12.5
      // 266b: fconst_0
      // 266c: fconst_0
      // 266d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2670: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2673: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2676: aastore
      // 2677: dup
      // 2678: bipush 3
      // 2679: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 267c: dup
      // 267d: fconst_1
      // 267e: fconst_0
      // 267f: fconst_0
      // 2680: fconst_0
      // 2681: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2684: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2687: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 268a: aastore
      // 268b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 268e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2691: ldc "head"
      // 2693: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2696: dup
      // 2697: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 269a: bipush 4
      // 269b: anewarray 28
      // 269e: dup
      // 269f: bipush 0
      // 26a0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 26a3: dup
      // 26a4: fconst_0
      // 26a5: ldc_w 40.0
      // 26a8: fconst_0
      // 26a9: fconst_0
      // 26aa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26ad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 26b0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 26b3: aastore
      // 26b4: dup
      // 26b5: bipush 1
      // 26b6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 26b9: dup
      // 26ba: ldc 0.125
      // 26bc: ldc 2.5
      // 26be: fconst_0
      // 26bf: fconst_0
      // 26c0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26c3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 26c6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 26c9: aastore
      // 26ca: dup
      // 26cb: bipush 2
      // 26cc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 26cf: dup
      // 26d0: ldc 0.5
      // 26d2: ldc_w 17.5
      // 26d5: fconst_0
      // 26d6: fconst_0
      // 26d7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26da: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 26dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 26e0: aastore
      // 26e1: dup
      // 26e2: bipush 3
      // 26e3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 26e6: dup
      // 26e7: fconst_1
      // 26e8: fconst_0
      // 26e9: fconst_0
      // 26ea: fconst_0
      // 26eb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26ee: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 26f1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 26f4: aastore
      // 26f5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 26f8: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 26fb: ldc "eye"
      // 26fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2700: dup
      // 2701: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2704: bipush 4
      // 2705: anewarray 28
      // 2708: dup
      // 2709: bipush 0
      // 270a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 270d: dup
      // 270e: fconst_0
      // 270f: fconst_0
      // 2710: ldc_w -0.3
      // 2713: fconst_0
      // 2714: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2717: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 271a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 271d: aastore
      // 271e: dup
      // 271f: bipush 1
      // 2720: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2723: dup
      // 2724: ldc 0.25
      // 2726: fconst_0
      // 2727: ldc_w -0.94
      // 272a: fconst_0
      // 272b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 272e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2731: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2734: aastore
      // 2735: dup
      // 2736: bipush 2
      // 2737: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 273a: dup
      // 273b: ldc_w 0.5417
      // 273e: fconst_0
      // 273f: ldc_w 0.15
      // 2742: fconst_0
      // 2743: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2746: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2749: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 274c: aastore
      // 274d: dup
      // 274e: bipush 3
      // 274f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2752: dup
      // 2753: fconst_1
      // 2754: fconst_0
      // 2755: fconst_0
      // 2756: fconst_0
      // 2757: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 275a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 275d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2760: aastore
      // 2761: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2764: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2767: ldc "right_arm"
      // 2769: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 276c: dup
      // 276d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2770: bipush 4
      // 2771: anewarray 28
      // 2774: dup
      // 2775: bipush 0
      // 2776: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2779: dup
      // 277a: fconst_0
      // 277b: ldc_w -145.1839
      // 277e: ldc_w 14.9084
      // 2781: ldc_w -20.3018
      // 2784: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2787: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 278a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 278d: aastore
      // 278e: dup
      // 278f: bipush 1
      // 2790: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2793: dup
      // 2794: ldc 0.125
      // 2796: ldc_w -53.1045
      // 2799: ldc_w -13.5536
      // 279c: ldc_w -27.5271
      // 279f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27a2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 27a5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 27a8: aastore
      // 27a9: dup
      // 27aa: bipush 2
      // 27ab: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 27ae: dup
      // 27af: ldc 0.5
      // 27b1: ldc_w -30.2131
      // 27b4: ldc_w -15.548
      // 27b7: ldc_w -29.0847
      // 27ba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27bd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 27c0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 27c3: aastore
      // 27c4: dup
      // 27c5: bipush 3
      // 27c6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 27c9: dup
      // 27ca: fconst_1
      // 27cb: fconst_0
      // 27cc: fconst_0
      // 27cd: fconst_0
      // 27ce: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27d1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 27d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 27d7: aastore
      // 27d8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 27db: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 27de: ldc "right_arm"
      // 27e0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 27e3: dup
      // 27e4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 27e7: bipush 4
      // 27e8: anewarray 28
      // 27eb: dup
      // 27ec: bipush 0
      // 27ed: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 27f0: dup
      // 27f1: fconst_0
      // 27f2: fconst_0
      // 27f3: fconst_0
      // 27f4: fconst_0
      // 27f5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27f8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 27fb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 27fe: aastore
      // 27ff: dup
      // 2800: bipush 1
      // 2801: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2804: dup
      // 2805: ldc 0.125
      // 2807: fconst_0
      // 2808: fconst_2
      // 2809: ldc -5.0
      // 280b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 280e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2811: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2814: aastore
      // 2815: dup
      // 2816: bipush 2
      // 2817: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 281a: dup
      // 281b: ldc 0.75
      // 281d: fconst_0
      // 281e: ldc_w 3.54
      // 2821: ldc_w -7.36
      // 2824: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2827: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 282a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 282d: aastore
      // 282e: dup
      // 282f: bipush 3
      // 2830: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2833: dup
      // 2834: fconst_1
      // 2835: fconst_0
      // 2836: fconst_0
      // 2837: fconst_0
      // 2838: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 283b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 283e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2841: aastore
      // 2842: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2845: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2848: ldc "right_arm2"
      // 284a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 284d: dup
      // 284e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2851: bipush 4
      // 2852: anewarray 28
      // 2855: dup
      // 2856: bipush 0
      // 2857: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 285a: dup
      // 285b: fconst_0
      // 285c: ldc_w -40.0
      // 285f: fconst_0
      // 2860: fconst_0
      // 2861: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2864: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2867: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 286a: aastore
      // 286b: dup
      // 286c: bipush 1
      // 286d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2870: dup
      // 2871: ldc 0.125
      // 2873: fconst_0
      // 2874: fconst_0
      // 2875: fconst_0
      // 2876: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2879: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 287c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 287f: aastore
      // 2880: dup
      // 2881: bipush 2
      // 2882: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2885: dup
      // 2886: ldc 0.5
      // 2888: ldc_w -20.0
      // 288b: fconst_0
      // 288c: fconst_0
      // 288d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2890: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2893: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2896: aastore
      // 2897: dup
      // 2898: bipush 3
      // 2899: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 289c: dup
      // 289d: fconst_1
      // 289e: fconst_0
      // 289f: fconst_0
      // 28a0: fconst_0
      // 28a1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28a4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 28a7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 28aa: aastore
      // 28ab: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 28ae: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 28b1: ldc "left_arm"
      // 28b3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 28b6: dup
      // 28b7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 28ba: bipush 4
      // 28bb: anewarray 28
      // 28be: dup
      // 28bf: bipush 0
      // 28c0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 28c3: dup
      // 28c4: fconst_0
      // 28c5: ldc_w -145.1839
      // 28c8: ldc_w -14.9084
      // 28cb: ldc_w 20.3018
      // 28ce: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28d1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 28d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 28d7: aastore
      // 28d8: dup
      // 28d9: bipush 1
      // 28da: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 28dd: dup
      // 28de: ldc 0.125
      // 28e0: ldc_w -53.1045
      // 28e3: ldc_w 13.5536
      // 28e6: ldc_w 27.5271
      // 28e9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28ec: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 28ef: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 28f2: aastore
      // 28f3: dup
      // 28f4: bipush 2
      // 28f5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 28f8: dup
      // 28f9: ldc 0.5
      // 28fb: ldc_w -30.2131
      // 28fe: ldc_w 15.548
      // 2901: ldc_w 29.0847
      // 2904: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2907: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 290a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 290d: aastore
      // 290e: dup
      // 290f: bipush 3
      // 2910: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2913: dup
      // 2914: fconst_1
      // 2915: fconst_0
      // 2916: fconst_0
      // 2917: fconst_0
      // 2918: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 291b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 291e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2921: aastore
      // 2922: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2925: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2928: ldc "left_arm"
      // 292a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 292d: dup
      // 292e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2931: bipush 4
      // 2932: anewarray 28
      // 2935: dup
      // 2936: bipush 0
      // 2937: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 293a: dup
      // 293b: fconst_0
      // 293c: fconst_0
      // 293d: fconst_0
      // 293e: fconst_0
      // 293f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2942: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2945: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2948: aastore
      // 2949: dup
      // 294a: bipush 1
      // 294b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 294e: dup
      // 294f: ldc 0.125
      // 2951: fconst_0
      // 2952: fconst_2
      // 2953: ldc -5.0
      // 2955: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2958: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 295b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 295e: aastore
      // 295f: dup
      // 2960: bipush 2
      // 2961: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2964: dup
      // 2965: ldc 0.75
      // 2967: fconst_0
      // 2968: ldc_w 3.54
      // 296b: ldc_w -7.36
      // 296e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2971: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2974: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2977: aastore
      // 2978: dup
      // 2979: bipush 3
      // 297a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 297d: dup
      // 297e: fconst_1
      // 297f: fconst_0
      // 2980: fconst_0
      // 2981: fconst_0
      // 2982: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2985: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2988: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 298b: aastore
      // 298c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 298f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2992: ldc "left_arm2"
      // 2994: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2997: dup
      // 2998: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 299b: bipush 4
      // 299c: anewarray 28
      // 299f: dup
      // 29a0: bipush 0
      // 29a1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 29a4: dup
      // 29a5: fconst_0
      // 29a6: ldc_w -40.0
      // 29a9: fconst_0
      // 29aa: fconst_0
      // 29ab: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29ae: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 29b1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 29b4: aastore
      // 29b5: dup
      // 29b6: bipush 1
      // 29b7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 29ba: dup
      // 29bb: ldc 0.125
      // 29bd: fconst_0
      // 29be: fconst_0
      // 29bf: fconst_0
      // 29c0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29c3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 29c6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 29c9: aastore
      // 29ca: dup
      // 29cb: bipush 2
      // 29cc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 29cf: dup
      // 29d0: ldc 0.5
      // 29d2: ldc_w -20.0
      // 29d5: fconst_0
      // 29d6: fconst_0
      // 29d7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29da: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 29dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 29e0: aastore
      // 29e1: dup
      // 29e2: bipush 3
      // 29e3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 29e6: dup
      // 29e7: fconst_1
      // 29e8: fconst_0
      // 29e9: fconst_0
      // 29ea: fconst_0
      // 29eb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29ee: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 29f1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 29f4: aastore
      // 29f5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 29f8: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 29fb: ldc "right_leg"
      // 29fd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2a00: dup
      // 2a01: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2a04: bipush 4
      // 2a05: anewarray 28
      // 2a08: dup
      // 2a09: bipush 0
      // 2a0a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a0d: dup
      // 2a0e: fconst_0
      // 2a0f: ldc_w -20.0
      // 2a12: fconst_0
      // 2a13: fconst_0
      // 2a14: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a17: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a1a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2a1d: aastore
      // 2a1e: dup
      // 2a1f: bipush 1
      // 2a20: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a23: dup
      // 2a24: ldc 0.125
      // 2a26: ldc 70.0
      // 2a28: fconst_0
      // 2a29: fconst_0
      // 2a2a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a2d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a30: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2a33: aastore
      // 2a34: dup
      // 2a35: bipush 2
      // 2a36: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a39: dup
      // 2a3a: ldc 0.5
      // 2a3c: ldc_w 62.5
      // 2a3f: fconst_0
      // 2a40: fconst_0
      // 2a41: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a44: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a47: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2a4a: aastore
      // 2a4b: dup
      // 2a4c: bipush 3
      // 2a4d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a50: dup
      // 2a51: fconst_1
      // 2a52: fconst_0
      // 2a53: fconst_0
      // 2a54: fconst_0
      // 2a55: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a58: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a5b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2a5e: aastore
      // 2a5f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2a62: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2a65: ldc "right_leg"
      // 2a67: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2a6a: dup
      // 2a6b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2a6e: bipush 4
      // 2a6f: anewarray 28
      // 2a72: dup
      // 2a73: bipush 0
      // 2a74: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a77: dup
      // 2a78: fconst_0
      // 2a79: ldc -1.0
      // 2a7b: ldc 8.0
      // 2a7d: ldc -5.0
      // 2a7f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a82: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a85: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2a88: aastore
      // 2a89: dup
      // 2a8a: bipush 1
      // 2a8b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2a8e: dup
      // 2a8f: ldc 0.125
      // 2a91: ldc -1.0
      // 2a93: ldc -4.0
      // 2a95: ldc -6.0
      // 2a97: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a9a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2a9d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2aa0: aastore
      // 2aa1: dup
      // 2aa2: bipush 2
      // 2aa3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2aa6: dup
      // 2aa7: ldc 0.5
      // 2aa9: ldc -1.0
      // 2aab: ldc -4.0
      // 2aad: ldc -7.0
      // 2aaf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ab2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ab5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ab8: aastore
      // 2ab9: dup
      // 2aba: bipush 3
      // 2abb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2abe: dup
      // 2abf: fconst_1
      // 2ac0: fconst_0
      // 2ac1: fconst_0
      // 2ac2: fconst_0
      // 2ac3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ac6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ac9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2acc: aastore
      // 2acd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2ad0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2ad3: ldc "left_leg"
      // 2ad5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2ad8: dup
      // 2ad9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2adc: bipush 4
      // 2add: anewarray 28
      // 2ae0: dup
      // 2ae1: bipush 0
      // 2ae2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2ae5: dup
      // 2ae6: fconst_0
      // 2ae7: ldc_w 35.0
      // 2aea: fconst_0
      // 2aeb: fconst_0
      // 2aec: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aef: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2af2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2af5: aastore
      // 2af6: dup
      // 2af7: bipush 1
      // 2af8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2afb: dup
      // 2afc: ldc 0.125
      // 2afe: ldc 25.0
      // 2b00: fconst_0
      // 2b01: fconst_0
      // 2b02: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b05: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b08: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b0b: aastore
      // 2b0c: dup
      // 2b0d: bipush 2
      // 2b0e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b11: dup
      // 2b12: ldc 0.5
      // 2b14: ldc_w 27.0627
      // 2b17: ldc_w 0.7686
      // 2b1a: ldc_w -1.1714
      // 2b1d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b20: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b23: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b26: aastore
      // 2b27: dup
      // 2b28: bipush 3
      // 2b29: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b2c: dup
      // 2b2d: fconst_1
      // 2b2e: fconst_0
      // 2b2f: fconst_0
      // 2b30: fconst_0
      // 2b31: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b34: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b37: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b3a: aastore
      // 2b3b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2b3e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2b41: ldc "left_leg"
      // 2b43: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2b46: dup
      // 2b47: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2b4a: bipush 4
      // 2b4b: anewarray 28
      // 2b4e: dup
      // 2b4f: bipush 0
      // 2b50: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b53: dup
      // 2b54: fconst_0
      // 2b55: fconst_0
      // 2b56: ldc_w 9.0
      // 2b59: ldc_w 5.6
      // 2b5c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b5f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b62: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b65: aastore
      // 2b66: dup
      // 2b67: bipush 1
      // 2b68: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b6b: dup
      // 2b6c: ldc 0.125
      // 2b6e: fconst_0
      // 2b6f: fconst_0
      // 2b70: fconst_2
      // 2b71: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b74: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b77: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b7a: aastore
      // 2b7b: dup
      // 2b7c: bipush 2
      // 2b7d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b80: dup
      // 2b81: ldc 0.5
      // 2b83: fconst_0
      // 2b84: fconst_0
      // 2b85: fconst_1
      // 2b86: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b89: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2b8c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2b8f: aastore
      // 2b90: dup
      // 2b91: bipush 3
      // 2b92: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2b95: dup
      // 2b96: fconst_1
      // 2b97: fconst_0
      // 2b98: fconst_0
      // 2b99: fconst_0
      // 2b9a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b9d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ba0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ba3: aastore
      // 2ba4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2ba7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2baa: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 2bad: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.JUMPING_FISH Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 2bb0: fconst_2
      // 2bb1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2bb4: ldc "body"
      // 2bb6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2bb9: dup
      // 2bba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2bbd: bipush 5
      // 2bbe: anewarray 28
      // 2bc1: dup
      // 2bc2: bipush 0
      // 2bc3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2bc6: dup
      // 2bc7: fconst_0
      // 2bc8: fconst_0
      // 2bc9: fconst_0
      // 2bca: fconst_0
      // 2bcb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bce: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2bd1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2bd4: aastore
      // 2bd5: dup
      // 2bd6: bipush 1
      // 2bd7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2bda: dup
      // 2bdb: ldc 0.5
      // 2bdd: ldc_w -17.5
      // 2be0: fconst_0
      // 2be1: fconst_0
      // 2be2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2be5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2be8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2beb: aastore
      // 2bec: dup
      // 2bed: bipush 2
      // 2bee: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2bf1: dup
      // 2bf2: ldc 0.75
      // 2bf4: ldc_w -15.0
      // 2bf7: fconst_0
      // 2bf8: fconst_0
      // 2bf9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bfc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2bff: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c02: aastore
      // 2c03: dup
      // 2c04: bipush 3
      // 2c05: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c08: dup
      // 2c09: ldc 0.875
      // 2c0b: ldc 5.0
      // 2c0d: fconst_0
      // 2c0e: fconst_0
      // 2c0f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c12: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2c15: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c18: aastore
      // 2c19: dup
      // 2c1a: bipush 4
      // 2c1b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c1e: dup
      // 2c1f: fconst_2
      // 2c20: ldc -27.5896
      // 2c22: ldc -4.4339
      // 2c24: ldc 2.3134
      // 2c26: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c29: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2c2c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c2f: aastore
      // 2c30: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2c33: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2c36: ldc "body"
      // 2c38: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2c3b: dup
      // 2c3c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2c3f: bipush 1
      // 2c40: anewarray 28
      // 2c43: dup
      // 2c44: bipush 0
      // 2c45: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c48: dup
      // 2c49: fconst_2
      // 2c4a: fconst_0
      // 2c4b: ldc -4.0
      // 2c4d: ldc -6.0
      // 2c4f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c52: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2c55: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c58: aastore
      // 2c59: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2c5c: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2c5f: ldc "chest"
      // 2c61: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2c64: dup
      // 2c65: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2c68: bipush 7
      // 2c6a: anewarray 28
      // 2c6d: dup
      // 2c6e: bipush 0
      // 2c6f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c72: dup
      // 2c73: fconst_0
      // 2c74: fconst_0
      // 2c75: fconst_0
      // 2c76: fconst_0
      // 2c77: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c7a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2c7d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c80: aastore
      // 2c81: dup
      // 2c82: bipush 1
      // 2c83: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c86: dup
      // 2c87: ldc 0.5
      // 2c89: ldc_w -15.0
      // 2c8c: fconst_0
      // 2c8d: fconst_0
      // 2c8e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c91: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2c94: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2c97: aastore
      // 2c98: dup
      // 2c99: bipush 2
      // 2c9a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2c9d: dup
      // 2c9e: ldc 0.75
      // 2ca0: ldc_w -15.0
      // 2ca3: fconst_0
      // 2ca4: fconst_0
      // 2ca5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ca8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2cab: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2cae: aastore
      // 2caf: dup
      // 2cb0: bipush 3
      // 2cb1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2cb4: dup
      // 2cb5: ldc 0.875
      // 2cb7: ldc_w -7.3924
      // 2cba: ldc_w -3.8282
      // 2cbd: ldc_w -3.2187
      // 2cc0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2cc6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2cc9: aastore
      // 2cca: dup
      // 2ccb: bipush 4
      // 2ccc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2ccf: dup
      // 2cd0: ldc 1.25
      // 2cd2: ldc_w -7.0675
      // 2cd5: ldc_w 7.6443
      // 2cd8: ldc_w 6.4664
      // 2cdb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cde: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ce1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ce4: aastore
      // 2ce5: dup
      // 2ce6: bipush 5
      // 2ce7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2cea: dup
      // 2ceb: ldc 1.75
      // 2ced: ldc_w -7.0675
      // 2cf0: ldc_w -7.6443
      // 2cf3: ldc_w -6.4664
      // 2cf6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cf9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2cfc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2cff: aastore
      // 2d00: dup
      // 2d01: bipush 6
      // 2d03: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d06: dup
      // 2d07: fconst_2
      // 2d08: ldc 2.4363
      // 2d0a: ldc 7.082
      // 2d0c: ldc 17.1819
      // 2d0e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d11: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2d14: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2d17: aastore
      // 2d18: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2d1b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2d1e: ldc "head"
      // 2d20: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2d23: dup
      // 2d24: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2d27: bipush 7
      // 2d29: anewarray 28
      // 2d2c: dup
      // 2d2d: bipush 0
      // 2d2e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d31: dup
      // 2d32: fconst_0
      // 2d33: fconst_0
      // 2d34: fconst_0
      // 2d35: fconst_0
      // 2d36: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d39: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2d3c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2d3f: aastore
      // 2d40: dup
      // 2d41: bipush 1
      // 2d42: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d45: dup
      // 2d46: ldc 0.5
      // 2d48: ldc_w 60.0
      // 2d4b: fconst_0
      // 2d4c: fconst_0
      // 2d4d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d50: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2d53: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2d56: aastore
      // 2d57: dup
      // 2d58: bipush 2
      // 2d59: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d5c: dup
      // 2d5d: ldc 0.75
      // 2d5f: ldc_w 62.5
      // 2d62: fconst_0
      // 2d63: fconst_0
      // 2d64: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d67: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2d6a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2d6d: aastore
      // 2d6e: dup
      // 2d6f: bipush 3
      // 2d70: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d73: dup
      // 2d74: ldc 0.875
      // 2d76: ldc_w 12.3973
      // 2d79: ldc_w 4.094
      // 2d7c: ldc_w -2.8728
      // 2d7f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d82: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2d85: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2d88: aastore
      // 2d89: dup
      // 2d8a: bipush 4
      // 2d8b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2d8e: dup
      // 2d8f: ldc 1.25
      // 2d91: ldc_w 11.5613
      // 2d94: ldc_w -12.2403
      // 2d97: ldc_w 8.7374
      // 2d9a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d9d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2da0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2da3: aastore
      // 2da4: dup
      // 2da5: bipush 5
      // 2da6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2da9: dup
      // 2daa: ldc 1.75
      // 2dac: ldc_w 12.082
      // 2daf: ldc_w 8.3907
      // 2db2: ldc_w 3.2336
      // 2db5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2db8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2dbb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2dbe: aastore
      // 2dbf: dup
      // 2dc0: bipush 6
      // 2dc2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2dc5: dup
      // 2dc6: fconst_2
      // 2dc7: ldc 34.987
      // 2dc9: ldc -19.3289
      // 2dcb: ldc -5.5392
      // 2dcd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dd0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2dd3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2dd6: aastore
      // 2dd7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 2dda: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 2ddd: ldc "eye"
      // 2ddf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 2de2: dup
      // 2de3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 2de6: bipush 23
      // 2de8: anewarray 28
      // 2deb: dup
      // 2dec: bipush 0
      // 2ded: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2df0: dup
      // 2df1: fconst_0
      // 2df2: fconst_0
      // 2df3: fconst_0
      // 2df4: fconst_0
      // 2df5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2df8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2dfb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2dfe: aastore
      // 2dff: dup
      // 2e00: bipush 1
      // 2e01: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e04: dup
      // 2e05: ldc 0.5
      // 2e07: fconst_0
      // 2e08: ldc -1.0
      // 2e0a: fconst_0
      // 2e0b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e0e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e11: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e14: aastore
      // 2e15: dup
      // 2e16: bipush 2
      // 2e17: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e1a: dup
      // 2e1b: ldc 0.8333
      // 2e1d: fconst_0
      // 2e1e: fconst_0
      // 2e1f: fconst_0
      // 2e20: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e23: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e26: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e29: aastore
      // 2e2a: dup
      // 2e2b: bipush 3
      // 2e2c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e2f: dup
      // 2e30: ldc 0.875
      // 2e32: ldc_w 0.3
      // 2e35: ldc_w -0.3
      // 2e38: fconst_0
      // 2e39: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e3c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e3f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e42: aastore
      // 2e43: dup
      // 2e44: bipush 4
      // 2e45: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e48: dup
      // 2e49: ldc_w 0.9167
      // 2e4c: ldc -0.2
      // 2e4e: ldc_w -0.1
      // 2e51: fconst_0
      // 2e52: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e55: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e58: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e5b: aastore
      // 2e5c: dup
      // 2e5d: bipush 5
      // 2e5e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e61: dup
      // 2e62: ldc_w 0.9583
      // 2e65: fconst_0
      // 2e66: ldc_w -0.1
      // 2e69: fconst_0
      // 2e6a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e6d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e70: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e73: aastore
      // 2e74: dup
      // 2e75: bipush 6
      // 2e77: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e7a: dup
      // 2e7b: fconst_1
      // 2e7c: fconst_0
      // 2e7d: ldc -0.2
      // 2e7f: fconst_0
      // 2e80: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e83: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2e86: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2e89: aastore
      // 2e8a: dup
      // 2e8b: bipush 7
      // 2e8d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2e90: dup
      // 2e91: ldc_w 1.0417
      // 2e94: ldc_w 0.3
      // 2e97: ldc -0.2
      // 2e99: fconst_0
      // 2e9a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e9d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ea0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ea3: aastore
      // 2ea4: dup
      // 2ea5: bipush 8
      // 2ea7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2eaa: dup
      // 2eab: ldc_w 1.0833
      // 2eae: ldc_w 0.3
      // 2eb1: ldc -0.5
      // 2eb3: fconst_0
      // 2eb4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2eb7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2eba: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ebd: aastore
      // 2ebe: dup
      // 2ebf: bipush 9
      // 2ec1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2ec4: dup
      // 2ec5: ldc_w 1.125
      // 2ec8: fconst_0
      // 2ec9: ldc -0.5
      // 2ecb: fconst_0
      // 2ecc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ecf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ed2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ed5: aastore
      // 2ed6: dup
      // 2ed7: bipush 10
      // 2ed9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2edc: dup
      // 2edd: ldc_w 1.1667
      // 2ee0: ldc_w 0.3
      // 2ee3: ldc -0.5
      // 2ee5: fconst_0
      // 2ee6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ee9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2eec: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2eef: aastore
      // 2ef0: dup
      // 2ef1: bipush 11
      // 2ef3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2ef6: dup
      // 2ef7: ldc_w 1.2083
      // 2efa: ldc_w 0.3
      // 2efd: ldc_w -0.8
      // 2f00: fconst_0
      // 2f01: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f04: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f07: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f0a: aastore
      // 2f0b: dup
      // 2f0c: bipush 12
      // 2f0e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f11: dup
      // 2f12: ldc 1.25
      // 2f14: ldc_w -0.1
      // 2f17: ldc_w -0.8
      // 2f1a: fconst_0
      // 2f1b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f1e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f21: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f24: aastore
      // 2f25: dup
      // 2f26: bipush 13
      // 2f28: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f2b: dup
      // 2f2c: ldc_w 1.2917
      // 2f2f: ldc_w -0.1
      // 2f32: ldc_w -0.3
      // 2f35: fconst_0
      // 2f36: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f39: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f3c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f3f: aastore
      // 2f40: dup
      // 2f41: bipush 14
      // 2f43: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f46: dup
      // 2f47: ldc_w 1.3333
      // 2f4a: ldc_w 0.3
      // 2f4d: ldc_w -0.3
      // 2f50: fconst_0
      // 2f51: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f54: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f57: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f5a: aastore
      // 2f5b: dup
      // 2f5c: bipush 15
      // 2f5e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f61: dup
      // 2f62: ldc_w 1.375
      // 2f65: ldc_w 0.3
      // 2f68: ldc_w -0.7
      // 2f6b: fconst_0
      // 2f6c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f6f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f72: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f75: aastore
      // 2f76: dup
      // 2f77: bipush 16
      // 2f79: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f7c: dup
      // 2f7d: ldc_w 1.4167
      // 2f80: ldc_w 0.3
      // 2f83: ldc_w -0.4
      // 2f86: fconst_0
      // 2f87: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f8a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2f8d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2f90: aastore
      // 2f91: dup
      // 2f92: bipush 17
      // 2f94: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2f97: dup
      // 2f98: ldc_w 1.4583
      // 2f9b: fconst_0
      // 2f9c: ldc_w -0.4
      // 2f9f: fconst_0
      // 2fa0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2fa3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2fa6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2fa9: aastore
      // 2faa: dup
      // 2fab: bipush 18
      // 2fad: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2fb0: dup
      // 2fb1: ldc 1.5
      // 2fb3: ldc 0.4
      // 2fb5: ldc_w -0.4
      // 2fb8: fconst_0
      // 2fb9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2fbc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2fbf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2fc2: aastore
      // 2fc3: dup
      // 2fc4: bipush 19
      // 2fc6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2fc9: dup
      // 2fca: ldc 1.5417
      // 2fcc: ldc_w 0.1
      // 2fcf: ldc_w -0.4
      // 2fd2: fconst_0
      // 2fd3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2fd6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2fd9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2fdc: aastore
      // 2fdd: dup
      // 2fde: bipush 20
      // 2fe0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2fe3: dup
      // 2fe4: ldc_w 1.5833
      // 2fe7: ldc_w 0.1
      // 2fea: ldc_w -0.8
      // 2fed: fconst_0
      // 2fee: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ff1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 2ff4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 2ff7: aastore
      // 2ff8: dup
      // 2ff9: bipush 21
      // 2ffb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 2ffe: dup
      // 2fff: ldc_w 1.625
      // 3002: ldc_w 0.1
      // 3005: ldc -0.6
      // 3007: fconst_0
      // 3008: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 300b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 300e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3011: aastore
      // 3012: dup
      // 3013: bipush 22
      // 3015: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3018: dup
      // 3019: fconst_2
      // 301a: fconst_0
      // 301b: fconst_0
      // 301c: fconst_0
      // 301d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3020: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3023: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3026: aastore
      // 3027: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 302a: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 302d: ldc "right_arm"
      // 302f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3032: dup
      // 3033: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3036: bipush 7
      // 3038: anewarray 28
      // 303b: dup
      // 303c: bipush 0
      // 303d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3040: dup
      // 3041: fconst_0
      // 3042: fconst_0
      // 3043: fconst_0
      // 3044: fconst_0
      // 3045: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3048: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 304b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 304e: aastore
      // 304f: dup
      // 3050: bipush 1
      // 3051: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3054: dup
      // 3055: ldc 0.5
      // 3057: ldc_w -23.3653
      // 305a: ldc_w -13.8019
      // 305d: ldc_w -51.1811
      // 3060: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3063: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3066: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3069: aastore
      // 306a: dup
      // 306b: bipush 2
      // 306c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 306f: dup
      // 3070: ldc 0.75
      // 3072: ldc_w -21.4744
      // 3075: ldc_w -16.6662
      // 3078: ldc_w -58.3663
      // 307b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 307e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3081: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3084: aastore
      // 3085: dup
      // 3086: bipush 3
      // 3087: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 308a: dup
      // 308b: ldc 0.875
      // 308d: ldc_w 22.1209
      // 3090: ldc_w 28.1195
      // 3093: ldc_w 97.512
      // 3096: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3099: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 309c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 309f: aastore
      // 30a0: dup
      // 30a1: bipush 4
      // 30a2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 30a5: dup
      // 30a6: ldc 1.25
      // 30a8: ldc_w 28.4547
      // 30ab: ldc_w 21.6721
      // 30ae: ldc_w 112.4637
      // 30b1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30b4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 30b7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 30ba: aastore
      // 30bb: dup
      // 30bc: bipush 5
      // 30bd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 30c0: dup
      // 30c1: ldc 1.75
      // 30c3: ldc_w 30.3777
      // 30c6: ldc_w 24.5068
      // 30c9: ldc_w 104.8473
      // 30cc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30cf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 30d2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 30d5: aastore
      // 30d6: dup
      // 30d7: bipush 6
      // 30d9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 30dc: dup
      // 30dd: fconst_2
      // 30de: ldc -0.1981
      // 30e0: ldc 7.6388
      // 30e2: ldc 53.0657
      // 30e4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30e7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 30ea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 30ed: aastore
      // 30ee: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 30f1: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 30f4: ldc "right_arm"
      // 30f6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 30f9: dup
      // 30fa: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 30fd: bipush 2
      // 30fe: anewarray 28
      // 3101: dup
      // 3102: bipush 0
      // 3103: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3106: dup
      // 3107: fconst_0
      // 3108: fconst_0
      // 3109: fconst_0
      // 310a: fconst_0
      // 310b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 310e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3111: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3114: aastore
      // 3115: dup
      // 3116: bipush 1
      // 3117: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 311a: dup
      // 311b: fconst_2
      // 311c: fconst_0
      // 311d: ldc 1.75
      // 311f: ldc -1.88
      // 3121: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3124: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3127: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 312a: aastore
      // 312b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 312e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3131: ldc "right_arm2"
      // 3133: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3136: dup
      // 3137: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 313a: bipush 7
      // 313c: anewarray 28
      // 313f: dup
      // 3140: bipush 0
      // 3141: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3144: dup
      // 3145: fconst_0
      // 3146: fconst_0
      // 3147: fconst_0
      // 3148: fconst_0
      // 3149: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 314c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 314f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3152: aastore
      // 3153: dup
      // 3154: bipush 1
      // 3155: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3158: dup
      // 3159: ldc 0.5
      // 315b: fconst_0
      // 315c: fconst_0
      // 315d: ldc_w -27.5
      // 3160: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3163: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3166: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3169: aastore
      // 316a: dup
      // 316b: bipush 2
      // 316c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 316f: dup
      // 3170: ldc 0.75
      // 3172: fconst_0
      // 3173: fconst_0
      // 3174: ldc_w -32.5
      // 3177: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 317a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 317d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3180: aastore
      // 3181: dup
      // 3182: bipush 3
      // 3183: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3186: dup
      // 3187: ldc 0.875
      // 3189: fconst_0
      // 318a: fconst_0
      // 318b: fconst_0
      // 318c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 318f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3192: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3195: aastore
      // 3196: dup
      // 3197: bipush 4
      // 3198: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 319b: dup
      // 319c: ldc 1.25
      // 319e: fconst_0
      // 319f: fconst_0
      // 31a0: ldc_w -17.5
      // 31a3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31a6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 31a9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 31ac: aastore
      // 31ad: dup
      // 31ae: bipush 5
      // 31af: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 31b2: dup
      // 31b3: ldc 1.75
      // 31b5: fconst_0
      // 31b6: fconst_0
      // 31b7: ldc_w -40.0
      // 31ba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31bd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 31c0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 31c3: aastore
      // 31c4: dup
      // 31c5: bipush 6
      // 31c7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 31ca: dup
      // 31cb: fconst_2
      // 31cc: ldc -35.0
      // 31ce: fconst_0
      // 31cf: fconst_0
      // 31d0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31d3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 31d6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 31d9: aastore
      // 31da: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 31dd: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 31e0: ldc "left_arm"
      // 31e2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 31e5: dup
      // 31e6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 31e9: bipush 7
      // 31eb: anewarray 28
      // 31ee: dup
      // 31ef: bipush 0
      // 31f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 31f3: dup
      // 31f4: fconst_0
      // 31f5: fconst_0
      // 31f6: fconst_0
      // 31f7: fconst_0
      // 31f8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31fb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 31fe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3201: aastore
      // 3202: dup
      // 3203: bipush 1
      // 3204: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3207: dup
      // 3208: ldc 0.5
      // 320a: ldc_w -50.8653
      // 320d: ldc_w 13.8019
      // 3210: ldc_w 51.1811
      // 3213: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3216: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3219: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 321c: aastore
      // 321d: dup
      // 321e: bipush 2
      // 321f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3222: dup
      // 3223: ldc 0.75
      // 3225: ldc_w -49.4228
      // 3228: ldc_w 19.5632
      // 322b: ldc_w 56.1967
      // 322e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3231: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3234: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3237: aastore
      // 3238: dup
      // 3239: bipush 3
      // 323a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 323d: dup
      // 323e: ldc 0.875
      // 3240: ldc_w 22.1209
      // 3243: ldc_w -28.1195
      // 3246: ldc_w -97.512
      // 3249: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 324c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 324f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3252: aastore
      // 3253: dup
      // 3254: bipush 4
      // 3255: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3258: dup
      // 3259: ldc 1.25
      // 325b: ldc_w 4.6209
      // 325e: ldc_w -28.1195
      // 3261: ldc_w -97.512
      // 3264: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3267: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 326a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 326d: aastore
      // 326e: dup
      // 326f: bipush 5
      // 3270: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3273: dup
      // 3274: ldc 1.75
      // 3276: ldc_w -13.0646
      // 3279: ldc_w -28.4057
      // 327c: ldc_w -91.844
      // 327f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3282: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3285: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3288: aastore
      // 3289: dup
      // 328a: bipush 6
      // 328c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 328f: dup
      // 3290: fconst_2
      // 3291: ldc -2.5
      // 3293: fconst_0
      // 3294: ldc -10.0
      // 3296: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3299: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 329c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 329f: aastore
      // 32a0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 32a3: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 32a6: ldc "left_arm"
      // 32a8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 32ab: dup
      // 32ac: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 32af: bipush 1
      // 32b0: anewarray 28
      // 32b3: dup
      // 32b4: bipush 0
      // 32b5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 32b8: dup
      // 32b9: fconst_0
      // 32ba: fconst_0
      // 32bb: fconst_0
      // 32bc: fconst_0
      // 32bd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 32c0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 32c3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 32c6: aastore
      // 32c7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 32ca: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 32cd: ldc "left_arm2"
      // 32cf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 32d2: dup
      // 32d3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 32d6: bipush 7
      // 32d8: anewarray 28
      // 32db: dup
      // 32dc: bipush 0
      // 32dd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 32e0: dup
      // 32e1: fconst_0
      // 32e2: fconst_0
      // 32e3: fconst_0
      // 32e4: fconst_0
      // 32e5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32e8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 32eb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 32ee: aastore
      // 32ef: dup
      // 32f0: bipush 1
      // 32f1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 32f4: dup
      // 32f5: ldc 0.5
      // 32f7: fconst_0
      // 32f8: fconst_0
      // 32f9: ldc_w 27.5
      // 32fc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32ff: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3302: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3305: aastore
      // 3306: dup
      // 3307: bipush 2
      // 3308: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 330b: dup
      // 330c: ldc 0.75
      // 330e: fconst_0
      // 330f: fconst_0
      // 3310: ldc_w 32.5
      // 3313: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3316: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3319: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 331c: aastore
      // 331d: dup
      // 331e: bipush 3
      // 331f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3322: dup
      // 3323: ldc 0.875
      // 3325: fconst_0
      // 3326: fconst_0
      // 3327: fconst_0
      // 3328: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 332b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 332e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3331: aastore
      // 3332: dup
      // 3333: bipush 4
      // 3334: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3337: dup
      // 3338: ldc 1.25
      // 333a: fconst_0
      // 333b: fconst_0
      // 333c: ldc_w 10.0
      // 333f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3342: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3345: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3348: aastore
      // 3349: dup
      // 334a: bipush 5
      // 334b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 334e: dup
      // 334f: ldc 1.75
      // 3351: fconst_0
      // 3352: fconst_0
      // 3353: ldc_w 27.5
      // 3356: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3359: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 335c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 335f: aastore
      // 3360: dup
      // 3361: bipush 6
      // 3363: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3366: dup
      // 3367: fconst_2
      // 3368: ldc -17.3727
      // 336a: ldc -0.5463
      // 336c: ldc 1.003
      // 336e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3371: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3374: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3377: aastore
      // 3378: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 337b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 337e: ldc "right_leg"
      // 3380: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3383: dup
      // 3384: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3387: bipush 2
      // 3388: anewarray 28
      // 338b: dup
      // 338c: bipush 0
      // 338d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3390: dup
      // 3391: fconst_0
      // 3392: fconst_0
      // 3393: fconst_0
      // 3394: fconst_0
      // 3395: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3398: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 339b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 339e: aastore
      // 339f: dup
      // 33a0: bipush 1
      // 33a1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 33a4: dup
      // 33a5: fconst_2
      // 33a6: ldc_w -17.5
      // 33a9: fconst_0
      // 33aa: fconst_0
      // 33ab: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33ae: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 33b1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 33b4: aastore
      // 33b5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 33b8: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 33bb: ldc "right_leg"
      // 33bd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 33c0: dup
      // 33c1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 33c4: bipush 1
      // 33c5: anewarray 28
      // 33c8: dup
      // 33c9: bipush 0
      // 33ca: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 33cd: dup
      // 33ce: fconst_2
      // 33cf: fconst_0
      // 33d0: fconst_0
      // 33d1: ldc -6.0
      // 33d3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33d6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 33d9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 33dc: aastore
      // 33dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 33e0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 33e3: ldc "left_leg"
      // 33e5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 33e8: dup
      // 33e9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 33ec: bipush 3
      // 33ed: anewarray 28
      // 33f0: dup
      // 33f1: bipush 0
      // 33f2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 33f5: dup
      // 33f6: fconst_0
      // 33f7: fconst_0
      // 33f8: fconst_0
      // 33f9: fconst_0
      // 33fa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33fd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3400: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3403: aastore
      // 3404: dup
      // 3405: bipush 1
      // 3406: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3409: dup
      // 340a: fconst_2
      // 340b: ldc_w 22.5
      // 340e: fconst_0
      // 340f: fconst_0
      // 3410: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3413: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3416: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3419: aastore
      // 341a: dup
      // 341b: bipush 2
      // 341c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 341f: dup
      // 3420: ldc 3.0
      // 3422: fconst_0
      // 3423: fconst_0
      // 3424: fconst_0
      // 3425: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3428: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 342b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 342e: aastore
      // 342f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3432: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3435: ldc "left_leg"
      // 3437: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 343a: dup
      // 343b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 343e: bipush 1
      // 343f: anewarray 28
      // 3442: dup
      // 3443: bipush 0
      // 3444: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3447: dup
      // 3448: fconst_2
      // 3449: fconst_0
      // 344a: fconst_0
      // 344b: ldc 3.0
      // 344d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3450: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3453: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3456: aastore
      // 3457: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 345a: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 345d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 3460: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.ANGRY Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 3463: ldc 3.0
      // 3465: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3468: ldc "body"
      // 346a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 346d: dup
      // 346e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3471: bipush 18
      // 3473: anewarray 28
      // 3476: dup
      // 3477: bipush 0
      // 3478: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 347b: dup
      // 347c: fconst_0
      // 347d: ldc -27.5896
      // 347f: ldc -4.4339
      // 3481: ldc 2.3134
      // 3483: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3486: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3489: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 348c: aastore
      // 348d: dup
      // 348e: bipush 1
      // 348f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3492: dup
      // 3493: ldc_w 0.2083
      // 3496: ldc -28.1336
      // 3498: ldc 6.8547
      // 349a: ldc 7.6379
      // 349c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 349f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 34a2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 34a5: aastore
      // 34a6: dup
      // 34a7: bipush 2
      // 34a8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 34ab: dup
      // 34ac: ldc 0.25
      // 34ae: ldc -31.4254
      // 34b0: ldc -28.3238
      // 34b2: ldc -9.3577
      // 34b4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34b7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 34ba: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 34bd: aastore
      // 34be: dup
      // 34bf: bipush 3
      // 34c0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 34c3: dup
      // 34c4: ldc 0.5
      // 34c6: ldc -26.4254
      // 34c8: ldc -28.3238
      // 34ca: ldc -9.3577
      // 34cc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34cf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 34d2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 34d5: aastore
      // 34d6: dup
      // 34d7: bipush 4
      // 34d8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 34db: dup
      // 34dc: ldc 0.625
      // 34de: ldc -27.5896
      // 34e0: ldc 4.4339
      // 34e2: ldc -2.3134
      // 34e4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34e7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 34ea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 34ed: aastore
      // 34ee: dup
      // 34ef: bipush 5
      // 34f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 34f3: dup
      // 34f4: ldc 0.8333
      // 34f6: ldc -28.1336
      // 34f8: ldc -6.8547
      // 34fa: ldc -7.6379
      // 34fc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34ff: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3502: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3505: aastore
      // 3506: dup
      // 3507: bipush 6
      // 3509: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 350c: dup
      // 350d: ldc 0.875
      // 350f: ldc -31.4254
      // 3511: ldc 28.3238
      // 3513: ldc 9.3577
      // 3515: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3518: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 351b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 351e: aastore
      // 351f: dup
      // 3520: bipush 7
      // 3522: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3525: dup
      // 3526: ldc_w 1.125
      // 3529: ldc -26.4254
      // 352b: ldc 28.3238
      // 352d: ldc 9.3577
      // 352f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3532: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3535: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3538: aastore
      // 3539: dup
      // 353a: bipush 8
      // 353c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 353f: dup
      // 3540: ldc 1.25
      // 3542: ldc -27.5896
      // 3544: ldc -4.4339
      // 3546: ldc 2.3134
      // 3548: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 354b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 354e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3551: aastore
      // 3552: dup
      // 3553: bipush 9
      // 3555: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3558: dup
      // 3559: ldc_w 1.4583
      // 355c: ldc -28.1336
      // 355e: ldc 6.8547
      // 3560: ldc 7.6379
      // 3562: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3565: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3568: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 356b: aastore
      // 356c: dup
      // 356d: bipush 10
      // 356f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3572: dup
      // 3573: ldc 1.5
      // 3575: ldc -31.4254
      // 3577: ldc -28.3238
      // 3579: ldc -9.3577
      // 357b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 357e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3581: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3584: aastore
      // 3585: dup
      // 3586: bipush 11
      // 3588: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 358b: dup
      // 358c: ldc 1.75
      // 358e: ldc -26.4254
      // 3590: ldc -28.3238
      // 3592: ldc -9.3577
      // 3594: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3597: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 359a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 359d: aastore
      // 359e: dup
      // 359f: bipush 12
      // 35a1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 35a4: dup
      // 35a5: ldc_w 1.875
      // 35a8: ldc -27.5896
      // 35aa: ldc 4.4339
      // 35ac: ldc -2.3134
      // 35ae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 35b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 35b7: aastore
      // 35b8: dup
      // 35b9: bipush 13
      // 35bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 35be: dup
      // 35bf: ldc_w 2.0833
      // 35c2: ldc -28.1336
      // 35c4: ldc -6.8547
      // 35c6: ldc -7.6379
      // 35c8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35cb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 35ce: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 35d1: aastore
      // 35d2: dup
      // 35d3: bipush 14
      // 35d5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 35d8: dup
      // 35d9: ldc_w 2.125
      // 35dc: ldc -31.4254
      // 35de: ldc 28.3238
      // 35e0: ldc 9.3577
      // 35e2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35e5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 35e8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 35eb: aastore
      // 35ec: dup
      // 35ed: bipush 15
      // 35ef: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 35f2: dup
      // 35f3: ldc_w 2.375
      // 35f6: ldc -26.4254
      // 35f8: ldc 28.3238
      // 35fa: ldc 9.3577
      // 35fc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35ff: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3602: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3605: aastore
      // 3606: dup
      // 3607: bipush 16
      // 3609: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 360c: dup
      // 360d: ldc_w 2.5833
      // 3610: ldc -27.5896
      // 3612: ldc -4.4339
      // 3614: ldc 2.3134
      // 3616: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3619: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 361c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 361f: aastore
      // 3620: dup
      // 3621: bipush 17
      // 3623: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3626: dup
      // 3627: ldc 3.0
      // 3629: fconst_0
      // 362a: fconst_0
      // 362b: fconst_0
      // 362c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 362f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3632: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3635: aastore
      // 3636: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3639: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 363c: ldc "body"
      // 363e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3641: dup
      // 3642: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3645: bipush 8
      // 3647: anewarray 28
      // 364a: dup
      // 364b: bipush 0
      // 364c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 364f: dup
      // 3650: fconst_0
      // 3651: fconst_0
      // 3652: ldc -4.0
      // 3654: ldc -6.0
      // 3656: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3659: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 365c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 365f: aastore
      // 3660: dup
      // 3661: bipush 1
      // 3662: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3665: dup
      // 3666: ldc 0.25
      // 3668: fconst_0
      // 3669: ldc -4.0
      // 366b: ldc -6.0
      // 366d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3670: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3673: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3676: aastore
      // 3677: dup
      // 3678: bipush 2
      // 3679: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 367c: dup
      // 367d: ldc 0.875
      // 367f: fconst_0
      // 3680: ldc -4.0
      // 3682: ldc -6.0
      // 3684: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3687: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 368a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 368d: aastore
      // 368e: dup
      // 368f: bipush 3
      // 3690: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3693: dup
      // 3694: ldc 1.25
      // 3696: fconst_0
      // 3697: ldc -4.0
      // 3699: ldc -6.0
      // 369b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 369e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 36a1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 36a4: aastore
      // 36a5: dup
      // 36a6: bipush 4
      // 36a7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 36aa: dup
      // 36ab: ldc 1.5
      // 36ad: fconst_0
      // 36ae: ldc -4.0
      // 36b0: ldc -6.0
      // 36b2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36b5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 36b8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 36bb: aastore
      // 36bc: dup
      // 36bd: bipush 5
      // 36be: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 36c1: dup
      // 36c2: ldc_w 2.125
      // 36c5: fconst_0
      // 36c6: ldc -4.0
      // 36c8: ldc -6.0
      // 36ca: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36cd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 36d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 36d3: aastore
      // 36d4: dup
      // 36d5: bipush 6
      // 36d7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 36da: dup
      // 36db: ldc_w 2.5833
      // 36de: fconst_0
      // 36df: ldc -4.0
      // 36e1: ldc -6.0
      // 36e3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36e6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 36e9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 36ec: aastore
      // 36ed: dup
      // 36ee: bipush 7
      // 36f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 36f3: dup
      // 36f4: ldc 3.0
      // 36f6: fconst_0
      // 36f7: fconst_0
      // 36f8: fconst_0
      // 36f9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36fc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 36ff: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3702: aastore
      // 3703: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3706: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3709: ldc "chest"
      // 370b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 370e: dup
      // 370f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3712: bipush 18
      // 3714: anewarray 28
      // 3717: dup
      // 3718: bipush 0
      // 3719: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 371c: dup
      // 371d: fconst_0
      // 371e: ldc 2.4363
      // 3720: ldc 7.082
      // 3722: ldc 17.1819
      // 3724: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3727: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 372a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 372d: aastore
      // 372e: dup
      // 372f: bipush 1
      // 3730: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3733: dup
      // 3734: ldc_w 0.2083
      // 3737: ldc 3.4023
      // 3739: ldc 11.8649
      // 373b: ldc 23.0407
      // 373d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3740: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3743: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3746: aastore
      // 3747: dup
      // 3748: bipush 2
      // 3749: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 374c: dup
      // 374d: ldc 0.25
      // 374f: ldc 53.4023
      // 3751: ldc 11.8649
      // 3753: ldc 23.0407
      // 3755: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3758: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 375b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 375e: aastore
      // 375f: dup
      // 3760: bipush 3
      // 3761: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3764: dup
      // 3765: ldc 0.5
      // 3767: ldc 42.8764
      // 3769: ldc 11.8928
      // 376b: ldc 20.4861
      // 376d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3770: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3773: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3776: aastore
      // 3777: dup
      // 3778: bipush 4
      // 3779: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 377c: dup
      // 377d: ldc 0.625
      // 377f: ldc 2.4363
      // 3781: ldc -7.082
      // 3783: ldc -17.1819
      // 3785: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3788: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 378b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 378e: aastore
      // 378f: dup
      // 3790: bipush 5
      // 3791: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3794: dup
      // 3795: ldc 0.8333
      // 3797: ldc 3.4023
      // 3799: ldc -11.8649
      // 379b: ldc -23.0407
      // 379d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37a0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 37a3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 37a6: aastore
      // 37a7: dup
      // 37a8: bipush 6
      // 37aa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 37ad: dup
      // 37ae: ldc 0.875
      // 37b0: ldc 53.4023
      // 37b2: ldc -11.8649
      // 37b4: ldc -23.0407
      // 37b6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37b9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 37bc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 37bf: aastore
      // 37c0: dup
      // 37c1: bipush 7
      // 37c3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 37c6: dup
      // 37c7: ldc_w 1.125
      // 37ca: ldc 42.8764
      // 37cc: ldc -11.8928
      // 37ce: ldc -20.4861
      // 37d0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37d3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 37d6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 37d9: aastore
      // 37da: dup
      // 37db: bipush 8
      // 37dd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 37e0: dup
      // 37e1: ldc 1.25
      // 37e3: ldc 2.4363
      // 37e5: ldc 7.082
      // 37e7: ldc 17.1819
      // 37e9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37ec: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 37ef: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 37f2: aastore
      // 37f3: dup
      // 37f4: bipush 9
      // 37f6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 37f9: dup
      // 37fa: ldc_w 1.4583
      // 37fd: ldc 3.4023
      // 37ff: ldc 11.8649
      // 3801: ldc 23.0407
      // 3803: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3806: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3809: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 380c: aastore
      // 380d: dup
      // 380e: bipush 10
      // 3810: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3813: dup
      // 3814: ldc 1.5
      // 3816: ldc 53.4023
      // 3818: ldc 11.8649
      // 381a: ldc 23.0407
      // 381c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 381f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3822: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3825: aastore
      // 3826: dup
      // 3827: bipush 11
      // 3829: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 382c: dup
      // 382d: ldc 1.75
      // 382f: ldc 42.8764
      // 3831: ldc 11.8928
      // 3833: ldc 20.4861
      // 3835: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3838: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 383b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 383e: aastore
      // 383f: dup
      // 3840: bipush 12
      // 3842: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3845: dup
      // 3846: ldc_w 1.875
      // 3849: ldc 2.4363
      // 384b: ldc -7.082
      // 384d: ldc -17.1819
      // 384f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3852: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3855: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3858: aastore
      // 3859: dup
      // 385a: bipush 13
      // 385c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 385f: dup
      // 3860: ldc_w 2.0833
      // 3863: ldc 3.4023
      // 3865: ldc -11.8649
      // 3867: ldc -23.0407
      // 3869: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 386c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 386f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3872: aastore
      // 3873: dup
      // 3874: bipush 14
      // 3876: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3879: dup
      // 387a: ldc_w 2.125
      // 387d: ldc 53.4023
      // 387f: ldc -11.8649
      // 3881: ldc -23.0407
      // 3883: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3886: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3889: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 388c: aastore
      // 388d: dup
      // 388e: bipush 15
      // 3890: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3893: dup
      // 3894: ldc_w 2.375
      // 3897: ldc 42.8764
      // 3899: ldc -11.8928
      // 389b: ldc -20.4861
      // 389d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38a0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 38a3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 38a6: aastore
      // 38a7: dup
      // 38a8: bipush 16
      // 38aa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 38ad: dup
      // 38ae: ldc_w 2.5833
      // 38b1: ldc 2.4363
      // 38b3: ldc 7.082
      // 38b5: ldc 17.1819
      // 38b7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38ba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 38bd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 38c0: aastore
      // 38c1: dup
      // 38c2: bipush 17
      // 38c4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 38c7: dup
      // 38c8: ldc 3.0
      // 38ca: fconst_0
      // 38cb: fconst_0
      // 38cc: fconst_0
      // 38cd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38d0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 38d3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 38d6: aastore
      // 38d7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 38da: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 38dd: ldc "head"
      // 38df: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 38e2: dup
      // 38e3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 38e6: bipush 18
      // 38e8: anewarray 28
      // 38eb: dup
      // 38ec: bipush 0
      // 38ed: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 38f0: dup
      // 38f1: fconst_0
      // 38f2: ldc 34.987
      // 38f4: ldc -19.3289
      // 38f6: ldc -5.5392
      // 38f8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38fb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 38fe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3901: aastore
      // 3902: dup
      // 3903: bipush 1
      // 3904: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3907: dup
      // 3908: ldc_w 0.2083
      // 390b: ldc 34.2775
      // 390d: ldc -26.6412
      // 390f: ldc -3.7261
      // 3911: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3914: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3917: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 391a: aastore
      // 391b: dup
      // 391c: bipush 2
      // 391d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3920: dup
      // 3921: ldc 0.25
      // 3923: ldc -6.8062
      // 3925: ldc -2.2153
      // 3927: ldc -9.2775
      // 3929: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 392c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 392f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3932: aastore
      // 3933: dup
      // 3934: bipush 3
      // 3935: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3938: dup
      // 3939: ldc 0.5
      // 393b: ldc 5.6844
      // 393d: ldc 1.8465
      // 393f: ldc -6.3612
      // 3941: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3944: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3947: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 394a: aastore
      // 394b: dup
      // 394c: bipush 4
      // 394d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3950: dup
      // 3951: ldc 0.625
      // 3953: ldc 34.987
      // 3955: ldc 19.3289
      // 3957: ldc 5.5392
      // 3959: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 395c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 395f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3962: aastore
      // 3963: dup
      // 3964: bipush 5
      // 3965: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3968: dup
      // 3969: ldc 0.8333
      // 396b: ldc 34.2775
      // 396d: ldc_w 26.6412
      // 3970: ldc_w 3.7261
      // 3973: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3976: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3979: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 397c: aastore
      // 397d: dup
      // 397e: bipush 6
      // 3980: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3983: dup
      // 3984: ldc 0.875
      // 3986: ldc -6.8062
      // 3988: ldc_w 2.2153
      // 398b: ldc_w 9.2775
      // 398e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3991: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3994: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3997: aastore
      // 3998: dup
      // 3999: bipush 7
      // 399b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 399e: dup
      // 399f: ldc_w 1.125
      // 39a2: ldc 5.6844
      // 39a4: ldc_w -1.8465
      // 39a7: ldc_w 6.3612
      // 39aa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39ad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 39b0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 39b3: aastore
      // 39b4: dup
      // 39b5: bipush 8
      // 39b7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 39ba: dup
      // 39bb: ldc 1.25
      // 39bd: ldc 34.987
      // 39bf: ldc -19.3289
      // 39c1: ldc -5.5392
      // 39c3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39c6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 39c9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 39cc: aastore
      // 39cd: dup
      // 39ce: bipush 9
      // 39d0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 39d3: dup
      // 39d4: ldc_w 1.4583
      // 39d7: ldc 34.2775
      // 39d9: ldc -26.6412
      // 39db: ldc -3.7261
      // 39dd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39e0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 39e3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 39e6: aastore
      // 39e7: dup
      // 39e8: bipush 10
      // 39ea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 39ed: dup
      // 39ee: ldc 1.5
      // 39f0: ldc -6.8062
      // 39f2: ldc -2.2153
      // 39f4: ldc -9.2775
      // 39f6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39f9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 39fc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 39ff: aastore
      // 3a00: dup
      // 3a01: bipush 11
      // 3a03: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a06: dup
      // 3a07: ldc 1.75
      // 3a09: ldc 5.6844
      // 3a0b: ldc 1.8465
      // 3a0d: ldc -6.3612
      // 3a0f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a12: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a15: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3a18: aastore
      // 3a19: dup
      // 3a1a: bipush 12
      // 3a1c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a1f: dup
      // 3a20: ldc_w 1.875
      // 3a23: ldc 34.987
      // 3a25: ldc 19.3289
      // 3a27: ldc 5.5392
      // 3a29: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a2c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a2f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3a32: aastore
      // 3a33: dup
      // 3a34: bipush 13
      // 3a36: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a39: dup
      // 3a3a: ldc_w 2.0833
      // 3a3d: ldc 34.2775
      // 3a3f: ldc_w 26.6412
      // 3a42: ldc_w 3.7261
      // 3a45: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a48: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a4b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3a4e: aastore
      // 3a4f: dup
      // 3a50: bipush 14
      // 3a52: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a55: dup
      // 3a56: ldc_w 2.125
      // 3a59: ldc -6.8062
      // 3a5b: ldc_w 2.2153
      // 3a5e: ldc_w 9.2775
      // 3a61: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a64: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a67: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3a6a: aastore
      // 3a6b: dup
      // 3a6c: bipush 15
      // 3a6e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a71: dup
      // 3a72: ldc_w 2.375
      // 3a75: ldc 5.6844
      // 3a77: ldc_w -1.8465
      // 3a7a: ldc_w 6.3612
      // 3a7d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a80: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a83: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3a86: aastore
      // 3a87: dup
      // 3a88: bipush 16
      // 3a8a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3a8d: dup
      // 3a8e: ldc_w 2.5833
      // 3a91: ldc 34.987
      // 3a93: ldc -19.3289
      // 3a95: ldc -5.5392
      // 3a97: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a9a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3a9d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3aa0: aastore
      // 3aa1: dup
      // 3aa2: bipush 17
      // 3aa4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3aa7: dup
      // 3aa8: ldc 3.0
      // 3aaa: fconst_0
      // 3aab: fconst_0
      // 3aac: fconst_0
      // 3aad: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ab0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ab3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3ab6: aastore
      // 3ab7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3aba: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3abd: ldc "eye"
      // 3abf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3ac2: dup
      // 3ac3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3ac6: bipush 14
      // 3ac8: anewarray 28
      // 3acb: dup
      // 3acc: bipush 0
      // 3acd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3ad0: dup
      // 3ad1: ldc_w 0.2083
      // 3ad4: fconst_0
      // 3ad5: ldc -0.6
      // 3ad7: fconst_0
      // 3ad8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3adb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ade: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3ae1: aastore
      // 3ae2: dup
      // 3ae3: bipush 1
      // 3ae4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3ae7: dup
      // 3ae8: ldc_w 0.2917
      // 3aeb: fconst_0
      // 3aec: fconst_1
      // 3aed: fconst_0
      // 3aee: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3af1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3af4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3af7: aastore
      // 3af8: dup
      // 3af9: bipush 2
      // 3afa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3afd: dup
      // 3afe: ldc 0.5
      // 3b00: fconst_0
      // 3b01: ldc -0.5
      // 3b03: fconst_0
      // 3b04: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b07: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b0a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b0d: aastore
      // 3b0e: dup
      // 3b0f: bipush 3
      // 3b10: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b13: dup
      // 3b14: ldc 0.8333
      // 3b16: fconst_0
      // 3b17: ldc -0.6
      // 3b19: fconst_0
      // 3b1a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b1d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b20: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b23: aastore
      // 3b24: dup
      // 3b25: bipush 4
      // 3b26: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b29: dup
      // 3b2a: ldc_w 0.9167
      // 3b2d: fconst_0
      // 3b2e: fconst_1
      // 3b2f: fconst_0
      // 3b30: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b33: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b36: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b39: aastore
      // 3b3a: dup
      // 3b3b: bipush 5
      // 3b3c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b3f: dup
      // 3b40: ldc_w 1.125
      // 3b43: fconst_0
      // 3b44: ldc -0.5
      // 3b46: fconst_0
      // 3b47: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b4a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b4d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b50: aastore
      // 3b51: dup
      // 3b52: bipush 6
      // 3b54: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b57: dup
      // 3b58: ldc 1.25
      // 3b5a: fconst_0
      // 3b5b: fconst_0
      // 3b5c: fconst_0
      // 3b5d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b60: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b63: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b66: aastore
      // 3b67: dup
      // 3b68: bipush 7
      // 3b6a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b6d: dup
      // 3b6e: ldc_w 1.4583
      // 3b71: fconst_0
      // 3b72: ldc -0.6
      // 3b74: fconst_0
      // 3b75: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b78: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b7b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b7e: aastore
      // 3b7f: dup
      // 3b80: bipush 8
      // 3b82: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b85: dup
      // 3b86: ldc 1.5417
      // 3b88: fconst_0
      // 3b89: fconst_1
      // 3b8a: fconst_0
      // 3b8b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b8e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3b91: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3b94: aastore
      // 3b95: dup
      // 3b96: bipush 9
      // 3b98: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3b9b: dup
      // 3b9c: ldc 1.75
      // 3b9e: fconst_0
      // 3b9f: ldc -0.5
      // 3ba1: fconst_0
      // 3ba2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ba5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ba8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3bab: aastore
      // 3bac: dup
      // 3bad: bipush 10
      // 3baf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3bb2: dup
      // 3bb3: ldc_w 2.0833
      // 3bb6: fconst_0
      // 3bb7: ldc -0.6
      // 3bb9: fconst_0
      // 3bba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bbd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3bc0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3bc3: aastore
      // 3bc4: dup
      // 3bc5: bipush 11
      // 3bc7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3bca: dup
      // 3bcb: ldc_w 2.1667
      // 3bce: fconst_0
      // 3bcf: fconst_1
      // 3bd0: fconst_0
      // 3bd1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bd4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3bd7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3bda: aastore
      // 3bdb: dup
      // 3bdc: bipush 12
      // 3bde: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3be1: dup
      // 3be2: ldc_w 2.375
      // 3be5: fconst_0
      // 3be6: ldc -0.5
      // 3be8: fconst_0
      // 3be9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bec: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3bef: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3bf2: aastore
      // 3bf3: dup
      // 3bf4: bipush 13
      // 3bf6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3bf9: dup
      // 3bfa: ldc 3.0
      // 3bfc: fconst_0
      // 3bfd: fconst_0
      // 3bfe: fconst_0
      // 3bff: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c02: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c05: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c08: aastore
      // 3c09: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3c0c: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3c0f: ldc "right_arm"
      // 3c11: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3c14: dup
      // 3c15: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3c18: bipush 18
      // 3c1a: anewarray 28
      // 3c1d: dup
      // 3c1e: bipush 0
      // 3c1f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c22: dup
      // 3c23: fconst_0
      // 3c24: ldc -0.1981
      // 3c26: ldc 7.6388
      // 3c28: ldc 53.0657
      // 3c2a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c2d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c30: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c33: aastore
      // 3c34: dup
      // 3c35: bipush 1
      // 3c36: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c39: dup
      // 3c3a: ldc_w 0.2083
      // 3c3d: ldc 2.1209
      // 3c3f: ldc 7.3428
      // 3c41: ldc 70.715
      // 3c43: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c46: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c49: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c4c: aastore
      // 3c4d: dup
      // 3c4e: bipush 2
      // 3c4f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c52: dup
      // 3c53: ldc 0.25
      // 3c55: ldc -51.1635
      // 3c57: ldc -21.4568
      // 3c59: ldc -33.9585
      // 3c5b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c5e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c61: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c64: aastore
      // 3c65: dup
      // 3c66: bipush 3
      // 3c67: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c6a: dup
      // 3c6b: ldc 0.5
      // 3c6d: ldc -47.2865
      // 3c6f: ldc -25.3148
      // 3c71: ldc -37.4248
      // 3c73: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c76: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c79: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c7c: aastore
      // 3c7d: dup
      // 3c7e: bipush 4
      // 3c7f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c82: dup
      // 3c83: ldc 0.625
      // 3c85: ldc -2.5
      // 3c87: fconst_0
      // 3c88: ldc_w 10.0
      // 3c8b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c8e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3c91: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3c94: aastore
      // 3c95: dup
      // 3c96: bipush 5
      // 3c97: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3c9a: dup
      // 3c9b: ldc 0.8333
      // 3c9d: ldc -9.5459
      // 3c9f: ldc_w -2.9932
      // 3ca2: ldc_w -7.25
      // 3ca5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ca8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3cab: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3cae: aastore
      // 3caf: dup
      // 3cb0: bipush 6
      // 3cb2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3cb5: dup
      // 3cb6: ldc 0.875
      // 3cb8: ldc 18.092
      // 3cba: ldc_w 3.4049
      // 3cbd: ldc_w 29.7197
      // 3cc0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cc3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3cc6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3cc9: aastore
      // 3cca: dup
      // 3ccb: bipush 7
      // 3ccd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3cd0: dup
      // 3cd1: ldc_w 1.125
      // 3cd4: ldc 27.7445
      // 3cd6: ldc_w 4.9441
      // 3cd9: ldc_w 24.9497
      // 3cdc: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cdf: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ce2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3ce5: aastore
      // 3ce6: dup
      // 3ce7: bipush 8
      // 3ce9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3cec: dup
      // 3ced: ldc 1.25
      // 3cef: ldc -0.1981
      // 3cf1: ldc 7.6388
      // 3cf3: ldc 53.0657
      // 3cf5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cf8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3cfb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3cfe: aastore
      // 3cff: dup
      // 3d00: bipush 9
      // 3d02: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d05: dup
      // 3d06: ldc_w 1.4583
      // 3d09: ldc 2.1209
      // 3d0b: ldc 7.3428
      // 3d0d: ldc 70.715
      // 3d0f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d12: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d15: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d18: aastore
      // 3d19: dup
      // 3d1a: bipush 10
      // 3d1c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d1f: dup
      // 3d20: ldc 1.5
      // 3d22: ldc -51.1635
      // 3d24: ldc -21.4568
      // 3d26: ldc -33.9585
      // 3d28: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d2b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d2e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d31: aastore
      // 3d32: dup
      // 3d33: bipush 11
      // 3d35: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d38: dup
      // 3d39: ldc 1.75
      // 3d3b: ldc -47.2865
      // 3d3d: ldc -25.3148
      // 3d3f: ldc -37.4248
      // 3d41: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d44: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d47: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d4a: aastore
      // 3d4b: dup
      // 3d4c: bipush 12
      // 3d4e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d51: dup
      // 3d52: ldc_w 1.875
      // 3d55: ldc -2.5
      // 3d57: fconst_0
      // 3d58: ldc_w 10.0
      // 3d5b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d5e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d61: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d64: aastore
      // 3d65: dup
      // 3d66: bipush 13
      // 3d68: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d6b: dup
      // 3d6c: ldc_w 2.0833
      // 3d6f: ldc -9.5459
      // 3d71: ldc_w -2.9932
      // 3d74: ldc_w -7.25
      // 3d77: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d7a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d7d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d80: aastore
      // 3d81: dup
      // 3d82: bipush 14
      // 3d84: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3d87: dup
      // 3d88: ldc_w 2.125
      // 3d8b: ldc 18.092
      // 3d8d: ldc_w 3.4049
      // 3d90: ldc_w 29.7197
      // 3d93: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d96: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3d99: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3d9c: aastore
      // 3d9d: dup
      // 3d9e: bipush 15
      // 3da0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3da3: dup
      // 3da4: ldc_w 2.375
      // 3da7: ldc 27.7445
      // 3da9: ldc_w 4.9441
      // 3dac: ldc_w 24.9497
      // 3daf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3db2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3db5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3db8: aastore
      // 3db9: dup
      // 3dba: bipush 16
      // 3dbc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3dbf: dup
      // 3dc0: ldc_w 2.5833
      // 3dc3: ldc -0.1981
      // 3dc5: ldc 7.6388
      // 3dc7: ldc 53.0657
      // 3dc9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dcc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3dcf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3dd2: aastore
      // 3dd3: dup
      // 3dd4: bipush 17
      // 3dd6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3dd9: dup
      // 3dda: ldc 3.0
      // 3ddc: fconst_0
      // 3ddd: fconst_0
      // 3dde: fconst_0
      // 3ddf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3de2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3de5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3de8: aastore
      // 3de9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3dec: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3def: ldc "right_arm"
      // 3df1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3df4: dup
      // 3df5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3df8: bipush 14
      // 3dfa: anewarray 28
      // 3dfd: dup
      // 3dfe: bipush 0
      // 3dff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e02: dup
      // 3e03: fconst_0
      // 3e04: fconst_0
      // 3e05: ldc 1.75
      // 3e07: ldc -1.88
      // 3e09: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e0c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e0f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e12: aastore
      // 3e13: dup
      // 3e14: bipush 1
      // 3e15: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e18: dup
      // 3e19: ldc_w 0.2083
      // 3e1c: fconst_0
      // 3e1d: ldc 3.99
      // 3e1f: ldc -4.15
      // 3e21: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e24: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e27: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e2a: aastore
      // 3e2b: dup
      // 3e2c: bipush 2
      // 3e2d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e30: dup
      // 3e31: ldc 0.25
      // 3e33: fconst_0
      // 3e34: fconst_2
      // 3e35: ldc -5.0
      // 3e37: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e3a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e3d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e40: aastore
      // 3e41: dup
      // 3e42: bipush 3
      // 3e43: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e46: dup
      // 3e47: ldc 0.625
      // 3e49: fconst_0
      // 3e4a: ldc 1.75
      // 3e4c: ldc -1.88
      // 3e4e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e51: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e54: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e57: aastore
      // 3e58: dup
      // 3e59: bipush 4
      // 3e5a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e5d: dup
      // 3e5e: ldc 0.8333
      // 3e60: fconst_0
      // 3e61: fconst_0
      // 3e62: fconst_0
      // 3e63: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e66: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e69: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e6c: aastore
      // 3e6d: dup
      // 3e6e: bipush 5
      // 3e6f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e72: dup
      // 3e73: ldc 0.875
      // 3e75: fconst_0
      // 3e76: ldc 3.0
      // 3e78: fconst_0
      // 3e79: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e7c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e7f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e82: aastore
      // 3e83: dup
      // 3e84: bipush 6
      // 3e86: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3e89: dup
      // 3e8a: ldc 1.25
      // 3e8c: fconst_0
      // 3e8d: ldc 1.75
      // 3e8f: ldc -1.88
      // 3e91: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e94: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3e97: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3e9a: aastore
      // 3e9b: dup
      // 3e9c: bipush 7
      // 3e9e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3ea1: dup
      // 3ea2: ldc_w 1.4583
      // 3ea5: fconst_0
      // 3ea6: ldc 3.99
      // 3ea8: ldc -4.15
      // 3eaa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ead: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3eb0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3eb3: aastore
      // 3eb4: dup
      // 3eb5: bipush 8
      // 3eb7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3eba: dup
      // 3ebb: ldc 1.5
      // 3ebd: fconst_0
      // 3ebe: fconst_2
      // 3ebf: ldc -5.0
      // 3ec1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ec4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ec7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3eca: aastore
      // 3ecb: dup
      // 3ecc: bipush 9
      // 3ece: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3ed1: dup
      // 3ed2: ldc_w 1.875
      // 3ed5: fconst_0
      // 3ed6: ldc 1.75
      // 3ed8: ldc -1.88
      // 3eda: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3edd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ee0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3ee3: aastore
      // 3ee4: dup
      // 3ee5: bipush 10
      // 3ee7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3eea: dup
      // 3eeb: ldc_w 2.0833
      // 3eee: fconst_0
      // 3eef: fconst_0
      // 3ef0: fconst_0
      // 3ef1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ef4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ef7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3efa: aastore
      // 3efb: dup
      // 3efc: bipush 11
      // 3efe: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f01: dup
      // 3f02: ldc_w 2.125
      // 3f05: fconst_0
      // 3f06: ldc 3.0
      // 3f08: fconst_0
      // 3f09: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f0c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f0f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f12: aastore
      // 3f13: dup
      // 3f14: bipush 12
      // 3f16: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f19: dup
      // 3f1a: ldc_w 2.5833
      // 3f1d: fconst_0
      // 3f1e: ldc 1.75
      // 3f20: ldc -1.88
      // 3f22: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f25: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f28: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f2b: aastore
      // 3f2c: dup
      // 3f2d: bipush 13
      // 3f2f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f32: dup
      // 3f33: ldc 3.0
      // 3f35: fconst_0
      // 3f36: fconst_0
      // 3f37: fconst_0
      // 3f38: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f3b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f3e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f41: aastore
      // 3f42: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 3f45: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 3f48: ldc "right_arm2"
      // 3f4a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 3f4d: dup
      // 3f4e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 3f51: bipush 18
      // 3f53: anewarray 28
      // 3f56: dup
      // 3f57: bipush 0
      // 3f58: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f5b: dup
      // 3f5c: fconst_0
      // 3f5d: ldc -35.0
      // 3f5f: fconst_0
      // 3f60: fconst_0
      // 3f61: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f64: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f67: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f6a: aastore
      // 3f6b: dup
      // 3f6c: bipush 1
      // 3f6d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f70: dup
      // 3f71: ldc_w 0.2083
      // 3f74: ldc -33.735
      // 3f76: ldc -9.9319
      // 3f78: ldc -14.4817
      // 3f7a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f7d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f80: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f83: aastore
      // 3f84: dup
      // 3f85: bipush 2
      // 3f86: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f89: dup
      // 3f8a: ldc 0.25
      // 3f8c: fconst_0
      // 3f8d: fconst_0
      // 3f8e: fconst_0
      // 3f8f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f92: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3f95: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3f98: aastore
      // 3f99: dup
      // 3f9a: bipush 3
      // 3f9b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3f9e: dup
      // 3f9f: ldc 0.5
      // 3fa1: ldc -2.5
      // 3fa3: fconst_0
      // 3fa4: fconst_0
      // 3fa5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fa8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3fab: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3fae: aastore
      // 3faf: dup
      // 3fb0: bipush 4
      // 3fb1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3fb4: dup
      // 3fb5: ldc 0.625
      // 3fb7: ldc -17.3727
      // 3fb9: ldc_w 0.5463
      // 3fbc: ldc_w -1.003
      // 3fbf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fc2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3fc5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3fc8: aastore
      // 3fc9: dup
      // 3fca: bipush 5
      // 3fcb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3fce: dup
      // 3fcf: ldc 0.8333
      // 3fd1: ldc -47.3727
      // 3fd3: ldc_w 0.5463
      // 3fd6: ldc_w -1.003
      // 3fd9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fdc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3fdf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3fe2: aastore
      // 3fe3: dup
      // 3fe4: bipush 6
      // 3fe6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 3fe9: dup
      // 3fea: ldc 0.875
      // 3fec: ldc -23.2281
      // 3fee: ldc_w -14.0447
      // 3ff1: ldc_w -14.8166
      // 3ff4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ff7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 3ffa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 3ffd: aastore
      // 3ffe: dup
      // 3fff: bipush 7
      // 4001: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4004: dup
      // 4005: ldc_w 1.125
      // 4008: ldc -15.7281
      // 400a: ldc_w -14.0447
      // 400d: ldc_w -14.8166
      // 4010: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4013: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4016: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4019: aastore
      // 401a: dup
      // 401b: bipush 8
      // 401d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4020: dup
      // 4021: ldc 1.25
      // 4023: ldc -35.0
      // 4025: fconst_0
      // 4026: fconst_0
      // 4027: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 402a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 402d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4030: aastore
      // 4031: dup
      // 4032: bipush 9
      // 4034: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4037: dup
      // 4038: ldc_w 1.4583
      // 403b: ldc -33.735
      // 403d: ldc -9.9319
      // 403f: ldc -14.4817
      // 4041: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4044: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4047: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 404a: aastore
      // 404b: dup
      // 404c: bipush 10
      // 404e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4051: dup
      // 4052: ldc 1.5
      // 4054: fconst_0
      // 4055: fconst_0
      // 4056: fconst_0
      // 4057: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 405a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 405d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4060: aastore
      // 4061: dup
      // 4062: bipush 11
      // 4064: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4067: dup
      // 4068: ldc 1.75
      // 406a: ldc -2.5
      // 406c: fconst_0
      // 406d: fconst_0
      // 406e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4071: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4074: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4077: aastore
      // 4078: dup
      // 4079: bipush 12
      // 407b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 407e: dup
      // 407f: ldc_w 1.875
      // 4082: ldc -17.3727
      // 4084: ldc_w 0.5463
      // 4087: ldc_w -1.003
      // 408a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 408d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4090: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4093: aastore
      // 4094: dup
      // 4095: bipush 13
      // 4097: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 409a: dup
      // 409b: ldc_w 2.0833
      // 409e: ldc -47.3727
      // 40a0: ldc_w 0.5463
      // 40a3: ldc_w -1.003
      // 40a6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40a9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 40ac: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 40af: aastore
      // 40b0: dup
      // 40b1: bipush 14
      // 40b3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 40b6: dup
      // 40b7: ldc_w 2.125
      // 40ba: ldc -23.2281
      // 40bc: ldc_w -14.0447
      // 40bf: ldc_w -14.8166
      // 40c2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40c5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 40c8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 40cb: aastore
      // 40cc: dup
      // 40cd: bipush 15
      // 40cf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 40d2: dup
      // 40d3: ldc_w 2.375
      // 40d6: ldc -15.7281
      // 40d8: ldc_w -14.0447
      // 40db: ldc_w -14.8166
      // 40de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 40e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 40e7: aastore
      // 40e8: dup
      // 40e9: bipush 16
      // 40eb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 40ee: dup
      // 40ef: ldc_w 2.5833
      // 40f2: ldc -35.0
      // 40f4: fconst_0
      // 40f5: fconst_0
      // 40f6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40f9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 40fc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 40ff: aastore
      // 4100: dup
      // 4101: bipush 17
      // 4103: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4106: dup
      // 4107: ldc 3.0
      // 4109: fconst_0
      // 410a: fconst_0
      // 410b: fconst_0
      // 410c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 410f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4112: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4115: aastore
      // 4116: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4119: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 411c: ldc "left_arm"
      // 411e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4121: dup
      // 4122: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4125: bipush 18
      // 4127: anewarray 28
      // 412a: dup
      // 412b: bipush 0
      // 412c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 412f: dup
      // 4130: fconst_0
      // 4131: ldc -2.5
      // 4133: fconst_0
      // 4134: ldc -10.0
      // 4136: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4139: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 413c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 413f: aastore
      // 4140: dup
      // 4141: bipush 1
      // 4142: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4145: dup
      // 4146: ldc_w 0.2083
      // 4149: ldc -9.5459
      // 414b: ldc 2.9932
      // 414d: ldc 7.25
      // 414f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4152: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4155: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4158: aastore
      // 4159: dup
      // 415a: bipush 2
      // 415b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 415e: dup
      // 415f: ldc 0.25
      // 4161: ldc 18.092
      // 4163: ldc -3.4049
      // 4165: ldc -29.7197
      // 4167: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 416a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 416d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4170: aastore
      // 4171: dup
      // 4172: bipush 3
      // 4173: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4176: dup
      // 4177: ldc 0.5
      // 4179: ldc 27.7445
      // 417b: ldc -4.9441
      // 417d: ldc -24.9497
      // 417f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4182: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4185: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4188: aastore
      // 4189: dup
      // 418a: bipush 4
      // 418b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 418e: dup
      // 418f: ldc 0.625
      // 4191: ldc -0.1981
      // 4193: ldc_w -7.6388
      // 4196: ldc_w -53.0657
      // 4199: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 419c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 419f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 41a2: aastore
      // 41a3: dup
      // 41a4: bipush 5
      // 41a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 41a8: dup
      // 41a9: ldc 0.8333
      // 41ab: ldc 2.1209
      // 41ad: ldc_w -7.3428
      // 41b0: ldc_w -70.715
      // 41b3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41b6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 41b9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 41bc: aastore
      // 41bd: dup
      // 41be: bipush 6
      // 41c0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 41c3: dup
      // 41c4: ldc 0.875
      // 41c6: ldc -51.1635
      // 41c8: ldc_w 21.4568
      // 41cb: ldc_w 33.9585
      // 41ce: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41d1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 41d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 41d7: aastore
      // 41d8: dup
      // 41d9: bipush 7
      // 41db: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 41de: dup
      // 41df: ldc_w 1.125
      // 41e2: ldc -47.2865
      // 41e4: ldc_w 25.3148
      // 41e7: ldc_w 37.4248
      // 41ea: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41ed: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 41f0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 41f3: aastore
      // 41f4: dup
      // 41f5: bipush 8
      // 41f7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 41fa: dup
      // 41fb: ldc 1.25
      // 41fd: ldc -2.5
      // 41ff: fconst_0
      // 4200: ldc -10.0
      // 4202: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4205: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4208: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 420b: aastore
      // 420c: dup
      // 420d: bipush 9
      // 420f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4212: dup
      // 4213: ldc_w 1.4583
      // 4216: ldc -9.5459
      // 4218: ldc 2.9932
      // 421a: ldc 7.25
      // 421c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 421f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4222: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4225: aastore
      // 4226: dup
      // 4227: bipush 10
      // 4229: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 422c: dup
      // 422d: ldc 1.5
      // 422f: ldc 18.092
      // 4231: ldc -3.4049
      // 4233: ldc -29.7197
      // 4235: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4238: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 423b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 423e: aastore
      // 423f: dup
      // 4240: bipush 11
      // 4242: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4245: dup
      // 4246: ldc 1.75
      // 4248: ldc 27.7445
      // 424a: ldc -4.9441
      // 424c: ldc -24.9497
      // 424e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4251: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4254: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4257: aastore
      // 4258: dup
      // 4259: bipush 12
      // 425b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 425e: dup
      // 425f: ldc_w 1.875
      // 4262: ldc -0.1981
      // 4264: ldc_w -7.6388
      // 4267: ldc_w -53.0657
      // 426a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 426d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4270: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4273: aastore
      // 4274: dup
      // 4275: bipush 13
      // 4277: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 427a: dup
      // 427b: ldc_w 2.0833
      // 427e: ldc 2.1209
      // 4280: ldc_w -7.3428
      // 4283: ldc_w -70.715
      // 4286: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4289: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 428c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 428f: aastore
      // 4290: dup
      // 4291: bipush 14
      // 4293: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4296: dup
      // 4297: ldc_w 2.125
      // 429a: ldc -51.1635
      // 429c: ldc_w 21.4568
      // 429f: ldc_w 33.9585
      // 42a2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42a5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 42a8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 42ab: aastore
      // 42ac: dup
      // 42ad: bipush 15
      // 42af: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 42b2: dup
      // 42b3: ldc_w 2.375
      // 42b6: ldc -47.2865
      // 42b8: ldc_w 25.3148
      // 42bb: ldc_w 37.4248
      // 42be: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42c1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 42c4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 42c7: aastore
      // 42c8: dup
      // 42c9: bipush 16
      // 42cb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 42ce: dup
      // 42cf: ldc_w 2.5833
      // 42d2: ldc -2.5
      // 42d4: fconst_0
      // 42d5: ldc -10.0
      // 42d7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42da: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 42dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 42e0: aastore
      // 42e1: dup
      // 42e2: bipush 17
      // 42e4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 42e7: dup
      // 42e8: ldc 3.0
      // 42ea: fconst_0
      // 42eb: fconst_0
      // 42ec: fconst_0
      // 42ed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 42f3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 42f6: aastore
      // 42f7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 42fa: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 42fd: ldc "left_arm"
      // 42ff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4302: dup
      // 4303: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4306: bipush 12
      // 4308: anewarray 28
      // 430b: dup
      // 430c: bipush 0
      // 430d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4310: dup
      // 4311: ldc_w 0.2083
      // 4314: fconst_0
      // 4315: fconst_0
      // 4316: fconst_0
      // 4317: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 431a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 431d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4320: aastore
      // 4321: dup
      // 4322: bipush 1
      // 4323: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4326: dup
      // 4327: ldc 0.25
      // 4329: fconst_0
      // 432a: ldc 3.0
      // 432c: fconst_0
      // 432d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4330: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4333: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4336: aastore
      // 4337: dup
      // 4338: bipush 2
      // 4339: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 433c: dup
      // 433d: ldc 0.625
      // 433f: fconst_0
      // 4340: ldc 1.75
      // 4342: ldc -1.88
      // 4344: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4347: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 434a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 434d: aastore
      // 434e: dup
      // 434f: bipush 3
      // 4350: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4353: dup
      // 4354: ldc 0.8333
      // 4356: fconst_0
      // 4357: ldc 3.99
      // 4359: ldc -4.15
      // 435b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 435e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4361: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4364: aastore
      // 4365: dup
      // 4366: bipush 4
      // 4367: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 436a: dup
      // 436b: ldc 0.875
      // 436d: fconst_0
      // 436e: fconst_2
      // 436f: ldc -5.0
      // 4371: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4374: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4377: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 437a: aastore
      // 437b: dup
      // 437c: bipush 5
      // 437d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4380: dup
      // 4381: ldc 1.25
      // 4383: fconst_0
      // 4384: fconst_0
      // 4385: fconst_0
      // 4386: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4389: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 438c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 438f: aastore
      // 4390: dup
      // 4391: bipush 6
      // 4393: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4396: dup
      // 4397: ldc_w 1.4583
      // 439a: fconst_0
      // 439b: fconst_0
      // 439c: fconst_0
      // 439d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43a0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 43a3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 43a6: aastore
      // 43a7: dup
      // 43a8: bipush 7
      // 43aa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 43ad: dup
      // 43ae: ldc 1.5
      // 43b0: fconst_0
      // 43b1: ldc 3.0
      // 43b3: fconst_0
      // 43b4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43b7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 43ba: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 43bd: aastore
      // 43be: dup
      // 43bf: bipush 8
      // 43c1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 43c4: dup
      // 43c5: ldc_w 1.875
      // 43c8: fconst_0
      // 43c9: ldc 1.75
      // 43cb: ldc -1.88
      // 43cd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43d0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 43d3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 43d6: aastore
      // 43d7: dup
      // 43d8: bipush 9
      // 43da: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 43dd: dup
      // 43de: ldc_w 2.0833
      // 43e1: fconst_0
      // 43e2: ldc 3.99
      // 43e4: ldc -4.15
      // 43e6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43e9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 43ec: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 43ef: aastore
      // 43f0: dup
      // 43f1: bipush 10
      // 43f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 43f6: dup
      // 43f7: ldc_w 2.125
      // 43fa: fconst_0
      // 43fb: fconst_2
      // 43fc: ldc -5.0
      // 43fe: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4401: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4404: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4407: aastore
      // 4408: dup
      // 4409: bipush 11
      // 440b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 440e: dup
      // 440f: ldc 3.0
      // 4411: fconst_0
      // 4412: fconst_0
      // 4413: fconst_0
      // 4414: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4417: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 441a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 441d: aastore
      // 441e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4421: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4424: ldc "left_arm2"
      // 4426: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4429: dup
      // 442a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 442d: bipush 18
      // 442f: anewarray 28
      // 4432: dup
      // 4433: bipush 0
      // 4434: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4437: dup
      // 4438: fconst_0
      // 4439: ldc -17.3727
      // 443b: ldc -0.5463
      // 443d: ldc 1.003
      // 443f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4442: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4445: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4448: aastore
      // 4449: dup
      // 444a: bipush 1
      // 444b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 444e: dup
      // 444f: ldc_w 0.2083
      // 4452: ldc -47.3727
      // 4454: ldc -0.5463
      // 4456: ldc 1.003
      // 4458: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 445b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 445e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4461: aastore
      // 4462: dup
      // 4463: bipush 2
      // 4464: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4467: dup
      // 4468: ldc 0.25
      // 446a: ldc -23.2281
      // 446c: ldc 14.0447
      // 446e: ldc 14.8166
      // 4470: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4473: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4476: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4479: aastore
      // 447a: dup
      // 447b: bipush 3
      // 447c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 447f: dup
      // 4480: ldc 0.5
      // 4482: ldc -15.7281
      // 4484: ldc 14.0447
      // 4486: ldc 14.8166
      // 4488: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 448b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 448e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4491: aastore
      // 4492: dup
      // 4493: bipush 4
      // 4494: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4497: dup
      // 4498: ldc 0.625
      // 449a: ldc -35.0
      // 449c: fconst_0
      // 449d: fconst_0
      // 449e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44a1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 44a4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 44a7: aastore
      // 44a8: dup
      // 44a9: bipush 5
      // 44aa: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 44ad: dup
      // 44ae: ldc 0.8333
      // 44b0: ldc -33.735
      // 44b2: ldc_w 9.9319
      // 44b5: ldc_w 14.4817
      // 44b8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44bb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 44be: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 44c1: aastore
      // 44c2: dup
      // 44c3: bipush 6
      // 44c5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 44c8: dup
      // 44c9: ldc 0.875
      // 44cb: fconst_0
      // 44cc: fconst_0
      // 44cd: fconst_0
      // 44ce: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44d1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 44d4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 44d7: aastore
      // 44d8: dup
      // 44d9: bipush 7
      // 44db: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 44de: dup
      // 44df: ldc_w 1.125
      // 44e2: ldc -2.5
      // 44e4: fconst_0
      // 44e5: fconst_0
      // 44e6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44e9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 44ec: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 44ef: aastore
      // 44f0: dup
      // 44f1: bipush 8
      // 44f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 44f6: dup
      // 44f7: ldc 1.25
      // 44f9: ldc -17.3727
      // 44fb: ldc -0.5463
      // 44fd: ldc 1.003
      // 44ff: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4502: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4505: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4508: aastore
      // 4509: dup
      // 450a: bipush 9
      // 450c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 450f: dup
      // 4510: ldc_w 1.4583
      // 4513: ldc -47.3727
      // 4515: ldc -0.5463
      // 4517: ldc 1.003
      // 4519: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 451c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 451f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4522: aastore
      // 4523: dup
      // 4524: bipush 10
      // 4526: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4529: dup
      // 452a: ldc 1.5
      // 452c: ldc -23.2281
      // 452e: ldc 14.0447
      // 4530: ldc 14.8166
      // 4532: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4535: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4538: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 453b: aastore
      // 453c: dup
      // 453d: bipush 11
      // 453f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4542: dup
      // 4543: ldc 1.75
      // 4545: ldc -15.7281
      // 4547: ldc 14.0447
      // 4549: ldc 14.8166
      // 454b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 454e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4551: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4554: aastore
      // 4555: dup
      // 4556: bipush 12
      // 4558: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 455b: dup
      // 455c: ldc_w 1.875
      // 455f: ldc -35.0
      // 4561: fconst_0
      // 4562: fconst_0
      // 4563: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4566: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4569: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 456c: aastore
      // 456d: dup
      // 456e: bipush 13
      // 4570: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4573: dup
      // 4574: ldc_w 2.0833
      // 4577: ldc -33.735
      // 4579: ldc_w 9.9319
      // 457c: ldc_w 14.4817
      // 457f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4582: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4585: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4588: aastore
      // 4589: dup
      // 458a: bipush 14
      // 458c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 458f: dup
      // 4590: ldc_w 2.125
      // 4593: fconst_0
      // 4594: fconst_0
      // 4595: fconst_0
      // 4596: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4599: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 459c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 459f: aastore
      // 45a0: dup
      // 45a1: bipush 15
      // 45a3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 45a6: dup
      // 45a7: ldc_w 2.375
      // 45aa: ldc -2.5
      // 45ac: fconst_0
      // 45ad: fconst_0
      // 45ae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 45b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 45b7: aastore
      // 45b8: dup
      // 45b9: bipush 16
      // 45bb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 45be: dup
      // 45bf: ldc_w 2.5833
      // 45c2: ldc -17.3727
      // 45c4: ldc -0.5463
      // 45c6: ldc 1.003
      // 45c8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45cb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 45ce: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 45d1: aastore
      // 45d2: dup
      // 45d3: bipush 17
      // 45d5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 45d8: dup
      // 45d9: ldc 3.0
      // 45db: fconst_0
      // 45dc: fconst_0
      // 45dd: fconst_0
      // 45de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 45e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 45e7: aastore
      // 45e8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 45eb: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 45ee: ldc "right_leg"
      // 45f0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 45f3: dup
      // 45f4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 45f7: bipush 10
      // 45f9: anewarray 28
      // 45fc: dup
      // 45fd: bipush 0
      // 45fe: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4601: dup
      // 4602: fconst_0
      // 4603: ldc_w -17.5
      // 4606: fconst_0
      // 4607: fconst_0
      // 4608: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 460b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 460e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4611: aastore
      // 4612: dup
      // 4613: bipush 1
      // 4614: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4617: dup
      // 4618: ldc 0.375
      // 461a: ldc_w 22.5
      // 461d: fconst_0
      // 461e: fconst_0
      // 461f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4622: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4625: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4628: aastore
      // 4629: dup
      // 462a: bipush 2
      // 462b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 462e: dup
      // 462f: ldc 0.625
      // 4631: ldc_w -17.5
      // 4634: fconst_0
      // 4635: fconst_0
      // 4636: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4639: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 463c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 463f: aastore
      // 4640: dup
      // 4641: bipush 3
      // 4642: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4645: dup
      // 4646: fconst_1
      // 4647: ldc_w 22.5
      // 464a: fconst_0
      // 464b: fconst_0
      // 464c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 464f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4652: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4655: aastore
      // 4656: dup
      // 4657: bipush 4
      // 4658: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 465b: dup
      // 465c: ldc 1.25
      // 465e: ldc_w -17.5
      // 4661: fconst_0
      // 4662: fconst_0
      // 4663: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4666: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4669: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 466c: aastore
      // 466d: dup
      // 466e: bipush 5
      // 466f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4672: dup
      // 4673: ldc_w 1.625
      // 4676: ldc_w 22.5
      // 4679: fconst_0
      // 467a: fconst_0
      // 467b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 467e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4681: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4684: aastore
      // 4685: dup
      // 4686: bipush 6
      // 4688: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 468b: dup
      // 468c: ldc_w 1.875
      // 468f: ldc_w -17.5
      // 4692: fconst_0
      // 4693: fconst_0
      // 4694: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4697: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 469a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 469d: aastore
      // 469e: dup
      // 469f: bipush 7
      // 46a1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 46a4: dup
      // 46a5: ldc 2.25
      // 46a7: ldc_w 22.5
      // 46aa: fconst_0
      // 46ab: fconst_0
      // 46ac: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46af: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 46b2: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 46b5: aastore
      // 46b6: dup
      // 46b7: bipush 8
      // 46b9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 46bc: dup
      // 46bd: ldc_w 2.5833
      // 46c0: ldc_w -17.5
      // 46c3: fconst_0
      // 46c4: fconst_0
      // 46c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 46cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 46ce: aastore
      // 46cf: dup
      // 46d0: bipush 9
      // 46d2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 46d5: dup
      // 46d6: ldc 3.0
      // 46d8: fconst_0
      // 46d9: fconst_0
      // 46da: fconst_0
      // 46db: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46de: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 46e1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 46e4: aastore
      // 46e5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 46e8: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 46eb: ldc "right_leg"
      // 46ed: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 46f0: dup
      // 46f1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 46f4: bipush 12
      // 46f6: anewarray 28
      // 46f9: dup
      // 46fa: bipush 0
      // 46fb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 46fe: dup
      // 46ff: fconst_0
      // 4700: fconst_0
      // 4701: fconst_0
      // 4702: ldc -6.0
      // 4704: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4707: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 470a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 470d: aastore
      // 470e: dup
      // 470f: bipush 1
      // 4710: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4713: dup
      // 4714: ldc 0.375
      // 4716: fconst_0
      // 4717: fconst_0
      // 4718: ldc 3.0
      // 471a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 471d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4720: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4723: aastore
      // 4724: dup
      // 4725: bipush 2
      // 4726: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4729: dup
      // 472a: ldc 0.625
      // 472c: fconst_0
      // 472d: fconst_0
      // 472e: ldc -6.0
      // 4730: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4733: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4736: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4739: aastore
      // 473a: dup
      // 473b: bipush 3
      // 473c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 473f: dup
      // 4740: fconst_1
      // 4741: fconst_0
      // 4742: fconst_0
      // 4743: ldc 3.0
      // 4745: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4748: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 474b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 474e: aastore
      // 474f: dup
      // 4750: bipush 4
      // 4751: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4754: dup
      // 4755: ldc_w 1.125
      // 4758: fconst_0
      // 4759: fconst_2
      // 475a: ldc_w -3.56
      // 475d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4760: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4763: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4766: aastore
      // 4767: dup
      // 4768: bipush 5
      // 4769: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 476c: dup
      // 476d: ldc 1.25
      // 476f: fconst_0
      // 4770: fconst_0
      // 4771: ldc -6.0
      // 4773: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4776: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4779: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 477c: aastore
      // 477d: dup
      // 477e: bipush 6
      // 4780: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4783: dup
      // 4784: ldc_w 1.625
      // 4787: fconst_0
      // 4788: fconst_0
      // 4789: ldc 3.0
      // 478b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 478e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4791: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4794: aastore
      // 4795: dup
      // 4796: bipush 7
      // 4798: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 479b: dup
      // 479c: ldc_w 1.875
      // 479f: fconst_0
      // 47a0: fconst_0
      // 47a1: ldc -6.0
      // 47a3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47a6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 47a9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 47ac: aastore
      // 47ad: dup
      // 47ae: bipush 8
      // 47b0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 47b3: dup
      // 47b4: ldc 2.25
      // 47b6: fconst_0
      // 47b7: fconst_0
      // 47b8: ldc 3.0
      // 47ba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47bd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 47c0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 47c3: aastore
      // 47c4: dup
      // 47c5: bipush 9
      // 47c7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 47ca: dup
      // 47cb: ldc_w 2.375
      // 47ce: fconst_0
      // 47cf: fconst_2
      // 47d0: ldc_w -3.56
      // 47d3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47d6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 47d9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 47dc: aastore
      // 47dd: dup
      // 47de: bipush 10
      // 47e0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 47e3: dup
      // 47e4: ldc_w 2.5833
      // 47e7: fconst_0
      // 47e8: fconst_0
      // 47e9: ldc -6.0
      // 47eb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 47ee: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 47f1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 47f4: aastore
      // 47f5: dup
      // 47f6: bipush 11
      // 47f8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 47fb: dup
      // 47fc: ldc 3.0
      // 47fe: fconst_0
      // 47ff: fconst_0
      // 4800: fconst_0
      // 4801: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4804: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4807: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 480a: aastore
      // 480b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 480e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4811: ldc "left_leg"
      // 4813: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4816: dup
      // 4817: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 481a: bipush 10
      // 481c: anewarray 28
      // 481f: dup
      // 4820: bipush 0
      // 4821: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4824: dup
      // 4825: fconst_0
      // 4826: ldc_w 22.5
      // 4829: fconst_0
      // 482a: fconst_0
      // 482b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 482e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4831: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4834: aastore
      // 4835: dup
      // 4836: bipush 1
      // 4837: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 483a: dup
      // 483b: ldc 0.375
      // 483d: ldc_w -17.5
      // 4840: fconst_0
      // 4841: fconst_0
      // 4842: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4845: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4848: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 484b: aastore
      // 484c: dup
      // 484d: bipush 2
      // 484e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4851: dup
      // 4852: ldc 0.625
      // 4854: ldc_w 22.5
      // 4857: fconst_0
      // 4858: fconst_0
      // 4859: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 485c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 485f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4862: aastore
      // 4863: dup
      // 4864: bipush 3
      // 4865: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4868: dup
      // 4869: fconst_1
      // 486a: ldc_w -17.5
      // 486d: fconst_0
      // 486e: fconst_0
      // 486f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4872: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4875: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4878: aastore
      // 4879: dup
      // 487a: bipush 4
      // 487b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 487e: dup
      // 487f: ldc 1.25
      // 4881: ldc_w 22.5
      // 4884: fconst_0
      // 4885: fconst_0
      // 4886: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4889: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 488c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 488f: aastore
      // 4890: dup
      // 4891: bipush 5
      // 4892: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4895: dup
      // 4896: ldc_w 1.625
      // 4899: ldc_w -17.5
      // 489c: fconst_0
      // 489d: fconst_0
      // 489e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48a1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 48a4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 48a7: aastore
      // 48a8: dup
      // 48a9: bipush 6
      // 48ab: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 48ae: dup
      // 48af: ldc_w 1.875
      // 48b2: ldc_w 22.5
      // 48b5: fconst_0
      // 48b6: fconst_0
      // 48b7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48ba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 48bd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 48c0: aastore
      // 48c1: dup
      // 48c2: bipush 7
      // 48c4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 48c7: dup
      // 48c8: ldc 2.25
      // 48ca: ldc_w -17.5
      // 48cd: fconst_0
      // 48ce: fconst_0
      // 48cf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48d2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 48d5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 48d8: aastore
      // 48d9: dup
      // 48da: bipush 8
      // 48dc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 48df: dup
      // 48e0: ldc_w 2.5833
      // 48e3: ldc_w 22.5
      // 48e6: fconst_0
      // 48e7: fconst_0
      // 48e8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48eb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 48ee: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 48f1: aastore
      // 48f2: dup
      // 48f3: bipush 9
      // 48f5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 48f8: dup
      // 48f9: ldc 3.0
      // 48fb: fconst_0
      // 48fc: fconst_0
      // 48fd: fconst_0
      // 48fe: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4901: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4904: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4907: aastore
      // 4908: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 490b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 490e: ldc "left_leg"
      // 4910: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4913: dup
      // 4914: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4917: bipush 12
      // 4919: anewarray 28
      // 491c: dup
      // 491d: bipush 0
      // 491e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4921: dup
      // 4922: fconst_0
      // 4923: fconst_0
      // 4924: fconst_0
      // 4925: ldc 3.0
      // 4927: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 492a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 492d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4930: aastore
      // 4931: dup
      // 4932: bipush 1
      // 4933: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4936: dup
      // 4937: ldc 0.375
      // 4939: fconst_0
      // 493a: fconst_0
      // 493b: ldc -6.0
      // 493d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4940: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4943: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4946: aastore
      // 4947: dup
      // 4948: bipush 2
      // 4949: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 494c: dup
      // 494d: ldc 0.625
      // 494f: fconst_0
      // 4950: fconst_0
      // 4951: ldc 3.0
      // 4953: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4956: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4959: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 495c: aastore
      // 495d: dup
      // 495e: bipush 3
      // 495f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4962: dup
      // 4963: ldc 0.8333
      // 4965: fconst_0
      // 4966: fconst_2
      // 4967: ldc_w -4.22
      // 496a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 496d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4970: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4973: aastore
      // 4974: dup
      // 4975: bipush 4
      // 4976: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4979: dup
      // 497a: fconst_1
      // 497b: fconst_0
      // 497c: fconst_0
      // 497d: ldc -6.0
      // 497f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4982: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4985: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4988: aastore
      // 4989: dup
      // 498a: bipush 5
      // 498b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 498e: dup
      // 498f: ldc 1.25
      // 4991: fconst_0
      // 4992: fconst_0
      // 4993: ldc 3.0
      // 4995: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4998: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 499b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 499e: aastore
      // 499f: dup
      // 49a0: bipush 6
      // 49a2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 49a5: dup
      // 49a6: ldc_w 1.625
      // 49a9: fconst_0
      // 49aa: fconst_0
      // 49ab: ldc -6.0
      // 49ad: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 49b0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 49b3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 49b6: aastore
      // 49b7: dup
      // 49b8: bipush 7
      // 49ba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 49bd: dup
      // 49be: ldc_w 1.875
      // 49c1: fconst_0
      // 49c2: fconst_0
      // 49c3: ldc 3.0
      // 49c5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 49c8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 49cb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 49ce: aastore
      // 49cf: dup
      // 49d0: bipush 8
      // 49d2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 49d5: dup
      // 49d6: ldc_w 2.0833
      // 49d9: fconst_0
      // 49da: fconst_2
      // 49db: ldc_w -4.22
      // 49de: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 49e1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 49e4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 49e7: aastore
      // 49e8: dup
      // 49e9: bipush 9
      // 49eb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 49ee: dup
      // 49ef: ldc 2.25
      // 49f1: fconst_0
      // 49f2: fconst_0
      // 49f3: ldc -6.0
      // 49f5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 49f8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 49fb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 49fe: aastore
      // 49ff: dup
      // 4a00: bipush 10
      // 4a02: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a05: dup
      // 4a06: ldc_w 2.5833
      // 4a09: fconst_0
      // 4a0a: fconst_0
      // 4a0b: ldc 3.0
      // 4a0d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4a10: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4a13: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4a16: aastore
      // 4a17: dup
      // 4a18: bipush 11
      // 4a1a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a1d: dup
      // 4a1e: ldc 3.0
      // 4a20: fconst_0
      // 4a21: fconst_0
      // 4a22: ldc 3.0
      // 4a24: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4a27: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4a2a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4a2d: aastore
      // 4a2e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4a31: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4a34: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 4a37: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.NANTA Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 4a3a: ldc 1.5
      // 4a3c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4a3f: ldc "body"
      // 4a41: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4a44: dup
      // 4a45: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4a48: bipush 5
      // 4a49: anewarray 28
      // 4a4c: dup
      // 4a4d: bipush 0
      // 4a4e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a51: dup
      // 4a52: fconst_0
      // 4a53: fconst_0
      // 4a54: fconst_0
      // 4a55: fconst_0
      // 4a56: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a59: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4a5c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4a5f: aastore
      // 4a60: dup
      // 4a61: bipush 1
      // 4a62: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a65: dup
      // 4a66: ldc 0.5
      // 4a68: ldc_w -25.5305
      // 4a6b: ldc_w 11.3125
      // 4a6e: ldc_w -5.3525
      // 4a71: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a74: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4a77: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4a7a: aastore
      // 4a7b: dup
      // 4a7c: bipush 2
      // 4a7d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a80: dup
      // 4a81: ldc 0.75
      // 4a83: ldc_w -15.5305
      // 4a86: ldc_w 11.3125
      // 4a89: ldc_w -5.3525
      // 4a8c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a8f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4a92: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4a95: aastore
      // 4a96: dup
      // 4a97: bipush 3
      // 4a98: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4a9b: dup
      // 4a9c: fconst_1
      // 4a9d: ldc_w 16.9695
      // 4aa0: ldc_w 11.3125
      // 4aa3: ldc_w -5.3525
      // 4aa6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aa9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4aac: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4aaf: aastore
      // 4ab0: dup
      // 4ab1: bipush 4
      // 4ab2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4ab5: dup
      // 4ab6: ldc_w 1.125
      // 4ab9: ldc_w 26.9695
      // 4abc: ldc_w 11.3125
      // 4abf: ldc_w -5.3525
      // 4ac2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ac5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4ac8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4acb: aastore
      // 4acc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4acf: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4ad2: ldc "body"
      // 4ad4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4ad7: dup
      // 4ad8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4adb: bipush 2
      // 4adc: anewarray 28
      // 4adf: dup
      // 4ae0: bipush 0
      // 4ae1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4ae4: dup
      // 4ae5: fconst_0
      // 4ae6: fconst_0
      // 4ae7: fconst_0
      // 4ae8: fconst_0
      // 4ae9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4aec: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4aef: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4af2: aastore
      // 4af3: dup
      // 4af4: bipush 1
      // 4af5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4af8: dup
      // 4af9: ldc 0.75
      // 4afb: fconst_0
      // 4afc: ldc_w -3.0
      // 4aff: fconst_0
      // 4b00: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4b03: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b06: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b09: aastore
      // 4b0a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4b0d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4b10: ldc "chest"
      // 4b12: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4b15: dup
      // 4b16: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4b19: bipush 5
      // 4b1a: anewarray 28
      // 4b1d: dup
      // 4b1e: bipush 0
      // 4b1f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4b22: dup
      // 4b23: fconst_0
      // 4b24: fconst_0
      // 4b25: fconst_0
      // 4b26: fconst_0
      // 4b27: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b2a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b2d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b30: aastore
      // 4b31: dup
      // 4b32: bipush 1
      // 4b33: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4b36: dup
      // 4b37: ldc 0.5
      // 4b39: ldc -5.0
      // 4b3b: ldc_w 15.0
      // 4b3e: fconst_0
      // 4b3f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b42: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b45: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b48: aastore
      // 4b49: dup
      // 4b4a: bipush 2
      // 4b4b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4b4e: dup
      // 4b4f: ldc 0.75
      // 4b51: ldc_w -15.7729
      // 4b54: ldc_w -7.9626
      // 4b57: ldc_w -14.9732
      // 4b5a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b5d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b60: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b63: aastore
      // 4b64: dup
      // 4b65: bipush 3
      // 4b66: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4b69: dup
      // 4b6a: fconst_1
      // 4b6b: ldc_w 15.7866
      // 4b6e: ldc_w 3.2354
      // 4b71: ldc_w 7.434
      // 4b74: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b77: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b7a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b7d: aastore
      // 4b7e: dup
      // 4b7f: bipush 4
      // 4b80: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4b83: dup
      // 4b84: ldc_w 1.125
      // 4b87: ldc_w 10.7866
      // 4b8a: ldc_w 3.2354
      // 4b8d: ldc_w 7.434
      // 4b90: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b93: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4b96: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4b99: aastore
      // 4b9a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4b9d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4ba0: ldc "head"
      // 4ba2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4ba5: dup
      // 4ba6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4ba9: bipush 5
      // 4baa: anewarray 28
      // 4bad: dup
      // 4bae: bipush 0
      // 4baf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4bb2: dup
      // 4bb3: fconst_0
      // 4bb4: fconst_0
      // 4bb5: fconst_0
      // 4bb6: fconst_0
      // 4bb7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bba: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4bbd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4bc0: aastore
      // 4bc1: dup
      // 4bc2: bipush 1
      // 4bc3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4bc6: dup
      // 4bc7: ldc 0.5
      // 4bc9: ldc_w 19.5538
      // 4bcc: ldc_w -17.7493
      // 4bcf: ldc_w -7.2742
      // 4bd2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bd5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4bd8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4bdb: aastore
      // 4bdc: dup
      // 4bdd: bipush 2
      // 4bde: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4be1: dup
      // 4be2: ldc 0.75
      // 4be4: ldc_w 20.7985
      // 4be7: ldc_w -6.6341
      // 4bea: ldc_w -13.1352
      // 4bed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bf0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4bf3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4bf6: aastore
      // 4bf7: dup
      // 4bf8: bipush 3
      // 4bf9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4bfc: dup
      // 4bfd: fconst_1
      // 4bfe: ldc_w -2.3469
      // 4c01: ldc_w 2.5273
      // 4c04: ldc_w 4.6737
      // 4c07: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c0a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c0d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c10: aastore
      // 4c11: dup
      // 4c12: bipush 4
      // 4c13: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4c16: dup
      // 4c17: ldc_w 1.125
      // 4c1a: ldc_w -17.2788
      // 4c1d: ldc_w 4.7896
      // 4c20: ldc_w 5.7398
      // 4c23: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c26: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c29: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c2c: aastore
      // 4c2d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4c30: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4c33: ldc "eye"
      // 4c35: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4c38: dup
      // 4c39: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4c3c: bipush 17
      // 4c3e: anewarray 28
      // 4c41: dup
      // 4c42: bipush 0
      // 4c43: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4c46: dup
      // 4c47: fconst_0
      // 4c48: fconst_0
      // 4c49: fconst_0
      // 4c4a: fconst_0
      // 4c4b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4c4e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c51: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c54: aastore
      // 4c55: dup
      // 4c56: bipush 1
      // 4c57: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4c5a: dup
      // 4c5b: ldc_w 0.4583
      // 4c5e: fconst_0
      // 4c5f: fconst_0
      // 4c60: fconst_0
      // 4c61: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4c64: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c67: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c6a: aastore
      // 4c6b: dup
      // 4c6c: bipush 2
      // 4c6d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4c70: dup
      // 4c71: ldc 0.5
      // 4c73: ldc_w 0.3
      // 4c76: ldc_w -0.3
      // 4c79: fconst_0
      // 4c7a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4c7d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c80: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c83: aastore
      // 4c84: dup
      // 4c85: bipush 3
      // 4c86: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4c89: dup
      // 4c8a: ldc_w 0.5417
      // 4c8d: ldc -0.2
      // 4c8f: ldc_w -0.1
      // 4c92: fconst_0
      // 4c93: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4c96: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4c99: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4c9c: aastore
      // 4c9d: dup
      // 4c9e: bipush 4
      // 4c9f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4ca2: dup
      // 4ca3: ldc 0.5833
      // 4ca5: fconst_0
      // 4ca6: ldc_w -0.1
      // 4ca9: fconst_0
      // 4caa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4cb0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4cb3: aastore
      // 4cb4: dup
      // 4cb5: bipush 5
      // 4cb6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4cb9: dup
      // 4cba: ldc 0.625
      // 4cbc: fconst_0
      // 4cbd: ldc -0.2
      // 4cbf: fconst_0
      // 4cc0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cc3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4cc6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4cc9: aastore
      // 4cca: dup
      // 4ccb: bipush 6
      // 4ccd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4cd0: dup
      // 4cd1: ldc_w 0.6667
      // 4cd4: ldc_w 0.3
      // 4cd7: ldc -0.2
      // 4cd9: fconst_0
      // 4cda: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cdd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4ce0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4ce3: aastore
      // 4ce4: dup
      // 4ce5: bipush 7
      // 4ce7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4cea: dup
      // 4ceb: ldc_w 0.7083
      // 4cee: ldc_w 0.3
      // 4cf1: ldc -0.5
      // 4cf3: fconst_0
      // 4cf4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4cf7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4cfa: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4cfd: aastore
      // 4cfe: dup
      // 4cff: bipush 8
      // 4d01: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d04: dup
      // 4d05: ldc 0.75
      // 4d07: fconst_0
      // 4d08: ldc -0.5
      // 4d0a: fconst_0
      // 4d0b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d0e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d11: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d14: aastore
      // 4d15: dup
      // 4d16: bipush 9
      // 4d18: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d1b: dup
      // 4d1c: ldc_w 0.7917
      // 4d1f: ldc_w 0.3
      // 4d22: ldc -0.5
      // 4d24: fconst_0
      // 4d25: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d28: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d2b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d2e: aastore
      // 4d2f: dup
      // 4d30: bipush 10
      // 4d32: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d35: dup
      // 4d36: ldc 0.8333
      // 4d38: ldc_w 0.3
      // 4d3b: ldc_w -0.8
      // 4d3e: fconst_0
      // 4d3f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d42: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d45: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d48: aastore
      // 4d49: dup
      // 4d4a: bipush 11
      // 4d4c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d4f: dup
      // 4d50: ldc 0.875
      // 4d52: ldc_w -0.1
      // 4d55: ldc_w -0.8
      // 4d58: fconst_0
      // 4d59: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d5c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d5f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d62: aastore
      // 4d63: dup
      // 4d64: bipush 12
      // 4d66: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d69: dup
      // 4d6a: ldc_w 0.9167
      // 4d6d: ldc_w -0.1
      // 4d70: ldc_w -0.3
      // 4d73: fconst_0
      // 4d74: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d77: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d7a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d7d: aastore
      // 4d7e: dup
      // 4d7f: bipush 13
      // 4d81: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d84: dup
      // 4d85: ldc_w 0.9583
      // 4d88: ldc_w 0.3
      // 4d8b: ldc_w -0.3
      // 4d8e: fconst_0
      // 4d8f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4d92: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4d95: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4d98: aastore
      // 4d99: dup
      // 4d9a: bipush 14
      // 4d9c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4d9f: dup
      // 4da0: ldc_w 1.0833
      // 4da3: ldc_w 0.16
      // 4da6: ldc_w -0.94
      // 4da9: fconst_0
      // 4daa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4dad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4db0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4db3: aastore
      // 4db4: dup
      // 4db5: bipush 15
      // 4db7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4dba: dup
      // 4dbb: ldc_w 1.2083
      // 4dbe: ldc_w 0.46
      // 4dc1: ldc_w -0.94
      // 4dc4: fconst_0
      // 4dc5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4dc8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4dcb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4dce: aastore
      // 4dcf: dup
      // 4dd0: bipush 16
      // 4dd2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4dd5: dup
      // 4dd6: ldc_w 1.2917
      // 4dd9: ldc_w 0.46
      // 4ddc: ldc_w -2.84
      // 4ddf: fconst_0
      // 4de0: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4de3: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4de6: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4de9: aastore
      // 4dea: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4ded: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4df0: ldc "eye"
      // 4df2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4df5: dup
      // 4df6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.SCALE Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4df9: bipush 3
      // 4dfa: anewarray 28
      // 4dfd: dup
      // 4dfe: bipush 0
      // 4dff: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e02: dup
      // 4e03: fconst_0
      // 4e04: dconst_1
      // 4e05: dconst_1
      // 4e06: dconst_1
      // 4e07: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4e0a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.LINEAR Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e0d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e10: aastore
      // 4e11: dup
      // 4e12: bipush 1
      // 4e13: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e16: dup
      // 4e17: ldc_w 1.0833
      // 4e1a: dconst_1
      // 4e1b: dconst_1
      // 4e1c: dconst_1
      // 4e1d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4e20: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e23: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e26: aastore
      // 4e27: dup
      // 4e28: bipush 2
      // 4e29: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e2c: dup
      // 4e2d: ldc_w 1.4167
      // 4e30: dconst_0
      // 4e31: dconst_0
      // 4e32: dconst_0
      // 4e33: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4e36: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e39: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e3c: aastore
      // 4e3d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4e40: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4e43: ldc "right_arm"
      // 4e45: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4e48: dup
      // 4e49: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4e4c: bipush 5
      // 4e4d: anewarray 28
      // 4e50: dup
      // 4e51: bipush 0
      // 4e52: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e55: dup
      // 4e56: fconst_0
      // 4e57: fconst_0
      // 4e58: fconst_0
      // 4e59: fconst_0
      // 4e5a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e5d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e60: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e63: aastore
      // 4e64: dup
      // 4e65: bipush 1
      // 4e66: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e69: dup
      // 4e6a: ldc 0.5
      // 4e6c: ldc_w -11.5752
      // 4e6f: ldc_w 4.7511
      // 4e72: ldc_w 22.0182
      // 4e75: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e78: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e7b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e7e: aastore
      // 4e7f: dup
      // 4e80: bipush 2
      // 4e81: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e84: dup
      // 4e85: ldc 0.75
      // 4e87: ldc_w -16.875
      // 4e8a: ldc_w 10.191
      // 4e8d: ldc_w 38.8013
      // 4e90: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e93: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4e96: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4e99: aastore
      // 4e9a: dup
      // 4e9b: bipush 3
      // 4e9c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4e9f: dup
      // 4ea0: fconst_1
      // 4ea1: ldc_w -52.4592
      // 4ea4: ldc_w 4.2642
      // 4ea7: ldc_w 34.1663
      // 4eaa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ead: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4eb0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4eb3: aastore
      // 4eb4: dup
      // 4eb5: bipush 4
      // 4eb6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4eb9: dup
      // 4eba: ldc_w 1.125
      // 4ebd: ldc_w -51.2051
      // 4ec0: ldc_w 14.1072
      // 4ec3: ldc_w 41.9818
      // 4ec6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ec9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4ecc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4ecf: aastore
      // 4ed0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4ed3: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4ed6: ldc "right_arm"
      // 4ed8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4edb: dup
      // 4edc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4edf: bipush 2
      // 4ee0: anewarray 28
      // 4ee3: dup
      // 4ee4: bipush 0
      // 4ee5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4ee8: dup
      // 4ee9: fconst_0
      // 4eea: fconst_0
      // 4eeb: fconst_0
      // 4eec: fconst_0
      // 4eed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ef0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4ef3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4ef6: aastore
      // 4ef7: dup
      // 4ef8: bipush 1
      // 4ef9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4efc: dup
      // 4efd: ldc 0.5
      // 4eff: fconst_0
      // 4f00: fconst_0
      // 4f01: fconst_0
      // 4f02: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f05: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f08: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f0b: aastore
      // 4f0c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4f0f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4f12: ldc "right_arm2"
      // 4f14: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4f17: dup
      // 4f18: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4f1b: bipush 5
      // 4f1c: anewarray 28
      // 4f1f: dup
      // 4f20: bipush 0
      // 4f21: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4f24: dup
      // 4f25: fconst_0
      // 4f26: fconst_0
      // 4f27: fconst_0
      // 4f28: fconst_0
      // 4f29: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f2c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f2f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f32: aastore
      // 4f33: dup
      // 4f34: bipush 1
      // 4f35: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4f38: dup
      // 4f39: ldc 0.5
      // 4f3b: ldc -10.0
      // 4f3d: fconst_0
      // 4f3e: fconst_0
      // 4f3f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f42: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f45: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f48: aastore
      // 4f49: dup
      // 4f4a: bipush 2
      // 4f4b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4f4e: dup
      // 4f4f: ldc 0.75
      // 4f51: ldc_w -50.0
      // 4f54: fconst_0
      // 4f55: fconst_0
      // 4f56: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f59: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f5c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f5f: aastore
      // 4f60: dup
      // 4f61: bipush 3
      // 4f62: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4f65: dup
      // 4f66: fconst_1
      // 4f67: ldc_w -37.5
      // 4f6a: fconst_0
      // 4f6b: fconst_0
      // 4f6c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f6f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f72: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f75: aastore
      // 4f76: dup
      // 4f77: bipush 4
      // 4f78: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4f7b: dup
      // 4f7c: ldc_w 1.125
      // 4f7f: ldc_w -50.0
      // 4f82: fconst_0
      // 4f83: fconst_0
      // 4f84: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f87: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4f8a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4f8d: aastore
      // 4f8e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 4f91: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 4f94: ldc "left_arm"
      // 4f96: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 4f99: dup
      // 4f9a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 4f9d: bipush 5
      // 4f9e: anewarray 28
      // 4fa1: dup
      // 4fa2: bipush 0
      // 4fa3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4fa6: dup
      // 4fa7: fconst_0
      // 4fa8: fconst_0
      // 4fa9: fconst_0
      // 4faa: fconst_0
      // 4fab: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fae: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4fb1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4fb4: aastore
      // 4fb5: dup
      // 4fb6: bipush 1
      // 4fb7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4fba: dup
      // 4fbb: ldc 0.5
      // 4fbd: ldc_w 35.0
      // 4fc0: fconst_0
      // 4fc1: ldc -10.0
      // 4fc3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fc6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4fc9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4fcc: aastore
      // 4fcd: dup
      // 4fce: bipush 2
      // 4fcf: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4fd2: dup
      // 4fd3: ldc 0.75
      // 4fd5: ldc_w 54.769
      // 4fd8: ldc_w 4.2936
      // 4fdb: ldc_w -16.1552
      // 4fde: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fe1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4fe4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 4fe7: aastore
      // 4fe8: dup
      // 4fe9: bipush 3
      // 4fea: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 4fed: dup
      // 4fee: fconst_1
      // 4fef: ldc_w 23.3922
      // 4ff2: ldc_w 9.4111
      // 4ff5: ldc_w -27.6459
      // 4ff8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ffb: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 4ffe: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5001: aastore
      // 5002: dup
      // 5003: bipush 4
      // 5004: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5007: dup
      // 5008: ldc_w 1.125
      // 500b: ldc_w 20.3597
      // 500e: ldc_w 15.0297
      // 5011: ldc_w -41.8845
      // 5014: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5017: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 501a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 501d: aastore
      // 501e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5021: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5024: ldc "left_arm"
      // 5026: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5029: dup
      // 502a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 502d: bipush 2
      // 502e: anewarray 28
      // 5031: dup
      // 5032: bipush 0
      // 5033: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5036: dup
      // 5037: fconst_0
      // 5038: fconst_0
      // 5039: fconst_0
      // 503a: fconst_0
      // 503b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 503e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5041: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5044: aastore
      // 5045: dup
      // 5046: bipush 1
      // 5047: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 504a: dup
      // 504b: ldc 0.5
      // 504d: fconst_0
      // 504e: fconst_0
      // 504f: fconst_0
      // 5050: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5053: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5056: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5059: aastore
      // 505a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 505d: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5060: ldc "left_arm2"
      // 5062: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5065: dup
      // 5066: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5069: bipush 5
      // 506a: anewarray 28
      // 506d: dup
      // 506e: bipush 0
      // 506f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5072: dup
      // 5073: fconst_0
      // 5074: fconst_0
      // 5075: fconst_0
      // 5076: fconst_0
      // 5077: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 507a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 507d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5080: aastore
      // 5081: dup
      // 5082: bipush 1
      // 5083: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5086: dup
      // 5087: ldc 0.5
      // 5089: ldc_w -40.0
      // 508c: fconst_0
      // 508d: fconst_0
      // 508e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5091: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5094: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5097: aastore
      // 5098: dup
      // 5099: bipush 2
      // 509a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 509d: dup
      // 509e: ldc 0.75
      // 50a0: ldc_w -22.5
      // 50a3: fconst_0
      // 50a4: fconst_0
      // 50a5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50a8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 50ab: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 50ae: aastore
      // 50af: dup
      // 50b0: bipush 3
      // 50b1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 50b4: dup
      // 50b5: fconst_1
      // 50b6: ldc_w -42.5
      // 50b9: fconst_0
      // 50ba: fconst_0
      // 50bb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50be: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 50c1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 50c4: aastore
      // 50c5: dup
      // 50c6: bipush 4
      // 50c7: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 50ca: dup
      // 50cb: ldc_w 1.125
      // 50ce: ldc_w -65.0
      // 50d1: fconst_0
      // 50d2: fconst_0
      // 50d3: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50d6: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 50d9: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 50dc: aastore
      // 50dd: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 50e0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 50e3: ldc "right_leg"
      // 50e5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 50e8: dup
      // 50e9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 50ec: bipush 4
      // 50ed: anewarray 28
      // 50f0: dup
      // 50f1: bipush 0
      // 50f2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 50f5: dup
      // 50f6: fconst_0
      // 50f7: fconst_0
      // 50f8: fconst_0
      // 50f9: fconst_0
      // 50fa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 50fd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5100: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5103: aastore
      // 5104: dup
      // 5105: bipush 1
      // 5106: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5109: dup
      // 510a: ldc 0.5
      // 510c: fconst_0
      // 510d: fconst_0
      // 510e: ldc_w 7.5
      // 5111: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5114: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5117: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 511a: aastore
      // 511b: dup
      // 511c: bipush 2
      // 511d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5120: dup
      // 5121: ldc 0.75
      // 5123: ldc_w 27.5
      // 5126: fconst_0
      // 5127: ldc_w 7.5
      // 512a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 512d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5130: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5133: aastore
      // 5134: dup
      // 5135: bipush 3
      // 5136: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5139: dup
      // 513a: fconst_1
      // 513b: ldc_w 80.0
      // 513e: fconst_0
      // 513f: ldc_w 7.5
      // 5142: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5145: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5148: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 514b: aastore
      // 514c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 514f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5152: ldc "right_leg"
      // 5154: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5157: dup
      // 5158: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 515b: bipush 5
      // 515c: anewarray 28
      // 515f: dup
      // 5160: bipush 0
      // 5161: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5164: dup
      // 5165: fconst_0
      // 5166: fconst_0
      // 5167: fconst_0
      // 5168: fconst_0
      // 5169: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 516c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 516f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5172: aastore
      // 5173: dup
      // 5174: bipush 1
      // 5175: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5178: dup
      // 5179: ldc 0.5
      // 517b: fconst_0
      // 517c: fconst_0
      // 517d: ldc -4.0
      // 517f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5182: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5185: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5188: aastore
      // 5189: dup
      // 518a: bipush 2
      // 518b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 518e: dup
      // 518f: ldc 0.75
      // 5191: fconst_0
      // 5192: ldc_w -3.0
      // 5195: ldc -4.0
      // 5197: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 519a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 519d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 51a0: aastore
      // 51a1: dup
      // 51a2: bipush 3
      // 51a3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 51a6: dup
      // 51a7: fconst_1
      // 51a8: fconst_0
      // 51a9: ldc -5.0
      // 51ab: ldc -7.0
      // 51ad: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51b0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 51b3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 51b6: aastore
      // 51b7: dup
      // 51b8: bipush 4
      // 51b9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 51bc: dup
      // 51bd: ldc_w 1.125
      // 51c0: fconst_0
      // 51c1: ldc -5.0
      // 51c3: ldc_w -8.0
      // 51c6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51c9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 51cc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 51cf: aastore
      // 51d0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 51d3: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 51d6: ldc "left_leg"
      // 51d8: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 51db: dup
      // 51dc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 51df: bipush 3
      // 51e0: anewarray 28
      // 51e3: dup
      // 51e4: bipush 0
      // 51e5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 51e8: dup
      // 51e9: fconst_0
      // 51ea: fconst_0
      // 51eb: fconst_0
      // 51ec: fconst_0
      // 51ed: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 51f0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 51f3: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 51f6: aastore
      // 51f7: dup
      // 51f8: bipush 1
      // 51f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 51fc: dup
      // 51fd: ldc 0.5
      // 51ff: ldc_w 17.5
      // 5202: fconst_0
      // 5203: fconst_0
      // 5204: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5207: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 520a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 520d: aastore
      // 520e: dup
      // 520f: bipush 2
      // 5210: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5213: dup
      // 5214: ldc_w 1.125
      // 5217: ldc 25.0
      // 5219: fconst_0
      // 521a: fconst_0
      // 521b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 521e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5221: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5224: aastore
      // 5225: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5228: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 522b: ldc "left_leg"
      // 522d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5230: dup
      // 5231: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5234: bipush 4
      // 5235: anewarray 28
      // 5238: dup
      // 5239: bipush 0
      // 523a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 523d: dup
      // 523e: fconst_0
      // 523f: fconst_0
      // 5240: fconst_0
      // 5241: fconst_0
      // 5242: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5245: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5248: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 524b: aastore
      // 524c: dup
      // 524d: bipush 1
      // 524e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5251: dup
      // 5252: ldc 0.5
      // 5254: fconst_0
      // 5255: fconst_0
      // 5256: fconst_2
      // 5257: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 525a: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 525d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5260: aastore
      // 5261: dup
      // 5262: bipush 2
      // 5263: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5266: dup
      // 5267: ldc 0.75
      // 5269: fconst_0
      // 526a: fconst_0
      // 526b: ldc 3.0
      // 526d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5270: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5273: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5276: aastore
      // 5277: dup
      // 5278: bipush 3
      // 5279: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 527c: dup
      // 527d: ldc_w 1.125
      // 5280: fconst_0
      // 5281: ldc -1.0
      // 5283: ldc 3.0
      // 5285: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5288: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 528b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 528e: aastore
      // 528f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5292: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5295: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 5298: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.DEATH Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 529b: ldc 1.5
      // 529d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.withLength (F)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 52a0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.looping ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 52a3: ldc "body"
      // 52a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 52a8: dup
      // 52a9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 52ac: bipush 3
      // 52ad: anewarray 28
      // 52b0: dup
      // 52b1: bipush 0
      // 52b2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 52b5: dup
      // 52b6: fconst_0
      // 52b7: fconst_0
      // 52b8: fconst_0
      // 52b9: fconst_0
      // 52ba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52bd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 52c0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 52c3: aastore
      // 52c4: dup
      // 52c5: bipush 1
      // 52c6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 52c9: dup
      // 52ca: ldc_w 0.2917
      // 52cd: ldc_w 12.5
      // 52d0: fconst_0
      // 52d1: fconst_0
      // 52d2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52d5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 52d8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 52db: aastore
      // 52dc: dup
      // 52dd: bipush 2
      // 52de: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 52e1: dup
      // 52e2: ldc 1.5
      // 52e4: fconst_0
      // 52e5: fconst_0
      // 52e6: fconst_0
      // 52e7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 52ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 52ed: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 52f0: aastore
      // 52f1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 52f4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 52f7: ldc "body"
      // 52f9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 52fc: dup
      // 52fd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5300: bipush 3
      // 5301: anewarray 28
      // 5304: dup
      // 5305: bipush 0
      // 5306: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5309: dup
      // 530a: fconst_0
      // 530b: fconst_0
      // 530c: fconst_0
      // 530d: fconst_0
      // 530e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5311: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5314: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5317: aastore
      // 5318: dup
      // 5319: bipush 1
      // 531a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 531d: dup
      // 531e: ldc 0.625
      // 5320: fconst_0
      // 5321: ldc 3.0
      // 5323: ldc_w -3.0
      // 5326: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5329: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 532c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 532f: aastore
      // 5330: dup
      // 5331: bipush 2
      // 5332: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5335: dup
      // 5336: ldc 1.5
      // 5338: fconst_0
      // 5339: fconst_0
      // 533a: fconst_0
      // 533b: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 533e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5341: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5344: aastore
      // 5345: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5348: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 534b: ldc "chest"
      // 534d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5350: dup
      // 5351: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5354: bipush 4
      // 5355: anewarray 28
      // 5358: dup
      // 5359: bipush 0
      // 535a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 535d: dup
      // 535e: fconst_0
      // 535f: ldc_w -20.0
      // 5362: fconst_0
      // 5363: fconst_0
      // 5364: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5367: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 536a: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 536d: aastore
      // 536e: dup
      // 536f: bipush 1
      // 5370: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5373: dup
      // 5374: ldc_w 0.2917
      // 5377: ldc_w -20.0
      // 537a: fconst_0
      // 537b: fconst_0
      // 537c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 537f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5382: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5385: aastore
      // 5386: dup
      // 5387: bipush 2
      // 5388: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 538b: dup
      // 538c: ldc 0.625
      // 538e: ldc_w -12.5
      // 5391: fconst_0
      // 5392: fconst_0
      // 5393: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5396: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5399: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 539c: aastore
      // 539d: dup
      // 539e: bipush 3
      // 539f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 53a2: dup
      // 53a3: ldc 1.5
      // 53a5: ldc_w -20.0
      // 53a8: fconst_0
      // 53a9: fconst_0
      // 53aa: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53ad: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 53b0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 53b3: aastore
      // 53b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 53b7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 53ba: ldc "head"
      // 53bc: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 53bf: dup
      // 53c0: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 53c3: bipush 4
      // 53c4: anewarray 28
      // 53c7: dup
      // 53c8: bipush 0
      // 53c9: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 53cc: dup
      // 53cd: fconst_0
      // 53ce: fconst_0
      // 53cf: fconst_0
      // 53d0: fconst_0
      // 53d1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53d4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 53d7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 53da: aastore
      // 53db: dup
      // 53dc: bipush 1
      // 53dd: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 53e0: dup
      // 53e1: ldc_w 0.2917
      // 53e4: ldc_w -12.5
      // 53e7: fconst_0
      // 53e8: fconst_0
      // 53e9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 53ec: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 53ef: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 53f2: aastore
      // 53f3: dup
      // 53f4: bipush 2
      // 53f5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 53f8: dup
      // 53f9: ldc_w 1.0417
      // 53fc: ldc -5.0
      // 53fe: fconst_0
      // 53ff: fconst_0
      // 5400: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5403: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5406: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5409: aastore
      // 540a: dup
      // 540b: bipush 3
      // 540c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 540f: dup
      // 5410: ldc 1.5
      // 5412: fconst_0
      // 5413: fconst_0
      // 5414: fconst_0
      // 5415: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5418: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 541b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 541e: aastore
      // 541f: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5422: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5425: ldc "eye"
      // 5427: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 542a: dup
      // 542b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 542e: bipush 2
      // 542f: anewarray 28
      // 5432: dup
      // 5433: bipush 0
      // 5434: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5437: dup
      // 5438: fconst_0
      // 5439: fconst_0
      // 543a: fconst_0
      // 543b: fconst_0
      // 543c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 543f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5442: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5445: aastore
      // 5446: dup
      // 5447: bipush 1
      // 5448: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 544b: dup
      // 544c: ldc_w 0.9167
      // 544f: fconst_0
      // 5450: fconst_0
      // 5451: fconst_0
      // 5452: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5455: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5458: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 545b: aastore
      // 545c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 545f: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5462: ldc "right_arm"
      // 5464: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5467: dup
      // 5468: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 546b: bipush 7
      // 546d: anewarray 28
      // 5470: dup
      // 5471: bipush 0
      // 5472: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5475: dup
      // 5476: fconst_0
      // 5477: fconst_0
      // 5478: fconst_0
      // 5479: fconst_0
      // 547a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 547d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5480: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5483: aastore
      // 5484: dup
      // 5485: bipush 1
      // 5486: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5489: dup
      // 548a: ldc_w 0.2917
      // 548d: ldc_w -34.6557
      // 5490: ldc_w -2.1827
      // 5493: ldc_w -4.1648
      // 5496: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5499: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 549c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 549f: aastore
      // 54a0: dup
      // 54a1: bipush 2
      // 54a2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 54a5: dup
      // 54a6: ldc_w 0.4167
      // 54a9: ldc_w 120.0
      // 54ac: fconst_0
      // 54ad: fconst_0
      // 54ae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 54b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 54b7: aastore
      // 54b8: dup
      // 54b9: bipush 3
      // 54ba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 54bd: dup
      // 54be: ldc 0.625
      // 54c0: ldc_w 75.8945
      // 54c3: ldc_w 29.6972
      // 54c6: ldc_w 56.6636
      // 54c9: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54cc: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 54cf: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 54d2: aastore
      // 54d3: dup
      // 54d4: bipush 4
      // 54d5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 54d8: dup
      // 54d9: ldc_w 0.9167
      // 54dc: ldc_w 174.3839
      // 54df: ldc_w 3.8849
      // 54e2: ldc_w 54.4004
      // 54e5: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54e8: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 54eb: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 54ee: aastore
      // 54ef: dup
      // 54f0: bipush 5
      // 54f1: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 54f4: dup
      // 54f5: ldc 1.25
      // 54f7: ldc_w 29.31
      // 54fa: ldc_w 18.71
      // 54fd: ldc_w 39.41
      // 5500: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5503: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5506: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5509: aastore
      // 550a: dup
      // 550b: bipush 6
      // 550d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5510: dup
      // 5511: ldc 1.5
      // 5513: fconst_0
      // 5514: fconst_0
      // 5515: fconst_0
      // 5516: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5519: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 551c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 551f: aastore
      // 5520: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5523: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5526: ldc "right_arm2"
      // 5528: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 552b: dup
      // 552c: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 552f: bipush 3
      // 5530: anewarray 28
      // 5533: dup
      // 5534: bipush 0
      // 5535: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5538: dup
      // 5539: fconst_0
      // 553a: fconst_0
      // 553b: fconst_0
      // 553c: fconst_0
      // 553d: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5540: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5543: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5546: aastore
      // 5547: dup
      // 5548: bipush 1
      // 5549: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 554c: dup
      // 554d: ldc_w 0.2917
      // 5550: fconst_0
      // 5551: fconst_0
      // 5552: ldc_w 10.0
      // 5555: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5558: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 555b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 555e: aastore
      // 555f: dup
      // 5560: bipush 2
      // 5561: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5564: dup
      // 5565: ldc 1.5
      // 5567: fconst_0
      // 5568: fconst_0
      // 5569: fconst_0
      // 556a: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 556d: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5570: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5573: aastore
      // 5574: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5577: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 557a: ldc "left_arm"
      // 557c: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 557f: dup
      // 5580: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5583: bipush 7
      // 5585: anewarray 28
      // 5588: dup
      // 5589: bipush 0
      // 558a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 558d: dup
      // 558e: fconst_0
      // 558f: fconst_0
      // 5590: fconst_0
      // 5591: fconst_0
      // 5592: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5595: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5598: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 559b: aastore
      // 559c: dup
      // 559d: bipush 1
      // 559e: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 55a1: dup
      // 55a2: ldc_w 0.2917
      // 55a5: ldc_w -34.6557
      // 55a8: ldc_w 2.1827
      // 55ab: ldc_w 4.1648
      // 55ae: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55b1: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 55b4: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 55b7: aastore
      // 55b8: dup
      // 55b9: bipush 2
      // 55ba: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 55bd: dup
      // 55be: ldc_w 0.4167
      // 55c1: ldc_w 120.0
      // 55c4: fconst_0
      // 55c5: fconst_0
      // 55c6: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55c9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 55cc: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 55cf: aastore
      // 55d0: dup
      // 55d1: bipush 3
      // 55d2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 55d5: dup
      // 55d6: ldc 0.625
      // 55d8: ldc_w 75.8945
      // 55db: ldc_w -29.6972
      // 55de: ldc_w -56.6636
      // 55e1: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55e4: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 55e7: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 55ea: aastore
      // 55eb: dup
      // 55ec: bipush 4
      // 55ed: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 55f0: dup
      // 55f1: ldc_w 0.9167
      // 55f4: ldc_w 174.3839
      // 55f7: ldc_w -3.8849
      // 55fa: ldc_w -54.4004
      // 55fd: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5600: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5603: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5606: aastore
      // 5607: dup
      // 5608: bipush 5
      // 5609: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 560c: dup
      // 560d: ldc 1.25
      // 560f: ldc_w 29.3085
      // 5612: ldc_w -18.7068
      // 5615: ldc_w -39.4142
      // 5618: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 561b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 561e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5621: aastore
      // 5622: dup
      // 5623: bipush 6
      // 5625: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5628: dup
      // 5629: ldc 1.5
      // 562b: fconst_0
      // 562c: fconst_0
      // 562d: fconst_0
      // 562e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5631: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5634: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5637: aastore
      // 5638: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 563b: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 563e: ldc "left_arm2"
      // 5640: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5643: dup
      // 5644: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5647: bipush 3
      // 5648: anewarray 28
      // 564b: dup
      // 564c: bipush 0
      // 564d: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5650: dup
      // 5651: fconst_0
      // 5652: fconst_0
      // 5653: fconst_0
      // 5654: fconst_0
      // 5655: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5658: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 565b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 565e: aastore
      // 565f: dup
      // 5660: bipush 1
      // 5661: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5664: dup
      // 5665: ldc_w 0.2917
      // 5668: fconst_0
      // 5669: fconst_0
      // 566a: ldc -10.0
      // 566c: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 566f: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5672: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5675: aastore
      // 5676: dup
      // 5677: bipush 2
      // 5678: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 567b: dup
      // 567c: ldc 1.5
      // 567e: fconst_0
      // 567f: fconst_0
      // 5680: fconst_0
      // 5681: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5684: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5687: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 568a: aastore
      // 568b: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 568e: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5691: ldc "right_leg"
      // 5693: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 5696: dup
      // 5697: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 569a: bipush 3
      // 569b: anewarray 28
      // 569e: dup
      // 569f: bipush 0
      // 56a0: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 56a3: dup
      // 56a4: fconst_0
      // 56a5: fconst_0
      // 56a6: fconst_0
      // 56a7: fconst_0
      // 56a8: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56ab: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 56ae: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 56b1: aastore
      // 56b2: dup
      // 56b3: bipush 1
      // 56b4: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 56b7: dup
      // 56b8: ldc_w 0.4583
      // 56bb: ldc -10.0
      // 56bd: fconst_0
      // 56be: fconst_0
      // 56bf: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56c2: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 56c5: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 56c8: aastore
      // 56c9: dup
      // 56ca: bipush 2
      // 56cb: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 56ce: dup
      // 56cf: ldc 1.5
      // 56d1: fconst_0
      // 56d2: fconst_0
      // 56d3: fconst_0
      // 56d4: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56d7: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 56da: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 56dd: aastore
      // 56de: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 56e1: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 56e4: ldc "right_leg"
      // 56e6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 56e9: dup
      // 56ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 56ed: bipush 3
      // 56ee: anewarray 28
      // 56f1: dup
      // 56f2: bipush 0
      // 56f3: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 56f6: dup
      // 56f7: fconst_0
      // 56f8: fconst_0
      // 56f9: fconst_0
      // 56fa: fconst_0
      // 56fb: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 56fe: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5701: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5704: aastore
      // 5705: dup
      // 5706: bipush 1
      // 5707: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 570a: dup
      // 570b: ldc_w 0.9583
      // 570e: fconst_0
      // 570f: ldc 5.0
      // 5711: ldc -1.0
      // 5713: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5716: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5719: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 571c: aastore
      // 571d: dup
      // 571e: bipush 2
      // 571f: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5722: dup
      // 5723: ldc 1.5
      // 5725: fconst_0
      // 5726: fconst_0
      // 5727: fconst_0
      // 5728: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 572b: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 572e: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5731: aastore
      // 5732: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 5735: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 5738: ldc "left_leg"
      // 573a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 573d: dup
      // 573e: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.ROTATION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 5741: bipush 4
      // 5742: anewarray 28
      // 5745: dup
      // 5746: bipush 0
      // 5747: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 574a: dup
      // 574b: fconst_0
      // 574c: fconst_0
      // 574d: fconst_0
      // 574e: fconst_0
      // 574f: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5752: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5755: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5758: aastore
      // 5759: dup
      // 575a: bipush 1
      // 575b: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 575e: dup
      // 575f: ldc_w 0.4583
      // 5762: ldc -10.0
      // 5764: fconst_0
      // 5765: fconst_0
      // 5766: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5769: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 576c: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 576f: aastore
      // 5770: dup
      // 5771: bipush 2
      // 5772: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 5775: dup
      // 5776: ldc_w 0.9583
      // 5779: ldc_w 24.1
      // 577c: fconst_0
      // 577d: fconst_0
      // 577e: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5781: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5784: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 5787: aastore
      // 5788: dup
      // 5789: bipush 3
      // 578a: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 578d: dup
      // 578e: ldc 1.5
      // 5790: fconst_0
      // 5791: fconst_0
      // 5792: fconst_0
      // 5793: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5796: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 5799: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 579c: aastore
      // 579d: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 57a0: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 57a3: ldc "left_leg"
      // 57a5: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel
      // 57a8: dup
      // 57a9: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Targets.POSITION Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;
      // 57ac: bipush 3
      // 57ad: anewarray 28
      // 57b0: dup
      // 57b1: bipush 0
      // 57b2: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 57b5: dup
      // 57b6: fconst_0
      // 57b7: fconst_0
      // 57b8: fconst_0
      // 57b9: fconst_0
      // 57ba: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57bd: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 57c0: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 57c3: aastore
      // 57c4: dup
      // 57c5: bipush 1
      // 57c6: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 57c9: dup
      // 57ca: ldc_w 0.9583
      // 57cd: fconst_0
      // 57ce: ldc 5.0
      // 57d0: ldc -1.0
      // 57d2: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57d5: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 57d8: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 57db: aastore
      // 57dc: dup
      // 57dd: bipush 2
      // 57de: new com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe
      // 57e1: dup
      // 57e2: ldc 1.5
      // 57e4: fconst_0
      // 57e5: fconst_0
      // 57e6: fconst_0
      // 57e7: invokestatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 57ea: getstatic com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolations.CATMULLROM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;
      // 57ed: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe.<init> (FLorg/joml/Vector3f;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Interpolation;)V
      // 57f0: aastore
      // 57f1: invokespecial com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel.<init> (Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel$Target;[Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedKeyframe;)V
      // 57f4: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationChannel;)Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder;
      // 57f7: invokevirtual com/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition$Builder.build ()Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 57fa: putstatic com/github/L_Ender/cataclysm/client/animation/Coralssus_Animation.SWIM Lcom/github/L_Ender/lionfishapi/client/model/AdvancedAnimations/AdvancedAnimationDefinition;
      // 57fd: return
   }
}
