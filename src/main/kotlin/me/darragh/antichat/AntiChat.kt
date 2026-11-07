package me.darragh.antichat

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object AntiChat : ClientModInitializer {
	const val MOD_ID: String = "anti-chat"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)
	private var enabled = true

	override fun onInitializeClient() {
		LOGGER.info("Initialized mod.")

		ClientSendMessageEvents.ALLOW_CHAT.register { _ -> !enabled }

		ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			dispatcher.register(ClientCommands.literal("antichat").executes { context ->
				enabled = !enabled
				context.source.sendFeedback(Component.literal(
					"Anti-Chat ${if (enabled) "enabled" else "disabled"}."
				))
				1
			})
		}
	}

	fun id(path: String): Identifier
			= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
