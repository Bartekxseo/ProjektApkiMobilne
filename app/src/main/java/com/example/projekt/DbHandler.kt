import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projekt.ExerciseModel
import java.text.SimpleDateFormat
import java.util.*

class DbHandler  // creating a constructor for our database handler.
    (context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        val query = ("CREATE TABLE " + CRUNCHES_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + AMOUNT_COL + " INTEGER)")

        val query1 = ("CREATE TABLE " + TARGET_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TARGET_COL + " INTEGER)")
        val query2 = ("INSERT INTO $TARGET_TABLE_NAME ($NAME_COL, $TARGET_COL) VALUES ( 'projekt', 0)")
        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query)
        db.execSQL(query1)
        db.execSQL(query2)
    }

    fun addTarget()
    {
        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, "projekt")
        values.put(TARGET_COL, 0)

        // after adding all values we are passing
        // content values to our table.
        db.insert(TARGET_TABLE_NAME, null, values)

        // at last we are closing our
        // database after adding database.
        db.close()
    }

    fun updateTarget(
       newTarget: Int
    ) {

        // calling a method to get writable database.
        val db = this.writableDatabase
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, "projekt")
        values.put(TARGET_COL, newTarget)

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TARGET_TABLE_NAME, values, "name=?", arrayOf("projekt"))
        db.close()
    }

    // this method is use to add new course to our sqlite database.
    fun addNewExercise(
        amount: Int
    ) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        val currentDate = getCurrentDateTime().toString("dd/MM/yyyy")
        values.put(DATE_COL, currentDate)
        values.put(AMOUNT_COL, amount)

        // after adding all values we are passing
        // content values to our table.
        db.insert(CRUNCHES_TABLE_NAME, null, values)

        // at last we are closing our
        // database after adding database.
        db.close()
    }

    fun getExercise(): ArrayList<ExerciseModel> {
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.

        // on below line we are creating a cursor with query to read data from database.
        val cursorExercise: Cursor = db.rawQuery("SELECT * FROM $CRUNCHES_TABLE_NAME", null)

        // on below line we are creating a new array list.

        // on below line we are creating a new array list.
        val exerciseModelArrayList: ArrayList<ExerciseModel> = ArrayList()

        // moving our cursor to first position.

        // moving our cursor to first position.
        if (cursorExercise.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                exerciseModelArrayList.add(
                    ExerciseModel(
                        cursorExercise.getString(1),
                        cursorExercise.getInt(2),
                    )
                )
            } while (cursorExercise.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursorExercise.close()
        return exerciseModelArrayList
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
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
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "companionDb"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val CRUNCHES_TABLE_NAME = "crunches"

        private const val TARGET_TABLE_NAME = "target"

        // below variable is for our id column.
        private const val ID_COL = "ID"

        // below variable is for our course name column
        private const val DATE_COL = "date"

        // below variable id for our course duration column.
        private const val AMOUNT_COL = "amount"

        // below variable for our course description column.
        private const val TARGET_COL = "userTarget"

        private const val NAME_COL = "name"

    }
}