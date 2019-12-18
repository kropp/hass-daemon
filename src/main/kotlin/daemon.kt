import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

@KtorExperimentalAPI
fun main() {
  val client = HttpClient {
    install(WebSockets)
  }

  runBlocking {
    val hostFromEnv = System.getenv("HOME_ASSISTANT_HOST")
    client.wss(
        host = hostFromEnv ?: "hassio",
        path = (System.getenv("HOME_ASSISTANT_PATH") ?: if (hostFromEnv == null) "/homeassistant" else "") + "/api/websocket"
    ) {
      for (frame in incoming.mapNotNull { it as? Frame.Text }) {
        val text = frame.readText()
        println(text)
        if (text.contains("auth_required")) {
          send("""{"type": "auth", "access_token": "${System.getenv("HASSIO_TOKEN")}"}""")
        }
        if (text.contains("auth_ok")) {
          send("""{"id": 1, "type": "subscribe_events"}""")
        }
      }
    }
  }
}