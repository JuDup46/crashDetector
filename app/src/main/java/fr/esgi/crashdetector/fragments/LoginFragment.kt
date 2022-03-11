package fr.esgi.crashdetector.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.esgi.crashdetector.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.esgi.crashdetector.api.ApiClient
import fr.esgi.crashdetector.models.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class LoginFragment :  Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = requireContext().filesDir
        val mailDirectory = File(path, "mail")
        mailDirectory.mkdirs()
        val file = File(mailDirectory, "email.txt")
        file.deleteRecursively()


        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button_login)
        val action = LoginFragmentDirections.actionLoginFragmentToRunFragment()

        button.setOnClickListener {
            var editLogin = view.findViewById<EditText>(R.id.editLogin)
            var editScooter = view.findViewById<EditText>(R.id.editScooterId)

            val editLoginValue = editLogin.text.toString().trim()
            val editScooterValue = editScooter.text.toString().trim()

            when {
                editLoginValue.isEmpty() && editScooterValue.isEmpty() -> {
                    editLogin.error = "Champ vide"
                    editScooter.error = "Champ vide"
                }
                editLoginValue.isEmpty() -> {
                    editLogin.error = "Champ vide"
                }
                editScooterValue.isEmpty() -> {
                    editScooter.error = "Champ vide"
                }
                else -> {
                    MainScope().launch(Dispatchers.Main) {
                        try {
                            var user = withContext(Dispatchers.Main) {
                                ApiClient.getUser(editLoginValue)
                            }
                            if (user.idscooter == editScooterValue) {
                                file.appendText(editLoginValue);
                                findNavController().navigate(action)
                            } else {
                                editScooter.error = "Ce modèle n'est pas lié à votre compte."
                            }
                        } catch (e: Exception) {
                            editLogin.error = "Email inexistant."
                        }
                    }
                }
            }

        }

        /*


            Log.i("AUTH", "$editLogin $editScoot")
            Log.i("AUTH", editScoot.isNotEmpty().toString())
            Log.i("AUTH", checkLogin(editLogin, editScoot).toString())
            if (checkLogin(editLogin, editScoot)) {

            }
        }

    }

    private fun getUserByEmail(email: String): User? {
        var userFound: User? = null
        MainScope().launch(Dispatchers.Main) {
            val users = withContext(Dispatchers.Main) {
                ApiClient.getUsers()
            }
            for (user in users) {
                Log.i("AUTH5", user.toString())
                if (user.email == email) userFound = user
            }
        }
        Log.i("AUTH5", userFound.toString())
        return userFound
    }

    private fun checkLogin(email: String, scooterId: String): Boolean {
        return getUserByEmail(email) != null && scooterId.length >= 10
    }

     */
    }
}