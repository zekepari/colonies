package io.anthills.classes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Display.Billboard;

import io.anthills.config.NodeConfig.NodeData;
import io.anthills.utils.FormatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class NodeDisplayText {
    private final World world;
    private TextDisplay textDisplay;
    private final NodeData nodeData;

    public NodeDisplayText(Location location, NodeData nodeData) {
        this.world = location.getWorld();
        this.nodeData = nodeData;
        this.textDisplay = world.spawn(location, TextDisplay.class, entity -> {
            entity.text(nodeData.getNodeName());
            entity.setBillboard(Billboard.CENTER);
            entity.setPersistent(false);
        });
    }

    public void update(int cooldown) {
        if (textDisplay.isValid()) {
            Component text = nodeData.getNodeName();

            if (cooldown > 0) {
                double ratio = (double) cooldown / nodeData.getRegenTimeSeconds();
                int gb = (int) (255 * ratio);
                String formattedCooldown = FormatUtils.formatCooldown(cooldown);
                TextColor dynamicColor = TextColor.fromHexString(String.format("#%02X%02X%02X", 255, gb, gb));
                Component cooldownLine = Component.text(formattedCooldown + " Remaining", dynamicColor);
                text = text.appendNewline().append(cooldownLine);
            } else {
                Component cooldownLine = Component.text("Ready", NamedTextColor.GREEN);
                text = text.appendNewline().append(cooldownLine);
            }

            textDisplay.text(text);
        }
    }

    public void delete() {
        if (textDisplay.isValid()) {
            textDisplay.remove();
        }
    }
}
