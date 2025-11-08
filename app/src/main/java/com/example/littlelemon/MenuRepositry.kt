
package littlelemon

import android.content.Context
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com. example. littlelemon. MenuDao
import com. example. littlelemon. AppDatabase
import com. example. littlelemon. MenuNetwork
import com. example. littlelemon. MenuItemEntity
import kotlinx.serialization.Serializable
import com.example.littlelemon.toEntity
import io. ktor. client. statement. bodyAsText


// Assumes these are in the same package `littlelemon`:
// - AppDatabase, MenuDao, MenuItemEntity (from your Database.kt)
// - MenuNetwork, MenuItemNetwork (from your Network.kt)

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)



class MenuRepository(private val menuDao: MenuDao) {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        // 👇 This line tells Ktor to accept even text/plain responses as JSON
        expectSuccess = true
        engine {
            // Optional logging or timeouts if you want
        }
    }

    companion object {
        const val MENU_URL =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    }

    suspend fun refreshMenu() {
        try {
            println("🌐 Fetching menu from $MENU_URL")

            // Fetch raw JSON text
            val jsonText: String = client.get(MENU_URL).bodyAsText()
            println("📄 Raw response length: ${jsonText.length}")

            // Decode manually
            val response: MenuNetwork = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.decodeFromString(jsonText)

            println("✅ Network success: got ${response.menu.size} items")

            val entities = response.menu.map { it.toEntity() }

            menuDao.clearAll()
            menuDao.insertAll(entities)
            println("💾 Inserted ${entities.size} items into DB")

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error fetching menu: ${e.localizedMessage}")
        }
    }
}


