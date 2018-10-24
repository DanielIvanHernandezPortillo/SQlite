package mx.edu.utng.constantdb;

/**
 * Created by ulises on 12/10/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mx.edu.utng.constantdb.db.ConstantDB;

public class ClientesDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ClientesDB(Context context){
        dbHelper = new DBHelper(context);
    }

    private void  openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }

    private void  openWritableDB(){
        db = dbHelper.getWritableDatabase();

    }

    private void closeDB(){
        if (db != null){
            db.close();
        }
    }

    private static  class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context, ConstantDB.DB_NAME,
                    null, ConstantDB.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL(ConstantDB.TABLA_CLIENTES_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    private ContentValues clienteMapperContentValues(Cliente cliente){
        ContentValues cv= new ContentValues();
        cv.put(ConstantDB.CLI_NOMBRE, cliente.getName());
        cv.put(ConstantDB.CLI_TELF, cliente.getTelf());
        cv.put(ConstantDB.CLI_MAIL, cliente.getMail());
        return cv;
    }



    public long insertCliente(Cliente cliente){
        this.openWritableDB();
        long rowID =
                db.insert(ConstantDB.TABLA_CLIENTES,
                        null, clienteMapperContentValues(cliente));
        this.closeDB();
        return rowID;
    }

    public void updateCliente(Cliente cliente){
        this.openWritableDB();
        String where = ConstantDB.CLI_ID +"=?";
        db.update(ConstantDB.TABLA_CLIENTES,
                clienteMapperContentValues(cliente),
                where, new String[]{String.valueOf(cliente.getId())});
        db.close();
    }

    public void  deleteCliente(int id){
        this.openWritableDB();
        String where = ConstantDB.CLI_ID + "=?";
        db.delete(ConstantDB.TABLA_CLIENTES, where,
                new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadClientes() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{ConstantDB.CLI_ID, ConstantDB.CLI_NOMBRE, ConstantDB.CLI_TELF, ConstantDB.CLI_MAIL};
        Cursor c = db.query(ConstantDB.TABLA_CLIENTES, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.setId(c.getInt(0));
                cliente.setName(c.getString(1));
                cliente.setTelf(c.getString(2));
                cliente.setMail(c.getString(3));
                list.add(cliente);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }


    //Método buscarCliente
    public Cliente buscarCliente(String nombre) {
        Cliente cliente = new Cliente();
        this.openReadableDB();
        String where = ConstantDB.CLI_NOMBRE + "= ?";
        String[] whereArgs = {nombre};
        Cursor c = db.query(ConstantDB.TABLA_CLIENTES, null, where, whereArgs, null, null, null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            cliente.setId(c.getInt(0));
            cliente.setName(c.getString(1));
            cliente.setTelf(c.getString(2));
            cliente.setMail(c.getString(3));
            c.close();
        }
        this.closeDB();
        return cliente;
    }



}

