package com.example.models

import kotlinx.serialization.Serializable
/*echo "# KTORBatalla" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/AlejandroCRG1/KTORBatalla.git
git push -u origin main*/
@Serializable
data class Participante(val nombre: String, val urlFotoPerfil: String, var puntuació : Int, var dataNaixement : String)