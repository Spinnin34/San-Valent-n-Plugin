package p.zestianparejas.command;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.zestianparejas.ZestianParejas;
import p.zestianparejas.mensaje.MessageUtils;

public class DivorcioCommand implements CommandExecutor {
    private final ZestianParejas plugin;

    public DivorcioCommand(ZestianParejas plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar si el jugador está casado
        if (plugin.getPareja(player) == null) {
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§C§4§CNo estás casado/a."));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            return true;
        }

        // Divorciar a la pareja
        plugin.removePareja(player); // Eliminar la pareja del archivo YAML

        // Notificar a los jugadores sobre el divorcio
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§C§4§CTe has divorciado/a."));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        return true;
    }
}


