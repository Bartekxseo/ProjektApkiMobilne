import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projekt.ExerciseModel
import java.text.SimpleDateFormat
import java.util.*

class DbHandler
    (context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + CRUNCHES_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + AMOUNT_COL + " INTEGER)")

        val query1 = ("CREATE TABLE " + TARGET_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TARGET_COL + " INTEGER)")
        val query2 = ("INSERT INTO $TARGET_TABLE_NAME ($NAME_COL, $TARGET_COL) VALUES ( 'projekt', 0)")
        db.execSQL(query)
        db.execSQL(query1)
        db.execSQL(query2)
    }

    fun getTarget() : Int {
        val db = this.readableDatabase
        val cursorTarget: Cursor = db.rawQuery("SELECT * FROM $TARGET_TABLE_NAME", null)

        var exerciseTarget = 0;

        if (cursorTarget.moveToFirst()) {
            exerciseTarget = cursorTarget.getInt(2)
        }


        cursorTarget.close()
        return exerciseTarget

    }

    fun updateTarget(
       newTarget: Int
    ) {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_COL, "projekt")
        values.put(TARGET_COL, newTarget)

        db.update(TARGET_TABLE_NAME, values, "name=?", arrayOf("projekt"))
        db.close()
    }

    fun addNewExercise(
        amount: Int
    ) {

        if(amount > 0)
        {
            val db = this.writableDatabase

            val values = ContentValues()

            val currentDate = getCurrentDateTime().toString("dd/MM/yyyy")
            values.put(DATE_COL, currentDate)
            values.put(AMOUNT_COL, amount)

            db.insert(CRUNCHES_TABLE_NAME, null, values)

            db.close()
        }
    }

    fun getExercise(): ArrayList<ExerciseModel> {
        val db = this.readableDatabase

        val cursorExercise: Cursor = db.rawQuery("SELECT * FROM $CRUNCHES_TABLE_NAME", null)

        val exerciseModelArrayList: ArrayList<ExerciseModel> = ArrayList()

        if (cursorExercise.moveToFirst()) {
            do {
                exerciseModelArrayList.add(
                    ExerciseModel(
                        cursorExercise.getString(1),
                        cursorExercise.getInt(2),
                    )
                )
            } while (cursorExercise.moveToNext())
        }
        cursorExercise.close()
        return exerciseModelArrayList
    }

    fun getTodaysExerciseDoneSum() : Int {
        val db = this.readableDatabase
        val today = getCurrentDateTime().toString("dd/MM/yyyy");
        val cursor : Cursor = db.rawQuery("SELECT * FROM $CRUNCHES_TABLE_NAME WHERE $DATE_COL = '$today'", null)
        var doneToday = 0
        if (cursor.moveToFirst()) {
            do {
                doneToday+= cursor.getInt(2)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return doneToday
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CRUNCHES_TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TARGET_TABLE_NAME)
        onCreate(db)
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    companion object {

        private const val DB_NAME = "companionDb"

        private const val DB_VERSION = 1

        private const val CRUNCHES_TABLE_NAME = "crunches"

        private const val TARGET_TABLE_NAME = "target"

        private const val ID_COL = "ID"

        private const val DATE_COL = "date"

        private const val AMOUNT_COL = "amount"

        private const val TARGET_COL = "userTarget"

        private const val NAME_COL = "name"

    }
}