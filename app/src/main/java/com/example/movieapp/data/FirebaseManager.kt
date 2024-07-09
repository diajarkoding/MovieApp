import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseManager {

    private var databaseReference: DatabaseReference? = null

    fun getDatabaseReference(): DatabaseReference {
        if (databaseReference == null) {
            val firebaseDatabase = FirebaseDatabase.getInstance("https://auth-demo-flutter-917eb-default-rtdb.asia-southeast1.firebasedatabase.app")
            databaseReference = firebaseDatabase.getReference("User")
        }
        return databaseReference!!
    }
}
