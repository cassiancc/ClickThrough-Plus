package cc.cassian.clickthrough.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(ControlsListWidget.class)
public class ControlsListWidgetMixin {
    @WrapOperation(method="<init>", at=@At(value="INVOKE",
            target="Ljava/util/Arrays;sort([Ljava/lang/Object;)V"))
    public void switchCrosshairTargetItemUse(Object[] a, Operation<Void> original, @Local LocalRef<KeyBinding[]> c) {
        KeyBinding[] array = Arrays.stream(c.get()).sorted((e, f) -> compareNamespaceFirst(e, f)).toArray(KeyBinding[]::new);
        c.set(array);
    }

    // Identifier#compareTo checks the path first, but we want to check the namespace first so that groups added by the
    // same mod appear next to each other.
    @Unique
    private static int compareNamespaceFirst(KeyBinding a, KeyBinding b) {
        var aId = a.getCategory().id().getNamespace();
        var bId = b.getCategory().id().getNamespace();

        if (!aId.equals("minecraft") && bId.equals("minecraft")) return 1000;

        int c = aId.compareTo(bId);

        if (c != 0) {
            return c;
        }

        return a.compareTo(b);
    }
}
