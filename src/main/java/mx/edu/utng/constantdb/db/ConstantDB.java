package mx.edu.utng.constantdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ulises on 12/10/2018.
 */

public class ConstantDB extends SQLiteOpenHelper{


    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;


    public static final String TABLA_CLIENTES = "clientes";
    public static final String CLI_ID = "id";
    public static final String CLI_NOMBRE = "nombre";
    public static final String CLI_TELF = " telefono ";
    public static final String CLI_MAIL = " email ";


        public static final String TABLA_CLIENTES_SQL = "CREATE TABLE "+ TABLA_CLIENTES + "("
            + CLI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLI_NOMBRE + " TEXT not null,"
            + CLI_TELF + " TEXT,"
            + CLI_MAIL + " TEXT);";

    public ConstantDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLA_CLIENTES +" " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,telefono TEXT,email TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_CLIENTES);
        onCreate(db);
    }

    public  boolean insertData(String nombre, String telefono, String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_NOMBRE,nombre);
        contentValues.put(CLI_TELF,telefono);
        contentValues.put(CLI_MAIL,correo);
        //contentValues.put(COL_10,garantialiquida);
        long result = db.insert(TABLA_CLIENTES,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLA_CLIENTES,null);
        return res;
    }

    public boolean updateData(String id,String nombre,String telefono,String correo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLI_NOMBRE,nombre);
        contentValues.put(CLI_TELF,telefono);
        contentValues.put(CLI_MAIL,correo);
        db.update(TABLA_CLIENTES, contentValues, "id = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String idgrupo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLA_CLIENTES, "id = ?",new String[] {idgrupo});
    }


}
