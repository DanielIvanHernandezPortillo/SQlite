package mx.edu.utng.constantdb;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import mx.edu.utng.constantdb.db.ConstantDB;

public class MainActivity extends ActionBarActivity {

    private ClientesDB Cdb;
    private ConstantDB db;
    private Cliente c;
    private EditText  nombre;
    private EditText  telefono;
    private EditText  correo;
    private EditText  idCliente;
    private Button guardar;
    private Button borrar;
    private Button actualizar;
    private Button mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ConstantDB(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = (EditText)findViewById(R.id.nombre);
        telefono = (EditText)findViewById(R.id.telefono);
        correo = (EditText)findViewById(R.id.correo);
        idCliente = (EditText)findViewById(R.id.etv_idCliente);
        guardar = (Button)findViewById(R.id.btn_agregar);
        borrar = (Button)findViewById(R.id.button_delete);
        actualizar = (Button)findViewById(R.id.button_update);
        mostrar = (Button)findViewById(R.id.button_viewAll);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();

        Cdb = new ClientesDB(this);
        //db = new ConstantDB(this);
        //c = new Cliente(this);

    }


    public void DeleteData(){
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = db.deleteData(idCliente.getText().toString());
                if(deleteRows> 0)
                    Toast.makeText(MainActivity.this, "Cliente borrado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Cliente no borrado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateData(){
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updateData(idCliente.getText().toString(),nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this, "Cliente modificado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Cliente no modificado", Toast.LENGTH_LONG).show();

            }
        });
    }

    public  void AddData(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MainActivity.this, "Cliente insertado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Cliente no insertado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll() {
        mostrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id:" + res.getString(0) + "\n");
                            buffer.append("nombre :" + res.getString(1) + "\n");
                            buffer.append("telefonp:" + res.getString(2) + "\n");
                            buffer.append("Corre :" + res.getString(3) + "\n\n");

                        }

                        // Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public  void showMessage(String tittle, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(Message);
        builder.show();
    }


    /*
        Cliente cliente1 = new Cliente("Jaime","11111","jaime@mail.net");
        Cliente cliente2 = new Cliente("Juan","22222","juan@mail.net");
        Cliente cliente3 = new Cliente("Pedro","33333","pedro@mail.net");
        Cliente cliente4 = new Cliente("David","44444","david@mail.net");

        Log.i("---> Base de datos: ", "Insertando Clientes....");
        db.insertCliente(cliente1);
        db.insertCliente(cliente2);
        db.insertCliente(cliente3);
        db.insertCliente(cliente4);
        Log.i("---> Base de datos: ", "Mostrando Clientes....");
        mostrarClientesLog();

        Log.i("---> Base de datos: ", "Borrando Cliente con id 1....");
        db.deleteCliente(1);
        mostrarClientesLog();

        Log.i("---> Base de datos: ", "Cambiando el nombre de cliente2....");
        cliente2.setName("María");
        db.updateCliente(cliente2);
        mostrarClientesLog();

        Log.i("---> Base de datos: ", "Buscando datos de cliente....");
        Cliente cliente = new Cliente();
        cliente = db.buscarCliente("Juan");
        Log.i("---> Base de datos: ", "Los datos de pedro son: "+cliente.toString());

        Log.i("---> Base de datos: ", "Cambiando el nombre de Juan....");
        cliente.setName("Maria");
        db.updateCliente(cliente);
        mostrarClientesLog();
    }

    private void mostrarClientesLog() {
        List list = db.loadClientes();

        for(Object cliente : list) {
            Log.i("---> Base de datos: ", cliente.toString());
        }
    }*/
}
