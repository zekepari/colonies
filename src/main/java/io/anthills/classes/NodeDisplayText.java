package io.anthills.classes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Display.Billboard;

import io.anthills.utils.FormatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class NodeDisplayText {
    private final World world;
    private TextDisplay itemDisplay;
    private Component nodeName;

    public NodeDisplayText(Location location, Component nodeName) {
        this.world = location.getWorld();
        this.nodeName = nodeName;
        this.itemDisplay = world.spawn(location, TextDisplay.class, entity -> {
            entity.text(nodeName);
            entity.setBillboard(Billboard.CENTER);
            entity.setPersistent(false);
        });
    }

    public void update(int cooldown, int totalCooldown) {
        if (itemDisplay.isValid()) {
            Component text = nodeName;

            if (cooldown > 0) {
                double ratio = (double) cooldown / totalCooldown;
                int gb = (int) (255 * ratio);
                String formattedCooldown = FormatUtils.formatCooldown(cooldown);
                TextColor dynamicColor = TextColor.fromHexString(String.format("#%02X%02X%02X", 255, gb, gb));
                Component cooldownLine = Component.text(formattedCooldown + " Remaining", dynamicColor);
                text = text.appendNewline().append(cooldownLine);
            } else {
                Component cooldownLine = Component.text("Ready", NamedTextColor.GREEN);
                text = text.appendNewline().append(cooldownLine);
            }

            itemDisplay.text(text);
        }
    }

    public void delete() {
        if (itemDisplay.isValid()) {
            itemDisplay.remove();
        }
    }
}
