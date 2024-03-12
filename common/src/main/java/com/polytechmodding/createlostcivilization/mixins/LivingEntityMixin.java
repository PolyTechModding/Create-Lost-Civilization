package com.polytechmodding.createlostcivilization.mixins;

import com.polytechmodding.createlostcivilization.entities.AirEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean canStandOnFluid(FluidState fluidState);
    @Shadow
    protected abstract boolean isAffectedByFluids();
    @Shadow
    protected abstract float getWaterSlowDown();
    @Shadow
    public abstract float getSpeed();
    @Shadow
    public abstract boolean onClimbable();
    @Shadow
    public abstract Vec3 getFluidFallingAdjustedMovement(double d, boolean bl, Vec3 vec3);

    @Unique
    private float createLostCivilization$waterSlowDown = 0.8F;

    @Inject(at = @At("HEAD"), method = "getWaterSlowDown()F", cancellable = true)
    protected void slowDownInject(@NotNull CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(createLostCivilization$waterSlowDown);
    }

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"))
    public void injectElseIf(Vec3 vec3, CallbackInfo ci) {
        if (((AirEntity) this).createLostCivilization$isInAir() && this.isAffectedByFluids()) {
            System.out.println("I got called");
            double e = this.getY();
            float f = this.isSprinting() ? 0.9f : this.getWaterSlowDown();
            float g = 0.02f;
            float h = EnchantmentHelper.getDepthStrider((LivingEntity)(Object) this);
            if (h > 3.0f) {
                h = 3.0f;
            }
            if (!this.onGround()) {
                h *= 0.5f;
            }
            if (h > 0.0f) {
                f += (0.54600006f - f) * h / 3.0f;
                g += (this.getSpeed() - g) * h / 3.0f;
            }
            this.moveRelative(g, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            Vec3 vec32 = this.getDeltaMovement();
            if (this.horizontalCollision && this.onClimbable()) {
                vec32 = new Vec3(vec32.x, 0.2, vec32.z);
            }
            this.setDeltaMovement(vec32.multiply(f, 0.8f, f));
            Vec3 vec33 = this.getFluidFallingAdjustedMovement(0.08, this.getDeltaMovement().y <= 0.0, this.getDeltaMovement());
            this.setDeltaMovement(vec33);
            if (this.horizontalCollision && this.isFree(vec33.x, vec33.y + (double)0.6f - this.getY() + e, vec33.z)) {
                this.setDeltaMovement(vec33.x, 0.3f, vec33.z);
            }
        }
    }
}
