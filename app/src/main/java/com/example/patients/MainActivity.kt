package com.example.patients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.patients.ui.consultations.ConsultationsScreen
import com.example.patients.ui.patients.PatientsScreen
import com.example.patients.ui.theme.PatientsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatientsTheme {
                var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
                var selectedName by rememberSaveable { mutableStateOf("") }

                val id = selectedId
                if (id == null) {
                    PatientsScreen(
                        onShowConsultations = { patient ->
                            selectedName = "${patient.prenom} ${patient.nom}"
                            selectedId = patient.id
                        }
                    )
                } else {
                    ConsultationsScreen(
                        patientId = id,
                        patientName = selectedName,
                        onBack = { selectedId = null }
                    )
                }
            }
        }
    }
}