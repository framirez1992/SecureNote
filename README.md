# SecureNote
App para registro seguro de notas.

Descripcion General
Esta aplicacion proporciona al usuario una forma de crear, editar y eliminar notas de texto con validacion biometrica o por contrasena del dispositivo.
Tambien proporciona un mecanismo de carga para lote de notas desde un archivo json.

Compilacion:
Se recomienda utilizar la distribucion de graddle gradle-8.4-rc-3-all o superiores.
utilizar Java 11 para compilar

app.build.graddle level

 compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
	
	 kotlinOptions {
        jvmTarget = '11'
    }
	
Para las annotacions utilizadas por dagger2 es necesario:
build.graddle.app level
	utiizar el plugin  
	plugins {
    id 'kotlin-kapt'
	}

agregar bajo build.graddle.app.android 
kapt {
        generateStubs = true
    }
	
Dependencias:
	
	Dagger2 (Dependency injection)
	api 'com.google.dagger:dagger:2.42'
    api 'com.google.dagger:dagger-android:2.24'

    annotationProcessor 'com.google.dagger:dagger-compiler:2.24'
    kapt 'com.google.dagger:dagger-compiler:2.24'

    annotationProcessor 'com.google.dagger:dagger-android-processor:2.24'
    kapt 'com.google.dagger:dagger-android-processor:2.24'

    compileOnly 'javax.annotation:jsr250-api:1.0'
    implementation 'javax.inject:javax.inject:1'
	
	Biometrics (fingerprint and facial recognition)
	implementation 'androidx.biometric:biometric:1.2.0-alpha05'
	
	RecyclerView
	implementation 'androidx.recyclerview:recyclerview:1.3.1'
	
	ViewModels
	implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
	
	Corutines
	implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
	
	Gson (json parser)
	implementation 'com.google.code.gson:gson:2.10.1'
	
	ViewBinding
	build.graddle.app.android
	buildFeatures{
        viewBinding true
    }
	
    Fragments y Activities
	implementation 'androidx.fragment:fragment-ktx:1.6.1'
    implementation 'androidx.activity:activity-ktx:1.7.2'
	
	FireBase (data storage)
	implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation 'com.google.firebase:firebase-firestore-ktx:24.8.1'
	
	add the googleplay services file in folder secureNote/app/google-services.json
	add in build.graddle.app
	pluggin{
		id 'com.google.gms.google-services'
	}
	
	

	

Ejecutar la aplicación

Para correr esta aplicacion es necesario un dispotivo con android Pie (api 28) o posterior.
Si el dispositivo no cuenta con lector de huellas o face id tiene la posibilidad de utilizar la contrasena del dispositivo.
Si el dispositivo posee capacidades de lectura biometrica pero no tiene ninguna configurada, la aplicacion guia  al usuario para agregar una.

La aplicacion utilizar una case de datos firebase la cual puede funcionar de manera offline en caso que el dispositivo no tenga internet.
cualquier transaccion realizada se guardada de forma local hasta el  el dispositivo vuelta a estar conectado y envie de forma automatica hacia el servidor.

carga masiva de notas:
El dispositivo puede cargar archivos con una estructura definida de datos y transformarla a instrucciones de insert de notas. posee validaciones para: 
Solo se permiten archivos .json
Validacion de archvivo vacio
Validacion de archivo Corrupto (archivo mal formateado json)


Observaciones: 
Esta app fue realizadacon fines de preentacion. no se considera un producto terminado.La base de datos esta compartida. 
