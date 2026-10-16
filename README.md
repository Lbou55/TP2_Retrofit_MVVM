# TP2_Retrofit_MVVM
<img width="351" height="771" alt="image" src="https://github.com/user-attachments/assets/2c881245-f670-4735-b22d-222364098031" />
<img width="1805" height="320" alt="image" src="https://github.com/user-attachments/assets/96aa3481-0293-41ef-8b63-1939fb9c83be" />


Questions de compréhension:

Q1: Lors d'une rotation, Android détruit puis recrée l'Activity et l'ecran Compose. En revanche le ViewModel n'est pas detruit , Android le conserve dans un espace qui survit aux changements de configuration et garde son UiState et la liste avec : viewModel(factory = ...)  et init { loadPatients() }

Q2: Pour que seul le ViewModel puisse modifier l’état. Pour que seul le ViewModel puisse modifier l’état. C’est la mise en œuvre du flux unidirectionnel : l’état descend, les événements remontent

Q3: Un état décrit ce que l'ecran est à un instant donnée, il a toujours une valeur et il est réaffiché aprèes une rotation 
Ex dans le TP: uiState: (Loading / Success avec la liste / Error).
Un événement est quelque chose qui s'est produit une seule fois , il n'a pas de valeur conservé et il n'est pas rejoué 
Ex dans le TP: messages: "Ali Ben Salah ajouté"

Q4: Un erreur MissingFieldException

Q5: HttpException : le serveur a répondu mais avec un code 4xx ou 5xx (le patient n’existe)
    IOException : il n'y a eu aucune réponse (serveur éteint, mauvaise IP, Wi-Fi coupé, délai dépassé.)
    Serveur arrété : IOException
    Patient inexistant : HttpException

Q6: Si on remplace l'API par une base ROOM:
  -PatientApi.kt: remplacé par un DAO Room
  -PatientRepository.kt: il appellerait le DAO au lieu de api 
  -AppContainer.kt: il construirait la base Room au lieu de Retrofit et Okhttp
  -Patient.kt: il recevrait les annotations Room (@Entity)
    
