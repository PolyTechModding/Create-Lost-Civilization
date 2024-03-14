package com.polytechmodding.createlostcivilization.mixins;

import com.polytechmodding.createlostcivilization.entities.AirEntity;
import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements AirEntity {

    private static final Logger logger = LoggerFactory.getLogger(Entity.class);

    @Shadow
    public abstract Level level();
    @Shadow
    public abstract boolean isSwimming();
    @Shadow
    public abstract boolean isSprinting();
    @Shadow
    public abstract boolean isInWater();
    @Shadow
    public abstract boolean isUnderWater();
    @Shadow
    public abstract boolean isPassenger();
    @Shadow
    protected abstract void doWaterSplashEffect();
    @Shadow
    public abstract void resetFallDistance();
    @Shadow
    public abstract void clearFire();
    @Shadow
    public abstract boolean touchingUnloadedChunk();
    @Shadow
    public abstract double getEyeY();
    @Shadow
    public abstract boolean isPushedByFluid();
    @Shadow
    public abstract Vec3 getDeltaMovement();
    @Shadow
    public abstract void setDeltaMovement(Vec3 vec3);
    @Shadow
    public abstract Entity getVehicle();
    @Shadow
    public abstract void setSwimming(boolean b);
    @Shadow
    public abstract AABB getBoundingBox();
    @Shadow
    private BlockPos blockPosition;
    @Shadow
    protected boolean firstTick;

    @Shadow protected boolean wasEyeInWater;

    @Shadow public abstract double getX();

    @Shadow public abstract double getZ();

    @Unique
    protected boolean createLostCivilization$wasTouchingAir;
    @Unique
    protected boolean createLostCivilization$wasEyeInAir;
    @Unique
    private double createLostCivilization$airHeight;
    @Unique
    private boolean createLostCivilization$airOnEyes;

    @Unique
    public double getCreateLostCivilization$airHeight() {
        return createLostCivilization$airHeight;
    }

    @Unique
    public boolean createLostCivilization$isInAir() {
        return this.createLostCivilization$wasTouchingAir &&
                level().dimensionTypeId().equals(CivilizationDimensions.MYSTERY_PLANET);
    }

    @Unique
    public boolean createLostCivilization$isUnderAir() {
        // logger.info("Is in Air: " + createLostCivilization$isInAir());
        // logger.info("Was Eye in Air: " + createLostCivilization$wasEyeInAir);
        return this.createLostCivilization$wasEyeInAir && this.createLostCivilization$isInAir();
    }

    @Unique
    protected boolean createLostCivilization$updateInAirStateAndDoAirPushing() {
        this.createLostCivilization$airHeight = 0;
        this.createLostCivilization$updateInAirStateAndDoAirCurrentPushing();
        return this.createLostCivilization$isInAir();
    }

    @Inject(method = "baseTick()V"
            ,at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateFluidOnEyes()V", shift = At.Shift.AFTER))
    public void injectTick(CallbackInfo ci) {
        // logger.info("Injection was called");
        createLostCivilization$updateInAirStateAndDoAirPushing();
        createLostCivilization$updateAirOnEyes();
    }

    @Inject(at = @At("HEAD"), method = "updateSwimming()V", cancellable = true)
    public void updateSwimming(CallbackInfo ci) {
        // logger.info("Injection of update swimming was called");
        boolean isMystery = level().dimensionTypeId().equals(CivilizationDimensions.MYSTERY_PLANET);
        if (this.isSwimming()) {
            // logger.info("I am swimming");
            this.setSwimming((!isMystery && this.isSprinting() && this.isInWater() && !this.isPassenger())
            || (this.isSprinting() && this.createLostCivilization$isInAir() && !this.isPassenger()));
            // logger.info("Passenger: " + !this.isPassenger());
            // logger.info("Sprinting: " + this.isSprinting());
            // logger.info("Is In Air: " + this.createLostCivilization$isInAir());
            // logger.info(String.valueOf((isMystery && this.isSprinting() && this.createLostCivilization$isInAir() && !this.isPassenger())));
            // logger.info("I am swimming");
        } else {
            this.setSwimming(((!isMystery && this.isSprinting() && this.isUnderWater() && !this.isPassenger()
                    && this.level().getFluidState(this.blockPosition).is(FluidTags.WATER)))
            || (isMystery && this.isSprinting() && this.createLostCivilization$isUnderAir() && !this.isPassenger()
                    && this.level().getBlockState(this.blockPosition).is(Blocks.AIR)));
        }
        ci.cancel();
    }

    @Unique
    void createLostCivilization$updateInAirStateAndDoAirCurrentPushing() {
        Boat boat;
        Entity entity = this.getVehicle();
        if (entity instanceof Boat && !((boat = (Boat)entity)).isUnderWater()) {
            this.createLostCivilization$wasTouchingAir = false;
        } else if (this.createLostCivilization$updateAirHeightAndDoAirPushing(0.014)) {
            if (!this.createLostCivilization$wasTouchingAir && !this.firstTick) {
                this.doWaterSplashEffect();
            }
            this.resetFallDistance();
            this.createLostCivilization$wasTouchingAir = true;
            this.clearFire();
        } else {
            this.createLostCivilization$wasTouchingAir = false;
        }
    }

    @Unique
    private void createLostCivilization$updateAirOnEyes() {
        Boat boat;
        this.wasEyeInWater = createLostCivilization$airOnEyes;
        createLostCivilization$airOnEyes = false;
        double d = this.getEyeY() - 0.1111111119389534;
        Entity entity = this.getVehicle();
        if (entity instanceof Boat && !(boat = (Boat)entity).isUnderWater()
                && boat.getBoundingBox().maxY >= d && boat.getBoundingBox().minY <= d) {
            return;
        }
        BlockPos blockPos = BlockPos.containing(this.getX(), d, this.getZ());
        double e = (float)blockPos.getY() + 384;
        if (e > d) {
            createLostCivilization$airOnEyes = true;
        }
    }

    @Unique
    public boolean createLostCivilization$updateAirHeightAndDoAirPushing(double d) {
        if (this.touchingUnloadedChunk()) {
            return false;
        }
        AABB aABB = this.getBoundingBox().deflate(0.001);
        int i = Mth.floor(aABB.minX);
        int j = Mth.ceil(aABB.maxX);
        int k = Mth.floor(aABB.minY);
        int l = Mth.ceil(aABB.maxY);
        int m = Mth.floor(aABB.minZ);
        int n = Mth.ceil(aABB.maxZ);
        double e = 0.0;
        boolean bl = this.isPushedByFluid();
        boolean bl2 = false;
        Vec3 vec3 = Vec3.ZERO;
        int o = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int p = i; p < j; ++p) {
            for (int q = k; q < l; ++q) {
                for (int r = m; r < n; ++r) {
                    double f;
                    mutableBlockPos.set(p, q, r);
                    if (!((f = (double)((float)q + 384)) >= aABB.minY)) continue;
                    bl2 = true;
                    e = Math.max(f - aABB.minY, e);
                    if (!bl) continue;
                    ++o;
                }
            }
        }
        if (vec3.length() > 0.0) {
            if (o > 0) {
                vec3 = vec3.scale(1.0 / (double)o);
            }
            if (!(((Entity)((Object)this)) instanceof Player)) {
                vec3 = vec3.normalize();
            }
            Vec3 vec33 = this.getDeltaMovement();
            vec3 = vec3.scale(d);
            double g = 0.003;
            if (Math.abs(vec33.x) < g && Math.abs(vec33.z) < g && vec3.length() < 0.15 * g) {
                vec3 = vec3.normalize().scale(0.15 * g);
            }
            this.setDeltaMovement(this.getDeltaMovement().add(vec3));
        }
        createLostCivilization$airHeight = e;
        return bl2;
    }
}
