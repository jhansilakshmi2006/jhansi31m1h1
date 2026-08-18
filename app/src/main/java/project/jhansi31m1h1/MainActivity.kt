package project.jhansi31m1h1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etPrompt: EditText
    private lateinit var btnAsk: Button
    private lateinit var tvAnswer: TextView

    // Replace this with your Gemini API key
    private val apiKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPrompt = findViewById(R.id.etPrompt)
        btnAsk = findViewById(R.id.btnAsk)
        tvAnswer = findViewById(R.id.tvAnswer)

        btnAsk.setOnClickListener {

            val question = etPrompt.text.toString()

            if (question.isEmpty()) {
                tvAnswer.text = "Please enter a question."
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    val request = ChatRequest(
                        listOf(
                            Content(
                                listOf(
                                    Part(question)
                                )
                            )
                        )
                    )

                    val response = RetrofitClient.api.askGemini(
                        request,
                        apiKey
                    )

                    val answer = response.candidates[0].content.parts[0].text

                    withContext(Dispatchers.Main) {
                        tvAnswer.text = answer
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {
                        tvAnswer.text = "Error: ${e.message}"
                    }

                }
            }
        }
    }
}