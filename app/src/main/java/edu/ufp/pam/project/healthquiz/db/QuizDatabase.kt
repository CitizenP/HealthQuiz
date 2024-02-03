package edu.ufp.pam.project.healthquiz.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ufp.pam.project.healthquiz.db.quizzes.QuizzResult
import edu.ufp.pam.project.healthquiz.db.quizzes.QuizzResultDao
import edu.ufp.pam.project.healthquiz.db.users.User
import edu.ufp.pam.project.healthquiz.db.users.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, QuizzResult::class],
    version = 7
)
abstract class QuizDatabase : RoomDatabase() {
    abstract val userDao: UserDAO
    abstract val quizzDao: QuizzResultDao
    // Behaves like a Singleton
    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabaseInstance(context: Context): QuizDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuizDatabase::class.java,
                        "HealthQuiz.db"
                    ).fallbackToDestructiveMigration().build().also {
                        INSTANCE = it
                    }

                    INSTANCE = instance
                }
                Log.i(this.javaClass.simpleName, "getDatabaseInstance(): $INSTANCE")
                return instance
            }
        }
    }
}