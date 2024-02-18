package p.zestianparejas.command;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.zestianparejas.ZestianParejas;
import p.zestianparejas.mensaje.MessageUtils;

import java.util.HashMap;
import java.util.Map;

public class CasarseCommand implements CommandExecutor {
    private Map<Player, Long> tiemposSolicitudes = new HashMap<>();
    private final ZestianParejas plugin;

    public CasarseCommand(ZestianParejas plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§C§4§CUso incorrecto del comando"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" §f↪ §7/casarse <nombre> | §aaceptar §7| §cdenegar"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));

            return true;
        }

        if (plugin.getPareja(player) != null) {
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§C§4§C¡Ya estás casado/a!"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));

            return true;
        }

        String subcommand = args[0].toLowerCase();
        if (subcommand.equals("aceptar") || subcommand.equals("denegar")) {
            if (!plugin.getSolicitudesPendientes().containsKey(player)) {
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§B§C§5§1No tienes ninguna solicitud de matrimonio pendiente."));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                return true;
            }

            Player pareja = plugin.getSolicitudesPendientes().get(player);
            long cooldown = 60 * 1000; // 60 segundos en milisegundos
            long currentTime = System.currentTimeMillis();
            long solicitudTime = tiemposSolicitudes.get(player);

            if ((currentTime - solicitudTime) >= cooldown) {
                plugin.getSolicitudesPendientes().remove(player);
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§C§4§CLa solicitud de matrimonio ha caducado."));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                return true;
            }

            if (subcommand.equals("aceptar")) {
                plugin.setPareja(player, pareja);
                plugin.getSolicitudesPendientes().remove(player);
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§B§C§5§1Has aceptado casarte con §x§F§B§4§2§4§2" + pareja.getName()));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));

                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§2§4§2" + player.getName() + " §x§F§B§B§C§5§1ha aceptado tu solicitud de matrimonio."));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));

                enviarTitulo(pareja, "§x§9§7§F§B§8§3§lMatrimonio Aceptado", "§x§F§B§B§C§5§1Felicidades, te has casado con §x§F§B§4§2§4§2" + player.getName());
            } else {
                plugin.getSolicitudesPendientes().remove(player);
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§B§C§5§1Has rechazado la solicitud de matrimonio de §x§F§B§4§2§4§2" + pareja.getName()));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));
                player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                player.sendMessage(MessageUtils.sendCenteredMessage(" "));

                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§2§4§2" + player.getName() + " §x§F§B§B§C§5§1ha rechazado tu solicitud de matrimonio."));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
                pareja.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
                pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));

                enviarTitulo(pareja,  "§c§lMatrimonio Rechazado",  "§x§F§B§B§C§5§1Lo siento, §x§F§B§4§2§4§2" + player.getName() + "§x§F§B§B§C§5§1 ha rechazado tu solicitud de matrimonio.");
            }
            return true;
        }

        String parejaName = args[0];
        Player pareja = player.getServer().getPlayer(parejaName);
        if (pareja == null) {
            player.sendMessage(ChatColor.RED + "⚠ Jugador no encontrado o no está en línea.");
            return true;
        }

        if (plugin.getSolicitudesPendientes().containsKey(pareja)) {
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§2§4§2" + pareja.getName() + " §x§F§B§B§C§5§1§x§F§B§B§C§5§1 ya tiene una solicitud de matrimonio pendiente."));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));
            player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
            player.sendMessage(MessageUtils.sendCenteredMessage(" "));

            return true;
        }

        tiemposSolicitudes.put(player, System.currentTimeMillis()); // Almacena la marca de tiempo para la solicitud actual
        plugin.getSolicitudesPendientes().put(pareja, player);
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§x§9§7§F§B§8§3Has enviado una solicitud de matrimonio a §x§F§B§4§2§4§2" + pareja.getName()));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage(player.getName() + " §x§F§B§4§2§4§2❤ §f" + pareja.getName()));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));
        player.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
        player.sendMessage(MessageUtils.sendCenteredMessage(" "));

        pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
        pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§8§2§6§F§E§B§lAURORACRAFT PAREJAS"));
        pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
        pareja.sendMessage(MessageUtils.sendCenteredMessage("§x§F§B§4§2§4§2" + player.getName() + " §x§F§B§B§C§5§1te ha enviado una solicitud de matrimonio."));
        pareja.sendMessage(MessageUtils.sendCenteredMessage("§f/casarse aceptar o denegar"));
        pareja.sendMessage(MessageUtils.sendCenteredMessage(player.getName() + " §x§F§B§4§2§4§2❤ §f" + pareja.getName()));
        pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));
        pareja.sendMessage(MessageUtils.sendCenteredMessage("§7From Developer ©Spinnin34"));
        pareja.sendMessage(MessageUtils.sendCenteredMessage(" "));


        return true;
    }

    private void enviarTitulo(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 10, 70, 20);
    }
}

