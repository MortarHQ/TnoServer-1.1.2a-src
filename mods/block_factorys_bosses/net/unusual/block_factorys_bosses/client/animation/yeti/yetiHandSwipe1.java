package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiHandSwipe1 {
   public static final AnimationDefinition HAND_SWIPE_1;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.createNewNode(SSAUConstructorSparseEx.java:198)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.createRead(SSAUConstructorSparseEx.java:191)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.varReadSingleVersion(SSAUConstructorSparseEx.java:104)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.varRead(SFormsConstructor.java:168)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.VarExprent.processSforms(VarExprent.java:574)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.Exprent.processSforms(Exprent.java:317)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.processSforms(InvocationExprent.java:1894)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:127)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.splitVariables(SFormsConstructor.java:95)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.splitVariables(SSAUConstructorSparseEx.java:43)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:238)
      //
      // Bytecode:
      // 0000: ldc 2.17
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "CTR_leg_left.002"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 11
      // 0013: anewarray 28
      // 0016: dup
      // 0017: bipush 0
      // 0018: new net/minecraft/client/animation/Keyframe
      // 001b: dup
      // 001c: ldc 0.25
      // 001e: ldc 2.113
      // 0020: fconst_0
      // 0021: ldc -13.058
      // 0023: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0026: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0029: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 002c: aastore
      // 002d: dup
      // 002e: bipush 1
      // 002f: new net/minecraft/client/animation/Keyframe
      // 0032: dup
      // 0033: ldc 0.29
      // 0035: ldc -1.561
      // 0037: ldc 1.593
      // 0039: ldc -9.166
      // 003b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 003e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0041: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0044: aastore
      // 0045: dup
      // 0046: bipush 2
      // 0047: new net/minecraft/client/animation/Keyframe
      // 004a: dup
      // 004b: ldc 0.33
      // 004d: ldc -5.235
      // 004f: ldc 3.186
      // 0051: ldc -1.976
      // 0053: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 005c: aastore
      // 005d: dup
      // 005e: bipush 3
      // 005f: new net/minecraft/client/animation/Keyframe
      // 0062: dup
      // 0063: ldc 0.38
      // 0065: ldc -3.295
      // 0067: fconst_0
      // 0068: ldc 1.849
      // 006a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 006d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0070: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0073: aastore
      // 0074: dup
      // 0075: bipush 4
      // 0076: new net/minecraft/client/animation/Keyframe
      // 0079: dup
      // 007a: ldc 1.21
      // 007c: ldc -3.295
      // 007e: fconst_0
      // 007f: ldc 1.849
      // 0081: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0084: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0087: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 008a: aastore
      // 008b: dup
      // 008c: bipush 5
      // 008d: new net/minecraft/client/animation/Keyframe
      // 0090: dup
      // 0091: ldc 1.25
      // 0093: ldc -2.838
      // 0095: fconst_0
      // 0096: ldc -4.002
      // 0098: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 009b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00a1: aastore
      // 00a2: dup
      // 00a3: bipush 6
      // 00a5: new net/minecraft/client/animation/Keyframe
      // 00a8: dup
      // 00a9: ldc 1.29
      // 00ab: ldc -1.99
      // 00ad: fconst_0
      // 00ae: ldc -14.869
      // 00b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b9: aastore
      // 00ba: dup
      // 00bb: bipush 7
      // 00bd: new net/minecraft/client/animation/Keyframe
      // 00c0: dup
      // 00c1: ldc 1.33
      // 00c3: ldc -1.534
      // 00c5: fconst_0
      // 00c6: ldc -20.721
      // 00c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00d1: aastore
      // 00d2: dup
      // 00d3: bipush 8
      // 00d5: new net/minecraft/client/animation/Keyframe
      // 00d8: dup
      // 00d9: ldc 1.83
      // 00db: ldc -1.534
      // 00dd: fconst_0
      // 00de: ldc -20.721
      // 00e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e9: aastore
      // 00ea: dup
      // 00eb: bipush 9
      // 00ed: new net/minecraft/client/animation/Keyframe
      // 00f0: dup
      // 00f1: ldc 1.88
      // 00f3: ldc 0.29
      // 00f5: fconst_0
      // 00f6: ldc -16.889
      // 00f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0101: aastore
      // 0102: dup
      // 0103: bipush 10
      // 0105: new net/minecraft/client/animation/Keyframe
      // 0108: dup
      // 0109: ldc 1.92
      // 010b: ldc 2.113
      // 010d: fconst_0
      // 010e: ldc -13.058
      // 0110: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0113: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0116: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0119: aastore
      // 011a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 011d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0120: ldc "CTR_hips"
      // 0122: new net/minecraft/client/animation/AnimationChannel
      // 0125: dup
      // 0126: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0129: bipush 52
      // 012b: anewarray 28
      // 012e: dup
      // 012f: bipush 0
      // 0130: new net/minecraft/client/animation/Keyframe
      // 0133: dup
      // 0134: fconst_0
      // 0135: ldc -0.727
      // 0137: ldc 0.028
      // 0139: ldc -4.943
      // 013b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 013e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0141: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0144: aastore
      // 0145: dup
      // 0146: bipush 1
      // 0147: new net/minecraft/client/animation/Keyframe
      // 014a: dup
      // 014b: ldc 0.04
      // 014d: ldc -0.664
      // 014f: ldc 0.967
      // 0151: ldc -1.686
      // 0153: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015c: aastore
      // 015d: dup
      // 015e: bipush 2
      // 015f: new net/minecraft/client/animation/Keyframe
      // 0162: dup
      // 0163: ldc 0.08
      // 0165: ldc -1.099
      // 0167: ldc 3.575
      // 0169: ldc 2.116
      // 016b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 016e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0171: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0174: aastore
      // 0175: dup
      // 0176: bipush 3
      // 0177: new net/minecraft/client/animation/Keyframe
      // 017a: dup
      // 017b: ldc 0.12
      // 017d: ldc -1.726
      // 017f: ldc 7.345
      // 0181: ldc 6.168
      // 0183: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0186: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0189: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 018c: aastore
      // 018d: dup
      // 018e: bipush 4
      // 018f: new net/minecraft/client/animation/Keyframe
      // 0192: dup
      // 0193: ldc 0.17
      // 0195: ldc -2.426
      // 0197: ldc 11.603
      // 0199: ldc 10.144
      // 019b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a4: aastore
      // 01a5: dup
      // 01a6: bipush 5
      // 01a7: new net/minecraft/client/animation/Keyframe
      // 01aa: dup
      // 01ab: ldc 0.21
      // 01ad: ldc -3.298
      // 01af: ldc 15.693
      // 01b1: ldc 13.751
      // 01b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01bc: aastore
      // 01bd: dup
      // 01be: bipush 6
      // 01c0: new net/minecraft/client/animation/Keyframe
      // 01c3: dup
      // 01c4: ldc 0.25
      // 01c6: ldc -4.569
      // 01c8: ldc 19.138
      // 01ca: ldc 16.738
      // 01cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d5: aastore
      // 01d6: dup
      // 01d7: bipush 7
      // 01d9: new net/minecraft/client/animation/Keyframe
      // 01dc: dup
      // 01dd: ldc 0.29
      // 01df: ldc -6.452
      // 01e1: ldc 21.684
      // 01e3: ldc 18.897
      // 01e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01ee: aastore
      // 01ef: dup
      // 01f0: bipush 8
      // 01f2: new net/minecraft/client/animation/Keyframe
      // 01f5: dup
      // 01f6: ldc 0.33
      // 01f8: ldc -9.046
      // 01fa: ldc 23.239
      // 01fc: ldc 20.092
      // 01fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0201: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0204: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0207: aastore
      // 0208: dup
      // 0209: bipush 9
      // 020b: new net/minecraft/client/animation/Keyframe
      // 020e: dup
      // 020f: ldc 0.38
      // 0211: ldc -12.296
      // 0213: ldc 23.796
      // 0215: ldc 20.279
      // 0217: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 021a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 021d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0220: aastore
      // 0221: dup
      // 0222: bipush 10
      // 0224: new net/minecraft/client/animation/Keyframe
      // 0227: dup
      // 0228: ldc 0.42
      // 022a: ldc -15.543
      // 022c: ldc 24.24
      // 022e: ldc 19.767
      // 0230: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0233: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0236: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0239: aastore
      // 023a: dup
      // 023b: bipush 11
      // 023d: new net/minecraft/client/animation/Keyframe
      // 0240: dup
      // 0241: ldc 0.46
      // 0243: ldc -18.108
      // 0245: ldc 25.061
      // 0247: ldc 19.151
      // 0249: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 024c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 024f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0252: aastore
      // 0253: dup
      // 0254: bipush 12
      // 0256: new net/minecraft/client/animation/Keyframe
      // 0259: dup
      // 025a: ldc 0.5
      // 025c: ldc -19.94
      // 025e: ldc 25.915
      // 0260: ldc 18.55
      // 0262: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0265: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0268: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 026b: aastore
      // 026c: dup
      // 026d: bipush 13
      // 026f: new net/minecraft/client/animation/Keyframe
      // 0272: dup
      // 0273: ldc 0.54
      // 0275: ldc -21.192
      // 0277: ldc 26.649
      // 0279: ldc 17.698
      // 027b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 027e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0281: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0284: aastore
      // 0285: dup
      // 0286: bipush 14
      // 0288: new net/minecraft/client/animation/Keyframe
      // 028b: dup
      // 028c: ldc 0.58
      // 028e: ldc -21.994
      // 0290: ldc 27.149
      // 0292: ldc 16.384
      // 0294: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0297: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 029a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029d: aastore
      // 029e: dup
      // 029f: bipush 15
      // 02a1: new net/minecraft/client/animation/Keyframe
      // 02a4: dup
      // 02a5: ldc 0.62
      // 02a7: ldc -22.102
      // 02a9: ldc 27.301
      // 02ab: ldc 14.44
      // 02ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b6: aastore
      // 02b7: dup
      // 02b8: bipush 16
      // 02ba: new net/minecraft/client/animation/Keyframe
      // 02bd: dup
      // 02be: ldc 0.67
      // 02c0: ldc -21.516
      // 02c2: ldc 26.983
      // 02c4: ldc 11.743
      // 02c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02cf: aastore
      // 02d0: dup
      // 02d1: bipush 17
      // 02d3: new net/minecraft/client/animation/Keyframe
      // 02d6: dup
      // 02d7: ldc 0.71
      // 02d9: ldc -20.675
      // 02db: ldc 26.043
      // 02dd: ldc 8.22
      // 02df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e8: aastore
      // 02e9: dup
      // 02ea: bipush 18
      // 02ec: new net/minecraft/client/animation/Keyframe
      // 02ef: dup
      // 02f0: ldc 0.75
      // 02f2: ldc -19.557
      // 02f4: ldc 24.501
      // 02f6: ldc 4.083
      // 02f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0301: aastore
      // 0302: dup
      // 0303: bipush 19
      // 0305: new net/minecraft/client/animation/Keyframe
      // 0308: dup
      // 0309: ldc 0.79
      // 030b: ldc -17.896
      // 030d: ldc 22.402
      // 030f: ldc -0.316
      // 0311: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0314: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0317: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 031a: aastore
      // 031b: dup
      // 031c: bipush 20
      // 031e: new net/minecraft/client/animation/Keyframe
      // 0321: dup
      // 0322: ldc 0.83
      // 0324: ldc -15.689
      // 0326: ldc 19.642
      // 0328: ldc -4.803
      // 032a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 032d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0330: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0333: aastore
      // 0334: dup
      // 0335: bipush 21
      // 0337: new net/minecraft/client/animation/Keyframe
      // 033a: dup
      // 033b: ldc 0.88
      // 033d: ldc -12.935
      // 033f: ldc 16.153
      // 0341: ldc -9.223
      // 0343: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0346: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0349: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 034c: aastore
      // 034d: dup
      // 034e: bipush 22
      // 0350: new net/minecraft/client/animation/Keyframe
      // 0353: dup
      // 0354: ldc 0.92
      // 0356: ldc -9.653
      // 0358: ldc 11.903
      // 035a: ldc -13.468
      // 035c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 035f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0362: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0365: aastore
      // 0366: dup
      // 0367: bipush 23
      // 0369: new net/minecraft/client/animation/Keyframe
      // 036c: dup
      // 036d: ldc 0.96
      // 036f: ldc -5.891
      // 0371: ldc 6.892
      // 0373: ldc -17.486
      // 0375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 037b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 037e: aastore
      // 037f: dup
      // 0380: bipush 24
      // 0382: new net/minecraft/client/animation/Keyframe
      // 0385: dup
      // 0386: fconst_1
      // 0387: ldc -1.72
      // 0389: ldc 1.142
      // 038b: ldc -21.288
      // 038d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0390: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0393: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0396: aastore
      // 0397: dup
      // 0398: bipush 25
      // 039a: new net/minecraft/client/animation/Keyframe
      // 039d: dup
      // 039e: ldc 1.04
      // 03a0: ldc 2.78
      // 03a2: ldc -5.311
      // 03a4: ldc -24.945
      // 03a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03af: aastore
      // 03b0: dup
      // 03b1: bipush 26
      // 03b3: new net/minecraft/client/animation/Keyframe
      // 03b6: dup
      // 03b7: ldc 1.08
      // 03b9: ldc 7.536
      // 03bb: ldc -12.423
      // 03bd: ldc -28.594
      // 03bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c8: aastore
      // 03c9: dup
      // 03ca: bipush 27
      // 03cc: new net/minecraft/client/animation/Keyframe
      // 03cf: dup
      // 03d0: ldc 1.12
      // 03d2: ldc 12.523
      // 03d4: ldc -20.139
      // 03d6: ldc -32.447
      // 03d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e1: aastore
      // 03e2: dup
      // 03e3: bipush 28
      // 03e5: new net/minecraft/client/animation/Keyframe
      // 03e8: dup
      // 03e9: ldc 1.17
      // 03eb: ldc 17.036
      // 03ed: ldc -28.135
      // 03ef: ldc -36.721
      // 03f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03fa: aastore
      // 03fb: dup
      // 03fc: bipush 29
      // 03fe: new net/minecraft/client/animation/Keyframe
      // 0401: dup
      // 0402: ldc 1.21
      // 0404: ldc 20.5
      // 0406: ldc -35.189
      // 0408: ldc -41.065
      // 040a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 040d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0410: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0413: aastore
      // 0414: dup
      // 0415: bipush 30
      // 0417: new net/minecraft/client/animation/Keyframe
      // 041a: dup
      // 041b: ldc 1.25
      // 041d: ldc 22.415
      // 041f: ldc -40.264
      // 0421: ldc -44.29
      // 0423: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0426: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0429: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 042c: aastore
      // 042d: dup
      // 042e: bipush 31
      // 0430: new net/minecraft/client/animation/Keyframe
      // 0433: dup
      // 0434: ldc 1.29
      // 0436: ldc 21.691
      // 0438: ldc -42.654
      // 043a: ldc -44.498
      // 043c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 043f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0445: aastore
      // 0446: dup
      // 0447: bipush 32
      // 0449: new net/minecraft/client/animation/Keyframe
      // 044c: dup
      // 044d: ldc 1.33
      // 044f: ldc 18.877
      // 0451: ldc -43.17
      // 0453: ldc -41.789
      // 0455: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0458: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 045b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 045e: aastore
      // 045f: dup
      // 0460: bipush 33
      // 0462: new net/minecraft/client/animation/Keyframe
      // 0465: dup
      // 0466: ldc 1.38
      // 0468: ldc 15.358
      // 046a: ldc -43.053
      // 046c: ldc -38.059
      // 046e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0471: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0474: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0477: aastore
      // 0478: dup
      // 0479: bipush 34
      // 047b: new net/minecraft/client/animation/Keyframe
      // 047e: dup
      // 047f: ldc 1.42
      // 0481: ldc 11.508
      // 0483: ldc -42.201
      // 0485: ldc -33.778
      // 0487: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 048a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 048d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0490: aastore
      // 0491: dup
      // 0492: bipush 35
      // 0494: new net/minecraft/client/animation/Keyframe
      // 0497: dup
      // 0498: ldc 1.46
      // 049a: ldc 7.662
      // 049c: ldc -40.589
      // 049e: ldc -29.288
      // 04a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a9: aastore
      // 04aa: dup
      // 04ab: bipush 36
      // 04ad: new net/minecraft/client/animation/Keyframe
      // 04b0: dup
      // 04b1: ldc 1.5
      // 04b3: ldc 4.099
      // 04b5: ldc -38.25
      // 04b7: ldc -24.853
      // 04b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04c2: aastore
      // 04c3: dup
      // 04c4: bipush 37
      // 04c6: new net/minecraft/client/animation/Keyframe
      // 04c9: dup
      // 04ca: ldc 1.54
      // 04cc: ldc 1.022
      // 04ce: ldc -35.263
      // 04d0: ldc -20.659
      // 04d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04db: aastore
      // 04dc: dup
      // 04dd: bipush 38
      // 04df: new net/minecraft/client/animation/Keyframe
      // 04e2: dup
      // 04e3: ldc 1.58
      // 04e5: ldc -1.445
      // 04e7: ldc -31.734
      // 04e9: ldc -16.818
      // 04eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f4: aastore
      // 04f5: dup
      // 04f6: bipush 39
      // 04f8: new net/minecraft/client/animation/Keyframe
      // 04fb: dup
      // 04fc: ldc 1.62
      // 04fe: ldc -3.244
      // 0500: ldc -27.786
      // 0502: ldc -13.39
      // 0504: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0507: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 050a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 050d: aastore
      // 050e: dup
      // 050f: bipush 40
      // 0511: new net/minecraft/client/animation/Keyframe
      // 0514: dup
      // 0515: ldc 1.67
      // 0517: ldc -4.372
      // 0519: ldc -23.548
      // 051b: ldc -10.398
      // 051d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0520: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0523: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0526: aastore
      // 0527: dup
      // 0528: bipush 41
      // 052a: new net/minecraft/client/animation/Keyframe
      // 052d: dup
      // 052e: ldc 1.71
      // 0530: ldc -4.86
      // 0532: ldc -19.149
      // 0534: ldc -7.859
      // 0536: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 053c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 053f: aastore
      // 0540: dup
      // 0541: bipush 42
      // 0543: new net/minecraft/client/animation/Keyframe
      // 0546: dup
      // 0547: ldc 1.75
      // 0549: ldc -4.767
      // 054b: ldc -14.719
      // 054d: ldc -5.799
      // 054f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0552: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0555: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0558: aastore
      // 0559: dup
      // 055a: bipush 43
      // 055c: new net/minecraft/client/animation/Keyframe
      // 055f: dup
      // 0560: ldc 1.79
      // 0562: ldc -4.178
      // 0564: ldc -10.397
      // 0566: ldc -4.283
      // 0568: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 056b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 056e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0571: aastore
      // 0572: dup
      // 0573: bipush 44
      // 0575: new net/minecraft/client/animation/Keyframe
      // 0578: dup
      // 0579: ldc 1.83
      // 057b: ldc -3.205
      // 057d: ldc -6.338
      // 057f: ldc_w -3.439
      // 0582: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0585: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0588: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 058b: aastore
      // 058c: dup
      // 058d: bipush 45
      // 058f: new net/minecraft/client/animation/Keyframe
      // 0592: dup
      // 0593: ldc 1.88
      // 0595: ldc_w -1.995
      // 0598: ldc_w -2.752
      // 059b: ldc_w -3.507
      // 059e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05a7: aastore
      // 05a8: dup
      // 05a9: bipush 46
      // 05ab: new net/minecraft/client/animation/Keyframe
      // 05ae: dup
      // 05af: ldc 1.92
      // 05b1: ldc -0.727
      // 05b3: ldc 0.028
      // 05b5: ldc -4.943
      // 05b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05c0: aastore
      // 05c1: dup
      // 05c2: bipush 47
      // 05c4: new net/minecraft/client/animation/Keyframe
      // 05c7: dup
      // 05c8: ldc_w 1.96
      // 05cb: ldc_w -0.025
      // 05ce: ldc_w 1.343
      // 05d1: ldc_w -6.358
      // 05d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05dd: aastore
      // 05de: dup
      // 05df: bipush 48
      // 05e1: new net/minecraft/client/animation/Keyframe
      // 05e4: dup
      // 05e5: fconst_2
      // 05e6: ldc_w -0.059
      // 05e9: ldc_w 1.199
      // 05ec: ldc_w -6.137
      // 05ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f8: aastore
      // 05f9: dup
      // 05fa: bipush 49
      // 05fc: new net/minecraft/client/animation/Keyframe
      // 05ff: dup
      // 0600: ldc_w 2.04
      // 0603: ldc -0.727
      // 0605: ldc 0.028
      // 0607: ldc -4.943
      // 0609: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 060c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 060f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0612: aastore
      // 0613: dup
      // 0614: bipush 50
      // 0616: new net/minecraft/client/animation/Keyframe
      // 0619: dup
      // 061a: ldc_w 2.08
      // 061d: ldc_w -1.187
      // 0620: ldc_w -0.658
      // 0623: ldc_w -4.326
      // 0626: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0629: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 062c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 062f: aastore
      // 0630: dup
      // 0631: bipush 51
      // 0633: new net/minecraft/client/animation/Keyframe
      // 0636: dup
      // 0637: ldc 2.17
      // 0639: ldc -0.727
      // 063b: ldc 0.028
      // 063d: ldc -4.943
      // 063f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0642: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0645: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0648: aastore
      // 0649: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 064c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 064f: ldc "CTR_hips"
      // 0651: new net/minecraft/client/animation/AnimationChannel
      // 0654: dup
      // 0655: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0658: bipush 49
      // 065a: anewarray 28
      // 065d: dup
      // 065e: bipush 0
      // 065f: new net/minecraft/client/animation/Keyframe
      // 0662: dup
      // 0663: fconst_0
      // 0664: fconst_0
      // 0665: fconst_0
      // 0666: fconst_0
      // 0667: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 066a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 066d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0670: aastore
      // 0671: dup
      // 0672: bipush 1
      // 0673: new net/minecraft/client/animation/Keyframe
      // 0676: dup
      // 0677: ldc 0.04
      // 0679: ldc_w -0.056
      // 067c: ldc_w 0.01
      // 067f: ldc_w 0.387
      // 0682: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0685: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0688: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 068b: aastore
      // 068c: dup
      // 068d: bipush 2
      // 068e: new net/minecraft/client/animation/Keyframe
      // 0691: dup
      // 0692: ldc 0.08
      // 0694: ldc_w -0.253
      // 0697: ldc_w 0.042
      // 069a: ldc_w 1.833
      // 069d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06a6: aastore
      // 06a7: dup
      // 06a8: bipush 3
      // 06a9: new net/minecraft/client/animation/Keyframe
      // 06ac: dup
      // 06ad: ldc 0.12
      // 06af: ldc_w -0.658
      // 06b2: ldc_w 0.091
      // 06b5: ldc_w 4.904
      // 06b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06c1: aastore
      // 06c2: dup
      // 06c3: bipush 4
      // 06c4: new net/minecraft/client/animation/Keyframe
      // 06c7: dup
      // 06c8: ldc 0.17
      // 06ca: ldc_w -1.399
      // 06cd: ldc_w 0.144
      // 06d0: ldc_w 9.617
      // 06d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06dc: aastore
      // 06dd: dup
      // 06de: bipush 5
      // 06df: new net/minecraft/client/animation/Keyframe
      // 06e2: dup
      // 06e3: ldc 0.21
      // 06e5: ldc_w -2.728
      // 06e8: ldc_w 0.186
      // 06eb: ldc_w 13.992
      // 06ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06f7: aastore
      // 06f8: dup
      // 06f9: bipush 6
      // 06fb: new net/minecraft/client/animation/Keyframe
      // 06fe: dup
      // 06ff: ldc 0.25
      // 0701: ldc_w -4.778
      // 0704: ldc_w 0.193
      // 0707: ldc_w 16.728
      // 070a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 070d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0710: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0713: aastore
      // 0714: dup
      // 0715: bipush 7
      // 0717: new net/minecraft/client/animation/Keyframe
      // 071a: dup
      // 071b: ldc 0.29
      // 071d: ldc_w -6.438
      // 0720: ldc_w 0.147
      // 0723: ldc_w 18.148
      // 0726: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0729: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 072c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 072f: aastore
      // 0730: dup
      // 0731: bipush 8
      // 0733: new net/minecraft/client/animation/Keyframe
      // 0736: dup
      // 0737: ldc 0.33
      // 0739: ldc_w -7.243
      // 073c: ldc_w 0.037
      // 073f: ldc_w 18.703
      // 0742: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0745: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0748: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 074b: aastore
      // 074c: dup
      // 074d: bipush 9
      // 074f: new net/minecraft/client/animation/Keyframe
      // 0752: dup
      // 0753: ldc 0.38
      // 0755: ldc_w -7.564
      // 0758: ldc_w -0.138
      // 075b: ldc_w 18.672
      // 075e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0761: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0764: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0767: aastore
      // 0768: dup
      // 0769: bipush 10
      // 076b: new net/minecraft/client/animation/Keyframe
      // 076e: dup
      // 076f: ldc 0.42
      // 0771: ldc_w -7.691
      // 0774: ldc_w -0.306
      // 0777: ldc_w 18.481
      // 077a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 077d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0780: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0783: aastore
      // 0784: dup
      // 0785: bipush 11
      // 0787: new net/minecraft/client/animation/Keyframe
      // 078a: dup
      // 078b: ldc 0.46
      // 078d: ldc_w -7.76
      // 0790: ldc_w -0.431
      // 0793: ldc_w 18.397
      // 0796: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0799: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 079c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 079f: aastore
      // 07a0: dup
      // 07a1: bipush 12
      // 07a3: new net/minecraft/client/animation/Keyframe
      // 07a6: dup
      // 07a7: ldc 0.5
      // 07a9: ldc_w -7.786
      // 07ac: ldc_w -0.532
      // 07af: ldc_w 18.376
      // 07b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07bb: aastore
      // 07bc: dup
      // 07bd: bipush 13
      // 07bf: new net/minecraft/client/animation/Keyframe
      // 07c2: dup
      // 07c3: ldc 0.54
      // 07c5: ldc_w -7.778
      // 07c8: ldc_w -0.617
      // 07cb: ldc_w 18.382
      // 07ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07d7: aastore
      // 07d8: dup
      // 07d9: bipush 14
      // 07db: new net/minecraft/client/animation/Keyframe
      // 07de: dup
      // 07df: ldc 0.58
      // 07e1: ldc_w -7.745
      // 07e4: ldc_w -0.688
      // 07e7: ldc_w 18.383
      // 07ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07f3: aastore
      // 07f4: dup
      // 07f5: bipush 15
      // 07f7: new net/minecraft/client/animation/Keyframe
      // 07fa: dup
      // 07fb: ldc 0.62
      // 07fd: ldc_w -7.693
      // 0800: ldc_w -0.749
      // 0803: ldc_w 18.348
      // 0806: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0809: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 080c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 080f: aastore
      // 0810: dup
      // 0811: bipush 16
      // 0813: new net/minecraft/client/animation/Keyframe
      // 0816: dup
      // 0817: ldc 0.67
      // 0819: ldc_w -7.626
      // 081c: ldc_w -0.801
      // 081f: ldc_w 18.246
      // 0822: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0825: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0828: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 082b: aastore
      // 082c: dup
      // 082d: bipush 17
      // 082f: new net/minecraft/client/animation/Keyframe
      // 0832: dup
      // 0833: ldc 0.71
      // 0835: ldc_w -7.549
      // 0838: ldc_w -0.843
      // 083b: ldc_w 18.047
      // 083e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0841: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0844: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0847: aastore
      // 0848: dup
      // 0849: bipush 18
      // 084b: new net/minecraft/client/animation/Keyframe
      // 084e: dup
      // 084f: ldc 0.75
      // 0851: ldc_w -7.464
      // 0854: ldc_w -0.877
      // 0857: ldc_w 17.714
      // 085a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 085d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0860: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0863: aastore
      // 0864: dup
      // 0865: bipush 19
      // 0867: new net/minecraft/client/animation/Keyframe
      // 086a: dup
      // 086b: ldc 0.79
      // 086d: ldc_w -7.374
      // 0870: ldc_w -0.901
      // 0873: ldc_w 17.208
      // 0876: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0879: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 087c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 087f: aastore
      // 0880: dup
      // 0881: bipush 20
      // 0883: new net/minecraft/client/animation/Keyframe
      // 0886: dup
      // 0887: ldc 0.83
      // 0889: ldc_w -7.283
      // 088c: ldc_w -0.916
      // 088f: ldc_w 16.481
      // 0892: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0895: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0898: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 089b: aastore
      // 089c: dup
      // 089d: bipush 21
      // 089f: new net/minecraft/client/animation/Keyframe
      // 08a2: dup
      // 08a3: ldc 0.88
      // 08a5: ldc_w -7.193
      // 08a8: ldc_w -0.921
      // 08ab: ldc_w 15.469
      // 08ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08b7: aastore
      // 08b8: dup
      // 08b9: bipush 22
      // 08bb: new net/minecraft/client/animation/Keyframe
      // 08be: dup
      // 08bf: ldc 0.92
      // 08c1: ldc_w -7.109
      // 08c4: ldc_w -0.914
      // 08c7: ldc_w 14.092
      // 08ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08d3: aastore
      // 08d4: dup
      // 08d5: bipush 23
      // 08d7: new net/minecraft/client/animation/Keyframe
      // 08da: dup
      // 08db: ldc 0.96
      // 08dd: ldc_w -7.033
      // 08e0: ldc_w -0.893
      // 08e3: ldc_w 12.234
      // 08e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ef: aastore
      // 08f0: dup
      // 08f1: bipush 24
      // 08f3: new net/minecraft/client/animation/Keyframe
      // 08f6: dup
      // 08f7: fconst_1
      // 08f8: ldc_w -6.968
      // 08fb: ldc_w -0.854
      // 08fe: ldc_w 9.719
      // 0901: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0904: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0907: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 090a: aastore
      // 090b: dup
      // 090c: bipush 25
      // 090e: new net/minecraft/client/animation/Keyframe
      // 0911: dup
      // 0912: ldc 1.04
      // 0914: ldc_w -6.918
      // 0917: ldc_w -0.792
      // 091a: ldc_w 6.254
      // 091d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0920: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0923: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0926: aastore
      // 0927: dup
      // 0928: bipush 26
      // 092a: new net/minecraft/client/animation/Keyframe
      // 092d: dup
      // 092e: ldc 1.08
      // 0930: ldc_w -6.885
      // 0933: ldc_w -0.694
      // 0936: ldc_w 1.271
      // 0939: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 093c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 093f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0942: aastore
      // 0943: dup
      // 0944: bipush 27
      // 0946: new net/minecraft/client/animation/Keyframe
      // 0949: dup
      // 094a: ldc 1.12
      // 094c: ldc_w -6.874
      // 094f: ldc_w -0.528
      // 0952: ldc_w -6.71
      // 0955: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0958: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 095b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 095e: aastore
      // 095f: dup
      // 0960: bipush 28
      // 0962: new net/minecraft/client/animation/Keyframe
      // 0965: dup
      // 0966: ldc 1.17
      // 0968: ldc_w -6.881
      // 096b: ldc_w -0.279
      // 096e: ldc_w -14.151
      // 0971: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 097a: aastore
      // 097b: dup
      // 097c: bipush 29
      // 097e: new net/minecraft/client/animation/Keyframe
      // 0981: dup
      // 0982: ldc 1.21
      // 0984: ldc_w -6.896
      // 0987: ldc_w -0.012
      // 098a: ldc_w -17.87
      // 098d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0990: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0993: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0996: aastore
      // 0997: dup
      // 0998: bipush 30
      // 099a: new net/minecraft/client/animation/Keyframe
      // 099d: dup
      // 099e: ldc 1.25
      // 09a0: ldc_w -6.911
      // 09a3: ldc_w 0.237
      // 09a6: ldc_w -19.76
      // 09a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09b2: aastore
      // 09b3: dup
      // 09b4: bipush 31
      // 09b6: new net/minecraft/client/animation/Keyframe
      // 09b9: dup
      // 09ba: ldc 1.29
      // 09bc: ldc_w -6.918
      // 09bf: ldc_w 0.433
      // 09c2: ldc_w -20.741
      // 09c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ce: aastore
      // 09cf: dup
      // 09d0: bipush 32
      // 09d2: new net/minecraft/client/animation/Keyframe
      // 09d5: dup
      // 09d6: ldc 1.33
      // 09d8: ldc_w -6.837
      // 09db: ldc_w 0.547
      // 09de: ldc_w -21.158
      // 09e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 09e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09ea: aastore
      // 09eb: dup
      // 09ec: bipush 33
      // 09ee: new net/minecraft/client/animation/Keyframe
      // 09f1: dup
      // 09f2: ldc 1.38
      // 09f4: ldc_w -6.614
      // 09f7: ldc_w 0.602
      // 09fa: ldc_w -20.962
      // 09fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a06: aastore
      // 0a07: dup
      // 0a08: bipush 34
      // 0a0a: new net/minecraft/client/animation/Keyframe
      // 0a0d: dup
      // 0a0e: ldc 1.42
      // 0a10: ldc_w -6.279
      // 0a13: ldc_w 0.621
      // 0a16: ldc_w -20.273
      // 0a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a22: aastore
      // 0a23: dup
      // 0a24: bipush 35
      // 0a26: new net/minecraft/client/animation/Keyframe
      // 0a29: dup
      // 0a2a: ldc 1.46
      // 0a2c: ldc_w -5.853
      // 0a2f: ldc_w 0.615
      // 0a32: ldc_w -19.183
      // 0a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a3e: aastore
      // 0a3f: dup
      // 0a40: bipush 36
      // 0a42: new net/minecraft/client/animation/Keyframe
      // 0a45: dup
      // 0a46: ldc 1.5
      // 0a48: ldc_w -5.355
      // 0a4b: ldc_w 0.59
      // 0a4e: ldc_w -17.771
      // 0a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a5a: aastore
      // 0a5b: dup
      // 0a5c: bipush 37
      // 0a5e: new net/minecraft/client/animation/Keyframe
      // 0a61: dup
      // 0a62: ldc 1.54
      // 0a64: ldc_w -4.8
      // 0a67: ldc_w 0.551
      // 0a6a: ldc_w -16.1
      // 0a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a76: aastore
      // 0a77: dup
      // 0a78: bipush 38
      // 0a7a: new net/minecraft/client/animation/Keyframe
      // 0a7d: dup
      // 0a7e: ldc 1.58
      // 0a80: ldc_w -4.203
      // 0a83: ldc_w 0.502
      // 0a86: ldc_w -14.231
      // 0a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a92: aastore
      // 0a93: dup
      // 0a94: bipush 39
      // 0a96: new net/minecraft/client/animation/Keyframe
      // 0a99: dup
      // 0a9a: ldc 1.62
      // 0a9c: ldc_w -3.579
      // 0a9f: ldc_w 0.445
      // 0aa2: ldc_w -12.218
      // 0aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aae: aastore
      // 0aaf: dup
      // 0ab0: bipush 40
      // 0ab2: new net/minecraft/client/animation/Keyframe
      // 0ab5: dup
      // 0ab6: ldc 1.67
      // 0ab8: ldc_w -2.941
      // 0abb: ldc_w 0.381
      // 0abe: ldc_w -10.116
      // 0ac1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ac4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ac7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aca: aastore
      // 0acb: dup
      // 0acc: bipush 41
      // 0ace: new net/minecraft/client/animation/Keyframe
      // 0ad1: dup
      // 0ad2: ldc 1.71
      // 0ad4: ldc_w -2.304
      // 0ad7: ldc_w 0.313
      // 0ada: ldc_w -7.984
      // 0add: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0ae0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ae3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ae6: aastore
      // 0ae7: dup
      // 0ae8: bipush 42
      // 0aea: new net/minecraft/client/animation/Keyframe
      // 0aed: dup
      // 0aee: ldc 1.75
      // 0af0: ldc_w -1.688
      // 0af3: ldc_w 0.243
      // 0af6: ldc_w -5.887
      // 0af9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0afc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b02: aastore
      // 0b03: dup
      // 0b04: bipush 43
      // 0b06: new net/minecraft/client/animation/Keyframe
      // 0b09: dup
      // 0b0a: ldc 1.79
      // 0b0c: ldc_w -1.112
      // 0b0f: ldc_w 0.173
      // 0b12: ldc_w -3.905
      // 0b15: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b1e: aastore
      // 0b1f: dup
      // 0b20: bipush 44
      // 0b22: new net/minecraft/client/animation/Keyframe
      // 0b25: dup
      // 0b26: ldc 1.83
      // 0b28: ldc_w -0.606
      // 0b2b: ldc_w 0.105
      // 0b2e: ldc_w -2.145
      // 0b31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b3a: aastore
      // 0b3b: dup
      // 0b3c: bipush 45
      // 0b3e: new net/minecraft/client/animation/Keyframe
      // 0b41: dup
      // 0b42: ldc 1.88
      // 0b44: ldc_w -0.213
      // 0b47: ldc_w 0.044
      // 0b4a: ldc_w -0.76
      // 0b4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b56: aastore
      // 0b57: dup
      // 0b58: bipush 46
      // 0b5a: new net/minecraft/client/animation/Keyframe
      // 0b5d: dup
      // 0b5e: ldc 1.92
      // 0b60: fconst_0
      // 0b61: fconst_0
      // 0b62: fconst_0
      // 0b63: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b6c: aastore
      // 0b6d: dup
      // 0b6e: bipush 47
      // 0b70: new net/minecraft/client/animation/Keyframe
      // 0b73: dup
      // 0b74: ldc_w 1.96
      // 0b77: ldc_w 0.038
      // 0b7a: ldc_w -0.019
      // 0b7d: ldc_w 0.141
      // 0b80: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b89: aastore
      // 0b8a: dup
      // 0b8b: bipush 48
      // 0b8d: new net/minecraft/client/animation/Keyframe
      // 0b90: dup
      // 0b91: ldc_w 2.04
      // 0b94: fconst_0
      // 0b95: fconst_0
      // 0b96: fconst_0
      // 0b97: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba0: aastore
      // 0ba1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0ba4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0ba7: ldc_w "CTR_chest"
      // 0baa: new net/minecraft/client/animation/AnimationChannel
      // 0bad: dup
      // 0bae: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0bb1: bipush 53
      // 0bb3: anewarray 28
      // 0bb6: dup
      // 0bb7: bipush 0
      // 0bb8: new net/minecraft/client/animation/Keyframe
      // 0bbb: dup
      // 0bbc: fconst_0
      // 0bbd: ldc_w 0.692
      // 0bc0: ldc_w 4.888
      // 0bc3: ldc_w 0.106
      // 0bc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bcf: aastore
      // 0bd0: dup
      // 0bd1: bipush 1
      // 0bd2: new net/minecraft/client/animation/Keyframe
      // 0bd5: dup
      // 0bd6: ldc 0.04
      // 0bd8: ldc_w 1.254
      // 0bdb: ldc_w 4.678
      // 0bde: ldc_w 3.556
      // 0be1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0be4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bea: aastore
      // 0beb: dup
      // 0bec: bipush 2
      // 0bed: new net/minecraft/client/animation/Keyframe
      // 0bf0: dup
      // 0bf1: ldc 0.08
      // 0bf3: ldc_w 2.412
      // 0bf6: ldc_w 3.986
      // 0bf9: ldc_w 11.756
      // 0bfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c05: aastore
      // 0c06: dup
      // 0c07: bipush 3
      // 0c08: new net/minecraft/client/animation/Keyframe
      // 0c0b: dup
      // 0c0c: ldc 0.12
      // 0c0e: ldc_w 3.34
      // 0c11: ldc_w 2.83
      // 0c14: ldc_w 21.483
      // 0c17: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c20: aastore
      // 0c21: dup
      // 0c22: bipush 4
      // 0c23: new net/minecraft/client/animation/Keyframe
      // 0c26: dup
      // 0c27: ldc 0.17
      // 0c29: ldc_w 3.364
      // 0c2c: ldc_w 1.545
      // 0c2f: ldc_w 29.567
      // 0c32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c3b: aastore
      // 0c3c: dup
      // 0c3d: bipush 5
      // 0c3e: new net/minecraft/client/animation/Keyframe
      // 0c41: dup
      // 0c42: ldc 0.21
      // 0c44: ldc_w 2.101
      // 0c47: ldc_w 0.638
      // 0c4a: ldc_w 32.907
      // 0c4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c56: aastore
      // 0c57: dup
      // 0c58: bipush 6
      // 0c5a: new net/minecraft/client/animation/Keyframe
      // 0c5d: dup
      // 0c5e: ldc 0.25
      // 0c60: ldc_w -0.287
      // 0c63: ldc_w 0.132
      // 0c66: ldc_w 31.798
      // 0c69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c72: aastore
      // 0c73: dup
      // 0c74: bipush 7
      // 0c76: new net/minecraft/client/animation/Keyframe
      // 0c79: dup
      // 0c7a: ldc 0.29
      // 0c7c: ldc_w -3.465
      // 0c7f: ldc_w -0.302
      // 0c82: ldc_w 28.913
      // 0c85: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c8e: aastore
      // 0c8f: dup
      // 0c90: bipush 8
      // 0c92: new net/minecraft/client/animation/Keyframe
      // 0c95: dup
      // 0c96: ldc 0.33
      // 0c98: ldc_w -7.341
      // 0c9b: ldc_w -0.745
      // 0c9e: ldc_w 24.625
      // 0ca1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ca4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ca7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0caa: aastore
      // 0cab: dup
      // 0cac: bipush 9
      // 0cae: new net/minecraft/client/animation/Keyframe
      // 0cb1: dup
      // 0cb2: ldc 0.38
      // 0cb4: ldc_w -11.612
      // 0cb7: ldc_w -1.248
      // 0cba: ldc_w 19.212
      // 0cbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc6: aastore
      // 0cc7: dup
      // 0cc8: bipush 10
      // 0cca: new net/minecraft/client/animation/Keyframe
      // 0ccd: dup
      // 0cce: ldc 0.42
      // 0cd0: ldc_w -15.711
      // 0cd3: ldc_w -1.832
      // 0cd6: ldc_w 12.89
      // 0cd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ce2: aastore
      // 0ce3: dup
      // 0ce4: bipush 11
      // 0ce6: new net/minecraft/client/animation/Keyframe
      // 0ce9: dup
      // 0cea: ldc 0.46
      // 0cec: ldc_w -19.128
      // 0cef: ldc_w -2.488
      // 0cf2: ldc_w 5.837
      // 0cf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cfe: aastore
      // 0cff: dup
      // 0d00: bipush 12
      // 0d02: new net/minecraft/client/animation/Keyframe
      // 0d05: dup
      // 0d06: ldc 0.5
      // 0d08: ldc_w -21.686
      // 0d0b: ldc_w -3.185
      // 0d0e: ldc_w -1.797
      // 0d11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d1a: aastore
      // 0d1b: dup
      // 0d1c: bipush 13
      // 0d1e: new net/minecraft/client/animation/Keyframe
      // 0d21: dup
      // 0d22: ldc 0.54
      // 0d24: ldc_w -23.439
      // 0d27: ldc_w -3.873
      // 0d2a: ldc_w -9.877
      // 0d2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d36: aastore
      // 0d37: dup
      // 0d38: bipush 14
      // 0d3a: new net/minecraft/client/animation/Keyframe
      // 0d3d: dup
      // 0d3e: ldc 0.58
      // 0d40: ldc_w -24.068
      // 0d43: ldc_w -4.498
      // 0d46: ldc_w -18.268
      // 0d49: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d52: aastore
      // 0d53: dup
      // 0d54: bipush 15
      // 0d56: new net/minecraft/client/animation/Keyframe
      // 0d59: dup
      // 0d5a: ldc 0.62
      // 0d5c: ldc_w -23.229
      // 0d5f: ldc_w -5.013
      // 0d62: ldc_w -26.827
      // 0d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d6e: aastore
      // 0d6f: dup
      // 0d70: bipush 16
      // 0d72: new net/minecraft/client/animation/Keyframe
      // 0d75: dup
      // 0d76: ldc 0.67
      // 0d78: ldc_w -20.969
      // 0d7b: ldc_w -5.385
      // 0d7e: ldc_w -35.38
      // 0d81: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d84: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d87: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d8a: aastore
      // 0d8b: dup
      // 0d8c: bipush 17
      // 0d8e: new net/minecraft/client/animation/Keyframe
      // 0d91: dup
      // 0d92: ldc 0.71
      // 0d94: ldc_w -17.272
      // 0d97: ldc_w -5.604
      // 0d9a: ldc_w -43.685
      // 0d9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0da0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0da6: aastore
      // 0da7: dup
      // 0da8: bipush 18
      // 0daa: new net/minecraft/client/animation/Keyframe
      // 0dad: dup
      // 0dae: ldc 0.75
      // 0db0: ldc_w -12.03
      // 0db3: ldc_w -5.681
      // 0db6: ldc_w -51.36
      // 0db9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dc2: aastore
      // 0dc3: dup
      // 0dc4: bipush 19
      // 0dc6: new net/minecraft/client/animation/Keyframe
      // 0dc9: dup
      // 0dca: ldc 0.79
      // 0dcc: ldc_w -4.988
      // 0dcf: ldc_w -5.637
      // 0dd2: ldc_w -57.66
      // 0dd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ddb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dde: aastore
      // 0ddf: dup
      // 0de0: bipush 20
      // 0de2: new net/minecraft/client/animation/Keyframe
      // 0de5: dup
      // 0de6: ldc 0.83
      // 0de8: ldc_w 4.98
      // 0deb: ldc_w -6.069
      // 0dee: ldc_w -61.887
      // 0df1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0df4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0df7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dfa: aastore
      // 0dfb: dup
      // 0dfc: bipush 21
      // 0dfe: new net/minecraft/client/animation/Keyframe
      // 0e01: dup
      // 0e02: ldc 0.88
      // 0e04: ldc_w 18.371
      // 0e07: ldc_w -7.48
      // 0e0a: ldc_w -63.889
      // 0e0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e16: aastore
      // 0e17: dup
      // 0e18: bipush 22
      // 0e1a: new net/minecraft/client/animation/Keyframe
      // 0e1d: dup
      // 0e1e: ldc 0.92
      // 0e20: ldc_w 33.907
      // 0e23: ldc_w -10.001
      // 0e26: ldc_w -62.9
      // 0e29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e32: aastore
      // 0e33: dup
      // 0e34: bipush 23
      // 0e36: new net/minecraft/client/animation/Keyframe
      // 0e39: dup
      // 0e3a: ldc 0.96
      // 0e3c: ldc_w 46.948
      // 0e3f: ldc_w -14.284
      // 0e42: ldc_w -57.627
      // 0e45: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e4e: aastore
      // 0e4f: dup
      // 0e50: bipush 24
      // 0e52: new net/minecraft/client/animation/Keyframe
      // 0e55: dup
      // 0e56: fconst_1
      // 0e57: ldc_w 48.621
      // 0e5a: ldc_w -21.338
      // 0e5d: ldc_w -45.552
      // 0e60: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e69: aastore
      // 0e6a: dup
      // 0e6b: bipush 25
      // 0e6d: new net/minecraft/client/animation/Keyframe
      // 0e70: dup
      // 0e71: ldc 1.04
      // 0e73: ldc_w 38.833
      // 0e76: ldc_w -30.356
      // 0e79: ldc_w -22.243
      // 0e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e85: aastore
      // 0e86: dup
      // 0e87: bipush 26
      // 0e89: new net/minecraft/client/animation/Keyframe
      // 0e8c: dup
      // 0e8d: ldc 1.08
      // 0e8f: ldc_w 19.722
      // 0e92: ldc_w -34.25
      // 0e95: ldc_w 8.481
      // 0e98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea1: aastore
      // 0ea2: dup
      // 0ea3: bipush 27
      // 0ea5: new net/minecraft/client/animation/Keyframe
      // 0ea8: dup
      // 0ea9: ldc 1.12
      // 0eab: ldc_w 7.276
      // 0eae: ldc_w -31.87
      // 0eb1: ldc_w 27.637
      // 0eb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0eba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ebd: aastore
      // 0ebe: dup
      // 0ebf: bipush 28
      // 0ec1: new net/minecraft/client/animation/Keyframe
      // 0ec4: dup
      // 0ec5: ldc 1.17
      // 0ec7: ldc_w 3.397
      // 0eca: ldc_w -29.754
      // 0ecd: ldc_w 34.936
      // 0ed0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ed3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ed6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ed9: aastore
      // 0eda: dup
      // 0edb: bipush 29
      // 0edd: new net/minecraft/client/animation/Keyframe
      // 0ee0: dup
      // 0ee1: ldc 1.21
      // 0ee3: ldc_w 3.067
      // 0ee6: ldc_w -29.6
      // 0ee9: ldc_w 34.823
      // 0eec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ef5: aastore
      // 0ef6: dup
      // 0ef7: bipush 30
      // 0ef9: new net/minecraft/client/animation/Keyframe
      // 0efc: dup
      // 0efd: ldc 1.25
      // 0eff: ldc_w 5.855
      // 0f02: ldc_w -30.254
      // 0f05: ldc_w 28.073
      // 0f08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f11: aastore
      // 0f12: dup
      // 0f13: bipush 31
      // 0f15: new net/minecraft/client/animation/Keyframe
      // 0f18: dup
      // 0f19: ldc 1.29
      // 0f1b: ldc_w 11.074
      // 0f1e: ldc_w -29.392
      // 0f21: ldc_w 15.051
      // 0f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f2d: aastore
      // 0f2e: dup
      // 0f2f: bipush 32
      // 0f31: new net/minecraft/client/animation/Keyframe
      // 0f34: dup
      // 0f35: ldc 1.33
      // 0f37: ldc_w 13.2
      // 0f3a: ldc_w -26.108
      // 0f3d: ldc_w 5.966
      // 0f40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f49: aastore
      // 0f4a: dup
      // 0f4b: bipush 33
      // 0f4d: new net/minecraft/client/animation/Keyframe
      // 0f50: dup
      // 0f51: ldc 1.38
      // 0f53: ldc_w 12.386
      // 0f56: ldc_w -22.777
      // 0f59: ldc_w 2.194
      // 0f5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f65: aastore
      // 0f66: dup
      // 0f67: bipush 34
      // 0f69: new net/minecraft/client/animation/Keyframe
      // 0f6c: dup
      // 0f6d: ldc 1.42
      // 0f6f: ldc_w 10.534
      // 0f72: ldc_w -19.671
      // 0f75: ldc_w 0.383
      // 0f78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f81: aastore
      // 0f82: dup
      // 0f83: bipush 35
      // 0f85: new net/minecraft/client/animation/Keyframe
      // 0f88: dup
      // 0f89: ldc 1.46
      // 0f8b: ldc_w 8.24
      // 0f8e: ldc_w -16.775
      // 0f91: ldc_w -0.391
      // 0f94: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f9d: aastore
      // 0f9e: dup
      // 0f9f: bipush 36
      // 0fa1: new net/minecraft/client/animation/Keyframe
      // 0fa4: dup
      // 0fa5: ldc 1.5
      // 0fa7: ldc_w 5.806
      // 0faa: ldc_w -14.06
      // 0fad: ldc_w -0.54
      // 0fb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fb9: aastore
      // 0fba: dup
      // 0fbb: bipush 37
      // 0fbd: new net/minecraft/client/animation/Keyframe
      // 0fc0: dup
      // 0fc1: ldc 1.54
      // 0fc3: ldc_w 3.424
      // 0fc6: ldc_w -11.502
      // 0fc9: ldc_w -0.293
      // 0fcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd5: aastore
      // 0fd6: dup
      // 0fd7: bipush 38
      // 0fd9: new net/minecraft/client/animation/Keyframe
      // 0fdc: dup
      // 0fdd: ldc 1.58
      // 0fdf: ldc_w 1.233
      // 0fe2: ldc_w -9.089
      // 0fe5: ldc_w 0.205
      // 0fe8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0feb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ff1: aastore
      // 0ff2: dup
      // 0ff3: bipush 39
      // 0ff5: new net/minecraft/client/animation/Keyframe
      // 0ff8: dup
      // 0ff9: ldc 1.62
      // 0ffb: ldc_w -0.658
      // 0ffe: ldc_w -6.812
      // 1001: ldc_w 0.847
      // 1004: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1007: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 100a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 100d: aastore
      // 100e: dup
      // 100f: bipush 40
      // 1011: new net/minecraft/client/animation/Keyframe
      // 1014: dup
      // 1015: ldc 1.67
      // 1017: ldc_w -2.155
      // 101a: ldc_w -4.669
      // 101d: ldc_w 1.543
      // 1020: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1023: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1026: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1029: aastore
      // 102a: dup
      // 102b: bipush 41
      // 102d: new net/minecraft/client/animation/Keyframe
      // 1030: dup
      // 1031: ldc 1.71
      // 1033: ldc_w -3.178
      // 1036: ldc_w -2.661
      // 1039: ldc_w 2.208
      // 103c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 103f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1042: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1045: aastore
      // 1046: dup
      // 1047: bipush 42
      // 1049: new net/minecraft/client/animation/Keyframe
      // 104c: dup
      // 104d: ldc 1.75
      // 104f: ldc_w -3.657
      // 1052: ldc_w -0.794
      // 1055: ldc_w 2.748
      // 1058: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 105b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 105e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1061: aastore
      // 1062: dup
      // 1063: bipush 43
      // 1065: new net/minecraft/client/animation/Keyframe
      // 1068: dup
      // 1069: ldc 1.79
      // 106b: ldc_w -3.535
      // 106e: ldc_w 0.925
      // 1071: ldc_w 3.043
      // 1074: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1077: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 107a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107d: aastore
      // 107e: dup
      // 107f: bipush 44
      // 1081: new net/minecraft/client/animation/Keyframe
      // 1084: dup
      // 1085: ldc 1.83
      // 1087: ldc_w -2.768
      // 108a: ldc_w 2.477
      // 108d: ldc_w 2.926
      // 1090: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1093: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1096: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1099: aastore
      // 109a: dup
      // 109b: bipush 45
      // 109d: new net/minecraft/client/animation/Keyframe
      // 10a0: dup
      // 10a1: ldc 1.88
      // 10a3: ldc_w -1.344
      // 10a6: ldc_w 3.826
      // 10a9: ldc_w 2.124
      // 10ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b5: aastore
      // 10b6: dup
      // 10b7: bipush 46
      // 10b9: new net/minecraft/client/animation/Keyframe
      // 10bc: dup
      // 10bd: ldc 1.92
      // 10bf: ldc_w 0.692
      // 10c2: ldc_w 4.888
      // 10c5: ldc_w 0.106
      // 10c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10d1: aastore
      // 10d2: dup
      // 10d3: bipush 47
      // 10d5: new net/minecraft/client/animation/Keyframe
      // 10d8: dup
      // 10d9: ldc_w 1.96
      // 10dc: ldc_w 2.456
      // 10df: ldc_w 5.454
      // 10e2: ldc_w -2.099
      // 10e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10ee: aastore
      // 10ef: dup
      // 10f0: bipush 48
      // 10f2: new net/minecraft/client/animation/Keyframe
      // 10f5: dup
      // 10f6: fconst_2
      // 10f7: ldc_w 2.875
      // 10fa: ldc_w 5.537
      // 10fd: ldc_w -2.59
      // 1100: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1103: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1106: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1109: aastore
      // 110a: dup
      // 110b: bipush 49
      // 110d: new net/minecraft/client/animation/Keyframe
      // 1110: dup
      // 1111: ldc_w 2.04
      // 1114: ldc_w 1.84
      // 1117: ldc_w 5.294
      // 111a: ldc_w -1.507
      // 111d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1120: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1123: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1126: aastore
      // 1127: dup
      // 1128: bipush 50
      // 112a: new net/minecraft/client/animation/Keyframe
      // 112d: dup
      // 112e: ldc_w 2.08
      // 1131: ldc_w 0.692
      // 1134: ldc_w 4.888
      // 1137: ldc_w 0.106
      // 113a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 113d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1140: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1143: aastore
      // 1144: dup
      // 1145: bipush 51
      // 1147: new net/minecraft/client/animation/Keyframe
      // 114a: dup
      // 114b: ldc_w 2.12
      // 114e: ldc_w 0.398
      // 1151: ldc_w 4.697
      // 1154: ldc_w 0.749
      // 1157: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 115a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 115d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1160: aastore
      // 1161: dup
      // 1162: bipush 52
      // 1164: new net/minecraft/client/animation/Keyframe
      // 1167: dup
      // 1168: ldc 2.17
      // 116a: ldc_w 0.546
      // 116d: ldc_w 4.793
      // 1170: ldc_w 0.428
      // 1173: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1176: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1179: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 117c: aastore
      // 117d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1180: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1183: ldc_w "CTR_head"
      // 1186: new net/minecraft/client/animation/AnimationChannel
      // 1189: dup
      // 118a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 118d: bipush 53
      // 118f: anewarray 28
      // 1192: dup
      // 1193: bipush 0
      // 1194: new net/minecraft/client/animation/Keyframe
      // 1197: dup
      // 1198: fconst_0
      // 1199: ldc_w 0.412
      // 119c: ldc_w -4.079
      // 119f: ldc_w -4.432
      // 11a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ab: aastore
      // 11ac: dup
      // 11ad: bipush 1
      // 11ae: new net/minecraft/client/animation/Keyframe
      // 11b1: dup
      // 11b2: ldc 0.04
      // 11b4: ldc_w 1.448
      // 11b7: ldc_w -7.377
      // 11ba: ldc_w -6.106
      // 11bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11c6: aastore
      // 11c7: dup
      // 11c8: bipush 2
      // 11c9: new net/minecraft/client/animation/Keyframe
      // 11cc: dup
      // 11cd: ldc 0.08
      // 11cf: ldc_w 5.116
      // 11d2: ldc_w -11.489
      // 11d5: ldc_w -11.965
      // 11d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11e1: aastore
      // 11e2: dup
      // 11e3: bipush 3
      // 11e4: new net/minecraft/client/animation/Keyframe
      // 11e7: dup
      // 11e8: ldc 0.12
      // 11ea: ldc_w 9.969
      // 11ed: ldc_w -14.104
      // 11f0: ldc_w -17.518
      // 11f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11fc: aastore
      // 11fd: dup
      // 11fe: bipush 4
      // 11ff: new net/minecraft/client/animation/Keyframe
      // 1202: dup
      // 1203: ldc 0.17
      // 1205: ldc_w 14.341
      // 1208: ldc_w -13.296
      // 120b: ldc_w -18.144
      // 120e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1217: aastore
      // 1218: dup
      // 1219: bipush 5
      // 121a: new net/minecraft/client/animation/Keyframe
      // 121d: dup
      // 121e: ldc 0.21
      // 1220: ldc_w 18.296
      // 1223: ldc_w -9.985
      // 1226: ldc_w -12.19
      // 1229: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 122c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 122f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1232: aastore
      // 1233: dup
      // 1234: bipush 6
      // 1236: new net/minecraft/client/animation/Keyframe
      // 1239: dup
      // 123a: ldc 0.25
      // 123c: ldc_w 22.624
      // 123f: ldc_w -6.853
      // 1242: ldc_w -2.733
      // 1245: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1248: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 124b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 124e: aastore
      // 124f: dup
      // 1250: bipush 7
      // 1252: new net/minecraft/client/animation/Keyframe
      // 1255: dup
      // 1256: ldc 0.29
      // 1258: ldc_w 27.472
      // 125b: ldc_w -6.381
      // 125e: ldc_w 4.812
      // 1261: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1264: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1267: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 126a: aastore
      // 126b: dup
      // 126c: bipush 8
      // 126e: new net/minecraft/client/animation/Keyframe
      // 1271: dup
      // 1272: ldc 0.33
      // 1274: ldc_w 31.207
      // 1277: ldc_w -8.808
      // 127a: ldc_w 8.425
      // 127d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1280: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1283: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1286: aastore
      // 1287: dup
      // 1288: bipush 9
      // 128a: new net/minecraft/client/animation/Keyframe
      // 128d: dup
      // 128e: ldc 0.38
      // 1290: ldc_w 33.112
      // 1293: ldc_w -12.278
      // 1296: ldc_w 8.587
      // 1299: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 129c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 129f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a2: aastore
      // 12a3: dup
      // 12a4: bipush 10
      // 12a6: new net/minecraft/client/animation/Keyframe
      // 12a9: dup
      // 12aa: ldc 0.42
      // 12ac: ldc_w 33.71
      // 12af: ldc_w -14.87
      // 12b2: ldc_w 7.092
      // 12b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12be: aastore
      // 12bf: dup
      // 12c0: bipush 11
      // 12c2: new net/minecraft/client/animation/Keyframe
      // 12c5: dup
      // 12c6: ldc 0.46
      // 12c8: ldc_w 32.777
      // 12cb: ldc_w -16.089
      // 12ce: ldc_w 6.14
      // 12d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12da: aastore
      // 12db: dup
      // 12dc: bipush 12
      // 12de: new net/minecraft/client/animation/Keyframe
      // 12e1: dup
      // 12e2: ldc 0.5
      // 12e4: ldc_w 30.757
      // 12e7: ldc_w -16.434
      // 12ea: ldc_w 6.87
      // 12ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12f6: aastore
      // 12f7: dup
      // 12f8: bipush 13
      // 12fa: new net/minecraft/client/animation/Keyframe
      // 12fd: dup
      // 12fe: ldc 0.54
      // 1300: ldc_w 28.629
      // 1303: ldc_w -16.416
      // 1306: ldc_w 10.235
      // 1309: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 130c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 130f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1312: aastore
      // 1313: dup
      // 1314: bipush 14
      // 1316: new net/minecraft/client/animation/Keyframe
      // 1319: dup
      // 131a: ldc 0.58
      // 131c: ldc_w 27.102
      // 131f: ldc_w -16.352
      // 1322: ldc_w 17.205
      // 1325: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1328: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 132b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 132e: aastore
      // 132f: dup
      // 1330: bipush 15
      // 1332: new net/minecraft/client/animation/Keyframe
      // 1335: dup
      // 1336: ldc 0.62
      // 1338: ldc_w 25.713
      // 133b: ldc_w -16.088
      // 133e: ldc_w 26.475
      // 1341: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1344: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1347: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 134a: aastore
      // 134b: dup
      // 134c: bipush 16
      // 134e: new net/minecraft/client/animation/Keyframe
      // 1351: dup
      // 1352: ldc 0.67
      // 1354: ldc_w 23.903
      // 1357: ldc_w -15.03
      // 135a: ldc_w 35.744
      // 135d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1360: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1363: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1366: aastore
      // 1367: dup
      // 1368: bipush 17
      // 136a: new net/minecraft/client/animation/Keyframe
      // 136d: dup
      // 136e: ldc 0.71
      // 1370: ldc_w 22.174
      // 1373: ldc_w -12.477
      // 1376: ldc_w 44.387
      // 1379: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 137c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1382: aastore
      // 1383: dup
      // 1384: bipush 18
      // 1386: new net/minecraft/client/animation/Keyframe
      // 1389: dup
      // 138a: ldc 0.75
      // 138c: ldc_w 20.633
      // 138f: ldc_w -8.04
      // 1392: ldc_w 51.842
      // 1395: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1398: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 139b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 139e: aastore
      // 139f: dup
      // 13a0: bipush 19
      // 13a2: new net/minecraft/client/animation/Keyframe
      // 13a5: dup
      // 13a6: ldc 0.79
      // 13a8: ldc_w 18.918
      // 13ab: ldc_w -1.508
      // 13ae: ldc_w 57.319
      // 13b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ba: aastore
      // 13bb: dup
      // 13bc: bipush 20
      // 13be: new net/minecraft/client/animation/Keyframe
      // 13c1: dup
      // 13c2: ldc 0.83
      // 13c4: ldc_w 16.423
      // 13c7: ldc_w 8.158
      // 13ca: ldc_w 59.772
      // 13cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13d6: aastore
      // 13d7: dup
      // 13d8: bipush 21
      // 13da: new net/minecraft/client/animation/Keyframe
      // 13dd: dup
      // 13de: ldc 0.88
      // 13e0: ldc_w 11.768
      // 13e3: ldc_w 21.125
      // 13e6: ldc_w 58.215
      // 13e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13f2: aastore
      // 13f3: dup
      // 13f4: bipush 22
      // 13f6: new net/minecraft/client/animation/Keyframe
      // 13f9: dup
      // 13fa: ldc 0.92
      // 13fc: ldc_w 1.625
      // 13ff: ldc_w 35.034
      // 1402: ldc_w 49.939
      // 1405: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1408: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 140b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 140e: aastore
      // 140f: dup
      // 1410: bipush 23
      // 1412: new net/minecraft/client/animation/Keyframe
      // 1415: dup
      // 1416: ldc 0.96
      // 1418: ldc_w -16.583
      // 141b: ldc_w 43.056
      // 141e: ldc_w 31.851
      // 1421: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1424: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1427: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 142a: aastore
      // 142b: dup
      // 142c: bipush 24
      // 142e: new net/minecraft/client/animation/Keyframe
      // 1431: dup
      // 1432: fconst_1
      // 1433: ldc_w -32.162
      // 1436: ldc_w 37.392
      // 1439: ldc_w 10.246
      // 143c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 143f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1445: aastore
      // 1446: dup
      // 1447: bipush 25
      // 1449: new net/minecraft/client/animation/Keyframe
      // 144c: dup
      // 144d: ldc 1.04
      // 144f: ldc_w -38.44
      // 1452: ldc_w 22.404
      // 1455: ldc_w -13.478
      // 1458: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 145b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 145e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1461: aastore
      // 1462: dup
      // 1463: bipush 26
      // 1465: new net/minecraft/client/animation/Keyframe
      // 1468: dup
      // 1469: ldc 1.08
      // 146b: ldc_w -34.36
      // 146e: ldc_w 8.337
      // 1471: ldc_w -38.813
      // 1474: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 147a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 147d: aastore
      // 147e: dup
      // 147f: bipush 27
      // 1481: new net/minecraft/client/animation/Keyframe
      // 1484: dup
      // 1485: ldc 1.12
      // 1487: ldc_w -30.651
      // 148a: ldc_w 2.126
      // 148d: ldc_w -57.316
      // 1490: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1493: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1496: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1499: aastore
      // 149a: dup
      // 149b: bipush 28
      // 149d: new net/minecraft/client/animation/Keyframe
      // 14a0: dup
      // 14a1: ldc 1.17
      // 14a3: ldc_w -30.326
      // 14a6: ldc_w -1.002
      // 14a9: ldc_w -67.962
      // 14ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14b5: aastore
      // 14b6: dup
      // 14b7: bipush 29
      // 14b9: new net/minecraft/client/animation/Keyframe
      // 14bc: dup
      // 14bd: ldc 1.21
      // 14bf: ldc_w -31.357
      // 14c2: ldc_w -1.231
      // 14c5: ldc_w -72.318
      // 14c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14d1: aastore
      // 14d2: dup
      // 14d3: bipush 30
      // 14d5: new net/minecraft/client/animation/Keyframe
      // 14d8: dup
      // 14d9: ldc 1.25
      // 14db: ldc_w -33.294
      // 14de: ldc_w 1.052
      // 14e1: ldc_w -69.461
      // 14e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ed: aastore
      // 14ee: dup
      // 14ef: bipush 31
      // 14f1: new net/minecraft/client/animation/Keyframe
      // 14f4: dup
      // 14f5: ldc 1.29
      // 14f7: ldc_w -34.92
      // 14fa: ldc_w 6.543
      // 14fd: ldc_w -58.227
      // 1500: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1503: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1506: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1509: aastore
      // 150a: dup
      // 150b: bipush 32
      // 150d: new net/minecraft/client/animation/Keyframe
      // 1510: dup
      // 1511: ldc 1.33
      // 1513: ldc_w -32.746
      // 1516: ldc_w 12.795
      // 1519: ldc_w -46.796
      // 151c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 151f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1522: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1525: aastore
      // 1526: dup
      // 1527: bipush 33
      // 1529: new net/minecraft/client/animation/Keyframe
      // 152c: dup
      // 152d: ldc 1.38
      // 152f: ldc_w -28.03
      // 1532: ldc_w 17.469
      // 1535: ldc_w -38.142
      // 1538: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 153b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 153e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1541: aastore
      // 1542: dup
      // 1543: bipush 34
      // 1545: new net/minecraft/client/animation/Keyframe
      // 1548: dup
      // 1549: ldc 1.42
      // 154b: ldc_w -22.275
      // 154e: ldc_w 20.581
      // 1551: ldc_w -30.13
      // 1554: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1557: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 155a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 155d: aastore
      // 155e: dup
      // 155f: bipush 35
      // 1561: new net/minecraft/client/animation/Keyframe
      // 1564: dup
      // 1565: ldc 1.46
      // 1567: ldc_w -16.196
      // 156a: ldc_w 21.992
      // 156d: ldc_w -22.716
      // 1570: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1573: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1576: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1579: aastore
      // 157a: dup
      // 157b: bipush 36
      // 157d: new net/minecraft/client/animation/Keyframe
      // 1580: dup
      // 1581: ldc 1.5
      // 1583: ldc_w -10.378
      // 1586: ldc_w 21.726
      // 1589: ldc_w -16.363
      // 158c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 158f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1592: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1595: aastore
      // 1596: dup
      // 1597: bipush 37
      // 1599: new net/minecraft/client/animation/Keyframe
      // 159c: dup
      // 159d: ldc 1.54
      // 159f: ldc_w -5.393
      // 15a2: ldc_w 18.622
      // 15a5: ldc_w -13.154
      // 15a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15b1: aastore
      // 15b2: dup
      // 15b3: bipush 38
      // 15b5: new net/minecraft/client/animation/Keyframe
      // 15b8: dup
      // 15b9: ldc 1.58
      // 15bb: ldc_w -1.175
      // 15be: ldc_w 12.921
      // 15c1: ldc_w -13.939
      // 15c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15cd: aastore
      // 15ce: dup
      // 15cf: bipush 39
      // 15d1: new net/minecraft/client/animation/Keyframe
      // 15d4: dup
      // 15d5: ldc 1.62
      // 15d7: ldc_w 2.807
      // 15da: ldc_w 6.387
      // 15dd: ldc_w -17.029
      // 15e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e9: aastore
      // 15ea: dup
      // 15eb: bipush 40
      // 15ed: new net/minecraft/client/animation/Keyframe
      // 15f0: dup
      // 15f1: ldc 1.67
      // 15f3: ldc_w 6.577
      // 15f6: ldc_w 0.318
      // 15f9: ldc_w -20.559
      // 15fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1602: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1605: aastore
      // 1606: dup
      // 1607: bipush 41
      // 1609: new net/minecraft/client/animation/Keyframe
      // 160c: dup
      // 160d: ldc 1.71
      // 160f: ldc_w 9.491
      // 1612: ldc_w -4.31
      // 1615: ldc_w -22.508
      // 1618: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 161b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 161e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1621: aastore
      // 1622: dup
      // 1623: bipush 42
      // 1625: new net/minecraft/client/animation/Keyframe
      // 1628: dup
      // 1629: ldc 1.75
      // 162b: ldc_w 10.422
      // 162e: ldc_w -6.658
      // 1631: ldc_w -20.696
      // 1634: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1637: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 163a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 163d: aastore
      // 163e: dup
      // 163f: bipush 43
      // 1641: new net/minecraft/client/animation/Keyframe
      // 1644: dup
      // 1645: ldc 1.79
      // 1647: ldc_w 9.384
      // 164a: ldc_w -7.309
      // 164d: ldc_w -16.305
      // 1650: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1653: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1656: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1659: aastore
      // 165a: dup
      // 165b: bipush 44
      // 165d: new net/minecraft/client/animation/Keyframe
      // 1660: dup
      // 1661: ldc 1.83
      // 1663: ldc_w 7.268
      // 1666: ldc_w -7.309
      // 1669: ldc_w -12.017
      // 166c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 166f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1672: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1675: aastore
      // 1676: dup
      // 1677: bipush 45
      // 1679: new net/minecraft/client/animation/Keyframe
      // 167c: dup
      // 167d: ldc 1.88
      // 167f: ldc_w 4.214
      // 1682: ldc_w -6.351
      // 1685: ldc_w -7.981
      // 1688: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 168b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 168e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1691: aastore
      // 1692: dup
      // 1693: bipush 46
      // 1695: new net/minecraft/client/animation/Keyframe
      // 1698: dup
      // 1699: ldc 1.92
      // 169b: ldc_w 0.412
      // 169e: ldc_w -4.079
      // 16a1: ldc_w -4.432
      // 16a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ad: aastore
      // 16ae: dup
      // 16af: bipush 47
      // 16b1: new net/minecraft/client/animation/Keyframe
      // 16b4: dup
      // 16b5: ldc_w 1.96
      // 16b8: ldc_w -5.54
      // 16bb: ldc_w 1.263
      // 16be: ldc_w 0.987
      // 16c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ca: aastore
      // 16cb: dup
      // 16cc: bipush 48
      // 16ce: new net/minecraft/client/animation/Keyframe
      // 16d1: dup
      // 16d2: fconst_2
      // 16d3: ldc_w -5.575
      // 16d6: ldc_w 0.552
      // 16d9: ldc_w 1.075
      // 16dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16e5: aastore
      // 16e6: dup
      // 16e7: bipush 49
      // 16e9: new net/minecraft/client/animation/Keyframe
      // 16ec: dup
      // 16ed: ldc_w 2.04
      // 16f0: ldc_w -2.668
      // 16f3: ldc_w -2.47
      // 16f6: ldc_w -1.031
      // 16f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1702: aastore
      // 1703: dup
      // 1704: bipush 50
      // 1706: new net/minecraft/client/animation/Keyframe
      // 1709: dup
      // 170a: ldc_w 2.08
      // 170d: ldc_w 0.357
      // 1710: ldc_w -4.547
      // 1713: ldc_w -3.96
      // 1716: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1719: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 171c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 171f: aastore
      // 1720: dup
      // 1721: bipush 51
      // 1723: new net/minecraft/client/animation/Keyframe
      // 1726: dup
      // 1727: ldc_w 2.12
      // 172a: ldc_w 1.063
      // 172d: ldc_w -4.494
      // 1730: ldc_w -5.162
      // 1733: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1736: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1739: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 173c: aastore
      // 173d: dup
      // 173e: bipush 52
      // 1740: new net/minecraft/client/animation/Keyframe
      // 1743: dup
      // 1744: ldc 2.17
      // 1746: ldc_w 0.623
      // 1749: ldc_w -4.047
      // 174c: ldc_w -4.784
      // 174f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1752: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1755: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1758: aastore
      // 1759: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 175c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 175f: ldc_w "CTR_head"
      // 1762: new net/minecraft/client/animation/AnimationChannel
      // 1765: dup
      // 1766: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1769: bipush 14
      // 176b: anewarray 28
      // 176e: dup
      // 176f: bipush 0
      // 1770: new net/minecraft/client/animation/Keyframe
      // 1773: dup
      // 1774: ldc 0.79
      // 1776: fconst_0
      // 1777: fconst_0
      // 1778: fconst_0
      // 1779: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 177c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 177f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1782: aastore
      // 1783: dup
      // 1784: bipush 1
      // 1785: new net/minecraft/client/animation/Keyframe
      // 1788: dup
      // 1789: fconst_1
      // 178a: fconst_0
      // 178b: fconst_0
      // 178c: fconst_0
      // 178d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1790: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1793: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1796: aastore
      // 1797: dup
      // 1798: bipush 2
      // 1799: new net/minecraft/client/animation/Keyframe
      // 179c: dup
      // 179d: ldc 1.04
      // 179f: ldc_w -0.005
      // 17a2: ldc_w -0.025
      // 17a5: ldc_w -0.044
      // 17a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17b1: aastore
      // 17b2: dup
      // 17b3: bipush 3
      // 17b4: new net/minecraft/client/animation/Keyframe
      // 17b7: dup
      // 17b8: ldc 1.08
      // 17ba: ldc_w -0.018
      // 17bd: ldc_w -0.093
      // 17c0: ldc_w -0.167
      // 17c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17cc: aastore
      // 17cd: dup
      // 17ce: bipush 4
      // 17cf: new net/minecraft/client/animation/Keyframe
      // 17d2: dup
      // 17d3: ldc 1.17
      // 17d5: ldc_w -0.064
      // 17d8: ldc_w -0.326
      // 17db: ldc_w -0.586
      // 17de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e7: aastore
      // 17e8: dup
      // 17e9: bipush 5
      // 17ea: new net/minecraft/client/animation/Keyframe
      // 17ed: dup
      // 17ee: ldc 1.33
      // 17f0: ldc_w -0.182
      // 17f3: ldc_w -0.931
      // 17f6: ldc_w -1.676
      // 17f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1802: aastore
      // 1803: dup
      // 1804: bipush 6
      // 1806: new net/minecraft/client/animation/Keyframe
      // 1809: dup
      // 180a: ldc 1.42
      // 180c: ldc_w -0.227
      // 180f: ldc_w -1.164
      // 1812: ldc_w -2.095
      // 1815: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1818: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 181b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 181e: aastore
      // 181f: dup
      // 1820: bipush 7
      // 1822: new net/minecraft/client/animation/Keyframe
      // 1825: dup
      // 1826: ldc 1.46
      // 1828: ldc_w -0.24
      // 182b: ldc_w -1.232
      // 182e: ldc_w -2.218
      // 1831: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183a: aastore
      // 183b: dup
      // 183c: bipush 8
      // 183e: new net/minecraft/client/animation/Keyframe
      // 1841: dup
      // 1842: ldc 1.5
      // 1844: ldc_w -0.245
      // 1847: ldc_w -1.257
      // 184a: ldc_w -2.263
      // 184d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1850: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1853: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1856: aastore
      // 1857: dup
      // 1858: bipush 9
      // 185a: new net/minecraft/client/animation/Keyframe
      // 185d: dup
      // 185e: ldc 1.54
      // 1860: ldc_w -0.227
      // 1863: ldc_w -1.164
      // 1866: ldc_w -2.095
      // 1869: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 186c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 186f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1872: aastore
      // 1873: dup
      // 1874: bipush 10
      // 1876: new net/minecraft/client/animation/Keyframe
      // 1879: dup
      // 187a: ldc 1.58
      // 187c: ldc_w -0.182
      // 187f: ldc_w -0.931
      // 1882: ldc_w -1.676
      // 1885: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 188b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 188e: aastore
      // 188f: dup
      // 1890: bipush 11
      // 1892: new net/minecraft/client/animation/Keyframe
      // 1895: dup
      // 1896: ldc 1.62
      // 1898: ldc_w -0.123
      // 189b: ldc_w -0.629
      // 189e: ldc_w -1.131
      // 18a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18aa: aastore
      // 18ab: dup
      // 18ac: bipush 12
      // 18ae: new net/minecraft/client/animation/Keyframe
      // 18b1: dup
      // 18b2: ldc 1.71
      // 18b4: ldc_w -0.018
      // 18b7: ldc_w -0.093
      // 18ba: ldc_w -0.167
      // 18bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c6: aastore
      // 18c7: dup
      // 18c8: bipush 13
      // 18ca: new net/minecraft/client/animation/Keyframe
      // 18cd: dup
      // 18ce: ldc 1.75
      // 18d0: fconst_0
      // 18d1: fconst_0
      // 18d2: fconst_0
      // 18d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18dc: aastore
      // 18dd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 18e0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 18e3: ldc_w "CTR_shoulde1r_left"
      // 18e6: new net/minecraft/client/animation/AnimationChannel
      // 18e9: dup
      // 18ea: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 18ed: bipush 25
      // 18ef: anewarray 28
      // 18f2: dup
      // 18f3: bipush 0
      // 18f4: new net/minecraft/client/animation/Keyframe
      // 18f7: dup
      // 18f8: fconst_0
      // 18f9: fconst_0
      // 18fa: fconst_0
      // 18fb: fconst_0
      // 18fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1902: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1905: aastore
      // 1906: dup
      // 1907: bipush 1
      // 1908: new net/minecraft/client/animation/Keyframe
      // 190b: dup
      // 190c: ldc 0.04
      // 190e: ldc_w -0.54
      // 1911: ldc_w -0.06
      // 1914: ldc_w 0.163
      // 1917: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 191a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 191d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1920: aastore
      // 1921: dup
      // 1922: bipush 2
      // 1923: new net/minecraft/client/animation/Keyframe
      // 1926: dup
      // 1927: ldc 0.08
      // 1929: ldc_w -1.986
      // 192c: ldc_w -0.222
      // 192f: ldc_w 0.6
      // 1932: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 193b: aastore
      // 193c: dup
      // 193d: bipush 3
      // 193e: new net/minecraft/client/animation/Keyframe
      // 1941: dup
      // 1942: ldc 0.12
      // 1944: ldc_w -4.081
      // 1947: ldc_w -0.457
      // 194a: ldc_w 1.232
      // 194d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1950: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1953: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1956: aastore
      // 1957: dup
      // 1958: bipush 4
      // 1959: new net/minecraft/client/animation/Keyframe
      // 195c: dup
      // 195d: ldc 0.17
      // 195f: ldc_w -6.564
      // 1962: ldc_w -0.735
      // 1965: ldc_w 1.982
      // 1968: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 196b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 196e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1971: aastore
      // 1972: dup
      // 1973: bipush 5
      // 1974: new net/minecraft/client/animation/Keyframe
      // 1977: dup
      // 1978: ldc 0.21
      // 197a: ldc_w -9.177
      // 197d: ldc_w -1.027
      // 1980: ldc_w 2.771
      // 1983: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1986: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1989: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 198c: aastore
      // 198d: dup
      // 198e: bipush 6
      // 1990: new net/minecraft/client/animation/Keyframe
      // 1993: dup
      // 1994: ldc 0.25
      // 1996: ldc_w -11.66
      // 1999: ldc_w -1.305
      // 199c: ldc_w 3.521
      // 199f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19a8: aastore
      // 19a9: dup
      // 19aa: bipush 7
      // 19ac: new net/minecraft/client/animation/Keyframe
      // 19af: dup
      // 19b0: ldc 0.29
      // 19b2: ldc_w -13.754
      // 19b5: ldc_w -1.54
      // 19b8: ldc_w 4.153
      // 19bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19c4: aastore
      // 19c5: dup
      // 19c6: bipush 8
      // 19c8: new net/minecraft/client/animation/Keyframe
      // 19cb: dup
      // 19cc: ldc 0.33
      // 19ce: ldc_w -15.201
      // 19d1: ldc_w -1.702
      // 19d4: ldc_w 4.59
      // 19d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e0: aastore
      // 19e1: dup
      // 19e2: bipush 9
      // 19e4: new net/minecraft/client/animation/Keyframe
      // 19e7: dup
      // 19e8: ldc 0.38
      // 19ea: ldc_w -15.741
      // 19ed: ldc_w -1.762
      // 19f0: ldc_w 4.753
      // 19f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19fc: aastore
      // 19fd: dup
      // 19fe: bipush 10
      // 1a00: new net/minecraft/client/animation/Keyframe
      // 1a03: dup
      // 1a04: ldc 0.58
      // 1a06: ldc_w -15.662
      // 1a09: ldc_w -1.753
      // 1a0c: ldc_w 4.73
      // 1a0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a18: aastore
      // 1a19: dup
      // 1a1a: bipush 11
      // 1a1c: new net/minecraft/client/animation/Keyframe
      // 1a1f: dup
      // 1a20: ldc 0.75
      // 1a22: ldc_w -15.285
      // 1a25: ldc_w -1.711
      // 1a28: ldc_w 4.616
      // 1a2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a34: aastore
      // 1a35: dup
      // 1a36: bipush 12
      // 1a38: new net/minecraft/client/animation/Keyframe
      // 1a3b: dup
      // 1a3c: ldc 0.88
      // 1a3e: ldc_w -14.661
      // 1a41: ldc_w -1.641
      // 1a44: ldc_w 4.427
      // 1a47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a50: aastore
      // 1a51: dup
      // 1a52: bipush 13
      // 1a54: new net/minecraft/client/animation/Keyframe
      // 1a57: dup
      // 1a58: ldc 0.92
      // 1a5a: ldc_w -14.243
      // 1a5d: ldc_w -1.594
      // 1a60: ldc_w 4.301
      // 1a63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a6c: aastore
      // 1a6d: dup
      // 1a6e: bipush 14
      // 1a70: new net/minecraft/client/animation/Keyframe
      // 1a73: dup
      // 1a74: ldc 0.96
      // 1a76: ldc_w -13.589
      // 1a79: ldc_w -1.521
      // 1a7c: ldc_w 4.104
      // 1a7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a88: aastore
      // 1a89: dup
      // 1a8a: bipush 15
      // 1a8c: new net/minecraft/client/animation/Keyframe
      // 1a8f: dup
      // 1a90: ldc 1.12
      // 1a92: ldc_w -10.303
      // 1a95: ldc_w -1.153
      // 1a98: ldc_w 3.111
      // 1a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa4: aastore
      // 1aa5: dup
      // 1aa6: bipush 16
      // 1aa8: new net/minecraft/client/animation/Keyframe
      // 1aab: dup
      // 1aac: ldc 1.17
      // 1aae: ldc_w -9.534
      // 1ab1: ldc_w -1.067
      // 1ab4: ldc_w 2.879
      // 1ab7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1abd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ac0: aastore
      // 1ac1: dup
      // 1ac2: bipush 17
      // 1ac4: new net/minecraft/client/animation/Keyframe
      // 1ac7: dup
      // 1ac8: ldc 1.33
      // 1aca: ldc_w -6.751
      // 1acd: ldc_w -0.756
      // 1ad0: ldc_w 2.039
      // 1ad3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ad6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ad9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1adc: aastore
      // 1add: dup
      // 1ade: bipush 18
      // 1ae0: new net/minecraft/client/animation/Keyframe
      // 1ae3: dup
      // 1ae4: ldc 1.38
      // 1ae6: ldc_w -6.123
      // 1ae9: ldc_w -0.685
      // 1aec: ldc 1.849
      // 1aee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1af1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1af7: aastore
      // 1af8: dup
      // 1af9: bipush 19
      // 1afb: new net/minecraft/client/animation/Keyframe
      // 1afe: dup
      // 1aff: ldc 1.62
      // 1b01: ldc_w -2.701
      // 1b04: ldc_w -0.302
      // 1b07: ldc_w 0.816
      // 1b0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b13: aastore
      // 1b14: dup
      // 1b15: bipush 20
      // 1b17: new net/minecraft/client/animation/Keyframe
      // 1b1a: dup
      // 1b1b: ldc 1.67
      // 1b1d: ldc_w -2.156
      // 1b20: ldc_w -0.241
      // 1b23: ldc_w 0.651
      // 1b26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b2f: aastore
      // 1b30: dup
      // 1b31: bipush 21
      // 1b33: new net/minecraft/client/animation/Keyframe
      // 1b36: dup
      // 1b37: ldc 1.75
      // 1b39: ldc_w -1.102
      // 1b3c: ldc_w -0.123
      // 1b3f: ldc_w 0.333
      // 1b42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b4b: aastore
      // 1b4c: dup
      // 1b4d: bipush 22
      // 1b4f: new net/minecraft/client/animation/Keyframe
      // 1b52: dup
      // 1b53: ldc 1.83
      // 1b55: ldc_w -0.299
      // 1b58: ldc_w -0.033
      // 1b5b: ldc_w 0.09
      // 1b5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b67: aastore
      // 1b68: dup
      // 1b69: bipush 23
      // 1b6b: new net/minecraft/client/animation/Keyframe
      // 1b6e: dup
      // 1b6f: ldc 1.88
      // 1b71: ldc_w -0.073
      // 1b74: ldc_w -0.008
      // 1b77: ldc_w 0.022
      // 1b7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b83: aastore
      // 1b84: dup
      // 1b85: bipush 24
      // 1b87: new net/minecraft/client/animation/Keyframe
      // 1b8a: dup
      // 1b8b: ldc 1.92
      // 1b8d: fconst_0
      // 1b8e: fconst_0
      // 1b8f: fconst_0
      // 1b90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b99: aastore
      // 1b9a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1b9d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ba0: ldc_w "CTR_shoulde1r_left"
      // 1ba3: new net/minecraft/client/animation/AnimationChannel
      // 1ba6: dup
      // 1ba7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1baa: bipush 52
      // 1bac: anewarray 28
      // 1baf: dup
      // 1bb0: bipush 0
      // 1bb1: new net/minecraft/client/animation/Keyframe
      // 1bb4: dup
      // 1bb5: fconst_0
      // 1bb6: ldc_w -0.112
      // 1bb9: ldc_w -0.19
      // 1bbc: ldc_w -0.487
      // 1bbf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bc2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bc5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bc8: aastore
      // 1bc9: dup
      // 1bca: bipush 1
      // 1bcb: new net/minecraft/client/animation/Keyframe
      // 1bce: dup
      // 1bcf: ldc 0.04
      // 1bd1: ldc_w -0.011
      // 1bd4: ldc_w -0.001
      // 1bd7: ldc_w -1.64
      // 1bda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be3: aastore
      // 1be4: dup
      // 1be5: bipush 2
      // 1be6: new net/minecraft/client/animation/Keyframe
      // 1be9: dup
      // 1bea: ldc 0.08
      // 1bec: ldc_w 0.288
      // 1bef: ldc_w 0.449
      // 1bf2: ldc_w -4.429
      // 1bf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bfe: aastore
      // 1bff: dup
      // 1c00: bipush 3
      // 1c01: new net/minecraft/client/animation/Keyframe
      // 1c04: dup
      // 1c05: ldc 0.12
      // 1c07: ldc_w 0.78
      // 1c0a: ldc_w 0.987
      // 1c0d: ldc_w -7.855
      // 1c10: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c19: aastore
      // 1c1a: dup
      // 1c1b: bipush 4
      // 1c1c: new net/minecraft/client/animation/Keyframe
      // 1c1f: dup
      // 1c20: ldc 0.17
      // 1c22: ldc 1.46
      // 1c24: ldc_w 1.437
      // 1c27: ldc_w -10.917
      // 1c2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c33: aastore
      // 1c34: dup
      // 1c35: bipush 5
      // 1c36: new net/minecraft/client/animation/Keyframe
      // 1c39: dup
      // 1c3a: ldc 0.21
      // 1c3c: ldc_w 2.321
      // 1c3f: ldc_w 1.626
      // 1c42: ldc_w -12.613
      // 1c45: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c4e: aastore
      // 1c4f: dup
      // 1c50: bipush 6
      // 1c52: new net/minecraft/client/animation/Keyframe
      // 1c55: dup
      // 1c56: ldc 0.25
      // 1c58: ldc_w 3.326
      // 1c5b: ldc_w 1.112
      // 1c5e: ldc_w -13.129
      // 1c61: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c6a: aastore
      // 1c6b: dup
      // 1c6c: bipush 7
      // 1c6e: new net/minecraft/client/animation/Keyframe
      // 1c71: dup
      // 1c72: ldc 0.29
      // 1c74: ldc_w 4.299
      // 1c77: ldc_w -0.225
      // 1c7a: ldc_w -13.384
      // 1c7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c86: aastore
      // 1c87: dup
      // 1c88: bipush 8
      // 1c8a: new net/minecraft/client/animation/Keyframe
      // 1c8d: dup
      // 1c8e: ldc 0.33
      // 1c90: ldc_w 5.035
      // 1c93: ldc_w -2.078
      // 1c96: ldc_w -13.481
      // 1c99: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c9c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c9f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca2: aastore
      // 1ca3: dup
      // 1ca4: bipush 9
      // 1ca6: new net/minecraft/client/animation/Keyframe
      // 1ca9: dup
      // 1caa: ldc 0.38
      // 1cac: ldc_w 5.326
      // 1caf: ldc_w -4.138
      // 1cb2: ldc_w -13.519
      // 1cb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cbb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cbe: aastore
      // 1cbf: dup
      // 1cc0: bipush 10
      // 1cc2: new net/minecraft/client/animation/Keyframe
      // 1cc5: dup
      // 1cc6: ldc 0.42
      // 1cc8: ldc_w 5.286
      // 1ccb: ldc_w -6.147
      // 1cce: ldc_w -13.582
      // 1cd1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cd4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cd7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cda: aastore
      // 1cdb: dup
      // 1cdc: bipush 11
      // 1cde: new net/minecraft/client/animation/Keyframe
      // 1ce1: dup
      // 1ce2: ldc 0.46
      // 1ce4: ldc_w 5.175
      // 1ce7: ldc_w -8.043
      // 1cea: ldc_w -13.681
      // 1ced: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cf0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cf6: aastore
      // 1cf7: dup
      // 1cf8: bipush 12
      // 1cfa: new net/minecraft/client/animation/Keyframe
      // 1cfd: dup
      // 1cfe: ldc 0.5
      // 1d00: ldc_w 5.006
      // 1d03: ldc_w -9.816
      // 1d06: ldc_w -13.809
      // 1d09: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d12: aastore
      // 1d13: dup
      // 1d14: bipush 13
      // 1d16: new net/minecraft/client/animation/Keyframe
      // 1d19: dup
      // 1d1a: ldc 0.54
      // 1d1c: ldc_w 4.795
      // 1d1f: ldc_w -11.452
      // 1d22: ldc_w -13.958
      // 1d25: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d2e: aastore
      // 1d2f: dup
      // 1d30: bipush 14
      // 1d32: new net/minecraft/client/animation/Keyframe
      // 1d35: dup
      // 1d36: ldc 0.58
      // 1d38: ldc_w 4.555
      // 1d3b: ldc_w -12.94
      // 1d3e: ldc_w -14.122
      // 1d41: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d4a: aastore
      // 1d4b: dup
      // 1d4c: bipush 15
      // 1d4e: new net/minecraft/client/animation/Keyframe
      // 1d51: dup
      // 1d52: ldc 0.62
      // 1d54: ldc_w 4.301
      // 1d57: ldc_w -14.268
      // 1d5a: ldc_w -14.292
      // 1d5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d66: aastore
      // 1d67: dup
      // 1d68: bipush 16
      // 1d6a: new net/minecraft/client/animation/Keyframe
      // 1d6d: dup
      // 1d6e: ldc 0.67
      // 1d70: ldc_w 4.047
      // 1d73: ldc_w -15.425
      // 1d76: ldc_w -14.463
      // 1d79: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d82: aastore
      // 1d83: dup
      // 1d84: bipush 17
      // 1d86: new net/minecraft/client/animation/Keyframe
      // 1d89: dup
      // 1d8a: ldc 0.71
      // 1d8c: ldc_w 3.807
      // 1d8f: ldc_w -16.398
      // 1d92: ldc_w -14.627
      // 1d95: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d9e: aastore
      // 1d9f: dup
      // 1da0: bipush 18
      // 1da2: new net/minecraft/client/animation/Keyframe
      // 1da5: dup
      // 1da6: ldc 0.75
      // 1da8: ldc_w 3.595
      // 1dab: ldc_w -17.175
      // 1dae: ldc_w -14.776
      // 1db1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1db4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1db7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dba: aastore
      // 1dbb: dup
      // 1dbc: bipush 19
      // 1dbe: new net/minecraft/client/animation/Keyframe
      // 1dc1: dup
      // 1dc2: ldc 0.79
      // 1dc4: ldc_w 3.427
      // 1dc7: ldc_w -17.745
      // 1dca: ldc_w -14.904
      // 1dcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dd6: aastore
      // 1dd7: dup
      // 1dd8: bipush 20
      // 1dda: new net/minecraft/client/animation/Keyframe
      // 1ddd: dup
      // 1dde: ldc 0.83
      // 1de0: ldc_w 3.315
      // 1de3: ldc_w -18.096
      // 1de6: ldc_w -15.003
      // 1de9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1def: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df2: aastore
      // 1df3: dup
      // 1df4: bipush 21
      // 1df6: new net/minecraft/client/animation/Keyframe
      // 1df9: dup
      // 1dfa: ldc 0.88
      // 1dfc: ldc_w 3.275
      // 1dff: ldc_w -18.216
      // 1e02: ldc_w -15.066
      // 1e05: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e0e: aastore
      // 1e0f: dup
      // 1e10: bipush 22
      // 1e12: new net/minecraft/client/animation/Keyframe
      // 1e15: dup
      // 1e16: ldc 0.92
      // 1e18: ldc_w 3.324
      // 1e1b: ldc -17.896
      // 1e1d: ldc_w -15.092
      // 1e20: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e29: aastore
      // 1e2a: dup
      // 1e2b: bipush 23
      // 1e2d: new net/minecraft/client/animation/Keyframe
      // 1e30: dup
      // 1e31: ldc 0.96
      // 1e33: ldc_w 3.667
      // 1e36: ldc_w -16.415
      // 1e39: ldc_w -15.105
      // 1e3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e45: aastore
      // 1e46: dup
      // 1e47: bipush 24
      // 1e49: new net/minecraft/client/animation/Keyframe
      // 1e4c: dup
      // 1e4d: fconst_1
      // 1e4e: ldc_w 4.597
      // 1e51: ldc_w -12.987
      // 1e54: ldc_w -15.135
      // 1e57: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e60: aastore
      // 1e61: dup
      // 1e62: bipush 25
      // 1e64: new net/minecraft/client/animation/Keyframe
      // 1e67: dup
      // 1e68: ldc 1.04
      // 1e6a: ldc_w 6.408
      // 1e6d: ldc_w -6.829
      // 1e70: ldc_w -15.21
      // 1e73: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e7c: aastore
      // 1e7d: dup
      // 1e7e: bipush 26
      // 1e80: new net/minecraft/client/animation/Keyframe
      // 1e83: dup
      // 1e84: ldc 1.08
      // 1e86: ldc_w 8.433
      // 1e89: ldc_w -1.053
      // 1e8c: ldc_w -23.976
      // 1e8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e98: aastore
      // 1e99: dup
      // 1e9a: bipush 27
      // 1e9c: new net/minecraft/client/animation/Keyframe
      // 1e9f: dup
      // 1ea0: ldc 1.12
      // 1ea2: ldc_w 8.133
      // 1ea5: ldc_w -1.234
      // 1ea8: ldc_w -23.511
      // 1eab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1eae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eb4: aastore
      // 1eb5: dup
      // 1eb6: bipush 28
      // 1eb8: new net/minecraft/client/animation/Keyframe
      // 1ebb: dup
      // 1ebc: ldc 1.17
      // 1ebe: ldc_w 7.362
      // 1ec1: ldc_w -1.738
      // 1ec4: ldc_w -22.272
      // 1ec7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1eca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ecd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ed0: aastore
      // 1ed1: dup
      // 1ed2: bipush 29
      // 1ed4: new net/minecraft/client/animation/Keyframe
      // 1ed7: dup
      // 1ed8: ldc 1.21
      // 1eda: ldc_w 6.317
      // 1edd: ldc_w -2.51
      // 1ee0: ldc_w -20.491
      // 1ee3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ee6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ee9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eec: aastore
      // 1eed: dup
      // 1eee: bipush 30
      // 1ef0: new net/minecraft/client/animation/Keyframe
      // 1ef3: dup
      // 1ef4: ldc 1.25
      // 1ef6: ldc_w 5.196
      // 1ef9: ldc_w -3.494
      // 1efc: ldc_w -18.401
      // 1eff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f02: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f05: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f08: aastore
      // 1f09: dup
      // 1f0a: bipush 31
      // 1f0c: new net/minecraft/client/animation/Keyframe
      // 1f0f: dup
      // 1f10: ldc 1.29
      // 1f12: ldc_w 4.195
      // 1f15: ldc_w -4.632
      // 1f18: ldc_w -16.237
      // 1f1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f24: aastore
      // 1f25: dup
      // 1f26: bipush 32
      // 1f28: new net/minecraft/client/animation/Keyframe
      // 1f2b: dup
      // 1f2c: ldc 1.33
      // 1f2e: ldc_w 3.511
      // 1f31: ldc_w -5.869
      // 1f34: ldc_w -14.231
      // 1f37: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f3a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f3d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f40: aastore
      // 1f41: dup
      // 1f42: bipush 33
      // 1f44: new net/minecraft/client/animation/Keyframe
      // 1f47: dup
      // 1f48: ldc 1.38
      // 1f4a: ldc_w 3.106
      // 1f4d: ldc_w -7.149
      // 1f50: ldc_w -12.57
      // 1f53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f5c: aastore
      // 1f5d: dup
      // 1f5e: bipush 34
      // 1f60: new net/minecraft/client/animation/Keyframe
      // 1f63: dup
      // 1f64: ldc 1.42
      // 1f66: ldc_w 2.801
      // 1f69: ldc_w -8.422
      // 1f6c: ldc_w -11.254
      // 1f6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f78: aastore
      // 1f79: dup
      // 1f7a: bipush 35
      // 1f7c: new net/minecraft/client/animation/Keyframe
      // 1f7f: dup
      // 1f80: ldc 1.46
      // 1f82: ldc_w 2.583
      // 1f85: ldc_w -9.637
      // 1f88: ldc_w -10.24
      // 1f8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f94: aastore
      // 1f95: dup
      // 1f96: bipush 36
      // 1f98: new net/minecraft/client/animation/Keyframe
      // 1f9b: dup
      // 1f9c: ldc 1.5
      // 1f9e: ldc_w 2.437
      // 1fa1: ldc_w -10.743
      // 1fa4: ldc_w -9.481
      // 1fa7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1faa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fb0: aastore
      // 1fb1: dup
      // 1fb2: bipush 37
      // 1fb4: new net/minecraft/client/animation/Keyframe
      // 1fb7: dup
      // 1fb8: ldc 1.54
      // 1fba: ldc_w 2.348
      // 1fbd: ldc_w -11.691
      // 1fc0: ldc_w -8.932
      // 1fc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fcc: aastore
      // 1fcd: dup
      // 1fce: bipush 38
      // 1fd0: new net/minecraft/client/animation/Keyframe
      // 1fd3: dup
      // 1fd4: ldc 1.58
      // 1fd6: ldc_w 2.303
      // 1fd9: ldc_w -12.431
      // 1fdc: ldc_w -8.548
      // 1fdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1fe2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fe5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fe8: aastore
      // 1fe9: dup
      // 1fea: bipush 39
      // 1fec: new net/minecraft/client/animation/Keyframe
      // 1fef: dup
      // 1ff0: ldc 1.62
      // 1ff2: ldc_w 2.286
      // 1ff5: ldc_w -12.911
      // 1ff8: ldc_w -8.283
      // 1ffb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ffe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2001: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2004: aastore
      // 2005: dup
      // 2006: bipush 40
      // 2008: new net/minecraft/client/animation/Keyframe
      // 200b: dup
      // 200c: ldc 1.67
      // 200e: ldc_w 2.284
      // 2011: ldc_w -13.083
      // 2014: ldc_w -8.092
      // 2017: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 201a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 201d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2020: aastore
      // 2021: dup
      // 2022: bipush 41
      // 2024: new net/minecraft/client/animation/Keyframe
      // 2027: dup
      // 2028: ldc 1.71
      // 202a: ldc_w 2.391
      // 202d: ldc_w -12.266
      // 2030: ldc_w -7.922
      // 2033: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2036: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2039: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 203c: aastore
      // 203d: dup
      // 203e: bipush 42
      // 2040: new net/minecraft/client/animation/Keyframe
      // 2043: dup
      // 2044: ldc 1.75
      // 2046: ldc_w 2.627
      // 2049: ldc_w -10.337
      // 204c: ldc_w -7.688
      // 204f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2052: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2055: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2058: aastore
      // 2059: dup
      // 205a: bipush 43
      // 205c: new net/minecraft/client/animation/Keyframe
      // 205f: dup
      // 2060: ldc 1.79
      // 2062: ldc_w 2.863
      // 2065: ldc_w -8.077
      // 2068: ldc_w -7.3
      // 206b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 206e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2071: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2074: aastore
      // 2075: dup
      // 2076: bipush 44
      // 2078: new net/minecraft/client/animation/Keyframe
      // 207b: dup
      // 207c: ldc 1.83
      // 207e: ldc_w 2.971
      // 2081: ldc_w -6.27
      // 2084: ldc_w -6.664
      // 2087: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 208a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 208d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2090: aastore
      // 2091: dup
      // 2092: bipush 45
      // 2094: new net/minecraft/client/animation/Keyframe
      // 2097: dup
      // 2098: ldc 1.88
      // 209a: ldc_w 2.95
      // 209d: ldc_w -5.466
      // 20a0: ldc_w -5.727
      // 20a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ac: aastore
      // 20ad: dup
      // 20ae: bipush 46
      // 20b0: new net/minecraft/client/animation/Keyframe
      // 20b3: dup
      // 20b4: ldc 1.92
      // 20b6: ldc_w 2.829
      // 20b9: ldc_w -5.291
      // 20bc: ldc_w -4.588
      // 20bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c8: aastore
      // 20c9: dup
      // 20ca: bipush 47
      // 20cc: new net/minecraft/client/animation/Keyframe
      // 20cf: dup
      // 20d0: ldc_w 1.96
      // 20d3: ldc_w 2.52
      // 20d6: ldc_w -5.141
      // 20d9: ldc_w -3.386
      // 20dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20e5: aastore
      // 20e6: dup
      // 20e7: bipush 48
      // 20e9: new net/minecraft/client/animation/Keyframe
      // 20ec: dup
      // 20ed: fconst_2
      // 20ee: ldc_w 1.936
      // 20f1: ldc_w -4.41
      // 20f4: ldc_w -2.258
      // 20f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 20fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2100: aastore
      // 2101: dup
      // 2102: bipush 49
      // 2104: new net/minecraft/client/animation/Keyframe
      // 2107: dup
      // 2108: ldc_w 2.04
      // 210b: ldc_w 1.071
      // 210e: ldc_w -2.773
      // 2111: ldc_w -1.335
      // 2114: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2117: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 211a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 211d: aastore
      // 211e: dup
      // 211f: bipush 50
      // 2121: new net/minecraft/client/animation/Keyframe
      // 2124: dup
      // 2125: ldc_w 2.08
      // 2128: ldc_w 0.252
      // 212b: ldc_w -1.012
      // 212e: ldc_w -0.714
      // 2131: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2134: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2137: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 213a: aastore
      // 213b: dup
      // 213c: bipush 51
      // 213e: new net/minecraft/client/animation/Keyframe
      // 2141: dup
      // 2142: ldc_w 2.12
      // 2145: ldc_w -0.112
      // 2148: ldc_w -0.19
      // 214b: ldc_w -0.487
      // 214e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2151: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2154: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2157: aastore
      // 2158: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 215b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 215e: ldc_w "CTR_upperarm_left"
      // 2161: new net/minecraft/client/animation/AnimationChannel
      // 2164: dup
      // 2165: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2168: bipush 53
      // 216a: anewarray 28
      // 216d: dup
      // 216e: bipush 0
      // 216f: new net/minecraft/client/animation/Keyframe
      // 2172: dup
      // 2173: fconst_0
      // 2174: ldc_w -1.675
      // 2177: ldc_w -2.702
      // 217a: ldc_w -0.074
      // 217d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2186: aastore
      // 2187: dup
      // 2188: bipush 1
      // 2189: new net/minecraft/client/animation/Keyframe
      // 218c: dup
      // 218d: ldc 0.04
      // 218f: ldc_w -3.878
      // 2192: ldc_w -1.753
      // 2195: ldc_w 4.32
      // 2198: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 219b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 219e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21a1: aastore
      // 21a2: dup
      // 21a3: bipush 2
      // 21a4: new net/minecraft/client/animation/Keyframe
      // 21a7: dup
      // 21a8: ldc 0.08
      // 21aa: ldc_w -4.594
      // 21ad: ldc_w -5.936
      // 21b0: ldc 6.168
      // 21b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21bb: aastore
      // 21bc: dup
      // 21bd: bipush 3
      // 21be: new net/minecraft/client/animation/Keyframe
      // 21c1: dup
      // 21c2: ldc 0.12
      // 21c4: ldc_w -5.301
      // 21c7: ldc_w -13.471
      // 21ca: ldc_w 3.997
      // 21cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21d6: aastore
      // 21d7: dup
      // 21d8: bipush 4
      // 21d9: new net/minecraft/client/animation/Keyframe
      // 21dc: dup
      // 21dd: ldc 0.17
      // 21df: ldc_w -6.471
      // 21e2: ldc_w -22.022
      // 21e5: ldc_w -1.081
      // 21e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21f1: aastore
      // 21f2: dup
      // 21f3: bipush 5
      // 21f4: new net/minecraft/client/animation/Keyframe
      // 21f7: dup
      // 21f8: ldc 0.21
      // 21fa: ldc_w -9.683
      // 21fd: ldc_w -26.906
      // 2200: ldc_w -2.73
      // 2203: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2206: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2209: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 220c: aastore
      // 220d: dup
      // 220e: bipush 6
      // 2210: new net/minecraft/client/animation/Keyframe
      // 2213: dup
      // 2214: ldc 0.25
      // 2216: ldc_w -15.01
      // 2219: ldc_w -27.579
      // 221c: ldc_w 1.492
      // 221f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2222: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2225: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2228: aastore
      // 2229: dup
      // 222a: bipush 7
      // 222c: new net/minecraft/client/animation/Keyframe
      // 222f: dup
      // 2230: ldc 0.29
      // 2232: ldc_w -21.585
      // 2235: ldc_w -27.221
      // 2238: ldc_w 9.814
      // 223b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 223e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2241: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2244: aastore
      // 2245: dup
      // 2246: bipush 8
      // 2248: new net/minecraft/client/animation/Keyframe
      // 224b: dup
      // 224c: ldc 0.33
      // 224e: ldc_w -20.122
      // 2251: ldc_w -39.032
      // 2254: ldc_w 7.612
      // 2257: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 225a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 225d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2260: aastore
      // 2261: dup
      // 2262: bipush 9
      // 2264: new net/minecraft/client/animation/Keyframe
      // 2267: dup
      // 2268: ldc 0.38
      // 226a: ldc_w 4.416
      // 226d: ldc_w -58.969
      // 2270: ldc_w -14.889
      // 2273: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2276: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2279: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 227c: aastore
      // 227d: dup
      // 227e: bipush 10
      // 2280: new net/minecraft/client/animation/Keyframe
      // 2283: dup
      // 2284: ldc 0.42
      // 2286: ldc_w 59.325
      // 2289: ldc_w -65.294
      // 228c: ldc_w -66.294
      // 228f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2292: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2295: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2298: aastore
      // 2299: dup
      // 229a: bipush 11
      // 229c: new net/minecraft/client/animation/Keyframe
      // 229f: dup
      // 22a0: ldc 0.46
      // 22a2: ldc_w 50.799
      // 22a5: ldc_w -71.317
      // 22a8: ldc_w -52.747
      // 22ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22b4: aastore
      // 22b5: dup
      // 22b6: bipush 12
      // 22b8: new net/minecraft/client/animation/Keyframe
      // 22bb: dup
      // 22bc: ldc 0.5
      // 22be: ldc_w 29.728
      // 22c1: ldc_w -72.765
      // 22c4: ldc_w -26.448
      // 22c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22d0: aastore
      // 22d1: dup
      // 22d2: bipush 13
      // 22d4: new net/minecraft/client/animation/Keyframe
      // 22d7: dup
      // 22d8: ldc 0.54
      // 22da: ldc_w 13.375
      // 22dd: ldc_w -68.887
      // 22e0: ldc_w -4.862
      // 22e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ec: aastore
      // 22ed: dup
      // 22ee: bipush 14
      // 22f0: new net/minecraft/client/animation/Keyframe
      // 22f3: dup
      // 22f4: ldc 0.58
      // 22f6: ldc_w 9.323
      // 22f9: ldc_w -60.951
      // 22fc: ldc_w 3.995
      // 22ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2302: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2305: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2308: aastore
      // 2309: dup
      // 230a: bipush 15
      // 230c: new net/minecraft/client/animation/Keyframe
      // 230f: dup
      // 2310: ldc 0.62
      // 2312: ldc_w 1.397
      // 2315: ldc_w -46.999
      // 2318: ldc_w 17.422
      // 231b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 231e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2321: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2324: aastore
      // 2325: dup
      // 2326: bipush 16
      // 2328: new net/minecraft/client/animation/Keyframe
      // 232b: dup
      // 232c: ldc 0.67
      // 232e: ldc_w 1.981
      // 2331: ldc_w -31.949
      // 2334: ldc_w 22.598
      // 2337: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 233a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 233d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2340: aastore
      // 2341: dup
      // 2342: bipush 17
      // 2344: new net/minecraft/client/animation/Keyframe
      // 2347: dup
      // 2348: ldc 0.71
      // 234a: ldc_w 6.897
      // 234d: ldc_w -19.262
      // 2350: ldc_w 21.844
      // 2353: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2356: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2359: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 235c: aastore
      // 235d: dup
      // 235e: bipush 18
      // 2360: new net/minecraft/client/animation/Keyframe
      // 2363: dup
      // 2364: ldc 0.75
      // 2366: ldc_w 11.94
      // 2369: ldc_w -8.325
      // 236c: ldc 18.55
      // 236e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2371: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2374: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2377: aastore
      // 2378: dup
      // 2379: bipush 19
      // 237b: new net/minecraft/client/animation/Keyframe
      // 237e: dup
      // 237f: ldc 0.79
      // 2381: ldc_w 15.533
      // 2384: ldc_w 0.7
      // 2387: ldc_w 13.723
      // 238a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 238d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2390: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2393: aastore
      // 2394: dup
      // 2395: bipush 20
      // 2397: new net/minecraft/client/animation/Keyframe
      // 239a: dup
      // 239b: ldc 0.83
      // 239d: ldc_w 17.847
      // 23a0: ldc_w 7.348
      // 23a3: ldc_w 6.517
      // 23a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23af: aastore
      // 23b0: dup
      // 23b1: bipush 21
      // 23b3: new net/minecraft/client/animation/Keyframe
      // 23b6: dup
      // 23b7: ldc 0.88
      // 23b9: ldc_w 19.315
      // 23bc: ldc_w 12.528
      // 23bf: ldc_w -0.513
      // 23c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23cb: aastore
      // 23cc: dup
      // 23cd: bipush 22
      // 23cf: new net/minecraft/client/animation/Keyframe
      // 23d2: dup
      // 23d3: ldc 0.92
      // 23d5: ldc_w 20.132
      // 23d8: ldc_w 15.885
      // 23db: ldc_w -4.347
      // 23de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23e7: aastore
      // 23e8: dup
      // 23e9: bipush 23
      // 23eb: new net/minecraft/client/animation/Keyframe
      // 23ee: dup
      // 23ef: ldc 0.96
      // 23f1: ldc_w 19.355
      // 23f4: ldc_w 14.221
      // 23f7: ldc_w -7.026
      // 23fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2400: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2403: aastore
      // 2404: dup
      // 2405: bipush 24
      // 2407: new net/minecraft/client/animation/Keyframe
      // 240a: dup
      // 240b: fconst_1
      // 240c: ldc_w 16.945
      // 240f: ldc_w 0.229
      // 2412: ldc_w -14.46
      // 2415: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2418: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 241b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 241e: aastore
      // 241f: dup
      // 2420: bipush 25
      // 2422: new net/minecraft/client/animation/Keyframe
      // 2425: dup
      // 2426: ldc 1.04
      // 2428: ldc_w 29.867
      // 242b: ldc_w -27.419
      // 242e: ldc_w -39.617
      // 2431: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 243a: aastore
      // 243b: dup
      // 243c: bipush 26
      // 243e: new net/minecraft/client/animation/Keyframe
      // 2441: dup
      // 2442: ldc 1.08
      // 2444: ldc_w 69.178
      // 2447: ldc_w -11.091
      // 244a: ldc_w -79.895
      // 244d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2450: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2453: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2456: aastore
      // 2457: dup
      // 2458: bipush 27
      // 245a: new net/minecraft/client/animation/Keyframe
      // 245d: dup
      // 245e: ldc 1.12
      // 2460: ldc_w 62.023
      // 2463: ldc_w -46.744
      // 2466: ldc_w -60.672
      // 2469: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 246c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 246f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2472: aastore
      // 2473: dup
      // 2474: bipush 28
      // 2476: new net/minecraft/client/animation/Keyframe
      // 2479: dup
      // 247a: ldc 1.17
      // 247c: ldc_w 47.0
      // 247f: ldc_w -55.37
      // 2482: ldc_w -45.255
      // 2485: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2488: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 248b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 248e: aastore
      // 248f: dup
      // 2490: bipush 29
      // 2492: new net/minecraft/client/animation/Keyframe
      // 2495: dup
      // 2496: ldc 1.21
      // 2498: ldc_w 33.318
      // 249b: ldc_w -53.524
      // 249e: ldc_w -32.557
      // 24a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24aa: aastore
      // 24ab: dup
      // 24ac: bipush 30
      // 24ae: new net/minecraft/client/animation/Keyframe
      // 24b1: dup
      // 24b2: ldc 1.25
      // 24b4: ldc_w 22.595
      // 24b7: ldc_w -45.164
      // 24ba: ldc_w -21.081
      // 24bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24c6: aastore
      // 24c7: dup
      // 24c8: bipush 31
      // 24ca: new net/minecraft/client/animation/Keyframe
      // 24cd: dup
      // 24ce: ldc 1.29
      // 24d0: ldc_w 14.194
      // 24d3: ldc_w -29.297
      // 24d6: ldc_w -8.094
      // 24d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24e2: aastore
      // 24e3: dup
      // 24e4: bipush 32
      // 24e6: new net/minecraft/client/animation/Keyframe
      // 24e9: dup
      // 24ea: ldc 1.33
      // 24ec: ldc_w 12.201
      // 24ef: ldc_w -15.124
      // 24f2: ldc_w -1.799
      // 24f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24fe: aastore
      // 24ff: dup
      // 2500: bipush 33
      // 2502: new net/minecraft/client/animation/Keyframe
      // 2505: dup
      // 2506: ldc 1.38
      // 2508: ldc_w 12.925
      // 250b: ldc_w -6.604
      // 250e: ldc_w -1.803
      // 2511: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2514: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2517: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 251a: aastore
      // 251b: dup
      // 251c: bipush 34
      // 251e: new net/minecraft/client/animation/Keyframe
      // 2521: dup
      // 2522: ldc 1.42
      // 2524: ldc_w 10.66
      // 2527: ldc_w 0.559
      // 252a: ldc_w 0.145
      // 252d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2530: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2533: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2536: aastore
      // 2537: dup
      // 2538: bipush 35
      // 253a: new net/minecraft/client/animation/Keyframe
      // 253d: dup
      // 253e: ldc 1.46
      // 2540: ldc_w 6.257
      // 2543: ldc_w 7.997
      // 2546: ldc_w 5.219
      // 2549: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 254c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 254f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2552: aastore
      // 2553: dup
      // 2554: bipush 36
      // 2556: new net/minecraft/client/animation/Keyframe
      // 2559: dup
      // 255a: ldc 1.5
      // 255c: ldc_w 0.378
      // 255f: ldc_w 17.943
      // 2562: ldc_w 16.282
      // 2565: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2568: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 256b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 256e: aastore
      // 256f: dup
      // 2570: bipush 37
      // 2572: new net/minecraft/client/animation/Keyframe
      // 2575: dup
      // 2576: ldc 1.54
      // 2578: ldc_w -2.955
      // 257b: ldc_w 28.202
      // 257e: ldc_w 29.785
      // 2581: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2584: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2587: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 258a: aastore
      // 258b: dup
      // 258c: bipush 38
      // 258e: new net/minecraft/client/animation/Keyframe
      // 2591: dup
      // 2592: ldc 1.58
      // 2594: ldc_w -2.807
      // 2597: ldc_w 33.914
      // 259a: ldc_w 39.825
      // 259d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25a6: aastore
      // 25a7: dup
      // 25a8: bipush 39
      // 25aa: new net/minecraft/client/animation/Keyframe
      // 25ad: dup
      // 25ae: ldc 1.62
      // 25b0: ldc_w -2.014
      // 25b3: ldc_w 34.469
      // 25b6: ldc_w 45.176
      // 25b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25c2: aastore
      // 25c3: dup
      // 25c4: bipush 40
      // 25c6: new net/minecraft/client/animation/Keyframe
      // 25c9: dup
      // 25ca: ldc 1.67
      // 25cc: ldc_w -2.694
      // 25cf: ldc_w 30.658
      // 25d2: ldc_w 44.722
      // 25d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25de: aastore
      // 25df: dup
      // 25e0: bipush 41
      // 25e2: new net/minecraft/client/animation/Keyframe
      // 25e5: dup
      // 25e6: ldc 1.71
      // 25e8: ldc_w -4.866
      // 25eb: ldc_w 23.173
      // 25ee: ldc_w 38.362
      // 25f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25fa: aastore
      // 25fb: dup
      // 25fc: bipush 42
      // 25fe: new net/minecraft/client/animation/Keyframe
      // 2601: dup
      // 2602: ldc 1.75
      // 2604: ldc_w -6.689
      // 2607: ldc_w 13.109
      // 260a: ldc_w 26.499
      // 260d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2610: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2613: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2616: aastore
      // 2617: dup
      // 2618: bipush 43
      // 261a: new net/minecraft/client/animation/Keyframe
      // 261d: dup
      // 261e: ldc 1.79
      // 2620: ldc_w -5.17
      // 2623: ldc_w 2.212
      // 2626: ldc_w 10.318
      // 2629: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 262c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 262f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2632: aastore
      // 2633: dup
      // 2634: bipush 44
      // 2636: new net/minecraft/client/animation/Keyframe
      // 2639: dup
      // 263a: ldc 1.83
      // 263c: ldc_w 3.542
      // 263f: ldc_w -8.92
      // 2642: ldc_w -9.578
      // 2645: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2648: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 264b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 264e: aastore
      // 264f: dup
      // 2650: bipush 45
      // 2652: new net/minecraft/client/animation/Keyframe
      // 2655: dup
      // 2656: ldc 1.88
      // 2658: ldc_w -1.256
      // 265b: ldc_w -7.379
      // 265e: ldc_w -3.702
      // 2661: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 266a: aastore
      // 266b: dup
      // 266c: bipush 46
      // 266e: new net/minecraft/client/animation/Keyframe
      // 2671: dup
      // 2672: ldc 1.92
      // 2674: ldc_w -2.316
      // 2677: ldc_w -3.664
      // 267a: ldc_w -2.17
      // 267d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2680: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2683: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2686: aastore
      // 2687: dup
      // 2688: bipush 47
      // 268a: new net/minecraft/client/animation/Keyframe
      // 268d: dup
      // 268e: ldc_w 1.96
      // 2691: ldc_w -1.863
      // 2694: ldc_w -0.12
      // 2697: ldc_w -2.424
      // 269a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 269d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26a3: aastore
      // 26a4: dup
      // 26a5: bipush 48
      // 26a7: new net/minecraft/client/animation/Keyframe
      // 26aa: dup
      // 26ab: fconst_2
      // 26ac: ldc_w -1.973
      // 26af: ldc_w 0.81
      // 26b2: ldc_w -1.954
      // 26b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26be: aastore
      // 26bf: dup
      // 26c0: bipush 49
      // 26c2: new net/minecraft/client/animation/Keyframe
      // 26c5: dup
      // 26c6: ldc_w 2.04
      // 26c9: ldc_w -2.443
      // 26cc: ldc_w -0.663
      // 26cf: ldc_w -0.565
      // 26d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26db: aastore
      // 26dc: dup
      // 26dd: bipush 50
      // 26df: new net/minecraft/client/animation/Keyframe
      // 26e2: dup
      // 26e3: ldc_w 2.08
      // 26e6: ldc_w -2.098
      // 26e9: ldc_w -3.062
      // 26ec: ldc_w -0.064
      // 26ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 26f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26f8: aastore
      // 26f9: dup
      // 26fa: bipush 51
      // 26fc: new net/minecraft/client/animation/Keyframe
      // 26ff: dup
      // 2700: ldc_w 2.12
      // 2703: ldc_w -1.392
      // 2706: ldc_w -3.895
      // 2709: ldc_w -0.462
      // 270c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 270f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2712: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2715: aastore
      // 2716: dup
      // 2717: bipush 52
      // 2719: new net/minecraft/client/animation/Keyframe
      // 271c: dup
      // 271d: ldc 2.17
      // 271f: ldc_w -1.341
      // 2722: ldc_w -3.318
      // 2725: ldc_w -0.506
      // 2728: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 272b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 272e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2731: aastore
      // 2732: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2735: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2738: ldc_w "CTR_lowerarm_left"
      // 273b: new net/minecraft/client/animation/AnimationChannel
      // 273e: dup
      // 273f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2742: bipush 50
      // 2744: anewarray 28
      // 2747: dup
      // 2748: bipush 0
      // 2749: new net/minecraft/client/animation/Keyframe
      // 274c: dup
      // 274d: fconst_0
      // 274e: ldc_w 0.994
      // 2751: ldc_w -2.337
      // 2754: ldc_w -0.568
      // 2757: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 275a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 275d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2760: aastore
      // 2761: dup
      // 2762: bipush 1
      // 2763: new net/minecraft/client/animation/Keyframe
      // 2766: dup
      // 2767: ldc 0.04
      // 2769: ldc_w 3.929
      // 276c: ldc_w -14.434
      // 276f: ldc_w -3.357
      // 2772: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2775: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2778: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 277b: aastore
      // 277c: dup
      // 277d: bipush 2
      // 277e: new net/minecraft/client/animation/Keyframe
      // 2781: dup
      // 2782: ldc 0.08
      // 2784: ldc_w 4.393
      // 2787: ldc_w -26.488
      // 278a: ldc_w -5.674
      // 278d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2790: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2793: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2796: aastore
      // 2797: dup
      // 2798: bipush 3
      // 2799: new net/minecraft/client/animation/Keyframe
      // 279c: dup
      // 279d: ldc 0.12
      // 279f: ldc_w 3.734
      // 27a2: ldc_w -34.536
      // 27a5: ldc_w -6.833
      // 27a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27b1: aastore
      // 27b2: dup
      // 27b3: bipush 4
      // 27b4: new net/minecraft/client/animation/Keyframe
      // 27b7: dup
      // 27b8: ldc 0.17
      // 27ba: ldc_w 1.153
      // 27bd: ldc_w -36.95
      // 27c0: ldc_w -5.59
      // 27c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27cc: aastore
      // 27cd: dup
      // 27ce: bipush 5
      // 27cf: new net/minecraft/client/animation/Keyframe
      // 27d2: dup
      // 27d3: ldc 0.21
      // 27d5: ldc_w -1.871
      // 27d8: ldc_w -35.801
      // 27db: ldc_w -3.455
      // 27de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27e7: aastore
      // 27e8: dup
      // 27e9: bipush 6
      // 27eb: new net/minecraft/client/animation/Keyframe
      // 27ee: dup
      // 27ef: ldc 0.25
      // 27f1: ldc_w -4.059
      // 27f4: ldc_w -33.115
      // 27f7: ldc_w -1.841
      // 27fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2800: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2803: aastore
      // 2804: dup
      // 2805: bipush 7
      // 2807: new net/minecraft/client/animation/Keyframe
      // 280a: dup
      // 280b: ldc 0.29
      // 280d: ldc_w 6.6
      // 2810: ldc_w -47.511
      // 2813: ldc_w -12.446
      // 2816: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2819: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 281c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 281f: aastore
      // 2820: dup
      // 2821: bipush 8
      // 2823: new net/minecraft/client/animation/Keyframe
      // 2826: dup
      // 2827: ldc 0.33
      // 2829: ldc_w 19.347
      // 282c: ldc_w -51.252
      // 282f: ldc_w -25.275
      // 2832: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2835: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2838: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 283b: aastore
      // 283c: dup
      // 283d: bipush 9
      // 283f: new net/minecraft/client/animation/Keyframe
      // 2842: dup
      // 2843: ldc 0.38
      // 2845: ldc_w -8.146
      // 2848: ldc_w -12.718
      // 284b: ldc_w 1.256
      // 284e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2851: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2854: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2857: aastore
      // 2858: dup
      // 2859: bipush 10
      // 285b: new net/minecraft/client/animation/Keyframe
      // 285e: dup
      // 285f: ldc 0.42
      // 2861: ldc_w -10.784
      // 2864: ldc_w 33.652
      // 2867: ldc_w 2.391
      // 286a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 286d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2870: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2873: aastore
      // 2874: dup
      // 2875: bipush 11
      // 2877: new net/minecraft/client/animation/Keyframe
      // 287a: dup
      // 287b: ldc 0.58
      // 287d: ldc_w -10.784
      // 2880: ldc_w 33.648
      // 2883: ldc_w 2.459
      // 2886: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2889: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 288c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 288f: aastore
      // 2890: dup
      // 2891: bipush 12
      // 2893: new net/minecraft/client/animation/Keyframe
      // 2896: dup
      // 2897: ldc 0.62
      // 2899: ldc_w -10.15
      // 289c: ldc_w 20.17
      // 289f: ldc_w 2.41
      // 28a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28ab: aastore
      // 28ac: dup
      // 28ad: bipush 13
      // 28af: new net/minecraft/client/animation/Keyframe
      // 28b2: dup
      // 28b3: ldc 0.67
      // 28b5: ldc_w -9.523
      // 28b8: ldc_w 8.582
      // 28bb: ldc_w 2.234
      // 28be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28c7: aastore
      // 28c8: dup
      // 28c9: bipush 14
      // 28cb: new net/minecraft/client/animation/Keyframe
      // 28ce: dup
      // 28cf: ldc 0.71
      // 28d1: ldc_w -9.296
      // 28d4: ldc_w 5.276
      // 28d7: ldc_w 2.168
      // 28da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28e3: aastore
      // 28e4: dup
      // 28e5: bipush 15
      // 28e7: new net/minecraft/client/animation/Keyframe
      // 28ea: dup
      // 28eb: ldc 0.75
      // 28ed: ldc_w -9.32
      // 28f0: ldc_w 6.246
      // 28f3: ldc_w 2.219
      // 28f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28ff: aastore
      // 2900: dup
      // 2901: bipush 16
      // 2903: new net/minecraft/client/animation/Keyframe
      // 2906: dup
      // 2907: ldc 0.79
      // 2909: ldc_w -9.405
      // 290c: ldc_w 8.204
      // 290f: ldc_w 2.295
      // 2912: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2915: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2918: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 291b: aastore
      // 291c: dup
      // 291d: bipush 17
      // 291f: new net/minecraft/client/animation/Keyframe
      // 2922: dup
      // 2923: ldc 0.83
      // 2925: ldc_w -9.478
      // 2928: ldc_w 9.965
      // 292b: ldc_w 2.362
      // 292e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2931: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2934: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2937: aastore
      // 2938: dup
      // 2939: bipush 18
      // 293b: new net/minecraft/client/animation/Keyframe
      // 293e: dup
      // 293f: ldc 0.88
      // 2941: ldc_w -9.132
      // 2944: ldc_w 5.26
      // 2947: ldc_w 2.244
      // 294a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 294d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2950: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2953: aastore
      // 2954: dup
      // 2955: bipush 19
      // 2957: new net/minecraft/client/animation/Keyframe
      // 295a: dup
      // 295b: ldc 0.92
      // 295d: ldc_w -7.607
      // 2960: ldc_w -11.745
      // 2963: ldc_w 1.354
      // 2966: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 296c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 296f: aastore
      // 2970: dup
      // 2971: bipush 20
      // 2973: new net/minecraft/client/animation/Keyframe
      // 2976: dup
      // 2977: ldc 0.96
      // 2979: ldc_w -2.401
      // 297c: ldc_w -35.389
      // 297f: ldc_w -3.074
      // 2982: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2985: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2988: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 298b: aastore
      // 298c: dup
      // 298d: bipush 21
      // 298f: new net/minecraft/client/animation/Keyframe
      // 2992: dup
      // 2993: fconst_1
      // 2994: ldc_w 31.565
      // 2997: ldc_w -51.509
      // 299a: ldc_w -36.501
      // 299d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29a6: aastore
      // 29a7: dup
      // 29a8: bipush 22
      // 29aa: new net/minecraft/client/animation/Keyframe
      // 29ad: dup
      // 29ae: ldc 1.04
      // 29b0: ldc_w 16.435
      // 29b3: ldc_w -49.219
      // 29b6: ldc_w -21.425
      // 29b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29c2: aastore
      // 29c3: dup
      // 29c4: bipush 23
      // 29c6: new net/minecraft/client/animation/Keyframe
      // 29c9: dup
      // 29ca: ldc 1.08
      // 29cc: ldc_w 10.907
      // 29cf: ldc_w -6.683
      // 29d2: ldc_w 1.052
      // 29d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29de: aastore
      // 29df: dup
      // 29e0: bipush 24
      // 29e2: new net/minecraft/client/animation/Keyframe
      // 29e5: dup
      // 29e6: ldc 1.12
      // 29e8: ldc_w 32.905
      // 29eb: ldc_w -24.078
      // 29ee: ldc_w -15.924
      // 29f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29fa: aastore
      // 29fb: dup
      // 29fc: bipush 25
      // 29fe: new net/minecraft/client/animation/Keyframe
      // 2a01: dup
      // 2a02: ldc 1.17
      // 2a04: ldc_w 25.592
      // 2a07: ldc_w -19.879
      // 2a0a: ldc_w -10.079
      // 2a0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a16: aastore
      // 2a17: dup
      // 2a18: bipush 26
      // 2a1a: new net/minecraft/client/animation/Keyframe
      // 2a1d: dup
      // 2a1e: ldc 1.21
      // 2a20: ldc 16.738
      // 2a22: ldc_w -12.645
      // 2a25: ldc_w -3.609
      // 2a28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a31: aastore
      // 2a32: dup
      // 2a33: bipush 27
      // 2a35: new net/minecraft/client/animation/Keyframe
      // 2a38: dup
      // 2a39: ldc 1.25
      // 2a3b: ldc_w 10.648
      // 2a3e: ldc_w -5.701
      // 2a41: ldc_w 0.105
      // 2a44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a4d: aastore
      // 2a4e: dup
      // 2a4f: bipush 28
      // 2a51: new net/minecraft/client/animation/Keyframe
      // 2a54: dup
      // 2a55: ldc 1.29
      // 2a57: ldc_w 6.701
      // 2a5a: ldc_w -0.093
      // 2a5d: ldc_w 1.942
      // 2a60: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a69: aastore
      // 2a6a: dup
      // 2a6b: bipush 29
      // 2a6d: new net/minecraft/client/animation/Keyframe
      // 2a70: dup
      // 2a71: ldc 1.33
      // 2a73: ldc_w 1.066
      // 2a76: ldc_w 9.301
      // 2a79: ldc_w 4.122
      // 2a7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a85: aastore
      // 2a86: dup
      // 2a87: bipush 30
      // 2a89: new net/minecraft/client/animation/Keyframe
      // 2a8c: dup
      // 2a8d: ldc 1.38
      // 2a8f: ldc_w -6.554
      // 2a92: ldc 24.501
      // 2a94: ldc_w 5.754
      // 2a97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aa0: aastore
      // 2aa1: dup
      // 2aa2: bipush 31
      // 2aa4: new net/minecraft/client/animation/Keyframe
      // 2aa7: dup
      // 2aa8: ldc 1.42
      // 2aaa: ldc_w -10.753
      // 2aad: ldc_w 33.623
      // 2ab0: ldc_w 5.523
      // 2ab3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ab6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ab9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2abc: aastore
      // 2abd: dup
      // 2abe: bipush 32
      // 2ac0: new net/minecraft/client/animation/Keyframe
      // 2ac3: dup
      // 2ac4: ldc 1.46
      // 2ac6: ldc_w -10.752
      // 2ac9: ldc_w 33.621
      // 2acc: ldc_w 4.999
      // 2acf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ad2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ad5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ad8: aastore
      // 2ad9: dup
      // 2ada: bipush 33
      // 2adc: new net/minecraft/client/animation/Keyframe
      // 2adf: dup
      // 2ae0: ldc 1.5
      // 2ae2: ldc_w -3.506
      // 2ae5: ldc_w 18.146
      // 2ae8: ldc_w 3.544
      // 2aeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2af1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2af4: aastore
      // 2af5: dup
      // 2af6: bipush 34
      // 2af8: new net/minecraft/client/animation/Keyframe
      // 2afb: dup
      // 2afc: ldc 1.54
      // 2afe: ldc_w 8.981
      // 2b01: ldc_w -3.199
      // 2b04: ldc_w -2.501
      // 2b07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b10: aastore
      // 2b11: dup
      // 2b12: bipush 35
      // 2b14: new net/minecraft/client/animation/Keyframe
      // 2b17: dup
      // 2b18: ldc 1.58
      // 2b1a: ldc_w 23.413
      // 2b1d: ldc_w -18.093
      // 2b20: ldc_w -13.145
      // 2b23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b2c: aastore
      // 2b2d: dup
      // 2b2e: bipush 36
      // 2b30: new net/minecraft/client/animation/Keyframe
      // 2b33: dup
      // 2b34: ldc 1.62
      // 2b36: ldc_w 40.87
      // 2b39: ldc_w -27.527
      // 2b3c: ldc_w -28.353
      // 2b3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b48: aastore
      // 2b49: dup
      // 2b4a: bipush 37
      // 2b4c: new net/minecraft/client/animation/Keyframe
      // 2b4f: dup
      // 2b50: ldc 1.67
      // 2b52: ldc_w 59.258
      // 2b55: ldc_w -32.489
      // 2b58: ldc_w -45.647
      // 2b5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b64: aastore
      // 2b65: dup
      // 2b66: bipush 38
      // 2b68: new net/minecraft/client/animation/Keyframe
      // 2b6b: dup
      // 2b6c: ldc 1.71
      // 2b6e: ldc_w 69.895
      // 2b71: ldc_w -34.519
      // 2b74: ldc_w -56.47
      // 2b77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b80: aastore
      // 2b81: dup
      // 2b82: bipush 39
      // 2b84: new net/minecraft/client/animation/Keyframe
      // 2b87: dup
      // 2b88: ldc 1.75
      // 2b8a: ldc_w 62.888
      // 2b8d: ldc_w -35.252
      // 2b90: ldc_w -51.151
      // 2b93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b9c: aastore
      // 2b9d: dup
      // 2b9e: bipush 40
      // 2ba0: new net/minecraft/client/animation/Keyframe
      // 2ba3: dup
      // 2ba4: ldc 1.79
      // 2ba6: ldc_w 32.593
      // 2ba9: ldc_w -29.904
      // 2bac: ldc_w -24.44
      // 2baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bb8: aastore
      // 2bb9: dup
      // 2bba: bipush 41
      // 2bbc: new net/minecraft/client/animation/Keyframe
      // 2bbf: dup
      // 2bc0: ldc 1.83
      // 2bc2: ldc 1.67
      // 2bc4: ldc_w -0.934
      // 2bc7: ldc_w -0.631
      // 2bca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bcd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bd0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bd3: aastore
      // 2bd4: dup
      // 2bd5: bipush 42
      // 2bd7: new net/minecraft/client/animation/Keyframe
      // 2bda: dup
      // 2bdb: ldc 1.88
      // 2bdd: ldc_w 4.463
      // 2be0: ldc_w -8.545
      // 2be3: ldc_w -2.648
      // 2be6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2be9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bef: aastore
      // 2bf0: dup
      // 2bf1: bipush 43
      // 2bf3: new net/minecraft/client/animation/Keyframe
      // 2bf6: dup
      // 2bf7: ldc 1.92
      // 2bf9: ldc_w 4.921
      // 2bfc: ldc_w -10.271
      // 2bff: ldc_w -3.099
      // 2c02: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c0b: aastore
      // 2c0c: dup
      // 2c0d: bipush 44
      // 2c0f: new net/minecraft/client/animation/Keyframe
      // 2c12: dup
      // 2c13: ldc_w 1.96
      // 2c16: ldc_w 4.204
      // 2c19: ldc_w -8.968
      // 2c1c: ldc_w -2.607
      // 2c1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c28: aastore
      // 2c29: dup
      // 2c2a: bipush 45
      // 2c2c: new net/minecraft/client/animation/Keyframe
      // 2c2f: dup
      // 2c30: fconst_2
      // 2c31: ldc_w 3.579
      // 2c34: ldc_w -7.782
      // 2c37: ldc_w -2.189
      // 2c3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c43: aastore
      // 2c44: dup
      // 2c45: bipush 46
      // 2c47: new net/minecraft/client/animation/Keyframe
      // 2c4a: dup
      // 2c4b: ldc_w 2.04
      // 2c4e: ldc_w 3.035
      // 2c51: ldc_w -6.709
      // 2c54: ldc_w -1.832
      // 2c57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c60: aastore
      // 2c61: dup
      // 2c62: bipush 47
      // 2c64: new net/minecraft/client/animation/Keyframe
      // 2c67: dup
      // 2c68: ldc_w 2.08
      // 2c6b: ldc 1.75
      // 2c6d: ldc_w -4.021
      // 2c70: ldc_w -1.022
      // 2c73: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c7c: aastore
      // 2c7d: dup
      // 2c7e: bipush 48
      // 2c80: new net/minecraft/client/animation/Keyframe
      // 2c83: dup
      // 2c84: ldc_w 2.12
      // 2c87: ldc_w 0.683
      // 2c8a: ldc_w -1.622
      // 2c8d: ldc_w -0.387
      // 2c90: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c93: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c96: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c99: aastore
      // 2c9a: dup
      // 2c9b: bipush 49
      // 2c9d: new net/minecraft/client/animation/Keyframe
      // 2ca0: dup
      // 2ca1: ldc 2.17
      // 2ca3: ldc_w 0.617
      // 2ca6: ldc_w -1.468
      // 2ca9: ldc_w -0.349
      // 2cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cb5: aastore
      // 2cb6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2cb9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2cbc: ldc_w "CTR_upperarm_left.001"
      // 2cbf: new net/minecraft/client/animation/AnimationChannel
      // 2cc2: dup
      // 2cc3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2cc6: bipush 53
      // 2cc8: anewarray 28
      // 2ccb: dup
      // 2ccc: bipush 0
      // 2ccd: new net/minecraft/client/animation/Keyframe
      // 2cd0: dup
      // 2cd1: fconst_0
      // 2cd2: ldc_w 3.204
      // 2cd5: ldc_w -22.398
      // 2cd8: ldc_w -1.822
      // 2cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ce4: aastore
      // 2ce5: dup
      // 2ce6: bipush 1
      // 2ce7: new net/minecraft/client/animation/Keyframe
      // 2cea: dup
      // 2ceb: ldc 0.04
      // 2ced: ldc_w 4.301
      // 2cf0: ldc_w -25.873
      // 2cf3: ldc_w -1.983
      // 2cf6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cf9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cfc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cff: aastore
      // 2d00: dup
      // 2d01: bipush 2
      // 2d02: new net/minecraft/client/animation/Keyframe
      // 2d05: dup
      // 2d06: ldc 0.08
      // 2d08: ldc_w 4.043
      // 2d0b: ldc_w -24.285
      // 2d0e: ldc_w -1.609
      // 2d11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d1a: aastore
      // 2d1b: dup
      // 2d1c: bipush 3
      // 2d1d: new net/minecraft/client/animation/Keyframe
      // 2d20: dup
      // 2d21: ldc 0.12
      // 2d23: ldc_w 2.71
      // 2d26: ldc_w -15.538
      // 2d29: ldc_w -1.432
      // 2d2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d35: aastore
      // 2d36: dup
      // 2d37: bipush 4
      // 2d38: new net/minecraft/client/animation/Keyframe
      // 2d3b: dup
      // 2d3c: ldc 0.17
      // 2d3e: ldc_w -3.351
      // 2d41: ldc_w -1.904
      // 2d44: ldc_w -5.899
      // 2d47: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d50: aastore
      // 2d51: dup
      // 2d52: bipush 5
      // 2d53: new net/minecraft/client/animation/Keyframe
      // 2d56: dup
      // 2d57: ldc 0.21
      // 2d59: ldc_w 6.107
      // 2d5c: ldc_w 7.826
      // 2d5f: ldc_w 4.539
      // 2d62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d6b: aastore
      // 2d6c: dup
      // 2d6d: bipush 6
      // 2d6f: new net/minecraft/client/animation/Keyframe
      // 2d72: dup
      // 2d73: ldc 0.25
      // 2d75: ldc_w 4.341
      // 2d78: ldc_w 11.251
      // 2d7b: ldc_w 3.182
      // 2d7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d87: aastore
      // 2d88: dup
      // 2d89: bipush 7
      // 2d8b: new net/minecraft/client/animation/Keyframe
      // 2d8e: dup
      // 2d8f: ldc 0.29
      // 2d91: ldc_w 4.411
      // 2d94: ldc_w 12.182
      // 2d97: ldc_w 2.945
      // 2d9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2da0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2da3: aastore
      // 2da4: dup
      // 2da5: bipush 8
      // 2da7: new net/minecraft/client/animation/Keyframe
      // 2daa: dup
      // 2dab: ldc 0.33
      // 2dad: ldc_w 2.594
      // 2db0: ldc_w 25.423
      // 2db3: ldc_w 2.084
      // 2db6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2db9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dbf: aastore
      // 2dc0: dup
      // 2dc1: bipush 9
      // 2dc3: new net/minecraft/client/animation/Keyframe
      // 2dc6: dup
      // 2dc7: ldc 0.38
      // 2dc9: ldc_w 1.086
      // 2dcc: ldc_w 43.962
      // 2dcf: ldc_w 1.987
      // 2dd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ddb: aastore
      // 2ddc: dup
      // 2ddd: bipush 10
      // 2ddf: new net/minecraft/client/animation/Keyframe
      // 2de2: dup
      // 2de3: ldc 0.42
      // 2de5: ldc_w 0.661
      // 2de8: ldc_w 56.607
      // 2deb: ldc_w 1.986
      // 2dee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2df1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2df4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2df7: aastore
      // 2df8: dup
      // 2df9: bipush 11
      // 2dfb: new net/minecraft/client/animation/Keyframe
      // 2dfe: dup
      // 2dff: ldc 0.46
      // 2e01: ldc_w 0.747
      // 2e04: ldc_w 53.971
      // 2e07: ldc_w 1.937
      // 2e0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e13: aastore
      // 2e14: dup
      // 2e15: bipush 12
      // 2e17: new net/minecraft/client/animation/Keyframe
      // 2e1a: dup
      // 2e1b: ldc 0.5
      // 2e1d: ldc_w 0.837
      // 2e20: ldc_w 51.121
      // 2e23: ldc_w 1.893
      // 2e26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e2f: aastore
      // 2e30: dup
      // 2e31: bipush 13
      // 2e33: new net/minecraft/client/animation/Keyframe
      // 2e36: dup
      // 2e37: ldc 0.54
      // 2e39: ldc_w 0.93
      // 2e3c: ldc_w 48.136
      // 2e3f: ldc_w 1.859
      // 2e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e4b: aastore
      // 2e4c: dup
      // 2e4d: bipush 14
      // 2e4f: new net/minecraft/client/animation/Keyframe
      // 2e52: dup
      // 2e53: ldc 0.58
      // 2e55: ldc_w 1.034
      // 2e58: ldc_w 44.817
      // 2e5b: ldc_w 1.834
      // 2e5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e67: aastore
      // 2e68: dup
      // 2e69: bipush 15
      // 2e6b: new net/minecraft/client/animation/Keyframe
      // 2e6e: dup
      // 2e6f: ldc 0.62
      // 2e71: ldc_w 1.335
      // 2e74: ldc_w 35.998
      // 2e77: ldc_w 1.839
      // 2e7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e83: aastore
      // 2e84: dup
      // 2e85: bipush 16
      // 2e87: new net/minecraft/client/animation/Keyframe
      // 2e8a: dup
      // 2e8b: ldc 0.67
      // 2e8d: ldc_w 1.865
      // 2e90: ldc_w 25.38
      // 2e93: ldc_w 1.985
      // 2e96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e9f: aastore
      // 2ea0: dup
      // 2ea1: bipush 17
      // 2ea3: new net/minecraft/client/animation/Keyframe
      // 2ea6: dup
      // 2ea7: ldc 0.71
      // 2ea9: ldc_w 2.764
      // 2eac: ldc_w 15.488
      // 2eaf: ldc_w 2.586
      // 2eb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2eb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ebb: aastore
      // 2ebc: dup
      // 2ebd: bipush 18
      // 2ebf: new net/minecraft/client/animation/Keyframe
      // 2ec2: dup
      // 2ec3: ldc 0.75
      // 2ec5: ldc_w 6.602
      // 2ec8: ldc_w 6.175
      // 2ecb: ldc_w 6.24
      // 2ece: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ed1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ed4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ed7: aastore
      // 2ed8: dup
      // 2ed9: bipush 19
      // 2edb: new net/minecraft/client/animation/Keyframe
      // 2ede: dup
      // 2edf: ldc 0.79
      // 2ee1: ldc_w -4.189
      // 2ee4: ldc_w -2.728
      // 2ee7: ldc_w -4.652
      // 2eea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2eed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ef0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ef3: aastore
      // 2ef4: dup
      // 2ef5: bipush 20
      // 2ef7: new net/minecraft/client/animation/Keyframe
      // 2efa: dup
      // 2efb: ldc 0.83
      // 2efd: ldc_w -0.661
      // 2f00: ldc_w -11.297
      // 2f03: ldc_w -1.176
      // 2f06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f0f: aastore
      // 2f10: dup
      // 2f11: bipush 21
      // 2f13: new net/minecraft/client/animation/Keyframe
      // 2f16: dup
      // 2f17: ldc 0.88
      // 2f19: ldc_w 0.329
      // 2f1c: ldc_w -22.555
      // 2f1f: ldc_w -0.39
      // 2f22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f2b: aastore
      // 2f2c: dup
      // 2f2d: bipush 22
      // 2f2f: new net/minecraft/client/animation/Keyframe
      // 2f32: dup
      // 2f33: ldc 0.92
      // 2f35: ldc_w 1.256
      // 2f38: ldc_w -38.939
      // 2f3b: ldc_w -0.245
      // 2f3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f47: aastore
      // 2f48: dup
      // 2f49: bipush 23
      // 2f4b: new net/minecraft/client/animation/Keyframe
      // 2f4e: dup
      // 2f4f: ldc 0.96
      // 2f51: ldc_w 2.643
      // 2f54: ldc_w -54.418
      // 2f57: ldc_w -0.597
      // 2f5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f63: aastore
      // 2f64: dup
      // 2f65: bipush 24
      // 2f67: new net/minecraft/client/animation/Keyframe
      // 2f6a: dup
      // 2f6b: fconst_1
      // 2f6c: ldc_w 4.06
      // 2f6f: ldc_w -53.106
      // 2f72: ldc_w -0.972
      // 2f75: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f7e: aastore
      // 2f7f: dup
      // 2f80: bipush 25
      // 2f82: new net/minecraft/client/animation/Keyframe
      // 2f85: dup
      // 2f86: ldc 1.04
      // 2f88: ldc_w 2.98
      // 2f8b: ldc_w -25.426
      // 2f8e: ldc_w -0.907
      // 2f91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f9a: aastore
      // 2f9b: dup
      // 2f9c: bipush 26
      // 2f9e: new net/minecraft/client/animation/Keyframe
      // 2fa1: dup
      // 2fa2: ldc 1.08
      // 2fa4: ldc_w 2.888
      // 2fa7: ldc_w 23.174
      // 2faa: fconst_1
      // 2fab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fb4: aastore
      // 2fb5: dup
      // 2fb6: bipush 27
      // 2fb8: new net/minecraft/client/animation/Keyframe
      // 2fbb: dup
      // 2fbc: ldc 1.12
      // 2fbe: ldc_w 12.142
      // 2fc1: ldc_w -7.441
      // 2fc4: ldc_w -5.743
      // 2fc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fcd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fd0: aastore
      // 2fd1: dup
      // 2fd2: bipush 28
      // 2fd4: new net/minecraft/client/animation/Keyframe
      // 2fd7: dup
      // 2fd8: ldc 1.17
      // 2fda: ldc_w 12.213
      // 2fdd: ldc_w -21.715
      // 2fe0: ldc_w -7.529
      // 2fe3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fe6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fe9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fec: aastore
      // 2fed: dup
      // 2fee: bipush 29
      // 2ff0: new net/minecraft/client/animation/Keyframe
      // 2ff3: dup
      // 2ff4: ldc 1.21
      // 2ff6: ldc_w 9.697
      // 2ff9: ldc_w -28.217
      // 2ffc: ldc_w -7.014
      // 2fff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3002: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3005: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3008: aastore
      // 3009: dup
      // 300a: bipush 30
      // 300c: new net/minecraft/client/animation/Keyframe
      // 300f: dup
      // 3010: ldc 1.25
      // 3012: ldc_w 7.585
      // 3015: ldc_w -32.202
      // 3018: ldc_w -6.011
      // 301b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 301e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3021: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3024: aastore
      // 3025: dup
      // 3026: bipush 31
      // 3028: new net/minecraft/client/animation/Keyframe
      // 302b: dup
      // 302c: ldc 1.29
      // 302e: ldc_w 6.329
      // 3031: ldc_w -36.881
      // 3034: ldc_w -5.214
      // 3037: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 303a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 303d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3040: aastore
      // 3041: dup
      // 3042: bipush 32
      // 3044: new net/minecraft/client/animation/Keyframe
      // 3047: dup
      // 3048: ldc 1.33
      // 304a: ldc_w 4.011
      // 304d: ldc_w -36.444
      // 3050: ldc_w -3.588
      // 3053: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3056: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3059: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 305c: aastore
      // 305d: dup
      // 305e: bipush 33
      // 3060: new net/minecraft/client/animation/Keyframe
      // 3063: dup
      // 3064: ldc 1.38
      // 3066: ldc_w 0.933
      // 3069: ldc_w -29.574
      // 306c: ldc_w -1.362
      // 306f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3072: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3075: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3078: aastore
      // 3079: dup
      // 307a: bipush 34
      // 307c: new net/minecraft/client/animation/Keyframe
      // 307f: dup
      // 3080: ldc 1.42
      // 3082: ldc_w -0.41
      // 3085: ldc_w -25.11
      // 3088: ldc_w -0.34
      // 308b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 308e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3091: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3094: aastore
      // 3095: dup
      // 3096: bipush 35
      // 3098: new net/minecraft/client/animation/Keyframe
      // 309b: dup
      // 309c: ldc 1.46
      // 309e: ldc_w -0.345
      // 30a1: ldc_w -24.781
      // 30a4: ldc_w -0.27
      // 30a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30b0: aastore
      // 30b1: dup
      // 30b2: bipush 36
      // 30b4: new net/minecraft/client/animation/Keyframe
      // 30b7: dup
      // 30b8: ldc 1.5
      // 30ba: ldc_w 2.245
      // 30bd: ldc_w -32.397
      // 30c0: ldc_w -1.807
      // 30c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30cc: aastore
      // 30cd: dup
      // 30ce: bipush 37
      // 30d0: new net/minecraft/client/animation/Keyframe
      // 30d3: dup
      // 30d4: ldc 1.54
      // 30d6: ldc_w 7.482
      // 30d9: ldc_w -42.22
      // 30dc: ldc -4.86
      // 30de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30e7: aastore
      // 30e8: dup
      // 30e9: bipush 38
      // 30eb: new net/minecraft/client/animation/Keyframe
      // 30ee: dup
      // 30ef: ldc 1.58
      // 30f1: ldc_w 12.881
      // 30f4: ldc_w -46.95
      // 30f7: ldc_w -7.393
      // 30fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3100: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3103: aastore
      // 3104: dup
      // 3105: bipush 39
      // 3107: new net/minecraft/client/animation/Keyframe
      // 310a: dup
      // 310b: ldc 1.62
      // 310d: ldc_w 17.948
      // 3110: ldc_w -47.948
      // 3113: ldc_w -9.034
      // 3116: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3119: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 311c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 311f: aastore
      // 3120: dup
      // 3121: bipush 40
      // 3123: new net/minecraft/client/animation/Keyframe
      // 3126: dup
      // 3127: ldc 1.67
      // 3129: ldc_w 21.995
      // 312c: ldc_w -45.396
      // 312f: ldc_w -9.424
      // 3132: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3135: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3138: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 313b: aastore
      // 313c: dup
      // 313d: bipush 41
      // 313f: new net/minecraft/client/animation/Keyframe
      // 3142: dup
      // 3143: ldc 1.71
      // 3145: ldc_w 23.089
      // 3148: ldc_w -39.131
      // 314b: ldc_w -8.154
      // 314e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3151: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3154: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3157: aastore
      // 3158: dup
      // 3159: bipush 42
      // 315b: new net/minecraft/client/animation/Keyframe
      // 315e: dup
      // 315f: ldc 1.75
      // 3161: ldc_w 18.844
      // 3164: ldc_w -28.415
      // 3167: ldc_w -5.451
      // 316a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 316d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3173: aastore
      // 3174: dup
      // 3175: bipush 43
      // 3177: new net/minecraft/client/animation/Keyframe
      // 317a: dup
      // 317b: ldc 1.79
      // 317d: ldc_w 9.924
      // 3180: ldc_w -14.53
      // 3183: ldc_w -3.117
      // 3186: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3189: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 318c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 318f: aastore
      // 3190: dup
      // 3191: bipush 44
      // 3193: new net/minecraft/client/animation/Keyframe
      // 3196: dup
      // 3197: ldc 1.83
      // 3199: ldc_w -49.58
      // 319c: ldc_w 1.307
      // 319f: ldc_w -52.66
      // 31a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31ab: aastore
      // 31ac: dup
      // 31ad: bipush 45
      // 31af: new net/minecraft/client/animation/Keyframe
      // 31b2: dup
      // 31b3: ldc 1.88
      // 31b5: ldc_w 2.694
      // 31b8: ldc_w -10.389
      // 31bb: ldc_w -2.424
      // 31be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31c7: aastore
      // 31c8: dup
      // 31c9: bipush 46
      // 31cb: new net/minecraft/client/animation/Keyframe
      // 31ce: dup
      // 31cf: ldc 1.92
      // 31d1: ldc_w 3.935
      // 31d4: ldc_w -18.292
      // 31d7: ldc_w -2.147
      // 31da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31e3: aastore
      // 31e4: dup
      // 31e5: bipush 47
      // 31e7: new net/minecraft/client/animation/Keyframe
      // 31ea: dup
      // 31eb: ldc_w 1.96
      // 31ee: ldc_w 4.117
      // 31f1: ldc_w -22.361
      // 31f4: ldc_w -2.119
      // 31f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3200: aastore
      // 3201: dup
      // 3202: bipush 48
      // 3204: new net/minecraft/client/animation/Keyframe
      // 3207: dup
      // 3208: fconst_2
      // 3209: ldc_w 4.053
      // 320c: ldc_w -23.736
      // 320f: ldc_w -2.084
      // 3212: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3215: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3218: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 321b: aastore
      // 321c: dup
      // 321d: bipush 49
      // 321f: new net/minecraft/client/animation/Keyframe
      // 3222: dup
      // 3223: ldc_w 2.04
      // 3226: ldc_w 3.835
      // 3229: ldc_w -22.934
      // 322c: ldc_w -2.025
      // 322f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3232: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3235: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3238: aastore
      // 3239: dup
      // 323a: bipush 50
      // 323c: new net/minecraft/client/animation/Keyframe
      // 323f: dup
      // 3240: ldc_w 2.08
      // 3243: ldc_w 3.349
      // 3246: ldc_w -21.437
      // 3249: ldc_w -1.896
      // 324c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 324f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3252: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3255: aastore
      // 3256: dup
      // 3257: bipush 51
      // 3259: new net/minecraft/client/animation/Keyframe
      // 325c: dup
      // 325d: ldc_w 2.12
      // 3260: ldc_w 3.02
      // 3263: ldc_w -21.175
      // 3266: ldc_w -1.79
      // 3269: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 326c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 326f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3272: aastore
      // 3273: dup
      // 3274: bipush 52
      // 3276: new net/minecraft/client/animation/Keyframe
      // 3279: dup
      // 327a: ldc 2.17
      // 327c: ldc_w 3.045
      // 327f: ldc_w -21.748
      // 3282: ldc_w -1.782
      // 3285: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3288: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 328b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 328e: aastore
      // 328f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3292: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3295: ldc_w "CTR_upperarm_left.001"
      // 3298: new net/minecraft/client/animation/AnimationChannel
      // 329b: dup
      // 329c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 329f: bipush 50
      // 32a1: anewarray 28
      // 32a4: dup
      // 32a5: bipush 0
      // 32a6: new net/minecraft/client/animation/Keyframe
      // 32a9: dup
      // 32aa: fconst_0
      // 32ab: dconst_1
      // 32ac: dconst_1
      // 32ad: ldc2_w 1.7869999408721924
      // 32b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32b9: aastore
      // 32ba: dup
      // 32bb: bipush 1
      // 32bc: new net/minecraft/client/animation/Keyframe
      // 32bf: dup
      // 32c0: ldc 0.04
      // 32c2: dconst_1
      // 32c3: dconst_1
      // 32c4: ldc2_w 1.6740000247955322
      // 32c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32d0: aastore
      // 32d1: dup
      // 32d2: bipush 2
      // 32d3: new net/minecraft/client/animation/Keyframe
      // 32d6: dup
      // 32d7: ldc 0.12
      // 32d9: dconst_1
      // 32da: dconst_1
      // 32db: ldc2_w 1.3459999561309814
      // 32de: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32e7: aastore
      // 32e8: dup
      // 32e9: bipush 3
      // 32ea: new net/minecraft/client/animation/Keyframe
      // 32ed: dup
      // 32ee: ldc 0.17
      // 32f0: dconst_1
      // 32f1: dconst_1
      // 32f2: ldc2_w 1.2309999465942383
      // 32f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 32f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32fe: aastore
      // 32ff: dup
      // 3300: bipush 4
      // 3301: new net/minecraft/client/animation/Keyframe
      // 3304: dup
      // 3305: ldc 0.21
      // 3307: dconst_1
      // 3308: dconst_1
      // 3309: ldc2_w 1.1640000343322754
      // 330c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 330f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3312: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3315: aastore
      // 3316: dup
      // 3317: bipush 5
      // 3318: new net/minecraft/client/animation/Keyframe
      // 331b: dup
      // 331c: ldc 0.25
      // 331e: dconst_1
      // 331f: dconst_1
      // 3320: ldc2_w 1.1160000562667847
      // 3323: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3326: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3329: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 332c: aastore
      // 332d: dup
      // 332e: bipush 6
      // 3330: new net/minecraft/client/animation/Keyframe
      // 3333: dup
      // 3334: ldc 0.29
      // 3336: dconst_1
      // 3337: dconst_1
      // 3338: ldc2_w 1.0379999876022339
      // 333b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 333e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3341: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3344: aastore
      // 3345: dup
      // 3346: bipush 7
      // 3348: new net/minecraft/client/animation/Keyframe
      // 334b: dup
      // 334c: ldc 0.33
      // 334e: dconst_1
      // 334f: dconst_1
      // 3350: ldc2_w 1.1460000276565552
      // 3353: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3356: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3359: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 335c: aastore
      // 335d: dup
      // 335e: bipush 8
      // 3360: new net/minecraft/client/animation/Keyframe
      // 3363: dup
      // 3364: ldc 0.38
      // 3366: dconst_1
      // 3367: dconst_1
      // 3368: ldc2_w 1.3539999723434448
      // 336b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 336e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3371: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3374: aastore
      // 3375: dup
      // 3376: bipush 9
      // 3378: new net/minecraft/client/animation/Keyframe
      // 337b: dup
      // 337c: ldc 0.42
      // 337e: dconst_1
      // 337f: dconst_1
      // 3380: ldc2_w 1.4780000448226929
      // 3383: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3386: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3389: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 338c: aastore
      // 338d: dup
      // 338e: bipush 10
      // 3390: new net/minecraft/client/animation/Keyframe
      // 3393: dup
      // 3394: ldc 0.46
      // 3396: dconst_1
      // 3397: dconst_1
      // 3398: ldc2_w 1.4170000553131104
      // 339b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 339e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33a4: aastore
      // 33a5: dup
      // 33a6: bipush 11
      // 33a8: new net/minecraft/client/animation/Keyframe
      // 33ab: dup
      // 33ac: ldc 0.5
      // 33ae: dconst_1
      // 33af: dconst_1
      // 33b0: ldc2_w 1.3880000114440918
      // 33b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33bc: aastore
      // 33bd: dup
      // 33be: bipush 12
      // 33c0: new net/minecraft/client/animation/Keyframe
      // 33c3: dup
      // 33c4: ldc 0.54
      // 33c6: dconst_1
      // 33c7: dconst_1
      // 33c8: ldc2_w 1.3830000162124634
      // 33cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33d4: aastore
      // 33d5: dup
      // 33d6: bipush 13
      // 33d8: new net/minecraft/client/animation/Keyframe
      // 33db: dup
      // 33dc: ldc 0.58
      // 33de: dconst_1
      // 33df: dconst_1
      // 33e0: ldc2_w 1.3969999551773071
      // 33e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33ec: aastore
      // 33ed: dup
      // 33ee: bipush 14
      // 33f0: new net/minecraft/client/animation/Keyframe
      // 33f3: dup
      // 33f4: ldc 0.62
      // 33f6: dconst_1
      // 33f7: dconst_1
      // 33f8: ldc2_w 1.350000023841858
      // 33fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 33fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3401: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3404: aastore
      // 3405: dup
      // 3406: bipush 15
      // 3408: new net/minecraft/client/animation/Keyframe
      // 340b: dup
      // 340c: ldc 0.67
      // 340e: dconst_1
      // 340f: dconst_1
      // 3410: ldc2_w 1.315999984741211
      // 3413: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3416: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3419: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 341c: aastore
      // 341d: dup
      // 341e: bipush 16
      // 3420: new net/minecraft/client/animation/Keyframe
      // 3423: dup
      // 3424: ldc 0.71
      // 3426: dconst_1
      // 3427: dconst_1
      // 3428: ldc2_w 1.343999981880188
      // 342b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 342e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3431: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3434: aastore
      // 3435: dup
      // 3436: bipush 17
      // 3438: new net/minecraft/client/animation/Keyframe
      // 343b: dup
      // 343c: ldc 0.75
      // 343e: dconst_1
      // 343f: dconst_1
      // 3440: ldc2_w 1.4210000038146973
      // 3443: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3446: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3449: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 344c: aastore
      // 344d: dup
      // 344e: bipush 18
      // 3450: new net/minecraft/client/animation/Keyframe
      // 3453: dup
      // 3454: ldc 0.79
      // 3456: dconst_1
      // 3457: dconst_1
      // 3458: ldc2_w 1.527999997138977
      // 345b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 345e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3461: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3464: aastore
      // 3465: dup
      // 3466: bipush 19
      // 3468: new net/minecraft/client/animation/Keyframe
      // 346b: dup
      // 346c: ldc 0.83
      // 346e: dconst_1
      // 346f: dconst_1
      // 3470: ldc2_w 1.6460000276565552
      // 3473: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3476: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3479: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 347c: aastore
      // 347d: dup
      // 347e: bipush 20
      // 3480: new net/minecraft/client/animation/Keyframe
      // 3483: dup
      // 3484: ldc 0.88
      // 3486: dconst_1
      // 3487: dconst_1
      // 3488: ldc2_w 1.725000023841858
      // 348b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 348e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3491: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3494: aastore
      // 3495: dup
      // 3496: bipush 21
      // 3498: new net/minecraft/client/animation/Keyframe
      // 349b: dup
      // 349c: ldc 0.92
      // 349e: dconst_1
      // 349f: dconst_1
      // 34a0: ldc2_w 1.7009999752044678
      // 34a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34ac: aastore
      // 34ad: dup
      // 34ae: bipush 22
      // 34b0: new net/minecraft/client/animation/Keyframe
      // 34b3: dup
      // 34b4: ldc 0.96
      // 34b6: dconst_1
      // 34b7: dconst_1
      // 34b8: ldc2_w 1.5770000219345093
      // 34bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c4: aastore
      // 34c5: dup
      // 34c6: bipush 23
      // 34c8: new net/minecraft/client/animation/Keyframe
      // 34cb: dup
      // 34cc: fconst_1
      // 34cd: dconst_1
      // 34ce: dconst_1
      // 34cf: ldc2_w 1.3669999837875366
      // 34d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34db: aastore
      // 34dc: dup
      // 34dd: bipush 24
      // 34df: new net/minecraft/client/animation/Keyframe
      // 34e2: dup
      // 34e3: ldc 1.04
      // 34e5: dconst_1
      // 34e6: dconst_1
      // 34e7: ldc2_w 1.2009999752044678
      // 34ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 34ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34f3: aastore
      // 34f4: dup
      // 34f5: bipush 25
      // 34f7: new net/minecraft/client/animation/Keyframe
      // 34fa: dup
      // 34fb: ldc 1.08
      // 34fd: dconst_1
      // 34fe: dconst_1
      // 34ff: ldc2_w 1.5160000324249268
      // 3502: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3505: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3508: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 350b: aastore
      // 350c: dup
      // 350d: bipush 26
      // 350f: new net/minecraft/client/animation/Keyframe
      // 3512: dup
      // 3513: ldc 1.12
      // 3515: dconst_1
      // 3516: dconst_1
      // 3517: ldc2_w 1.1160000562667847
      // 351a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 351d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3523: aastore
      // 3524: dup
      // 3525: bipush 27
      // 3527: new net/minecraft/client/animation/Keyframe
      // 352a: dup
      // 352b: ldc 1.17
      // 352d: dconst_1
      // 352e: dconst_1
      // 352f: ldc2_w 1.1929999589920044
      // 3532: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3535: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3538: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 353b: aastore
      // 353c: dup
      // 353d: bipush 28
      // 353f: new net/minecraft/client/animation/Keyframe
      // 3542: dup
      // 3543: ldc 1.21
      // 3545: dconst_1
      // 3546: dconst_1
      // 3547: ldc2_w 1.3559999465942383
      // 354a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 354d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3550: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3553: aastore
      // 3554: dup
      // 3555: bipush 29
      // 3557: new net/minecraft/client/animation/Keyframe
      // 355a: dup
      // 355b: ldc 1.29
      // 355d: dconst_1
      // 355e: dconst_1
      // 355f: ldc2_w 1.7489999532699585
      // 3562: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3565: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3568: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 356b: aastore
      // 356c: dup
      // 356d: bipush 30
      // 356f: new net/minecraft/client/animation/Keyframe
      // 3572: dup
      // 3573: ldc 1.33
      // 3575: dconst_1
      // 3576: dconst_1
      // 3577: ldc2_w 1.9170000553131104
      // 357a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 357d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3583: aastore
      // 3584: dup
      // 3585: bipush 31
      // 3587: new net/minecraft/client/animation/Keyframe
      // 358a: dup
      // 358b: ldc 1.38
      // 358d: dconst_1
      // 358e: dconst_1
      // 358f: ldc2_w 2.0390000343322754
      // 3592: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 359b: aastore
      // 359c: dup
      // 359d: bipush 32
      // 359f: new net/minecraft/client/animation/Keyframe
      // 35a2: dup
      // 35a3: ldc 1.42
      // 35a5: dconst_1
      // 35a6: dconst_1
      // 35a7: ldc2_w 2.0799999237060547
      // 35aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35b3: aastore
      // 35b4: dup
      // 35b5: bipush 33
      // 35b7: new net/minecraft/client/animation/Keyframe
      // 35ba: dup
      // 35bb: ldc 1.46
      // 35bd: dconst_1
      // 35be: dconst_1
      // 35bf: ldc2_w 2.0390000343322754
      // 35c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35cb: aastore
      // 35cc: dup
      // 35cd: bipush 34
      // 35cf: new net/minecraft/client/animation/Keyframe
      // 35d2: dup
      // 35d3: ldc 1.54
      // 35d5: dconst_1
      // 35d6: dconst_1
      // 35d7: ldc2_w 1.7510000467300415
      // 35da: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35e3: aastore
      // 35e4: dup
      // 35e5: bipush 35
      // 35e7: new net/minecraft/client/animation/Keyframe
      // 35ea: dup
      // 35eb: ldc 1.58
      // 35ed: dconst_1
      // 35ee: dconst_1
      // 35ef: ldc2_w 1.621000051498413
      // 35f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 35f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35fb: aastore
      // 35fc: dup
      // 35fd: bipush 36
      // 35ff: new net/minecraft/client/animation/Keyframe
      // 3602: dup
      // 3603: ldc 1.62
      // 3605: dconst_1
      // 3606: dconst_1
      // 3607: ldc2_w 1.4539999961853027
      // 360a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 360d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3610: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3613: aastore
      // 3614: dup
      // 3615: bipush 37
      // 3617: new net/minecraft/client/animation/Keyframe
      // 361a: dup
      // 361b: ldc 1.67
      // 361d: dconst_1
      // 361e: dconst_1
      // 361f: ldc2_w 1.2829999923706055
      // 3622: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3625: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3628: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 362b: aastore
      // 362c: dup
      // 362d: bipush 38
      // 362f: new net/minecraft/client/animation/Keyframe
      // 3632: dup
      // 3633: ldc 1.71
      // 3635: dconst_1
      // 3636: dconst_1
      // 3637: ldc2_w 1.1649999618530273
      // 363a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 363d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3640: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3643: aastore
      // 3644: dup
      // 3645: bipush 39
      // 3647: new net/minecraft/client/animation/Keyframe
      // 364a: dup
      // 364b: ldc 1.75
      // 364d: dconst_1
      // 364e: dconst_1
      // 364f: ldc2_w 1.1640000343322754
      // 3652: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3655: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3658: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 365b: aastore
      // 365c: dup
      // 365d: bipush 40
      // 365f: new net/minecraft/client/animation/Keyframe
      // 3662: dup
      // 3663: ldc 1.79
      // 3665: dconst_1
      // 3666: dconst_1
      // 3667: ldc2_w 1.315000057220459
      // 366a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 366d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3670: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3673: aastore
      // 3674: dup
      // 3675: bipush 41
      // 3677: new net/minecraft/client/animation/Keyframe
      // 367a: dup
      // 367b: ldc 1.83
      // 367d: dconst_1
      // 367e: dconst_1
      // 367f: ldc2_w 1.6160000562667847
      // 3682: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3685: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3688: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 368b: aastore
      // 368c: dup
      // 368d: bipush 42
      // 368f: new net/minecraft/client/animation/Keyframe
      // 3692: dup
      // 3693: ldc 1.88
      // 3695: dconst_1
      // 3696: dconst_1
      // 3697: ldc2_w 1.5959999561309814
      // 369a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 369d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a3: aastore
      // 36a4: dup
      // 36a5: bipush 43
      // 36a7: new net/minecraft/client/animation/Keyframe
      // 36aa: dup
      // 36ab: ldc 1.92
      // 36ad: dconst_1
      // 36ae: dconst_1
      // 36af: ldc2_w 1.6440000534057617
      // 36b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36bb: aastore
      // 36bc: dup
      // 36bd: bipush 44
      // 36bf: new net/minecraft/client/animation/Keyframe
      // 36c2: dup
      // 36c3: ldc_w 1.96
      // 36c6: dconst_1
      // 36c7: dconst_1
      // 36c8: ldc2_w 1.7059999704360962
      // 36cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d4: aastore
      // 36d5: dup
      // 36d6: bipush 45
      // 36d8: new net/minecraft/client/animation/Keyframe
      // 36db: dup
      // 36dc: fconst_2
      // 36dd: dconst_1
      // 36de: dconst_1
      // 36df: ldc2_w 1.7369999885559082
      // 36e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36eb: aastore
      // 36ec: dup
      // 36ed: bipush 46
      // 36ef: new net/minecraft/client/animation/Keyframe
      // 36f2: dup
      // 36f3: ldc_w 2.04
      // 36f6: dconst_1
      // 36f7: dconst_1
      // 36f8: ldc2_w 1.7400000095367432
      // 36fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 36fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3701: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3704: aastore
      // 3705: dup
      // 3706: bipush 47
      // 3708: new net/minecraft/client/animation/Keyframe
      // 370b: dup
      // 370c: ldc_w 2.08
      // 370f: dconst_1
      // 3710: dconst_1
      // 3711: ldc2_w 1.753999948501587
      // 3714: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3717: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 371a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 371d: aastore
      // 371e: dup
      // 371f: bipush 48
      // 3721: new net/minecraft/client/animation/Keyframe
      // 3724: dup
      // 3725: ldc_w 2.12
      // 3728: dconst_1
      // 3729: dconst_1
      // 372a: ldc2_w 1.7799999713897705
      // 372d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3730: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3733: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3736: aastore
      // 3737: dup
      // 3738: bipush 49
      // 373a: new net/minecraft/client/animation/Keyframe
      // 373d: dup
      // 373e: ldc 2.17
      // 3740: dconst_1
      // 3741: dconst_1
      // 3742: ldc2_w 1.7899999618530273
      // 3745: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 3748: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 374b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 374e: aastore
      // 374f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3752: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3755: ldc_w "CTR_shoulder_right"
      // 3758: new net/minecraft/client/animation/AnimationChannel
      // 375b: dup
      // 375c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 375f: bipush 53
      // 3761: anewarray 28
      // 3764: dup
      // 3765: bipush 0
      // 3766: new net/minecraft/client/animation/Keyframe
      // 3769: dup
      // 376a: fconst_0
      // 376b: ldc_w 0.016
      // 376e: ldc_w -1.1
      // 3771: ldc_w -0.172
      // 3774: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3777: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 377a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 377d: aastore
      // 377e: dup
      // 377f: bipush 1
      // 3780: new net/minecraft/client/animation/Keyframe
      // 3783: dup
      // 3784: ldc 0.04
      // 3786: ldc_w -0.26
      // 3789: ldc_w -1.4
      // 378c: ldc_w -0.351
      // 378f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3792: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3795: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3798: aastore
      // 3799: dup
      // 379a: bipush 2
      // 379b: new net/minecraft/client/animation/Keyframe
      // 379e: dup
      // 379f: ldc 0.08
      // 37a1: ldc_w -0.772
      // 37a4: ldc_w -1.958
      // 37a7: ldc_w -1.1
      // 37aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37b3: aastore
      // 37b4: dup
      // 37b5: bipush 3
      // 37b6: new net/minecraft/client/animation/Keyframe
      // 37b9: dup
      // 37ba: ldc 0.12
      // 37bc: ldc_w -1.048
      // 37bf: ldc_w -2.258
      // 37c2: ldc_w -2.744
      // 37c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37ce: aastore
      // 37cf: dup
      // 37d0: bipush 4
      // 37d1: new net/minecraft/client/animation/Keyframe
      // 37d4: dup
      // 37d5: ldc 0.17
      // 37d7: ldc_w -0.398
      // 37da: ldc_w -1.885
      // 37dd: ldc_w -5.402
      // 37e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37e9: aastore
      // 37ea: dup
      // 37eb: bipush 5
      // 37ec: new net/minecraft/client/animation/Keyframe
      // 37ef: dup
      // 37f0: ldc 0.21
      // 37f2: ldc_w 1.031
      // 37f5: ldc_w -1.063
      // 37f8: ldc_w -8.394
      // 37fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 37fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3801: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3804: aastore
      // 3805: dup
      // 3806: bipush 6
      // 3808: new net/minecraft/client/animation/Keyframe
      // 380b: dup
      // 380c: ldc 0.25
      // 380e: ldc_w 2.461
      // 3811: ldc_w -0.241
      // 3814: ldc_w -10.836
      // 3817: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 381a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 381d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3820: aastore
      // 3821: dup
      // 3822: bipush 7
      // 3824: new net/minecraft/client/animation/Keyframe
      // 3827: dup
      // 3828: ldc 0.29
      // 382a: ldc_w 3.111
      // 382d: ldc_w 0.133
      // 3830: ldc_w -11.846
      // 3833: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3836: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3839: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 383c: aastore
      // 383d: dup
      // 383e: bipush 8
      // 3840: new net/minecraft/client/animation/Keyframe
      // 3843: dup
      // 3844: ldc 0.33
      // 3846: ldc_w 3.097
      // 3849: ldc_w -0.268
      // 384c: ldc_w -11.715
      // 384f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3852: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3855: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3858: aastore
      // 3859: dup
      // 385a: bipush 9
      // 385c: new net/minecraft/client/animation/Keyframe
      // 385f: dup
      // 3860: ldc 0.38
      // 3862: ldc_w 3.059
      // 3865: ldc_w -1.369
      // 3868: ldc_w -11.348
      // 386b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 386e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3871: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3874: aastore
      // 3875: dup
      // 3876: bipush 10
      // 3878: new net/minecraft/client/animation/Keyframe
      // 387b: dup
      // 387c: ldc 0.42
      // 387e: ldc_w 3.001
      // 3881: ldc_w -3.013
      // 3884: ldc_w -10.78
      // 3887: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 388a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 388d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3890: aastore
      // 3891: dup
      // 3892: bipush 11
      // 3894: new net/minecraft/client/animation/Keyframe
      // 3897: dup
      // 3898: ldc 0.46
      // 389a: ldc_w 2.927
      // 389d: ldc_w -5.045
      // 38a0: ldc_w -10.051
      // 38a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38ac: aastore
      // 38ad: dup
      // 38ae: bipush 12
      // 38b0: new net/minecraft/client/animation/Keyframe
      // 38b3: dup
      // 38b4: ldc 0.5
      // 38b6: ldc_w 2.842
      // 38b9: ldc_w -7.31
      // 38bc: ldc_w -9.196
      // 38bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38c8: aastore
      // 38c9: dup
      // 38ca: bipush 13
      // 38cc: new net/minecraft/client/animation/Keyframe
      // 38cf: dup
      // 38d0: ldc 0.54
      // 38d2: ldc_w 2.749
      // 38d5: ldc -9.653
      // 38d7: ldc_w -8.254
      // 38da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38e3: aastore
      // 38e4: dup
      // 38e5: bipush 14
      // 38e7: new net/minecraft/client/animation/Keyframe
      // 38ea: dup
      // 38eb: ldc 0.58
      // 38ed: ldc_w 2.653
      // 38f0: ldc_w -11.918
      // 38f3: ldc_w -7.26
      // 38f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 38f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38ff: aastore
      // 3900: dup
      // 3901: bipush 15
      // 3903: new net/minecraft/client/animation/Keyframe
      // 3906: dup
      // 3907: ldc 0.62
      // 3909: ldc_w 2.559
      // 390c: ldc_w -13.95
      // 390f: ldc_w -6.253
      // 3912: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3915: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3918: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 391b: aastore
      // 391c: dup
      // 391d: bipush 16
      // 391f: new net/minecraft/client/animation/Keyframe
      // 3922: dup
      // 3923: ldc 0.67
      // 3925: ldc_w 2.47
      // 3928: ldc_w -15.594
      // 392b: ldc_w -5.269
      // 392e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3931: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3934: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3937: aastore
      // 3938: dup
      // 3939: bipush 17
      // 393b: new net/minecraft/client/animation/Keyframe
      // 393e: dup
      // 393f: ldc 0.71
      // 3941: ldc_w 2.392
      // 3944: ldc_w -16.694
      // 3947: ldc_w -4.347
      // 394a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 394d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3950: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3953: aastore
      // 3954: dup
      // 3955: bipush 18
      // 3957: new net/minecraft/client/animation/Keyframe
      // 395a: dup
      // 395b: ldc 0.75
      // 395d: ldc_w 2.328
      // 3960: ldc_w -17.095
      // 3963: ldc_w -3.522
      // 3966: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 396c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 396f: aastore
      // 3970: dup
      // 3971: bipush 19
      // 3973: new net/minecraft/client/animation/Keyframe
      // 3976: dup
      // 3977: ldc 0.79
      // 3979: ldc_w 2.279
      // 397c: ldc_w -16.775
      // 397f: ldc_w -2.824
      // 3982: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3985: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3988: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 398b: aastore
      // 398c: dup
      // 398d: bipush 20
      // 398f: new net/minecraft/client/animation/Keyframe
      // 3992: dup
      // 3993: ldc 0.83
      // 3995: ldc_w 2.242
      // 3998: ldc_w -15.881
      // 399b: ldc_w -2.252
      // 399e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39a7: aastore
      // 39a8: dup
      // 39a9: bipush 21
      // 39ab: new net/minecraft/client/animation/Keyframe
      // 39ae: dup
      // 39af: ldc 0.88
      // 39b1: ldc_w 2.216
      // 39b4: ldc_w -14.515
      // 39b7: ldc_w -1.795
      // 39ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39c3: aastore
      // 39c4: dup
      // 39c5: bipush 22
      // 39c7: new net/minecraft/client/animation/Keyframe
      // 39ca: dup
      // 39cb: ldc 0.92
      // 39cd: ldc_w 2.198
      // 39d0: ldc_w -12.779
      // 39d3: ldc_w -1.443
      // 39d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39df: aastore
      // 39e0: dup
      // 39e1: bipush 23
      // 39e3: new net/minecraft/client/animation/Keyframe
      // 39e6: dup
      // 39e7: ldc 0.96
      // 39e9: ldc_w 2.187
      // 39ec: ldc_w -10.775
      // 39ef: ldc_w -1.187
      // 39f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 39f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39fb: aastore
      // 39fc: dup
      // 39fd: bipush 24
      // 39ff: new net/minecraft/client/animation/Keyframe
      // 3a02: dup
      // 3a03: fconst_1
      // 3a04: ldc_w 2.182
      // 3a07: ldc_w -8.603
      // 3a0a: ldc_w -1.016
      // 3a0d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a10: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a13: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a16: aastore
      // 3a17: dup
      // 3a18: bipush 25
      // 3a1a: new net/minecraft/client/animation/Keyframe
      // 3a1d: dup
      // 3a1e: ldc 1.04
      // 3a20: ldc_w 2.18
      // 3a23: ldc_w -6.366
      // 3a26: ldc_w -0.921
      // 3a29: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a32: aastore
      // 3a33: dup
      // 3a34: bipush 26
      // 3a36: new net/minecraft/client/animation/Keyframe
      // 3a39: dup
      // 3a3a: ldc 1.08
      // 3a3c: ldc_w 2.179
      // 3a3f: ldc_w -4.165
      // 3a42: ldc_w -0.891
      // 3a45: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a4e: aastore
      // 3a4f: dup
      // 3a50: bipush 27
      // 3a52: new net/minecraft/client/animation/Keyframe
      // 3a55: dup
      // 3a56: ldc 1.12
      // 3a58: ldc_w 2.179
      // 3a5b: ldc_w -2.088
      // 3a5e: ldc_w -0.941
      // 3a61: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a64: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a67: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a6a: aastore
      // 3a6b: dup
      // 3a6c: bipush 28
      // 3a6e: new net/minecraft/client/animation/Keyframe
      // 3a71: dup
      // 3a72: ldc 1.17
      // 3a74: ldc_w 2.174
      // 3a77: ldc_w -0.163
      // 3a7a: ldc_w -1.077
      // 3a7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a86: aastore
      // 3a87: dup
      // 3a88: bipush 29
      // 3a8a: new net/minecraft/client/animation/Keyframe
      // 3a8d: dup
      // 3a8e: ldc 1.21
      // 3a90: ldc_w 2.16
      // 3a93: ldc 1.593
      // 3a95: ldc_w -1.277
      // 3a98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3a9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aa1: aastore
      // 3aa2: dup
      // 3aa3: bipush 30
      // 3aa5: new net/minecraft/client/animation/Keyframe
      // 3aa8: dup
      // 3aa9: ldc 1.25
      // 3aab: ldc_w 2.134
      // 3aae: ldc_w 3.164
      // 3ab1: ldc_w -1.52
      // 3ab4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ab7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3abd: aastore
      // 3abe: dup
      // 3abf: bipush 31
      // 3ac1: new net/minecraft/client/animation/Keyframe
      // 3ac4: dup
      // 3ac5: ldc 1.29
      // 3ac7: ldc_w 2.091
      // 3aca: ldc_w 4.537
      // 3acd: ldc_w -1.785
      // 3ad0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ad3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ad6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ad9: aastore
      // 3ada: dup
      // 3adb: bipush 32
      // 3add: new net/minecraft/client/animation/Keyframe
      // 3ae0: dup
      // 3ae1: ldc 1.33
      // 3ae3: ldc_w 2.026
      // 3ae6: ldc_w 5.694
      // 3ae9: ldc_w -2.049
      // 3aec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3aef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3af2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3af5: aastore
      // 3af6: dup
      // 3af7: bipush 33
      // 3af9: new net/minecraft/client/animation/Keyframe
      // 3afc: dup
      // 3afd: ldc 1.38
      // 3aff: ldc_w 1.936
      // 3b02: ldc_w 6.621
      // 3b05: ldc_w -2.292
      // 3b08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b11: aastore
      // 3b12: dup
      // 3b13: bipush 34
      // 3b15: new net/minecraft/client/animation/Keyframe
      // 3b18: dup
      // 3b19: ldc 1.42
      // 3b1b: ldc_w 1.816
      // 3b1e: ldc_w 7.302
      // 3b21: ldc_w -2.492
      // 3b24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b2d: aastore
      // 3b2e: dup
      // 3b2f: bipush 35
      // 3b31: new net/minecraft/client/animation/Keyframe
      // 3b34: dup
      // 3b35: ldc 1.46
      // 3b37: ldc_w 1.662
      // 3b3a: ldc_w 7.722
      // 3b3d: ldc_w -2.628
      // 3b40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b49: aastore
      // 3b4a: dup
      // 3b4b: bipush 36
      // 3b4d: new net/minecraft/client/animation/Keyframe
      // 3b50: dup
      // 3b51: ldc 1.5
      // 3b53: ldc_w 1.47
      // 3b56: ldc_w 7.866
      // 3b59: ldc_w -2.678
      // 3b5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b65: aastore
      // 3b66: dup
      // 3b67: bipush 37
      // 3b69: new net/minecraft/client/animation/Keyframe
      // 3b6c: dup
      // 3b6d: ldc 1.54
      // 3b6f: ldc_w 1.115
      // 3b72: ldc_w 7.45
      // 3b75: ldc_w -2.46
      // 3b78: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b81: aastore
      // 3b82: dup
      // 3b83: bipush 38
      // 3b85: new net/minecraft/client/animation/Keyframe
      // 3b88: dup
      // 3b89: ldc 1.58
      // 3b8b: ldc_w 0.52
      // 3b8e: ldc_w 6.321
      // 3b91: ldc_w -1.868
      // 3b94: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b9d: aastore
      // 3b9e: dup
      // 3b9f: bipush 39
      // 3ba1: new net/minecraft/client/animation/Keyframe
      // 3ba4: dup
      // 3ba5: ldc 1.62
      // 3ba7: ldc_w -0.251
      // 3baa: ldc_w 4.657
      // 3bad: ldc_w -0.995
      // 3bb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bb9: aastore
      // 3bba: dup
      // 3bbb: bipush 40
      // 3bbd: new net/minecraft/client/animation/Keyframe
      // 3bc0: dup
      // 3bc1: ldc 1.67
      // 3bc3: ldc_w -1.131
      // 3bc6: ldc_w 2.636
      // 3bc9: ldc_w 0.064
      // 3bcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bd5: aastore
      // 3bd6: dup
      // 3bd7: bipush 41
      // 3bd9: new net/minecraft/client/animation/Keyframe
      // 3bdc: dup
      // 3bdd: ldc 1.71
      // 3bdf: ldc_w -2.056
      // 3be2: ldc_w 0.437
      // 3be5: ldc_w 1.217
      // 3be8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3beb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bf1: aastore
      // 3bf2: dup
      // 3bf3: bipush 42
      // 3bf5: new net/minecraft/client/animation/Keyframe
      // 3bf8: dup
      // 3bf9: ldc 1.75
      // 3bfb: ldc_w -2.959
      // 3bfe: ldc_w -1.762
      // 3c01: ldc_w 2.37
      // 3c04: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c0d: aastore
      // 3c0e: dup
      // 3c0f: bipush 43
      // 3c11: new net/minecraft/client/animation/Keyframe
      // 3c14: dup
      // 3c15: ldc 1.79
      // 3c17: ldc_w -3.775
      // 3c1a: ldc_w -3.782
      // 3c1d: ldc_w 3.43
      // 3c20: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c29: aastore
      // 3c2a: dup
      // 3c2b: bipush 44
      // 3c2d: new net/minecraft/client/animation/Keyframe
      // 3c30: dup
      // 3c31: ldc 1.83
      // 3c33: ldc_w -4.44
      // 3c36: ldc_w -5.446
      // 3c39: ldc_w 4.302
      // 3c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c45: aastore
      // 3c46: dup
      // 3c47: bipush 45
      // 3c49: new net/minecraft/client/animation/Keyframe
      // 3c4c: dup
      // 3c4d: ldc 1.88
      // 3c4f: ldc_w -4.886
      // 3c52: ldc_w -6.575
      // 3c55: ldc_w 4.894
      // 3c58: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c61: aastore
      // 3c62: dup
      // 3c63: bipush 46
      // 3c65: new net/minecraft/client/animation/Keyframe
      // 3c68: dup
      // 3c69: ldc 1.92
      // 3c6b: ldc_w -5.05
      // 3c6e: ldc_w -6.991
      // 3c71: ldc_w 5.112
      // 3c74: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c7d: aastore
      // 3c7e: dup
      // 3c7f: bipush 47
      // 3c81: new net/minecraft/client/animation/Keyframe
      // 3c84: dup
      // 3c85: ldc_w 1.96
      // 3c88: ldc_w -4.119
      // 3c8b: ldc_w -6.69
      // 3c8e: ldc_w 3.716
      // 3c91: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c9a: aastore
      // 3c9b: dup
      // 3c9c: bipush 48
      // 3c9e: new net/minecraft/client/animation/Keyframe
      // 3ca1: dup
      // 3ca2: fconst_2
      // 3ca3: ldc_w -1.879
      // 3ca6: ldc_w -4.581
      // 3ca9: ldc_w 1.224
      // 3cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cb5: aastore
      // 3cb6: dup
      // 3cb7: bipush 49
      // 3cb9: new net/minecraft/client/animation/Keyframe
      // 3cbc: dup
      // 3cbd: ldc_w 2.04
      // 3cc0: ldc_w -0.002
      // 3cc3: ldc_w -0.737
      // 3cc6: ldc_w -0.262
      // 3cc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ccc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ccf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cd2: aastore
      // 3cd3: dup
      // 3cd4: bipush 50
      // 3cd6: new net/minecraft/client/animation/Keyframe
      // 3cd9: dup
      // 3cda: ldc_w 2.08
      // 3cdd: ldc_w 0.049
      // 3ce0: ldc_w -0.113
      // 3ce3: ldc_w -0.581
      // 3ce6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ce9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cef: aastore
      // 3cf0: dup
      // 3cf1: bipush 51
      // 3cf3: new net/minecraft/client/animation/Keyframe
      // 3cf6: dup
      // 3cf7: ldc_w 2.12
      // 3cfa: ldc 0.04
      // 3cfc: ldc_w -0.369
      // 3cff: ldc_w -0.475
      // 3d02: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d0b: aastore
      // 3d0c: dup
      // 3d0d: bipush 52
      // 3d0f: new net/minecraft/client/animation/Keyframe
      // 3d12: dup
      // 3d13: ldc 2.17
      // 3d15: ldc_w 0.025
      // 3d18: ldc_w -0.844
      // 3d1b: ldc_w -0.278
      // 3d1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d27: aastore
      // 3d28: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3d2b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3d2e: ldc_w "CTR_upperarm_right"
      // 3d31: new net/minecraft/client/animation/AnimationChannel
      // 3d34: dup
      // 3d35: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3d38: bipush 49
      // 3d3a: anewarray 28
      // 3d3d: dup
      // 3d3e: bipush 0
      // 3d3f: new net/minecraft/client/animation/Keyframe
      // 3d42: dup
      // 3d43: fconst_0
      // 3d44: ldc_w 0.839
      // 3d47: ldc_w -2.513
      // 3d4a: ldc_w 0.452
      // 3d4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d56: aastore
      // 3d57: dup
      // 3d58: bipush 1
      // 3d59: new net/minecraft/client/animation/Keyframe
      // 3d5c: dup
      // 3d5d: ldc 0.04
      // 3d5f: ldc_w 1.904
      // 3d62: ldc_w -2.962
      // 3d65: ldc_w 3.035
      // 3d68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d71: aastore
      // 3d72: dup
      // 3d73: bipush 2
      // 3d74: new net/minecraft/client/animation/Keyframe
      // 3d77: dup
      // 3d78: ldc 0.08
      // 3d7a: ldc_w 1.53
      // 3d7d: ldc_w -6.112
      // 3d80: ldc_w 1.834
      // 3d83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d8c: aastore
      // 3d8d: dup
      // 3d8e: bipush 3
      // 3d8f: new net/minecraft/client/animation/Keyframe
      // 3d92: dup
      // 3d93: ldc 0.12
      // 3d95: ldc_w 0.242
      // 3d98: ldc_w -9.518
      // 3d9b: ldc_w -1.336
      // 3d9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3da1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3da4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3da7: aastore
      // 3da8: dup
      // 3da9: bipush 4
      // 3daa: new net/minecraft/client/animation/Keyframe
      // 3dad: dup
      // 3dae: ldc 0.17
      // 3db0: ldc_w -1.353
      // 3db3: ldc_w -11.393
      // 3db6: ldc_w -7.547
      // 3db9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dc2: aastore
      // 3dc3: dup
      // 3dc4: bipush 5
      // 3dc5: new net/minecraft/client/animation/Keyframe
      // 3dc8: dup
      // 3dc9: ldc 0.21
      // 3dcb: ldc_w -1.415
      // 3dce: ldc_w -10.641
      // 3dd1: ldc_w -7.083
      // 3dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ddd: aastore
      // 3dde: dup
      // 3ddf: bipush 6
      // 3de1: new net/minecraft/client/animation/Keyframe
      // 3de4: dup
      // 3de5: ldc 0.25
      // 3de7: ldc_w -0.305
      // 3dea: ldc_w -10.665
      // 3ded: ldc_w 3.535
      // 3df0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3df3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3df6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3df9: aastore
      // 3dfa: dup
      // 3dfb: bipush 7
      // 3dfd: new net/minecraft/client/animation/Keyframe
      // 3e00: dup
      // 3e01: ldc 0.29
      // 3e03: ldc_w -1.283
      // 3e06: ldc_w -11.78
      // 3e09: ldc_w 11.809
      // 3e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e15: aastore
      // 3e16: dup
      // 3e17: bipush 8
      // 3e19: new net/minecraft/client/animation/Keyframe
      // 3e1c: dup
      // 3e1d: ldc 0.33
      // 3e1f: ldc_w -4.965
      // 3e22: ldc_w -10.045
      // 3e25: ldc_w 3.284
      // 3e28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e31: aastore
      // 3e32: dup
      // 3e33: bipush 9
      // 3e35: new net/minecraft/client/animation/Keyframe
      // 3e38: dup
      // 3e39: ldc 0.38
      // 3e3b: ldc_w -9.397
      // 3e3e: ldc_w -5.909
      // 3e41: ldc_w -17.045
      // 3e44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e4d: aastore
      // 3e4e: dup
      // 3e4f: bipush 10
      // 3e51: new net/minecraft/client/animation/Keyframe
      // 3e54: dup
      // 3e55: ldc 0.42
      // 3e57: ldc_w -14.222
      // 3e5a: ldc_w -0.972
      // 3e5d: ldc_w -41.309
      // 3e60: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e69: aastore
      // 3e6a: dup
      // 3e6b: bipush 11
      // 3e6d: new net/minecraft/client/animation/Keyframe
      // 3e70: dup
      // 3e71: ldc 0.46
      // 3e73: ldc_w -19.079
      // 3e76: ldc_w 3.165
      // 3e79: ldc_w -61.638
      // 3e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e85: aastore
      // 3e86: dup
      // 3e87: bipush 12
      // 3e89: new net/minecraft/client/animation/Keyframe
      // 3e8c: dup
      // 3e8d: ldc 0.5
      // 3e8f: ldc_w -23.61
      // 3e92: ldc_w 4.9
      // 3e95: ldc_w -70.163
      // 3e98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ea1: aastore
      // 3ea2: dup
      // 3ea3: bipush 13
      // 3ea5: new net/minecraft/client/animation/Keyframe
      // 3ea8: dup
      // 3ea9: ldc 0.54
      // 3eab: ldc_w -27.505
      // 3eae: ldc_w 0.122
      // 3eb1: ldc_w -69.879
      // 3eb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ebd: aastore
      // 3ebe: dup
      // 3ebf: bipush 14
      // 3ec1: new net/minecraft/client/animation/Keyframe
      // 3ec4: dup
      // 3ec5: ldc 0.58
      // 3ec7: ldc_w -30.648
      // 3eca: ldc_w -11.273
      // 3ecd: ldc_w -69.203
      // 3ed0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ed3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ed6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ed9: aastore
      // 3eda: dup
      // 3edb: bipush 15
      // 3edd: new net/minecraft/client/animation/Keyframe
      // 3ee0: dup
      // 3ee1: ldc 0.62
      // 3ee3: ldc_w -32.975
      // 3ee6: ldc_w -24.872
      // 3ee9: ldc_w -68.396
      // 3eec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3eef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ef2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ef5: aastore
      // 3ef6: dup
      // 3ef7: bipush 16
      // 3ef9: new net/minecraft/client/animation/Keyframe
      // 3efc: dup
      // 3efd: ldc 0.67
      // 3eff: ldc_w -34.419
      // 3f02: ldc_w -36.266
      // 3f05: ldc_w -67.72
      // 3f08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f11: aastore
      // 3f12: dup
      // 3f13: bipush 17
      // 3f15: new net/minecraft/client/animation/Keyframe
      // 3f18: dup
      // 3f19: ldc 0.71
      // 3f1b: ldc_w -34.915
      // 3f1e: ldc_w -41.044
      // 3f21: ldc_w -67.437
      // 3f24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f2d: aastore
      // 3f2e: dup
      // 3f2f: bipush 18
      // 3f31: new net/minecraft/client/animation/Keyframe
      // 3f34: dup
      // 3f35: ldc 0.92
      // 3f37: ldc_w -34.915
      // 3f3a: ldc_w -41.044
      // 3f3d: ldc_w -67.437
      // 3f40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f49: aastore
      // 3f4a: dup
      // 3f4b: bipush 19
      // 3f4d: new net/minecraft/client/animation/Keyframe
      // 3f50: dup
      // 3f51: ldc 0.96
      // 3f53: ldc_w -24.484
      // 3f56: ldc_w -29.866
      // 3f59: ldc_w -65.237
      // 3f5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f65: aastore
      // 3f66: dup
      // 3f67: bipush 20
      // 3f69: new net/minecraft/client/animation/Keyframe
      // 3f6c: dup
      // 3f6d: fconst_1
      // 3f6e: ldc_w -14.054
      // 3f71: ldc_w -18.687
      // 3f74: ldc_w -49.839
      // 3f77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f80: aastore
      // 3f81: dup
      // 3f82: bipush 21
      // 3f84: new net/minecraft/client/animation/Keyframe
      // 3f87: dup
      // 3f88: ldc 1.04
      // 3f8a: ldc_w -69.922
      // 3f8d: ldc_w -45.532
      // 3f90: ldc_w -11.005
      // 3f93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f9c: aastore
      // 3f9d: dup
      // 3f9e: bipush 22
      // 3fa0: new net/minecraft/client/animation/Keyframe
      // 3fa3: dup
      // 3fa4: ldc 1.08
      // 3fa6: ldc_w -178.3
      // 3fa9: ldc_w 11.205
      // 3fac: ldc_w -33.944
      // 3faf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb8: aastore
      // 3fb9: dup
      // 3fba: bipush 23
      // 3fbc: new net/minecraft/client/animation/Keyframe
      // 3fbf: dup
      // 3fc0: ldc 1.12
      // 3fc2: ldc_w -206.089
      // 3fc5: ldc_w 16.892
      // 3fc8: ldc_w -90.869
      // 3fcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fd4: aastore
      // 3fd5: dup
      // 3fd6: bipush 24
      // 3fd8: new net/minecraft/client/animation/Keyframe
      // 3fdb: dup
      // 3fdc: ldc 1.17
      // 3fde: ldc_w -217.238
      // 3fe1: ldc_w 26.135
      // 3fe4: ldc_w -117.458
      // 3fe7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ff0: aastore
      // 3ff1: dup
      // 3ff2: bipush 25
      // 3ff4: new net/minecraft/client/animation/Keyframe
      // 3ff7: dup
      // 3ff8: ldc 1.21
      // 3ffa: ldc_w -224.783
      // 3ffd: ldc_w 35.057
      // 4000: ldc_w -136.211
      // 4003: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4006: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4009: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 400c: aastore
      // 400d: dup
      // 400e: bipush 26
      // 4010: new net/minecraft/client/animation/Keyframe
      // 4013: dup
      // 4014: ldc 1.25
      // 4016: ldc_w -230.155
      // 4019: ldc_w 43.089
      // 401c: ldc_w -150.0
      // 401f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4022: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4025: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4028: aastore
      // 4029: dup
      // 402a: bipush 27
      // 402c: new net/minecraft/client/animation/Keyframe
      // 402f: dup
      // 4030: ldc 1.29
      // 4032: ldc_w -234.125
      // 4035: ldc_w 49.596
      // 4038: ldc_w -160.242
      // 403b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 403e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4041: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4044: aastore
      // 4045: dup
      // 4046: bipush 28
      // 4048: new net/minecraft/client/animation/Keyframe
      // 404b: dup
      // 404c: ldc 1.33
      // 404e: ldc_w -237.256
      // 4051: ldc_w 53.929
      // 4054: ldc_w -167.837
      // 4057: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 405a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 405d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4060: aastore
      // 4061: dup
      // 4062: bipush 29
      // 4064: new net/minecraft/client/animation/Keyframe
      // 4067: dup
      // 4068: ldc 1.38
      // 406a: ldc_w -240.089
      // 406d: ldc_w 56.442
      // 4070: ldc_w -173.768
      // 4073: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4076: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4079: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 407c: aastore
      // 407d: dup
      // 407e: bipush 30
      // 4080: new net/minecraft/client/animation/Keyframe
      // 4083: dup
      // 4084: ldc 1.42
      // 4086: ldc_w -243.168
      // 4089: ldc_w 57.785
      // 408c: ldc_w -179.075
      // 408f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4092: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4095: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4098: aastore
      // 4099: dup
      // 409a: bipush 31
      // 409c: new net/minecraft/client/animation/Keyframe
      // 409f: dup
      // 40a0: ldc 1.46
      // 40a2: ldc_w -247.036
      // 40a5: ldc_w 58.612
      // 40a8: ldc_w -184.796
      // 40ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b4: aastore
      // 40b5: dup
      // 40b6: bipush 32
      // 40b8: new net/minecraft/client/animation/Keyframe
      // 40bb: dup
      // 40bc: ldc 1.5
      // 40be: ldc_w -252.236
      // 40c1: ldc_w 59.574
      // 40c4: ldc_w -191.968
      // 40c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40d0: aastore
      // 40d1: dup
      // 40d2: bipush 33
      // 40d4: new net/minecraft/client/animation/Keyframe
      // 40d7: dup
      // 40d8: ldc 1.54
      // 40da: ldc_w -259.313
      // 40dd: ldc_w 61.321
      // 40e0: ldc_w -201.63
      // 40e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40ec: aastore
      // 40ed: dup
      // 40ee: bipush 34
      // 40f0: new net/minecraft/client/animation/Keyframe
      // 40f3: dup
      // 40f4: ldc 1.58
      // 40f6: ldc_w -268.642
      // 40f9: ldc_w 64.229
      // 40fc: ldc_w -214.454
      // 40ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4102: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4105: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4108: aastore
      // 4109: dup
      // 410a: bipush 35
      // 410c: new net/minecraft/client/animation/Keyframe
      // 410f: dup
      // 4110: ldc 1.62
      // 4112: ldc_w -279.933
      // 4115: ldc_w 67.561
      // 4118: ldc_w -229.647
      // 411b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 411e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4124: aastore
      // 4125: dup
      // 4126: bipush 36
      // 4128: new net/minecraft/client/animation/Keyframe
      // 412b: dup
      // 412c: ldc 1.67
      // 412e: ldc_w -292.729
      // 4131: ldc_w 70.304
      // 4134: ldc_w -246.047
      // 4137: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 413a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 413d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4140: aastore
      // 4141: dup
      // 4142: bipush 37
      // 4144: new net/minecraft/client/animation/Keyframe
      // 4147: dup
      // 4148: ldc 1.71
      // 414a: ldc_w -306.571
      // 414d: ldc_w 71.443
      // 4150: ldc_w -262.496
      // 4153: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4156: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4159: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 415c: aastore
      // 415d: dup
      // 415e: bipush 38
      // 4160: new net/minecraft/client/animation/Keyframe
      // 4163: dup
      // 4164: ldc 1.75
      // 4166: ldc_w -320.85
      // 4169: ldc_w 70.194
      // 416c: ldc_w -277.884
      // 416f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4172: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4175: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4178: aastore
      // 4179: dup
      // 417a: bipush 39
      // 417c: new net/minecraft/client/animation/Keyframe
      // 417f: dup
      // 4180: ldc 1.79
      // 4182: ldc_w -334.346
      // 4185: ldc_w 66.601
      // 4188: ldc_w -291.318
      // 418b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 418e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4191: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4194: aastore
      // 4195: dup
      // 4196: bipush 40
      // 4198: new net/minecraft/client/animation/Keyframe
      // 419b: dup
      // 419c: ldc 1.83
      // 419e: ldc_w -349.308
      // 41a1: ldc_w 55.736
      // 41a4: ldc_w -306.903
      // 41a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41b0: aastore
      // 41b1: dup
      // 41b2: bipush 41
      // 41b4: new net/minecraft/client/animation/Keyframe
      // 41b7: dup
      // 41b8: ldc 1.88
      // 41ba: ldc_w -357.333
      // 41bd: ldc_w 37.298
      // 41c0: ldc_w -317.588
      // 41c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41cc: aastore
      // 41cd: dup
      // 41ce: bipush 42
      // 41d0: new net/minecraft/client/animation/Keyframe
      // 41d3: dup
      // 41d4: ldc 1.92
      // 41d6: ldc_w -358.834
      // 41d9: ldc_w 18.875
      // 41dc: ldc_w -323.79
      // 41df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41e8: aastore
      // 41e9: dup
      // 41ea: bipush 43
      // 41ec: new net/minecraft/client/animation/Keyframe
      // 41ef: dup
      // 41f0: ldc_w 1.96
      // 41f3: ldc_w -354.968
      // 41f6: ldc_w 6.999
      // 41f9: ldc_w -335.817
      // 41fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4202: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4205: aastore
      // 4206: dup
      // 4207: bipush 44
      // 4209: new net/minecraft/client/animation/Keyframe
      // 420c: dup
      // 420d: fconst_2
      // 420e: ldc_w -355.518
      // 4211: ldc_w 2.982
      // 4214: ldc_w -349.702
      // 4217: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 421a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 421d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4220: aastore
      // 4221: dup
      // 4222: bipush 45
      // 4224: new net/minecraft/client/animation/Keyframe
      // 4227: dup
      // 4228: ldc_w 2.04
      // 422b: ldc_w -354.261
      // 422e: ldc_w -0.157
      // 4231: ldc_w -353.938
      // 4234: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4237: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 423a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 423d: aastore
      // 423e: dup
      // 423f: bipush 46
      // 4241: new net/minecraft/client/animation/Keyframe
      // 4244: dup
      // 4245: ldc_w 2.08
      // 4248: ldc_w -355.376
      // 424b: ldc_w -2.03
      // 424e: ldc_w -358.602
      // 4251: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4254: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4257: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 425a: aastore
      // 425b: dup
      // 425c: bipush 47
      // 425e: new net/minecraft/client/animation/Keyframe
      // 4261: dup
      // 4262: ldc_w 2.12
      // 4265: ldc_w -356.617
      // 4268: ldc_w -2.8
      // 426b: ldc_w -361.235
      // 426e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4271: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4274: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4277: aastore
      // 4278: dup
      // 4279: bipush 48
      // 427b: new net/minecraft/client/animation/Keyframe
      // 427e: dup
      // 427f: ldc 2.17
      // 4281: ldc_w -359.425
      // 4284: ldc_w -2.899
      // 4287: ldc_w -360.651
      // 428a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 428d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4290: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4293: aastore
      // 4294: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4297: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 429a: ldc_w "CTR_upperarm_right"
      // 429d: new net/minecraft/client/animation/AnimationChannel
      // 42a0: dup
      // 42a1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 42a4: bipush 11
      // 42a6: anewarray 28
      // 42a9: dup
      // 42aa: bipush 0
      // 42ab: new net/minecraft/client/animation/Keyframe
      // 42ae: dup
      // 42af: ldc 1.54
      // 42b1: fconst_0
      // 42b2: fconst_0
      // 42b3: fconst_0
      // 42b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42bd: aastore
      // 42be: dup
      // 42bf: bipush 1
      // 42c0: new net/minecraft/client/animation/Keyframe
      // 42c3: dup
      // 42c4: ldc 1.58
      // 42c6: ldc_w -0.553
      // 42c9: ldc_w -0.171
      // 42cc: ldc_w 0.335
      // 42cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42d8: aastore
      // 42d9: dup
      // 42da: bipush 2
      // 42db: new net/minecraft/client/animation/Keyframe
      // 42de: dup
      // 42df: ldc 1.62
      // 42e1: ldc_w -1.769
      // 42e4: ldc_w -0.547
      // 42e7: ldc_w 1.071
      // 42ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42f3: aastore
      // 42f4: dup
      // 42f5: bipush 3
      // 42f6: new net/minecraft/client/animation/Keyframe
      // 42f9: dup
      // 42fa: ldc 1.67
      // 42fc: ldc_w -2.985
      // 42ff: ldc_w -0.923
      // 4302: ldc_w 1.808
      // 4305: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4308: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 430b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 430e: aastore
      // 430f: dup
      // 4310: bipush 4
      // 4311: new net/minecraft/client/animation/Keyframe
      // 4314: dup
      // 4315: ldc 1.71
      // 4317: ldc_w -3.538
      // 431a: ldc_w -1.094
      // 431d: ldc_w 2.143
      // 4320: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4323: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4326: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4329: aastore
      // 432a: dup
      // 432b: bipush 5
      // 432c: new net/minecraft/client/animation/Keyframe
      // 432f: dup
      // 4330: ldc 1.92
      // 4332: ldc_w -3.538
      // 4335: ldc_w -1.094
      // 4338: ldc_w 2.143
      // 433b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 433e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4341: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4344: aastore
      // 4345: dup
      // 4346: bipush 6
      // 4348: new net/minecraft/client/animation/Keyframe
      // 434b: dup
      // 434c: ldc_w 1.96
      // 434f: ldc_w -3.276
      // 4352: ldc_w -1.013
      // 4355: ldc_w 1.984
      // 4358: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 435b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 435e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4361: aastore
      // 4362: dup
      // 4363: bipush 7
      // 4365: new net/minecraft/client/animation/Keyframe
      // 4368: dup
      // 4369: fconst_2
      // 436a: ldc_w -2.621
      // 436d: ldc_w -0.81
      // 4370: ldc_w 1.587
      // 4373: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4376: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4379: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 437c: aastore
      // 437d: dup
      // 437e: bipush 8
      // 4380: new net/minecraft/client/animation/Keyframe
      // 4383: dup
      // 4384: ldc_w 2.08
      // 4387: ldc_w -0.917
      // 438a: ldc_w -0.284
      // 438d: ldc_w 0.556
      // 4390: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4393: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4396: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4399: aastore
      // 439a: dup
      // 439b: bipush 9
      // 439d: new net/minecraft/client/animation/Keyframe
      // 43a0: dup
      // 43a1: ldc_w 2.12
      // 43a4: ldc_w -0.262
      // 43a7: ldc_w -0.081
      // 43aa: ldc_w 0.159
      // 43ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43b6: aastore
      // 43b7: dup
      // 43b8: bipush 10
      // 43ba: new net/minecraft/client/animation/Keyframe
      // 43bd: dup
      // 43be: ldc 2.17
      // 43c0: fconst_0
      // 43c1: fconst_0
      // 43c2: fconst_0
      // 43c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43cc: aastore
      // 43cd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 43d0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 43d3: ldc_w "CTR_upperarm_right"
      // 43d6: new net/minecraft/client/animation/AnimationChannel
      // 43d9: dup
      // 43da: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 43dd: bipush 3
      // 43de: anewarray 28
      // 43e1: dup
      // 43e2: bipush 0
      // 43e3: new net/minecraft/client/animation/Keyframe
      // 43e6: dup
      // 43e7: ldc 1.04
      // 43e9: dconst_1
      // 43ea: dconst_1
      // 43eb: dconst_1
      // 43ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 43ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43f5: aastore
      // 43f6: dup
      // 43f7: bipush 1
      // 43f8: new net/minecraft/client/animation/Keyframe
      // 43fb: dup
      // 43fc: ldc 1.08
      // 43fe: ldc2_w 1.3630000352859497
      // 4401: dconst_1
      // 4402: dconst_1
      // 4403: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4406: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4409: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 440c: aastore
      // 440d: dup
      // 440e: bipush 2
      // 440f: new net/minecraft/client/animation/Keyframe
      // 4412: dup
      // 4413: ldc 1.12
      // 4415: dconst_1
      // 4416: dconst_1
      // 4417: dconst_1
      // 4418: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 441b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 441e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4421: aastore
      // 4422: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4425: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4428: ldc_w "CTR_lowerarm_right"
      // 442b: new net/minecraft/client/animation/AnimationChannel
      // 442e: dup
      // 442f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4432: bipush 52
      // 4434: anewarray 28
      // 4437: dup
      // 4438: bipush 0
      // 4439: new net/minecraft/client/animation/Keyframe
      // 443c: dup
      // 443d: fconst_0
      // 443e: ldc_w -0.021
      // 4441: ldc_w -1.724
      // 4444: ldc_w -0.014
      // 4447: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 444a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 444d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4450: aastore
      // 4451: dup
      // 4452: bipush 1
      // 4453: new net/minecraft/client/animation/Keyframe
      // 4456: dup
      // 4457: ldc 0.04
      // 4459: ldc_w -0.3
      // 445c: ldc_w -6.794
      // 445f: ldc_w -0.11
      // 4462: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4465: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4468: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 446b: aastore
      // 446c: dup
      // 446d: bipush 2
      // 446e: new net/minecraft/client/animation/Keyframe
      // 4471: dup
      // 4472: ldc 0.08
      // 4474: ldc_w -0.904
      // 4477: ldc_w -4.641
      // 447a: ldc_w -0.286
      // 447d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4480: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4483: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4486: aastore
      // 4487: dup
      // 4488: bipush 3
      // 4489: new net/minecraft/client/animation/Keyframe
      // 448c: dup
      // 448d: ldc 0.12
      // 448f: ldc_w -2.175
      // 4492: ldc_w 3.93
      // 4495: ldc_w -0.802
      // 4498: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 449b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 449e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44a1: aastore
      // 44a2: dup
      // 44a3: bipush 4
      // 44a4: new net/minecraft/client/animation/Keyframe
      // 44a7: dup
      // 44a8: ldc 0.17
      // 44aa: ldc -4.372
      // 44ac: ldc_w 12.991
      // 44af: ldc_w -1.986
      // 44b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44bb: aastore
      // 44bc: dup
      // 44bd: bipush 5
      // 44be: new net/minecraft/client/animation/Keyframe
      // 44c1: dup
      // 44c2: ldc 0.21
      // 44c4: ldc_w -4.459
      // 44c7: ldc_w 5.322
      // 44ca: ldc_w -1.604
      // 44cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44d6: aastore
      // 44d7: dup
      // 44d8: bipush 6
      // 44da: new net/minecraft/client/animation/Keyframe
      // 44dd: dup
      // 44de: ldc 0.25
      // 44e0: ldc_w -2.571
      // 44e3: ldc_w -15.391
      // 44e6: ldc_w -0.254
      // 44e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44f2: aastore
      // 44f3: dup
      // 44f4: bipush 7
      // 44f6: new net/minecraft/client/animation/Keyframe
      // 44f9: dup
      // 44fa: ldc 0.29
      // 44fc: ldc_w -1.532
      // 44ff: ldc_w -27.362
      // 4502: ldc_w 0.293
      // 4505: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4508: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 450b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 450e: aastore
      // 450f: dup
      // 4510: bipush 8
      // 4512: new net/minecraft/client/animation/Keyframe
      // 4515: dup
      // 4516: ldc 0.33
      // 4518: ldc_w -1.78
      // 451b: ldc_w -25.953
      // 451e: ldc_w 0.526
      // 4521: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4524: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4527: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 452a: aastore
      // 452b: dup
      // 452c: bipush 9
      // 452e: new net/minecraft/client/animation/Keyframe
      // 4531: dup
      // 4532: ldc 0.38
      // 4534: ldc_w -2.004
      // 4537: ldc_w -24.504
      // 453a: ldc_w 0.834
      // 453d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4540: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4543: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4546: aastore
      // 4547: dup
      // 4548: bipush 10
      // 454a: new net/minecraft/client/animation/Keyframe
      // 454d: dup
      // 454e: ldc 0.42
      // 4550: ldc_w -2.187
      // 4553: ldc_w -23.049
      // 4556: ldc_w 1.233
      // 4559: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 455c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 455f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4562: aastore
      // 4563: dup
      // 4564: bipush 11
      // 4566: new net/minecraft/client/animation/Keyframe
      // 4569: dup
      // 456a: ldc 0.46
      // 456c: ldc_w -2.312
      // 456f: ldc_w -21.624
      // 4572: ldc_w 1.735
      // 4575: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4578: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 457b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 457e: aastore
      // 457f: dup
      // 4580: bipush 12
      // 4582: new net/minecraft/client/animation/Keyframe
      // 4585: dup
      // 4586: ldc 0.5
      // 4588: ldc_w -2.361
      // 458b: ldc_w -20.264
      // 458e: ldc_w 2.357
      // 4591: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4594: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4597: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 459a: aastore
      // 459b: dup
      // 459c: bipush 13
      // 459e: new net/minecraft/client/animation/Keyframe
      // 45a1: dup
      // 45a2: ldc 0.54
      // 45a4: ldc_w -2.318
      // 45a7: ldc_w -19.006
      // 45aa: ldc_w 3.113
      // 45ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b6: aastore
      // 45b7: dup
      // 45b8: bipush 14
      // 45ba: new net/minecraft/client/animation/Keyframe
      // 45bd: dup
      // 45be: ldc 0.58
      // 45c0: ldc_w -2.168
      // 45c3: ldc_w -17.888
      // 45c6: ldc_w 4.019
      // 45c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45d2: aastore
      // 45d3: dup
      // 45d4: bipush 15
      // 45d6: new net/minecraft/client/animation/Keyframe
      // 45d9: dup
      // 45da: ldc 0.62
      // 45dc: ldc_w -1.899
      // 45df: ldc_w -16.946
      // 45e2: ldc_w 5.089
      // 45e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45ee: aastore
      // 45ef: dup
      // 45f0: bipush 16
      // 45f2: new net/minecraft/client/animation/Keyframe
      // 45f5: dup
      // 45f6: ldc 0.67
      // 45f8: ldc_w -1.507
      // 45fb: ldc_w -16.223
      // 45fe: ldc_w 6.336
      // 4601: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4604: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4607: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 460a: aastore
      // 460b: dup
      // 460c: bipush 17
      // 460e: new net/minecraft/client/animation/Keyframe
      // 4611: dup
      // 4612: ldc 0.71
      // 4614: ldc_w -0.992
      // 4617: ldc_w -15.758
      // 461a: ldc_w 7.768
      // 461d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4620: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4623: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4626: aastore
      // 4627: dup
      // 4628: bipush 18
      // 462a: new net/minecraft/client/animation/Keyframe
      // 462d: dup
      // 462e: ldc 0.75
      // 4630: ldc_w -0.363
      // 4633: ldc_w -15.593
      // 4636: ldc_w 9.391
      // 4639: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 463c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 463f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4642: aastore
      // 4643: dup
      // 4644: bipush 19
      // 4646: new net/minecraft/client/animation/Keyframe
      // 4649: dup
      // 464a: ldc 0.79
      // 464c: ldc_w 1.013
      // 464f: ldc_w -18.003
      // 4652: ldc_w 12.28
      // 4655: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4658: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 465b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 465e: aastore
      // 465f: dup
      // 4660: bipush 20
      // 4662: new net/minecraft/client/animation/Keyframe
      // 4665: dup
      // 4666: ldc 0.83
      // 4668: ldc_w 2.666
      // 466b: ldc_w -23.779
      // 466e: ldc_w 16.491
      // 4671: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4674: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4677: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 467a: aastore
      // 467b: dup
      // 467c: bipush 21
      // 467e: new net/minecraft/client/animation/Keyframe
      // 4681: dup
      // 4682: ldc 0.88
      // 4684: ldc_w 3.45
      // 4687: ldc_w -30.622
      // 468a: ldc_w 20.793
      // 468d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4690: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4693: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4696: aastore
      // 4697: dup
      // 4698: bipush 22
      // 469a: new net/minecraft/client/animation/Keyframe
      // 469d: dup
      // 469e: ldc 0.92
      // 46a0: ldc_w 3.313
      // 46a3: ldc_w -36.238
      // 46a6: ldc_w 24.212
      // 46a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b2: aastore
      // 46b3: dup
      // 46b4: bipush 23
      // 46b6: new net/minecraft/client/animation/Keyframe
      // 46b9: dup
      // 46ba: ldc 0.96
      // 46bc: ldc_w 3.057
      // 46bf: ldc_w -38.547
      // 46c2: ldc_w 25.633
      // 46c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ce: aastore
      // 46cf: dup
      // 46d0: bipush 24
      // 46d2: new net/minecraft/client/animation/Keyframe
      // 46d5: dup
      // 46d6: fconst_1
      // 46d7: ldc_w 1.569
      // 46da: ldc_w -38.484
      // 46dd: ldc_w 24.741
      // 46e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46e9: aastore
      // 46ea: dup
      // 46eb: bipush 25
      // 46ed: new net/minecraft/client/animation/Keyframe
      // 46f0: dup
      // 46f1: ldc 1.04
      // 46f3: ldc_w 0.065
      // 46f6: ldc_w -38.418
      // 46f9: ldc_w 22.191
      // 46fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4702: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4705: aastore
      // 4706: dup
      // 4707: bipush 26
      // 4709: new net/minecraft/client/animation/Keyframe
      // 470c: dup
      // 470d: ldc 1.08
      // 470f: ldc_w -0.324
      // 4712: ldc_w -48.708
      // 4715: ldc_w 20.025
      // 4718: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 471b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 471e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4721: aastore
      // 4722: dup
      // 4723: bipush 27
      // 4725: new net/minecraft/client/animation/Keyframe
      // 4728: dup
      // 4729: ldc 1.12
      // 472b: ldc_w -3.417
      // 472e: ldc_w -33.22
      // 4731: ldc_w 16.441
      // 4734: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4737: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 473a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 473d: aastore
      // 473e: dup
      // 473f: bipush 28
      // 4741: new net/minecraft/client/animation/Keyframe
      // 4744: dup
      // 4745: ldc 1.17
      // 4747: ldc_w 0.752
      // 474a: fconst_2
      // 474b: ldc_w 13.371
      // 474e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4751: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4754: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4757: aastore
      // 4758: dup
      // 4759: bipush 29
      // 475b: new net/minecraft/client/animation/Keyframe
      // 475e: dup
      // 475f: ldc 1.21
      // 4761: ldc_w -0.287
      // 4764: ldc_w 22.691
      // 4767: ldc_w 8.579
      // 476a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 476d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4770: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4773: aastore
      // 4774: dup
      // 4775: bipush 30
      // 4777: new net/minecraft/client/animation/Keyframe
      // 477a: dup
      // 477b: ldc 1.25
      // 477d: ldc_w -3.365
      // 4780: ldc_w 22.511
      // 4783: ldc_w 5.032
      // 4786: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4789: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 478c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 478f: aastore
      // 4790: dup
      // 4791: bipush 31
      // 4793: new net/minecraft/client/animation/Keyframe
      // 4796: dup
      // 4797: ldc 1.29
      // 4799: ldc_w -5.937
      // 479c: ldc_w 22.066
      // 479f: ldc_w 2.048
      // 47a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47ab: aastore
      // 47ac: dup
      // 47ad: bipush 32
      // 47af: new net/minecraft/client/animation/Keyframe
      // 47b2: dup
      // 47b3: ldc 1.33
      // 47b5: ldc_w -7.996
      // 47b8: ldc_w 21.42
      // 47bb: ldc_w -0.376
      // 47be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47c7: aastore
      // 47c8: dup
      // 47c9: bipush 33
      // 47cb: new net/minecraft/client/animation/Keyframe
      // 47ce: dup
      // 47cf: ldc 1.38
      // 47d1: ldc_w -9.571
      // 47d4: ldc_w 20.63
      // 47d7: ldc_w -2.278
      // 47da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47e3: aastore
      // 47e4: dup
      // 47e5: bipush 34
      // 47e7: new net/minecraft/client/animation/Keyframe
      // 47ea: dup
      // 47eb: ldc 1.42
      // 47ed: ldc_w -10.718
      // 47f0: ldc_w 19.744
      // 47f3: ldc_w -3.718
      // 47f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47ff: aastore
      // 4800: dup
      // 4801: bipush 35
      // 4803: new net/minecraft/client/animation/Keyframe
      // 4806: dup
      // 4807: ldc 1.46
      // 4809: ldc_w -11.501
      // 480c: ldc_w 18.804
      // 480f: ldc_w -4.762
      // 4812: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4815: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4818: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 481b: aastore
      // 481c: dup
      // 481d: bipush 36
      // 481f: new net/minecraft/client/animation/Keyframe
      // 4822: dup
      // 4823: ldc 1.5
      // 4825: ldc_w -11.987
      // 4828: ldc_w 17.845
      // 482b: ldc_w -5.481
      // 482e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4831: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4834: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4837: aastore
      // 4838: dup
      // 4839: bipush 37
      // 483b: new net/minecraft/client/animation/Keyframe
      // 483e: dup
      // 483f: ldc 1.54
      // 4841: ldc_w -12.24
      // 4844: ldc_w 16.897
      // 4847: ldc_w -5.937
      // 484a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 484d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4850: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4853: aastore
      // 4854: dup
      // 4855: bipush 38
      // 4857: new net/minecraft/client/animation/Keyframe
      // 485a: dup
      // 485b: ldc 1.58
      // 485d: ldc_w -12.317
      // 4860: ldc_w 15.99
      // 4863: ldc_w -6.191
      // 4866: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4869: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 486c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 486f: aastore
      // 4870: dup
      // 4871: bipush 39
      // 4873: new net/minecraft/client/animation/Keyframe
      // 4876: dup
      // 4877: ldc 1.62
      // 4879: ldc_w -12.27
      // 487c: ldc_w 15.15
      // 487f: ldc_w -6.294
      // 4882: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4885: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4888: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 488b: aastore
      // 488c: dup
      // 488d: bipush 40
      // 488f: new net/minecraft/client/animation/Keyframe
      // 4892: dup
      // 4893: ldc 1.71
      // 4895: ldc_w -11.98
      // 4898: ldc_w 13.777
      // 489b: ldc_w -6.23
      // 489e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48a7: aastore
      // 48a8: dup
      // 48a9: bipush 41
      // 48ab: new net/minecraft/client/animation/Keyframe
      // 48ae: dup
      // 48af: ldc 1.75
      // 48b1: ldc_w -11.811
      // 48b4: ldc_w 13.296
      // 48b7: ldc_w -6.142
      // 48ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48c3: aastore
      // 48c4: dup
      // 48c5: bipush 42
      // 48c7: new net/minecraft/client/animation/Keyframe
      // 48ca: dup
      // 48cb: ldc 1.79
      // 48cd: ldc_w -11.67
      // 48d0: ldc_w 12.988
      // 48d3: ldc_w -6.064
      // 48d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48df: aastore
      // 48e0: dup
      // 48e1: bipush 43
      // 48e3: new net/minecraft/client/animation/Keyframe
      // 48e6: dup
      // 48e7: ldc 1.83
      // 48e9: ldc_w -10.171
      // 48ec: ldc_w 9.115
      // 48ef: ldc_w -4.945
      // 48f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48fb: aastore
      // 48fc: dup
      // 48fd: bipush 44
      // 48ff: new net/minecraft/client/animation/Keyframe
      // 4902: dup
      // 4903: ldc 1.88
      // 4905: ldc_w -5.874
      // 4908: ldc_w 8.21
      // 490b: ldc_w -0.504
      // 490e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4911: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4914: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4917: aastore
      // 4918: dup
      // 4919: bipush 45
      // 491b: new net/minecraft/client/animation/Keyframe
      // 491e: dup
      // 491f: ldc 1.92
      // 4921: ldc_w -1.877
      // 4924: ldc_w -1.214
      // 4927: ldc_w 3.507
      // 492a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 492d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4930: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4933: aastore
      // 4934: dup
      // 4935: bipush 46
      // 4937: new net/minecraft/client/animation/Keyframe
      // 493a: dup
      // 493b: ldc_w 1.96
      // 493e: ldc_w -1.957
      // 4941: ldc_w 0.213
      // 4944: ldc_w 3.458
      // 4947: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 494a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 494d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4950: aastore
      // 4951: dup
      // 4952: bipush 47
      // 4954: new net/minecraft/client/animation/Keyframe
      // 4957: dup
      // 4958: fconst_2
      // 4959: ldc_w -14.136
      // 495c: ldc_w 2.259
      // 495f: ldc_w -11.328
      // 4962: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4965: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4968: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 496b: aastore
      // 496c: dup
      // 496d: bipush 48
      // 496f: new net/minecraft/client/animation/Keyframe
      // 4972: dup
      // 4973: ldc_w 2.04
      // 4976: ldc_w -8.644
      // 4979: ldc_w -9.439
      // 497c: ldc_w -7.458
      // 497f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4982: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4985: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4988: aastore
      // 4989: dup
      // 498a: bipush 49
      // 498c: new net/minecraft/client/animation/Keyframe
      // 498f: dup
      // 4990: ldc_w 2.08
      // 4993: ldc_w -8.327
      // 4996: ldc_w -5.833
      // 4999: ldc_w -5.641
      // 499c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 499f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49a5: aastore
      // 49a6: dup
      // 49a7: bipush 50
      // 49a9: new net/minecraft/client/animation/Keyframe
      // 49ac: dup
      // 49ad: ldc_w 2.12
      // 49b0: ldc_w -6.921
      // 49b3: ldc_w -1.473
      // 49b6: ldc_w -3.368
      // 49b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c2: aastore
      // 49c3: dup
      // 49c4: bipush 51
      // 49c6: new net/minecraft/client/animation/Keyframe
      // 49c9: dup
      // 49ca: ldc 2.17
      // 49cc: ldc_w -0.004
      // 49cf: ldc_w -0.352
      // 49d2: ldc_w -0.003
      // 49d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49de: aastore
      // 49df: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 49e2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 49e5: ldc_w "CTR_lowerarm_right"
      // 49e8: new net/minecraft/client/animation/AnimationChannel
      // 49eb: dup
      // 49ec: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 49ef: bipush 3
      // 49f0: anewarray 28
      // 49f3: dup
      // 49f4: bipush 0
      // 49f5: new net/minecraft/client/animation/Keyframe
      // 49f8: dup
      // 49f9: ldc 1.04
      // 49fb: dconst_1
      // 49fc: dconst_1
      // 49fd: dconst_1
      // 49fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a07: aastore
      // 4a08: dup
      // 4a09: bipush 1
      // 4a0a: new net/minecraft/client/animation/Keyframe
      // 4a0d: dup
      // 4a0e: ldc 1.08
      // 4a10: ldc2_w 1.2740000486373901
      // 4a13: dconst_1
      // 4a14: dconst_1
      // 4a15: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a1e: aastore
      // 4a1f: dup
      // 4a20: bipush 2
      // 4a21: new net/minecraft/client/animation/Keyframe
      // 4a24: dup
      // 4a25: ldc 1.12
      // 4a27: dconst_1
      // 4a28: dconst_1
      // 4a29: dconst_1
      // 4a2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 4a2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a33: aastore
      // 4a34: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4a37: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4a3a: ldc_w "CTR_upperarm_right.001"
      // 4a3d: new net/minecraft/client/animation/AnimationChannel
      // 4a40: dup
      // 4a41: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4a44: bipush 53
      // 4a46: anewarray 28
      // 4a49: dup
      // 4a4a: bipush 0
      // 4a4b: new net/minecraft/client/animation/Keyframe
      // 4a4e: dup
      // 4a4f: fconst_0
      // 4a50: ldc_w 0.168
      // 4a53: ldc_w 17.864
      // 4a56: ldc_w 2.048
      // 4a59: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a62: aastore
      // 4a63: dup
      // 4a64: bipush 1
      // 4a65: new net/minecraft/client/animation/Keyframe
      // 4a68: dup
      // 4a69: ldc 0.04
      // 4a6b: ldc_w -0.056
      // 4a6e: ldc_w 15.772
      // 4a71: ldc_w 1.821
      // 4a74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a7d: aastore
      // 4a7e: dup
      // 4a7f: bipush 2
      // 4a80: new net/minecraft/client/animation/Keyframe
      // 4a83: dup
      // 4a84: ldc 0.08
      // 4a86: ldc_w -0.107
      // 4a89: ldc_w 15.862
      // 4a8c: ldc_w 1.781
      // 4a8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a98: aastore
      // 4a99: dup
      // 4a9a: bipush 3
      // 4a9b: new net/minecraft/client/animation/Keyframe
      // 4a9e: dup
      // 4a9f: ldc 0.12
      // 4aa1: ldc_w -0.078
      // 4aa4: ldc_w 16.174
      // 4aa7: ldc_w 1.811
      // 4aaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ab0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ab3: aastore
      // 4ab4: dup
      // 4ab5: bipush 4
      // 4ab6: new net/minecraft/client/animation/Keyframe
      // 4ab9: dup
      // 4aba: ldc 0.17
      // 4abc: ldc_w -0.357
      // 4abf: ldc_w 18.067
      // 4ac2: ldc_w 1.613
      // 4ac5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ac8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4acb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ace: aastore
      // 4acf: dup
      // 4ad0: bipush 5
      // 4ad1: new net/minecraft/client/animation/Keyframe
      // 4ad4: dup
      // 4ad5: ldc 0.21
      // 4ad7: ldc_w -3.25
      // 4ada: ldc_w 11.682
      // 4add: ldc_w -0.739
      // 4ae0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ae3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ae6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ae9: aastore
      // 4aea: dup
      // 4aeb: bipush 6
      // 4aed: new net/minecraft/client/animation/Keyframe
      // 4af0: dup
      // 4af1: ldc 0.25
      // 4af3: ldc_w -4.329
      // 4af6: ldc_w -4.565
      // 4af9: ldc_w -9.45
      // 4afc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b05: aastore
      // 4b06: dup
      // 4b07: bipush 7
      // 4b09: new net/minecraft/client/animation/Keyframe
      // 4b0c: dup
      // 4b0d: ldc 0.29
      // 4b0f: ldc_w -11.012
      // 4b12: ldc_w -14.957
      // 4b15: ldc_w -11.442
      // 4b18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b21: aastore
      // 4b22: dup
      // 4b23: bipush 8
      // 4b25: new net/minecraft/client/animation/Keyframe
      // 4b28: dup
      // 4b29: ldc 0.33
      // 4b2b: ldc_w -11.046
      // 4b2e: ldc_w -7.493
      // 4b31: ldc_w -16.682
      // 4b34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b3d: aastore
      // 4b3e: dup
      // 4b3f: bipush 9
      // 4b41: new net/minecraft/client/animation/Keyframe
      // 4b44: dup
      // 4b45: ldc 0.38
      // 4b47: ldc_w -19.306
      // 4b4a: ldc_w 13.022
      // 4b4d: ldc_w -13.406
      // 4b50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b59: aastore
      // 4b5a: dup
      // 4b5b: bipush 10
      // 4b5d: new net/minecraft/client/animation/Keyframe
      // 4b60: dup
      // 4b61: ldc 0.42
      // 4b63: ldc_w -28.489
      // 4b66: ldc_w 40.841
      // 4b69: ldc_w -24.247
      // 4b6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b75: aastore
      // 4b76: dup
      // 4b77: bipush 11
      // 4b79: new net/minecraft/client/animation/Keyframe
      // 4b7c: dup
      // 4b7d: ldc 0.46
      // 4b7f: ldc_w -53.754
      // 4b82: ldc_w 40.986
      // 4b85: ldc_w -47.98
      // 4b88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b91: aastore
      // 4b92: dup
      // 4b93: bipush 12
      // 4b95: new net/minecraft/client/animation/Keyframe
      // 4b98: dup
      // 4b99: ldc 0.5
      // 4b9b: ldc_w -77.735
      // 4b9e: ldc_w 5.042
      // 4ba1: ldc_w -48.705
      // 4ba4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ba7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4baa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bad: aastore
      // 4bae: dup
      // 4baf: bipush 13
      // 4bb1: new net/minecraft/client/animation/Keyframe
      // 4bb4: dup
      // 4bb5: ldc 0.54
      // 4bb7: ldc_w -67.683
      // 4bba: ldc_w -35.217
      // 4bbd: ldc_w -70.935
      // 4bc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bc9: aastore
      // 4bca: dup
      // 4bcb: bipush 14
      // 4bcd: new net/minecraft/client/animation/Keyframe
      // 4bd0: dup
      // 4bd1: ldc 0.58
      // 4bd3: ldc_w -63.544
      // 4bd6: ldc_w -103.746
      // 4bd9: ldc_w -62.538
      // 4bdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4be2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4be5: aastore
      // 4be6: dup
      // 4be7: bipush 15
      // 4be9: new net/minecraft/client/animation/Keyframe
      // 4bec: dup
      // 4bed: ldc 0.62
      // 4bef: ldc_w -12.331
      // 4bf2: ldc_w -138.821
      // 4bf5: ldc_w -10.169
      // 4bf8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bfb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bfe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c01: aastore
      // 4c02: dup
      // 4c03: bipush 16
      // 4c05: new net/minecraft/client/animation/Keyframe
      // 4c08: dup
      // 4c09: ldc 0.67
      // 4c0b: ldc_w 12.534
      // 4c0e: ldc_w -123.309
      // 4c11: ldc_w 15.078
      // 4c14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c1d: aastore
      // 4c1e: dup
      // 4c1f: bipush 17
      // 4c21: new net/minecraft/client/animation/Keyframe
      // 4c24: dup
      // 4c25: ldc 0.71
      // 4c27: ldc_w 13.515
      // 4c2a: ldc_w -110.686
      // 4c2d: ldc 16.153
      // 4c2f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c32: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c35: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c38: aastore
      // 4c39: dup
      // 4c3a: bipush 18
      // 4c3c: new net/minecraft/client/animation/Keyframe
      // 4c3f: dup
      // 4c40: ldc 0.75
      // 4c42: ldc_w 11.439
      // 4c45: ldc_w -102.295
      // 4c48: ldc_w 14.082
      // 4c4b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c4e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c51: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c54: aastore
      // 4c55: dup
      // 4c56: bipush 19
      // 4c58: new net/minecraft/client/animation/Keyframe
      // 4c5b: dup
      // 4c5c: ldc 0.79
      // 4c5e: ldc_w 8.784
      // 4c61: ldc_w -94.361
      // 4c64: ldc_w 11.403
      // 4c67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c70: aastore
      // 4c71: dup
      // 4c72: bipush 20
      // 4c74: new net/minecraft/client/animation/Keyframe
      // 4c77: dup
      // 4c78: ldc 0.83
      // 4c7a: ldc_w 5.447
      // 4c7d: ldc_w -86.408
      // 4c80: ldc_w 8.002
      // 4c83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c8c: aastore
      // 4c8d: dup
      // 4c8e: bipush 21
      // 4c90: new net/minecraft/client/animation/Keyframe
      // 4c93: dup
      // 4c94: ldc 0.88
      // 4c96: ldc_w 1.907
      // 4c99: ldc_w -77.408
      // 4c9c: ldc_w 4.365
      // 4c9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ca2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ca5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ca8: aastore
      // 4ca9: dup
      // 4caa: bipush 22
      // 4cac: new net/minecraft/client/animation/Keyframe
      // 4caf: dup
      // 4cb0: ldc 0.92
      // 4cb2: ldc_w -1.564
      // 4cb5: ldc_w -66.968
      // 4cb8: ldc_w 0.758
      // 4cbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cc4: aastore
      // 4cc5: dup
      // 4cc6: bipush 23
      // 4cc8: new net/minecraft/client/animation/Keyframe
      // 4ccb: dup
      // 4ccc: ldc 0.96
      // 4cce: ldc_w 4.753
      // 4cd1: ldc_w -63.661
      // 4cd4: ldc_w 7.414
      // 4cd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cdd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ce0: aastore
      // 4ce1: dup
      // 4ce2: bipush 24
      // 4ce4: new net/minecraft/client/animation/Keyframe
      // 4ce7: dup
      // 4ce8: fconst_1
      // 4ce9: ldc_w 15.098
      // 4cec: ldc_w -72.874
      // 4cef: ldc_w 18.149
      // 4cf2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cf5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cf8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cfb: aastore
      // 4cfc: dup
      // 4cfd: bipush 25
      // 4cff: new net/minecraft/client/animation/Keyframe
      // 4d02: dup
      // 4d03: ldc 1.04
      // 4d05: ldc_w -10.602
      // 4d08: ldc_w -87.941
      // 4d0b: ldc_w -8.598
      // 4d0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d17: aastore
      // 4d18: dup
      // 4d19: bipush 26
      // 4d1b: new net/minecraft/client/animation/Keyframe
      // 4d1e: dup
      // 4d1f: ldc 1.08
      // 4d21: ldc_w 105.103
      // 4d24: ldc_w -117.987
      // 4d27: ldc_w 108.626
      // 4d2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d33: aastore
      // 4d34: dup
      // 4d35: bipush 27
      // 4d37: new net/minecraft/client/animation/Keyframe
      // 4d3a: dup
      // 4d3b: ldc 1.12
      // 4d3d: ldc_w 37.458
      // 4d40: ldc_w 21.664
      // 4d43: ldc_w 34.534
      // 4d46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d4f: aastore
      // 4d50: dup
      // 4d51: bipush 28
      // 4d53: new net/minecraft/client/animation/Keyframe
      // 4d56: dup
      // 4d57: ldc 1.17
      // 4d59: ldc_w 17.927
      // 4d5c: ldc_w -33.157
      // 4d5f: ldc_w 22.537
      // 4d62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d6b: aastore
      // 4d6c: dup
      // 4d6d: bipush 29
      // 4d6f: new net/minecraft/client/animation/Keyframe
      // 4d72: dup
      // 4d73: ldc 1.21
      // 4d75: ldc_w 10.195
      // 4d78: ldc_w -53.913
      // 4d7b: ldc_w 13.297
      // 4d7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d87: aastore
      // 4d88: dup
      // 4d89: bipush 30
      // 4d8b: new net/minecraft/client/animation/Keyframe
      // 4d8e: dup
      // 4d8f: ldc 1.25
      // 4d91: ldc_w 8.082
      // 4d94: ldc_w -62.786
      // 4d97: ldc_w 10.929
      // 4d9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4da0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4da3: aastore
      // 4da4: dup
      // 4da5: bipush 31
      // 4da7: new net/minecraft/client/animation/Keyframe
      // 4daa: dup
      // 4dab: ldc 1.29
      // 4dad: ldc_w 12.956
      // 4db0: ldc_w -65.875
      // 4db3: ldc_w 16.012
      // 4db6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4db9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dbf: aastore
      // 4dc0: dup
      // 4dc1: bipush 32
      // 4dc3: new net/minecraft/client/animation/Keyframe
      // 4dc6: dup
      // 4dc7: ldc 1.33
      // 4dc9: ldc_w 32.77
      // 4dcc: ldc_w -63.69
      // 4dcf: ldc_w 36.808
      // 4dd2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dd5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dd8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ddb: aastore
      // 4ddc: dup
      // 4ddd: bipush 33
      // 4ddf: new net/minecraft/client/animation/Keyframe
      // 4de2: dup
      // 4de3: ldc 1.38
      // 4de5: ldc_w 48.004
      // 4de8: ldc_w -48.418
      // 4deb: ldc_w 53.56
      // 4dee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4df1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4df4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4df7: aastore
      // 4df8: dup
      // 4df9: bipush 34
      // 4dfb: new net/minecraft/client/animation/Keyframe
      // 4dfe: dup
      // 4dff: ldc 1.42
      // 4e01: ldc_w 52.92
      // 4e04: ldc_w -39.821
      // 4e07: ldc_w 59.583
      // 4e0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e13: aastore
      // 4e14: dup
      // 4e15: bipush 35
      // 4e17: new net/minecraft/client/animation/Keyframe
      // 4e1a: dup
      // 4e1b: ldc 1.46
      // 4e1d: ldc_w 54.35
      // 4e20: ldc_w -38.957
      // 4e23: ldc_w 61.198
      // 4e26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e2f: aastore
      // 4e30: dup
      // 4e31: bipush 36
      // 4e33: new net/minecraft/client/animation/Keyframe
      // 4e36: dup
      // 4e37: ldc 1.5
      // 4e39: ldc_w 54.193
      // 4e3c: ldc_w -41.647
      // 4e3f: ldc_w 60.694
      // 4e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e4b: aastore
      // 4e4c: dup
      // 4e4d: bipush 37
      // 4e4f: new net/minecraft/client/animation/Keyframe
      // 4e52: dup
      // 4e53: ldc 1.54
      // 4e55: ldc_w 52.78
      // 4e58: ldc_w -44.592
      // 4e5b: ldc_w 58.898
      // 4e5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e67: aastore
      // 4e68: dup
      // 4e69: bipush 38
      // 4e6b: new net/minecraft/client/animation/Keyframe
      // 4e6e: dup
      // 4e6f: ldc 1.58
      // 4e71: ldc_w 50.585
      // 4e74: ldc_w -45.454
      // 4e77: ldc_w 56.519
      // 4e7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e83: aastore
      // 4e84: dup
      // 4e85: bipush 39
      // 4e87: new net/minecraft/client/animation/Keyframe
      // 4e8a: dup
      // 4e8b: ldc 1.62
      // 4e8d: ldc_w 48.222
      // 4e90: ldc_w -44.445
      // 4e93: ldc_w 54.138
      // 4e96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e9f: aastore
      // 4ea0: dup
      // 4ea1: bipush 40
      // 4ea3: new net/minecraft/client/animation/Keyframe
      // 4ea6: dup
      // 4ea7: ldc 1.67
      // 4ea9: ldc_w 45.909
      // 4eac: ldc_w -42.545
      // 4eaf: ldc_w 51.891
      // 4eb2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4eb5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4eb8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ebb: aastore
      // 4ebc: dup
      // 4ebd: bipush 41
      // 4ebf: new net/minecraft/client/animation/Keyframe
      // 4ec2: dup
      // 4ec3: ldc 1.71
      // 4ec5: ldc_w 43.365
      // 4ec8: ldc_w -40.492
      // 4ecb: ldc_w 49.412
      // 4ece: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ed1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ed4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ed7: aastore
      // 4ed8: dup
      // 4ed9: bipush 42
      // 4edb: new net/minecraft/client/animation/Keyframe
      // 4ede: dup
      // 4edf: ldc 1.75
      // 4ee1: ldc_w 40.13
      // 4ee4: ldc_w -38.519
      // 4ee7: ldc_w 46.183
      // 4eea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4eed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ef0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ef3: aastore
      // 4ef4: dup
      // 4ef5: bipush 43
      // 4ef7: new net/minecraft/client/animation/Keyframe
      // 4efa: dup
      // 4efb: ldc 1.79
      // 4efd: ldc_w 36.138
      // 4f00: ldc_w -36.217
      // 4f03: ldc_w 42.162
      // 4f06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f0f: aastore
      // 4f10: dup
      // 4f11: bipush 44
      // 4f13: new net/minecraft/client/animation/Keyframe
      // 4f16: dup
      // 4f17: ldc 1.83
      // 4f19: ldc_w 29.124
      // 4f1c: ldc_w -31.131
      // 4f1f: ldc_w 35.164
      // 4f22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f2b: aastore
      // 4f2c: dup
      // 4f2d: bipush 45
      // 4f2f: new net/minecraft/client/animation/Keyframe
      // 4f32: dup
      // 4f33: ldc 1.88
      // 4f35: ldc_w 19.331
      // 4f38: ldc_w -22.63
      // 4f3b: ldc_w 25.509
      // 4f3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f47: aastore
      // 4f48: dup
      // 4f49: bipush 46
      // 4f4b: new net/minecraft/client/animation/Keyframe
      // 4f4e: dup
      // 4f4f: ldc 1.92
      // 4f51: ldc_w 9.605
      // 4f54: ldc_w -13.218
      // 4f57: ldc_w 16.241
      // 4f5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f63: aastore
      // 4f64: dup
      // 4f65: bipush 47
      // 4f67: new net/minecraft/client/animation/Keyframe
      // 4f6a: dup
      // 4f6b: ldc_w 1.96
      // 4f6e: ldc_w 8.431
      // 4f71: ldc_w 1.34
      // 4f74: ldc_w 2.927
      // 4f77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f80: aastore
      // 4f81: dup
      // 4f82: bipush 48
      // 4f84: new net/minecraft/client/animation/Keyframe
      // 4f87: dup
      // 4f88: fconst_2
      // 4f89: ldc_w 1.925
      // 4f8c: ldc_w 13.837
      // 4f8f: ldc_w 3.35
      // 4f92: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4f95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f9b: aastore
      // 4f9c: dup
      // 4f9d: bipush 49
      // 4f9f: new net/minecraft/client/animation/Keyframe
      // 4fa2: dup
      // 4fa3: ldc_w 2.04
      // 4fa6: ldc_w -0.781
      // 4fa9: ldc_w 14.153
      // 4fac: ldc_w 1.202
      // 4faf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fb8: aastore
      // 4fb9: dup
      // 4fba: bipush 50
      // 4fbc: new net/minecraft/client/animation/Keyframe
      // 4fbf: dup
      // 4fc0: ldc_w 2.08
      // 4fc3: ldc_w -0.874
      // 4fc6: ldc_w 16.799
      // 4fc9: ldc_w 1.166
      // 4fcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fd5: aastore
      // 4fd6: dup
      // 4fd7: bipush 51
      // 4fd9: new net/minecraft/client/animation/Keyframe
      // 4fdc: dup
      // 4fdd: ldc_w 2.12
      // 4fe0: ldc_w -0.757
      // 4fe3: ldc_w 18.997
      // 4fe6: ldc_w 1.289
      // 4fe9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4fec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ff2: aastore
      // 4ff3: dup
      // 4ff4: bipush 52
      // 4ff6: new net/minecraft/client/animation/Keyframe
      // 4ff9: dup
      // 4ffa: ldc 2.17
      // 4ffc: ldc_w 0.224
      // 4fff: ldc_w 18.678
      // 5002: ldc_w 2.11
      // 5005: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5008: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 500b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 500e: aastore
      // 500f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5012: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5015: ldc_w "CTR_upperarm_right.001"
      // 5018: new net/minecraft/client/animation/AnimationChannel
      // 501b: dup
      // 501c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 501f: bipush 51
      // 5021: anewarray 28
      // 5024: dup
      // 5025: bipush 0
      // 5026: new net/minecraft/client/animation/Keyframe
      // 5029: dup
      // 502a: fconst_0
      // 502b: dconst_1
      // 502c: dconst_1
      // 502d: ldc2_w 1.6740000247955322
      // 5030: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5033: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5036: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5039: aastore
      // 503a: dup
      // 503b: bipush 1
      // 503c: new net/minecraft/client/animation/Keyframe
      // 503f: dup
      // 5040: ldc 0.04
      // 5042: dconst_1
      // 5043: dconst_1
      // 5044: ldc2_w 1.725000023841858
      // 5047: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 504a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 504d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5050: aastore
      // 5051: dup
      // 5052: bipush 2
      // 5053: new net/minecraft/client/animation/Keyframe
      // 5056: dup
      // 5057: ldc 0.08
      // 5059: dconst_1
      // 505a: dconst_1
      // 505b: ldc2_w 1.7389999628067017
      // 505e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5061: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5064: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5067: aastore
      // 5068: dup
      // 5069: bipush 3
      // 506a: new net/minecraft/client/animation/Keyframe
      // 506d: dup
      // 506e: ldc 0.12
      // 5070: dconst_1
      // 5071: dconst_1
      // 5072: ldc2_w 1.7020000219345093
      // 5075: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5078: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 507b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 507e: aastore
      // 507f: dup
      // 5080: bipush 4
      // 5081: new net/minecraft/client/animation/Keyframe
      // 5084: dup
      // 5085: ldc 0.17
      // 5087: dconst_1
      // 5088: dconst_1
      // 5089: ldc2_w 1.5750000476837158
      // 508c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 508f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5092: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5095: aastore
      // 5096: dup
      // 5097: bipush 5
      // 5098: new net/minecraft/client/animation/Keyframe
      // 509b: dup
      // 509c: ldc 0.21
      // 509e: dconst_1
      // 509f: dconst_1
      // 50a0: ldc2_w 1.503999948501587
      // 50a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 50a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50ac: aastore
      // 50ad: dup
      // 50ae: bipush 6
      // 50b0: new net/minecraft/client/animation/Keyframe
      // 50b3: dup
      // 50b4: ldc 0.25
      // 50b6: dconst_1
      // 50b7: dconst_1
      // 50b8: ldc2_w 1.5800000429153442
      // 50bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 50be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50c4: aastore
      // 50c5: dup
      // 50c6: bipush 7
      // 50c8: new net/minecraft/client/animation/Keyframe
      // 50cb: dup
      // 50cc: ldc 0.29
      // 50ce: dconst_1
      // 50cf: dconst_1
      // 50d0: ldc2_w 1.63100004196167
      // 50d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 50d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50dc: aastore
      // 50dd: dup
      // 50de: bipush 8
      // 50e0: new net/minecraft/client/animation/Keyframe
      // 50e3: dup
      // 50e4: ldc 0.33
      // 50e6: dconst_1
      // 50e7: dconst_1
      // 50e8: ldc2_w 1.4359999895095825
      // 50eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 50ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50f4: aastore
      // 50f5: dup
      // 50f6: bipush 9
      // 50f8: new net/minecraft/client/animation/Keyframe
      // 50fb: dup
      // 50fc: ldc 0.38
      // 50fe: dconst_1
      // 50ff: dconst_1
      // 5100: ldc2_w 1.093999981880188
      // 5103: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5106: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5109: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 510c: aastore
      // 510d: dup
      // 510e: bipush 10
      // 5110: new net/minecraft/client/animation/Keyframe
      // 5113: dup
      // 5114: ldc 0.42
      // 5116: dconst_1
      // 5117: dconst_1
      // 5118: ldc2_w 0.781000018119812
      // 511b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 511e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5124: aastore
      // 5125: dup
      // 5126: bipush 11
      // 5128: new net/minecraft/client/animation/Keyframe
      // 512b: dup
      // 512c: ldc 0.46
      // 512e: dconst_1
      // 512f: dconst_1
      // 5130: ldc2_w 0.7910000085830688
      // 5133: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5136: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5139: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 513c: aastore
      // 513d: dup
      // 513e: bipush 12
      // 5140: new net/minecraft/client/animation/Keyframe
      // 5143: dup
      // 5144: ldc 0.5
      // 5146: dconst_1
      // 5147: dconst_1
      // 5148: ldc2_w 1.0609999895095825
      // 514b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 514e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5151: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5154: aastore
      // 5155: dup
      // 5156: bipush 13
      // 5158: new net/minecraft/client/animation/Keyframe
      // 515b: dup
      // 515c: ldc 0.54
      // 515e: dconst_1
      // 515f: dconst_1
      // 5160: ldc2_w 1.4259999990463257
      // 5163: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5166: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5169: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 516c: aastore
      // 516d: dup
      // 516e: bipush 14
      // 5170: new net/minecraft/client/animation/Keyframe
      // 5173: dup
      // 5174: ldc 0.58
      // 5176: dconst_1
      // 5177: dconst_1
      // 5178: ldc2_w 1.902999997138977
      // 517b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 517e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5181: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5184: aastore
      // 5185: dup
      // 5186: bipush 15
      // 5188: new net/minecraft/client/animation/Keyframe
      // 518b: dup
      // 518c: ldc 0.62
      // 518e: dconst_1
      // 518f: dconst_1
      // 5190: ldc2_w 2.4030001163482666
      // 5193: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5196: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5199: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 519c: aastore
      // 519d: dup
      // 519e: bipush 16
      // 51a0: new net/minecraft/client/animation/Keyframe
      // 51a3: dup
      // 51a4: ldc 0.67
      // 51a6: dconst_1
      // 51a7: dconst_1
      // 51a8: ldc2_w 2.8310000896453857
      // 51ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51b4: aastore
      // 51b5: dup
      // 51b6: bipush 17
      // 51b8: new net/minecraft/client/animation/Keyframe
      // 51bb: dup
      // 51bc: ldc 0.71
      // 51be: dconst_1
      // 51bf: dconst_1
      // 51c0: ldc2_w 3.1419999599456787
      // 51c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51cc: aastore
      // 51cd: dup
      // 51ce: bipush 18
      // 51d0: new net/minecraft/client/animation/Keyframe
      // 51d3: dup
      // 51d4: ldc 0.75
      // 51d6: dconst_1
      // 51d7: dconst_1
      // 51d8: ldc2_w 3.3519999980926514
      // 51db: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51e4: aastore
      // 51e5: dup
      // 51e6: bipush 19
      // 51e8: new net/minecraft/client/animation/Keyframe
      // 51eb: dup
      // 51ec: ldc 0.79
      // 51ee: dconst_1
      // 51ef: dconst_1
      // 51f0: ldc2_w 3.4730000495910645
      // 51f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 51f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51fc: aastore
      // 51fd: dup
      // 51fe: bipush 20
      // 5200: new net/minecraft/client/animation/Keyframe
      // 5203: dup
      // 5204: ldc 0.83
      // 5206: dconst_1
      // 5207: dconst_1
      // 5208: ldc2_w 3.499000072479248
      // 520b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 520e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5211: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5214: aastore
      // 5215: dup
      // 5216: bipush 21
      // 5218: new net/minecraft/client/animation/Keyframe
      // 521b: dup
      // 521c: ldc 0.88
      // 521e: dconst_1
      // 521f: dconst_1
      // 5220: ldc2_w 3.421999931335449
      // 5223: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5226: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5229: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 522c: aastore
      // 522d: dup
      // 522e: bipush 22
      // 5230: new net/minecraft/client/animation/Keyframe
      // 5233: dup
      // 5234: ldc 0.92
      // 5236: dconst_1
      // 5237: dconst_1
      // 5238: ldc2_w 3.2320001125335693
      // 523b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 523e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5241: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5244: aastore
      // 5245: dup
      // 5246: bipush 23
      // 5248: new net/minecraft/client/animation/Keyframe
      // 524b: dup
      // 524c: ldc 0.96
      // 524e: dconst_1
      // 524f: dconst_1
      // 5250: ldc2_w 2.941999912261963
      // 5253: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5256: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5259: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 525c: aastore
      // 525d: dup
      // 525e: bipush 24
      // 5260: new net/minecraft/client/animation/Keyframe
      // 5263: dup
      // 5264: fconst_1
      // 5265: dconst_1
      // 5266: dconst_1
      // 5267: ldc2_w 2.687999963760376
      // 526a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 526d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5270: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5273: aastore
      // 5274: dup
      // 5275: bipush 25
      // 5277: new net/minecraft/client/animation/Keyframe
      // 527a: dup
      // 527b: ldc 1.04
      // 527d: dconst_1
      // 527e: dconst_1
      // 527f: ldc2_w 2.5390000343322754
      // 5282: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5285: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5288: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 528b: aastore
      // 528c: dup
      // 528d: bipush 26
      // 528f: new net/minecraft/client/animation/Keyframe
      // 5292: dup
      // 5293: ldc 1.08
      // 5295: ldc2_w 0.9769999980926514
      // 5298: ldc2_w 0.9909999966621399
      // 529b: ldc2_w 0.8489999771118164
      // 529e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52a7: aastore
      // 52a8: dup
      // 52a9: bipush 27
      // 52ab: new net/minecraft/client/animation/Keyframe
      // 52ae: dup
      // 52af: ldc 1.12
      // 52b1: dconst_1
      // 52b2: dconst_1
      // 52b3: ldc2_w 0.6779999732971191
      // 52b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52bf: aastore
      // 52c0: dup
      // 52c1: bipush 28
      // 52c3: new net/minecraft/client/animation/Keyframe
      // 52c6: dup
      // 52c7: ldc 1.17
      // 52c9: dconst_1
      // 52ca: dconst_1
      // 52cb: ldc2_w 0.7960000038146973
      // 52ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52d7: aastore
      // 52d8: dup
      // 52d9: bipush 29
      // 52db: new net/minecraft/client/animation/Keyframe
      // 52de: dup
      // 52df: ldc 1.21
      // 52e1: dconst_1
      // 52e2: dconst_1
      // 52e3: ldc2_w 0.8600000143051147
      // 52e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 52e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52ef: aastore
      // 52f0: dup
      // 52f1: bipush 30
      // 52f3: new net/minecraft/client/animation/Keyframe
      // 52f6: dup
      // 52f7: ldc 1.25
      // 52f9: dconst_1
      // 52fa: dconst_1
      // 52fb: ldc2_w 0.7839999794960022
      // 52fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5301: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5304: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5307: aastore
      // 5308: dup
      // 5309: bipush 31
      // 530b: new net/minecraft/client/animation/Keyframe
      // 530e: dup
      // 530f: ldc 1.29
      // 5311: dconst_1
      // 5312: dconst_1
      // 5313: ldc2_w 0.550000011920929
      // 5316: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 531c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 531f: aastore
      // 5320: dup
      // 5321: bipush 32
      // 5323: new net/minecraft/client/animation/Keyframe
      // 5326: dup
      // 5327: ldc 1.33
      // 5329: dconst_1
      // 532a: dconst_1
      // 532b: ldc2_w 0.4390000104904175
      // 532e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5331: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5334: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5337: aastore
      // 5338: dup
      // 5339: bipush 33
      // 533b: new net/minecraft/client/animation/Keyframe
      // 533e: dup
      // 533f: ldc 1.38
      // 5341: dconst_1
      // 5342: dconst_1
      // 5343: ldc2_w 0.5809999704360962
      // 5346: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5349: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 534c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 534f: aastore
      // 5350: dup
      // 5351: bipush 34
      // 5353: new net/minecraft/client/animation/Keyframe
      // 5356: dup
      // 5357: ldc 1.42
      // 5359: dconst_1
      // 535a: dconst_1
      // 535b: ldc2_w 0.8180000185966492
      // 535e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5361: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5364: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5367: aastore
      // 5368: dup
      // 5369: bipush 35
      // 536b: new net/minecraft/client/animation/Keyframe
      // 536e: dup
      // 536f: ldc 1.5
      // 5371: dconst_1
      // 5372: dconst_1
      // 5373: ldc2_w 1.3320000171661377
      // 5376: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5379: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 537c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 537f: aastore
      // 5380: dup
      // 5381: bipush 36
      // 5383: new net/minecraft/client/animation/Keyframe
      // 5386: dup
      // 5387: ldc 1.54
      // 5389: dconst_1
      // 538a: dconst_1
      // 538b: ldc2_w 1.5709999799728394
      // 538e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5391: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5394: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5397: aastore
      // 5398: dup
      // 5399: bipush 37
      // 539b: new net/minecraft/client/animation/Keyframe
      // 539e: dup
      // 539f: ldc 1.58
      // 53a1: dconst_1
      // 53a2: dconst_1
      // 53a3: ldc2_w 1.7790000438690186
      // 53a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53af: aastore
      // 53b0: dup
      // 53b1: bipush 38
      // 53b3: new net/minecraft/client/animation/Keyframe
      // 53b6: dup
      // 53b7: ldc 1.62
      // 53b9: dconst_1
      // 53ba: dconst_1
      // 53bb: ldc2_w 1.9420000314712524
      // 53be: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53c7: aastore
      // 53c8: dup
      // 53c9: bipush 39
      // 53cb: new net/minecraft/client/animation/Keyframe
      // 53ce: dup
      // 53cf: ldc 1.71
      // 53d1: dconst_1
      // 53d2: dconst_1
      // 53d3: ldc2_w 2.1570000648498535
      // 53d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53df: aastore
      // 53e0: dup
      // 53e1: bipush 40
      // 53e3: new net/minecraft/client/animation/Keyframe
      // 53e6: dup
      // 53e7: ldc 1.75
      // 53e9: dconst_1
      // 53ea: dconst_1
      // 53eb: ldc2_w 2.2339999675750732
      // 53ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 53f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53f7: aastore
      // 53f8: dup
      // 53f9: bipush 41
      // 53fb: new net/minecraft/client/animation/Keyframe
      // 53fe: dup
      // 53ff: ldc 1.79
      // 5401: dconst_1
      // 5402: dconst_1
      // 5403: ldc2_w 2.2899999618530273
      // 5406: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5409: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 540c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 540f: aastore
      // 5410: dup
      // 5411: bipush 42
      // 5413: new net/minecraft/client/animation/Keyframe
      // 5416: dup
      // 5417: ldc 1.83
      // 5419: dconst_1
      // 541a: dconst_1
      // 541b: ldc2_w 2.302000045776367
      // 541e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5421: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5424: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5427: aastore
      // 5428: dup
      // 5429: bipush 43
      // 542b: new net/minecraft/client/animation/Keyframe
      // 542e: dup
      // 542f: ldc 1.88
      // 5431: dconst_1
      // 5432: dconst_1
      // 5433: ldc2_w 2.2320001125335693
      // 5436: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5439: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 543c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 543f: aastore
      // 5440: dup
      // 5441: bipush 44
      // 5443: new net/minecraft/client/animation/Keyframe
      // 5446: dup
      // 5447: ldc 1.92
      // 5449: dconst_1
      // 544a: dconst_1
      // 544b: ldc2_w 2.0929999351501465
      // 544e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5451: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5454: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5457: aastore
      // 5458: dup
      // 5459: bipush 45
      // 545b: new net/minecraft/client/animation/Keyframe
      // 545e: dup
      // 545f: ldc_w 1.96
      // 5462: dconst_1
      // 5463: dconst_1
      // 5464: ldc2_w 1.840999960899353
      // 5467: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 546a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 546d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5470: aastore
      // 5471: dup
      // 5472: bipush 46
      // 5474: new net/minecraft/client/animation/Keyframe
      // 5477: dup
      // 5478: fconst_2
      // 5479: dconst_1
      // 547a: dconst_1
      // 547b: ldc2_w 1.659000039100647
      // 547e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5481: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5484: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5487: aastore
      // 5488: dup
      // 5489: bipush 47
      // 548b: new net/minecraft/client/animation/Keyframe
      // 548e: dup
      // 548f: ldc_w 2.04
      // 5492: dconst_1
      // 5493: dconst_1
      // 5494: ldc2_w 1.7009999752044678
      // 5497: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 549a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 549d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54a0: aastore
      // 54a1: dup
      // 54a2: bipush 48
      // 54a4: new net/minecraft/client/animation/Keyframe
      // 54a7: dup
      // 54a8: ldc_w 2.08
      // 54ab: dconst_1
      // 54ac: dconst_1
      // 54ad: ldc2_w 1.6920000314712524
      // 54b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54b9: aastore
      // 54ba: dup
      // 54bb: bipush 49
      // 54bd: new net/minecraft/client/animation/Keyframe
      // 54c0: dup
      // 54c1: ldc_w 2.12
      // 54c4: dconst_1
      // 54c5: dconst_1
      // 54c6: ldc2_w 1.6699999570846558
      // 54c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54d2: aastore
      // 54d3: dup
      // 54d4: bipush 50
      // 54d6: new net/minecraft/client/animation/Keyframe
      // 54d9: dup
      // 54da: ldc 2.17
      // 54dc: dconst_1
      // 54dd: dconst_1
      // 54de: ldc2_w 1.6670000553131104
      // 54e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 54e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54ea: aastore
      // 54eb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 54ee: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 54f1: ldc_w "CTR_leg_left"
      // 54f4: new net/minecraft/client/animation/AnimationChannel
      // 54f7: dup
      // 54f8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 54fb: bipush 52
      // 54fd: anewarray 28
      // 5500: dup
      // 5501: bipush 0
      // 5502: new net/minecraft/client/animation/Keyframe
      // 5505: dup
      // 5506: fconst_0
      // 5507: ldc_w -177.066
      // 550a: ldc_w 52.262
      // 550d: ldc_w 182.934
      // 5510: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5513: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5516: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5519: aastore
      // 551a: dup
      // 551b: bipush 1
      // 551c: new net/minecraft/client/animation/Keyframe
      // 551f: dup
      // 5520: ldc 0.04
      // 5522: ldc_w -179.192
      // 5525: ldc_w 51.629
      // 5528: ldc_w 180.808
      // 552b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 552e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5531: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5534: aastore
      // 5535: dup
      // 5536: bipush 2
      // 5537: new net/minecraft/client/animation/Keyframe
      // 553a: dup
      // 553b: ldc 0.08
      // 553d: ldc_w -182.306
      // 5540: ldc_w 50.999
      // 5543: ldc_w 177.694
      // 5546: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5549: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 554c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 554f: aastore
      // 5550: dup
      // 5551: bipush 3
      // 5552: new net/minecraft/client/animation/Keyframe
      // 5555: dup
      // 5556: ldc 0.12
      // 5558: ldc_w -186.777
      // 555b: ldc_w 51.832
      // 555e: ldc_w 173.223
      // 5561: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5564: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5567: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 556a: aastore
      // 556b: dup
      // 556c: bipush 4
      // 556d: new net/minecraft/client/animation/Keyframe
      // 5570: dup
      // 5571: ldc 0.17
      // 5573: ldc_w -192.786
      // 5576: ldc_w 53.931
      // 5579: ldc_w 167.214
      // 557c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 557f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5582: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5585: aastore
      // 5586: dup
      // 5587: bipush 5
      // 5588: new net/minecraft/client/animation/Keyframe
      // 558b: dup
      // 558c: ldc 0.21
      // 558e: ldc_w -198.795
      // 5591: ldc_w 52.443
      // 5594: ldc_w 161.205
      // 5597: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 559a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 559d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55a0: aastore
      // 55a1: dup
      // 55a2: bipush 6
      // 55a4: new net/minecraft/client/animation/Keyframe
      // 55a7: dup
      // 55a8: ldc 0.25
      // 55aa: ldc_w -203.342
      // 55ad: ldc_w 46.06
      // 55b0: ldc_w 156.658
      // 55b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55bc: aastore
      // 55bd: dup
      // 55be: bipush 7
      // 55c0: new net/minecraft/client/animation/Keyframe
      // 55c3: dup
      // 55c4: ldc 0.29
      // 55c6: ldc_w -202.07
      // 55c9: ldc_w 37.824
      // 55cc: ldc_w 157.93
      // 55cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55d8: aastore
      // 55d9: dup
      // 55da: bipush 8
      // 55dc: new net/minecraft/client/animation/Keyframe
      // 55df: dup
      // 55e0: ldc 0.33
      // 55e2: ldc_w -195.244
      // 55e5: ldc_w 22.161
      // 55e8: ldc_w 164.756
      // 55eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55f4: aastore
      // 55f5: dup
      // 55f6: bipush 9
      // 55f8: new net/minecraft/client/animation/Keyframe
      // 55fb: dup
      // 55fc: ldc 0.38
      // 55fe: ldc_w -192.597
      // 5601: ldc_w 9.528
      // 5604: ldc_w 167.403
      // 5607: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 560a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 560d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5610: aastore
      // 5611: dup
      // 5612: bipush 10
      // 5614: new net/minecraft/client/animation/Keyframe
      // 5617: dup
      // 5618: ldc 0.42
      // 561a: ldc_w -192.165
      // 561d: ldc_w 6.503
      // 5620: ldc_w 167.835
      // 5623: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5626: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5629: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 562c: aastore
      // 562d: dup
      // 562e: bipush 11
      // 5630: new net/minecraft/client/animation/Keyframe
      // 5633: dup
      // 5634: ldc 0.46
      // 5636: ldc_w -191.651
      // 5639: ldc_w 3.989
      // 563c: ldc_w 168.349
      // 563f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5642: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5645: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5648: aastore
      // 5649: dup
      // 564a: bipush 12
      // 564c: new net/minecraft/client/animation/Keyframe
      // 564f: dup
      // 5650: ldc 0.5
      // 5652: ldc_w -191.17
      // 5655: ldc_w 2.154
      // 5658: ldc_w 168.83
      // 565b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 565e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5661: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5664: aastore
      // 5665: dup
      // 5666: bipush 13
      // 5668: new net/minecraft/client/animation/Keyframe
      // 566b: dup
      // 566c: ldc 0.54
      // 566e: ldc_w -190.607
      // 5671: ldc_w 1.177
      // 5674: ldc_w 169.393
      // 5677: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 567a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 567d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5680: aastore
      // 5681: dup
      // 5682: bipush 14
      // 5684: new net/minecraft/client/animation/Keyframe
      // 5687: dup
      // 5688: ldc 0.58
      // 568a: ldc_w -189.875
      // 568d: ldc 1.17
      // 568f: ldc_w 170.125
      // 5692: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5695: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5698: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 569b: aastore
      // 569c: dup
      // 569d: bipush 15
      // 569f: new net/minecraft/client/animation/Keyframe
      // 56a2: dup
      // 56a3: ldc 0.62
      // 56a5: ldc_w -188.906
      // 56a8: ldc_w 2.481
      // 56ab: ldc_w 171.094
      // 56ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56b7: aastore
      // 56b8: dup
      // 56b9: bipush 16
      // 56bb: new net/minecraft/client/animation/Keyframe
      // 56be: dup
      // 56bf: ldc 0.67
      // 56c1: ldc_w -187.652
      // 56c4: ldc_w 5.256
      // 56c7: ldc_w 172.348
      // 56ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56d3: aastore
      // 56d4: dup
      // 56d5: bipush 17
      // 56d7: new net/minecraft/client/animation/Keyframe
      // 56da: dup
      // 56db: ldc 0.71
      // 56dd: ldc_w -186.071
      // 56e0: ldc_w 9.241
      // 56e3: ldc_w 173.929
      // 56e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56ef: aastore
      // 56f0: dup
      // 56f1: bipush 18
      // 56f3: new net/minecraft/client/animation/Keyframe
      // 56f6: dup
      // 56f7: ldc 0.75
      // 56f9: ldc_w -184.187
      // 56fc: ldc_w 14.089
      // 56ff: ldc_w 175.813
      // 5702: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5705: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5708: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 570b: aastore
      // 570c: dup
      // 570d: bipush 19
      // 570f: new net/minecraft/client/animation/Keyframe
      // 5712: dup
      // 5713: ldc 0.79
      // 5715: ldc_w -182.084
      // 5718: ldc_w 19.518
      // 571b: ldc_w 177.916
      // 571e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5721: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5724: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5727: aastore
      // 5728: dup
      // 5729: bipush 20
      // 572b: new net/minecraft/client/animation/Keyframe
      // 572e: dup
      // 572f: ldc 0.83
      // 5731: ldc_w -179.787
      // 5734: ldc_w 25.251
      // 5737: ldc_w 180.213
      // 573a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 573d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5740: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5743: aastore
      // 5744: dup
      // 5745: bipush 21
      // 5747: new net/minecraft/client/animation/Keyframe
      // 574a: dup
      // 574b: ldc 0.88
      // 574d: ldc_w -177.313
      // 5750: ldc_w 30.923
      // 5753: ldc_w 182.687
      // 5756: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5759: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 575c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 575f: aastore
      // 5760: dup
      // 5761: bipush 22
      // 5763: new net/minecraft/client/animation/Keyframe
      // 5766: dup
      // 5767: ldc 0.92
      // 5769: ldc_w -174.706
      // 576c: ldc_w 36.072
      // 576f: ldc_w 185.294
      // 5772: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5775: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5778: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 577b: aastore
      // 577c: dup
      // 577d: bipush 23
      // 577f: new net/minecraft/client/animation/Keyframe
      // 5782: dup
      // 5783: ldc 0.96
      // 5785: ldc_w -172.065
      // 5788: ldc_w 40.155
      // 578b: ldc_w 187.935
      // 578e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5791: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5794: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5797: aastore
      // 5798: dup
      // 5799: bipush 24
      // 579b: new net/minecraft/client/animation/Keyframe
      // 579e: dup
      // 579f: fconst_1
      // 57a0: ldc_w -169.602
      // 57a3: ldc_w 42.595
      // 57a6: ldc_w 190.398
      // 57a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57b2: aastore
      // 57b3: dup
      // 57b4: bipush 25
      // 57b6: new net/minecraft/client/animation/Keyframe
      // 57b9: dup
      // 57ba: ldc 1.04
      // 57bc: ldc_w -167.688
      // 57bf: ldc_w 42.84
      // 57c2: ldc_w 192.312
      // 57c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57ce: aastore
      // 57cf: dup
      // 57d0: bipush 26
      // 57d2: new net/minecraft/client/animation/Keyframe
      // 57d5: dup
      // 57d6: ldc 1.08
      // 57d8: ldc_w -166.904
      // 57db: ldc_w 40.403
      // 57de: ldc_w 193.096
      // 57e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57ea: aastore
      // 57eb: dup
      // 57ec: bipush 27
      // 57ee: new net/minecraft/client/animation/Keyframe
      // 57f1: dup
      // 57f2: ldc 1.12
      // 57f4: ldc_w -168.292
      // 57f7: ldc_w 35.691
      // 57fa: ldc_w 191.708
      // 57fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5800: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5803: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5806: aastore
      // 5807: dup
      // 5808: bipush 28
      // 580a: new net/minecraft/client/animation/Keyframe
      // 580d: dup
      // 580e: ldc 1.17
      // 5810: ldc_w -171.802
      // 5813: ldc_w 33.529
      // 5816: ldc_w 188.197
      // 5819: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 581c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 581f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5822: aastore
      // 5823: dup
      // 5824: bipush 29
      // 5826: new net/minecraft/client/animation/Keyframe
      // 5829: dup
      // 582a: ldc 1.21
      // 582c: ldc_w -175.24
      // 582f: ldc_w 34.934
      // 5832: ldc_w 184.76
      // 5835: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5838: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 583b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 583e: aastore
      // 583f: dup
      // 5840: bipush 30
      // 5842: new net/minecraft/client/animation/Keyframe
      // 5845: dup
      // 5846: ldc 1.25
      // 5848: ldc_w -141.42
      // 584b: ldc_w 39.901
      // 584e: ldc_w 151.528
      // 5851: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5854: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5857: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 585a: aastore
      // 585b: dup
      // 585c: bipush 31
      // 585e: new net/minecraft/client/animation/Keyframe
      // 5861: dup
      // 5862: ldc 1.29
      // 5864: ldc_w -116.033
      // 5867: ldc_w 54.23
      // 586a: ldc_w 135.404
      // 586d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5870: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5873: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5876: aastore
      // 5877: dup
      // 5878: bipush 32
      // 587a: new net/minecraft/client/animation/Keyframe
      // 587d: dup
      // 587e: ldc 1.33
      // 5880: ldc_w -108.749
      // 5883: ldc_w 62.977
      // 5886: ldc_w 132.879
      // 5889: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 588c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 588f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5892: aastore
      // 5893: dup
      // 5894: bipush 33
      // 5896: new net/minecraft/client/animation/Keyframe
      // 5899: dup
      // 589a: ldc 1.38
      // 589c: ldc_w -106.698
      // 589f: ldc_w 60.968
      // 58a2: ldc_w 127.523
      // 58a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58ae: aastore
      // 58af: dup
      // 58b0: bipush 34
      // 58b2: new net/minecraft/client/animation/Keyframe
      // 58b5: dup
      // 58b6: ldc 1.42
      // 58b8: ldc_w -106.093
      // 58bb: ldc_w 59.743
      // 58be: ldc_w 124.346
      // 58c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58ca: aastore
      // 58cb: dup
      // 58cc: bipush 35
      // 58ce: new net/minecraft/client/animation/Keyframe
      // 58d1: dup
      // 58d2: ldc 1.46
      // 58d4: ldc_w -106.776
      // 58d7: ldc_w 59.091
      // 58da: ldc_w 123.087
      // 58dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58e6: aastore
      // 58e7: dup
      // 58e8: bipush 36
      // 58ea: new net/minecraft/client/animation/Keyframe
      // 58ed: dup
      // 58ee: ldc 1.5
      // 58f0: ldc_w -108.521
      // 58f3: ldc_w 58.814
      // 58f6: ldc_w 123.355
      // 58f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5902: aastore
      // 5903: dup
      // 5904: bipush 37
      // 5906: new net/minecraft/client/animation/Keyframe
      // 5909: dup
      // 590a: ldc 1.54
      // 590c: ldc_w -110.957
      // 590f: ldc_w 58.667
      // 5912: ldc_w 124.569
      // 5915: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5918: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 591b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 591e: aastore
      // 591f: dup
      // 5920: bipush 38
      // 5922: new net/minecraft/client/animation/Keyframe
      // 5925: dup
      // 5926: ldc 1.58
      // 5928: ldc_w -114.139
      // 592b: ldc_w 57.679
      // 592e: ldc_w 126.611
      // 5931: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5934: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5937: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 593a: aastore
      // 593b: dup
      // 593c: bipush 39
      // 593e: new net/minecraft/client/animation/Keyframe
      // 5941: dup
      // 5942: ldc 1.62
      // 5944: ldc_w -119.535
      // 5947: ldc_w 55.798
      // 594a: ldc_w 130.885
      // 594d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5950: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5953: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5956: aastore
      // 5957: dup
      // 5958: bipush 40
      // 595a: new net/minecraft/client/animation/Keyframe
      // 595d: dup
      // 595e: ldc 1.67
      // 5960: ldc_w -128.592
      // 5963: ldc_w 54.261
      // 5966: ldc_w 138.853
      // 5969: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 596c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 596f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5972: aastore
      // 5973: dup
      // 5974: bipush 41
      // 5976: new net/minecraft/client/animation/Keyframe
      // 5979: dup
      // 597a: ldc 1.71
      // 597c: ldc_w -141.177
      // 597f: ldc_w 54.502
      // 5982: ldc_w 150.589
      // 5985: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5988: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 598b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 598e: aastore
      // 598f: dup
      // 5990: bipush 42
      // 5992: new net/minecraft/client/animation/Keyframe
      // 5995: dup
      // 5996: ldc 1.75
      // 5998: ldc_w -154.1
      // 599b: ldc_w 57.342
      // 599e: ldc_w 163.196
      // 59a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59aa: aastore
      // 59ab: dup
      // 59ac: bipush 43
      // 59ae: new net/minecraft/client/animation/Keyframe
      // 59b1: dup
      // 59b2: ldc 1.79
      // 59b4: ldc_w -163.87
      // 59b7: ldc_w 61.668
      // 59ba: ldc_w 173.231
      // 59bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59c6: aastore
      // 59c7: dup
      // 59c8: bipush 44
      // 59ca: new net/minecraft/client/animation/Keyframe
      // 59cd: dup
      // 59ce: ldc 1.83
      // 59d0: ldc_w -170.059
      // 59d3: ldc_w 66.04
      // 59d6: ldc_w 180.096
      // 59d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59e2: aastore
      // 59e3: dup
      // 59e4: bipush 45
      // 59e6: new net/minecraft/client/animation/Keyframe
      // 59e9: dup
      // 59ea: ldc 1.88
      // 59ec: ldc_w -174.882
      // 59ef: ldc_w 59.829
      // 59f2: ldc_w 182.038
      // 59f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59fe: aastore
      // 59ff: dup
      // 5a00: bipush 46
      // 5a02: new net/minecraft/client/animation/Keyframe
      // 5a05: dup
      // 5a06: ldc 1.92
      // 5a08: ldc_w -177.066
      // 5a0b: ldc_w 52.262
      // 5a0e: ldc_w 182.934
      // 5a11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a1a: aastore
      // 5a1b: dup
      // 5a1c: bipush 47
      // 5a1e: new net/minecraft/client/animation/Keyframe
      // 5a21: dup
      // 5a22: ldc_w 1.96
      // 5a25: ldc_w -176.349
      // 5a28: ldc_w 52.813
      // 5a2b: ldc_w 183.651
      // 5a2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a37: aastore
      // 5a38: dup
      // 5a39: bipush 48
      // 5a3b: new net/minecraft/client/animation/Keyframe
      // 5a3e: dup
      // 5a3f: fconst_2
      // 5a40: ldc_w -176.47
      // 5a43: ldc_w 52.645
      // 5a46: ldc_w 183.53
      // 5a49: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a4c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a4f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a52: aastore
      // 5a53: dup
      // 5a54: bipush 49
      // 5a56: new net/minecraft/client/animation/Keyframe
      // 5a59: dup
      // 5a5a: ldc_w 2.04
      // 5a5d: ldc_w -177.066
      // 5a60: ldc_w 52.262
      // 5a63: ldc_w 182.934
      // 5a66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a6f: aastore
      // 5a70: dup
      // 5a71: bipush 50
      // 5a73: new net/minecraft/client/animation/Keyframe
      // 5a76: dup
      // 5a77: ldc_w 2.08
      // 5a7a: ldc_w -177.362
      // 5a7d: ldc_w 52.11
      // 5a80: ldc_w 182.638
      // 5a83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a8c: aastore
      // 5a8d: dup
      // 5a8e: bipush 51
      // 5a90: new net/minecraft/client/animation/Keyframe
      // 5a93: dup
      // 5a94: ldc 2.17
      // 5a96: ldc_w -177.066
      // 5a99: ldc_w 52.262
      // 5a9c: ldc_w 182.934
      // 5a9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5aa2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aa5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aa8: aastore
      // 5aa9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5aac: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5aaf: ldc_w "CTR_leg_left"
      // 5ab2: new net/minecraft/client/animation/AnimationChannel
      // 5ab5: dup
      // 5ab6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ab9: bipush 52
      // 5abb: anewarray 28
      // 5abe: dup
      // 5abf: bipush 0
      // 5ac0: new net/minecraft/client/animation/Keyframe
      // 5ac3: dup
      // 5ac4: fconst_0
      // 5ac5: ldc_w 0.878
      // 5ac8: ldc_w -1.018
      // 5acb: ldc_w -0.451
      // 5ace: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ad1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ad4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ad7: aastore
      // 5ad8: dup
      // 5ad9: bipush 1
      // 5ada: new net/minecraft/client/animation/Keyframe
      // 5add: dup
      // 5ade: ldc 0.04
      // 5ae0: ldc_w 0.285
      // 5ae3: ldc_w -0.388
      // 5ae6: ldc_w -0.207
      // 5ae9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5aec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5aef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5af2: aastore
      // 5af3: dup
      // 5af4: bipush 2
      // 5af5: new net/minecraft/client/animation/Keyframe
      // 5af8: dup
      // 5af9: ldc 0.08
      // 5afb: ldc_w -0.667
      // 5afe: ldc_w 0.096
      // 5b01: ldc_w -0.005
      // 5b04: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b0d: aastore
      // 5b0e: dup
      // 5b0f: bipush 3
      // 5b10: new net/minecraft/client/animation/Keyframe
      // 5b13: dup
      // 5b14: ldc 0.12
      // 5b16: ldc_w -1.988
      // 5b19: ldc_w -0.026
      // 5b1c: ldc_w 0.236
      // 5b1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b28: aastore
      // 5b29: dup
      // 5b2a: bipush 4
      // 5b2b: new net/minecraft/client/animation/Keyframe
      // 5b2e: dup
      // 5b2f: ldc 0.17
      // 5b31: ldc_w -3.188
      // 5b34: ldc_w -1.368
      // 5b37: ldc_w 0.189
      // 5b3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b43: aastore
      // 5b44: dup
      // 5b45: bipush 5
      // 5b46: new net/minecraft/client/animation/Keyframe
      // 5b49: dup
      // 5b4a: ldc 0.21
      // 5b4c: ldc_w -3.37
      // 5b4f: ldc_w -3.26
      // 5b52: ldc_w -0.753
      // 5b55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b5e: aastore
      // 5b5f: dup
      // 5b60: bipush 6
      // 5b62: new net/minecraft/client/animation/Keyframe
      // 5b65: dup
      // 5b66: ldc 0.25
      // 5b68: ldc_w -2.579
      // 5b6b: ldc_w -4.768
      // 5b6e: ldc_w -2.04
      // 5b71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b7a: aastore
      // 5b7b: dup
      // 5b7c: bipush 7
      // 5b7e: new net/minecraft/client/animation/Keyframe
      // 5b81: dup
      // 5b82: ldc 0.29
      // 5b84: ldc_w -4.95
      // 5b87: ldc_w -2.763
      // 5b8a: ldc_w -2.071
      // 5b8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5b90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b96: aastore
      // 5b97: dup
      // 5b98: bipush 8
      // 5b9a: new net/minecraft/client/animation/Keyframe
      // 5b9d: dup
      // 5b9e: ldc 0.33
      // 5ba0: ldc_w -6.212
      // 5ba3: ldc_w 1.568
      // 5ba6: ldc_w -4.223
      // 5ba9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5baf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bb2: aastore
      // 5bb3: dup
      // 5bb4: bipush 9
      // 5bb6: new net/minecraft/client/animation/Keyframe
      // 5bb9: dup
      // 5bba: ldc 0.38
      // 5bbc: ldc_w -2.844
      // 5bbf: ldc_w -1.42
      // 5bc2: ldc_w -5.513
      // 5bc5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5bc8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bcb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bce: aastore
      // 5bcf: dup
      // 5bd0: bipush 10
      // 5bd2: new net/minecraft/client/animation/Keyframe
      // 5bd5: dup
      // 5bd6: ldc 0.42
      // 5bd8: ldc_w -2.556
      // 5bdb: ldc_w -1.693
      // 5bde: ldc_w -5.781
      // 5be1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5be4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5be7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bea: aastore
      // 5beb: dup
      // 5bec: bipush 11
      // 5bee: new net/minecraft/client/animation/Keyframe
      // 5bf1: dup
      // 5bf2: ldc 0.46
      // 5bf4: ldc_w -2.246
      // 5bf7: ldc_w -1.975
      // 5bfa: ldc_w -5.951
      // 5bfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c06: aastore
      // 5c07: dup
      // 5c08: bipush 12
      // 5c0a: new net/minecraft/client/animation/Keyframe
      // 5c0d: dup
      // 5c0e: ldc 0.5
      // 5c10: ldc_w -1.988
      // 5c13: ldc_w -2.202
      // 5c16: ldc_w -6.044
      // 5c19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c22: aastore
      // 5c23: dup
      // 5c24: bipush 13
      // 5c26: new net/minecraft/client/animation/Keyframe
      // 5c29: dup
      // 5c2a: ldc 0.54
      // 5c2c: ldc_w -1.754
      // 5c2f: ldc_w -2.359
      // 5c32: ldc_w -6.055
      // 5c35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c3e: aastore
      // 5c3f: dup
      // 5c40: bipush 14
      // 5c42: new net/minecraft/client/animation/Keyframe
      // 5c45: dup
      // 5c46: ldc 0.58
      // 5c48: ldc_w -1.52
      // 5c4b: ldc_w -2.445
      // 5c4e: ldc_w -5.979
      // 5c51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c5a: aastore
      // 5c5b: dup
      // 5c5c: bipush 15
      // 5c5e: new net/minecraft/client/animation/Keyframe
      // 5c61: dup
      // 5c62: ldc 0.62
      // 5c64: ldc_w -1.28
      // 5c67: ldc_w -2.44
      // 5c6a: ldc_w -5.797
      // 5c6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c76: aastore
      // 5c77: dup
      // 5c78: bipush 16
      // 5c7a: new net/minecraft/client/animation/Keyframe
      // 5c7d: dup
      // 5c7e: ldc 0.67
      // 5c80: ldc_w -1.018
      // 5c83: ldc_w -2.354
      // 5c86: ldc_w -5.498
      // 5c89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5c8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c92: aastore
      // 5c93: dup
      // 5c94: bipush 17
      // 5c96: new net/minecraft/client/animation/Keyframe
      // 5c99: dup
      // 5c9a: ldc 0.71
      // 5c9c: ldc_w -0.698
      // 5c9f: ldc_w -2.256
      // 5ca2: ldc_w -5.09
      // 5ca5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ca8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cae: aastore
      // 5caf: dup
      // 5cb0: bipush 18
      // 5cb2: new net/minecraft/client/animation/Keyframe
      // 5cb5: dup
      // 5cb6: ldc 0.75
      // 5cb8: ldc_w -0.312
      // 5cbb: ldc_w -2.213
      // 5cbe: ldc_w -4.605
      // 5cc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cca: aastore
      // 5ccb: dup
      // 5ccc: bipush 19
      // 5cce: new net/minecraft/client/animation/Keyframe
      // 5cd1: dup
      // 5cd2: ldc 0.79
      // 5cd4: ldc_w 0.112
      // 5cd7: ldc_w -2.254
      // 5cda: ldc_w -4.086
      // 5cdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ce0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ce3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ce6: aastore
      // 5ce7: dup
      // 5ce8: bipush 20
      // 5cea: new net/minecraft/client/animation/Keyframe
      // 5ced: dup
      // 5cee: ldc 0.83
      // 5cf0: ldc_w 0.559
      // 5cf3: ldc_w -2.411
      // 5cf6: ldc_w -3.575
      // 5cf9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5cfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d02: aastore
      // 5d03: dup
      // 5d04: bipush 21
      // 5d06: new net/minecraft/client/animation/Keyframe
      // 5d09: dup
      // 5d0a: ldc 0.88
      // 5d0c: ldc_w 1.006
      // 5d0f: ldc_w -2.712
      // 5d12: ldc_w -3.126
      // 5d15: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d1e: aastore
      // 5d1f: dup
      // 5d20: bipush 22
      // 5d22: new net/minecraft/client/animation/Keyframe
      // 5d25: dup
      // 5d26: ldc 0.92
      // 5d28: ldc_w 1.414
      // 5d2b: ldc_w -3.178
      // 5d2e: ldc_w -2.802
      // 5d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d3a: aastore
      // 5d3b: dup
      // 5d3c: bipush 23
      // 5d3e: new net/minecraft/client/animation/Keyframe
      // 5d41: dup
      // 5d42: ldc 0.96
      // 5d44: ldc_w 1.73
      // 5d47: ldc_w -3.818
      // 5d4a: ldc_w -2.663
      // 5d4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d56: aastore
      // 5d57: dup
      // 5d58: bipush 24
      // 5d5a: new net/minecraft/client/animation/Keyframe
      // 5d5d: dup
      // 5d5e: fconst_1
      // 5d5f: ldc 1.88
      // 5d61: ldc_w -4.646
      // 5d64: ldc_w -2.751
      // 5d67: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d70: aastore
      // 5d71: dup
      // 5d72: bipush 25
      // 5d74: new net/minecraft/client/animation/Keyframe
      // 5d77: dup
      // 5d78: ldc 1.04
      // 5d7a: ldc_w 1.783
      // 5d7d: ldc_w -5.734
      // 5d80: ldc_w -3.071
      // 5d83: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5d86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d8c: aastore
      // 5d8d: dup
      // 5d8e: bipush 26
      // 5d90: new net/minecraft/client/animation/Keyframe
      // 5d93: dup
      // 5d94: ldc 1.08
      // 5d96: ldc_w 1.409
      // 5d99: ldc_w -7.188
      // 5d9c: ldc_w -3.527
      // 5d9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5da2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5da5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5da8: aastore
      // 5da9: dup
      // 5daa: bipush 27
      // 5dac: new net/minecraft/client/animation/Keyframe
      // 5daf: dup
      // 5db0: ldc 1.12
      // 5db2: ldc_w 0.543
      // 5db5: ldc_w -10.32
      // 5db8: ldc_w -3.447
      // 5dbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc4: aastore
      // 5dc5: dup
      // 5dc6: bipush 28
      // 5dc8: new net/minecraft/client/animation/Keyframe
      // 5dcb: dup
      // 5dcc: ldc 1.17
      // 5dce: ldc_w -1.043
      // 5dd1: ldc_w -14.322
      // 5dd4: ldc_w -3.051
      // 5dd7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5dda: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ddd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5de0: aastore
      // 5de1: dup
      // 5de2: bipush 29
      // 5de4: new net/minecraft/client/animation/Keyframe
      // 5de7: dup
      // 5de8: ldc 1.21
      // 5dea: ldc_w -2.731
      // 5ded: ldc_w -16.303
      // 5df0: ldc_w -2.741
      // 5df3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5df6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5df9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dfc: aastore
      // 5dfd: dup
      // 5dfe: bipush 30
      // 5e00: new net/minecraft/client/animation/Keyframe
      // 5e03: dup
      // 5e04: ldc 1.25
      // 5e06: ldc_w -3.26
      // 5e09: ldc_w -13.44
      // 5e0c: ldc_w -5.004
      // 5e0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e18: aastore
      // 5e19: dup
      // 5e1a: bipush 31
      // 5e1c: new net/minecraft/client/animation/Keyframe
      // 5e1f: dup
      // 5e20: ldc 1.29
      // 5e22: ldc_w -1.575
      // 5e25: ldc_w -7.805
      // 5e28: ldc_w -8.915
      // 5e2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e34: aastore
      // 5e35: dup
      // 5e36: bipush 32
      // 5e38: new net/minecraft/client/animation/Keyframe
      // 5e3b: dup
      // 5e3c: ldc 1.33
      // 5e3e: ldc_w -0.065
      // 5e41: ldc_w -5.446
      // 5e44: ldc_w -11.276
      // 5e47: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e50: aastore
      // 5e51: dup
      // 5e52: bipush 33
      // 5e54: new net/minecraft/client/animation/Keyframe
      // 5e57: dup
      // 5e58: ldc 1.38
      // 5e5a: ldc_w -0.63
      // 5e5d: ldc_w -4.915
      // 5e60: ldc_w -11.378
      // 5e63: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e6c: aastore
      // 5e6d: dup
      // 5e6e: bipush 34
      // 5e70: new net/minecraft/client/animation/Keyframe
      // 5e73: dup
      // 5e74: ldc 1.42
      // 5e76: ldc_w -0.833
      // 5e79: ldc_w -4.417
      // 5e7c: ldc_w -10.957
      // 5e7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e88: aastore
      // 5e89: dup
      // 5e8a: bipush 35
      // 5e8c: new net/minecraft/client/animation/Keyframe
      // 5e8f: dup
      // 5e90: ldc 1.46
      // 5e92: ldc_w -0.778
      // 5e95: ldc_w -3.887
      // 5e98: ldc_w -10.194
      // 5e9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5e9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ea1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ea4: aastore
      // 5ea5: dup
      // 5ea6: bipush 36
      // 5ea8: new net/minecraft/client/animation/Keyframe
      // 5eab: dup
      // 5eac: ldc 1.5
      // 5eae: ldc_w -0.575
      // 5eb1: ldc_w -3.304
      // 5eb4: ldc_w -9.259
      // 5eb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5eba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ebd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ec0: aastore
      // 5ec1: dup
      // 5ec2: bipush 37
      // 5ec4: new net/minecraft/client/animation/Keyframe
      // 5ec7: dup
      // 5ec8: ldc 1.54
      // 5eca: ldc_w -0.35
      // 5ecd: ldc_w -2.653
      // 5ed0: ldc_w -8.356
      // 5ed3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ed6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ed9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5edc: aastore
      // 5edd: dup
      // 5ede: bipush 38
      // 5ee0: new net/minecraft/client/animation/Keyframe
      // 5ee3: dup
      // 5ee4: ldc 1.58
      // 5ee6: ldc_w -0.267
      // 5ee9: ldc_w -1.896
      // 5eec: ldc_w -7.724
      // 5eef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5ef2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ef5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef8: aastore
      // 5ef9: dup
      // 5efa: bipush 39
      // 5efc: new net/minecraft/client/animation/Keyframe
      // 5eff: dup
      // 5f00: ldc 1.62
      // 5f02: ldc_w -0.307
      // 5f05: ldc_w -1.125
      // 5f08: ldc_w -7.15
      // 5f0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f14: aastore
      // 5f15: dup
      // 5f16: bipush 40
      // 5f18: new net/minecraft/client/animation/Keyframe
      // 5f1b: dup
      // 5f1c: ldc 1.67
      // 5f1e: ldc_w -0.338
      // 5f21: ldc_w -0.519
      // 5f24: ldc_w -6.237
      // 5f27: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f30: aastore
      // 5f31: dup
      // 5f32: bipush 41
      // 5f34: new net/minecraft/client/animation/Keyframe
      // 5f37: dup
      // 5f38: ldc 1.71
      // 5f3a: ldc_w -0.23
      // 5f3d: ldc_w -0.28
      // 5f40: ldc_w -4.743
      // 5f43: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f4c: aastore
      // 5f4d: dup
      // 5f4e: bipush 42
      // 5f50: new net/minecraft/client/animation/Keyframe
      // 5f53: dup
      // 5f54: ldc 1.75
      // 5f56: ldc_w 0.091
      // 5f59: ldc_w -0.554
      // 5f5c: ldc_w -2.787
      // 5f5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f68: aastore
      // 5f69: dup
      // 5f6a: bipush 43
      // 5f6c: new net/minecraft/client/animation/Keyframe
      // 5f6f: dup
      // 5f70: ldc 1.79
      // 5f72: ldc_w 0.53
      // 5f75: ldc_w -1.15
      // 5f78: ldc_w -1.004
      // 5f7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f84: aastore
      // 5f85: dup
      // 5f86: bipush 44
      // 5f88: new net/minecraft/client/animation/Keyframe
      // 5f8b: dup
      // 5f8c: ldc 1.83
      // 5f8e: ldc_w 0.978
      // 5f91: ldc_w -1.796
      // 5f94: ldc_w 0.249
      // 5f97: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5f9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fa0: aastore
      // 5fa1: dup
      // 5fa2: bipush 45
      // 5fa4: new net/minecraft/client/animation/Keyframe
      // 5fa7: dup
      // 5fa8: ldc 1.88
      // 5faa: ldc_w 0.876
      // 5fad: ldc_w -1.097
      // 5fb0: ldc_w 0.305
      // 5fb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fbc: aastore
      // 5fbd: dup
      // 5fbe: bipush 46
      // 5fc0: new net/minecraft/client/animation/Keyframe
      // 5fc3: dup
      // 5fc4: ldc 1.92
      // 5fc6: ldc_w 0.878
      // 5fc9: ldc_w -1.018
      // 5fcc: ldc_w -0.451
      // 5fcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fd8: aastore
      // 5fd9: dup
      // 5fda: bipush 47
      // 5fdc: new net/minecraft/client/animation/Keyframe
      // 5fdf: dup
      // 5fe0: ldc_w 1.96
      // 5fe3: ldc_w 1.057
      // 5fe6: ldc_w -1.269
      // 5fe9: ldc_w -0.509
      // 5fec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5fef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ff2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ff5: aastore
      // 5ff6: dup
      // 5ff7: bipush 48
      // 5ff9: new net/minecraft/client/animation/Keyframe
      // 5ffc: dup
      // 5ffd: fconst_2
      // 5ffe: ldc_w 1.028
      // 6001: ldc_w -1.229
      // 6004: ldc_w -0.51
      // 6007: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 600a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 600d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6010: aastore
      // 6011: dup
      // 6012: bipush 49
      // 6014: new net/minecraft/client/animation/Keyframe
      // 6017: dup
      // 6018: ldc_w 2.04
      // 601b: ldc_w 0.878
      // 601e: ldc_w -1.018
      // 6021: ldc_w -0.451
      // 6024: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6027: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 602a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 602d: aastore
      // 602e: dup
      // 602f: bipush 50
      // 6031: new net/minecraft/client/animation/Keyframe
      // 6034: dup
      // 6035: ldc_w 2.08
      // 6038: ldc_w 0.8
      // 603b: ldc_w -0.911
      // 603e: ldc_w -0.416
      // 6041: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6044: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6047: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 604a: aastore
      // 604b: dup
      // 604c: bipush 51
      // 604e: new net/minecraft/client/animation/Keyframe
      // 6051: dup
      // 6052: ldc 2.17
      // 6054: ldc_w 0.878
      // 6057: ldc_w -1.018
      // 605a: ldc_w -0.451
      // 605d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6060: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6063: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6066: aastore
      // 6067: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 606a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 606d: ldc_w "CTR_leg_right"
      // 6070: new net/minecraft/client/animation/AnimationChannel
      // 6073: dup
      // 6074: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6077: bipush 53
      // 6079: anewarray 28
      // 607c: dup
      // 607d: bipush 0
      // 607e: new net/minecraft/client/animation/Keyframe
      // 6081: dup
      // 6082: fconst_0
      // 6083: ldc_w -2.789
      // 6086: ldc_w 0.901
      // 6089: ldc_w 2.789
      // 608c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 608f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6092: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6095: aastore
      // 6096: dup
      // 6097: bipush 1
      // 6098: new net/minecraft/client/animation/Keyframe
      // 609b: dup
      // 609c: ldc 0.04
      // 609e: ldc_w 14.67
      // 60a1: ldc_w 0.603
      // 60a4: ldc_w 18.132
      // 60a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60b0: aastore
      // 60b1: dup
      // 60b2: bipush 2
      // 60b3: new net/minecraft/client/animation/Keyframe
      // 60b6: dup
      // 60b7: ldc 0.08
      // 60b9: ldc_w 27.637
      // 60bc: ldc_w -3.543
      // 60bf: ldc_w 27.925
      // 60c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60cb: aastore
      // 60cc: dup
      // 60cd: bipush 3
      // 60ce: new net/minecraft/client/animation/Keyframe
      // 60d1: dup
      // 60d2: ldc 0.12
      // 60d4: ldc_w 22.748
      // 60d7: ldc_w -8.907
      // 60da: ldc_w 22.525
      // 60dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60e6: aastore
      // 60e7: dup
      // 60e8: bipush 4
      // 60e9: new net/minecraft/client/animation/Keyframe
      // 60ec: dup
      // 60ed: ldc 0.17
      // 60ef: ldc_w 20.252
      // 60f2: ldc_w -9.368
      // 60f5: ldc_w 24.038
      // 60f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6101: aastore
      // 6102: dup
      // 6103: bipush 5
      // 6104: new net/minecraft/client/animation/Keyframe
      // 6107: dup
      // 6108: ldc 0.21
      // 610a: ldc_w 36.75
      // 610d: ldc_w 2.486
      // 6110: ldc_w 39.176
      // 6113: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6116: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6119: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 611c: aastore
      // 611d: dup
      // 611e: bipush 6
      // 6120: new net/minecraft/client/animation/Keyframe
      // 6123: dup
      // 6124: ldc 0.25
      // 6126: ldc_w 60.408
      // 6129: ldc_w 7.523
      // 612c: ldc_w 58.053
      // 612f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6132: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6135: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6138: aastore
      // 6139: dup
      // 613a: bipush 7
      // 613c: new net/minecraft/client/animation/Keyframe
      // 613f: dup
      // 6140: ldc 0.29
      // 6142: ldc_w 66.573
      // 6145: ldc_w 7.944
      // 6148: ldc_w 58.973
      // 614b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 614e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6151: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6154: aastore
      // 6155: dup
      // 6156: bipush 8
      // 6158: new net/minecraft/client/animation/Keyframe
      // 615b: dup
      // 615c: ldc 0.33
      // 615e: ldc_w 79.73
      // 6161: ldc_w 8.977
      // 6164: ldc_w 69.539
      // 6167: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 616a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 616d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6170: aastore
      // 6171: dup
      // 6172: bipush 9
      // 6174: new net/minecraft/client/animation/Keyframe
      // 6177: dup
      // 6178: ldc 0.38
      // 617a: ldc_w 100.238
      // 617d: ldc_w 9.558
      // 6180: ldc_w 89.384
      // 6183: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6186: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6189: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 618c: aastore
      // 618d: dup
      // 618e: bipush 10
      // 6190: new net/minecraft/client/animation/Keyframe
      // 6193: dup
      // 6194: ldc 0.42
      // 6196: ldc_w 119.101
      // 6199: ldc_w 9.013
      // 619c: ldc_w 108.582
      // 619f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61a8: aastore
      // 61a9: dup
      // 61aa: bipush 11
      // 61ac: new net/minecraft/client/animation/Keyframe
      // 61af: dup
      // 61b0: ldc 0.46
      // 61b2: ldc_w 130.019
      // 61b5: ldc_w 8.156
      // 61b8: ldc_w 120.015
      // 61bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61c4: aastore
      // 61c5: dup
      // 61c6: bipush 12
      // 61c8: new net/minecraft/client/animation/Keyframe
      // 61cb: dup
      // 61cc: ldc 0.5
      // 61ce: ldc_w 135.815
      // 61d1: ldc_w 7.547
      // 61d4: ldc_w 126.36
      // 61d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61e0: aastore
      // 61e1: dup
      // 61e2: bipush 13
      // 61e4: new net/minecraft/client/animation/Keyframe
      // 61e7: dup
      // 61e8: ldc 0.54
      // 61ea: ldc_w 138.425
      // 61ed: ldc_w 7.341
      // 61f0: ldc_w 129.748
      // 61f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61fc: aastore
      // 61fd: dup
      // 61fe: bipush 14
      // 6200: new net/minecraft/client/animation/Keyframe
      // 6203: dup
      // 6204: ldc 0.58
      // 6206: ldc_w 138.828
      // 6209: ldc_w 7.535
      // 620c: ldc_w 131.32
      // 620f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6212: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6215: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6218: aastore
      // 6219: dup
      // 621a: bipush 15
      // 621c: new net/minecraft/client/animation/Keyframe
      // 621f: dup
      // 6220: ldc 0.62
      // 6222: ldc_w 136.165
      // 6225: ldc_w 8.291
      // 6228: ldc_w 130.391
      // 622b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 622e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6231: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6234: aastore
      // 6235: dup
      // 6236: bipush 16
      // 6238: new net/minecraft/client/animation/Keyframe
      // 623b: dup
      // 623c: ldc 0.67
      // 623e: ldc_w 129.882
      // 6241: ldc_w 9.506
      // 6244: ldc_w 126.561
      // 6247: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 624a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 624d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6250: aastore
      // 6251: dup
      // 6252: bipush 17
      // 6254: new net/minecraft/client/animation/Keyframe
      // 6257: dup
      // 6258: ldc 0.71
      // 625a: ldc_w 121.637
      // 625d: ldc_w 10.724
      // 6260: ldc_w 121.609
      // 6263: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6266: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6269: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 626c: aastore
      // 626d: dup
      // 626e: bipush 18
      // 6270: new net/minecraft/client/animation/Keyframe
      // 6273: dup
      // 6274: ldc 0.75
      // 6276: ldc_w 112.989
      // 6279: ldc_w 11.778
      // 627c: ldc_w 117.045
      // 627f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6282: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6285: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6288: aastore
      // 6289: dup
      // 628a: bipush 19
      // 628c: new net/minecraft/client/animation/Keyframe
      // 628f: dup
      // 6290: ldc 0.79
      // 6292: ldc_w 123.999
      // 6295: ldc_w 16.203
      // 6298: ldc_w 132.768
      // 629b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 629e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62a4: aastore
      // 62a5: dup
      // 62a6: bipush 20
      // 62a8: new net/minecraft/client/animation/Keyframe
      // 62ab: dup
      // 62ac: ldc 0.83
      // 62ae: ldc_w 81.798
      // 62b1: ldc_w 27.023
      // 62b4: ldc_w 95.673
      // 62b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62c0: aastore
      // 62c1: dup
      // 62c2: bipush 21
      // 62c4: new net/minecraft/client/animation/Keyframe
      // 62c7: dup
      // 62c8: ldc 0.88
      // 62ca: ldc_w 84.508
      // 62cd: ldc_w 40.471
      // 62d0: ldc_w 103.717
      // 62d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62dc: aastore
      // 62dd: dup
      // 62de: bipush 22
      // 62e0: new net/minecraft/client/animation/Keyframe
      // 62e3: dup
      // 62e4: ldc 0.92
      // 62e6: ldc_w 81.916
      // 62e9: ldc_w 54.247
      // 62ec: ldc_w 106.557
      // 62ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62f8: aastore
      // 62f9: dup
      // 62fa: bipush 23
      // 62fc: new net/minecraft/client/animation/Keyframe
      // 62ff: dup
      // 6300: ldc 0.96
      // 6302: ldc_w 77.287
      // 6305: ldc_w 64.764
      // 6308: ldc_w 106.864
      // 630b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 630e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6311: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6314: aastore
      // 6315: dup
      // 6316: bipush 24
      // 6318: new net/minecraft/client/animation/Keyframe
      // 631b: dup
      // 631c: fconst_1
      // 631d: ldc_w 70.374
      // 6320: ldc_w 68.196
      // 6323: ldc_w 102.553
      // 6326: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6329: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 632c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 632f: aastore
      // 6330: dup
      // 6331: bipush 25
      // 6333: new net/minecraft/client/animation/Keyframe
      // 6336: dup
      // 6337: ldc 1.04
      // 6339: ldc_w 62.031
      // 633c: ldc_w 66.969
      // 633f: ldc_w 92.517
      // 6342: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6345: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6348: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 634b: aastore
      // 634c: dup
      // 634d: bipush 26
      // 634f: new net/minecraft/client/animation/Keyframe
      // 6352: dup
      // 6353: ldc 1.08
      // 6355: ldc_w 54.824
      // 6358: ldc_w 65.942
      // 635b: ldc_w 79.274
      // 635e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6361: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6364: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6367: aastore
      // 6368: dup
      // 6369: bipush 27
      // 636b: new net/minecraft/client/animation/Keyframe
      // 636e: dup
      // 636f: ldc 1.12
      // 6371: ldc_w 62.041
      // 6374: ldc_w 63.823
      // 6377: ldc_w 83.234
      // 637a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 637d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6380: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6383: aastore
      // 6384: dup
      // 6385: bipush 28
      // 6387: new net/minecraft/client/animation/Keyframe
      // 638a: dup
      // 638b: ldc 1.17
      // 638d: ldc_w 91.322
      // 6390: ldc_w 69.645
      // 6393: ldc_w 124.634
      // 6396: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6399: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 639c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 639f: aastore
      // 63a0: dup
      // 63a1: bipush 29
      // 63a3: new net/minecraft/client/animation/Keyframe
      // 63a6: dup
      // 63a7: ldc 1.21
      // 63a9: ldc_w 101.595
      // 63ac: ldc_w 78.221
      // 63af: ldc_w 147.511
      // 63b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63bb: aastore
      // 63bc: dup
      // 63bd: bipush 30
      // 63bf: new net/minecraft/client/animation/Keyframe
      // 63c2: dup
      // 63c3: ldc 1.25
      // 63c5: ldc_w 92.221
      // 63c8: ldc_w 66.739
      // 63cb: ldc_w 135.275
      // 63ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63d7: aastore
      // 63d8: dup
      // 63d9: bipush 31
      // 63db: new net/minecraft/client/animation/Keyframe
      // 63de: dup
      // 63df: ldc 1.29
      // 63e1: ldc_w 87.734
      // 63e4: ldc_w 60.305
      // 63e7: ldc_w 127.208
      // 63ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63f3: aastore
      // 63f4: dup
      // 63f5: bipush 32
      // 63f7: new net/minecraft/client/animation/Keyframe
      // 63fa: dup
      // 63fb: ldc 1.33
      // 63fd: ldc_w 87.867
      // 6400: ldc_w 56.037
      // 6403: ldc_w 124.031
      // 6406: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6409: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 640c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 640f: aastore
      // 6410: dup
      // 6411: bipush 33
      // 6413: new net/minecraft/client/animation/Keyframe
      // 6416: dup
      // 6417: ldc 1.38
      // 6419: ldc_w 90.23
      // 641c: ldc_w 52.524
      // 641f: ldc_w 124.008
      // 6422: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6425: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6428: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 642b: aastore
      // 642c: dup
      // 642d: bipush 34
      // 642f: new net/minecraft/client/animation/Keyframe
      // 6432: dup
      // 6433: ldc 1.42
      // 6435: ldc_w 95.038
      // 6438: ldc_w 49.651
      // 643b: ldc_w 127.253
      // 643e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6441: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6444: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6447: aastore
      // 6448: dup
      // 6449: bipush 35
      // 644b: new net/minecraft/client/animation/Keyframe
      // 644e: dup
      // 644f: ldc 1.46
      // 6451: ldc_w 102.644
      // 6454: ldc_w 47.598
      // 6457: ldc_w 133.882
      // 645a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 645d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6460: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6463: aastore
      // 6464: dup
      // 6465: bipush 36
      // 6467: new net/minecraft/client/animation/Keyframe
      // 646a: dup
      // 646b: ldc 1.5
      // 646d: ldc_w 112.955
      // 6470: ldc_w 46.762
      // 6473: ldc_w 143.544
      // 6476: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6479: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 647c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 647f: aastore
      // 6480: dup
      // 6481: bipush 37
      // 6483: new net/minecraft/client/animation/Keyframe
      // 6486: dup
      // 6487: ldc 1.54
      // 6489: ldc_w 124.854
      // 648c: ldc_w 47.585
      // 648f: ldc_w 154.882
      // 6492: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6495: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6498: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 649b: aastore
      // 649c: dup
      // 649d: bipush 38
      // 649f: new net/minecraft/client/animation/Keyframe
      // 64a2: dup
      // 64a3: ldc 1.58
      // 64a5: ldc_w 136.448
      // 64a8: ldc_w 50.259
      // 64ab: ldc_w 165.801
      // 64ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64b7: aastore
      // 64b8: dup
      // 64b9: bipush 39
      // 64bb: new net/minecraft/client/animation/Keyframe
      // 64be: dup
      // 64bf: ldc 1.62
      // 64c1: ldc_w 146.275
      // 64c4: ldc_w 54.575
      // 64c7: ldc_w 174.682
      // 64ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64d3: aastore
      // 64d4: dup
      // 64d5: bipush 40
      // 64d7: new net/minecraft/client/animation/Keyframe
      // 64da: dup
      // 64db: ldc 1.67
      // 64dd: ldc_w 153.288
      // 64e0: ldc_w 55.013
      // 64e3: ldc_w 178.253
      // 64e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64ef: aastore
      // 64f0: dup
      // 64f1: bipush 41
      // 64f3: new net/minecraft/client/animation/Keyframe
      // 64f6: dup
      // 64f7: ldc 1.71
      // 64f9: ldc_w 158.531
      // 64fc: ldc_w 47.282
      // 64ff: ldc_w 176.455
      // 6502: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6505: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6508: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 650b: aastore
      // 650c: dup
      // 650d: bipush 42
      // 650f: new net/minecraft/client/animation/Keyframe
      // 6512: dup
      // 6513: ldc 1.75
      // 6515: ldc_w 161.074
      // 6518: ldc_w 34.368
      // 651b: ldc_w 170.038
      // 651e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6521: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6524: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6527: aastore
      // 6528: dup
      // 6529: bipush 43
      // 652b: new net/minecraft/client/animation/Keyframe
      // 652e: dup
      // 652f: ldc 1.79
      // 6531: ldc_w 160.79
      // 6534: ldc_w 25.87
      // 6537: ldc_w 163.169
      // 653a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 653d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6540: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6543: aastore
      // 6544: dup
      // 6545: bipush 44
      // 6547: new net/minecraft/client/animation/Keyframe
      // 654a: dup
      // 654b: ldc 1.83
      // 654d: ldc_w 172.194
      // 6550: ldc_w 26.643
      // 6553: ldc_w 173.38
      // 6556: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6559: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 655c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 655f: aastore
      // 6560: dup
      // 6561: bipush 45
      // 6563: new net/minecraft/client/animation/Keyframe
      // 6566: dup
      // 6567: ldc 1.88
      // 6569: ldc_w 177.458
      // 656c: ldc_w 32.639
      // 656f: ldc_w 180.458
      // 6572: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6575: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6578: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 657b: aastore
      // 657c: dup
      // 657d: bipush 46
      // 657f: new net/minecraft/client/animation/Keyframe
      // 6582: dup
      // 6583: ldc 1.92
      // 6585: ldc_w 177.211
      // 6588: ldc_w 37.277
      // 658b: ldc_w 182.789
      // 658e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6591: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6594: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6597: aastore
      // 6598: dup
      // 6599: bipush 47
      // 659b: new net/minecraft/client/animation/Keyframe
      // 659e: dup
      // 659f: ldc_w 1.96
      // 65a2: ldc_w 176.232
      // 65a5: ldc_w 39.197
      // 65a8: ldc_w 183.768
      // 65ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65b4: aastore
      // 65b5: dup
      // 65b6: bipush 48
      // 65b8: new net/minecraft/client/animation/Keyframe
      // 65bb: dup
      // 65bc: fconst_2
      // 65bd: ldc_w 176.391
      // 65c0: ldc_w 38.901
      // 65c3: ldc_w 183.609
      // 65c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65cf: aastore
      // 65d0: dup
      // 65d1: bipush 49
      // 65d3: new net/minecraft/client/animation/Keyframe
      // 65d6: dup
      // 65d7: ldc_w 2.04
      // 65da: ldc_w 177.211
      // 65dd: ldc_w 37.277
      // 65e0: ldc_w 182.789
      // 65e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65ec: aastore
      // 65ed: dup
      // 65ee: bipush 50
      // 65f0: new net/minecraft/client/animation/Keyframe
      // 65f3: dup
      // 65f4: ldc_w 2.08
      // 65f7: ldc_w 177.628
      // 65fa: ldc_w 36.371
      // 65fd: ldc_w 182.372
      // 6600: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6603: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6606: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6609: aastore
      // 660a: dup
      // 660b: bipush 51
      // 660d: new net/minecraft/client/animation/Keyframe
      // 6610: dup
      // 6611: ldc_w 2.12
      // 6614: ldc_w 177.419
      // 6617: ldc_w 36.824
      // 661a: ldc_w 182.581
      // 661d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6620: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6623: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6626: aastore
      // 6627: dup
      // 6628: bipush 52
      // 662a: new net/minecraft/client/animation/Keyframe
      // 662d: dup
      // 662e: ldc 2.17
      // 6630: ldc_w 177.211
      // 6633: ldc_w 37.277
      // 6636: ldc_w 182.789
      // 6639: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 663c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 663f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6642: aastore
      // 6643: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6646: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6649: ldc_w "CTR_leg_right"
      // 664c: new net/minecraft/client/animation/AnimationChannel
      // 664f: dup
      // 6650: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6653: bipush 53
      // 6655: anewarray 28
      // 6658: dup
      // 6659: bipush 0
      // 665a: new net/minecraft/client/animation/Keyframe
      // 665d: dup
      // 665e: fconst_0
      // 665f: ldc_w 0.698
      // 6662: ldc_w 0.987
      // 6665: ldc_w 0.254
      // 6668: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 666b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 666e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6671: aastore
      // 6672: dup
      // 6673: bipush 1
      // 6674: new net/minecraft/client/animation/Keyframe
      // 6677: dup
      // 6678: ldc 0.04
      // 667a: ldc_w 0.692
      // 667d: ldc_w 0.677
      // 6680: ldc_w -0.669
      // 6683: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6686: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6689: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 668c: aastore
      // 668d: dup
      // 668e: bipush 2
      // 668f: new net/minecraft/client/animation/Keyframe
      // 6692: dup
      // 6693: ldc 0.08
      // 6695: ldc_w 0.615
      // 6698: ldc_w 0.44
      // 669b: ldc_w -1.311
      // 669e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66a7: aastore
      // 66a8: dup
      // 66a9: bipush 3
      // 66aa: new net/minecraft/client/animation/Keyframe
      // 66ad: dup
      // 66ae: ldc 0.12
      // 66b0: ldc_w 0.557
      // 66b3: ldc_w 2.308
      // 66b6: ldc_w 0.247
      // 66b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66c2: aastore
      // 66c3: dup
      // 66c4: bipush 4
      // 66c5: new net/minecraft/client/animation/Keyframe
      // 66c8: dup
      // 66c9: ldc 0.17
      // 66cb: ldc_w 1.204
      // 66ce: ldc_w 3.512
      // 66d1: ldc_w 1.029
      // 66d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66dd: aastore
      // 66de: dup
      // 66df: bipush 5
      // 66e0: new net/minecraft/client/animation/Keyframe
      // 66e3: dup
      // 66e4: ldc 0.21
      // 66e6: ldc_w 0.746
      // 66e9: ldc_w -0.797
      // 66ec: ldc_w -2.263
      // 66ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66f8: aastore
      // 66f9: dup
      // 66fa: bipush 6
      // 66fc: new net/minecraft/client/animation/Keyframe
      // 66ff: dup
      // 6700: ldc 0.25
      // 6702: ldc_w 0.331
      // 6705: ldc_w -4.954
      // 6708: ldc_w -3.247
      // 670b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 670e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6711: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6714: aastore
      // 6715: dup
      // 6716: bipush 7
      // 6718: new net/minecraft/client/animation/Keyframe
      // 671b: dup
      // 671c: ldc 0.29
      // 671e: ldc_w 0.301
      // 6721: ldc_w -4.71
      // 6724: ldc_w -3.196
      // 6727: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 672a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 672d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6730: aastore
      // 6731: dup
      // 6732: bipush 8
      // 6734: new net/minecraft/client/animation/Keyframe
      // 6737: dup
      // 6738: ldc 0.33
      // 673a: ldc_w 0.278
      // 673d: ldc_w -4.743
      // 6740: ldc_w -3.173
      // 6743: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6746: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6749: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 674c: aastore
      // 674d: dup
      // 674e: bipush 9
      // 6750: new net/minecraft/client/animation/Keyframe
      // 6753: dup
      // 6754: ldc 0.38
      // 6756: ldc_w 0.271
      // 6759: ldc_w -4.842
      // 675c: ldc_w -3.147
      // 675f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6762: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6765: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6768: aastore
      // 6769: dup
      // 676a: bipush 10
      // 676c: new net/minecraft/client/animation/Keyframe
      // 676f: dup
      // 6770: ldc 0.42
      // 6772: ldc_w 0.255
      // 6775: ldc_w -4.858
      // 6778: ldc_w -3.113
      // 677b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 677e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6781: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6784: aastore
      // 6785: dup
      // 6786: bipush 11
      // 6788: new net/minecraft/client/animation/Keyframe
      // 678b: dup
      // 678c: ldc 0.46
      // 678e: ldc_w 0.23
      // 6791: ldc_w -4.781
      // 6794: ldc_w -3.086
      // 6797: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 679a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 679d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67a0: aastore
      // 67a1: dup
      // 67a2: bipush 12
      // 67a4: new net/minecraft/client/animation/Keyframe
      // 67a7: dup
      // 67a8: ldc 0.5
      // 67aa: ldc_w 0.202
      // 67ad: ldc_w -4.65
      // 67b0: ldc_w -3.069
      // 67b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67bc: aastore
      // 67bd: dup
      // 67be: bipush 13
      // 67c0: new net/minecraft/client/animation/Keyframe
      // 67c3: dup
      // 67c4: ldc 0.54
      // 67c6: ldc_w 0.173
      // 67c9: ldc_w -4.433
      // 67cc: ldc_w -3.067
      // 67cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67d8: aastore
      // 67d9: dup
      // 67da: bipush 14
      // 67dc: new net/minecraft/client/animation/Keyframe
      // 67df: dup
      // 67e0: ldc 0.58
      // 67e2: ldc_w 0.15
      // 67e5: ldc_w -4.108
      // 67e8: ldc_w -3.079
      // 67eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67f4: aastore
      // 67f5: dup
      // 67f6: bipush 15
      // 67f8: new net/minecraft/client/animation/Keyframe
      // 67fb: dup
      // 67fc: ldc 0.62
      // 67fe: ldc_w 0.15
      // 6801: ldc_w -3.624
      // 6804: ldc_w -3.097
      // 6807: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 680a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 680d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6810: aastore
      // 6811: dup
      // 6812: bipush 16
      // 6814: new net/minecraft/client/animation/Keyframe
      // 6817: dup
      // 6818: ldc 0.67
      // 681a: ldc_w 0.205
      // 681d: ldc_w -2.968
      // 6820: ldc_w -3.097
      // 6823: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6826: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6829: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 682c: aastore
      // 682d: dup
      // 682e: bipush 17
      // 6830: new net/minecraft/client/animation/Keyframe
      // 6833: dup
      // 6834: ldc 0.71
      // 6836: ldc_w 0.365
      // 6839: ldc_w -2.186
      // 683c: ldc_w -3.07
      // 683f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6842: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6845: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6848: aastore
      // 6849: dup
      // 684a: bipush 18
      // 684c: new net/minecraft/client/animation/Keyframe
      // 684f: dup
      // 6850: ldc 0.75
      // 6852: ldc_w 0.675
      // 6855: ldc_w -1.365
      // 6858: ldc_w -3.019
      // 685b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 685e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6861: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6864: aastore
      // 6865: dup
      // 6866: bipush 19
      // 6868: new net/minecraft/client/animation/Keyframe
      // 686b: dup
      // 686c: ldc 0.79
      // 686e: ldc_w 1.141
      // 6871: ldc_w -0.628
      // 6874: ldc_w -2.962
      // 6877: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 687a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 687d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6880: aastore
      // 6881: dup
      // 6882: bipush 20
      // 6884: new net/minecraft/client/animation/Keyframe
      // 6887: dup
      // 6888: ldc 0.83
      // 688a: ldc_w 1.711
      // 688d: ldc_w -0.119
      // 6890: ldc_w -2.958
      // 6893: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6896: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6899: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 689c: aastore
      // 689d: dup
      // 689e: bipush 21
      // 68a0: new net/minecraft/client/animation/Keyframe
      // 68a3: dup
      // 68a4: ldc 0.88
      // 68a6: ldc_w 2.235
      // 68a9: ldc_w 0.014
      // 68ac: ldc_w -3.071
      // 68af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68b8: aastore
      // 68b9: dup
      // 68ba: bipush 22
      // 68bc: new net/minecraft/client/animation/Keyframe
      // 68bf: dup
      // 68c0: ldc 0.92
      // 68c2: ldc_w 2.468
      // 68c5: ldc_w -0.355
      // 68c8: ldc -3.298
      // 68ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68d3: aastore
      // 68d4: dup
      // 68d5: bipush 23
      // 68d7: new net/minecraft/client/animation/Keyframe
      // 68da: dup
      // 68db: ldc 0.96
      // 68dd: ldc_w 2.139
      // 68e0: ldc_w -1.329
      // 68e3: ldc_w -3.472
      // 68e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68ef: aastore
      // 68f0: dup
      // 68f1: bipush 24
      // 68f3: new net/minecraft/client/animation/Keyframe
      // 68f6: dup
      // 68f7: fconst_1
      // 68f8: ldc_w 1.114
      // 68fb: ldc_w -3.036
      // 68fe: ldc_w -3.223
      // 6901: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6904: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6907: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 690a: aastore
      // 690b: dup
      // 690c: bipush 25
      // 690e: new net/minecraft/client/animation/Keyframe
      // 6911: dup
      // 6912: ldc 1.04
      // 6914: ldc_w -0.089
      // 6917: ldc_w -4.999
      // 691a: ldc_w -2.254
      // 691d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6920: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6923: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6926: aastore
      // 6927: dup
      // 6928: bipush 26
      // 692a: new net/minecraft/client/animation/Keyframe
      // 692d: dup
      // 692e: ldc 1.08
      // 6930: ldc_w -0.171
      // 6933: ldc_w -4.862
      // 6936: ldc_w -1.741
      // 6939: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 693c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 693f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6942: aastore
      // 6943: dup
      // 6944: bipush 27
      // 6946: new net/minecraft/client/animation/Keyframe
      // 6949: dup
      // 694a: ldc 1.12
      // 694c: ldc_w -0.256
      // 694f: ldc_w -4.676
      // 6952: ldc_w -1.104
      // 6955: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6958: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 695b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 695e: aastore
      // 695f: dup
      // 6960: bipush 28
      // 6962: new net/minecraft/client/animation/Keyframe
      // 6965: dup
      // 6966: ldc 1.17
      // 6968: ldc_w 4.261
      // 696b: ldc_w 1.566
      // 696e: ldc_w -0.524
      // 6971: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 697a: aastore
      // 697b: dup
      // 697c: bipush 29
      // 697e: new net/minecraft/client/animation/Keyframe
      // 6981: dup
      // 6982: ldc 1.21
      // 6984: ldc_w 5.872
      // 6987: ldc_w 0.149
      // 698a: ldc_w 1.248
      // 698d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6990: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6993: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6996: aastore
      // 6997: dup
      // 6998: bipush 30
      // 699a: new net/minecraft/client/animation/Keyframe
      // 699d: dup
      // 699e: ldc 1.25
      // 69a0: ldc_w 4.689
      // 69a3: ldc_w 0.586
      // 69a6: ldc_w 0.587
      // 69a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69b2: aastore
      // 69b3: dup
      // 69b4: bipush 31
      // 69b6: new net/minecraft/client/animation/Keyframe
      // 69b9: dup
      // 69ba: ldc 1.29
      // 69bc: ldc_w 3.797
      // 69bf: ldc_w 0.534
      // 69c2: ldc_w 0.425
      // 69c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69ce: aastore
      // 69cf: dup
      // 69d0: bipush 32
      // 69d2: new net/minecraft/client/animation/Keyframe
      // 69d5: dup
      // 69d6: ldc 1.33
      // 69d8: ldc_w 3.22
      // 69db: ldc_w 0.352
      // 69de: ldc_w 0.452
      // 69e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69ea: aastore
      // 69eb: dup
      // 69ec: bipush 33
      // 69ee: new net/minecraft/client/animation/Keyframe
      // 69f1: dup
      // 69f2: ldc 1.38
      // 69f4: ldc_w 2.814
      // 69f7: ldc_w 0.091
      // 69fa: ldc_w 0.522
      // 69fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a06: aastore
      // 6a07: dup
      // 6a08: bipush 34
      // 6a0a: new net/minecraft/client/animation/Keyframe
      // 6a0d: dup
      // 6a0e: ldc 1.42
      // 6a10: ldc_w 2.531
      // 6a13: ldc_w -0.218
      // 6a16: ldc_w 0.609
      // 6a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a22: aastore
      // 6a23: dup
      // 6a24: bipush 35
      // 6a26: new net/minecraft/client/animation/Keyframe
      // 6a29: dup
      // 6a2a: ldc 1.46
      // 6a2c: ldc_w 2.302
      // 6a2f: ldc_w -0.589
      // 6a32: ldc_w 0.702
      // 6a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a3e: aastore
      // 6a3f: dup
      // 6a40: bipush 36
      // 6a42: new net/minecraft/client/animation/Keyframe
      // 6a45: dup
      // 6a46: ldc 1.5
      // 6a48: ldc_w 2.053
      // 6a4b: ldc_w -1.057
      // 6a4e: ldc_w 0.782
      // 6a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a5a: aastore
      // 6a5b: dup
      // 6a5c: bipush 37
      // 6a5e: new net/minecraft/client/animation/Keyframe
      // 6a61: dup
      // 6a62: ldc 1.54
      // 6a64: ldc_w 1.721
      // 6a67: ldc_w -1.654
      // 6a6a: ldc_w 0.8
      // 6a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a76: aastore
      // 6a77: dup
      // 6a78: bipush 38
      // 6a7a: new net/minecraft/client/animation/Keyframe
      // 6a7d: dup
      // 6a7e: ldc 1.58
      // 6a80: ldc_w 1.264
      // 6a83: ldc_w -2.4
      // 6a86: ldc_w 0.674
      // 6a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a92: aastore
      // 6a93: dup
      // 6a94: bipush 39
      // 6a96: new net/minecraft/client/animation/Keyframe
      // 6a99: dup
      // 6a9a: ldc 1.62
      // 6a9c: ldc_w 0.681
      // 6a9f: ldc_w -3.278
      // 6aa2: ldc_w 0.304
      // 6aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6aae: aastore
      // 6aaf: dup
      // 6ab0: bipush 40
      // 6ab2: new net/minecraft/client/animation/Keyframe
      // 6ab5: dup
      // 6ab6: ldc 1.67
      // 6ab8: ldc_w 1.137
      // 6abb: ldc_w -2.523
      // 6abe: ldc_w 0.747
      // 6ac1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ac4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ac7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6aca: aastore
      // 6acb: dup
      // 6acc: bipush 41
      // 6ace: new net/minecraft/client/animation/Keyframe
      // 6ad1: dup
      // 6ad2: ldc 1.71
      // 6ad4: ldc_w 2.052
      // 6ad7: ldc_w 0.422
      // 6ada: ldc_w 1.677
      // 6add: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ae0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ae3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ae6: aastore
      // 6ae7: dup
      // 6ae8: bipush 42
      // 6aea: new net/minecraft/client/animation/Keyframe
      // 6aed: dup
      // 6aee: ldc 1.75
      // 6af0: ldc_w 1.403
      // 6af3: ldc_w 2.336
      // 6af6: ldc_w 0.566
      // 6af9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6afc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6aff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b02: aastore
      // 6b03: dup
      // 6b04: bipush 43
      // 6b06: new net/minecraft/client/animation/Keyframe
      // 6b09: dup
      // 6b0a: ldc 1.79
      // 6b0c: ldc_w 0.273
      // 6b0f: ldc_w 0.642
      // 6b12: ldc_w -0.89
      // 6b15: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b1e: aastore
      // 6b1f: dup
      // 6b20: bipush 44
      // 6b22: new net/minecraft/client/animation/Keyframe
      // 6b25: dup
      // 6b26: ldc 1.83
      // 6b28: ldc_w 0.057
      // 6b2b: ldc_w -0.389
      // 6b2e: ldc_w -1.045
      // 6b31: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b3a: aastore
      // 6b3b: dup
      // 6b3c: bipush 45
      // 6b3e: new net/minecraft/client/animation/Keyframe
      // 6b41: dup
      // 6b42: ldc 1.88
      // 6b44: ldc_w 0.318
      // 6b47: ldc_w 0.367
      // 6b4a: ldc_w -0.421
      // 6b4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b56: aastore
      // 6b57: dup
      // 6b58: bipush 46
      // 6b5a: new net/minecraft/client/animation/Keyframe
      // 6b5d: dup
      // 6b5e: ldc 1.92
      // 6b60: ldc_w 0.698
      // 6b63: ldc_w 0.987
      // 6b66: ldc_w 0.254
      // 6b69: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b72: aastore
      // 6b73: dup
      // 6b74: bipush 47
      // 6b76: new net/minecraft/client/animation/Keyframe
      // 6b79: dup
      // 6b7a: ldc_w 1.96
      // 6b7d: ldc_w 1.004
      // 6b80: ldc 1.33
      // 6b82: ldc_w 0.627
      // 6b85: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b8e: aastore
      // 6b8f: dup
      // 6b90: bipush 48
      // 6b92: new net/minecraft/client/animation/Keyframe
      // 6b95: dup
      // 6b96: fconst_2
      // 6b97: ldc_w 0.952
      // 6b9a: ldc_w 1.274
      // 6b9d: ldc_w 0.566
      // 6ba0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ba3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ba6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ba9: aastore
      // 6baa: dup
      // 6bab: bipush 49
      // 6bad: new net/minecraft/client/animation/Keyframe
      // 6bb0: dup
      // 6bb1: ldc_w 2.04
      // 6bb4: ldc_w 0.698
      // 6bb7: ldc_w 0.987
      // 6bba: ldc_w 0.254
      // 6bbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bc6: aastore
      // 6bc7: dup
      // 6bc8: bipush 50
      // 6bca: new net/minecraft/client/animation/Keyframe
      // 6bcd: dup
      // 6bce: ldc_w 2.08
      // 6bd1: ldc_w 0.576
      // 6bd4: ldc_w 0.832
      // 6bd7: ldc_w 0.093
      // 6bda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6be0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6be3: aastore
      // 6be4: dup
      // 6be5: bipush 51
      // 6be7: new net/minecraft/client/animation/Keyframe
      // 6bea: dup
      // 6beb: ldc_w 2.12
      // 6bee: ldc_w 0.636
      // 6bf1: ldc_w 0.911
      // 6bf4: ldc_w 0.173
      // 6bf7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c00: aastore
      // 6c01: dup
      // 6c02: bipush 52
      // 6c04: new net/minecraft/client/animation/Keyframe
      // 6c07: dup
      // 6c08: ldc 2.17
      // 6c0a: ldc_w 0.698
      // 6c0d: ldc_w 0.987
      // 6c10: ldc_w 0.254
      // 6c13: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c1c: aastore
      // 6c1d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6c20: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6c23: ldc_w "CTR_leg_right.002"
      // 6c26: new net/minecraft/client/animation/AnimationChannel
      // 6c29: dup
      // 6c2a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6c2d: bipush 15
      // 6c2f: anewarray 28
      // 6c32: dup
      // 6c33: bipush 0
      // 6c34: new net/minecraft/client/animation/Keyframe
      // 6c37: dup
      // 6c38: ldc 0.08
      // 6c3a: ldc_w -2.707
      // 6c3d: fconst_0
      // 6c3e: ldc_w -2.155
      // 6c41: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c4a: aastore
      // 6c4b: dup
      // 6c4c: bipush 1
      // 6c4d: new net/minecraft/client/animation/Keyframe
      // 6c50: dup
      // 6c51: ldc 0.12
      // 6c53: ldc_w -4.553
      // 6c56: ldc_w 3.233
      // 6c59: ldc_w -1.225
      // 6c5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c65: aastore
      // 6c66: dup
      // 6c67: bipush 2
      // 6c68: new net/minecraft/client/animation/Keyframe
      // 6c6b: dup
      // 6c6c: ldc 0.17
      // 6c6e: ldc_w -7.789
      // 6c71: ldc_w 6.466
      // 6c74: ldc_w 4.236
      // 6c77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c80: aastore
      // 6c81: dup
      // 6c82: bipush 3
      // 6c83: new net/minecraft/client/animation/Keyframe
      // 6c86: dup
      // 6c87: ldc 0.21
      // 6c89: ldc_w -9.651
      // 6c8c: ldc_w 3.233
      // 6c8f: ldc_w 15.385
      // 6c92: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c95: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c98: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c9b: aastore
      // 6c9c: dup
      // 6c9d: bipush 4
      // 6c9e: new net/minecraft/client/animation/Keyframe
      // 6ca1: dup
      // 6ca2: ldc 0.25
      // 6ca4: ldc_w -10.123
      // 6ca7: fconst_0
      // 6ca8: ldc_w 22.005
      // 6cab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cb4: aastore
      // 6cb5: dup
      // 6cb6: bipush 5
      // 6cb7: new net/minecraft/client/animation/Keyframe
      // 6cba: dup
      // 6cbb: ldc 1.08
      // 6cbd: ldc_w -10.123
      // 6cc0: fconst_0
      // 6cc1: ldc_w 22.005
      // 6cc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ccd: aastore
      // 6cce: dup
      // 6ccf: bipush 6
      // 6cd1: new net/minecraft/client/animation/Keyframe
      // 6cd4: dup
      // 6cd5: ldc 1.12
      // 6cd7: ldc_w -10.469
      // 6cda: ldc_w -0.714
      // 6cdd: ldc_w 7.635
      // 6ce0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ce3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ce6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ce9: aastore
      // 6cea: dup
      // 6ceb: bipush 7
      // 6ced: new net/minecraft/client/animation/Keyframe
      // 6cf0: dup
      // 6cf1: ldc 1.17
      // 6cf3: ldc_w -11.111
      // 6cf6: ldc_w -2.039
      // 6cf9: ldc_w -19.052
      // 6cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d05: aastore
      // 6d06: dup
      // 6d07: bipush 8
      // 6d09: new net/minecraft/client/animation/Keyframe
      // 6d0c: dup
      // 6d0d: ldc 1.21
      // 6d0f: ldc_w -11.457
      // 6d12: ldc_w -2.752
      // 6d15: ldc_w -33.422
      // 6d18: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d21: aastore
      // 6d22: dup
      // 6d23: bipush 9
      // 6d25: new net/minecraft/client/animation/Keyframe
      // 6d28: dup
      // 6d29: ldc 1.62
      // 6d2b: ldc_w -11.457
      // 6d2e: ldc_w -2.752
      // 6d31: ldc_w -33.422
      // 6d34: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d3d: aastore
      // 6d3e: dup
      // 6d3f: bipush 10
      // 6d41: new net/minecraft/client/animation/Keyframe
      // 6d44: dup
      // 6d45: ldc 1.67
      // 6d47: ldc_w -10.627
      // 6d4a: ldc_w -1.52
      // 6d4d: ldc_w -30.114
      // 6d50: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d59: aastore
      // 6d5a: dup
      // 6d5b: bipush 11
      // 6d5d: new net/minecraft/client/animation/Keyframe
      // 6d60: dup
      // 6d61: ldc 1.71
      // 6d63: ldc_w -8.594
      // 6d66: ldc_w 0.767
      // 6d69: ldc_w -22.263
      // 6d6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d75: aastore
      // 6d76: dup
      // 6d77: bipush 12
      // 6d79: new net/minecraft/client/animation/Keyframe
      // 6d7c: dup
      // 6d7d: ldc 1.75
      // 6d7f: ldc_w -6.041
      // 6d82: ldc_w 1.999
      // 6d85: ldc_w -12.982
      // 6d88: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6d8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d91: aastore
      // 6d92: dup
      // 6d93: bipush 13
      // 6d95: new net/minecraft/client/animation/Keyframe
      // 6d98: dup
      // 6d99: ldc 1.79
      // 6d9b: ldc_w -3.728
      // 6d9e: fconst_1
      // 6d9f: ldc_w -5.328
      // 6da2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6da5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6da8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dab: aastore
      // 6dac: dup
      // 6dad: bipush 14
      // 6daf: new net/minecraft/client/animation/Keyframe
      // 6db2: dup
      // 6db3: ldc 1.83
      // 6db5: ldc_w -2.707
      // 6db8: fconst_0
      // 6db9: ldc_w -2.155
      // 6dbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6dbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6dc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dc5: aastore
      // 6dc6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6dc9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6dcc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 6dcf: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiHandSwipe1.HAND_SWIPE_1 Lnet/minecraft/client/animation/AnimationDefinition;
      // 6dd2: return
   }
}
